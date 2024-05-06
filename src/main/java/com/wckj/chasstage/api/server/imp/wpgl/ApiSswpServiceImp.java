package com.wckj.chasstage.api.server.imp.wpgl;

import com.wckj.chasstage.api.def.wpgl.model.QwjlParam;
import com.wckj.chasstage.api.def.wpgl.model.SswpBean;
import com.wckj.chasstage.api.def.wpgl.model.SswpParam;
import com.wckj.chasstage.api.def.wpgl.model.SswpQwjlParam;
import com.wckj.chasstage.api.def.wpgl.service.ApiSswpService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.*;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baq.service.ChasBaqrefService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.qwjl.entity.ChasQwjl;
import com.wckj.chasstage.modules.qwjl.service.ChasQwjlService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.sswp.service.ChasSswpxxService;
import com.wckj.chasstage.modules.wpxg.entity.ChasSswpxg;
import com.wckj.chasstage.modules.wpxg.service.ChasSswpxgService;
import com.wckj.chasstage.modules.wpxg.service.UnusedLockers;
import com.wckj.chasstage.modules.zpxx.service.ChasSswpZpxxService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.dic.DicUtil;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName : ApiSbglService  //类名
 * @Description : 随身物品  //描述
 * @Author : lcm  //作者
 * @Date: 2020-09-08 16:19  //时间
 */
@Service
public class ApiSswpServiceImp implements ApiSswpService {
    @Autowired
    private ChasSswpxxService chasSswpxxService;
    @Autowired
    private ChasRyjlService ryjlService;
    @Autowired
    private ChasBaqService baqService;
    @Autowired
    private ChasSswpZpxxService zpxxService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private ChasBaqrefService baqrefService;
    @Autowired
    private JdoneSysUserService userService;
    @Autowired
    private ChasSswpxgService sswpxgService;
    @Autowired
    private ChasQwjlService qwjlService;

    /**
     * @param param
     * @return com.wckj.framework.api.ApiReturnResult<java.lang.String>  //返回值
     * @throws //异常
     * @description 查询物品分页数据//描述
     * @author lcm //作者
     * @date 2020/9/10 11:04 //时间
     */
    @Override
    public ApiReturnResult<String> getSswpApiPageData(SswpParam param) {

        ApiReturnResult apiReturnResult = new ApiReturnResult();
        try {
            Map<String, Object> params = MapCollectionUtil.objectToMap(param);
            DataQxbsUtil.getSelectAll(chasSswpxxService, params);
            Map<String, Object> objectMap = chasSswpxxService.selectAll(param.getPage(), param.getRows(), params, "lrsj desc");
            apiReturnResult.setData(objectMap);
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("查询数据成功");
        } catch (Exception e) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(e.getMessage());
            e.printStackTrace();
        }

        return apiReturnResult;
    }


    @Override
    public ApiReturnResult<String> getCyrApiPageData(Integer page, Integer rows, String ryxm) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("ryxm", ryxm);
            Map<String, Object> objectMap = chasSswpxxService.selectCyrAll(page, rows, params, "");
            apiReturnResult.setData(objectMap);
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("查询数据成功");
        } catch (Exception e) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(e.getMessage());
            e.printStackTrace();
        }

        return apiReturnResult;
    }

    /**
     * @param bean
     * @return com.wckj.framework.api.ApiReturnResult<java.lang.String>  //返回值
     * @throws //异常
     * @description 保存或新增物品//描述
     * @author lcm //作者
     * @date 2020/9/10 11:03 //时间
     */
    @Override
    public ApiReturnResult<String> saveOrUpdateSswp(SswpBean bean) {

        ApiReturnResult apiReturnResult = new ApiReturnResult();
        DevResult result = chasSswpxxService.SaveWithUpdate_Form(bean);
        if (result.getCode() == 1) {
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage(result.getMessage());
            apiReturnResult.setData(result.getData());
        } else {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(result.getMessage());

        }
        return apiReturnResult;
    }

    /**
     * @param ids
     * @return com.wckj.framework.api.ApiReturnResult<java.lang.String>  //返回值
     * @throws //异常
     * @description 删除物品//描述
     * @author lcm //作者
     * @date 2020/9/10 11:03 //时间
     */
    @Override
    public ApiReturnResult<String> deleteSswp(String ids) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();

        Map<String, Object> map = chasSswpxxService.deleteSswpxxByIds(ids);
        boolean flag = Boolean.valueOf(map.get("success").toString());
        if (flag) {
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("物品信息删除成功");
        } else {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("物品信息删除失败");
        }
        return apiReturnResult;

    }

    /**
     * @param id
     * @return com.wckj.framework.api.ApiReturnResult<java.lang.String>  //返回值
     * @throws //异常
     * @description 根据wpid查询物品照片   //描述
     * @author lcm //作者
     * @date 2020/9/10 11:01 //时间
     */
    @Override
    public ApiReturnResult<String> getWpxxImgs(String id) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();

        Map<String, Object> map = chasSswpxxService.getImageByWpid(id);
        String imgsize = String.valueOf(map.get("imgsize"));
        if (!"0".equals(imgsize)) {
            apiReturnResult.setCode("200");
            List<Map<String, Object>> listmap = (List<Map<String, Object>>) map.get("imgs");
            apiReturnResult.setData(listmap);
            apiReturnResult.setMessage("获取照片信息成功");
        } else {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("获取照片信息为空");
        }
        return apiReturnResult;
    }

    /**
     * @param ids
     * @param wplhzt
     * @return com.wckj.framework.api.ApiReturnResult<java.lang.String>  //返回值
     * @throws //异常
     * @description 物品领回//描述
     * @author lcm //作者
     * @date 2020/9/10 11:03 //时间
     */
    @Override
    public ApiReturnResult<String> sswplh(String ids, String wplhzt) {

        ApiReturnResult apiReturnResult = new ApiReturnResult();

        Map<String, Object> map = chasSswpxxService.takeBack(ids, wplhzt);
        boolean flag = Boolean.valueOf(map.get("success").toString());
        if (flag) {
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("物品领回成功");
        } else {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("物品领回失败");
        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<String> getZpxxByRybh(String rybh,String zplx) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();

        Map<String, Object> map = chasSswpxxService.findZpxxByRybh(rybh,zplx);
        String imgsize = String.valueOf(map.get("imgsize"));
        if (!"0".equals(imgsize)) {
            apiReturnResult.setCode("200");
            apiReturnResult.setData(map);
            apiReturnResult.setMessage("获取照片信息成功");
        } else {
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("获取照片信息为空");
        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<String> getWpxxByRybh(String rybh) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();

        Map<String, Object> map = chasSswpxxService.findWpxxByRybh(rybh);
        boolean flag = Boolean.valueOf(map.get("success").toString());
        if (flag) {
            List listmap = (List) map.get("data");
            List translateListMap = DicUtil.translate(listmap, new String[]{"WPLB"}, new String[]{"lb"});
            apiReturnResult.setData(translateListMap);
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("获取物品数据成功");
        } else {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(map.get("msg").toString());
        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<String> getWpxxById(String id) {
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        Map<String, Object> map = chasSswpxxService.findWpxxById(id);
        boolean flag = Boolean.valueOf(map.get("success").toString());
        if (flag) {
            List listmap = (List) map.get("data");
            apiReturnResult.setData(listmap);
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage("获取物品数据成功");
        } else {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(map.get("msg").toString());
        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<String> uploadPz(String bizId, String base64, String bizType,String sfsc) {
        if(SYSCONSTANT.SF_S.equals(sfsc)){
            try {
                List<FileInfoObj> fileInfoList = FrwsApiForThirdPart.getFileInfoList(bizId);
                for (int i = 0; i < fileInfoList.size(); i++) {
                    FileInfoObj fileInfoObj = fileInfoList.get(i);
                    if(fileInfoObj != null && StringUtils.isNotEmpty(fileInfoObj.getId())){
                        FrwsApiForThirdPart.deleteFileByFileId(fileInfoObj.getId());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        DevResult result = zpxxService.uploadpz(bizId, base64, bizType);
        ApiReturnResult apiReturnResult = new ApiReturnResult();

        if (result.getCode() == 1) {
            apiReturnResult.setCode("200");
            apiReturnResult.setMessage(result.getMessage());
        } else {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage(result.getMessage());
        }
        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<?> getQwjlApiPageData(SswpQwjlParam param) {
        PageDataResultSet<Map<String,Object>> entityPageData = qwjlService.getQwjlApiPageData(param.getPage(),
                param.getRows(),
                ParamUtil.builder()
                        .accept("rybh", param.getRybh())
                        .accept("cabId", param.getCabId())
                        .accept("czlx",param.getCzlx())
                        .toMap(), null);
        return ResultUtil.ReturnSuccess(entityPageData);
    }

    @Override
    public ApiReturnResult<String> getWpgByDwdm(String baqid, String lb) {
        Map<String, Object> result = new HashMap<String, Object>();
        ApiReturnResult apiReturnResult = new ApiReturnResult();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            List<String> baqids = new ArrayList<String>();
            if (StringUtil.isEmpty(baqid)) {
                baqid = baqrefService.getBaqidByUser();
            }
            baqids.add(baqid);
            param.put("baqList", baqids);
            List<ChasSswpxg> sswpxgs = new ArrayList<>();
            List<ChasSswpxg> sswpxgList = sswpxgService.findList(param, null);  //查询所有,用于选择人员时，回显所选物品柜
            UnusedLockers unusedLockers = sswpxgService.getUnusedLockers(baqids);
            if (StringUtil.isNotEmpty(lb)) {
                if (lb.equals("sj")) {
                    sswpxgs.addAll(unusedLockers.getUnusedPhoneData());
                }
                //qt 默认为查询物品柜
                if (lb.equals("qt")) {
                    sswpxgs.addAll(unusedLockers.getUnusedThingData());
                }
            }
            result.put("wpgListAll", JSONArray.fromObject(sswpxgList));
            result.put("wpgList", JSONArray.fromObject(sswpxgs));

//            }
//            result.put("wplb", bean.getLb());
            apiReturnResult.setData(result);
        } catch (Exception e) {
            apiReturnResult.setCode("500");
            apiReturnResult.setMessage("物品柜数据获取失败");
        }

        return apiReturnResult;
    }

    @Override
    public ApiReturnResult<String> saveQwjl(QwjlParam param) {
        Map<String, Object> result = new HashMap<>();
        if (StringUtils.isEmpty(param.getCabId()) ||
                StringUtil.isEmpty(param.getMjsfz()) ||
                StringUtils.isEmpty(param.getCzlx())) {
            return ResultUtil.ReturnError("参数不能为空!");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("sjgId", param.getCabId());
        params.put("ryzt", 1);
        List<ChasRyjl> ryjls = ryjlService.findList(params, null);
        if (ryjls.isEmpty()) {
            return ResultUtil.ReturnError("根据手机柜ID查询不到人员记录!");
        }
        ChasRyjl ryjl = ryjls.get(0);
        ChasBaqryxx baqryxx = baqryxxService.findRyxxBywdbhL(ryjl.getWdbhL());
        ChasQwjl chasQwjl = new ChasQwjl();
        chasQwjl.setId(StringUtils.getGuid32());
        chasQwjl.setBaqid(baqryxx.getBaqid());
        chasQwjl.setBaqmc(baqryxx.getBaqmc());
        chasQwjl.setCyr(baqryxx.getRyxm());
        chasQwjl.setCzmjSfzh(param.getMjsfz());
        chasQwjl.setCzmjXm(param.getMjxm());
        chasQwjl.setLrrSfzh(param.getMjsfz());
        chasQwjl.setLrsj(new Date());
        chasQwjl.setCzsj(new Date());
        chasQwjl.setDataFlag("0");
        chasQwjl.setDwxtbh(DicUtil.translate("JDONE_SYS_SYSCODE", baqryxx.getZbdwBh()));
        chasQwjl.setZbdwBh(baqryxx.getZbdwBh());
        chasQwjl.setIsdel(SYSCONSTANT.ALL_DATA_MARK_NORMAL_I);
        chasQwjl.setRybh(baqryxx.getRybh());
        chasQwjl.setCzlx(param.getCzlx());
        chasQwjl.setCabId(param.getCabId());
        //保存物品柜编号
        ChasSswpxg sswpxg = sswpxgService.findById(param.getCabId());
        chasQwjl.setCabBh(sswpxg.getBh());
        qwjlService.save(chasQwjl);
        return ResultUtil.ReturnSuccess("取物记录保存成功!");
    }
}
