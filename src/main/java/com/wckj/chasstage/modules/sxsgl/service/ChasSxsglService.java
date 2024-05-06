package com.wckj.chasstage.modules.sxsgl.service;

import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 审讯室管理业务Service
 */
public interface ChasSxsglService {

    // 分配审讯室 自动/手动
    DevResult sxsFp(String baqid, String rybh, String qyid,String useSxsMjxm,HttpServletRequest request);

    // 更新审讯室信息
    DevResult sxsGx(String baqid, String sxsbh);

    DevResult sxsGxXx(String baqid, String rybh);

    // 解除审讯室 逻辑删除
    DevResult sxsJc(String baqid, String rybh, boolean dxled);

    // 离开审讯室
    DevResult sxsLk(String baqid, String rybh);
    // 离开审讯室 验证笔录
    DevResult sxsLkYzBl(String baqid, String rybh);

    /**
     * 关闭审讯室
     * @param id
     * @return
     */
    Map<String,Object> close(String id);
    /**
     * 打开审讯室
     * @param id
     * @return
     */
    Map<String, Object> open(String id);

    //发起审讯戒护任务
    DevResult startSXjhrw(ChasRyjl ryjl, ChasSxsKz sxsKz);
    DevResult endSXjhrw(ChasRyjl ryjl);
    String getKsqLedText(String baqid);

    /**
     * 关闭审讯室排风扇
     * @param id
     * @return
     */
    Map<String,Object> closePfs(String id);
    /**
     * 打开审讯室排风扇
     * @param id
     * @return
     */
    Map<String, Object> openPfs(String id);
}
