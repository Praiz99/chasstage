package com.wckj.chasstage.api.server.imp.device.internal.impl;


import com.wckj.chasstage.api.server.imp.device.IWpgServiceImpl;
import com.wckj.chasstage.api.server.imp.device.internal.IWpgOper;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import scala.collection.Map;
import scala.collection.immutable.Stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class IWpgOperImpl extends BaseDeviceOper implements IWpgOper {
    private static final Logger log = Logger.getLogger(IWpgOperImpl.class);
    @Override
    public DevResult fpCwg(String baqid, String boxtype, String sbgn) {
        log.info(String.format("storagePreAssign: baqid{%s}, boxtype{%s}, sbgn{%s}", baqid, boxtype, sbgn));
        return processOper(baqid, "storagePreAssignNew", new Object[] {baqid,boxtype,sbgn});
    }

    //储物柜打开接口废弃
    public DevResult openCwg(String baqid, String rybh, String boxno,String sbgn) {
        return processOper(baqid, "storageOpen", new Object[] { baqid, rybh,boxno,"1",sbgn});
    }

    @Override
    public DevResult openCwgBg(String baqid, String rybh, String boxno, String sbgn) {
        return processOper(baqid, "storageOpen", new Object[] { baqid, rybh,boxno,"2",sbgn});
    }

    @Override
    public DevResult jcCwg(String baqid, String rybh, String boxno,String sbgn) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        List<String> List = new ArrayList<>();
        map.put("org",baqid);
        map.put("recordId",rybh);
        List.add(boxno);
        List.add(sbgn);
        map.put("boxNo_deviceFun ",List);
        return processOperNew(baqid, "storageFinish",map);
    }

    @Override
    public DevResult cwgBd(String baqid, String rybh, String boxno, String xm,String wdbhH,String sbgn) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        List<String> List = new ArrayList<>();
        map.put("org",baqid);
        map.put("recordId",rybh);
        map.put("boxNo",boxno);
        List.add(xm);
        List.add(wdbhH);
        List.add(sbgn);
        map.put("name_watchNo_deviceFun",List);
        return processOperNew(baqid, "storagePut", map);
    }
}
