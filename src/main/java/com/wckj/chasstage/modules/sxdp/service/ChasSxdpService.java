package com.wckj.chasstage.modules.sxdp.service;

import com.wckj.chasstage.modules.sxdp.dao.ChasSxdpMapper;
import com.wckj.chasstage.modules.sxdp.entity.ChasSxdp;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.Map;

public interface ChasSxdpService extends IBaseService<ChasSxdpMapper, ChasSxdp> {

	void updateByXyr(ChasSxdp chasSxdp);
	
	/**
	 * 开始审讯接口，参数：sxsbh审讯室编号（可空），sxsmc审讯室名称，sxmjxm审讯民警姓名，sxmjbh审讯民警编号（可空）,xyr嫌疑人编号
	 *
	 * @param chasSxdp
	 * @return
	 */
	Map<String, Object> sxStart(ChasSxdp chasSxdp);
	
	/**
	 *创建审讯大屏接口，参数：xyr嫌疑人编号, xyrxm嫌疑人姓名，
	 *
	 * @param chasSxdp
	 * @return
	 */
	Map<String, Object> createSx(ChasSxdp chasSxdp);

}
