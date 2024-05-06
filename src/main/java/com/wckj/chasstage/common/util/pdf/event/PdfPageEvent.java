package com.wckj.chasstage.common.util.pdf.event;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.*;
import com.wckj.chasstage.common.util.pdf.PdfPrintUtil;
import com.wckj.framework.core.utils.StringUtils;


public class PdfPageEvent extends PdfPageEventHelper {
	
	//PdfTemplate实例用于保存总页数
	public PdfTemplate tpl;
	
	//页码字体
	public BaseFont helv;
	
	//字体尺寸
	public int fontSize = 14;
	
	//页号对齐方式
	public int pageNoAlign = Element.ALIGN_LEFT;
	
	//页码文本
	public String pageNumText;
	
	//是否展示页号
	public boolean showPageNo;
	
	//是否展示条形码
	public boolean showBarcode;
	
	/**
	 * 构建函数
	 * @param showPageNo boolean 是否展示页号
	 * @param showBarcode boolean 是否展示条形码
	 */
	public PdfPageEvent(boolean showPageNo, boolean showBarcode) {
		super();
		this.showPageNo = showPageNo;
		this.showBarcode = showBarcode;
	}

	@Override
	public void onCloseDocument(PdfWriter writer, Document document) {
		if(showPageNo){
			int startX = StringUtils.strlen(pageNumText) / 2 * fontSize;
			tpl.beginText();
			tpl.setFontAndSize(PdfPrintUtil.getCnFSongHBaseFont(), fontSize);
			tpl.setTextMatrix(startX, 0);
			tpl.showText("" + (writer.getPageNumber() - 1) + " 页");		
			tpl.endText();
			tpl.moveTo(startX - fontSize / 2, 0);
			tpl.lineTo(startX + fontSize, 0);
			tpl.stroke();
		}
	}
	
	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		if(showBarcode || showPageNo){
			PdfContentByte cb = writer.getDirectContent();
			
			//bottom尺寸
			//float textBase = document.bottom() - fontSize - PdfPrintUtil.getBottomTextBase();
			float textBase = document.bottom() / 2;
			
			//开始输出文本
			cb.beginText();
			cb.setFontAndSize(helv, fontSize);
			pageNumText = "第 " + writer.getPageNumber() + " 页  共 ";
			int pageNumTextWidth = (StringUtils.strlen(pageNumText) + 10) / 2 * fontSize;
			
			//处理条形码
			if(showBarcode){
				cb.setColorFill(BaseColor.WHITE);
				
				//页号左对齐
				if(pageNoAlign == Element.ALIGN_LEFT){
					cb.setTextMatrix(document.right() - 85, textBase);
				}
				
				//页号右对齐
				else if(pageNoAlign == Element.ALIGN_RIGHT){
					cb.setTextMatrix(document.left() + 40, textBase);
				}
				
				cb.showText("barcode");
			}
			
			//处理页号
			if(showPageNo){			
				cb.setColorFill(BaseColor.BLACK);
				
				//页号左对齐
				if(pageNoAlign == Element.ALIGN_LEFT){
					pageNumText = pageNumText.trim();
					cb.setTextMatrix(document.left(), textBase);
				}
				
				//页号右对齐
				else if(pageNoAlign == Element.ALIGN_RIGHT){
					cb.setTextMatrix(document.right() - pageNumTextWidth, textBase);
				}
				
				cb.showText(pageNumText);
			}
			
			cb.endText();
			
			//处理页号
			if(showPageNo){
				//页号左对齐
				if(pageNoAlign == Element.ALIGN_LEFT){
					cb.moveTo(document.left() + fontSize, textBase);
					cb.lineTo(document.left() + fontSize * 2.5f, textBase);
					cb.stroke();
					cb.addTemplate(tpl, document.left() + fontSize, textBase);
				}
				
				//页号右对齐
				else if(pageNoAlign == Element.ALIGN_RIGHT){
					cb.moveTo(document.right() - pageNumTextWidth + fontSize, textBase);
					cb.lineTo(document.right() - pageNumTextWidth + fontSize * 2.5f, textBase);
					cb.stroke();
					cb.addTemplate(tpl, document.right() - pageNumTextWidth, textBase);
				}
			}
		}
	}
	
	@Override
	public void onOpenDocument(PdfWriter writer, Document document) {
		try{
			tpl = writer.getDirectContent().createTemplate(200, 50);
			helv = PdfPrintUtil.getCnFSongHBaseFont();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
