package com.wckj.chasstage.api.server.imp.device;


import com.alibaba.fastjson.JSON;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.api.server.imp.device.internal.DataWSI;
import com.wckj.chasstage.api.server.imp.device.internal.dto.AreaEntity;
import com.wckj.chasstage.api.server.imp.device.internal.json.*;
import com.wckj.chasstage.api.server.device.DataTB;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.api.server.imp.device.util.DeviceException;
import com.wckj.chasstage.common.util.JsonTransferUtil;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.sbgl.entity.*;
import com.wckj.chasstage.modules.sbgl.service.*;
import com.wckj.chasstage.modules.wpg.entity.ChasSswpg;
import com.wckj.chasstage.modules.wpxg.entity.ChasSswpxg;
import com.wckj.chasstage.modules.wpg.service.ChasSswpgService;
import com.wckj.chasstage.modules.wpxg.service.ChasSswpxgService;
import com.wckj.chasstage.modules.wd.entity.ChasWd;
import com.wckj.chasstage.modules.wd.service.ChasWdService;
import com.wckj.chasstage.modules.znqt.entity.ChasZnqt;
import com.wckj.chasstage.modules.znqt.service.ChasZnqtService;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.jdone.modules.sys.util.DicUtil;
import com.wckj.jdone.modules.sys.util.SysUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional
public class DataTbImpl implements DataTB {
	private static final Logger log = Logger.getLogger(DataTbImpl.class);
	@Autowired
	private ChasWdService chasWdService;
	@Autowired
	private ChasXtQyService chasQyService;
	@Autowired
	private ChasSbService chasSbService;
	@Autowired
	private ChasSswpgService chasSswpgService;
	@Autowired
	private ChasSswpxgService chasSswpxgService;
	@Autowired
	private ChasWdService wdService;
	@Autowired
	private DataWSI dataWSI;
	@Autowired
	private ChasZnqtService chasZnqtService;
	@Autowired
	private ChasBaqService chasBaqService;

	@Override
	public DevResult baqTb(String baqid, String name)throws DeviceException {
		DevResult result = dataWSI.baqSynchronization(baqid, name);
		return result;
	}

	@Override
	public DevResult baqQyTb(String baqid) throws DeviceException {
		DevResult webResult = new DevResult();
		DevResult result = dataWSI.baqQyTb(baqid,
				SysUtil.getParamValue("dataTbTime"));
		if (result.getCode() != 1) {
			webResult.setCodeMessage(1, result.getMessage());
			return webResult;
		}
		Map<String, String> dcallqyid = new HashMap<String, String>();
		List<Map<String, Object>> baqQyMap = (List<Map<String, Object>>) result
				.get("areaList");
		List<AreaEntity> areaList = JsonTransferUtil.mapsToObjects(baqQyMap,
				AreaEntity.class);
		Map<String, Object> map = new HashMap<>();
		List<ChasXtQy> chasXtQylist;
		for (AreaEntity qy : areaList) {
			boolean su = false;
			map.clear();
			map.put("ysid", qy.getId());
			dcallqyid.put(qy.getId(), qy.getId());
			chasXtQylist = chasQyService.findByParams(map);
			ChasXtQy chasXtQy = null;
			if (!chasXtQylist.isEmpty()) {
				chasXtQy = chasXtQylist.get(0);
			}
			if (chasXtQy == null) {
				chasXtQy = new ChasXtQy();
				chasXtQy.setId(StringUtils.getGuid32());
				chasXtQy.setIsdel(0);
				su = true;
			}
			chasXtQy.setQymc(qy.getName());
			chasXtQy.setBaqid(qy.getOrg());
			ChasBaq baq = chasBaqService.findById(qy.getOrg());
			if(baq!=null){
				chasXtQy.setBaqmc(baq.getBaqmc());
			}
			chasXtQy.setRysl(qy.getPeopleCountThreshold());
			chasXtQy.setRyzlsj(qy.getStayTimeThreshold());
			chasXtQy.setPxbh(qy.getOrderNum());
			chasXtQy.setSfgns(Integer.valueOf(qy.getWorkRoom()));
			chasXtQy.setFjlx(qy.getAreaType());
			chasXtQy.setKzlx(qy.getRoomType());
			chasXtQy.setYsbh(qy.getOriId());
			chasXtQy.setSfgns(Integer.valueOf(qy.getWorkRoom()));
			chasXtQy.setYsid(qy.getId());
			if (qy.getCreateDate() != null) {
				chasXtQy.setLrsj(new Date(qy.getCreateDate()));
			}
			if (qy.getUpdateDate() != null) {
				chasXtQy.setXgsj(new Date(qy.getUpdateDate()));
			}
			// 新增/更改
			if (su) {
				chasQyService.save(chasXtQy);
			} else {
				chasQyService.update(chasXtQy);
			}
		}
		// 删除本地多出DC 不存在的区域
		if (StringUtils.isNotEmpty(baqid)) {
			map.clear();
			map.put("baqid", baqid);
			chasXtQylist = chasQyService.findList(map, null);
			for (ChasXtQy chasXtQy : chasXtQylist) {
				if(StringUtils.isNotEmpty(chasXtQy.getYsid())) {
					if (!dcallqyid.containsKey(chasXtQy.getYsid())) {
						chasQyService.deleteById(chasXtQy.getId());
					}
				}
			}
		}
		webResult.setCode(0);
		webResult.add("qy", areaList.size());
		webResult.setMessage(String.format("同步区域数量:{%d}", areaList.size()));
		log.debug(String.format("办案区:{%s} baqQyTb同步完成后开始baqSbTb同步", baqid));
		return webResult;
	}

	@Override
	public DevResult getDeviceByOrg(String baqid, String proNo) {
		log.debug(String.format("开始同步办案区{%s}设备",baqid));
		DevResult webResult = DevResult.success("同步设备成功");
		DevResult result = dataWSI.getDeviceByOrg(baqid, proNo);
		if (result.getCode() != 1) { // 同步数据失败
			webResult.setCodeMessage(result.getCode(), result.getMessage());
			log.error("同步设备数据出错"+result.getMessage());
			return webResult;
		}

		try {
			List<BaseDevData> antList = parse(result,"antList", Ant.class);
			webResult.add("ant", baqDeviceSave(baqid,"7",antList));
			List<BaseDevData> cabList = parse(result,"cabList", Cab.class);
			webResult.add("物品柜",baqDeviceSave(baqid,"4",cabList));
			webResult.add("cab",baqWpgSync(baqid,cabList));
			List<BaseDevData> readerList = parse(result,"cardReaderList", CardReader.class);
			webResult.add("reader",baqDeviceSave(baqid,"5",readerList));
			List<BaseDevData> nvrList = parse(result,"nvrList", Nvr.class);
			webResult.add("nvr",baqDeviceSave(baqid,"10",nvrList));
			List<BaseDevData> equipList = parse(result,"equipList", Equip.class);
			webResult.add("equip",baqDeviceSave(baqid,"8",equipList));
			List<BaseDevData> cameraList = parse(result,"cameraList", Camera.class);
			webResult.add("camera",baqDeviceSave(baqid,"2",cameraList));
			List<BaseDevData> relayList = parse(result,"relayList", Relay.class);
			webResult.add("relay",baqDeviceSave(baqid,"3",relayList));
			List<BaseDevData> dzspList = parse(result,"dzspList", Relay.class);
			webResult.add("dzsp",baqDeviceSave(baqid,"11",dzspList));
			List<BaseDevData> ledList = parse(result,"ledList", Led.class);
			webResult.add("led",baqDeviceSave(baqid,"1",ledList));
			List<BaseDevData> tagList = parse(result,"tagList", Tag.class);
			webResult.add("tag",baqDeviceSave(baqid,"6",tagList));
			List<BaseDevData> brakeList = parse(result,"brakeList", Brake.class);
			webResult.add("brake",baqDeviceSave(baqid,"12",brakeList));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("解析数据异常",e);
			throw new RuntimeException(e);
		}
		return webResult;
	}

	private List<BaseDevData> parse(DevResult result,String key,Class clazz){
			List<Map<String, Object>> chasWdMap = (List<Map<String, Object>>) result
					.get(key);
			List<BaseDevData> chasWdList = JsonTransferUtil.mapsToObjects(chasWdMap,clazz);
			return chasWdList;
	}
	public DevResult baqDeviceSave(String baqid,String type,List<BaseDevData> devList)  {
		//|| devList.isEmpty()
		if(devList == null){
			//if(!"3".equals(type))
			return DevResult.error("无设备数据");
		}
		DevResult webResult = new DevResult();
		Map<String, String> znqtmap = new HashMap<>();
//        if(!"2".equals(type)){
//            chasSbService.clearByBaqAndLx(baqid,type);
//        }
		Map<String, String> dcallsbid = new HashMap<>();
        Map<String, String> dcallWdid = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		List<ChasSb> chasSblist;
		for (BaseDevData device : devList) {
			boolean su = false;
			dcallsbid.put(device.getId(), device.getId());
			map.clear();
			map.put("sbbh", device.getId());
			chasSblist = chasSbService.findByParams(map);
			ChasSb chasSb = null;
			if (!chasSblist.isEmpty()) {
				chasSb = chasSblist.get(0);
				chasSb.setBaqid(device.getOrg());
				chasSb.setBaqmc(device.getOrgName());
				chasSb.setQyid(device.getDeviceLocation());
				chasSb.setSblx(device.getDeviceType());
				chasSb.setSbbh(device.getId());
				chasSb.setSbmc(device.getName());
				chasSb.setSbgn(device.getDeviceFun());
				ChasSb chasSbNew = device.toSb();
				chasSb.setKzcs1(chasSbNew.getKzcs1());
				chasSb.setKzcs2(chasSbNew.getKzcs2());
				chasSb.setKzcs3(chasSbNew.getKzcs3());
				chasSb.setKzcs4(chasSbNew.getKzcs4());
				chasSb.setKzcs5(chasSbNew.getKzcs5());
			}else{
				chasSb = device.toSb();
				su = true;
			}
			/*if(StringUtils.isNotEmpty(device.getDeviceLocation())){
				map.clear();
				map.put("ysid", device.getDeviceLocation());
				map.put("baqid", device.getOrg());
				List<ChasXtQy> chasXtQylist = chasQyService.findByParams(map);
				if (!chasXtQylist.isEmpty()) {
					chasSb.setQyid(chasXtQylist.get(0).getId());
				}else{
					chasSb.setQyid(null);
				}
			}*/



			if (su) {
				chasSbService.save(chasSb);
			} else {
				chasSbService.update(chasSb);
			}

			if("3".equals(type)){//只有继电器才可能是
				//智能墙体
				if (SYSCONSTANT.SBGN_ZNQT.equals(device.getDeviceFun())) {
					ChasXtQy qy = chasQyService.findByYsid(device.getDeviceLocation());
					znqtmap.put(qy.getYsid(), baqid + "," + device.getOrgName() + ","
							+ qy.getYsid() + "," + qy.getQymc());
				}
			}else if("6".equals(type)){//标签，将数据保存至chas_xt_wd表，兼容原系统
                dcallWdid.put(chasSb.getSbbh(), chasSb.getSbbh());
			    ChasWd wd = Tag.towd(chasSb);
			    map.clear();
				map.put("ysid", device.getId());
				map.put("baqid", device.getOrg());
                List<ChasWd> wds = wdService.findList(map, null);
                if(wds==null||wds.isEmpty()){
                    wdService.save(wd);
                }else{
                    ChasWd w=wds.get(0);
                    w.setBaqid(wd.getBaqid());
                    w.setBaqmc(wd.getBaqmc());
                    w.setWdbhL(wd.getWdbhL());
                    w.setWdbhH(wd.getWdbhH());
                    w.setLx(wd.getLx());
                    wdService.update(w);
                }
            }
		}
		if("3".equals(type)){
			saveZnqt(baqid,znqtmap);
		}
		if("2".equals(type)||!"".equals(type)){
			map.clear();
			map.put("baqid", baqid);
			map.put("sblx", type);
			chasSblist = chasSbService.findList(map, null);
			for (ChasSb sb : chasSblist) {
				if (!dcallsbid.containsKey(sb.getSbbh())) {
					chasSbService.deleteById(sb.getId());
				}
			}
			if("6".equals(type)){//删除不存在dc手环
                map.clear();
                map.put("baqid", baqid);
                List<ChasWd> list = wdService.findList(map, null);
                for (ChasWd wd : list) {
                    if (!dcallWdid.containsKey(wd.getYsid())) {
                        wdService.deleteById(wd.getId());
                    }
                }
            }
		}
		webResult.setCode(1);
		webResult.setMessage(String.format("同步设备数量:%d", devList.size()));
		return webResult;
	}

	private void saveZnqt(String baqid,Map<String, String> znqtmap){
		if(znqtmap == null){
			return;
		}
		// 智能墙体自动配置 先删除DC删除的 或无硬件的 再配置进去
		Map<String, Object> map = new HashMap<>();
		map.put("baqid", baqid);
		List<ChasZnqt> chasZnqtlist = chasZnqtService.findList(map, null);
		for (ChasZnqt ChasZnqt : chasZnqtlist) {
			if (!znqtmap.containsKey(ChasZnqt.getWzdm())) {
				chasZnqtService.deleteById(ChasZnqt.getId());
			}
			znqtmap.remove(ChasZnqt.getWzdm());
		}
		if (!znqtmap.isEmpty()) {
			Set<String> keys = znqtmap.keySet();
			for (String key : keys) {
				String[] znqtxx = znqtmap.get(key).split(",");
				ChasZnqt chasZnqt = new ChasZnqt();
				chasZnqt.setId(StringUtils.getGuid32());
				chasZnqt.setBaqid(baqid);
				chasZnqt.setBaqmc(znqtxx[1]);
				chasZnqt.setWzdm(znqtxx[2]);
				chasZnqt.setWzmc(znqtxx[3]);
				chasZnqt.setBz("auto");
				chasZnqt.setDataflag("0");
				chasZnqt.setIsdel(0);
				chasZnqt.setLrsj(new Date());
				chasZnqtService.save(chasZnqt);
			}
		}
	}
	public DevResult baqWpgSync(String baqid,List<BaseDevData> devList) {
		//||devList.isEmpty()
		if(devList == null){
			return DevResult.error("无物品柜设备");
		}
		if(devList.isEmpty()){//没有物品柜时，清除原来同步的数据
			Map<String, Object> cwgmap = new HashMap<>();
			cwgmap.put("baqid", baqid);
			List<ChasSswpg> chasSswpglist = chasSswpgService.findList(
					cwgmap, null);
			if(chasSswpglist != null&& !chasSswpglist.isEmpty()){
				for (ChasSswpg wpg:chasSswpglist){
					chasSswpxgService.clearByWpg(wpg.getId());
					chasSswpgService.deleteById(wpg.getId());
				}
			}
		}
		DevResult webResult = new DevResult();
		Map<String, String> map = new HashMap<>();
		Map<String, Object> cwgmap = new HashMap<>();
		for (BaseDevData dev : devList) {
			Cab device = (Cab) dev;
			// 保存主配置
			String wpgid= device.getOrg() + device.getCabGroup();
			if (!map.containsKey(wpgid)) {
				boolean su = false;
				cwgmap.put("ysid", device.getCabGroup());
				cwgmap.put("baqid", device.getOrg());
				List<ChasSswpg> chasSswpglist = chasSswpgService.findList(
						cwgmap, null);
				ChasSswpg chasSswpg = null;
				if (!chasSswpglist.isEmpty()) {
					chasSswpg = chasSswpglist.get(0);
				}
				if (chasSswpg == null) {
					chasSswpg = new ChasSswpg();
					chasSswpg.setId(StringUtils.getGuid32());
					su = true;
				}
				ChasBaq baq = chasBaqService.findById(device.getOrg());
				chasSswpg.setBaqid(device.getOrg());
				chasSswpg.setBaqmc(device.getOrgName());
				chasSswpg.setIsdel(0);
				chasSswpg.setLrsj(new Date(device.getCreateDate()));
				chasSswpg.setLrrSfzh("admin");
				chasSswpg.setXgrSfzh("admin");
				chasSswpg.setXgsj(new Date(device.getUpdateDate()));
				if(su){//后台可能修改名称，防止更新时名称恢复默认
					chasSswpg.setMc(device.getCabGroup());
				}
				chasSswpg.setSfzng(SYSCONSTANT.Y_I);
				chasSswpg.setDataflag("0");
				chasSswpg.setDwdm(baq.getDwdm());
				chasSswpg.setDwxtbh(baq.getDwxtbh());
				chasSswpg.setYsid(device.getCabGroup());
				// 新增/更改
				if (su) {
					chasSswpgService.save(chasSswpg);
				} else {
					chasSswpgService.update(chasSswpg);
				}
				map.put(wpgid,chasSswpg.getId());
				chasSswpxgService.clearByWpg(chasSswpg.getId());
			}

			ChasSswpxg chasSswpxg = device.toSswpxg(map.get(wpgid));
			chasSswpxgService.save(chasSswpxg);

		}

		webResult.setCode(1);
		webResult.setMessage(String.format("同步储物柜数量:%d", devList.size()));
		return webResult;
	}
}
