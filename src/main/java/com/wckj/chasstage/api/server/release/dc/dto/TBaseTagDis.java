package com.wckj.chasstage.api.server.release.dc.dto;

import java.util.List;

public class TBaseTagDis {
    private int baseId;
    private short tagNum;
    private List<Tag> tags;

    public TBaseTagDis() {
    }

    public TBaseTagDis(int baseId, short tagNum, List<Tag> tags) {
        this.baseId = baseId;
        this.tagNum = tagNum;
        this.tags = tags;
    }

    public int getBaseId() {
        return this.baseId;
    }

    public void setBaseId(int baseId) {
        this.baseId = baseId;
    }

    public short getTagNum() {
        return this.tagNum;
    }

    public void setTagNum(short tagNum) {
        this.tagNum = tagNum;
    }

    public List<Tag> getTags() {
        return this.tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String toString() {
        return "TBaseTagDis [baseId=" + this.baseId + ", tagNum=" + this.tagNum + ", tags=" + this.tags + "]";
    }
}
