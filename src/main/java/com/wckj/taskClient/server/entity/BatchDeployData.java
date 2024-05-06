package com.wckj.taskClient.server.entity;

/**
 * batch任务配置信息
 * @author xutaotao
 *
 */
public class BatchDeployData {
	private String sourceType;
	private String dbUserName;
	private String dbUserPswd;
	private String dbType;
	private String httpUrl;
	private String conInfo;
	private String inputFieldStr;
	private String outputFieldStr;
	private String paramNameStr;
	private String operatorStr;
	private String paramValueStr;
	private String tableName;
	private String logoField;
	private String updateField;
	private String lastExecuteTime;
	
	
	
	public String getLastExecuteTime() {
		return lastExecuteTime;
	}
	public void setLastExecuteTime(String lastExecuteTime) {
		this.lastExecuteTime = lastExecuteTime;
	}
	public String getUpdateField() {
		return updateField;
	}
	public void setUpdateField(String updateField) {
		this.updateField = updateField;
	}
	public String getLogoField() {
		return logoField;
	}
	public void setLogoField(String logoField) {
		this.logoField = logoField;
	}
	public String getParamNameStr() {
		return paramNameStr;
	}
	public void setParamNameStr(String paramNameStr) {
		this.paramNameStr = paramNameStr;
	}
	public String getOperatorStr() {
		return operatorStr;
	}
	public void setOperatorStr(String operatorStr) {
		this.operatorStr = operatorStr;
	}
	public String getParamValueStr() {
		return paramValueStr;
	}
	public void setParamValueStr(String paramValueStr) {
		this.paramValueStr = paramValueStr;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getDbUserName() {
		return dbUserName;
	}
	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}
	public String getDbUserPswd() {
		return dbUserPswd;
	}
	public void setDbUserPswd(String dbUserPswd) {
		this.dbUserPswd = dbUserPswd;
	}
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public String getHttpUrl() {
		return httpUrl;
	}
	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}
	public String getConInfo() {
		return conInfo;
	}
	public void setConInfo(String conInfo) {
		this.conInfo = conInfo;
	}
	public String getInputFieldStr() {
		return inputFieldStr;
	}
	public void setInputFieldStr(String inputFieldStr) {
		this.inputFieldStr = inputFieldStr;
	}
	public String getOutputFieldStr() {
		return outputFieldStr;
	}
	public void setOutputFieldStr(String outputFieldStr) {
		this.outputFieldStr = outputFieldStr;
	}
	
	

}
