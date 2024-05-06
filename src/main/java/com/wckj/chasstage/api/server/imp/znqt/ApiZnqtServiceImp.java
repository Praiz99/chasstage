package com.wckj.chasstage.api.server.imp.znqt;

import com.wckj.chasstage.api.def.znqt.model.ZnqtParam;
import com.wckj.chasstage.api.def.znqt.service.ApiZnqtService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.DataQxbsUtil;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.modules.znqt.entity.ChasZnqt;
import com.wckj.chasstage.modules.znqt.service.ChasZnqtService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.jdone.common.util.SysUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wutl
 * @Title: 智能墙体实现类
 * @Package
 * @Description:
 * @date 2020-10-20 10:03
 */
@Service
public class ApiZnqtServiceImp implements ApiZnqtService {

    @Autowired
    private ChasZnqtService znqtService;

    /**
     * 智能墙体分页查询
     *
     * @param page
     * @param rows
     * @param baqid
     * @param wzmc
     * @return
     */
    @Override
    public ApiReturnResult<Map<String, Object>> findZnqtPageData(int page, int rows, String baqid, String wzmc) {
        ApiReturnResult<Map<String, Object>> apiReturnResult = new ApiReturnResult<>();
        try {
            Map<String, Object> params = new HashMap<>();
            String userRoleId = WebContext.getSessionUser().getRoleCode();
            if (!"0101".equals(userRoleId)) {
                String orgCode = WebContext.getSessionUser().getCurrentOrgCode();
                params.put("sydwdm", orgCode);
            }
            params.put("baqid", baqid);
            params.put("wzmc", wzmc);//所在区域
            DataQxbsUtil.getSelectAll(znqtService, params);
            params.put("baqid", baqid);
            params.put("wzmc", wzmc);
            PageDataResultSet<ChasZnqt> List = znqtService.getEntityPageData(page, rows, params, "lrsj desc");
            Map<String, Object> result = new HashMap<>();
            result.put("rows", List.getData());
            result.put("total", List.getTotal());
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("获取成功！");
            apiReturnResult.setData(result);
        } catch (Exception e) {
            e.printStackTrace();
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("获取失败：" + SysUtil.getExceptionDetail(e));
        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<String> saveZnqt(ZnqtParam znqtParam) {
        ApiReturnResult<String> returnResult = new ApiReturnResult<>();
        ChasZnqt znqt = new ChasZnqt();
        SessionUser sessionUser = WebContext.getSessionUser();
        try {
            MyBeanUtils.copyBeanNotNull2Bean(znqtParam, znqt);
            znqt.setId(StringUtils.getGuid32());
            znqt.setLrrSfzh(sessionUser.getIdCard());
            znqt.setLrsj(new Date());
            znqt.setXgrSfzh(sessionUser.getIdCard());
            znqt.setXgsj(new Date());
            znqtService.save(znqt);
            returnResult.setCode("200");
            returnResult.setMessage("保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            returnResult.setCode("500");
            returnResult.setMessage("保存失败！");
        }
        return returnResult;
    }

    @Override
    public ApiReturnResult<String> updateZnqt(ZnqtParam znqtParam) {
        ApiReturnResult<String> returnResult = new ApiReturnResult<>();
        try {
            SessionUser sessionUser = WebContext.getSessionUser();
            ChasZnqt znqt = znqtService.findById(znqtParam.getId());
            if (znqt == null) {
                returnResult.setCode("500");
                returnResult.setMessage("修改失败，未找到智能墙体信息！");
            } else {
                MyBeanUtils.copyBeanNotNull2Bean(znqtParam, znqt);
                znqt.setXgrSfzh(sessionUser.getIdCard());
                znqt.setXgsj(new Date());
                znqtService.update(znqt);
                returnResult.setCode("200");
                returnResult.setMessage("修改成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnResult.setCode("500");
            returnResult.setMessage("修改失败！" + SysUtil.getExceptionDetail(e));
        }
        return returnResult;
    }

    /**
     * 打开智能墙体
     * @param baqid
     * @param qyid
     * @param sbbh
     * @return
     */
    @Override
    public ApiReturnResult<String> openZnqt(String baqid, String qyid, String sbbh) {
        ApiReturnResult<String> returnResult = new ApiReturnResult<>();
        try {
            DevResult devResult = znqtService.znqtJdq(baqid, qyid, sbbh);
            int code = devResult.getCode();
            if(1 == code){
                returnResult.setMessage("打开成功");
                returnResult.setCode("200");
            }else{
                returnResult.setMessage("打开失败：" + devResult.getMessage());
                returnResult.setCode("500");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnResult.setMessage("打开失败：" + SysUtil.getExceptionDetail(e));
            returnResult.setCode("500");
        }
        return returnResult;
    }

    /**
     * 删除智能墙体
     * @param id
     * @return
     */
    @Override
    public ApiReturnResult<String> deleteZnqt(String id) {
        ApiReturnResult<String> returnResult = new ApiReturnResult<>();
        try {
            String[] ids = id.split(",");
            for(int i = 0; i < ids.length; i++){
                String idtemp = ids[i];
                znqtService.deleteById(idtemp);
            }
            returnResult.setCode("200");
            returnResult.setMessage("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            returnResult.setCode("500");
            returnResult.setMessage("删除失败：" + SysUtil.getExceptionDetail(e));
        }
        return returnResult;
    }
}
