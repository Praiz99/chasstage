package com.wckj.chasstage.api.def.shsp.service;

import com.wckj.chasstage.api.def.shsp.model.ShcomitParam;
import com.wckj.chasstage.api.def.shsp.model.ShspParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;
import com.wckj.jdone.modules.act2.core.NodeProcessCmdObj2;

/**
 * @author wutl
 * @Title: 审核审批
 * @Package
 * @Description:
 * @date 2020-10-2914:43
 */
public interface ApiShspService extends IApiBaseService {

    ApiReturnResult<?> getRmgDataByParamMap(Integer page, Integer rows, ShspParam shspParam, String createTimeDesc);

    ApiReturnResult<?> executeProcess(NodeProcessCmdObj2 cmdObj);

    /**
     * 出所审批流程
     * @param bizId
     * @param bizType
     * @return
     */
    ApiReturnResult<?> cqsplc(String bizId, String bizType);

    /**
     * 获取审批详情
     * @param bizId
     * @return
     */
    ApiReturnResult<?> getCqspXq(String bizId);

    /**
     * 出区审批
     * @param shcomitParam
     * @return
     */
    ApiReturnResult<?> docqsp(ShcomitParam shcomitParam);

    /**
     * 获取审批流程节点
     * @param bizId
     * @param bizType
     * @return
     */
    ApiReturnResult<?> getNextTaskNodeInfo(String bizId, String bizType);

    /**
     * 不再处理
     * @param bizId
     * @param bizType
     * @return
     */
    ApiReturnResult<?> noPrompt(String msgId);
}
