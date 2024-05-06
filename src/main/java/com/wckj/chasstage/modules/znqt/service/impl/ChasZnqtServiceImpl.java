package com.wckj.chasstage.modules.znqt.service.impl;


import com.wckj.chasstage.api.server.device.IJdqService;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.znqt.dao.ChasZnqtMapper;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.znqt.entity.ChasZnqt;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.znqt.service.ChasZnqtService;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChasZnqtServiceImpl extends BaseService<ChasZnqtMapper, ChasZnqt> implements ChasZnqtService {
    protected Logger log = LoggerFactory.getLogger(ChasZnqtServiceImpl.class);

    @Autowired
    private ChasSbService chasSbService;
    @Autowired
    private IJdqService jdqService;
    @Override
    public Map<String, Object> saveZnqt(ChasZnqt znqt, String id) throws Exception {
        String idCard = WebContext.getSessionUser().getIdCard();
        Map<String,Object> result = new HashMap<String, Object>();
        try {
            if(StringUtils.isEmpty(id)){ //新增
                znqt.setLrrSfzh(idCard);
                znqt.setId(StringUtils.getGuid32());
                save(znqt);
                result.put("success", true);
                result.put("msg", "保存成功!");
            }else{//修改
                znqt.setXgrSfzh(idCard);
                ChasZnqt chasZnqt = findById(id);
                MyBeanUtils.copyBeanNotNull2Bean(znqt, chasZnqt);
                update(chasZnqt);
                result.put("success", true);
                result.put("msg", "修改成功!");
            }
        } catch (Exception e) {
            log.error("saveZnqt:",e);
            result.put("success", false);
            result.put("msg", "系统异常:"+e.getMessage());
            throw e;
        }
        return result;
    }

    @Override
    public DevResult znqtJdq(String baqid, String qyid, String... sbbh) {
        // 打开智能墙体
        log.debug(String.format("办案区：%s 区域id:%s 墙体设备数量:%d", baqid, qyid,
                sbbh.length));
        DevResult wr = new DevResult();
        // 2000取值与配置参数
        // 打开所有
        if (StringUtils.isNotEmpty(qyid)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("baqid", baqid);
            map.put("qyid", qyid);
            map.put("sblx", SYSCONSTANT.SBLX_JDQ);
            map.put("sbgn", SYSCONSTANT.SBGN_ZNQT);
            List<ChasSb> sblist = chasSbService.findByParams(map);
            if (!sblist.isEmpty()) {
                for (ChasSb sb : sblist) {
                    wr.add(sb.getSbbh(),jdqService.openRelayBytime(baqid, sb.getSbbh(), 2000));
                    wr.add(sb.getSbbh(),jdqService.openOrClose(baqid,SYSCONSTANT.ON, sb.getSbbh()));
                    log.debug(String.format(
                            "办案区：{} 区域ID:{}  墙体设备编号:{} 返回信息:{}", baqid, qyid,
                            sb.getId(), wr.getMessage()));

                }
            }
        }
        // 打开单个或多个
        if (sbbh != null&&sbbh.length > 0) {
            for (String onesbbh : sbbh) {
                wr.add(onesbbh, jdqService.openRelayBytime(baqid, onesbbh, 2000));
                wr.add(onesbbh, jdqService.openOrClose(baqid, SYSCONSTANT.ON,onesbbh));
                log.debug(String.format("办案区：%s  墙体设备编号:%s 返回信息:%s", baqid,
                        onesbbh, wr.getMessage()));
            }
        }

        wr.setCodeMessage(1, "操作成功");
        return wr;

    }
}
