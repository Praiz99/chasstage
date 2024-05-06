package com.wckj.chasstage.api.server.imp.device.internal.dto;

public class BaseCabEntity {

	private String id;
	private String org;
	private String orgName;
	private String name;
	private String deviceType;
	private String deviceFactory;
	private String deviceFun;
	private String deviceLocation;
	private String proNo;
	private long updateDate;
	private long createDate;
	/** 箱号 */
	private String boxNo;
	/** 设备IP */
	private String ip;
	/** 设备IP */
	private String bip;
	/** 端口号 */
	private String port;
	/** 后控制板 */
	private String bpcbNo;
	/** 前控制板 */
	private String fpcbNo;
	/** 前真实箱号 */
	private String fboxNo;
	/** 后真实箱号 */
	private String bboxNo;
	/** 柜子类型 */
	private String cabType;
	/** 箱子类型 */
	private String boxType;
	/** 使用状态 */
	private String useState;
	/** 使用者 */
	private String user;
	/** 使用人ID */
	private String userId;
	/** 柜子组号 */
	private String cabGroup;
	/** 客户端Ip */
	private String clientIp;
	/** 客户端端口 */
	private String clientPort;

	public String getBoxNo() {
		return this.boxNo;
	}

	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBip() {
		return this.bip;
	}

	public void setBip(String bip) {
		this.bip = bip;
	}

	public String getPort() {
		return this.port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getBpcbNo() {
		return this.bpcbNo;
	}

	public void setBpcbNo(String bpcbNo) {
		this.bpcbNo = bpcbNo;
	}

	public String getFpcbNo() {
		return this.fpcbNo;
	}

	public void setFpcbNo(String fpcbNo) {
		this.fpcbNo = fpcbNo;
	}

	public String getFboxNo() {
		return this.fboxNo;
	}

	public void setFboxNo(String fboxNo) {
		this.fboxNo = fboxNo;
	}

	public String getBboxNo() {
		return this.bboxNo;
	}

	public void setBboxNo(String bboxNo) {
		this.bboxNo = bboxNo;
	}

	public String getCabType() {
		return this.cabType;
	}

	public void setCabType(String cabType) {
		this.cabType = cabType;
	}

	public String getBoxType() {
		return this.boxType;
	}

	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}

	public String getUseState() {
		return this.useState;
	}

	public void setUseState(String useState) {
		this.useState = useState;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCabGroup() {
		return this.cabGroup;
	}

	public void setCabGroup(String cabGroup) {
		this.cabGroup = cabGroup;
	}

	public String getClientIp() {
		return this.clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getClientPort() {
		return this.clientPort;
	}

	public void setClientPort(String clientPort) {
		this.clientPort = clientPort;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceFactory() {
		return deviceFactory;
	}

	public void setDeviceFactory(String deviceFactory) {
		this.deviceFactory = deviceFactory;
	}

	public String getDeviceFun() {
		return deviceFun;
	}

	public void setDeviceFun(String deviceFun) {
		this.deviceFun = deviceFun;
	}

	public String getDeviceLocation() {
		return deviceLocation;
	}

	public void setDeviceLocation(String deviceLocation) {
		this.deviceLocation = deviceLocation;
	}

	public String getProNo() {
		return proNo;
	}

	public void setProNo(String proNo) {
		this.proNo = proNo;
	}

	public long getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(long updateDate) {
		this.updateDate = updateDate;
	}

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}
}
