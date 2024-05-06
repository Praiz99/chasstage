package com.wckj.chasstage.api.server.release.rest.ythcj;

import com.alibaba.fastjson.JSON;
import com.wckj.chasstage.api.def.baq.model.BaqGnpzBean;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.gnpz.entity.ChasXtGnpz;
import com.wckj.chasstage.modules.gnpz.service.ChasXtGnpzService;
import com.wckj.chasstage.modules.znpz.entity.ChasXtBaqznpz;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.jdone.modules.sys.util.SysUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

/**
 * @author wutl
 * @Title: 一体化采集
 * @Package
 * @Description:
 * @date 2021-1-712:38
 */
@Api(tags = "一体化采集跳转")
@RestController
@RequestMapping(value = "/api/rest/chasstage/ythcj/ythcjService")
public class YthcjController {
    @Autowired
    private ChasBaqryxxService chasBaqryxxService;
    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    @Autowired
    private ChasXtGnpzService gnpzService;

    @RequestMapping("/jumpChas")
    @ApiAccessNotValidate
    public ModelAndView jumpChas(String info) {
        ModelAndView model = new ModelAndView("chasstage/htgl/ythcj/jumpChas");
        String[] infoArr = info.split("_");
        String address = infoArr[0];
        //
        String chasstageYthcjurl = SysUtil.getParamValue("chasstage_ythcjurl");
        //String chasstageYthcjurl = "192.168.11.175:8080";

        if (StringUtils.isNotEmpty(chasstageYthcjurl)) {
            address = chasstageYthcjurl;
        }
        String rybh = infoArr[1];
        String baqid = chasBaqryxxService.findByRybh(rybh).getBaqid();
        ChasXtBaqznpz baqznpz = baqznpzService.findByBaqid2(baqid);
        if(Objects.isNull(baqznpz)){
            model.addObject("ythcjForm","ythcjFrom");
        }else {
            ChasXtGnpz gnpz = gnpzService.findById(baqznpz.getGnpzid());
            String ythcj = JSON.parseObject(gnpz.getGnpz(), BaqGnpzBean.class).getRyrs_ythcj();
            if(StringUtils.equals(ythcj,"0")){
                model.addObject("ythcjForm","ythcjForm");
            }else if(StringUtils.equals(ythcj,"1")){
                model.addObject("ythcjForm","ythcjFormNew");
            }
        }
        model.addObject("rybh", rybh);
        model.addObject("address", address);
        return model;
    }
}
