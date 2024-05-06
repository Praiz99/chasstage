package com.wckj.chasstage.api.server.imp.device;


import com.wckj.chasstage.api.server.imp.device.internal.BaseDevService;
import com.wckj.chasstage.common.util.SYSCONSTANT;

import com.wckj.chasstage.api.server.device.IWdService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.shsng.entity.ChasSng;
import com.wckj.chasstage.modules.shsng.service.ChasShsngService;
import com.wckj.chasstage.modules.yy.entity.ChasYwYy;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IWdServiceImpl extends BaseDevService implements IWdService {

    private static final Logger log = Logger.getLogger(IWdServiceImpl.class);
    @Autowired
    private ChasShsngService chasXtSngService;
    @Override
    public DevResult wdBd(ChasRyjl chasRyj) {
        if(locationOper == null){
            init();
        }
        log.debug(String.format("IWdServiceImpl.wdBd办案区{%s}人员{%s} 姓名{%s}绑定腕带{%s}",
                chasRyj.getBaqid(), chasRyj.getRybh(),chasRyj.getXm(),chasRyj.getWdbhL()));
        DevResult result = locationOper.wdBd(chasRyj.getBaqid(),
                chasRyj.getBaqmc(), chasRyj.getWdbhL(), SYSCONSTANT.TAGTYPE_XYR,
                chasRyj.getWdbhH(), chasRyj.getXm(), chasRyj.getRybh());
        if(result.getCode() == 1){
            ChasSng sng = new ChasSng();
            sng.setZt("1");
            sng.setZgqk("2");
            sng.setKzcs2(chasRyj.getWdbhL());
            chasXtSngService.sngUpdate(sng);
        }
        return result;
    }

    public DevResult wdBd(ChasYwFkdj fkdj) {
        if(locationOper == null){
            init();
        }
        log.debug(String.format("IWdServiceImpl.wdBd办案区{%s}访客{%s} 姓名{%s}绑定腕带{%s}",
                fkdj.getBaqid(), fkdj.getRybh(),fkdj.getFkxm(),fkdj.getWdbhL()));
        //定位标签类型  民警 2 访客 4
        return locationOper.wdBd(fkdj.getBaqid(),
                fkdj.getBaqmc(), fkdj.getWdbhL(), "2",
                fkdj.getWdbhH(), fkdj.getFkxm(), fkdj.getRybh());
    }

    @Override
    public DevResult wdBd(ChasYwMjrq mjrq) {
        if(locationOper == null){
            init();
        }
        log.debug(String.format("IWdServiceImpl.wdBd办案区{%s}民警{%s} 姓名{%s}绑定腕带{%s}",
                mjrq.getBaqid(), mjrq.getRybh(),mjrq.getMjxm(),mjrq.getWdbhL()));
        //定位标签类型  民警 2 访客 4
        return locationOper.wdBd(mjrq.getBaqid(),
                mjrq.getBaqmc(), mjrq.getWdbhL(), "2",
                mjrq.getWdbhH(), mjrq.getMjxm(), mjrq.getRybh());
    }

    @Override
    public DevResult wdBd(ChasYwYy yy) {
        if(locationOper == null){
            init();
        }
        log.debug(String.format("IWdServiceImpl.wdBd办案区{%s}预约{%s} 姓名{%s}绑定腕带{%s}",
                yy.getBaqid(), yy.getRybh(),yy.getYyrxm(),yy.getShbh()));
        //定位标签类型  民警 2 访客 4
        return locationOper.wdBd(yy.getBaqid(),
                yy.getBaqmc(), yy.getShbh(), "2",
                "", yy.getYyrxm(), yy.getRybh());
    }

    @Override
    public DevResult wdJc(ChasRyjl chasRyj) {
        log.debug(String.format("IWdServiceImpl.wdJc 办案区{%s}人员{%s} 姓名{%s}解除腕带{%s}",
                chasRyj.getBaqid(), chasRyj.getRybh(),chasRyj.getXm(),chasRyj.getWdbhL()));
        return locationOper.wdJc(chasRyj.getBaqid(), chasRyj.getWdbhL(), chasRyj.getRybh());
    }

    @Override
    public DevResult wdJc(ChasYwFkdj fkdj) {
        log.debug(String.format("IWdServiceImpl.wdJc 办案区{%s}访客{%s} 姓名{%s}解除腕带{%s}",
                fkdj.getBaqid(), fkdj.getRybh(),fkdj.getFkxm(),fkdj.getWdbhL()));
        return locationOper.wdJc(fkdj.getBaqid(), fkdj.getWdbhL(), fkdj.getRybh());
    }

    @Override
    public DevResult wdJc(ChasYwMjrq mjrq) {
        log.debug(String.format("IWdServiceImpl.wdJc 办案区{%s}民警{%s} 姓名{%s}解除腕带{%s}",
                mjrq.getBaqid(), mjrq.getRybh(),mjrq.getMjxm(),mjrq.getWdbhL()));
        return locationOper.wdJc(mjrq.getBaqid(), mjrq.getWdbhL(), mjrq.getRybh());
    }

    @Override
    public DevResult wdJc(ChasYwYy yy) {
        log.debug(String.format("IWdServiceImpl.wdJc 办案区{%s}民警{%s} 姓名{%s}解除腕带{%s}",
                yy.getBaqid(), yy.getRybh(),yy.getYyrxm(),yy.getShbh()));
        return locationOper.wdJc(yy.getBaqid(), yy.getShbh(), yy.getRybh());
    }

    @Override
    public DevResult wdDdl(String baqid, ChasSb wd) {
        return locationOper.wdDdl(baqid, wd);
    }


}

