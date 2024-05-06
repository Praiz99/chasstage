package com.wckj.chasstage.api.server.imp.yybb;

import com.wckj.chasstage.api.def.yybb.model.YybbParam;
import com.wckj.chasstage.api.def.yybb.model.YybbSendHandle;
import com.wckj.chasstage.api.def.yybb.model.YybbSendParam;
import com.wckj.chasstage.api.def.yybb.model.YyhjEnue;
import com.wckj.chasstage.api.def.yybb.service.ApiYybbService;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.yybb.dao.ChasYwYybbMapper;
import com.wckj.chasstage.modules.yybb.entity.ChasYwYybb;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.dic.DicUtil;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wutl
 * @Title: 语音播报实现类
 * @Package
 * @Description:
 * @date 2020-12-2311:58
 */
@Service
public class ApiYybbServiceImpl implements ApiYybbService {

    private static Logger logger = LoggerFactory.getLogger(ApiYybbServiceImpl.class);

    @Autowired
    private ChasYwYybbMapper ywYybbMapper;
    @Autowired
    private ChasBaqService baqService;


    @Override
    public ApiReturnResult<String> save(YybbParam yybbParam) {
        ApiReturnResult<String> returnResult = new ApiReturnResult<>();
        ChasYwYybb ywYybb = new ChasYwYybb();
        try {
            MyBeanUtils.copyBeanNotNull2Bean(yybbParam, ywYybb);
            ywYybb.setId(StringUtils.getGuid32());
            ywYybb.setIsdel(0);
            ywYybb.setLrsj(new Date());
            ywYybbMapper.insert(ywYybb);
            returnResult.setCode("200");
            returnResult.setMessage("保存成功");
            return returnResult;
        } catch (Exception e) {
            returnResult.setCode("500");
            returnResult.setMessage("语音播报保存异常" + e.getMessage());
            return returnResult;
        }
    }

    @Override
    public ApiReturnResult<String> update(YybbParam yybbParam) {
        ApiReturnResult<String> returnResult = new ApiReturnResult<>();
        String id = yybbParam.getId();
        ChasYwYybb ywYybb = ywYybbMapper.selectByPrimaryKey(id);
        try {
            MyBeanUtils.copyBeanNotNull2Bean(yybbParam, ywYybb);
            ywYybb.setXgsj(new Date());
            ywYybbMapper.updateByPrimaryKey(ywYybb);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("语音播报修改异常", e);
            returnResult.setCode("500");
            returnResult.setMessage("语音播报修改异常" + e.getMessage());
            return returnResult;
        }
        returnResult.setCode("200");
        returnResult.setMessage("修改成功");
        return returnResult;
    }

    @Override
    public ApiReturnResult<String> delete(String id) {
        ApiReturnResult<String> returnResult = new ApiReturnResult<>();
        String[] idArr = id.split(",");
        for (int i = 0; i < idArr.length; i++) {
            String delId = idArr[i];
            ywYybbMapper.deleteByPrimaryKey(delId);
        }
        returnResult.setCode("200");
        returnResult.setMessage("删除成功");
        return returnResult;
    }

    @Override
    public ApiReturnResult<?> findList(int page, int rows, YybbParam yybbParam, String order) {
        ApiReturnResult<Map<String, Object>> returnResult = new ApiReturnResult<>();
        SessionUser sessionUser = WebContext.getSessionUser();
        List<ChasBaq> baqList = baqService.getBaqByOrgCode(sessionUser.getCurrentOrgCode());
        yybbParam.setBaqidList(baqList);
        Map<String, Object> param = new HashMap<>();
        param.put("baqid", yybbParam.getBaqid());
        param.put("bbhj", yybbParam.getBbhj());
        param.put("sfqy", yybbParam.getSfqy());
        param.put("qyid", yybbParam.getQyid());
        String baqids = null;
        if (baqList.size() > 0) {
            baqids = baqList.stream().map(baq -> "'" + baq.getId() + "'").collect(Collectors.joining(","));
        }
        param.put("baqidList", baqids);
        MybatisPageDataResultSet<ChasYwYybb> yybbSet = ywYybbMapper.selectAll(page, rows, param, order);
        List<ChasYwYybb> data = yybbSet.getData();
        List<Map<String, Object>> translate = DicUtil.translate(data, new String[]{"CHASSTAGE_YYBBSJ"}, new String[]{"bbhj"});
        returnResult.setCode("200");
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("total", yybbSet.getTotal());
        dataMap.put("rows", translate);
        returnResult.setData(dataMap);
        return returnResult;
    }
}
