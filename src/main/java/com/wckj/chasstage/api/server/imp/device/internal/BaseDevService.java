package com.wckj.chasstage.api.server.imp.device.internal;


import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.api.server.imp.device.internal.*;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsKzService;
import com.wckj.chasstage.modules.qqdh.service.ChasQqyzService;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.framework.core.ServiceContext;

public class BaseDevService {

    public static IWpgOper wpgOper = ServiceContext.getServiceByClass(IWpgOper.class);

    public static ChasRyjlService chasRyjlService= ServiceContext.getServiceByClass(ChasRyjlService.class);

    public static ILocationOper locationOper= ServiceContext.getServiceByClass(ILocationOper.class);



    public static ILedOper ledOper= ServiceContext.getServiceByClass(ILedOper.class);

    public static ChasSbService chasSbService= ServiceContext.getServiceByClass(ChasSbService.class);

    public static ChasXtQyService chasQyService= ServiceContext.getServiceByClass(ChasXtQyService.class);

    public static ChasDhsKzService chasDhsKzService= ServiceContext.getServiceByClass(ChasDhsKzService.class);

    public static ChasBaqryxxService chasBaqryxxService= ServiceContext.getServiceByClass(ChasBaqryxxService.class);

    public static ChasSxsKzService chasSxsKzService= ServiceContext.getServiceByClass(ChasSxsKzService.class);


    public static IJdqOper jdqOper= ServiceContext.getServiceByClass(IJdqOper.class);

    public static ICameraOper cameraOper= ServiceContext.getServiceByClass(ICameraOper.class);
    public static ChasBaqService chasBaqService = ServiceContext.getServiceByClass(ChasBaqService.class);

    //public static ChasYjxxService chasYjxxService = ServiceContext.getServiceByClass(ChasYjxxService.class);
    public static ChasQqyzService chasQqyzService = ServiceContext.getServiceByClass(ChasQqyzService.class);
    public static IDzspOper dzspOper = ServiceContext.getServiceByClass(IDzspOper.class);
    public static IBrakeOper brakeOper = ServiceContext.getServiceByClass(IBrakeOper.class);
    public static void init(){
        wpgOper = ServiceContext.getServiceByClass(IWpgOper.class);
        chasRyjlService= ServiceContext.getServiceByClass(ChasRyjlService.class);
        locationOper= ServiceContext.getServiceByClass(ILocationOper.class);
        ledOper= ServiceContext.getServiceByClass(ILedOper.class);
        chasSbService= ServiceContext.getServiceByClass(ChasSbService.class);
        chasQyService= ServiceContext.getServiceByClass(ChasXtQyService.class);
        chasDhsKzService= ServiceContext.getServiceByClass(ChasDhsKzService.class);
        chasBaqryxxService= ServiceContext.getServiceByClass(ChasBaqryxxService.class);
        chasSxsKzService= ServiceContext.getServiceByClass(ChasSxsKzService.class);
        jdqOper= ServiceContext.getServiceByClass(IJdqOper.class);
        cameraOper= ServiceContext.getServiceByClass(ICameraOper.class);
        chasBaqService = ServiceContext.getServiceByClass(ChasBaqService.class);
        //chasYjxxService = ServiceContext.getServiceByClass(ChasYjxxService.class);
        chasQqyzService = ServiceContext.getServiceByClass(ChasQqyzService.class);
        dzspOper = ServiceContext.getServiceByClass(IDzspOper.class);
        brakeOper = ServiceContext.getServiceByClass(IBrakeOper.class);
    }
}
