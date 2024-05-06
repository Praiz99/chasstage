package com.wckj.chasstage.api.server.imp.tjdj;

import com.wckj.chasstage.api.def.tjdj.model.TjdjBean;
import com.wckj.chasstage.api.def.tjdj.service.ApiTjdjService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.jhrw.service.ChasYwJhrwService;
import com.wckj.chasstage.modules.tjdj.entity.ChasTjdj;
import com.wckj.chasstage.modules.tjdj.service.ChasTjdjService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wutl
 * @Title: 体检登记服务
 * @Package
 * @Description:
 * @date 2020-10-2414:33
 */
@Service
public class ApiTjdjServiceImp implements ApiTjdjService {
    @Autowired
    private ChasTjdjService tjdjService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private ChasYwJhrwService jhrwService;
    /**
     * 根据人员编号获取体检登记
     * @param rybh
     * @return
     */
    @Override
    public ApiReturnResult<TjdjBean> getTjdjByRybh(String rybh) {
        ApiReturnResult<TjdjBean> returnResult = new ApiReturnResult<>();
        ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
        if (baqryxx == null) {
            returnResult.setCode("500");
            returnResult.setMessage("人员不存在");
            return returnResult;
        }
        ChasTjdj chasTjdj = tjdjService.getTjdjByRybh(rybh);
        TjdjBean tjdjBean = null;
        if(chasTjdj == null){
            tjdjBean = new TjdjBean();
            tjdjBean.setId("");
            tjdjBean.setBcjc(StringUtils.getGuid32());
            tjdjBean.setRstj(StringUtils.getGuid32());
            tjdjBean.setSfyc(null);
            tjdjBean.setYcsm("");
            tjdjBean.setRybh(baqryxx.getRybh());
            tjdjBean.setRyid(baqryxx.getId());
            tjdjBean.setXbzw(StringUtils.getGuid32());
            tjdjBean.setXyjc(StringUtils.getGuid32());
            tjdjBean.setQt("");
            tjdjBean.setXcg(StringUtils.getGuid32());
            tjdjBean.setXdt(StringUtils.getGuid32());
        }else{
            tjdjBean = new TjdjBean();
            try {
                MyBeanUtils.copyBean2Bean(tjdjBean, chasTjdj);
                if(StringUtil.isEmpty(tjdjBean.getBcjc())){
                    tjdjBean.setBcjc(StringUtils.getGuid32());
                }
                if(StringUtil.isEmpty(tjdjBean.getRstj())){
                    tjdjBean.setRstj(StringUtils.getGuid32());
                }
                if(StringUtil.isEmpty(tjdjBean.getXbzw())){
                    tjdjBean.setXbzw(StringUtils.getGuid32());
                }
                if(StringUtil.isEmpty(tjdjBean.getXyjc())){
                    tjdjBean.setXyjc(StringUtils.getGuid32());
                }
                if(StringUtil.isEmpty(tjdjBean.getXcg())){
                    tjdjBean.setXcg(StringUtils.getGuid32());
                }
                if(StringUtil.isEmpty(tjdjBean.getXdt())){
                    tjdjBean.setXdt(StringUtils.getGuid32());
                }
            } catch (Exception e) {
            }
        }
        returnResult.setCode("200");
        returnResult.setMessage("获取成功");
        returnResult.setData(tjdjBean);
        return returnResult;
    }
    /**
     * 保存体检登记
     * @param tjdjBean
     * @return
     */
    @Override
    public ApiReturnResult<String> saveTjdj(TjdjBean tjdjBean) {
        ApiReturnResult<String> apiReturnResult = new ApiReturnResult<>();
        ChasTjdj tjdj = new ChasTjdj();
        try {
            ChasBaqryxx baqryxx = baqryxxService.findByRybh(tjdjBean.getRybh());
            if(baqryxx == null){
                apiReturnResult.setMessage("该人员不存在");
                apiReturnResult.setCode("500");
                return apiReturnResult;
            }
            MyBeanUtils.copyBean2Bean(tjdj, tjdjBean);
            tjdj.setId(StringUtils.getGuid32());
            tjdj.setRyid(baqryxx.getId());
            tjdj.setRybh(baqryxx.getRybh());
            tjdjService.save(tjdj);
            if (baqryxx != null) {
                //结束体检戒护
                DevResult devResult = jhrwService.changeJhrwZt(baqryxx.getMjXm(), baqryxx.getZbdwBh(), baqryxx, SYSCONSTANT.JHRWLX_TJJH, SYSCONSTANT.JHRWZT_YZX);
                if (devResult.getCode() != 0) {
                    throw new Exception("开启戒护任务失败:" + devResult.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("保存失败：" + e.getMessage());
        }
        return apiReturnResult;
    }
    /**
     * 修改体检登记
     * @param tjdjBean
     * @return
     */
    @Override
    public ApiReturnResult<String> updateTjdj(TjdjBean tjdjBean) {
        ApiReturnResult<String> returnResult = new ApiReturnResult<>();
        ChasTjdj tjdjByRybh = tjdjService.getTjdjByRybh(tjdjBean.getRybh());
        if (tjdjByRybh == null) {
            returnResult.setMessage("该人员不存在体检信息，无法修改");
            returnResult.setCode("500");
        }else{
            try {
                MyBeanUtils.copyBean2Bean(tjdjByRybh, tjdjBean);
                this.tjdjService.update(tjdjByRybh);
            } catch (Exception e) {
                e.printStackTrace();
                returnResult.setMessage("修改失败：" + e.getMessage());
                returnResult.setCode("500");
            }
        }
        return returnResult;
    }
}
