package com.wckj.chasstage.modules.signconfig.entity;

import java.util.Date;

public class ChasXtSignConfig {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column chas_xt_signconfig.id
     *
     * @mbg.generated Sat Jan 04 15:28:59 CST 2020
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column chas_xt_signconfig.baqid
     *
     * @mbg.generated Sat Jan 04 15:28:59 CST 2020
     */
    private String baqid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column chas_xt_signconfig.text
     *
     * @mbg.generated Sat Jan 04 15:28:59 CST 2020
     */
    private String text;

    private String csqz;

    private String xbqz;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column chas_xt_signconfig.sjbq
     *
     * @mbg.generated Sat Jan 04 15:28:59 CST 2020
     */
    private Date sjbq;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column chas_xt_signconfig.id
     *
     * @return the value of chas_xt_signconfig.id
     *
     * @mbg.generated Sat Jan 04 15:28:59 CST 2020
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column chas_xt_signconfig.id
     *
     * @param id the value for chas_xt_signconfig.id
     *
     * @mbg.generated Sat Jan 04 15:28:59 CST 2020
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column chas_xt_signconfig.baqid
     *
     * @return the value of chas_xt_signconfig.baqid
     *
     * @mbg.generated Sat Jan 04 15:28:59 CST 2020
     */
    public String getBaqid() {
        return baqid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column chas_xt_signconfig.baqid
     *
     * @param baqid the value for chas_xt_signconfig.baqid
     *
     * @mbg.generated Sat Jan 04 15:28:59 CST 2020
     */
    public void setBaqid(String baqid) {
        this.baqid = baqid == null ? null : baqid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column chas_xt_signconfig.text
     *
     * @return the value of chas_xt_signconfig.text
     *
     * @mbg.generated Sat Jan 04 15:28:59 CST 2020
     */
    public String getText() {
        return text;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column chas_xt_signconfig.text
     *
     * @param text the value for chas_xt_signconfig.text
     *
     * @mbg.generated Sat Jan 04 15:28:59 CST 2020
     */
    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column chas_xt_signconfig.sjbq
     *
     * @return the value of chas_xt_signconfig.sjbq
     *
     * @mbg.generated Sat Jan 04 15:28:59 CST 2020
     */
    public Date getSjbq() {
        return sjbq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column chas_xt_signconfig.sjbq
     *
     * @param sjbq the value for chas_xt_signconfig.sjbq
     *
     * @mbg.generated Sat Jan 04 15:28:59 CST 2020
     */
    public void setSjbq(Date sjbq) {
        this.sjbq = sjbq;
    }

    public String getCsqz() {
        return csqz;
    }

    public void setCsqz(String csqz) {
        this.csqz = csqz;
    }

    public String getXbqz() {
        return xbqz;
    }

    public void setXbqz(String xbqz) {
        this.xbqz = xbqz;
    }
}