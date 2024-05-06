package com.wckj.chasstage.common.util;

import com.wckj.framework.core.utils.StringUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkUtil {
    public final static String IP_Pattern ="(2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2}(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}";
    public static boolean isHostReachable(String host, int timeOut) {
        try {

            return InetAddress.getByName(host).isReachable(timeOut);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean isIpAddressStr(String ip){
        if(StringUtils.isEmpty(ip)){
            return false;
        }
        return ip.matches(IP_Pattern);
    }

}
