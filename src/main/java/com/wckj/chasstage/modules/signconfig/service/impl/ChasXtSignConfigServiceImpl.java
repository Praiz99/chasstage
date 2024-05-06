package com.wckj.chasstage.modules.signconfig.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import com.alibaba.fastjson.JSONObject;
import com.wckj.chasstage.api.def.baq.model.SignConfigBean;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.modules.signconfig.dao.ChasXtSignConfigMapper;
import com.wckj.chasstage.modules.signconfig.entity.ChasXtSignConfig;
import com.wckj.chasstage.modules.signconfig.service.ChasXtSignConfigService;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChasXtSignConfigServiceImpl extends BaseService<ChasXtSignConfigMapper, ChasXtSignConfig> implements ChasXtSignConfigService {
    @Override
    public ChasXtSignConfig findByBaqid(String baqid) {
        return baseDao.findByBaqid(baqid);
    }

    @Override
    public void saveSignConfig(SignConfigBean bean) throws Exception {
        if (StringUtils.isNotEmpty(bean.getId())) {
            ChasXtSignConfig signConfig = findById(bean.getId());
            MyBeanUtils.copyBeanNotNull2Bean(bean,signConfig);
            update(signConfig);
        } else {
            ChasXtSignConfig signConfig = new ChasXtSignConfig();
            MyBeanUtils.copyBeanNotNull2Bean(bean,signConfig);
            signConfig.setId(StringUtils.getGuid32());
            save(signConfig);
        }
    }

    @Override
    public Map<?, ?> getSignConfigPageData(int page, int rows, Map<String, Object> params, String order) {
        Map<String,Object> result = new HashMap<>();
        PageDataResultSet<ChasXtSignConfig> list = getEntityPageData(page,rows,params,order);
        result.put("total",list.getTotal());
        result.put("rows",list.getData());
        return result;
    }

    @Override
    public boolean xgqzlx(String id, String[] checkedCities) {
        ChasXtSignConfig config = findById(id);
        String all = "{\n" +
                "      \"办案民警签字\": \"bamj:0\",\n" +
                "      \"办案民警(协办)签字\": \"police_xb:1\",\n" +
                "      \"入区管理员签字\":\"jbqkgly:2\" ,\n" +
                "      \"检查民警签字\":\"jcmj:3\" ,\n" +
                "      \"检查民警(协办)签字\":\"checkmj_xb:4\" ,\n" +
                "      \"见证人签字\":\"jzr:5\" ,\n" +
                "      \"被检查人签字\":\"bjcr:6\",\n" +
                "      \"被检查人员捺印\":\"bechecked:7\" ,\n" +
                "      \"办案人员签字\":\"bary:8\" ,\n" +
                "      \"办案人员(协办)签字\":\"casehandler_xb:9\" ,\n" +
                "      \"随身财物管理员签字\":\"sscwgly:10\" ,\n" +
                "      \"涉案人员签字\":\"sary:11\" ,\n" +
                "      \"涉案人员捺印\": \"involved:12\",\n" +
                "      \"领取人签名\": \"lqr:13\",\n" +
                "      \"领取人捺印\":\"recipients:14\" ,\n" +
                "      \"出所管理员签字\":\"csgly:15\" ,\n" +
                "      \"出所管理员(协办)签字\": \"csmanager_xb:16\"\n" +
                "    }";
        //{ "办案民警签字", "入区管理员签字", "检查民警签字", "见证人签字","被检查人签字","被检查人员捺印", "办案人员签字",
        //            "随身财物管理员签字","涉案人员签字","涉案人员捺印","领取人签名","领取人捺印","出所管理员签字"}
        if("0".equals(config.getXbqz())){
            all = "{\n" +
                    "      \"办案民警签字\": \"bamj:0\",\n" +
                    "      \"入区管理员签字\":\"jbqkgly:1\" ,\n" +
                    "      \"检查民警签字\":\"jcmj:2\" ,\n" +
                    "      \"见证人签字\":\"jzr:3\" ,\n" +
                    "      \"被检查人签字\":\"bjcr:4\",\n" +
                    "      \"被检查人员捺印\":\"bechecked:5\" ,\n" +
                    "      \"办案人员签字\":\"bary:6\" ,\n" +
                    "      \"随身财物管理员签字\":\"sscwgly:7\" ,\n" +
                    "      \"涉案人员签字\":\"sary:8\" ,\n" +
                    "      \"涉案人员捺印\": \"involved:9\",\n" +
                    "      \"领取人签名\": \"lqr:10\",\n" +
                    "      \"领取人捺印\":\"recipients:11\" ,\n" +
                    "      \"出所管理员签字\":\"csgly:12\"\n" +
                    "    }";
        }
        List<String> czlist = new ArrayList<>();
        if(config != null){
            String text = config.getText();
            Map map = JSONObject.parseObject(text, Map.class);
            List<Map<String, Object>> merge = (List<Map<String, Object>>) map.get("merge");
            Map<String,Object> only1 = (Map<String, Object>) map.get("only");
            ConcurrentHashMap only = new ConcurrentHashMap();
            only.putAll(only1);
            for (Map<String, Object> objectMap : merge) {
                ConcurrentHashMap mergeC = new ConcurrentHashMap();
                mergeC.putAll(objectMap);
                for (Object o : mergeC.keySet()) {
                    String key = (String) o;
                    boolean b = true;
                    for (String value : checkedCities) {
                        if(value.equals(objectMap.get(key))){
                            czlist.add(value);
                            b = false;
                            break;
                        }
                    }
                    if(b){
                        objectMap.remove(key);
                    }
                }
            }
            for (Object o : only.keySet()) {
                String key = (String) o;
                boolean b = true;
                for (String value : checkedCities) {
                    if(value.equals(only.get(key))){
                        czlist.add(value);
                        b = false;
                        break;
                    }
                }
                if(b){
                    only.remove(key);
                }
            }
            List<String> xzlist = new ArrayList<>(Arrays.asList(checkedCities));
            xzlist.removeAll(czlist);
            if(xzlist.size() > 0){
                Map map1 = JSONObject.parseObject(all, Map.class);
                for (String s : xzlist) {
                    String key = (String) map1.get(s);
                    only.put(key,s);
                }
            }
            map.put("merge",merge);
            map.put("only",only);
            String s = JSONObject.toJSONString(map);
            config.setText(s);
            update(config);
        }
        return true;
    }
}
