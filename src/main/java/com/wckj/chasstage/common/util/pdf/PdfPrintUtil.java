package com.wckj.chasstage.common.util.pdf;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.wckj.chasstage.common.util.pdf.event.PdfObliqueLineCellEvent;
import com.wckj.framework.core.utils.StringUtils;
import org.springframework.web.context.ContextLoader;

import java.io.File;

public class PdfPrintUtil {
	
	/**默认二维码条码类型(1为公文二维条码，2为QRCode),值为@value*/
	public static int defaultBarCodeType = 1;
	
	public static final String separator = File.separator;
	
	/**
	 * 打印斜线
	 * @param stamper PdfStamper
	 * @param pageNo int 页号
	 * @param firstX float 开始点X轴坐标
	 * @param firstY float 开始点Y轴坐标
	 * @param endX float 结束点X轴坐标
	 * @param endY float 结束点Y轴坐标
	 * @throws Exception
	 */
	public static void printObliqueLine(PdfStamper stamper, int pageNo, float firstX,
                                        float firstY, float endX, float endY) throws Exception{
		PdfContentByte cb = stamper.getOverContent(pageNo);
		
		cb.setLineWidth(1.0f);
		cb.moveTo(firstX, firstY);
		cb.lineTo(endX, endY);
		cb.stroke();
	}
	
	/**
	 * 获取业务表的打印处理类
	 * @param tableName String 业务表名称
	 * @param form RdpFmForm 业务表单
	 * @param mainTable RdpResTable 资源主表
	 * @param businessId String 业务主键
	 * @param rs RecordSet 数据库结果集
	 * @param conn Connection 数据库连接
	 * @return PdfPrintHandler
	 */
//	public static PdfPrintHandler getPrintHandler(String tableName, String businessId,
//			boolean isEncryptBarcode){
//		PdfPrintHandler handler = null;
//		
////		String propName = "pdf.print." + tableName.toLowerCase();
////		String propValue = ContextPropertyConfigurer.getPropertyValue(propName);
//		String propValue = "com.wckj.szx.bazx.util.print.caseAqBaqsyqkdjNew";
//		
//		if(StringUtils.isNotEmpty(propValue)){
//			try {
//				handler = (PdfPrintHandler)ClassUtil.getInstance(propValue);
//				//----handler.setForm(form);
//				//----handler.setMainTable(mainTable);
//				handler.setBusinessId(businessId);
//
//				handler.setEncryptBarcode(isEncryptBarcode);
//			}
//			catch (Exception e) {
//				handler = null;
//			}
//		}
//		
//		return handler;
//	}
	
	/**
	 * 获取中文实际字体(仿宋体,横向)
	 * @param fontSize float 字段大小,如 12
	 * @param fontStyle int 字体样式,如 Font.BOLD(粗),Font.ITALIC(斜),Font.BOLDITALIC(粗斜),Font.NORMAL(普通)等
	 * @return Font
	 */
	public static Font getCnFSongHFont(float fontSize, int fontStyle){
		return ITextFontUtil.getFont(getCnFSongHBaseFont(), fontSize, fontStyle);
	}

	/**
	 * 获取中文实际字体(仿宋体,横向)
	 * @return BaseFont
	 */
	public static BaseFont getCnFSongHBaseFont(){
		return getCnHBaseFont("SIMFANG.TTF");
	}

	/**
	 * 获取中文基本字体
	 * @param fontFile String 字体文件名称,如 SIMFANG.TTF(仿宋体)
	 * @return BaseFont
	 */
	public static BaseFont getCnHBaseFont(String fontFile){
		String appRealPath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		return ITextFontUtil.getBaseFont(appRealPath + separator+"static"+separator+"chas"+separator+"pdf"+separator+"fonts"+separator + fontFile, BaseFont.IDENTITY_H);
	}
	
	/**
	 * 获取一个表格列
	 * @param text String 文本
	 * @param font Font 字体
	 * @param fixedHeight float 行固定高度
	 * @param minimumHeight float 行最小高度
	 * @return PdfPCell
	 */
	public static PdfPCell getTableCell(String text, Font font, float fixedHeight,
                                        float minimumHeight){
		return getTableCell(text, font, fixedHeight, minimumHeight, Element.ALIGN_CENTER,
				Element.ALIGN_MIDDLE);
	}
	
	/**
	 * 获取一个表格列
	 * @param text String 文本
	 * @param font Font 字体
	 * @param fixedHeight float 行固定高度
	 * @param minimumHeight float 行最小高度
	 * @param horizontalAlignment int 水平对方方式
	 * @param verticalAlignment int 垂直对方方式
	 * @return PdfPCell
	 */
	public static PdfPCell getTableCell(String text, Font font, float fixedHeight,
                                        float minimumHeight, int horizontalAlignment, int verticalAlignment){
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(horizontalAlignment);
		cell.setVerticalAlignment(verticalAlignment);
		
		//设置固定高度
		if(fixedHeight != 0)
			cell.setFixedHeight(fixedHeight);
		
		//设置最小高度
		if(minimumHeight != 0)
			cell.setMinimumHeight(minimumHeight);
		
		return cell;
	}
	
	/**
	 * 获取一个表格列
	 * @param text String 文本
	 * @param font Font 字体
	 * @param fixedHeight float 行固定高度
	 * @param minimumHeight float 行最小高度
	 * @param horizontalAlignment int 水平对方方式
	 * @param verticalAlignment int 垂直对方方式
	 * @return PdfPCell
	 */
	public static PdfPCell getNoBorderTableCell(String text, Font font, float fixedHeight,
                                                float minimumHeight, int horizontalAlignment, int verticalAlignment){
		PdfPCell cell = getTableCell(text, font, fixedHeight, minimumHeight,
				horizontalAlignment, verticalAlignment);
		cell.setBorder(0);
		
		return cell;
	}
	
	/**
	 * 获取一个无边框表格列
	 * @param text String 文本
	 * @param font Font 字体
	 * @return PdfPCell
	 */
	public static PdfPCell getNoBorderTableCell(String text, Font font){
		return getNoBorderTableCell(text, font, 0, 0);
	}
	
	/**
	 * 获取一个无边框表格列
	 * @param text String 文本
	 * @param font Font 字体
	 * @param fixedHeight float 行固定高度
	 * @param minimumHeight float 行最小高度
	 * @return PdfPCell
	 */
	public static PdfPCell getNoBorderTableCell(String text, Font font, float fixedHeight, float minimumHeight){
		PdfPCell cell = getTableCell(text, font, fixedHeight, minimumHeight);
		cell.setBorder(0);
		
		return cell;
	}
	
	/**
	 * 构建包含空格的字符串
	 * @param baseStr String 要构建的基本字符串
	 * @param strLength int 字符串长度
	 * @return String
	 */
	public static String buildBlankStr(String baseStr, int strLength){
		String blankStr = StringUtils.clearNull(baseStr).trim();

		if(StringUtils.isNotEmpty(blankStr)){
			int baseLength = StringUtils.strlen(blankStr);
			if(baseLength < strLength){
				int blankLength = (strLength - baseLength) / 2;
				for(int i = 0; i < blankLength; i ++){
					blankStr = " " + blankStr + " ";
				}
			}
		}
		else{
			for(int i = 0; i < strLength * 2; i ++){
				blankStr += " ";
			}
		}

		return blankStr;
	}
	public static String fillStr(String baseStr, int strLength){
		String blankStr = StringUtils.clearNull(baseStr).trim();

		if(StringUtils.isNotEmpty(blankStr)){
			int baseLength = StringUtils.strlen(blankStr);
			if(baseLength < strLength){
				int blankLength = (strLength - baseLength) / 2;
				for(int i = 0; i < blankLength; i ++){
					blankStr = " " + blankStr + " ";
				}
				if((strLength - baseLength)%2==1){
					blankStr+=" ";
				}
			}
		}
		else{
			for(int i = 0; i < strLength; i ++){
				blankStr += " ";
			}
		}

		return blankStr;
	}

	/**
	 * 获取中文实际字体(方正小标宋,横向)
	 * @param fontSize float 字段大小,如 12
	 * @param fontStyle int 字体样式,如 Font.BOLD(粗),Font.ITALIC(斜),Font.BOLDITALIC(粗斜),Font.NORMAL(普通)等
	 * @return Font
	 */
	public static Font getCnFZSongHFont(float fontSize, int fontStyle){
		return ITextFontUtil.getFont(getCnFZSongHBaseFont(), fontSize, fontStyle);
	}
	
	/**
	 * 获取中文实际字体(方正小标宋,横向)
	 * @return BaseFont
	 */
	public static BaseFont getCnFZSongHBaseFont(){
		return getCnHBaseFont("方正小标宋简体.TTF");
	}
	
	/**
	 * 获取二维码条码类型
	 * @return int 二维码条码类型(1为公文二维条码，2为QRCode)
	 */
	public static int getBarCodeType(){
//		String barCodeTypeStr = SettingUtil.getParamValue("barCodeType");
//		if(StringUtils.isNotEmpty(barCodeTypeStr)){
//			return Integer.parseInt(barCodeTypeStr);
//		}
//		else{
			return defaultBarCodeType;
//		}
	}
	/**
	 * 判断表单是否需要二维码加密
	 * @param tableName String 业务表名称
	 * @return boolean
	 */
	public static boolean isBarcodeEncryptForm(String tableName){
		boolean isEncrypt = true;
		
		/*String propName = "barcode.unencrypt.tables";
		String propValue = ContextPropertyConfigurer.getPropertyValue(propName);		
		if(StringUtils.isNotEmpty(propValue)){
			String[] encryptForms = propValue.split(CommonConstants.DEFAULT_SPLIT_STR);
			for(String table : encryptForms){
				if(table.equalsIgnoreCase(tableName)){
					isEncrypt = false;
					break;
				}
			}
		}*/
		
		return isEncrypt;
	}
	
	
	/**
	 * 补齐表格行并返回单元格事件数组(用于画斜线)
	 * @param table PdfPTable 需要处理的PdfPTable
	 * @param maxTableHeight float 表格最大高度
	 * @param currentTableHeight float 当前表格高度
	 * @param columnNum 单元列数量
	 * @return PdfObliqueLineCellEvent[]
	 */
	public static PdfObliqueLineCellEvent[] buildElseRow(PdfPTable table, float maxTableHeight, float currentTableHeight, int columnNum, int blankRowHeight){
		PdfObliqueLineCellEvent[] obliqueLineEvents = null;
		
		//填充斜线处理
        if(currentTableHeight < maxTableHeight){
        	float elseTableHeight = maxTableHeight - currentTableHeight;
        	int elseRowNum = (int)(elseTableHeight / blankRowHeight);
        	
        	//构建斜线单元格事件
        	obliqueLineEvents = buildElseRow(table, elseRowNum, columnNum,blankRowHeight);
        }
        
        return obliqueLineEvents;
	}
	
	/**
	 * 补齐表格行并返回单元格事件数组(用于画斜线)
	 * @param table PdfPTable 需要处理的PdfPTable
	 * @param elseRowNum int 需要补齐的行数
	 * @param columnNum 单元列数量
	 * @return PdfObliqueLineCellEvent[]
	 */
	public static PdfObliqueLineCellEvent[] buildElseRow(PdfPTable table, int elseRowNum, int columnNum, int blankRowHeight){
		//斜线开始行和结束行渲染事件
		PdfObliqueLineCellEvent obliqueLineStartEvent = null;
		PdfObliqueLineCellEvent obliqueLineEndEvent = null;
		
        //补齐子表行数
    	for(int i = 1; i <= elseRowNum; i ++){
    		for(int j = 0; j < columnNum; j ++){
    			PdfPCell blankCell = getBlankCell(PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, blankRowHeight);
    			
    			//开始单元格
				if(i == 1 && j == 0){
					obliqueLineStartEvent = new PdfObliqueLineCellEvent();
					blankCell.setCellEvent(obliqueLineStartEvent);
				}
				
				//结束单元格
				if(i == elseRowNum && j == (columnNum - 1)){
					obliqueLineEndEvent = new PdfObliqueLineCellEvent();
					blankCell.setCellEvent(obliqueLineEndEvent);
				}
				
				table.addCell(blankCell);
    		}
    	}
    	
    	return new PdfObliqueLineCellEvent[]{obliqueLineStartEvent, obliqueLineEndEvent};
	}
	
	/**
	 * 画斜线
	 * @param writer PdfWriter
	 * @param events PdfObliqueLineCellEvent[]
	 */
	public static void writeObliqueLine(PdfWriter writer, PdfObliqueLineCellEvent[] events, int height){
		if(events != null && events.length == 2){
			PdfObliqueLineCellEvent obliqueLineStartEvent = events[0];
			PdfObliqueLineCellEvent obliqueLineEndEvent = events[1];
			writeObliqueLine(writer, obliqueLineStartEvent, obliqueLineEndEvent,height);
		}
	}
	
	/**
	 * 画斜线
	 * @param writer PdfWriter
	 * @param obliqueLineStartEvent PdfObliqueLineCellEvent
	 * @param obliqueLineEndEvent PdfObliqueLineCellEvent
	 */
	public static void writeObliqueLine(PdfWriter writer, PdfObliqueLineCellEvent obliqueLineStartEvent, PdfObliqueLineCellEvent obliqueLineEndEvent, int height){
		if(obliqueLineStartEvent != null && obliqueLineEndEvent != null){
			Rectangle startRect = obliqueLineStartEvent.getRect();
			Rectangle endRect = obliqueLineEndEvent.getRect();
			if(startRect != null && endRect != null){
				PdfContentByte cb = writer.getDirectContent();
				
				cb.setLineWidth(1.0f);
				cb.moveTo(startRect.getLeft(), startRect.getTop());
				cb.lineTo(endRect.getRight(), endRect.getTop() - height);
				cb.stroke();
			}
		}
	}
	
	/**
	 * 获取一个表格空列
	 * @param font Font 字体
	 * @param fixedHeight float 行固定高度
	 * @param minimumHeight float 行最小高度
	 * @return PdfPCell
	 */
	public static PdfPCell getBlankCell(Font font, float fixedHeight, float minimumHeight){
		return getTableCell("", font, fixedHeight, minimumHeight);
	}
}
