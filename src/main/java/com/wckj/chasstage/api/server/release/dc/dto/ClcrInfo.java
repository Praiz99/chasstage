package com.wckj.chasstage.api.server.release.dc.dto;

/**
 *
 */
public class ClcrInfo {
    private String id;
    private String org;
    private String orgName;
    private Long updateDate;
    private Long createDate;
    //private String clBrand;
    private String carType;
    //private String zrrSfzh;
    //private String zrrXm;
    //名称
    private String carName;
    //编号
    private String carNumber;
    //车牌号
    private String license;
    //是否有效
    private String islr;
    //起止有效时间
    private Long yxsjq;
    private Long yxsjz;


    //以下字段无效，仅防止json解析报错
    private String name;
    private String deviceType;
    private String deviceFactory;
    private String deviceFun;
    private String deviceLocation;
    private String proNo;
    private String useNet;
    private String netip;
    private String netport;

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

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getIslr() {
        return islr;
    }

    public void setIslr(String islr) {
        this.islr = islr;
    }

    public Long getYxsjq() {
        return yxsjq;
    }

    public void setYxsjq(Long yxsjq) {
        this.yxsjq = yxsjq;
    }

    public Long getYxsjz() {
        return yxsjz;
    }

    public void setYxsjz(Long yxsjz) {
        this.yxsjz = yxsjz;
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

    public String getUseNet() {
        return useNet;
    }

    public void setUseNet(String useNet) {
        this.useNet = useNet;
    }

    public String getNetip() {
        return netip;
    }

    public void setNetip(String netip) {
        this.netip = netip;
    }

    public String getNetport() {
        return netport;
    }

    public void setNetport(String netport) {
        this.netport = netport;
    }


//    public static void main(String[] args) throws Exception {
//        String data = "{\"id\":\"297e5e4d6c40bd1a016c46ef545a0013\",\"org\":\"8B11F52BF76F4F399055DC7FA06817BE\",\"orgName\":\"西兴办案区\",\"name\":null,\"deviceType\":null,\"deviceFactory\":null,\"deviceFun\":null,\"deviceLocation\":null,\"proNo\":\"1\",\"updateDate\":1564640555000,\"createDate\":1564558185000,\"useNet\":null,\"netip\":null,\"netport\":null,\"carName\":\"本田\",\"carType\":\"\",\"carNumber\":\"1\",\"license\":\"浙A33221\",\"islr\":\"1\",\"yxsjq\":1564471712000,\"yxsjz\":1567236521000}";
//        ClcrInfo content = JsonUtil.parse(data, ClcrInfo.class);
//        System.out.println(content.getCarName());
//    }
}
