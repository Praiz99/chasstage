package com.wckj.chasstage.common.util.pdf.event;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;

public class PdfObliqueLineCellEvent implements PdfPCellEvent {
	
	//单元格矩形
	private Rectangle rect;
	
	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	//构造函数
	public PdfObliqueLineCellEvent() {
	}
	
	@Override
	public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
		this.rect = position;
	}
}
