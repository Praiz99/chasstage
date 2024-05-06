package com.wckj.chasstage.api.server.imp.device.internal;



import com.wckj.chasstage.api.server.imp.device.util.DevResult;

import java.util.List;

/**
 * led接口
 */
public interface ILedOper {
    /**
     *  设置两行内容
     * @param org 办案区编号
     * @param deviceType 设备类型
     * @param sbbh 设备编号
     * @param ledTexts 显示内容 第一个 为标题 固定 第二行 动态滚动
     * @param showTime 显示时间
     * @return
     */
    DevResult ledSend(String org, String deviceType, String sbbh,
                      List<String> ledTexts, Integer showTime);


    /**
     * 多行刷新某一行
     * @param org 办案区编号
     * @param id 设备编号
     * @param content 显示内容
     * @param lineNo 行号
     * @param showTime 延迟显示时间
     * @return
     */
    DevResult setRowContent(String org, String id, String content, Integer lineNo, Integer showTime);
}
