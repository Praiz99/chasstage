package com.wckj.chasstage.api.def.qqdh.service;

import com.wckj.chasstage.api.def.qqdh.model.QqdhBean;
import com.wckj.chasstage.api.def.qqdh.model.QqdhParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;
import com.wckj.jdone.modules.act.core.NodeProcessCmdObj;
import com.wckj.jdone.modules.act2.core.NodeProcessCmdObj2;

/**
 * 亲情电话
 */
public interface ApiQqdhService extends IApiBaseService {

    ApiReturnResult<?> getPageData(QqdhParam param);

    ApiReturnResult<?> save(QqdhBean bean);

    ApiReturnResult<?> update(QqdhBean bean);

    ApiReturnResult<?> deletes(String ids);

    //ApiReturnResult<?> qqdhsq(QqdhBean bean,NodeProcessCmdObj2 cmdObj2);

    //获取亲情电话录音文件url
    ApiReturnResult<?> qqdhtqly(String id);

    ApiReturnResult<?> open(String ryid, String id);

    ApiReturnResult<?> valideApprove(String ryid);

    //关闭
    ApiReturnResult<?> close(String ryid, String id);

    ApiReturnResult<?> startProcess(NodeProcessCmdObj2 node);

    ApiReturnResult<?> executeProcess(NodeProcessCmdObj2 cmdObj);
}
