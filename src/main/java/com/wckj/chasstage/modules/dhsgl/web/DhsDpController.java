package com.wckj.chasstage.modules.dhsgl.web;

import com.wckj.chasstage.modules.dhsgl.service.ChasDhsKzService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: QYT
 * @Date: 2023/11/14 2:19 下午
 * @Description:等候室大屏
 */
@Controller
@RequestMapping(value = "/loginDhsDp")
public class DhsDpController {

    final static Logger log = Logger.getLogger(DhsDpController.class);

    @Autowired
    private ChasDhsKzService dhsKzService;

    @RequestMapping("/dhsDpList")
    public String dhsDpList(HttpServletRequest request) {
        log.info("进入等候室大屏");
        String baqid = request.getParameter("baqid");
        request.setAttribute("baqid", baqid);
        return "chasstage/htgl/bigscreen/dhs/dhsDplist";
    }

    @ResponseBody
    @RequestMapping("/getDhsryList")
    public Map<String, Object> getDhsryList(HttpServletRequest request, String baqid) {
        return dhsKzService.findDhsryList(baqid);
    }

}
