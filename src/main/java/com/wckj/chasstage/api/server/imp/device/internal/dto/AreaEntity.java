package com.wckj.chasstage.api.server.imp.device.internal.dto;




public class AreaEntity {

	private String id;

	private String name;

	private String org;

	private Integer peopleCountThreshold;

	private Integer stayTimeThreshold;

	private Integer orderNum;

	private String workRoom;

	private String areaType;

	private String oriId;

	private Long updateDate;

	private Long createDate;

	private String roomType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPeopleCountThreshold() {
		return peopleCountThreshold;
	}

	public void setPeopleCountThreshold(Integer peopleCountThreshold) {
		this.peopleCountThreshold = peopleCountThreshold;
	}

	public Integer getStayTimeThreshold() {
		return stayTimeThreshold;
	}

	public void setStayTimeThreshold(Integer stayTimeThreshold) {
		this.stayTimeThreshold = stayTimeThreshold;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getWorkRoom() {
		return workRoom;
	}

	public void setWorkRoom(String workRoom) {
		this.workRoom = workRoom;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getOriId() {
		return oriId;
	}

	public void setOriId(String oriId) {
		this.oriId = oriId;
	}

	public Long getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Long updateDate) {
		this.updateDate = updateDate;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

}
