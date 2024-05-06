package com.wckj.chasstage.api.server.imp.device;


import com.wckj.chasstage.api.server.imp.device.internal.BaseDevService;
import com.wckj.chasstage.common.util.SYSCONSTANT;

import com.wckj.chasstage.api.server.device.IWpgService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.api.server.imp.device.util.DeviceException;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.framework.core.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class IWpgServiceImpl extends BaseDevService implements IWpgService {
    private static final Logger log = Logger.getLogger(IWpgServiceImpl.class);

    @Override
    public DevResult fpCwg(String baqid, String boxtype) {
        if(wpgOper == null){
            init();
        }
        log.debug(String.format("预分配储物柜 办案区{%s} 类型{%s}",baqid,boxtype));
        //随身物品柜
        DevResult wr= wpgOper.fpCwg(baqid, boxtype,"1");
        DevResult result = new DevResult();
        result.setCode(wr.getCode());
        result.setMessage(wr.getMessage());
        result.setData(wr.getData());
        return result;
    }
    @Override
    public DevResult fpSjg(String baqid, String boxtype){
        if(wpgOper == null){
            init();
        }
        log.debug(String.format("预分配手机柜 办案区{%s} 类型{%s}",baqid,boxtype));
        //智能手机柜
        DevResult wr=  wpgOper.fpCwg(baqid, boxtype,"40");
        DevResult result = new DevResult();
        result.setCode(wr.getCode());
        result.setMessage(wr.getMessage());
        result.setData(wr.getData());
        return result;
    }

    @Override
    public DevResult openCwg(String baqid, String rybh) {
        if(chasRyjlService == null){
            init();
        }
        DevResult wr = new DevResult();
        String msg;
        try {
            ChasRyjl chasRyj = chasRyjlService.findRyjlByRybh(baqid, rybh);
            String boxbh = chasRyj.getCwgBh();
            if (StringUtils.isEmpty(boxbh)
                    || SYSCONSTANT.BOX_TYPE_W.equals(boxbh)) {
                return notUseCwg(baqid,rybh,boxbh);
            }
            wr = wpgOper.openCwg(baqid, rybh, boxbh,"1");
        } catch (DeviceException e) {
            msg = String.format("办案区:%s 人员编号:%s 打开储物柜异常:%s", baqid, rybh,
                    e.getMessage());
            wr.setCode(0);
            wr.setMessage(msg);
            log.error("cwgOpen:", e);
        }
        return wr;
    }
    @Override
    public DevResult openCwg(String baqid, String rybh, String boxbh, String sbgn) {
        if(chasRyjlService == null){
            init();
        }
        DevResult wr = new DevResult();
        String msg;
        try {
            wr = wpgOper.openCwg(baqid, rybh, boxbh,sbgn);
        } catch (DeviceException e) {
            msg = String.format("办案区:%s 人员编号:%s,储物柜编号:%s 打开储物柜异常:%s", baqid, rybh,boxbh,
                    e.getMessage());
            wr.setCode(0);
            wr.setMessage(msg);
            log.error("cwgOpen:", e);
        }
        return wr;
    }

    @Override
    public DevResult openBgCwg(String baqid, String rybh, String boxbh) {
        if(chasRyjlService == null){
            init();
        }
        DevResult wr = new DevResult();
        String msg;
        try {
            wr = wpgOper.openCwgBg(baqid, rybh, boxbh,"");
        } catch (DeviceException e) {
            msg = String.format("办案区:%s 人员编号:%s,储物柜编号:%s 打开储物柜后门异常:%s", baqid, rybh,boxbh,
                    e.getMessage());
            wr.setCode(0);
            wr.setMessage(msg);
            log.error("openBgCwg:", e);
        }
        return wr;
    }

    @Override
    public DevResult jcCwg(String baqid, String rybh) {
        DevResult wr = new DevResult();
        String msg;
        try {
            ChasRyjl chasRyj = chasRyjlService.findRyjlByRybh(baqid, rybh);
            String boxbh = chasRyj.getCwgBh();
            if (StringUtils.isEmpty(boxbh)
                    || SYSCONSTANT.BOX_TYPE_W.equals(boxbh)) {
               return notUseCwg(baqid,rybh,boxbh);
            }

            wr = wpgOper.jcCwg(baqid, rybh, boxbh,"1");
        } catch (DeviceException e) {
            msg = String.format("办案区:%s 人员编号:%s 解除储物柜异常:%s", baqid, rybh,
                    e.getMessage());
            log.error(msg);
            wr.setCode(0);
            wr.setMessage(msg);
            log.error("jcCwg:", e);
        }
        return wr;
    }

    @Override
    public DevResult jcSjg(String baqid, String rybh) {
        DevResult wr = new DevResult();
        String msg;
        try {
            ChasRyjl chasRyj = chasRyjlService.findRyjlByRybh(baqid, rybh);
            String boxbh = chasRyj.getSjgBh();
            if (StringUtils.isEmpty(boxbh)
                    || SYSCONSTANT.BOX_TYPE_W.equals(boxbh)) {
                return notUseCwg(baqid,rybh,boxbh);
            }

            wr = wpgOper.jcCwg(baqid, rybh, boxbh,"40");
        } catch (DeviceException e) {
            msg = String.format("办案区:%s 人员编号:%s 解除手机柜异常:%s", baqid, rybh,
                    e.getMessage());
            log.error(msg);
            wr.setCode(0);
            wr.setMessage(msg);
            log.error("jcSjg:", e);
        }
        return wr;
    }
    @Override
    public DevResult jcZng(String baqid, String rybh,String boxbh){
        DevResult wr = new DevResult();
        String msg;
        try {
            wr = wpgOper.jcCwg(baqid, rybh, boxbh,"");
        } catch (DeviceException e) {
            msg = String.format("办案区:%s 人员编号:%s 柜号:%s,解除智能柜异常:%s", baqid, rybh,
                    boxbh,e.getMessage());
            log.error(msg);
            wr.setCode(0);
            wr.setMessage(msg);
            log.error("jcZng:", e);
        }
        return wr;
    }
    @Override
    public DevResult cwgBd(String baqid, String rybh) {
        log.debug(String.format("办案区{%s}人员{%s}绑定储物柜",baqid,rybh));
        ChasRyjl chasRyj = chasRyjlService.findRyjlByRybh(baqid, rybh);
        DevResult wr=  wpgOper.cwgBd(baqid, rybh, chasRyj.getCwgBh(), chasRyj.getXm(),chasRyj.getWdbhL(),"1");
        DevResult result = new DevResult();
        result.setCode(wr.getCode());
        result.setMessage(wr.getMessage());
        result.setData(wr.getData());
        return result;
    }
    @Override
    public DevResult cwgBd(String baqid, String rybh,String cwgBh) {
        log.debug(String.format("办案区{%s}人员{%s}绑定储物柜",baqid,rybh));
        ChasRyjl chasRyj = chasRyjlService.findRyjlByRybh(baqid, rybh);
        DevResult wr=  wpgOper.cwgBd(baqid, rybh, cwgBh, chasRyj.getXm(),chasRyj.getWdbhL(),"1");
        DevResult result = new DevResult();
        result.setCode(wr.getCode());
        result.setMessage(wr.getMessage());
        result.setData(wr.getData());
        return result;
    }
    @Override
    public DevResult sjgBd(String baqid, String rybh) {
        log.debug(String.format("办案区{%s}人员{%s}绑定手机柜",baqid,rybh));
        ChasRyjl chasRyj = chasRyjlService.findRyjlByRybh(baqid, rybh);
        DevResult wr=  wpgOper.cwgBd(baqid, rybh, chasRyj.getSjgBh(), chasRyj.getXm(),chasRyj.getWdbhL(),"40");
        DevResult result = new DevResult();
        result.setCode(wr.getCode());
        result.setMessage(wr.getMessage());
        result.setData(wr.getData());
        return result;
    }
    @Override
    public DevResult sjgBd(String baqid, String rybh,String sjbh) {
        log.debug(String.format("办案区{%s}人员{%s}绑定手机柜",baqid,rybh));
        ChasRyjl chasRyj = chasRyjlService.findRyjlByRybh(baqid, rybh);
        DevResult wr=  wpgOper.cwgBd(baqid, rybh, sjbh, chasRyj.getXm(),chasRyj.getWdbhL(),"40");
        DevResult result = new DevResult();
        result.setCode(wr.getCode());
        result.setMessage(wr.getMessage());
        result.setData(wr.getData());
        return result;
    }

    //@Override
    public DevResult cwgLsOpen(String baqid, String rybh, String boxbh,String sbgn) {
        return wpgOper.openCwg(baqid, rybh, boxbh,sbgn);
    }

    private DevResult notUseCwg(String baqid,String rybh,String boxbh){
        DevResult wr = new DevResult();
        String msg = String.format("办案区:%s 人员编号:%s 未使用储物柜", baqid, rybh);
        wr.setCode(0);
        wr.setMessage("未使用储物柜");
        log.debug(msg);
        return wr;
    }
}
