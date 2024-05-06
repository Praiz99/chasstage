package com.wckj.chasstage.api.server.release.dc.service.gj;


import com.wckj.chasstage.api.server.release.dc.dto.TagLocationInfo;

/**
 * 处理定位轨迹信息 嫌疑人、民警、访客、对讲机等
 */
public interface IRygjProcessor {
    boolean process(String baqid, TagLocationInfo content);
}
