package com.wckj.chasstage.api.server.device;


import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.chasstage.modules.mjzpk.entity.ChasXtMjzpk;
import com.wckj.chasstage.modules.yy.entity.ChasYwYy;

public interface IBrakeService {
    //下发人脸信息到办案区所有人脸门禁闸机
    DevResult sendRyFaceInfo(ChasBaqryxx ryxx);


    DevResult sendRyFaceInfo(ChasYwMjrq mjrq);

    DevResult sendRyFaceInfo(ChasYwFkdj fkdj);



}
