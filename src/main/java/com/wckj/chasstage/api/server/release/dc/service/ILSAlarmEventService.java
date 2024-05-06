package com.wckj.chasstage.api.server.release.dc.service;


import com.wckj.chasstage.api.server.release.dc.dto.LSLocationEventInfo;

public interface ILSAlarmEventService {
    void dealWckjEvent(String org, LSLocationEventInfo content);
}
