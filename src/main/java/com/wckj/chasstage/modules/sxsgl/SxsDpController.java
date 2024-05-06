package com.wckj.chasstage.modules.sxsgl;

import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: QYT
 * @Date: 2023/11/15 3:31 下午
 * @Description:审讯室大屏
 */
@Controller
@RequestMapping(value = "/loginSxsDp")
public class SxsDpController {

    final static Logger log = Logger.getLogger(SxsDpController.class);

    @Autowired
    private ChasSxsKzService sxsKzService;

    @RequestMapping("/sxsDpList")
    public String dhsDpList(HttpServletRequest request) {
        log.info("进入审讯室大屏");
        String baqid = request.getParameter("baqid");
        request.setAttribute("baqid", baqid);
        return "chasstage/htgl/bigscreen/sxs/sxsDplist";
    }

    @ResponseBody
    @RequestMapping("/getSxsryList")
    public Map<String, Object> getSxsryList(HttpServletRequest request, String baqid) {
        return sxsKzService.findSxsryList(baqid);
    }
}
