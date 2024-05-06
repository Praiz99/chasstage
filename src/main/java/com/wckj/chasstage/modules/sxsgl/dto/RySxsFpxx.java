package com.wckj.chasstage.modules.sxsgl.dto;

import java.util.Objects;

public class RySxsFpxx {
    String rybh;
    String content;

    public RySxsFpxx(String rybh, String content) {
        this.rybh = rybh;
        this.content = content;
    }

    public String getRybh() {
        return rybh;
    }



    public String getContent() {
        return content;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RySxsFpxx rySxsFpxx = (RySxsFpxx) o;
        return Objects.equals(rybh, rySxsFpxx.rybh);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rybh);
    }

    @Override
    public String toString() {
        return "RySxsFpxx{" +
                "rybh='" + rybh + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
