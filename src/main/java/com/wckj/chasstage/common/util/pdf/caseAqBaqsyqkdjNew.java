package com.wckj.chasstage.common.util.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.wckj.chasstage.common.util.ConvertNumberToChinese;
import com.wckj.chasstage.common.util.DateTimeUtils;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.common.util.pdf.event.PdfCheckBoxEvent;
import com.wckj.chasstage.common.util.pdf.event.PdfObliqueLineCellEvent;
import com.wckj.chasstage.common.util.pdf.event.PdfPageEvent;
import com.wckj.chasstage.common.util.pdf.event.PdfUnderDashedLineCellEvent;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.cssp.entity.ChasYmCssp;
import com.wckj.chasstage.modules.cssp.service.ChasYmCsspService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.sign.entity.ChasSign;
import com.wckj.chasstage.modules.sign.service.ChasSignService;
import com.wckj.chasstage.modules.sswp.entity.ChasSswpxx;
import com.wckj.chasstage.modules.sswp.service.ChasSswpxxService;
import com.wckj.chasstage.modules.zpxx.entity.ChasSswpZpxx;
import com.wckj.chasstage.modules.zpxx.service.ChasSswpZpxxService;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.json.jackson.JsonUtil;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.springframework.web.context.ContextLoader;
import sun.misc.BASE64Decoder;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class caseAqBaqsyqkdjNew extends caseAqBaqsyqkdj {
    protected Logger log = LoggerFactory.getLogger(caseAqBaqsyqkdjNew.class);

	private ChasSswpxxService sswpxxService = ContextLoader.getCurrentWebApplicationContext().getBean(ChasSswpxxService.class);
	private ChasBaqryxxService ryxxService = ContextLoader.getCurrentWebApplicationContext().getBean(ChasBaqryxxService.class);
	private ChasSignService chasSignService = ContextLoader.getCurrentWebApplicationContext().getBean(ChasSignService.class);
	private ChasBaqService chasBaqService = ContextLoader.getCurrentWebApplicationContext().getBean(ChasBaqService.class);
	private ChasYmCsspService csspService = ContextLoader.getCurrentWebApplicationContext().getBean(ChasYmCsspService.class);
	private ChasYwRygjService rygjService = ContextLoader.getCurrentWebApplicationContext().getBean(ChasYwRygjService.class);
	private ChasXtQyService chasQyService = ContextLoader.getCurrentWebApplicationContext().getBean(ChasXtQyService.class);
	private ChasSswpZpxxService zpxxService = ContextLoader.getCurrentWebApplicationContext().getBean(ChasSswpZpxxService.class);

	public void createAqjcqkTable(Document document, PdfWriter writer,float topHeight, ChasBaqryxx chasBaqryxx, Map<String, String> signmap) throws Exception {
		if (StringUtils.isEmpty(chasBaqryxx.getRybh())) {
			return;
		}

		// 人员安全检查表格
		PdfPTable stjcjl = new PdfPTable(10);

		stjcjl.setSplitLate(false);
		stjcjl.setSplitRows(true);

		stjcjl.setHorizontalAlignment(Element.ALIGN_CENTER);
		stjcjl.setWidthPercentage(100);
		stjcjl.setWidths(new int[] { 38, 25, 69, 65, 64, 60, 25, 42, 32, 120 });
		stjcjl.setTotalWidth(540);
		stjcjl.setSpacingBefore(0);

		PdfPCell td1 = PdfPrintUtil.getTableCell("安\n\n全\n\n检\n\n查\n\n情\n\n况",
				PdfPrintUtil.getCnFSongHFont(12, Font.BOLD), 0,
				qdTableRowMiniHeight);
		td1.setRowspan(6);
		stjcjl.addCell(td1);

		PdfPCell td2 = PdfPrintUtil.getTableCell("人身检查记录",
				PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
				qdTableRowMiniHeight);
		td2.setRowspan(3);
		stjcjl.addCell(td2);

		PdfPTable stjcTable = new PdfPTable(2);

		stjcTable.setHorizontalAlignment(Element.ALIGN_CENTER);
		stjcTable.setWidthPercentage(100);
		stjcTable.setWidths(new int[] { 50, 50 });

		// 自述症状
		String zszzStart = "    自述症状:（既往病史、是否饮酒、是否患有传染性等疾病）";
		String jbqk = StringUtils.clearNull(chasBaqryxx.getRYzjb());
		jbqk = StringUtils.isEmpty(jbqk) ? "无" : jbqk;
		PdfPCell jbqkCell = PdfPrintUtil.getNoBorderTableCell(zszzStart + "  "
				+ jbqk, qdTableContentFont, 0, 0, Element.ALIGN_LEFT,
				Element.ALIGN_TOP);
		jbqkCell.setLeading(15, 0);
		jbqkCell.setMinimumHeight(97);
		jbqkCell.setPaddingBottom(5);
		jbqkCell.setCellEvent(new PdfUnderDashedLineCellEvent(zszzStart, jbqk,5, 12));
		jbqkCell.setBorderWidthBottom(0.7f);
		stjcTable.addCell(jbqkCell);

        // 身体检查图片
        String src = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+"/static/chas/pdf/imgs/stjcPtoto.jpg";

        String jcqk = StringUtils.clearNull(chasBaqryxx.getRSsms());
        String jcqkStr = "";
        if(chasBaqryxx.getRSfyzjb() != null && chasBaqryxx.getRSfyzjb() == 1){
        	jcqkStr += "1.有过往病史;";
		}else{
			jcqkStr += "1.无过往病史;";
		}
        if(chasBaqryxx.getRSfssjc() != null && chasBaqryxx.getRSfssjc() == 1){
			jcqkStr += "2.有伤情;";
		}else{
			jcqkStr += "2.无伤情;";
		}
        if(chasBaqryxx.getrSfyj() != null && chasBaqryxx.getrSfyj() == 1){
			jcqkStr += "3.有饮酒;";
		}else{
			jcqkStr += "3.无饮酒;";
		}
        byte[] bytes = null;
        //解析json字符串
        if(StringUtils.isNotEmpty(jcqk) && !StringUtils.equals(jcqk,"无")){
            List<Map> maps = JsonUtil.getListFromJsonString(jcqk,Map.class);
            if(maps != null && !maps.isEmpty()){
				int inx = 1;
				jcqkStr += "4.伤情描述:";
				for(Map<String,Object> map : maps){
					jcqkStr += "("+(inx++)+")"+map.get("part") +":"+map.get("desc")+";";
				}
				bytes = drawImagetoLargePicture(src,maps);
			}
        }
        if(StringUtils.isEmpty(jcqkStr)){
            jcqkStr = "无";
        }
        if(bytes == null){
			bytes = drawImagetoLargePicture(src,null);
		}
        //Image stjcImage = Image.getInstance(src);
        Image stjcImage = new Jpeg(bytes);
        stjcImage.scaleAbsolute(220, 190);
        stjcImage.setScaleToFitHeight(false);
        stjcImage.setAlignment(Image.ALIGN_CENTER);

        PdfPCell rightCell = new PdfPCell(stjcImage);
		rightCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		rightCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		rightCell.setRowspan(2);
		stjcTable.addCell(rightCell);

		// 检查情况
		String jcStart = "    检查情况:（体表是否有伤痕、是否饮酒以及全身检查情况）";
		PdfPCell jcCell = PdfPrintUtil.getNoBorderTableCell(jcStart + "  "
				+ jcqkStr, qdTableContentFont, 0, 0, Element.ALIGN_LEFT,
				Element.ALIGN_TOP);
		jcCell.setLeading(15, 0);
		jcCell.setMinimumHeight(97);
		jcCell.setPaddingBottom(5);
		jcCell.setCellEvent(new PdfUnderDashedLineCellEvent(zszzStart, jbqk, 5,
				12));
		stjcTable.addCell(jcCell);

		PdfPCell sqqkCell = new PdfPCell(stjcTable);

		sqqkCell.setColspan(8);
		sqqkCell.setPaddingLeft(3);
		stjcjl.addCell(sqqkCell);

		String kssj = "";
		if (chasBaqryxx.getAqjckssj() != null) {
			kssj = DateTimeUtils.getDateStr(chasBaqryxx.getAqjckssj(), 15);
		}
		String jssj = "";
		if (chasBaqryxx.getAqjcjssj() != null) {
			jssj = DateTimeUtils.getDateStr(chasBaqryxx.getAqjcjssj(), 15);
		}
		PdfPCell jzrCells2 = PdfPrintUtil.getTableCell("安全检查时间段",
				qdTableContentFont, 0, qdTableRowMiniHeight);
		jzrCells2.setColspan(2);
		stjcjl.addCell(jzrCells2);
		PdfPCell jzrCell1 = PdfPrintUtil.getTableCell(kssj + " --- " + jssj,
				qdTableContentFont, 0, qdTableRowMiniHeight);
		jzrCell1.setColspan(6);
		stjcjl.addCell(jzrCell1);

		// -------
		stjcjl.addCell(PdfPrintUtil.getTableCell("检查民警（签字）",
				qdTableContentFont, 0, qdTableRowMiniHeight));
		PdfPCell s = imgHandle(qmny[2], signmap.get(qmny[2]), 0, 50, 23,true);

		// -----
		s.setHorizontalAlignment(Element.ALIGN_CENTER);
		s.setPaddingTop(4);
		stjcjl.addCell(s);
		stjcjl.addCell(PdfPrintUtil.getTableCell("见证人 （签字）",
				qdTableContentFont, 0, qdTableRowMiniHeight));
		PdfPCell jzrCell = imgHandle(qmny[3], signmap.get(qmny[3]), 0, 50, 20,true);

		jzrCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		jzrCell.setPaddingTop(8);
		stjcjl.addCell(jzrCell);

		PdfPCell bjcrCell = PdfPrintUtil.getTableCell("被检查人\n(签字捺印)",
				qdTableContentFont, 0, qdTableRowMiniHeight);
		bjcrCell.setColspan(2);
		stjcjl.addCell(bjcrCell);
		PdfPCell bjcr = imgHandle("", signmap.get(""), 0,60, 30,true);
		bjcr.setBorderWidthLeft(0);
		bjcr.setBorderWidthRight(0);
		bjcr.setBorderWidthTop(0);
		bjcr.setBorderWidthBottom(0);
		bjcr.setPadding(3);
		bjcr.setPaddingLeft(15);

		PdfPCell bjcrzw = null;
		if(isNewDoc()){
			byte[] hb = PdfComprehensiveUtil.hbqznytp(signmap.get(qmny[4]),signmap.get(qmny[5]),true);
			if(hb != null && hb.length > 0){
				bjcrzw = imgHandle(qmny[4]+qmny[5], Base64.getEncoder().encodeToString(hb), 0, 100, 27,false);
			}else{
				bjcrzw = imgHandle(qmny[4]+qmny[5], "", 0, 100, 27,false);
			}
		}else{
			bjcrzw = imgHandle(qmny[4]+qmny[5], signmap.get(qmny[4]+qmny[5]), 0, 100, 30,false);
		}
		bjcrzw.setBorderWidthLeft(0);
        bjcrzw.setBorderWidthTop(0);
        bjcrzw.setBorderWidthBottom(0);
		//bjcrzw.setPaddingLeft(68);

		bjcr.setHorizontalAlignment(Element.ALIGN_RIGHT);
		stjcjl.addCell(bjcr);
		stjcjl.addCell(bjcrzw);

		// 随身物品处理开始//----
		PdfPTable zptable = null;
		String sswpMessage = "";
		PdfObliqueLineCellEvent[] obliqueLineEvents = null;
		int sHeight = 25;
		if (chasBaqryxx.getSfznbaq() == 0) {
			// 非智能办案区
			sswpMessage = "随身财物检查记录";
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("rybh", chasBaqryxx.getRybh());
			List<ChasSswpxx> sswpxxList = sswpxxService.findList(param, "");

			// 添加子表
			zptable = new PdfPTable(6);
			zptable.setWidths(new int[] { 10, 20, 10, 20, 20, 20 });
			zptable.setWidthPercentage(100);
			zptable.setTotalWidth(200);

			// 添加头部行
			zptable.addCell(PdfPrintUtil.getTableCell("编号", qdTableContentFont,
					0, sHeight));
			zptable.addCell(PdfPrintUtil.getTableCell("名称", qdTableContentFont,
					0, sHeight));
			zptable.addCell(PdfPrintUtil.getTableCell("数量", qdTableContentFont,
					0, sHeight));
			zptable.addCell(PdfPrintUtil.getTableCell("特征", qdTableContentFont,
					0, sHeight));
			zptable.addCell(PdfPrintUtil.getTableCell("是否涉案",
					qdTableContentFont, 0, sHeight));
			zptable.addCell(PdfPrintUtil.getTableCell("保管柜号",
					qdTableContentFont, 0, sHeight));
			// 表格最大高度
			float maxTableHeight = 203;

			// 当前表格高度
			float currentTableHeight = zptable.getRowHeight(0);

			// 清单列数
			int columnNum = 6;

			// 序号计数
			int num = 1;

			for (int i = 0, len = sswpxxList.size(); i < len; i++) {
				ChasSswpxx sswpxx = sswpxxList.get(i);
				zptable.addCell(PdfPrintUtil.getTableCell(String.valueOf(num),qdTableContentFont, 0, sHeight));
				zptable.addCell(PdfPrintUtil.getTableCell(sswpxx.getMc(),qdTableContentFont, 0, sHeight));
				zptable.addCell(PdfPrintUtil.getTableCell(ConvertNumberToChinese.CnUpperCaser(sswpxx.getSl())+sswpxx.getDw(),qdTableContentFont, 0, sHeight));
				zptable.addCell(PdfPrintUtil.getTableCell(sswpxx.getTz() == null ? "" : sswpxx.getTz(),	qdTableContentFont, 0, sHeight));

				PdfPCell sfsa = PdfPrintUtil.getTableCell("",	PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,qdTableRowMiniHeight);
				sfsa.setCellEvent(new PdfCheckBoxEvent("", 20, new String[] {"是", "否" }, new boolean[] { false, false }));
				zptable.addCell(sfsa);

				// 物品在库 显示位置 不在显示状态
				zptable.addCell(PdfPrintUtil.getTableCell(DicUtil.translate("WPZT", sswpxx.getWpzt()),qdTableContentFont, 0, sHeight));
				currentTableHeight += zptable.getRowHeight(num);
				num++;
			}

			// 斜线单元格处理
			obliqueLineEvents = PdfPrintUtil.buildElseRow(zptable,maxTableHeight, currentTableHeight, columnNum, sHeight);

		} else {
			// 智能办案区
			sswpMessage = "随身财物照片";
			zptable = new PdfPTable(2);
			zptable.setWidthPercentage(100);

			String[] zpUrls = getWpZpUrls(chasBaqryxx);
			for (int i = 0; i < zpUrls.length; i++) {
				Image image = null;
				if (StringUtils.isNotEmpty(zpUrls[i])) {
					try {
						image = Image.getInstance(zpUrls[i]);
						image.scaleAbsolute(220, 165);
						image.setScaleToFitHeight(false);
						image.setAlignment(Image.ALIGN_CENTER);
					} catch (Exception e) {
						image = null;
						e.printStackTrace();
						log.error("登记表组装PDF数据",e);
					}
				}
				PdfPCell zpCell = null;
				if (image != null) {
					zpCell = new PdfPCell(image, false);
					zpCell.setFixedHeight(210);
				} else {
					zpCell = PdfPrintUtil.getTableCell("", qdTableContentFont,
							0, 210);
				}
				zpCell.setBorder(0);
				zpCell.setPadding(2);
				zpCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				zpCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				zptable.addCell(zpCell);
			}
		}
		PdfPCell td3 = PdfPrintUtil.getTableCell(sswpMessage,
				PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
				qdTableRowMiniHeight);
		td3.setRowspan(3);
		stjcjl.addCell(td3);

		PdfPCell sswpCell = new PdfPCell();
		sswpCell.setColspan(8);
		sswpCell.addElement(zptable);

		stjcjl.addCell(sswpCell);

		PdfPCell saryCell0 = PdfPrintUtil.getTableCell("办案人员（签字）",
				qdTableContentFont, 0, qdTableRowMiniHeight);
		PdfPCell saryCell2 = imgHandle(qmny[6], signmap.get(qmny[6]), 0, 40, 50,true);
		saryCell2.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell saryCell3 = PdfPrintUtil.getTableCell("随身财物\n管理员\n（签字）",
				qdTableContentFont, 0, qdTableRowMiniHeight);
		PdfPCell saryCell4 = imgHandle(qmny[7], signmap.get(qmny[7]), 0, 40, 40,true);
		saryCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		saryCell4.setPaddingTop(12);

		PdfPCell saryCell = PdfPrintUtil.getTableCell("涉案人员\n(签字捺印)",
				qdTableContentFont, 0, qdTableRowMiniHeight);
		saryCell.setColspan(2);
		saryCell2.setRowspan(2);
		saryCell3.setRowspan(2);
		saryCell4.setRowspan(2);
		saryCell0.setRowspan(2);
		saryCell.setRowspan(2);
		saryCell.setFixedHeight(55);
		stjcjl.addCell(saryCell0);
		stjcjl.addCell(saryCell2);
		stjcjl.addCell(saryCell3);
		stjcjl.addCell(saryCell4);
		stjcjl.addCell(saryCell);

		// 涉案人员签字表格

		PdfPCell saryQxCell = PdfPrintUtil.getTableCell(
				"本人自愿将以上个人随身物品存放进(电子)保管柜。",
				PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
				qdTableRowMiniHeight, Element.ALIGN_LEFT, Element.ALIGN_TOP);
		saryQxCell.setColspan(2);
		saryQxCell.setBorderWidthLeft(0);
		saryQxCell.setBorderWidthBottom(0);
		saryQxCell.setBorderWidthTop(0);

		stjcjl.addCell(saryQxCell);

		// 签字标记

		PdfPCell ch = imgHandle("", signmap.get(""), 0, 50,25,true);
		PdfPCell chzw = null;
		if(isNewDoc()){
			byte[] hb = PdfComprehensiveUtil.hbqznytp(signmap.get(qmny[8]),signmap.get(qmny[9]),true);
			if(hb != null && hb.length > 0){
				chzw = imgHandle(qmny[8]+qmny[9], Base64.getEncoder().encodeToString(hb), 0, 100, 25,false);
			}else{
				chzw = imgHandle(qmny[8]+qmny[9], "", 0, 100, 25,false);
			}
		}else{
			chzw = imgHandle(qmny[8]+qmny[9], signmap.get(qmny[8]+qmny[9]), 0, 100, 25,false);
		}
		ch.setBorderWidthLeft(0);
		ch.setBorderWidthTop(0);
		ch.setBorderWidthRight(0);
		ch.setPaddingLeft(5);
		ch.setPaddingTop(-5);
        ch.setHorizontalAlignment(Element.ALIGN_RIGHT);
		//ch.setPaddingTop(-10);
		chzw.setBorderWidthLeft(0);
		chzw.setBorderWidthTop(0);
		chzw.setPaddingLeft(50);
		chzw.setPaddingTop(-5);
		stjcjl.addCell(ch);
		stjcjl.addCell(chzw);
		document.add(stjcjl);
		// 画斜线
		PdfPrintUtil.writeObliqueLine(writer, obliqueLineEvents, sHeight);

	}

	// 获取物品照片地址//------
	private String[] getWpZpUrls(ChasBaqryxx chasBaqryxx) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("rybh", chasBaqryxx.getRybh());
		param.put("zplx", "01");

		List<ChasSswpZpxx> rs = zpxxService.findList(param, "lrsj desc");

		param.put("zplx", "03");

		List<ChasSswpZpxx> cs = zpxxService.findList(param, "lrsj desc");

		List<ChasSswpZpxx> list = new ArrayList<ChasSswpZpxx>();

		if (rs.size() > 0)
			list.add(rs.get(0));
		if (cs.size() > 0)
			list.add(cs.get(0));

		int i = 0;
		String[] result = new String[2];
		for (ChasSswpZpxx zpjl : list) {
		    try{
		    	if(StringUtil.isNotEmpty(zpjl.getBizId())){
					FileInfoObj infoObj = FrwsApiForThirdPart.getFileInfoByBizId(zpjl.getBizId());
					if(infoObj != null){
						result[i] = infoObj.getDownUrl();
						i++;
					}
				}
            }catch (Exception e){
		        log.error("getWpZpUrls:",e);
            }
		}
		return result;
	}

	// 获取系统区域
	private Map<String, ChasXtQy> getXtQy(ChasBaqryxx chasBaqryxx) {
		Map<String, ChasXtQy> result = new HashMap<String, ChasXtQy>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("baqid", chasBaqryxx.getBaqid());
		List<ChasXtQy> chasxtqylist = chasQyService.findList(param, null);
		for (ChasXtQy chasxtqy : chasxtqylist) {
			result.put(chasxtqy.getYsid(), chasxtqy);
		}
		return result;
	}

	// 活动内容
	private String hdlr(String roomtype) {
		// 登记1采集2审讯3看守4其他5餐饮9
		if (SYSCONSTANT.ZD_CASE_FJLX_DJQ.equals(roomtype)) {
			return "入所登记";
		} else if (SYSCONSTANT.ZD_CASE_FJLX_XXCJS.equals(roomtype)) {
			return "信息采集";
		} else if (SYSCONSTANT.ZD_CASE_FJLX_KSQ.equals(roomtype)) {
			return "休息";
			// return "0200".equals(rylx)?"等待讯问":"等待询问";
		} else if (SYSCONSTANT.ZD_CASE_FJLX_SXS.equals(roomtype)) {
			return "审讯中";
			// return "0200".equals(rylx)?"讯问中":"询问中";
		} else if (SYSCONSTANT.ZD_CASE_FJLX_QT.equals(roomtype)) {
			return "休息";
		} else if (SYSCONSTANT.ZD_CASE_FJLX_CY.equals(roomtype)) {
			return "餐饮";
		} else if(SYSCONSTANT.ZD_CASE_FJLX_NJQ.equals(roomtype)){
			return "检测";
		}
		return "";
	}

	// 获取人员轨迹信息
	private List<ChasRygj> getRyGj(ChasBaqryxx chasBaqryxx) {
		List<ChasRygj> result = new ArrayList<ChasRygj>();
		List<ChasRygj> rygjList = new ArrayList<>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("rybh", chasBaqryxx.getRybh());
		param.put("baqid", chasBaqryxx.getBaqid());
		param.put("temporary","60");  //秒
		rygjList = rygjService.findList(param, "kssj asc");
		if (chasBaqryxx.getSfznbaq() == 0) {
			return rygjList;
		}
		Map<String, ChasXtQy> xtqtmap = getXtQy(chasBaqryxx);
		//根据区域合并轨迹，如果其中有其他区域轨迹，那么则不予合并,只合并连续的轨迹数据
		ChasRygj temporary = null;
		for (ChasRygj rygj : rygjList) {
			ChasXtQy qy = xtqtmap.get(rygj.getQyid());
			if(qy != null && !"1,2,3,4,8,9,11".contains(qy.getFjlx())){  //如果不符合规定房间类型则过滤
				continue;
			}
			if(result.isEmpty()){
				result.add(rygj);
				temporary = rygj;
			}else{
				if(StringUtil.equals(temporary.getQyid(),rygj.getQyid())){
					ChasRygj rygj1 = result.get(result.size() - 1);
					rygj1.setJssj(rygj.getJssj());
				}else{
					result.add(rygj);
					temporary = rygj;
				}
			}
		}
		return result;
	}

	// 构建人员轨迹记录
	// 询问、讯问、等候、休息、饮食等活动记录
	public PdfPTable buildRyGj(ChasBaqryxx chasBaqryxx) throws Exception {
		log.error(String.format("人员姓名：%s 人员编号：%s 进入轨迹方法开始",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));
		PdfPTable yyhdTable = new PdfPTable(8);
		yyhdTable.setHorizontalAlignment(Element.ALIGN_CENTER);
		yyhdTable.setWidthPercentage(100);
		yyhdTable.setWidths(new int[] { 38, 80, 24, 80, 70, 82, 60, 100 });
		yyhdTable.setTotalWidth(540);
		yyhdTable.setSpacingBefore(0);

		PdfPCell hdTitCell = PdfPrintUtil.getTableCell(
				"询\n问\n、\n讯\n问\n、\n等\n候\n、\n休\n息\n、\n饮\n食\n等\n活\n动\n记\n录",
				PdfPrintUtil.getCnFSongHFont(12, Font.BOLD), 0,
				qdTableRowMiniHeight);
		hdTitCell.setRowspan(11);
		yyhdTable.addCell(hdTitCell);

		// 插入表头
		PdfPCell sjtdCell = PdfPrintUtil.getTableCell("时    间",
				PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
				qdTableRowMiniHeight);
		sjtdCell.setColspan(3);
		yyhdTable.addCell(sjtdCell);

		yyhdTable.addCell(PdfPrintUtil.getTableCell("房间名称",
				PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
				qdTableRowMiniHeight));

		PdfPCell hdnr = PdfPrintUtil.getTableCell("活动内容",
				PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
				qdTableRowMiniHeight);
		// hdnr.setColspan(2);
		yyhdTable.addCell(hdnr);
		yyhdTable.addCell(PdfPrintUtil.getTableCell("备注",
				PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
				qdTableRowMiniHeight));
		yyhdTable.addCell(PdfPrintUtil.getTableCell("签名",
				PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
				qdTableRowMiniHeight));

		// 依据人员编号 获取人员轨迹信息
		log.error(String.format("人员姓名：%s 人员编号：%s 查询轨迹数据开始",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));
		List<ChasRygj> rygjList = getRyGj(chasBaqryxx);
		log.error(String.format("人员姓名：%s 人员编号：%s 查询轨迹数据结束",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));
		Map<String, ChasXtQy> xtqtmap = getXtQy(chasBaqryxx);
		log.error(String.format("人员姓名：%s 人员编号：%s 查询区域数据结束",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));
		final int rygjSize = rygjList.size();
		int xhs = 11,current_inx = 0;  //规定最多显示10条
		int isdengji = 0;
		int isxxcj = 0;
		// 循环增加活动记录
		log.error(String.format("人员姓名：%s 人员编号：%s 组装轨迹cell开始",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));
		for (int i = 0; i < xhs; i++) {
			String kssj = "  日  时  分";
			String jssj = "  日  时  分";
			String room = "";
			String scout = "";
			String event = "";
			String remark = "";

			if (i < rygjSize) {
                ChasRygj map = new ChasRygj();
                if(current_inx < rygjSize){
                    map = rygjList.get(current_inx++);
                }
				ChasXtQy chasXtQy = xtqtmap.get(map.getQyid());
				if (map.getKssj() != null) {
					kssj = DateTimeUtils.getDateFormat(map.getKssj(),"dd日HH时mm分");
				}
				if (map.getJssj() != null) {
					jssj = DateTimeUtils.getDateFormat(map.getJssj(),"dd日HH时mm分");
				}
				if (current_inx == rygjSize - 1)
					jssj = map.getJssj() != null ? jssj : DateTimeUtils.getDateFormat(new Date(), "dd日HH时mm分");
				room = map.getQymc();
				if(chasXtQy!=null){
					event= StringUtils.isEmpty(map.getHdnr()) ? hdlr(chasXtQy.getFjlx()): map.getHdnr();
				}else{
					event=map.getHdnr();
				}
				remark = map.getBz();

                if (chasXtQy != null) {
                    log.info("区域名称:"+chasXtQy.getQymc()+",区域类型:"+chasXtQy.getFjlx());
                    // 登记区只显示一次
                    if ("1".equals(chasXtQy.getFjlx())) {
                        if (isdengji > 0) {
                            continue;
                        }
                        isdengji++;
                    } else
                        // 信息采集只显示一次窗体哦你
                        if ("2".equals(chasXtQy.getFjlx())) {
                            if (isxxcj > 0) {
                                continue;
                            }
                            isxxcj++;
                        } else
                            //只显示等候室、审讯室的定位信息
                            if(!StringUtils.contains(new String[]{SYSCONSTANT.FJLX_DHS,SYSCONSTANT.FJLX_SXS},chasXtQy.getFjlx())){
                                continue;
                            }
                    log.info("区域名称:"+chasXtQy.getQymc()+",区域类型:"+chasXtQy.getFjlx()+",该条数据不进行拦截!");
                }
			}

			yyhdTable.addCell(PdfPrintUtil.getTableCell(kssj,
					PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, 26));
			yyhdTable.addCell(PdfPrintUtil.getTableCell("至",
					PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, 26));
			yyhdTable.addCell(PdfPrintUtil.getTableCell(jssj,
					PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, 26));
			yyhdTable.addCell(PdfPrintUtil.getTableCell(room,
					PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, 26));
			log.error(String.format("人员姓名：%s 人员编号：%s 活动内容开始翻译",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));
			yyhdTable.addCell(PdfPrintUtil.getTableCell(
					DicUtil.translate("HDNR", event),
					PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, 26));
			log.error(String.format("人员姓名：%s 人员编号：%s 活动内容结束翻译",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));
			yyhdTable.addCell(PdfPrintUtil.getTableCell(remark,
					PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, 26));
			yyhdTable.addCell(PdfPrintUtil.getTableCell("",
					PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, 26));
		}
		log.error(String.format("人员姓名：%s 人员编号：%s 组装轨迹cell结束",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));
		return yyhdTable;
	}

	public void handlePrint() throws Exception {

		Document document = null;
		ByteArrayOutputStream baos = null;

		try {
			ChasBaqryxx chasBaqryxx = ryxxService.findById(getBusinessId());
			// RecordSet mainRs = getRs();
			if (chasBaqryxx != null) {
				log.error(String.format("人员姓名：%s 人员编号：%s 开始组装登记表",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("rybh", chasBaqryxx.getRybh());
				List<ChasSign> signlist = chasSignService.findList(params, null);
				Map<String, String> signmap = new HashMap<String, String>();
				for (ChasSign sign : signlist) {
					signmap.put(sign.getSignType(), sign.getImgBody());
				}
				// 初始化文档
				log.error(String.format("人员姓名：%s 人员编号：%s 初始化文档",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));
				document = new Document(PageSize.A4);
				// Rectangle r = PdfConstants.DEFAULT_PAGE_SIZE;
				// 位置调整
				document.setMargins(23, 32, 18, 18);

				baos = new ByteArrayOutputStream();
				PdfWriter writer = PdfWriter.getInstance(document, baos);

				writer.setPageEvent(new PdfPageEvent(false, false));
				document.open();

				// 初始化头部表格
				PdfPTable headTable = new PdfPTable(2);
				headTable.setHorizontalAlignment(Element.ALIGN_CENTER);
				headTable.setWidthPercentage(100);
				headTable.setWidths(new int[] { 55, 45 });
				headTable.setSpacingBefore(20);
				// 文书名称
				Chunk formChunk = new Chunk("办案区使用情况登记表",
						PdfPrintUtil.getCnFSongHFont(18, Font.BOLD));
				formChunk.setCharacterSpacing(0);
				PdfPCell formCell = new PdfPCell(new Phrase(formChunk));
				formCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				formCell.setBorder(0);
				formCell.setColspan(2);
				headTable.addCell(formCell);

				// 填表单位===办案区名称
				String tbdw = "填表单位:" + (StringUtils.isEmpty(chasBaqryxx.getBaqmc()) ? "" : chasBaqryxx.getBaqmc());
				PdfPCell tbdwCell = PdfPrintUtil.getNoBorderTableCell(tbdw,
						qdTableContentFont);
				tbdwCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbdwCell.setPaddingTop(3);
				headTable.addCell(tbdwCell);

				// 编号 ====办案区使用情况编号
				String bh = "编号:" + (StringUtils.isEmpty(chasBaqryxx.getBaqsyqkbh()) ? "" : chasBaqryxx.getBaqsyqkbh());
				PdfPCell bhCell = PdfPrintUtil.getNoBorderTableCell(bh,qdTableContentFont);
				bhCell.setPaddingTop(3);
				headTable.addCell(bhCell);
				// 添加头部表格
				document.add(headTable);

				// 人员基本信息表格
				log.error(String.format("人员姓名：%s 人员编号：%s 基本信息表格组装开始",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));
				PdfPTable ryjbxxTab = createRyjbxxPdfTable(chasBaqryxx, signmap);// ①
				log.error(String.format("人员姓名：%s 人员编号：%s 基本信息表格组装结束",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));
				// 添加人员基本信息表格
				document.add(ryjbxxTab);

				// 已占用高度
				float topHeight = headTable.getTotalHeight() + ryjbxxTab.getTotalHeight();
				// 创建安全检查表格
				log.error(String.format("人员姓名：%s 人员编号：%s 安全检查表格开始",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));
				createAqjcqkTable(document, writer, topHeight, chasBaqryxx,signmap);
				log.error(String.format("人员姓名：%s 人员编号：%s 安全检查表格结束",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));
				// 第二联处理

				document.newPage();
				// 采集信息
				PdfPTable t = new PdfPTable(5);
				t.setHorizontalAlignment(Element.ALIGN_CENTER);
				t.setWidthPercentage(100);
				t.setWidths(new int[] { 39, 92, 93, 90, 225 });
				t.setTotalWidth(540);
				t.setSpacingBefore(0);

				PdfPCell td1 = PdfPrintUtil.getTableCell("采集\n信息",
						PdfPrintUtil.getCnFSongHFont(12, Font.BOLD), 0,
						qdTableRowMiniHeight);
				// td1.setRowspan(2);
				t.addCell(td1);

				t.addCell(PdfPrintUtil.getTableCell("信息采集",
						PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
						qdTableRowMiniHeight));
				PdfPCell xxcjCell = PdfPrintUtil.getTableCell("",
						PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
						qdTableRowMiniHeight);
				// xxcjCell.setCellEvent(new PdfCheckBoxEvent("xxcj",20 ,new
				// String[]{"是","否"}, new boolean[]{isXxcj, false }));
				xxcjCell.setCellEvent(new PdfCheckBoxEvent("SFYTHCJ", 20,
						new String[] { "是", "否" }, new boolean[] {
								chasBaqryxx.getSfythcj() == 1,
								chasBaqryxx.getSfythcj() != 1 }));
				t.addCell(xxcjCell);

				t.addCell(PdfPrintUtil.getTableCell("信息入库",
						PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
						qdTableRowMiniHeight));
				PdfPCell xxrkCell = PdfPrintUtil.getTableCell("",
						PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
						qdTableRowMiniHeight);
				xxrkCell.setCellEvent(new PdfCheckBoxEvent("sftb", 20,
						new String[] { "是", "否" }, new boolean[] {chasBaqryxx.getSftb() == 1,chasBaqryxx.getSftb() != 1 }));
				t.addCell(xxrkCell);

				document.add(t);

				// 询问、讯问、等候、休息、饮食等活动记录
				log.error(String.format("人员姓名：%s 人员编号：%s 人员轨迹开始",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));
				document.add(buildRyGj(chasBaqryxx));
				log.error(String.format("人员姓名：%s 人员编号：%s 人员轨迹结束",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));

				PdfPTable t2 = new PdfPTable(2);
				t2.setHorizontalAlignment(Element.ALIGN_CENTER);
				t2.setWidthPercentage(100);
				t2.setWidths(new int[] { 39, 502 });
				t2.setTotalWidth(540);
				t2.setSpacingBefore(0);

				// 离开办案区
				PdfPCell lkbaqTitCell = PdfPrintUtil.getTableCell(
						"离\n\n开\n\n办\n\n案\n\n区",
						PdfPrintUtil.getCnFSongHFont(12, Font.BOLD), 0,
						qdTableRowMiniHeight);
				lkbaqTitCell.setRowspan(2);
				t2.addCell(lkbaqTitCell);

				// 中途离开处理

				PdfPTable t3 = new PdfPTable(7);
				t3.setHorizontalAlignment(Element.ALIGN_CENTER);
				t3.setWidthPercentage(100);
				t3.setWidths(new int[] { 81, 65, 65, 65, 64, 65, 97 });
				t3.setTotalWidth(502);
				t3.setSpacingBefore(0);

				log.error(String.format("人员姓名：%s 人员编号：%s 出入所数据组装开始",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));
				String rybh = chasBaqryxx.getRybh();
				// 临时出入所数量数量
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("rybh", rybh);
				param.put("lksjisnotnull", rybh);// 时间查询is not null 参数非null “”
				param.put("lklx", SYSCONSTANT.LKLX_LSCS);
				List<ChasYmCssp> cssplist = csspService.findList(param, "lksj");
				int rowCount = 0;
				// String sprxm = "";
				ChasYmCssp chasYmCssp = null;
				for (; rowCount < cssplist.size(); rowCount++) {
					if (rowCount == 3) {
						break;
					}
					chasYmCssp = cssplist.get(rowCount);
					PdfPCell ztlkCell = PdfPrintUtil.getTableCell("中途离开",
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight);
					ztlkCell.setRowspan(2);
					t3.addCell(ztlkCell);

					t3.addCell(PdfPrintUtil.getTableCell("离开时间",
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight));
					PdfPCell lksjCell = PdfPrintUtil.getTableCell(
							DateTimeUtils.getDateStr(chasYmCssp.getLksj(), 15),
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight);
					lksjCell.setColspan(2);
					t3.addCell(lksjCell);

					t3.addCell(PdfPrintUtil.getTableCell("返回时间",
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight));
					PdfPCell fhsjCell = PdfPrintUtil.getTableCell(
							chasYmCssp.getHlsj() == null ? "" : DateTimeUtils
									.getDateStr(chasYmCssp.getHlsj(), 15),
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight);
					fhsjCell.setColspan(2);
					t3.addCell(fhsjCell);

					t3.addCell(PdfPrintUtil.getTableCell("提押人",
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight));
					t3.addCell(PdfPrintUtil.getTableCell(chasYmCssp.getZbrXm(),
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight));
					t3.addCell(PdfPrintUtil.getTableCell("审批人",
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight));
					t3.addCell(PdfPrintUtil.getTableCell(chasYmCssp.getSprXm(),
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight));
					t3.addCell(PdfPrintUtil.getTableCell("离开原因",
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight));
					String csyy = "";
					if (SYSCONSTANT.CSSP_RYZT_LSCS.equals(chasYmCssp.getLklx())) {
						// 临时出所
						csyy = DicUtil.translate("LSCSYY",
								chasYmCssp.getLkyydm());
						if(StringUtils.isNotEmpty(chasYmCssp.getLkyy())){
							csyy += "("+chasYmCssp.getLkyy()+")";
						}
					} else if (SYSCONSTANT.CSSP_SHLX_CS.equals(chasYmCssp
							.getLklx())) {
						// 出所
						csyy = DicUtil
								.translate("CSYY", chasYmCssp.getLkyydm());
						if(StringUtils.isNotEmpty(chasYmCssp.getLkyy())){
							csyy += "("+chasYmCssp.getLkyy()+")";
						}
					}
					t3.addCell(PdfPrintUtil.getTableCell(csyy,
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight));
				}

				for (int i = rowCount; i < 3; i++) {
					PdfPCell ztlkCell = PdfPrintUtil.getTableCell("中途离开",
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight);
					ztlkCell.setRowspan(2);
					t3.addCell(ztlkCell);

					t3.addCell(PdfPrintUtil.getTableCell("离开时间",
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight));
					PdfPCell lksjCell = PdfPrintUtil.getTableCell("",
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight);
					lksjCell.setColspan(2);
					t3.addCell(lksjCell);

					t3.addCell(PdfPrintUtil.getTableCell("返回时间",
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight));
					PdfPCell fhsjCell = PdfPrintUtil.getTableCell("",
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight);
					fhsjCell.setColspan(2);
					t3.addCell(fhsjCell);

					t3.addCell(PdfPrintUtil.getTableCell("提押人",
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight));
					t3.addCell(PdfPrintUtil.getTableCell("",
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight));
					t3.addCell(PdfPrintUtil.getTableCell("审批人",
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight));
					t3.addCell(PdfPrintUtil.getTableCell("",
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight));
					t3.addCell(PdfPrintUtil.getTableCell("离开原因",
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight));
					t3.addCell(PdfPrintUtil.getTableCell("",
							PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
							qdTableRowMiniHeight));
				}

				t3.addCell(PdfPrintUtil.getTableCell("最后离开\n时间",
						PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
						qdTableRowMiniHeight));
				// 最后离开时间
				String zhlksj = "年  月  日  时  分";
				String csyy = "";
				String fzrxm = "";
				param.clear();
				param.put("rybh", rybh);
				param.put("lklx", SYSCONSTANT.LKLX_CS);
				param.put("spzt", SYSCONSTANT.SHZT_SPTG);
				List<ChasYmCssp> ryhjList = csspService.findList(param,"lksj desc");
				if (ryhjList.size() > 0) {
					if (chasBaqryxx.getCCssj() != null) {
						zhlksj = DateTimeUtils
								.doFormatDate("yyyy年MM月dd日 HH时mm分ss秒",
										chasBaqryxx.getCCssj());
					}
					csyy = StringUtils.isNotEmpty(ryhjList.get(0).getLkyydm()) ? ryhjList
							.get(0).getLkyydm() : "";
					fzrxm = ryhjList.get(0).getSprXm();
				}

				PdfPCell zhlksjCell = PdfPrintUtil.getTableCell(zhlksj,
						PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
						qdTableRowMiniHeight);
				zhlksjCell.setColspan(3);
				t3.addCell(zhlksjCell);

				t3.addCell(PdfPrintUtil.getTableCell("审批人",
						PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
						qdTableRowMiniHeight));

				PdfPCell sprCell = PdfPrintUtil.getTableCell(fzrxm,
						PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
						qdTableRowMiniHeight);
				sprCell.setColspan(2);
				t3.addCell(sprCell);

				t3.addCell(PdfPrintUtil.getTableCell("离开原因",
						PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
						qdTableRowMiniHeight));

				csyy = DicUtil.translate("CSYY", csyy);
				if(!ryhjList.isEmpty()){
					if(StringUtils.isNotEmpty(ryhjList.get(0).getLkyy())){
						csyy += "("+ryhjList.get(0).getLkyy()+")";
					}
				}
				PdfPCell lkyyCell = PdfPrintUtil.getTableCell(csyy,
						PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
						qdTableRowMiniHeight, Element.ALIGN_LEFT,
						Element.ALIGN_MIDDLE);
				lkyyCell.setColspan(6);
				lkbaqTitCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				t3.addCell(lkyyCell);

				PdfPCell ctlkjlCell = new PdfPCell(t3);
				t2.addCell(ctlkjlCell);

				// 随身物品处理
				log.error(String.format("人员姓名：%s 人员编号：%s 随身物品处理开始",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));
				PdfPTable t4 = new PdfPTable(5);
				t4.setHorizontalAlignment(Element.ALIGN_CENTER);
				t4.setWidthPercentage(100);
				t4.setWidths(new int[] { 40, 150, 60, 60, 192 });// 462
				t4.setTotalWidth(502);
				t4.setSpacingBefore(0);

				PdfPCell sswpclTitCell = PdfPrintUtil.getTableCell(
						"随身\n物品\n处理\n情况",
						PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
						qdTableRowMiniHeight);
				sswpclTitCell.setRowspan(4);
				t4.addCell(sswpclTitCell);

				PdfPCell sswpClqkCell = PdfPrintUtil.getTableCell("随身物品处理情况：",
						PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, 0,
						Element.ALIGN_LEFT, Element.ALIGN_MIDDLE);
				sswpClqkCell.setBorderWidth(0);
				sswpClqkCell.setPaddingLeft(5);
				sswpClqkCell.setColspan(4);

				boolean isAllBack = true;
				String wfhwpxx = "";
				// String BAQRY_SSWPZT_WU = "01";
				/** 办案区人员随身物品状态--未处理 **/
				// String BAQRY_SSWPZT_WCL = "02";
				/** 办案区人员随身物品状态--部分处理 **/
				// String BAQRY_SSWPZT_BFCL = "03";
				/** 办案区人员随身物品状态--已处理 **/
				// String BAQRY_SSWPZT_YCL = "04";
				String wpzt = SYSCONSTANT.BAQRY_SSWPZT_WU; //
				if (SYSCONSTANT.N_I == chasBaqryxx.getSfznbaq()) {
					// 非智能
					if (SYSCONSTANT.BAQRY_SSWPZT_BFCL.equals(chasBaqryxx.getSswpzt())) {
						wpzt = SYSCONSTANT.BAQRY_SSWPZT_BFCL;
						param.clear();
						param.put("rybh", rybh);
						param.put("wpzts", new String[]{SYSCONSTANT.SSWPZT_ZSWP,SYSCONSTANT.SSWPZT_ZBCL});
						List<ChasSswpxx> chasSswpxxlist = sswpxxService
								.findList(param, "lrsj desc");
						for (ChasSswpxx chasSswpxx : chasSswpxxlist) {
							wfhwpxx += chasSswpxx.getMc()+"、";
						}
						if(StringUtils.isNotEmpty(wfhwpxx)){
							wfhwpxx = wfhwpxx.substring(0,wfhwpxx.length()-1);
						}
					}
					if (SYSCONSTANT.BAQRY_SSWPZT_YCL.equals(chasBaqryxx.getSswpzt())) {
						wpzt = SYSCONSTANT.BAQRY_SSWPZT_YCL;
						wfhwpxx = "无";
					}
				} else {
					// 智能
					if (SYSCONSTANT.BAQRY_SSWPZT_BFCL.equals(chasBaqryxx.getSswpzt())) {
						wpzt = SYSCONSTANT.BAQRY_SSWPZT_BFCL;
						//wfhwpxx = "打包物品";
						param.clear();
						param.put("rybh", rybh);
						param.put("wpzts", new String[]{SYSCONSTANT.SSWPZT_ZSWP,SYSCONSTANT.SSWPZT_ZBCL});
						List<ChasSswpxx> chasSswpxxlist = sswpxxService.findList(param, "lrsj desc");
						String zky = "",zsa = "";
						for (ChasSswpxx chasSswpxx : chasSswpxxlist) {
						    if(StringUtils.equals(chasSswpxx.getWpclzt(),"04")){  //转扣押
						        zky += chasSswpxx.getMc()+"、";
                            }
                            if(StringUtils.equals(chasSswpxx.getWpclzt(),"05")){  //转涉案
                                zsa += chasSswpxx.getMc()+"、";
                            }
							//wfhwpxx += chasSswpxx.getMc()+"、";
						}
						if(StringUtils.isNotEmpty(zky)){
                            wfhwpxx += zky.substring(0,zky.length()-1) + " 转扣押;";
                        }
                        if(StringUtils.isNotEmpty(zsa)){
                            wfhwpxx += zsa.substring(0,zsa.length()-1) + " 转涉案;";
                        }
						/*if(StringUtils.isNotEmpty(wfhwpxx)){
							wfhwpxx = wfhwpxx.substring(0,wfhwpxx.length()-1);
						}*/
					}
					if (SYSCONSTANT.BAQRY_SSWPZT_YCL.equals(chasBaqryxx.getSswpzt())) {
						wpzt = SYSCONSTANT.BAQRY_SSWPZT_YCL;
						wfhwpxx = "无";
					}
				}
				if(SYSCONSTANT.BAQRY_SSWPZT_WCL.equals(chasBaqryxx.getSswpzt())){
					param.clear();
					param.put("rybh", rybh);
					param.put("wpzt", SYSCONSTANT.SSWPZT_ZSWP);
					List<ChasSswpxx> chasSswpxxlist = sswpxxService
							.findList(param, "lrsj desc");
					for (ChasSswpxx chasSswpxx : chasSswpxxlist) {
						wfhwpxx += chasSswpxx.getMc()+"、";
					}
					if(StringUtils.isNotEmpty(wfhwpxx)){
						wfhwpxx = wfhwpxx.substring(0,wfhwpxx.length()-1);
					}
				}
				sswpClqkCell.setCellEvent(new PdfCheckBoxEvent("sswpclqk", 110,
						new String[] { "全部返还", "部分返还" }, new boolean[] {
								SYSCONSTANT.BAQRY_SSWPZT_YCL.equals(wpzt),
								SYSCONSTANT.BAQRY_SSWPZT_BFCL.equals(wpzt) }));
				t4.addCell(sswpClqkCell);

				String wfhTitle = "未返还物品情况记载：", wfhContent = wfhwpxx;

				if (!isAllBack)
					wfhContent += "其余已返还。";

				PdfPCell wfhCell = PdfPrintUtil.getNoBorderTableCell(wfhTitle
						+ wfhContent, qdTableContentFont, 0, 0,
						Element.ALIGN_LEFT, Element.ALIGN_TOP);
				wfhCell.setLeading(15, 0);
				wfhCell.setBorderWidth(0);
				wfhCell.setPaddingLeft(5);
				wfhCell.setMinimumHeight(82);
				wfhCell.setPaddingBottom(5);
				wfhCell.setCellEvent(new PdfUnderDashedLineCellEvent(wfhTitle,
						"", 5, 18));
				wfhCell.setColspan(4);
				t4.addCell(wfhCell);

				PdfPCell lqrCell = PdfPrintUtil.getTableCell("领取人（签名捺印）：",
						qdTableContentFont, 0, qdTableRowMiniHeight);
				lqrCell.setBorderWidth(0);
				t4.addCell(lqrCell);
				PdfPCell lqrqmCell = null;
				if(isNewDoc()){
					byte[] hb = PdfComprehensiveUtil.hbqznytp(signmap.get(qmny[10]),signmap.get(qmny[11]),true);
					if(hb != null && hb.length > 0){
						lqrqmCell = imgHandle(qmny[10]+qmny[11], Base64.getEncoder().encodeToString(hb), 0, 100, 40,false);
					}else{
						lqrqmCell = imgHandle(qmny[10]+qmny[11], "", 0, 100, 40,false);
					}
				}else{
					lqrqmCell = imgHandle(qmny[10]+qmny[11],signmap.get(qmny[10]+qmny[11]), 0, 100, 40,false);
				}
				lqrqmCell.setBorderWidth(0);
				lqrqmCell.setPadding(3);
				t4.addCell(lqrqmCell);
				PdfPCell lqrzwCell = imgHandle("", signmap.get(""), 0, 50, 60,true);
				lqrzwCell.setBorderWidth(0);
				lqrzwCell.setPadding(3);
				t4.addCell(lqrzwCell);
				String sfzhmtx = "身份证号码：";
				if (chasBaqryxx.getRysfzh() == null
						|| chasBaqryxx.getRysfzh().trim().length() == 0) {
					sfzhmtx = sfzhmtx + "                       ";
				} else {
					sfzhmtx = sfzhmtx + chasBaqryxx.getRysfzh();
				}
				PdfPCell sfzhmCell = PdfPrintUtil.getTableCell(sfzhmtx,
						qdTableContentFont, 0, qdTableRowMiniHeight);
				sfzhmCell.setBorderWidth(0);
				t4.addCell(sfzhmCell);
				String format = DateTimeUtils.getDateString(10);
				PdfPCell nyrCell = PdfPrintUtil.getTableCell(format,
						PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
						qdTableRowMiniHeight, Element.ALIGN_RIGHT,
						Element.ALIGN_MIDDLE);
				nyrCell.setPaddingRight(50);
				nyrCell.setBorderWidth(0);
				nyrCell.setColspan(4);
				t4.addCell(nyrCell);

				PdfPCell sswpCell = new PdfPCell(t4);
				t2.addCell(sswpCell);

				document.add(t2);

				// 管理员签名
				log.error(String.format("人员姓名：%s 人员编号：%s 管理员签名开始",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));
				PdfPTable t5 = new PdfPTable(2);
				t5.setHorizontalAlignment(Element.ALIGN_CENTER);
				t5.setWidthPercentage(100);
				t5.setWidths(new int[] { 79, 461 });
				t5.setTotalWidth(540);
				t5.setSpacingBefore(0);
				t5.addCell(PdfPrintUtil.getTableCell("管理员签名",
						PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0,
						qdTableRowMiniHeight));
				PdfPCell jzrCell = imgHandle(qmny[12],signmap.get(qmny[12]), 0, 100, 30,true);
				jzrCell.setPadding(3);
				jzrCell.setPaddingLeft(20);
				jzrCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				t5.addCell(jzrCell);
				document.add(t5);
				// 关闭文档
				log.error(String.format("人员姓名：%s 人员编号：%s 关闭文档",chasBaqryxx.getRyxm(),chasBaqryxx.getRybh()));
				document.close();
			}

			// 输出内容
			if (baos != null) {
				byte[] fileBody = baos.toByteArray();
				setContent(fileBody);
			}

		} catch (Exception e) {
			e.printStackTrace();
            log.error("人员登记表异常:",e);
		} finally {
			if (baos != null)
				baos.close();
		}

	}

	private PdfPCell imgHandle(String type, String imgBody, float percent,float width, float height,boolean refine) {
		PdfPCell cell = null;
		try {
			if(!isNewDoc()){
				imgBody = "";
			}
			if (StringUtils.isNotEmpty(imgBody)) {
				BASE64Decoder decoder = new BASE64Decoder();
				byte[] zp = decoder.decodeBuffer(imgBody);
				if(refine){
					BufferedImage bufferedImage = PdfComprehensiveUtil.byte2BufferedImage(zp);
					bufferedImage = PdfComprehensiveUtil.drawImageByImage2(bufferedImage,BufferedImage.TYPE_4BYTE_ABGR);
					zp = PdfComprehensiveUtil.bufferedImage2Byte(bufferedImage);
				}
				Image img = Image.getInstance(zp);
				if (percent == 0) {
					img.scaleAbsolute(width, height);
				} else {
					img.scalePercent(percent);
				}
				cell = new PdfPCell(img);
			} else {
				cell = PdfPrintUtil.getTableCell(type, FontFactory.getFont(
						FontFactory.COURIER, 2, Font.NORMAL, new BaseColor(255,
								255, 255)), 0, qdTableRowMiniHeight);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("人员登记表异常:",e);
		}
		cell.setPaddingTop(2);
		return cell;
	}
}
