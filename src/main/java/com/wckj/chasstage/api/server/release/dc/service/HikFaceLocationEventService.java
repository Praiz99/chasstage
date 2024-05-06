package com.wckj.chasstage.api.server.release.dc.service;

import com.wckj.chasstage.api.server.release.dc.dto.CameraLocationInfo;
import com.wckj.chasstage.api.server.release.dc.dto.LocationEventInfo;
import com.wckj.chasstage.api.server.release.dc.dto.TagLocationInfo;

public interface HikFaceLocationEventService {

    void dealLocationMessageWckj(String org, CameraLocationInfo content);
}
