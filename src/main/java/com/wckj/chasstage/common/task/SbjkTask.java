package com.wckj.chasstage.common.task;


import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.framework.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

/**
 * 设备是否在线、是否丢包
 */
@Component("sbjkTask")
public class SbjkTask extends BaseAlarmService{
	private static final Logger log = LoggerFactory.getLogger(SbjkTask.class);

	private static final int MAXNUM = 5; // 设置重发数据的最多次数
	private static final int TIMEOUT = 5000;  //设置接收数据的超时时间
//	private static final int CLIENT_PORT = 8888;
	private static final int SERVER_PORT = 13366;
	private static final int REV_SIZE = 1024; //接收数据的存储空间大小

	@Autowired
	private ChasSbService sbService;

	public void task() {
		log.info("开始执行设备是否在线、是否丢包定时任务"+System.currentTimeMillis());
		List<ChasSb> sbList = sbService.findZDSB();
		try {
			for (int i = 0; i < sbList.size(); i++) {
				ChasSb chasSb = sbList.get(i);
				if(StringUtils.isEmpty(chasSb.getJkjk())){
					continue;
				}
				String str_send = chasSb.getId(); //要发送的字串
				byte[] buf_rev = new byte[REV_SIZE];     //要接收的存储空间

				/*第一步 实例化DatagramSocket*/
				DatagramSocket mSoc = new DatagramSocket();
				mSoc.setSoTimeout(TIMEOUT);              //设置接收数据时阻塞的最长时间

				/*第二步 实例化用于发送的DatagramPacket和用于接收的DatagramPacket*/
				InetAddress inetAddress = InetAddress.getByName(chasSb.getJkjk());
				int serverPort = SERVER_PORT;
				if(StringUtils.isNotEmpty(chasSb.getKzcs2())){
					serverPort = new Integer(chasSb.getKzcs2());
				}
				DatagramPacket data_send = new DatagramPacket(str_send.getBytes(),
						str_send.length(), inetAddress, serverPort);

				DatagramPacket data_rev = new DatagramPacket(buf_rev, REV_SIZE);

				/*第三步 DatagramPacket send发送数据，receive接收数据*/
				int send_count = 0; // 重发数据的次数
				boolean revResponse = false; // 是否接收到数据的标志位
				while (!revResponse && send_count < MAXNUM) {
					try {
						mSoc.send(data_send); //发送数据
						mSoc.receive(data_rev);//接收数据
						revResponse = true;
					} catch (InterruptedIOException e) {
						// 如果接收数据时阻塞超时，重发并减少一次重发的次数
						send_count += 1;
						//log.info("Time out," + (MAXNUM - send_count) + " more tries...");
					}
				}
				if (revResponse) {
					// 如果收到数据，则打印出来
					String id = new String(data_rev.getData(), 0,
							data_rev.getLength());

					ChasSb sb = sbService.findById(id);
					sb.setSfzx("1");
					sb.setSfdb("0");
					sb.setWlzt("正常");
					sb.setGzzt("正常");
					sbService.update(sb);
					String str_receive = new String(data_rev.getData(), 0,
							data_rev.getLength())
							+ " from "
							+ data_rev.getAddress().getHostAddress()
							+ ":"
							+ data_rev.getPort();
					log.info("client received data from server："+str_receive);
					// 由于dp_receive在接收了数据之后，其内部消息长度值会变为实际接收的消息的字节数，
					// 所以这里要将dp_receive的内部消息长度重新置为1024
					data_rev.setLength(REV_SIZE);
				} else {
					ChasSb sb = sbService.findById(str_send);
					sb.setSfzx("0");
					sb.setSfdb("1");
					sb.setWlzt("异常");
					sb.setGzzt("异常");
					sbService.update(sb);
					// 如果重发MAXNUM次数据后，仍未获得服务器发送回来的数据，则打印如下信息
					log.info("No response -- give up:"+str_send);
				}

				/*第四步 关闭DatagramPacket*/
				mSoc.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
