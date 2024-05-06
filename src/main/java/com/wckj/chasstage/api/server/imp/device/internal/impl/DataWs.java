package com.wckj.chasstage.api.server.imp.device.internal.impl;


import com.wckj.chasstage.api.server.imp.device.internal.DataWSI;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.api.server.imp.device.util.DeviceException;
import org.springframework.stereotype.Service;

/** 
 * 从dc同步办案区信息、设备、区域、腕带、标签
 */
@Service
public class DataWs extends BaseDeviceOper implements DataWSI {

	@Override
	public DevResult baqWdTb(String baqid, String time) {
		return	processOper(baqid, "getTagList", new Object[] { baqid, time});
	}

	@Override
	public DevResult baqQyTb(String baqid, String time) throws DeviceException  {
		return processOper(baqid, "getAreaList", new Object[] { baqid, time});
	}

	@Override
	public DevResult baqSbTb(String baqid, String time) throws DeviceException  {
		return processOper(baqid, "getSdDeviceList", new Object[] { baqid, time});
	}
	@Override
	public DevResult baqCwgTb(String baqid, String time) {
		return processOper(baqid, "getSdCabList", new Object[] { baqid, time});
	}
	
	@Override
	public DevResult baqSynchronization(String baqid, String name) {
		return processOper(baqid, "baqSynchronization", new Object[] { baqid, name});
	}

	@Override
	public DevResult getDeviceByOrg(String baqid, String proNo) {
		return processOper(baqid, "getDeviceByOrg", new Object[] { baqid, proNo});
	}
}
