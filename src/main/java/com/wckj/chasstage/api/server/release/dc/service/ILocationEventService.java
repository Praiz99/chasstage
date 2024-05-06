package com.wckj.chasstage.api.server.release.dc.service;


import com.wckj.chasstage.api.server.release.dc.dto.LocationEventInfo;

public interface ILocationEventService {
    void dealWckjEvent(String org, LocationEventInfo content);

    public void heartRateEvent(String baqid,LocationEventInfo content);
}
