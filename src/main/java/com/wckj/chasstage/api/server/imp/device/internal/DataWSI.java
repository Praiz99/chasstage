package com.wckj.chasstage.api.server.imp.device.internal;


import com.wckj.chasstage.api.server.imp.device.util.DevResult;

/**
 * @author {XL-SaiAren}
 * @date 创建时间：2017年11月15日 上午10:45:57
 * @version V2.0.0 类说明
 */
public interface DataWSI {

	/** 同步办案区区域 **/
	DevResult baqQyTb(String baqid, String time);

	/** 同步办案区设备 （省内）**/
	@Deprecated
	DevResult baqSbTb(String baqid, String time);

	/** 同步腕带 **/
	@Deprecated
	DevResult baqWdTb(String baqid, String time);

	/** 同步办案区储物柜 **/
	@Deprecated
	DevResult baqCwgTb(String baqid, String time);
	
	/** 新增办案区 同步id name 至DC系统 **/
	DevResult baqSynchronization(String baqid, String name);
	/** 同步办案区设备 新接口（外省） **/
	DevResult getDeviceByOrg(String baqid, String proNo);
}
