package com.wckj.chasstage.api.server.device;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;

/**
 * 电子水牌控制
 */
public interface IDzspService {
    /**
     * 发送异常状态的电子水牌
     * @return
     */
    DevResult sendErrorInfo(String baqid, String qyid, String msg);
    /**
     * 发送空闲状态的电子水牌
     * @return
     */
    DevResult sendIdleInfo(String baqid, String qyid);
    /**
     * 发送待审讯状态的电子水牌
     * @return
     */
    DevResult sendTrialInfo(ChasSxsKz sxsKz);
    /**
     * 发送审讯中状态的电子水牌
     * @return
     */
    DevResult sendUsingInfo(ChasSxsKz sxsKz);
    /**
     * 人员出区时刷新电子水牌显示信息
     * @param baqid 办案区id
     * @param qyid 审讯室区域id
     * @param rybh 人员编号
     * @return
     */
    DevResult refresh(String baqid, String qyid, String rybh);
}
