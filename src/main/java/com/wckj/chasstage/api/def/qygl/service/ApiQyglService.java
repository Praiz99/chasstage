package com.wckj.chasstage.api.def.qygl.service;import com.wckj.chasstage.api.def.qygl.model.QyglBean;import com.wckj.framework.api.ApiReturnResult;import com.wckj.framework.api.IApiBaseService;/** * @ClassName : ApiQyglService  //类名 * @Description : 区域管理功能  //描述 * @Author : lcm  //作者 * @Date: 2020-09-09 10:07  //时间 */public interface ApiQyglService extends IApiBaseService {    ApiReturnResult<String> getQyglApiPageData(QyglBean param);    ApiReturnResult<String> tbBaqqy(String baqId);    ApiReturnResult<String> deleteBaqByIds(String Ids);    ApiReturnResult<?> getQyDicByBaq(String baqid,String queryValue,String fjlx);}