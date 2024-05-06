package com.wckj.chasstage.api.server.imp.common;

import com.wckj.chasstage.api.def.common.service.DicService;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.dic.DicCodeObj;
import com.wckj.framework.core.dic.DicObj;
import com.wckj.framework.core.dic.RefDicObject;
import com.wckj.jdone.modules.com.dic.core.ComDicManager;
import com.wckj.jdone.modules.com.dic.entity.JdoneComDic;
import com.wckj.jdone.modules.com.dic.entity.JdoneComDicCode;
import com.wckj.jdone.modules.com.dic.entity.JdoneComDicRef;
import com.wckj.jdone.modules.com.dic.service.JdoneComDicCodeService;
import com.wckj.jdone.modules.com.dic.service.JdoneComDicRefService;
import com.wckj.jdone.modules.com.dic.service.JdoneComDicService;
import com.wckj.jdone.modules.sys.util.DicManageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {
    @Autowired
    private ComDicManager dicManager;
    @Autowired
    private JdoneComDicCodeService codeService;
    @Autowired
    private JdoneComDicRefService refService;
    @Autowired
    private JdoneComDicService dicService;

    @Override
    public ApiReturnResult<?> getDicData() {
        Map<String, Object> dicMap = new HashMap<>();

        DicObj dicObj = dicManager.getDicObj("RSYY");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dicId", dicObj.getId());
        List<JdoneComDicCode> codeObjs = codeService.findList(params, null);
        Map<String, Object> dicCodeMap = new HashMap<>();
        for (JdoneComDicCode code : codeObjs) {
            dicCodeMap.put(code.getCode(), code.getName());
        }
        dicMap.put("RSYY", dicCodeMap);

        //DAFS
        dicObj = dicManager.getDicObj("DAFS");
        params.put("dicId", dicObj.getId());
        codeObjs = codeService.findList(params, null);
        dicCodeMap = new HashMap<>();
        for (JdoneComDicCode code : codeObjs) {
            dicCodeMap.put(code.getCode(), code.getName());
        }
        dicMap.put("DAFS", dicCodeMap);

        dicObj = dicManager.getDicObj("CHAS_ZD_CASE_RYLB");
        params.put("dicId", dicObj.getId());
        codeObjs = codeService.findList(params, null);
        dicCodeMap = new HashMap<>();
        for (JdoneComDicCode code : codeObjs) {
            dicCodeMap.put(code.getCode(), code.getName());
        }
        dicMap.put("ZD_CASE_RYLB", dicCodeMap);

        JdoneComDic dic = this.dicService.findByIdOrMark("CHAS_ZD_CASE_ZJLX");
        JdoneComDicRef refDic = (JdoneComDicRef) refService.findById(dic.getId());
        RefDicObject refObj = this.buildRefDicObj(dic, refDic);
        PageDataResultSet<DicCodeObj> refDicCodePageData = DicManageHelper.getRefDicCodePageData(refObj, -1, -1);
        List<DicCodeObj> data = refDicCodePageData.getData();
        dicCodeMap = new HashMap<>();
        for (DicCodeObj code : data) {
            dicCodeMap.put(code.getCode(), code.getName());
        }
        dicMap.put("ZD_CASE_ZJLX", dicCodeMap);

        dicObj = dicManager.getDicObj("CHAS_ZD_CASE_TSQT");
        params.put("dicId", dicObj.getId());
        codeObjs = codeService.findList(params, null);
        dicCodeMap = new HashMap<>();
        for (JdoneComDicCode code : codeObjs) {
            dicCodeMap.put(code.getCode(), code.getName());
        }
        dicMap.put("CHAS_ZD_CASE_TSQT", dicCodeMap);

        dicObj = dicManager.getDicObj("CHAS_ZD_ZB_XB");
        params.put("dicId", dicObj.getId());
        codeObjs = codeService.findList(params, null);
        dicCodeMap = new HashMap<>();
        for (JdoneComDicCode code : codeObjs) {
            dicCodeMap.put(code.getCode(), code.getName());
        }
        dicMap.put("ZD_ZB_XB", dicCodeMap);


        dicObj = dicManager.getDicObj("CHAS_ZD_ZZMM");
        params.put("dicId", dicObj.getId());
        codeObjs = codeService.findList(params, null);
        dicCodeMap = new HashMap<>();
        for (JdoneComDicCode code : codeObjs) {
            dicCodeMap.put(code.getCode(), code.getName());
        }
        dicMap.put("CHAS_ZD_ZZMM", dicCodeMap);

        dicObj = dicManager.getDicObj("CHAS_ZD_ZB_XL");
        params.put("dicId", dicObj.getId());
        codeObjs = codeService.findList(params, null);
        dicCodeMap = new HashMap<>();
        for (JdoneComDicCode code : codeObjs) {
            dicCodeMap.put(code.getCode(), code.getName());
        }
        dicMap.put("CHAS_ZD_ZB_XL", dicCodeMap);

       /* dicObj = dicManager.getDicObj("ZD_CASE_XZQH");
        params.put("dicId", dicObj.getId());
        codeObjs = codeService.findList(params, null);
        dicCodeMap = new HashMap<>();
        for (JdoneComDicCode code : codeObjs) {
            dicCodeMap.put(code.getCode(), code.getName());
        }
        dicMap.put("ZD_CASE_XZQH", dicCodeMap);*/

        /*dic = this.dicService.findByIdOrMark("ZD_CASE_XZQH");
        refDic = (JdoneComDicRef) refService.findById(dic.getId());
        refObj = this.buildRefDicObj(dic, refDic);
        refDicCodePageData = DicManageHelper.getRefDicCodePageData(refObj, -1, -1);
        data = refDicCodePageData.getData();
        dicCodeMap = new HashMap<>();
        for (DicCodeObj code : data) {
            dicCodeMap.put(code.getCode(), code.getName());
        }
        dicMap.put("ZD_CASE_XZQH", dicCodeMap);*/
        /*dicObj = dicManager.getDicObj("SB_CAB_TYPE");
        params.put("dicId",dicObj.getId());
        codeObjs = codeService.findList(params,null);
        dicCodeMap = new HashMap<>();
        for(JdoneComDicCode code : codeObjs){
            dicCodeMap.put(code.getCode(),code.getName());
        }
        dicMap.put("SB_CAB_TYPE",dicCodeMap);*/
        dicObj = dicManager.getDicObj("ZD_ZB_MZ");
        params.put("dicId", dicObj.getId());
        codeObjs = codeService.findList(params, null);
        dicCodeMap = new HashMap<>();
        for (JdoneComDicCode code : codeObjs) {
            dicCodeMap.put(code.getCode(), code.getName());
        }
        dicMap.put("ZD_ZB_MZ", dicCodeMap);
        return ResultUtil.ReturnSuccess(dicMap);
    }

    private RefDicObject buildRefDicObj(JdoneComDic dic, JdoneComDicRef refDic) {
        RefDicObject result = null;
        if (refDic == null) {
            return result;
        } else {
            result = new RefDicObject();
            result.setId(dic.getId());
            result.setCatMark(dic.getCatMark());
            result.setDicDesc(dic.getDicDesc());
            result.setDicMark(dic.getDicMark());
            result.setDicName(dic.getDicName());
            result.setFcode(refDic.getCodeField());
            result.setFname(refDic.getNameField());
            result.setFfpy(refDic.getFpyField());
            result.setFspy(refDic.getSpyField());
            result.setOrders(refDic.getOrders());
            result.setFpk(refDic.getPkField());
            result.setTableMark(refDic.getTableName());
            result.setSourceMark(refDic.getSourceMark());
            result.setOrders(refDic.getOrders());
            result.setForders(refDic.getOrderFields());
            result.setFilterRule(refDic.getFilterRule());
            return result;
        }
    }
}
