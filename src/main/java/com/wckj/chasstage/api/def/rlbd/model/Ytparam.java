package com.wckj.chasstage.api.def.rlbd.model;

/**
 * @author wutl
 * @Title:
 * @Package
 * @Description:
 * @date 2021-4-19 10:29
 */
public class Ytparam {
    private String picUrl;
    private int useHikFmsInterface;
    private int total;
    private double similarity;
    private int needUrl = 1;
    private String type = "";

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getUseHikFmsInterface() {
        return useHikFmsInterface;
    }

    public void setUseHikFmsInterface(int useHikFmsInterface) {
        this.useHikFmsInterface = useHikFmsInterface;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public int getNeedUrl() {
        return needUrl;
    }

    public void setNeedUrl(int needUrl) {
        this.needUrl = needUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
