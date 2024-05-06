package com.wckj.chasstage.api.server.imp.device.internal.impl;


import com.wckj.chasstage.api.server.imp.device.internal.EntranceOper;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.DateTimeUtils;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.mjzpk.entity.ChasXtMjzpk;
import com.wckj.chasstage.modules.yy.entity.ChasYwYy;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 门禁
 */
@Service
public class EntranceOperImpl extends BaseDeviceOper implements EntranceOper {
    protected Logger log = LoggerFactory.getLogger(EntranceOperImpl.class);

    @Override
    public DevResult apptifs(ChasXtMjzpk mjzpk, ChasYwYy yy) {
        try {
            Date jssjDate = new Date(yy.getKssj().getTime()+1000*60*60*48);
            String kssj= DateTimeUtils.getDateStr(yy.getKssj(), 13);
            String jssj=DateTimeUtils.getDateStr(jssjDate,13 );
            String zpid="";
            FileInfoObj fileList = FrwsApiForThirdPart.getFileInfoByBizId(mjzpk.getZpid());
            if (fileList != null) {
                zpid=fileList.getDownUrl();
                DevResult r=processOper(mjzpk.getBaqid(), "apptifs",
                        new Object[]{mjzpk.getBaqid(),yy.getYyrjh(),yy.getYyrxm()
                                ,yy.getYyrsfzh(),kssj,jssj,zpid
                        });
                if(r.getCode()==1){
                    return r;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("下发门禁人脸异常",e);
        }
        return DevResult.error("下发门禁失败");
    }
}
