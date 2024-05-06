package com.wckj.chasstage.api.server.device;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.api.server.imp.device.util.DeviceException;

/**
 * 用于从dc同步区域、设备等数据
 */
public interface DataTB {
	/** 同步办案区 **/
	DevResult baqTb(String baqid, String name) throws DeviceException;

	/**
	 * 同步办案区腕带
	 **/
	//DevResult baqWdTb(String baqid) throws DeviceException;

	/**
	 * 同步办案区区域
	 **/
	DevResult baqQyTb(String baqid) throws DeviceException;
	/**
	 * 同步办案区设备
	 **/
	//DevResult baqSbTb(String baqid) throws DeviceException;
	/**
	 * 同步办案区储物柜
	 **/
	//DevResult baqCwgTb(String baqid) throws DeviceException;
	public DevResult getDeviceByOrg(String baqid, String proNo);
}
