package com.wckj.chasstage.api.server.release.dc.service;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;

public interface IRfidEventService {
    DevResult cardScan(String org, String card, String sbgn);
}
