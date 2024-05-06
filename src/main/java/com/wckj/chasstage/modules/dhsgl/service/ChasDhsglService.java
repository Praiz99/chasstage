package com.wckj.chasstage.modules.dhsgl.service;

import com.wckj.chasstage.api.server.imp.device.util.DevResult;

public interface ChasDhsglService {
    // 分配等候室 自动/手动
    DevResult dhsFp(String baqid, String rybh, String qyid);
    // 回到等候室 验证笔录
    DevResult dhsFpYzBl(String baqid, String rybh, String qyid);

    // 更新等候室
    DevResult dhsGx(String baqid, String dhsbh);
    DevResult dhsGxXx(String baqid, String rybh);

    // 解除等候室 逻辑删除
    DevResult dhsJc(String baqid, String rybh, boolean dxled);
    /**
     * 单独看押
     * @param baqid 办案区id
     * @param rybh 人员编号
     * @param qyid 等候室id（手动分配时传值）
     * @return
     */
    DevResult aloneAssign(String baqid, String rybh, String qyid);
}
