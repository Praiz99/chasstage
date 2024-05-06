package com.wckj.chasstage.api.server.release.dc.service;


import com.wckj.chasstage.api.server.release.dc.dto.TagLocationInfo;

public interface ILSLocationEventService {

    void dealLocationMessageWckj(String org, TagLocationInfo content);

}
