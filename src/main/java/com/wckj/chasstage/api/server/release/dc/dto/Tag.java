package com.wckj.chasstage.api.server.release.dc.dto;

public class Tag {
    private int tagId;
    private int dis;
    private Long timeDis;

    public Tag() {
    }

    public Tag(int tagId, int dis, Long timeDis) {
        this.tagId = tagId;
        this.dis = dis;
        this.timeDis = timeDis;
    }

    public int getTagId() {
        return this.tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public float getDis() {
        return (float)this.dis;
    }

    public void setDis(int dis) {
        this.dis = dis;
    }

    public Long getTimeDis() {
        return this.timeDis;
    }

    public void setTimeDis(Long timeDis) {
        this.timeDis = timeDis;
    }

    public String toString() {
        return "Tag [tagId=" + this.tagId + ", dis=" + this.dis + ", timeDis=" + this.timeDis + "]";
    }
}
