package com.wckj.chasstage.api.server.imp.device.internal;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.mjzpk.entity.ChasXtMjzpk;
import com.wckj.chasstage.modules.yy.entity.ChasYwYy;

/**
 * 门禁
 */
public interface EntranceOper {
    DevResult apptifs(ChasXtMjzpk mjzpk, ChasYwYy yy);
}
