package com.wckj.chasstage.common.util.pdf.event;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.wckj.chasstage.common.util.pdf.PdfPrintUtil;

/**
 * PDF下划虚线单元格事件处理类
 * @author freechan
 *
 */
public class PdfCheckBoxEvent implements PdfPCellEvent {
	
	
	private String name;
	private float leftX = 10.0f;
	private String[] labels;
	private boolean[] checkeds;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getLeftX() {
		return leftX;
	}

	public void setLeftX(float leftX) {
		this.leftX = leftX;
	}

	public String[] getLabels() {
		return labels;
	}

	public void setLabels(String[] labels) {
		this.labels = labels;
	}

	public boolean[] getCheckeds() {
		return checkeds;
	}

	public void setCheckeds(boolean[] checkeds) {
		this.checkeds = checkeds;
	}


	public PdfCheckBoxEvent() {
	}
	
	public PdfCheckBoxEvent(String name, String[] labels) {
		this.name = name;
		this.labels = labels;
	}

	public PdfCheckBoxEvent(String name,float leftX,String[] labels) {
		this.name = name;
		this.leftX = leftX;
		this.labels = labels;
	}

	public PdfCheckBoxEvent(String name, float leftX, String[] labels, boolean[] checkeds) {
		this.name = name;
		this.leftX = leftX;
		this.labels = labels;
		this.checkeds = checkeds;
	}

	
	@Override
	public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
		
		PdfContentByte contentByte = canvases[0];
		PdfWriter writer = contentByte.getPdfWriter();
		
		float left = position.getLeft();
		float top = position.getTop();
		float bottom = position.getBottom();
		
		//float ao = (right - left)/2;
		float bo = (top - bottom)/2;
		
	    float x = left + leftX;
	    float y = bottom + bo + 5;
	    
	    
	    try {
	    	
	    	Rectangle rect = null;
	    	RadioCheckField checkbox = null;
	    	PdfContentByte canvas = writer.getDirectContent();
	    	PdfAppearance[] onOff = new PdfAppearance[2];
	    	
	        onOff[0] = canvas.createAppearance(10, 10);
	        onOff[0].rectangle(0, 0, 9, 9);
	        onOff[0].stroke();
	        
	        onOff[1] = canvas.createAppearance(10, 10);
	        onOff[1].setColorFill(BaseColor.WHITE);
	        onOff[1].rectangle(0, 0, 9, 9);
	        onOff[1].fillStroke();
	        onOff[1].moveTo(1, 5);
	        onOff[1].lineTo(4, 2);
	        onOff[1].lineTo(9, 9);
	        onOff[1].stroke();
	    	
	        float tw = 0.0f;
	    	for(int i = 0; i < labels.length; i++){
	    		final String label = labels[i];
	    		final boolean checked = checkeds != null && checkeds.length > i ? checkeds[i] : false;
	    		
	    		float startX  = x + tw;
	    		rect = new Rectangle(startX, y, startX+10, y-10);
	    		checkbox = new RadioCheckField(writer, rect, name, "Yes");
	    		
	    		PdfFormField field = checkbox.getCheckField();
		        field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, Boolean.FALSE.toString(), onOff[0]);
		        field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, Boolean.TRUE.toString(), onOff[1]);
				field.setAppearanceState(Boolean.toString(checked));
				writer.addAnnotation(field);
				
				ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
				        new Phrase(label, PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL)), startX + 10, y-10, 0);
				
				//已占用宽度
				float wd = label.length() * 13 + 15;
				tw += wd;
	    	}
	    	
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
}
