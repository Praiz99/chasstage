package com.wckj.chasstage.modules.sxdp.service.impl;

import com.wckj.chasstage.api.server.release.ws.handler.SpringWebSocketHandler;
import com.wckj.chasstage.modules.sxdp.dao.ChasSxdpMapper;
import com.wckj.chasstage.modules.sxdp.entity.ChasSxdp;
import com.wckj.chasstage.modules.sxdp.service.ChasSxdpService;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
@Transactional
public class ChasSxdpServiceImpl extends BaseService<ChasSxdpMapper, ChasSxdp> implements ChasSxdpService {
	private static final Logger log = Logger.getLogger(ChasSxdpServiceImpl.class);

	@Bean//这个注解会从Spring容器拿出Bean
	public SpringWebSocketHandler infoHandler() {
		return new SpringWebSocketHandler();
	}

	@Override
	public void updateByXyr(ChasSxdp chasSxdp) {
		baseDao.startMission(chasSxdp);
	}

	@Override
	public Map<String, Object> sxStart(ChasSxdp chasSxdp) {
		// TODO Auto-generated method stub
		Map<String,Object> result = new HashMap<>();
		SessionUser user = WebContext.getSessionUser();
		try {
			chasSxdp.setXgrSfzh(user.getIdCard());
			Calendar nowTime = Calendar.getInstance();
			chasSxdp.setXgsj(nowTime.getTime());
			nowTime.add(Calendar.MINUTE, 5);
			chasSxdp.setSxsj(nowTime.getTime());
			chasSxdp.setRwzt("02");
			updateByXyr(chasSxdp);
			infoHandler().sendMessageToUser("sxdpOrgCode", new TextMessage(""));
			result.put("code",0);
			result.put("message","保存成功!");
		} catch (Exception e) {
			result.put("code",-1);
			result.put("message",e.getMessage());
		}
		return result;
	}

	@Override
	public Map<String, Object> createSx(ChasSxdp chasSxdp) {
		Map<String,Object> result = new HashMap<>();
		SessionUser user = WebContext.getSessionUser();
		try {
			chasSxdp.setId(StringUtils.getGuid32());
			if (null != user) {
				chasSxdp.setLrrSfzh(user.getIdCard() == null ? "" : user.getIdCard());
			}
			chasSxdp.setLrsj(new Date());
			chasSxdp.setRwzt("01");
			chasSxdp.setIsdel(0);
			baseDao.insert(chasSxdp);
			infoHandler().sendLocaltion("sxdpOrgCode", "");
			result.put("code",0);
			result.put("message","保存成功!");
			log.info("开启审讯大屏成功!");
		} catch (Exception e) {
			result.put("code",-1);
			result.put("message",e.getMessage());
			log.error("开启审讯大屏异常:",e);
		}
		return result;
	}

}

