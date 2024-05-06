package com.wckj.chasstage.api.server.release.rest.common;

import com.wckj.chasstage.modules.gnpz.service.ChasXtGnpzService;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: QYT
 * @Date: 2023/8/11 9:29 上午
 * @Description:确认访问和数据库是否正常
 */
@Api(tags = "确认访问和数据库是否正常")
@RestController
@RequestMapping(value = "/rest/check/")
public class ChcekConnetionController {

    @Autowired
    private ChasXtGnpzService gnpzService;

    @ApiAccessNotValidate
    @RequestMapping("/testPage")
    public ModelAndView chcekConnetionPage() {
        String message = "项目和数据库访问正常!";
        ModelAndView model = new ModelAndView("chasstage/common/chcekConnetionPage");
        boolean access = true;
        try {
            gnpzService.findById("");
        } catch (Exception e) {
            e.printStackTrace();
            message = "数据库访问异常!";
            access = false;
        }
        model.addObject("message", message);
        model.addObject("access", access);
        return model;
    }


}
