package com.wckj.chasstage.modules.wpxg.service;


import com.wckj.chasstage.modules.wpxg.entity.ChasSswpxg;

import java.util.List;

public interface UnusedLockers {
    boolean isHaveUnusedLockerByThing();

    boolean isHaveUnusedLockerByPhone();

    List<ChasSswpxg> getUnusedData();

    List<ChasSswpxg> getUnusedThingData();

    List<ChasSswpxg> getUnusedPhoneData();
}
