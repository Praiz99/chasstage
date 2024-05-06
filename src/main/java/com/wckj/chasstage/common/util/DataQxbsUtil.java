package com.wckj.chasstage.common.util;


import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.IBaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.modules.sys.util.SysUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class DataQxbsUtil {
	private static final Logger log = LoggerFactory.getLogger(DataQxbsUtil.class);
	public static void getSelectAll(IBaseService<?, ?> service, Map<String, Object> param) {
		getQxbsByDaoName(service, param, "selectAll");
	}

	public static void getQxbsByDaoName(IBaseService<?, ?> service, Map<String, Object> param, String daoname) {
		SessionUser user = WebContext.getSessionUser();
		if(user != null){
            ChasBaqService baqService = ServiceContext.getServiceByClass(ChasBaqService.class);
            String zrBaqid = baqService.getZrBaqid();
            String rangeType = SysUtil.getDataRangeType(service.getSqlId(daoname),"");
            param.put("qxbs", rangeType);
            param.put("orgSysCode", buildOrgCode(user.getCurrentOrgSysCode(),rangeType));
            param.put("idCard", user.getIdCard());
            String baqid = (String) param.get("baqid");
            if (StringUtils.isEmpty(baqid)) {
                param.put("baqid", zrBaqid);
            }
            log.info("列表查询数据权限标识:org:" + user.getCurrentOrgSysCode() + ",userId:" + user.getIdCard() + ",qxbs:" + rangeType + ",roleId:" +
                    user.getCurrentUserRoleId() + ",roleName:" + user.getRoleName() + ",roleCode:" + user.getRoleCode() + ",baqid:" + param.get("baqid"));
        }
	}

    public static String buildOrgCode(String org,String rangeType) {
        String code = "";
        if(StringUtils.equals(rangeType, SYSCONSTANT.PROVINCE)){
            code = org.substring(0,2);
        }else if(StringUtils.equals(rangeType, SYSCONSTANT.CITY)){
            code = org.substring(0,4);
        }else if(StringUtils.equals(rangeType, SYSCONSTANT.REG)){
            code = org.substring(0,6);
        }else if(StringUtils.equals(rangeType, SYSCONSTANT.ORG)){
            code = org.substring(0,8);
        }else if(StringUtils.equals(rangeType, SYSCONSTANT.ADMIN)){
            return code;
        }else{
            code = org;
        }
        return code+"%";
    }
}
