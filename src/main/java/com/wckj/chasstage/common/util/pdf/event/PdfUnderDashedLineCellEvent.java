package com.wckj.chasstage.common.util.pdf.event;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.wckj.chasstage.common.util.pdf.PdfPrintHandler;
import com.wckj.framework.core.utils.StringUtils;

/**
 * PDF下划虚线单元格事件处理类
 * @author freechan
 *
 */
public class PdfUnderDashedLineCellEvent implements PdfPCellEvent {
	
	//起始文本
	private String startText;
	
	//下划线文本
	private String underLineText;
	
	//最小行数
	private int minRows;
	
	//底部最小高度
	private int minBottomHeight;
	
	//减去头部高度
	private float detractTopHeight;
	
	//是否第一页(第一页时才处理startText)
	private boolean isFirst = true;;

	//构造函数
	public PdfUnderDashedLineCellEvent() {
	}
	
	
	//构造函数
	public PdfUnderDashedLineCellEvent(String startText, String underLineText, int minRows,float detractTopHeight) {
		this.startText = startText;
		this.underLineText = underLineText;
		this.minRows = minRows;
		this.detractTopHeight = detractTopHeight;
	}
	
	
	public String getStartText() {
		return startText;
	}

	public void setStartText(String startText) {
		this.startText = startText;
	}

	public String getUnderLineText() {
		return underLineText;
	}

	public void setUnderLineText(String underLineText) {
		this.underLineText = underLineText;
	}

	public int getMinRows() {
		return minRows;
	}

	public void setMinRows(int minRows) {
		this.minRows = minRows;
	}

	public int getMinBottomHeight() {
		return minBottomHeight;
	}

	public void setMinBottomHeight(int minBottomHeight) {
		this.minBottomHeight = minBottomHeight;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}
	
	
	public float getDetractTopHeight() {
		return detractTopHeight;
	}

	public void setDetractTopHeight(float detractTopHeight) {
		this.detractTopHeight = detractTopHeight;
	}

	@Override
	public void cellLayout(PdfPCell cell, Rectangle position,
                           PdfContentByte[] canvases) {
		PdfContentByte contentByte = canvases[0];
		contentByte.setLineWidth(0.7f);
		contentByte.setLineDash(2, 1.5f, 0);
		
		float left = position.getLeft();
		float right = position.getRight();
		float top = position.getTop();
		float bottom = position.getBottom();
		
		//起始Y坐标
		float startY = 0;
		
		//当前X坐标
		float currentX = left;
		
		//当前Y坐标
		float currentY = top - detractTopHeight;
		
		//需要下划线的行数
		int needRowNum = minRows;
		
		//处理起始文本
		if(StringUtils.isNotEmpty(startText)){
			int startLen = StringUtils.strlen(startText) / 2 + StringUtils.strlen(startText) % 2;
			int startX = (startLen % 18) * 11;
			startY = (startLen / 18) * 22;
			currentX += startX;
			currentY = currentY - startY;
		}
		
		//需要下划线的行数
		float needRowHeight = top - bottom - startY;
		needRowNum = Math.round(needRowHeight / 15);
		if(needRowNum < minRows){
			needRowNum = minRows;
		}
		
		//循环处理下划线
		for(int i = 0; i < needRowNum; i ++){
			if(currentY < minBottomHeight){
				this.isFirst = false;
				continue;
			}
			
			//处理首页
			if(isFirst){
				contentByte.moveTo(currentX+2, currentY);
				contentByte.lineTo(right-4, currentY);
				currentX = left;
				currentY -= 15;
			}
			
			//处理跨页
			else{
				contentByte.moveTo(left, currentY);
				contentByte.lineTo(right, currentY);
				currentY -= PdfPrintHandler.underLineRowHeight;
			}
		}
		
		contentByte.stroke();
		contentByte.setLineDash(0);
	}
}
