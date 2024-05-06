package com.wckj.chasstage.common.util;

import com.wckj.chasstage.api.def.yybb.model.YybbSendHandle;
import com.wckj.chasstage.api.def.yybb.model.YybbSendParam;
import com.wckj.chasstage.modules.yybb.entity.ChasYwYybb;
import com.wckj.chasstage.modules.yybb.service.ChasYybbService;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wutl
 * @Title: 语音播报工具类
 * @Package
 * @Description:
 * @date 2020-12-2410:37
 */
public class YybbUtil {

    private static Logger logger = LoggerFactory.getLogger(YybbUtil.class);

    /**
     * 根据办案区发送语音播报
     * 根据位置输入人员姓名或民警姓名或等候室名称或审讯室名称
     * 办案区id和播报环节必填。
     * @param yybbSendParam 语音播报内容对象
     */
    public static void sendYybb(YybbSendParam yybbSendParam) {
        try {
            ChasYybbService yybbService = ServiceContext.getServiceByClass(ChasYybbService.class);
            String ryxm = yybbSendParam.getRyxm();
            String mjxm = yybbSendParam.getMjxm();
            String dhsmc = yybbSendParam.getDhsmc();
            String sxsmc = yybbSendParam.getSxsmc();
            String baqid = yybbSendParam.getBaqid();
            if (StringUtil.isEmpty(baqid)) {
                logger.error("播报办案区id参数为空。");
                return;
            }
            if (yybbSendParam.getBbhj() == null) {
                logger.error("办案区[" + baqid + "]语音播报，播报环节参数为空。");
                return;
            }
            String bbhjName = yybbSendParam.getBbhj().getName();
            Map<String, Object> param = new HashMap<>();
            param.put("baqid", baqid);
            param.put("bbhj", yybbSendParam.getBbhj().getCode());
            param.put("sfqy", 1);
            List<ChasYwYybb> yybbList = yybbService.findList(param, "lrsj desc");
            ChasYwYybb ywYybb = null;
            if (yybbList.size() > 0) {
                ywYybb = yybbList.get(0);
            } else {
                logger.info("办案区[" + baqid + "]未配置播报环节【"+bbhjName+"】");
                return;
            }
            //创建播报对象
            YybbSendHandle yybbSendHandle = new YybbSendHandle(ryxm, mjxm,
                    dhsmc, sxsmc, baqid, ywYybb.getQyid(), ywYybb.getBbnr());
            //替换实际播报内容
            yybbSendHandle.replaceContent();
            logger.info("办案区[" + baqid + "]," + bbhjName + "开始发送播报：{" + yybbSendHandle.getBbnr() + "}");
            yybbSendHandle.speak();
            logger.info("办案区[" + baqid + "]," + bbhjName + "播报结束");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("播报异常", e);
        }
    }
}
