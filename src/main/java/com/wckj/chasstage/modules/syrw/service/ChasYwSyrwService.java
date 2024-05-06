package com.wckj.chasstage.modules.syrw.service;

import com.wckj.chasstage.modules.syrw.dao.ChasYwSyrwMapper;
import com.wckj.chasstage.modules.syrw.entity.ChasSyrw;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

public interface ChasYwSyrwService extends IBaseService<ChasYwSyrwMapper, ChasSyrw> {
	
	/**
	 * 送押任务开始
	 * @param 办案区名称， 嫌疑人编号，嫌疑人姓名，性别，入区原因，特殊群体，案件类型，主办民警，出区原因，送押时间，申请人身份证号，申请人姓名,联系人，联系电话
	 * @return code(0,-1)
	 */
	Map<String, Object> missionStart(ChasSyrw chasSyrw);

	void endMission(ChasSyrw chasSyrw);

}
