package com.wckj.chasstage.api.server.device;



import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.yy.entity.ChasYwYy;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;

public interface IWdService {
    //手环、胸卡绑定
    DevResult wdBd(ChasRyjl chasRyj);
    DevResult wdBd(ChasYwFkdj fkdj);
    DevResult wdBd(ChasYwMjrq mjrq);
    DevResult wdBd(ChasYwYy yy);
    //DevResult wdBd(ChasYwDjj djj);
    //手环、胸卡解绑
    DevResult wdJc(ChasRyjl chasRyj);
    DevResult wdJc(ChasYwFkdj fkdj);
    DevResult wdJc(ChasYwMjrq mjrq);
    DevResult wdJc(ChasYwYy yy);
    //DevResult wdJc(ChasYwDjj djj);
    //低电量检测
    DevResult wdDdl(String baqid, ChasSb wd);
}
