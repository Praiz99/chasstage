package com.wckj.chasstage.common.util;

import com.wckj.chasstage.modules.signconfig.entity.ChasXtSignConfig;
import com.wckj.chasstage.modules.signconfig.service.ChasXtSignConfigService;
import com.wckj.chasstage.modules.signconfig.service.impl.ChasXtSignConfigServiceImpl;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignbsUtil {
    protected static Logger log = LoggerFactory.getLogger(SignbsUtil.class);
    private static String[] qmny = {"bamj","jbqkgly","jcmj","jzr","bjcr","bechecked","bary","sscwgly","sary","involved","lqr","recipients","csgly"};
    private static String[] qmts = { "办案民警签字", "入区管理员签字", "检查民警签字", "见证人签字","被检查人签字","被检查人员捺印", "办案人员签字",
            "随身财物管理员签字","涉案人员签字","涉案人员捺印","领取人签名","领取人捺印","出所管理员签字"};
    private static Boolean[] qmb = {false, false, false, false, false, false, false,false,false,false,false,false,false};

    private static String[] qmny_xb = {"bamj","police_xb","jbqkgly","jcmj","checkmj_xb","jzr","bjcr","bechecked","bary","casehandler_xb","sscwgly","sary","involved","lqr","recipients","csgly","csmanager_xb"};
    private static String[] qmts_xb = { "办案民警签字","办案民警(协办)签字","入区管理员签字", "检查民警签字","检查民警(协办)签字", "见证人签字","被检查人签字","被检查人员捺印", "办案人员签字","办案人员(协办)签字",
            "随身财物管理员签字","涉案人员签字","涉案人员捺印","领取人签名","领取人捺印","出所管理员签字","出所管理员(协办)签字"};
    private static Boolean[] qmb_xb = {false, false, false, false, false, false, false,false,false,false,false,false,false,false,false,false,false};

    /**
     *  根据办案区配置签字捺印，解析并返回对应顺序到 SignqmnyConfig
     * @param baqid 办案区ID
     * @param isKeep 是否存留原有数组下标
     * @return
     */
    public static SignqmnyConfig AnalysisByConfig(String baqid,boolean isKeep){
        SignqmnyConfig config = new SignqmnyConfig();

        ChasXtSignConfigService configService = ServiceContext.getServiceByClass(ChasXtSignConfigServiceImpl.class);
        ChasXtSignConfig signConfig = configService.findByBaqid(baqid);
        if(signConfig != null){
            try{
                String text = signConfig.getText();
                Map<String,Object> objectMap = JsonUtil.parse(text,Map.class);

                String[] clone_qmny = {},clone_qmts = {};
                Boolean[] clone_qmb = {};
                //已启用协办签字
                if(StringUtil.equals(signConfig.getXbqz(),"1")){
                    clone_qmny = qmny_xb.clone();
                    clone_qmts = qmts_xb.clone();
                    clone_qmb = qmb_xb.clone();
                }else{
                    clone_qmny = qmny.clone();
                    clone_qmts = qmts.clone();
                    clone_qmb = qmb.clone();
                }

                if(isKeep){
                    //不采用原版的根据签字捺印，给PDF赋值的标识算法 如: only(a) only(b) merge(a+b+)
                    config.setQmny(clone_qmny);
                    config.setQmbn(clone_qmb);
                    config.setQmts(clone_qmts);
                }else{
                    List<Map<String,Object>> mergeList = (ArrayList) objectMap.get("merge");  //合并签字
                    Map<String,Object> onlyMap = (Map<String, Object>) objectMap.get("only");  //单独签字
                    //根据配置获取给定长度数组
                    String[] signType = new String[clone_qmny.length];
                    String[] signTips = new String[clone_qmts.length];

                    //根据签字顺序排序(无视页面配置顺序,按照既定 人员登记表 签字顺序)  合并签字解析
                    AnalysisSignCollection(mergeList,signType,signTips,clone_qmts,isKeep);

                    //单独签字解析
                    AnalysisSignCollection(onlyMap,signType,signTips,clone_qmts,isKeep);

                    config.setQmny(requireNonNull(signType));
                    config.setQmts(requireNonNull(signTips));
                    config.setQmbn(Arrays.asList(clone_qmb.clone()).subList(0,config.getQmny().length).toArray(new Boolean[config.getQmny().length]));
                }

                log.info("AnalysisByConfig:",config.getQmny());
                log.info("AnalysisByConfig:",config.getQmts());
                log.info("AnalysisByConfig:",config.getQmbn());
            }catch (Exception e){
                config.setQmny(qmny);
                config.setQmts(qmts);
                config.setQmbn(qmb.clone());
                log.error("解析签字捺印配置异常:",e);
                e.printStackTrace();
            }
        }else{
            config.setQmny(qmny);
            config.setQmts(qmts);
            config.setQmbn(qmb.clone());
        }
        return config;
    }

    /**
     *
     * @param object  解析对象
     * @param signType  签字标识
     * @param signTips  签字提示
     * @param isKeep  是否保存原下标
     */
    public static void AnalysisSignCollection(Object object,String[] signType,String[] signTips,String[] target_qmts,boolean isKeep){
        if(object instanceof Map){
            ((Map) object).keySet().forEach(key_ -> {
                String key = key_.toString().split(":")[0];
                String inx = key_.toString().split(":")[1];
                signType[Integer.parseInt(inx)] = key;
                signTips[Integer.parseInt(inx)] = target_qmts[Integer.parseInt(inx)];
            });
        }
        if(object instanceof ArrayList){
            ((ArrayList)object).forEach(mergeMap -> {
                List<Integer> inxList = new ArrayList<>();
                StringBuffer types = new StringBuffer();
                StringBuffer tips = new StringBuffer();
                ((Map)mergeMap).keySet().forEach(mergeKey->{
                    String key = ((String)mergeKey).split(":")[0];
                    String inx = ((String)mergeKey).split(":")[1];
                    types.append(key+"+");
                    tips.append(target_qmts[Integer.parseInt(inx)]+"和");
                    inxList.add(Integer.parseInt(inx));
                });
                if(!isKeep){
                    //每一个合并的下标最小值
                    IntSummaryStatistics intSummaryStatistics = inxList.stream().mapToInt(x -> x).summaryStatistics();
                    signType[intSummaryStatistics.getMin()] = types.toString();
                    String tip = tips.toString().substring(0,tips.toString().length()-1);
                    signTips[intSummaryStatistics.getMin()] = tip;
                }else{
                    inxList.forEach(inx -> {
                        signType[inx] = types.toString();
                        String tip = tips.toString().substring(0,tips.toString().length()-1);
                        signTips[inx] = tip;
                    });
                }
            });
        }
    }

    /**
     * 清除数组中为null的元素,并返回一个新数组
     * @param array
     * @return
     */
    public static String[] requireNonNull(String[] array){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if(array[i] != null){
                list.add(array[i]);
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * 获取一个签字时  WEB页面所需的 标识 以及 提示 (不留存)
     * @param baqid
     * @return
     */
    public static SignqmnyConfig getSignByConfiguration(String baqid){
        SignqmnyConfig config = AnalysisByConfig(baqid,false);
        return config;
    }

    /**
     * 根据规则返回页面所需操作下标
     * @param signqmnyConfig  配置对象
     * @param ruleType  规则字符
     * @param callType 调用标识
     * @param isdepart  是否出所操作
     * @return
     */
    public static Integer[] getSignInxByRule(SignqmnyConfig signqmnyConfig,String ruleType,String callType,ChasXtSignConfig config,boolean isdepart){
        String[] signType = signqmnyConfig.getQmny();
        String[] signTips = signqmnyConfig.getQmts();

        String[] clonse_qmny = {};
        if(config != null){
            if(StringUtil.equals(config.getXbqz(),"1")){
                clonse_qmny = qmny_xb.clone();
            }else{
                clonse_qmny = qmny.clone();
            }
        }else{
            //默认采用 （非协办签名捺印）
            clonse_qmny = qmny.clone();
        }

        List<Integer> indexList = new ArrayList<>();
        if(StringUtil.equals(ruleType,"aqjc")){
            if(StringUtil.equals(callType,"qm")){
                for (int i = 0; i < signType.length; i++) {
                    if(contains(signType[i],"bamj") || contains(signType[i],"police_xb") || contains(signType[i],"jbqkgly")){
                        indexList.add(i);
                    }
                }
            }
            if(StringUtil.equals(callType,"ny")){
                return new Integer[]{};
            }
        }
        if(StringUtil.equals(ruleType,"ythcj")){
            for (int i = 0; i < signType.length; i++) {
                String tip = "";
                if(StringUtil.equals(callType,"qm")){
                    tip = "签";
                }
                if(StringUtil.equals(callType,"ny")){
                    tip = "捺";
                }
                if(!contains(signType[i],"bamj") && !contains(signType[i],"police_xb") && !contains(signType[i],"jbqkgly") && signTips[i].contains(tip)){
                    indexList.add(i);
                }
            }
        }
        //如果没有type值 龙岩版本适用
        if(StringUtil.isEmpty(ruleType)){
            String tip = "";
            if(StringUtil.equals(callType,"qm")){
                tip = "签";
            }
            if(StringUtil.equals(callType,"ny")){
                tip = "捺";
            }
            for (int i = 0; i < signTips.length; i++) {
                if(signTips[i].contains(tip)){
                    indexList.add(i);
                }
            }
        }
        if(isdepart){
            //过滤第二页签字标识
            indexList.removeIf(inx -> contains(signType[inx],"lqr"));
            indexList.removeIf(inx -> contains(signType[inx],"recipients"));
            indexList.removeIf(inx -> contains(signType[inx],"csgly"));
            indexList.removeIf(inx -> contains(signType[inx],"csmanager_xb"));
        }
        return indexList.toArray(new Integer[indexList.size()]);
    }

    /**
     * str1 是否包含  str2
     * @param str1
     * @param str2
     * @return
     */
    public static boolean contains(String str1,String str2){
        if(StringUtil.isEmpty(str1) || StringUtil.isEmpty(str2)){
            return false;
        }
        Pattern pattern = Pattern.compile("\\b"+str2+"\\b");
        Matcher matcher = pattern.matcher(str1);
        return matcher.find();
    }

    public static void main(String[] args) {
        getSignByConfiguration("FE47A8A0EFA24D72BB0042648149ED45");
    }
}