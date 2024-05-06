package com.wckj.chasstage.api.def.common.service;

import com.wckj.chasstage.api.def.common.model.FileInfo;
import com.wckj.chasstage.api.def.common.model.ProcessParam;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;
import com.wckj.jdone.modules.act2.core.NodeProcessCmdObj2;

import java.util.List;

/**
 * @author wutl
 * @Title: 公共服务
 * @Package 公共服务
 * @Description: 公共服务
 * @date 2020-9-2314:33
 */
public interface ApiCommonService extends IApiBaseService {

    /**
     * 根据文件bizId和bizType获取文件信息
     * @param bizId
     * @param bizType
     * @return
     */
    ApiReturnResult<List<FileInfo>> getZpwjInfo(String bizId, String bizType);

    /**
     * 根据办案区id和功能类型，角色为当前登录人
     * @param baqid
     * @param funType
     * @return
     */
    ApiReturnResult<?> getWlsxtxxByFunType(String baqid, String funType);

    /**
     * 根据客户端Ip查询绑定的摄像头
     * @param baqid
     * @param funType
     * @param clientIp
     * @return
     */
    ApiReturnResult<?> getWlsxtxxByFunTypeClientIp(String baqid, String funType, String clientIp);

    ApiReturnResult<?> getMjRoles(String sfzh);
}
