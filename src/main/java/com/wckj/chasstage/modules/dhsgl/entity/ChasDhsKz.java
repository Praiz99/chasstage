package com.wckj.chasstage.modules.dhsgl.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 等候室分配情况
 */
public class ChasDhsKz {
        /**
         *主键
         */
        private String id;

        /**
         *逻辑删除
         */
        private Integer isdel;

        /**
         *版本
         */
        private String dataflag;

        /**
         *录入人身份证号
         */
        private String lrrSfzh;

        /**
         *录入时间
         */
        private Date lrsj;

        /**
         *修改人身份证号
         */
        private String xgrSfzh;

        /**
         *修改时间
         */
        private Date xgsj;

        /**
         *办案区
         */
        private String baqid;

        /**
         *办案区名称
         */
        private String baqmc;

        /**
         *区域id
         */
        private String qyid;

        /**
         *人员记录id
         */
        private String ryid;

        /**
         *办案区人员姓名
         */
        private String ryxm;

        /**
         *性别
         */
        private String ryxb;

        /**
         *腕带编号 低频编号
         */
        private String wdbh;

        /**
         *开始时间
         */
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date kssj;

        /**
         *调查阶段
         */
        private String dcjd;

        /**
         *分配状态
         */
        private String fpzt;

        /**
         *案件编号
         */
        private String ajbh;

        /**
         *警情编号
         */
        private String jqbh;

        /**
         *身份证号
         */
        private String sfzh;

        /**
         *人员编号
         */
        private String rybh;


        public String getId() {
            return id;
        }


        public void setId(String id) {
            this.id = id == null ? null : id.trim();
        }


        public Integer getIsdel() {
            return isdel;
        }


        public void setIsdel(Integer isdel) {
            this.isdel = isdel;
        }


        public String getDataflag() {
            return dataflag;
        }


        public void setDataflag(String dataflag) {
            this.dataflag = dataflag == null ? null : dataflag.trim();
        }


        public String getLrrSfzh() {
            return lrrSfzh;
        }


        public void setLrrSfzh(String lrrSfzh) {
            this.lrrSfzh = lrrSfzh == null ? null : lrrSfzh.trim();
        }


        public Date getLrsj() {
            return lrsj;
        }


        public void setLrsj(Date lrsj) {
            this.lrsj = lrsj;
        }


        public String getXgrSfzh() {
            return xgrSfzh;
        }


        public void setXgrSfzh(String xgrSfzh) {
            this.xgrSfzh = xgrSfzh == null ? null : xgrSfzh.trim();
        }


        public Date getXgsj() {
            return xgsj;
        }


        public void setXgsj(Date xgsj) {
            this.xgsj = xgsj;
        }


        public String getBaqid() {
            return baqid;
        }


        public void setBaqid(String baqid) {
            this.baqid = baqid == null ? null : baqid.trim();
        }


        public String getBaqmc() {
            return baqmc;
        }


        public void setBaqmc(String baqmc) {
            this.baqmc = baqmc == null ? null : baqmc.trim();
        }


        public String getQyid() {
            return qyid;
        }


        public void setQyid(String qyid) {
            this.qyid = qyid == null ? null : qyid.trim();
        }


        public String getRyid() {
            return ryid;
        }


        public void setRyid(String ryid) {
            this.ryid = ryid == null ? null : ryid.trim();
        }


        public String getRyxm() {
            return ryxm;
        }


        public void setRyxm(String ryxm) {
            this.ryxm = ryxm == null ? null : ryxm.trim();
        }


        public String getRyxb() {
            return ryxb;
        }


        public void setRyxb(String ryxb) {
            this.ryxb = ryxb == null ? null : ryxb.trim();
        }


        public String getWdbh() {
            return wdbh;
        }


        public void setWdbh(String wdbh) {
            this.wdbh = wdbh == null ? null : wdbh.trim();
        }


        public Date getKssj() {
            return kssj;
        }


        public void setKssj(Date kssj) {
            this.kssj = kssj;
        }


        public String getDcjd() {
            return dcjd;
        }


        public void setDcjd(String dcjd) {
            this.dcjd = dcjd == null ? null : dcjd.trim();
        }


        public String getFpzt() {
            return fpzt;
        }


        public void setFpzt(String fpzt) {
            this.fpzt = fpzt == null ? null : fpzt.trim();
        }


        public String getAjbh() {
            return ajbh;
        }


        public void setAjbh(String ajbh) {
            this.ajbh = ajbh == null ? null : ajbh.trim();
        }


        public String getJqbh() {
            return jqbh;
        }


        public void setJqbh(String jqbh) {
            this.jqbh = jqbh == null ? null : jqbh.trim();
        }


        public String getSfzh() {
            return sfzh;
        }


        public void setSfzh(String sfzh) {
            this.sfzh = sfzh == null ? null : sfzh.trim();
        }


        public String getRybh() {
            return rybh;
        }

        public void setRybh(String rybh) {
            this.rybh = rybh == null ? null : rybh.trim();
        }
}
