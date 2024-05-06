package com.wckj.chasstage.api.server.release.dc.service;


import com.wckj.chasstage.api.server.release.dc.dto.ClcrInfo;

public interface IVehicleService {
    void dealVehicleMessage(String org, ClcrInfo content);
}
