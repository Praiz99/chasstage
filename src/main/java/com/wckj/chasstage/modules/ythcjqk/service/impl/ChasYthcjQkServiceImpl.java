package com.wckj.chasstage.modules.ythcjqk.service.impl;

import com.wckj.chasstage.api.def.qtdj.model.YthcjParam;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.pcry.entity.ChasYwPcry;
import com.wckj.chasstage.modules.pcry.service.ChasYwPcryService;
import com.wckj.chasstage.modules.ythcjqk.dao.ChasythcjQkMapper;
import com.wckj.chasstage.modules.ythcjqk.entity.ChasythcjQk;
import com.wckj.chasstage.modules.ythcjqk.service.ChasYthcjQkService;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChasYthcjQkServiceImpl extends BaseService<ChasythcjQkMapper, ChasythcjQk> implements ChasYthcjQkService {

    private static final Logger logger = LoggerFactory.getLogger(ChasYthcjQkServiceImpl.class);

    private String ZWCJ = "0",DNACJ="0",MFCJ="0",SDQK="02",NYJC="0";

    @Lazy
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private ChasYwPcryService pcryService;

    @Override
    public void saveOrUpdate(YthcjParam ythcjParam) throws Exception {
        SessionUser user = WebContext.getSessionUser();
        String fromSign = ythcjParam.getFromSign();
        ChasythcjQk qk = new ChasythcjQk();
        MyBeanUtils.copyBeanNotNull2Bean(ythcjParam,qk);
        String rybh = qk.getRybh();
        if(StringUtil.equals(fromSign, SYSCONSTANT.FROMSIGNPCRY)){
            //盘查人员
            if(StringUtil.isEmpty(qk.getId())){
                ChasYwPcry pcry = pcryService.findByRybh(qk.getRybh());
                if(pcry != null){
                    qk.setId(StringUtils.getGuid32());
                    qk.setLrrSfzh(user.getIdCard());
                    qk.setIsdel(0);
                    qk.setLrsj(new Date());
                    qk.setXgrSfzh(user.getIdCard());
                    qk.setXgsj(new Date());
                    qk.setBaqid(pcry.getBaqid());
                    qk.setBaqmc(pcry.getBaqmc());
                    qk.setRybh(pcry.getRybh());
                    qk.setRyxm(pcry.getRyxm());
                    qk.setDataFlag("");
                    save(qk);

                    if(determineQk(qk)){
                        //查询是否有办案区人员，如果有，那么就联动修改办案区人员采集状态
                        Map<String,Object> param = new HashMap<>();
                        param.put("rybh",pcry.getRybh());
                        List<ChasBaqryxx> baqryxxes = baqryxxService.findList(param,null);
                        if(!baqryxxes.isEmpty()){
                            ChasBaqryxx baqryxx = baqryxxes.get(0);
                            baqryxx.setSfythcj(1);
                            baqryxxService.update(baqryxx);
                        }

                        pcry.setYthcjzt(1);
                        pcryService.update(pcry);
                    }
                }else{
                    logger.error("新增______盘查rybh不存在:{}",rybh);
                }
            }else{
                ChasYwPcry pcry = pcryService.findByRybh(rybh);
                if(pcry != null){
                    ChasythcjQk qk_ = findById(qk.getId());
                    MyBeanUtils.copyBeanNotNull2Bean(qk,qk_);
                    update(qk_);

                    if(determineQk(qk_)){
                        //查询是否有办案区人员，如果有，那么就联动修改办案区人员采集状态
                        Map<String,Object> param = new HashMap<>();
                        param.put("rybh",qk_.getRybh());
                        List<ChasBaqryxx> baqryxxes = baqryxxService.findList(param,null);
                        if(!baqryxxes.isEmpty()){
                            ChasBaqryxx baqryxx = baqryxxes.get(0);
                            baqryxx.setSfythcj(1);
                            baqryxxService.update(baqryxx);
                        }

                        pcry.setYthcjzt(1);
                        pcryService.update(pcry);
                    }else{
                        //查询是否有办案区人员，如果有，那么就联动修改办案区人员采集状态
                        Map<String,Object> param = new HashMap<>();
                        param.put("rybh",qk_.getRybh());
                        List<ChasBaqryxx> baqryxxes = baqryxxService.findList(param,null);
                        if(!baqryxxes.isEmpty()){
                            ChasBaqryxx baqryxx = baqryxxes.get(0);
                            baqryxx.setSfythcj(0);
                            baqryxxService.update(baqryxx);
                        }

                        pcry.setYthcjzt(0);
                        pcryService.update(pcry);
                    }
                }else{
                    logger.error("修改____盘查人员编号不存在:{}",rybh);
                }
            }
        }
        if(StringUtil.equals(fromSign,SYSCONSTANT.FROMSIGNBAQRYXX)){
            //办案区人员
            if(StringUtils.isEmpty(qk.getId())||"null".equals(qk.getId())){
                ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
                if(baqryxx != null){
                    qk.setId(StringUtils.getGuid32());
                    qk.setLrrSfzh(user.getIdCard());
                    qk.setIsdel(0);
                    qk.setLrsj(new Date());
                    qk.setXgrSfzh(user.getIdCard());
                    qk.setXgsj(new Date());
                    qk.setBaqid(baqryxx.getBaqid());
                    qk.setBaqmc(baqryxx.getBaqmc());
                    qk.setRybh(baqryxx.getRybh());
                    qk.setRyxm(baqryxx.getRyxm());
                    qk.setDataFlag("");
                    save(qk);

                    if(determineQk(qk)){
                        baqryxx.setSfythcj(1);
                        baqryxxService.update(baqryxx);

                        //如果有盘查人员那么就同步信息
                        Map<String,Object> param = new HashMap<>();
                        param.put("rybh",baqryxx.getRybh());
                        List<ChasYwPcry> pcries = pcryService.findList(param,null);
                        if(!pcries.isEmpty()){
                            ChasYwPcry pcry = pcries.get(0);
                            pcry.setYthcjzt(1);
                            pcryService.update(pcry);
                        }
                    }
                }else{
                    logger.error("新增采集____办案区人员编号不存在:{}",rybh);
                }
            }else{
                ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
                if(baqryxx != null){
                    ChasythcjQk qk_ = findById(qk.getId());
                    MyBeanUtils.copyBeanNotNull2Bean(qk,qk_);
                    update(qk_);

                    if(determineQk(qk_)){
                        baqryxx.setSfythcj(1);
                        baqryxxService.update(baqryxx);
                        //如果有盘查人员那么就同步信息
                        Map<String,Object> param = new HashMap<>();
                        param.put("rybh",qk_.getRybh());
                        List<ChasYwPcry> pcries = pcryService.findList(param,null);
                        if(!pcries.isEmpty()){
                            ChasYwPcry pcry = pcries.get(0);
                            pcry.setYthcjzt(1);
                            pcryService.update(pcry);
                        }
                    }else{
                        baqryxx.setSfythcj(0);
                        baqryxxService.update(baqryxx);
                        //如果有盘查人员那么就同步信息
                        Map<String,Object> param = new HashMap<>();
                        param.put("rybh",qk_.getRybh());
                        List<ChasYwPcry> pcries = pcryService.findList(param,null);
                        if(!pcries.isEmpty()){
                            ChasYwPcry pcry = pcries.get(0);
                            pcry.setYthcjzt(0);
                            pcryService.update(pcry);
                        }
                    }
                }else{
                    logger.error("修改采集____办案区人员编号不存在:{}",rybh);
                }
            }
        }
    }

    /**
     * 一体化采集状态判断
     * @param qk 采集情况
     * @param old 旧数据
     * @return 新一体化采集状态
     */
    private Integer checkQk(ChasythcjQk qk,Integer old){
        Integer newCheck = SYSCONSTANT.Y_I;

        if(SYSCONSTANT.Y_I.equals(old)){
            //确认是否要判断之前保存数据
            //暂时不做判断
        }
        /**为true是为全部未采集*/
        boolean flag = ZWCJ.equals(qk.getZwcj()) && DNACJ.equals(qk.getDnacj())&&MFCJ.equals(qk.getMfcj()) && SDQK.equals(qk.getSdqk()) && NYJC.equals(qk.getNyjc());
        if(flag){
            newCheck = SYSCONSTANT.N_I;
        }

        return newCheck ;
    }

    private boolean determineQk (ChasythcjQk qk){
        boolean iscj = false;
        if((StringUtil.equals("0",qk.getZwcj()) && StringUtil.isNotEmpty(qk.getZwcj())) ||
                (StringUtil.equals("0",qk.getDnacj()) && StringUtil.isNotEmpty(qk.getDnacj()))
                || (!StringUtil.equals("1",qk.getSdqk()) && StringUtil.isNotEmpty(qk.getSdqk())) ||
                (StringUtil.equals("0",qk.getMfcj()) && StringUtil.isNotEmpty(qk.getMfcj())) ||
                (!StringUtil.equals("0",qk.getNyjc()) && StringUtil.isNotEmpty(qk.getNyjc()))){
            iscj = true;
        }
        return iscj;
    }
}
