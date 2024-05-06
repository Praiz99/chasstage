package com.wckj.chasstage.api.server.imp.sbgl;

import com.wckj.chasstage.api.def.sbgl.model.SbglBean;
import com.wckj.chasstage.api.def.sbgl.model.SbglParam;
import com.wckj.chasstage.api.def.sbgl.model.WdParam;
import com.wckj.chasstage.api.def.sbgl.service.ApiSbglService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.MapCollectionUtil;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.wd.entity.ChasWd;
import com.wckj.chasstage.modules.wd.service.ChasWdService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiSbglServiceImp implements ApiSbglService {

    @Autowired
    private ChasWdService wdService;
    @Autowired
    private ChasSbService sbService;
    @Autowired
    private ChasRyjlService ryjlService;


    @Override
    public ApiReturnResult<String> openOrClose(String id, int dc) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();

        DevResult result = sbService.openOrClose(id, dc);
        if (result.getCode() == 1) {
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage(result.getMessage());
        } else {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(result.getMessage());
        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<String> checkWdbh(WdParam wdParam) {
        String baqid = wdParam.getBaqid();
        String wdbh = wdParam.getWdbh();
        if (StringUtils.isEmpty(wdbh)) {
            return ResultUtil.ReturnError("腕带编号不能为空!");
        }
        if (StringUtils.isEmpty(baqid)) {
            return ResultUtil.ReturnError("办案区ID不能为空!");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("baqid", baqid);
        params.put("wdbhL", wdbh);
        List<ChasWd> wdList = wdService.findList(params, null);
        if (wdList.isEmpty()) {
            return ResultUtil.ReturnError("本办案区不存在该手环!");
        }
        params.put("wdbhL", wdbh);
        params.put("ryzt", SYSCONSTANT.BAQRYDCZT_JXZ);
        params.put("isdel", SYSCONSTANT.N_I);
        List<ChasRyjl> ryjls = ryjlService.findList(params, null);
        if (!ryjls.isEmpty()) { // 该手环已存在
            return ResultUtil.ReturnError("该手环已绑定!");
        } else {
            return ResultUtil.ReturnSuccess();
        }
    }

    @Override
    public ApiReturnResult<String> getSbglApiPageData(SbglParam param) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        try {
            Map<String, Object> params = MapCollectionUtil.objectToMap(param);
            Map<String, Object> objectMap = sbService.selectAll(param.getPage(), param.getRows(), params, "lrsj desc");
            apiReturnResult.setData(objectMap);
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("设备查询数据成功");
        } catch (Exception e) {
            apiReturnResult.setCode("500");
            e.printStackTrace();
        }

        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<String> saveSbgl(SbglBean bean) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        int num = sbService.saveSbgl(bean);
        if (num == 1) {
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("设备信息保存成功");
        } else {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("设备信息保存失败");

        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<String> updateSbgl(SbglBean bean) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        int num = sbService.updateSbgl(bean);
        if (num == 1) {
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("设备信息修改成功");
        } else {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("设备信息修改失败");
        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<String> deleteSbgl(String id) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        int num = sbService.deleteSbgl(id);
        if (num == 1) {
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("设备信息删除成功");
        } else {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("设备信息删除失败");

        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<String> getSbByLxAndQy(SbglParam param) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        try {
            if(StringUtils.isEmpty(param.getSblx())&&StringUtils.isEmpty(param.getQyid())){
                return ResultUtil.ReturnError("设备类型和区域id不能为空!");
            }
            ChasSb sb = sbService.getSbByLxAndQy(param.getSblx(), param.getQyid());
            apiReturnResult.setData(sb);
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("设备查询数据成功");
        } catch (Exception e) {
            apiReturnResult.setCode("500");
            e.printStackTrace();
        }
        return apiReturnResult;
    }
}
