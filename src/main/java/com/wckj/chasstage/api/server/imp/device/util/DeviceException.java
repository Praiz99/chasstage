package com.wckj.chasstage.api.server.imp.device.util;

/**
 * 设备控制异常
 */
public class DeviceException extends RuntimeException {
    private String deviceInfo;

    public DeviceException() {
        super();
    }

    public DeviceException(String message) {
        super(message);
    }

    public DeviceException(String deviceInfo, String message) {
        super(message);
        this.deviceInfo = deviceInfo;
    }

    public DeviceException(String deviceInfo, String message, Throwable cause) {
        super(message, cause);
        this.deviceInfo = deviceInfo;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
