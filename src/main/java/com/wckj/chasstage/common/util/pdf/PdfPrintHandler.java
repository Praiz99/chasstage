package com.wckj.chasstage.common.util.pdf;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.wckj.framework.core.utils.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class PdfPrintHandler {

	//表单
	//---private RdpFmFormEntity form;
	
	//主表
	//--private RdpResTableEntity mainTable;
	
	//主表业务主键值
	private String businessId;
	
	//文本数据
	private JSONObject textData;
	
	//签章数据
	private String signData;
	
	//签章JSON数组
	private JSONArray signArray;
	
	//打印内容
	private byte[] content;
	
	//是否二维码加密
	private boolean isEncryptBarcode;
	
	/** 字体-中文宋体 */
	public static String CHINESE_FONT_SONGTI = "STSongStd-Light";
	
	/** 编码-中文国标(横向) */
	public static String ENCODE_GB_H = "UniGB-UCS2-H";

	/** 编码-中文国标(纵向) */
	public static String ENCODE_GB_V = "UniGB-UCS2-V";
	
	//文书标题字体
//	public Font wsTitleFont = PdfPrintUtil.getCnFZSongHFont(22, Font.NORMAL);
//	
//	//文书子标题字体
//	public Font wsSubTitleFont = PdfPrintUtil.getCnFZSongHFont(16, Font.NORMAL);
	
	//文书号字体
	public Font wshFont = PdfPrintUtil.getCnFSongHFont(14, Font.NORMAL);
	
	//表格标题字体
	public Font tableTitleFont = PdfPrintUtil.getCnFSongHFont(16, Font.NORMAL);
	
	//表格内容字体
	public Font tableContentFont = PdfPrintUtil.getCnFSongHFont(16, Font.NORMAL);
	
	//段落内容字体
	public Font paraContentFont = PdfPrintUtil.getCnFSongHFont(16, Font.NORMAL);
	
	//清单内容字体
	public Font qdContentFont = PdfPrintUtil.getCnFSongHFont(14, Font.NORMAL);
	
	//清单表格内容字体
	public Font qdTableContentFont = PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL);
	
	//呈请表内容字体1
//	public Font cqTableContentFont1 = PdfPrintUtil.getCnSongHFont(12, Font.NORMAL);
//	
//	//呈请表内容字体
//	public Font cqTableContentFont2 = PdfPrintUtil.getCnSongHFont(12, Font.NORMAL);
//	
//	//呈请段落内容字段
//	public Font cqParaContentFont = PdfPrintUtil.getCnSongHFont(12, Font.NORMAL);
	
	/**签章定位模式-根据名称定位,值为@value*/
	public static String Sign_Position_Mode_ByName = "0";
	
	/**签章定位模式-根据标识定位,值为@value*/
	public static String Sign_Position_Mode_ByMark = "1";
	
	/**签章定位模式-根据位置定位,值为@value*/
	public static String Sign_Position_Mode_ByPosition = "2";
	
	/**下划线-每行最大字符数,值为@value*/
	public static int underLineRowMaxCharNum = 32;
	
	/**下划线-行高度,值为@value*/
	public static int underLineRowHeight = 20;
	
	/**清单-表格行最小高度,值为@value*/
	public int qdTableRowMiniHeight = 30;
	
	/**清单-每页行数,值为@value*/
	public int qdTableRowNum = 20;
	
	/**清单-子表宽度,值为@value*/
	public int qdSubTableWidth = 520;
	
	/**照片-宽度,值为@value*/
	public int photoWidth = 110;
	
	/**照片-高度,值为@value*/
	public int photoHeight = 125;
	
	/**公章-机构区分值字段名称,值为@value*/
	public String sealOrganizationIdtName = "CBDW_BH";
	
	/**公章-区域区分值字段名称,值为@value*/
	public String sealRegionIdtName = "CBQY_BH";
	
	/**公章-流程区分值字段名称,值为@value*/
	public String sealWorkFlowIdtName = "SIGNNAME";
	
	/**段落-行高,值为@value*/
	public int paraLeading = 20;
	
	/**子表是否溢出,值为@value*/
	public boolean isSubTableOverflow = false;
	
	/**是否新文档,值为@value*/
	public boolean isNewDoc = true;
	
	/**文本号文本标记,值为@value*/
	public String wshTextMark = "wsh";
	
	
	/**流程全局变量Map,值为@value*/
	public Map<String, Object> variables;
	
	

	/*------public RdpFmFormEntity getForm() {
		return form;
	}

	public void setForm(RdpFmFormEntity form) {
		this.form = form;
	}*/

	/*----public RdpResTableEntity getMainTable() {
		return mainTable;
	}

	public void setMainTable(RdpResTableEntity mainTable) {
		this.mainTable = mainTable;
	}*/

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public JSONObject getTextData() {
		return textData;
	}

	public void setTextData(JSONObject textData) {
		this.textData = textData;
	}
	
	@SuppressWarnings("unchecked")
	public void putTextData(String name, String value){
		if(StringUtils.isNotEmpty(value)){
			if(textData == null) textData = new JSONObject();
			textData.put(name, value);
		}
	}

	public String getSignData() {
		return signData;
	}

	public void setSignData(String signData) {
		this.signData = signData;
	}

	public JSONArray getSignArray() {
		return signArray;
	}

	public void setSignArray(JSONArray signArray) {
		this.signArray = signArray;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public boolean isSubTableOverflow() {
		return isSubTableOverflow;
	}

	public void setSubTableOverflow(boolean isSubTableOverflow) {
		this.isSubTableOverflow = isSubTableOverflow;
	}

	public boolean isEncryptBarcode() {
		return isEncryptBarcode;
	}

	public void setEncryptBarcode(boolean isEncryptBarcode) {
		this.isEncryptBarcode = isEncryptBarcode;
	}

	public boolean isNewDoc() {
		return isNewDoc;
	}

	public void setNewDoc(boolean isNewDoc) {
		this.isNewDoc = isNewDoc;
	}


	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}




	
	
	
	/**
	 * 获取子表最大高度
	 * @param mainTable PdfPTable 
	 * @param offset float 偏移量 
	 * @return float
	 */
	public static float getSubTableMaxHeight(PdfPTable mainTable, float offset){
		//当前主表高度
		float currentMainHeight = 0;
        for(int i = 0; i < mainTable.getRows().size(); i ++){
        	currentMainHeight += mainTable.getRowHeight(i);
        }
        
        //子表最大高度
      	float subTableMaxHeight = PageSize.A4.getHeight() - currentMainHeight - offset;
      	
      	return subTableMaxHeight;
	}

	/**
	 * 处理打印
	 * @throws Exception
	 */
	public abstract void handlePrint() throws Exception;
	
	
	
	/**
	 * 设置公章数据(根据句柄中放置的签章数组)
	 */
	@SuppressWarnings({ "unchecked"})
	public void setSealData(){
		if(getSignArray() != null){
			//去重后的签章数组
			JSONArray newJsonArray = new JSONArray();
			
			//签章去重
			Map<String, String> signMap = new LinkedHashMap<String, String>();			
			for(int i = 0; i < getSignArray().size(); i ++){
				JSONObject json = (JSONObject)getSignArray().get(i);
				String signMark = (String)json.get("mark");
				if(!signMap.containsKey(signMark)){
					newJsonArray.add(json);
					signMap.put(signMark, signMark);
				}
			}
			
			//设置签章文本数据
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("sign", newJsonArray);
			jsonObject.put("signCount", getSignArray().size());
			setSignData(jsonObject.toJSONString());
		}
	}
}
