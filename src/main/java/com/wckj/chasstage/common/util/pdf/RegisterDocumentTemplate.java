package com.wckj.chasstage.common.util.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.wckj.chasstage.common.util.*;
import com.wckj.chasstage.common.util.pdf.event.PdfCheckBoxEvent;
import com.wckj.chasstage.common.util.pdf.event.PdfObliqueLineCellEvent;
import com.wckj.chasstage.common.util.pdf.event.PdfPageEvent;
import com.wckj.chasstage.common.util.pdf.event.PdfUnderDashedLineCellEvent;
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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.*;

/**
 * 人员登记表协办民警模板
 */
public class RegisterDocumentTemplate extends PdfPrintHandler {

    protected static Logger log = LoggerFactory.getLogger(RegisterDocumentTemplate.class);
    public String[] qmny = {};

    private ChasSswpxxService sswpxxService = ContextLoader.getCurrentWebApplicationContext().getBean(ChasSswpxxService.class);
    private ChasBaqryxxService ryxxService = ContextLoader.getCurrentWebApplicationContext().getBean(ChasBaqryxxService.class);
    private ChasSignService chasSignService = ContextLoader.getCurrentWebApplicationContext().getBean(ChasSignService.class);
    private ChasYmCsspService csspService = ContextLoader.getCurrentWebApplicationContext().getBean(ChasYmCsspService.class);
    private ChasYwRygjService rygjService = ContextLoader.getCurrentWebApplicationContext().getBean(ChasYwRygjService.class);
    private ChasXtQyService chasQyService = ContextLoader.getCurrentWebApplicationContext().getBean(ChasXtQyService.class);
    private ChasSswpZpxxService zpxxService = ContextLoader.getCurrentWebApplicationContext().getBean(ChasSswpZpxxService.class);

    public RegisterDocumentTemplate(String businessId){
        SignqmnyConfig config = SignbsUtil.AnalysisByConfig(businessId,true);
        qmny = config.getQmny();
    }

    /**
     * 创建基本信息
     * @return
     */
    private PdfPTable createBasicInfo(ChasBaqryxx chasBaqryxx, Map<String, String> signmap) throws Exception{
        long begin = new Date().getTime();
        PdfPTable INFO_TAB = new PdfPTable(9);
        INFO_TAB.setHorizontalAlignment(Element.ALIGN_CENTER);
        INFO_TAB.setWidthPercentage(100);
        INFO_TAB.setWidths(new int[]{38,64,90,48,40,60,70,40,90});
        INFO_TAB.setSpacingBefore(2);

        PdfPCell rr = PdfPrintUtil.getTableCell("人\n\n员\n\n基\n\n本\n\n情\n\n况", PdfPrintUtil.getCnFSongHFont(12, Font.BOLD),0, qdTableRowMiniHeight);
        rr.setRowspan(7);
        INFO_TAB.addCell(rr);

        appendTableCellByText("姓 名",INFO_TAB);
        appendTableCellByText(chasBaqryxx.getRyxm(),INFO_TAB);
        appendTableCellByText("性 别",INFO_TAB);
        appendTableCellByText(DicUtil.translate("CHAS_ZD_ZB_XB", chasBaqryxx.getXb()),INFO_TAB);
        appendTableCellByText("出 生\n日 期",INFO_TAB);
        //去除出生日期的时分秒
        String csrq = "";
        if(chasBaqryxx.getCsrq()!=null) {
            csrq = DateTimeUtils.getDateStr(chasBaqryxx.getCsrq(), 13);
            if (csrq!=null&&csrq.indexOf(" ")>0)csrq=csrq.substring(0,csrq.indexOf(" "));
        }
        appendTableCellByText(csrq,INFO_TAB);
        appendTableCellByText("联 系\n方 式",INFO_TAB);
        appendTableCellByText(chasBaqryxx.getLxdh(),INFO_TAB);

        appendTableCellByText("身份证\n证种类",INFO_TAB);
        appendTableCellByText(DicUtil.translate("CHAS_ZD_CASE_ZJLX", chasBaqryxx.getZjlx()),INFO_TAB);
        appendTableCellByText("身份证\n证号码",INFO_TAB);
        appendTableCellByText(chasBaqryxx.getRysfzh(),3,INFO_TAB);
        appendTableCellByText("到 案 \n方 式",INFO_TAB);
        String dafs = chasBaqryxx.getDafs();
        if(SYSCONSTANT.DAFS_QT.equals(dafs)){
            dafs=chasBaqryxx.getDafsText();
        }else{
            dafs= DicUtil.translate("DAFS",dafs);
        }
        appendTableCellByText(dafs,INFO_TAB);

        //第三行
        appendTableCellByText("户籍地",INFO_TAB);
        appendTableCellByText(chasBaqryxx.getHjdxz(),7,Element.ALIGN_LEFT,INFO_TAB);

        //第四行
        appendTableCellByText("现住址",INFO_TAB);
        appendTableCellByText(chasBaqryxx.getXzdxz(),7,Element.ALIGN_LEFT,INFO_TAB);

        String jqbh_ = chasBaqryxx.getJqbh();
        appendTableCellByText("警情编号",INFO_TAB);
        appendTableCellByText(jqbh_,4,Element.ALIGN_CENTER,INFO_TAB);
        appendTableCellByText("案由",INFO_TAB);
        String rsyystr = chasBaqryxx.getRyzaybh();
        if (SYSCONSTANT.DAFS_QT.equals(rsyystr)) {
            rsyystr = "其他(" + chasBaqryxx.getRyzaymc() + ")";
        } else {
            rsyystr = DicUtil.translate("RSYY", rsyystr);
        }
        appendTableCellByText(rsyystr,2,Element.ALIGN_CENTER,INFO_TAB);

        String ajbh_ = chasBaqryxx.getAjbh();
        appendTableCellByText("案件编号",INFO_TAB);
        appendTableCellByText(ajbh_,4,Element.ALIGN_CENTER,INFO_TAB);

        appendTableCellByText("办案民警\n(签名)",INFO_TAB);
        PdfPCell bamj = imgHandle(qmny[0],signmap.get(qmny[0]),0,30,25,true);
        bamj.setHorizontalAlignment(Element.ALIGN_CENTER);
        //协办签字
        PdfPCell bamj_xb = imgHandle(qmny[1],signmap.get(qmny[1]),0,30,25,true);
        bamj_xb.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell merge_cell = new PdfPCell(createTableEmbedSign(2,new int[]{30,30},bamj,bamj_xb));
        merge_cell.setColspan(2);
        INFO_TAB.addCell(merge_cell);

        //第六行
        appendTableCellByText("入区时间",INFO_TAB);
        appendTableCellByText(DateTimeUtils.getDateStr(chasBaqryxx.getRRssj(), 15),4,Element.ALIGN_CENTER,INFO_TAB);
        appendTableCellByText("管 理 员\n（签名）",INFO_TAB);
        PdfPCell glyqm = imgHandle(qmny[2], signmap.get(qmny[2]),0,60,25,true);
        glyqm.setHorizontalAlignment(Element.ALIGN_CENTER);
        glyqm.setColspan(2);
        INFO_TAB.addCell(glyqm);
        long end = new Date().getTime();
        log.debug(getBusinessId()+":人员基本信息表格【"+(end-begin)+"】");
        return INFO_TAB;
    }

    /**
     * 创建安全检查信息tab
     * @param document
     * @param writer
     * @param chasBaqryxx
     * @param signmap
     */
    public void createSecurityInfo(Document document, PdfWriter writer, ChasBaqryxx chasBaqryxx, Map<String, String> signmap) throws Exception{
        long begin = new Date().getTime();
        PdfPTable SECURITY_TAB = new PdfPTable(10);
        if (StringUtils.isEmpty(chasBaqryxx.getRybh())) {
            return;
        }
        // 人员安全检查表格
        SECURITY_TAB.setSplitLate(false);
        SECURITY_TAB.setSplitRows(true);
        SECURITY_TAB.setHorizontalAlignment(Element.ALIGN_CENTER);
        SECURITY_TAB.setWidthPercentage(100);
        SECURITY_TAB.setWidths(new int[] { 38, 25, 69, 65, 64, 60, 25, 42, 32, 120 });
        SECURITY_TAB.setTotalWidth(540);
        SECURITY_TAB.setSpacingBefore(0);

        PdfPCell td1 = PdfPrintUtil.getTableCell("安\n\n全\n\n检\n\n查\n\n情\n\n况",PdfPrintUtil.getCnFSongHFont(12, Font.BOLD), 0, qdTableRowMiniHeight);
        td1.setRowspan(6);
        SECURITY_TAB.addCell(td1);

        PdfPCell td2 = PdfPrintUtil.getTableCell("人身检查记录",PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, qdTableRowMiniHeight);
        td2.setRowspan(3);
        SECURITY_TAB.addCell(td2);

        PdfPTable stjcTable = new PdfPTable(2);

        stjcTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        stjcTable.setWidthPercentage(100);
        stjcTable.setWidths(new int[] { 50, 50 });

        // 自述症状
        String zszzStart = "    自述症状:（既往病史、是否饮酒、是否患有传染性等疾病）";
        String jbqk = StringUtils.clearNull(chasBaqryxx.getRYzjb());
        jbqk = StringUtils.isEmpty(jbqk) ? "无" : jbqk;
        PdfPCell jbqkCell = PdfPrintUtil.getNoBorderTableCell(zszzStart + "  "+ jbqk, qdTableContentFont, 0, 0, Element.ALIGN_LEFT, Element.ALIGN_TOP);
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
        if(StringUtils.isNotEmpty(jcqk) && !StringUtils.equals(jcqk,"无")&&!StringUtils.equals(jcqk,"null")){
            List<Map> maps = JsonUtil.getListFromJsonString(jcqk,Map.class);
            if(maps != null){
                int inx = 1;
                jcqkStr += "4.伤情描述:";
                for(Map<String,Object> map : maps){
                    jcqkStr += "("+(inx++)+")"+map.get("part")+":"+map.get("desc")+";";
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
        PdfPCell jcCell = PdfPrintUtil.getNoBorderTableCell(jcStart + "  "+ jcqkStr, qdTableContentFont, 0, 0, Element.ALIGN_LEFT, Element.ALIGN_TOP);
        jcCell.setLeading(15, 0);
        jcCell.setMinimumHeight(97);
        jcCell.setPaddingBottom(5);
        jcCell.setCellEvent(new PdfUnderDashedLineCellEvent(zszzStart, jbqk, 5, 12));
        stjcTable.addCell(jcCell);

        PdfPCell sqqkCell = new PdfPCell(stjcTable);
        sqqkCell.setColspan(8);
        sqqkCell.setPaddingLeft(3);
        SECURITY_TAB.addCell(sqqkCell);

        String kssj = "";
        if (chasBaqryxx.getAqjckssj() != null) {
            kssj = DateTimeUtils.getDateStr(chasBaqryxx.getAqjckssj(), 15);
        }
        String jssj = "";
        if (chasBaqryxx.getAqjcjssj() != null) {
            jssj = DateTimeUtils.getDateStr(chasBaqryxx.getAqjcjssj(), 15);
        }
        PdfPCell jzrCells2 = PdfPrintUtil.getTableCell("安全检查时间段", qdTableContentFont, 0, qdTableRowMiniHeight);
        jzrCells2.setColspan(2);
        SECURITY_TAB.addCell(jzrCells2);
        PdfPCell jzrCell1 = PdfPrintUtil.getTableCell(kssj + " --- " + jssj, qdTableContentFont, 0, qdTableRowMiniHeight);
        jzrCell1.setColspan(6);
        SECURITY_TAB.addCell(jzrCell1);

        // -------
        SECURITY_TAB.addCell(PdfPrintUtil.getTableCell("检查民警（签字）", qdTableContentFont, 0, qdTableRowMiniHeight));
        PdfPCell s = imgHandle(qmny[3], signmap.get(qmny[3]), 0, 50, 23,true);
        s.setHorizontalAlignment(Element.ALIGN_CENTER);
        s.setPaddingTop(8);
        //SECURITY_TAB.addCell(s);
        //检查民警——协办
        PdfPCell s1 = imgHandle(qmny[4], signmap.get(qmny[4]), 0, 50, 23,true);
        s1.setHorizontalAlignment(Element.ALIGN_CENTER);
        s1.setPaddingTop(8);
        PdfPCell merge_cell = new PdfPCell(createTableEmbedSign(null,new int[]{40},s,s1, DocumentCellType.HORIZONTAL,23));
        //merge_cell.setColspan(2);
        SECURITY_TAB.addCell(merge_cell);

        SECURITY_TAB.addCell(PdfPrintUtil.getTableCell("见证人 （签字）", qdTableContentFont, 0, qdTableRowMiniHeight));
        PdfPCell jzrCell = imgHandle(qmny[5], signmap.get(qmny[5]), 0, 50, 20,true);

        jzrCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        jzrCell.setPaddingTop(11);
        SECURITY_TAB.addCell(jzrCell);

        PdfPCell bjcrCell = PdfPrintUtil.getTableCell("被检查人\n(签字捺印)", qdTableContentFont, 0, qdTableRowMiniHeight);
        bjcrCell.setColspan(2);
        SECURITY_TAB.addCell(bjcrCell);
        PdfPCell bjcr = imgHandle("", signmap.get(""), 0,60, 30,true);
        bjcr.setBorderWidthLeft(0);
        bjcr.setBorderWidthRight(0);
        bjcr.setBorderWidthTop(0);
        bjcr.setBorderWidthBottom(0);
        bjcr.setPadding(3);
        bjcr.setPaddingLeft(15);

        PdfPCell bjcrzw;
        if(isNewDoc()){
            byte[] hb = PdfComprehensiveUtil.hbqznytp(signmap.get(qmny[6]),signmap.get(qmny[7]),true);
            if(hb != null && hb.length > 0){
                bjcrzw = imgHandle(qmny[6]+qmny[7], Base64.getEncoder().encodeToString(hb), 0, 100, 27,false);
            }else{
                bjcrzw = imgHandle(qmny[6]+qmny[7], "", 0, 100, 27,false);
            }
        }else{
            bjcrzw = imgHandle(qmny[6]+qmny[7], signmap.get(qmny[6]+qmny[7]), 0, 100, 30,false);
        }
        bjcrzw.setBorderWidthLeft(0);
        bjcrzw.setBorderWidthTop(0);
        bjcrzw.setBorderWidthBottom(0);
        //bjcrzw.setPaddingLeft(68);

        bjcr.setHorizontalAlignment(Element.ALIGN_RIGHT);
        SECURITY_TAB.addCell(bjcr);
        SECURITY_TAB.addCell(bjcrzw);

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
            long end = new Date().getTime();
            log.debug(getBusinessId()+":非智能办案区,创建安全检查信息tab【"+(end-begin)+"】");
        } else {
            // 智能办案区
            sswpMessage = "随身财物照片";
            zptable = new PdfPTable(2);
            zptable.setWidthPercentage(100);
            begin = new Date().getTime();
            String[] zpUrls = getWpZpUrls(chasBaqryxx);
            long end = new Date().getTime();
            log.debug(getBusinessId()+":智能办案区,获取物品照片URL数据【"+(end-begin)+"】");
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
        PdfPCell td3 = PdfPrintUtil.getTableCell(sswpMessage, PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, qdTableRowMiniHeight);
        td3.setRowspan(3);
        SECURITY_TAB.addCell(td3);

        PdfPCell sswpCell = new PdfPCell();
        sswpCell.setColspan(8);
        sswpCell.addElement(zptable);

        SECURITY_TAB.addCell(sswpCell);

        PdfPCell saryCell0 = PdfPrintUtil.getTableCell("办案人员（签字）", qdTableContentFont, 0, qdTableRowMiniHeight);
        PdfPCell saryCell2 = imgHandle(qmny[8], signmap.get(qmny[8]), 0, 50, 25,true);
        saryCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        //办案人员--协办
        PdfPCell saryCell2_xb = imgHandle(qmny[9], signmap.get(qmny[9]), 0, 50, 25,true);
        saryCell2_xb.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell saryCell3 = PdfPrintUtil.getTableCell("随身财物\n管理员\n（签字）", qdTableContentFont, 0, qdTableRowMiniHeight);
        PdfPCell saryCell4 = imgHandle(qmny[10], signmap.get(qmny[10]), 0, 40, 40,true);
        saryCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        saryCell4.setPaddingTop(12);

        PdfPCell saryCell = PdfPrintUtil.getTableCell("涉案人员\n(签字捺印)", qdTableContentFont, 0, qdTableRowMiniHeight);
        saryCell.setColspan(2);
        //saryCell2.setRowspan(2);
        //saryCell2_xb.setRowspan(2);
        saryCell3.setRowspan(2);
        saryCell4.setRowspan(2);
        saryCell0.setRowspan(2);
        saryCell.setRowspan(2);
        saryCell.setFixedHeight(55);
        SECURITY_TAB.addCell(saryCell0);
        merge_cell = new PdfPCell(createTableEmbedSign(null,new int[]{40},saryCell2,saryCell2_xb, DocumentCellType.HORIZONTAL,50));
        merge_cell.setRowspan(2);
        //SECURITY_TAB.addCell(saryCell2);
        //SECURITY_TAB.addCell(saryCell2_xb);
        SECURITY_TAB.addCell(merge_cell);
        SECURITY_TAB.addCell(saryCell3);
        SECURITY_TAB.addCell(saryCell4);
        SECURITY_TAB.addCell(saryCell);

        // 涉案人员签字表格
        PdfPCell saryQxCell = PdfPrintUtil.getTableCell("本人自愿将以上个人随身物品存放进(电子)保管柜。", PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, qdTableRowMiniHeight, Element.ALIGN_LEFT, Element.ALIGN_TOP);
        saryQxCell.setColspan(2);
        saryQxCell.setBorderWidthLeft(0);
        saryQxCell.setBorderWidthBottom(0);
        saryQxCell.setBorderWidthTop(0);
        SECURITY_TAB.addCell(saryQxCell);

        // 签字标记
        PdfPCell ch = imgHandle("", signmap.get(""), 0, 50,25,true);
        PdfPCell chzw;
        if(isNewDoc()){
            byte[] hb = PdfComprehensiveUtil.hbqznytp(signmap.get(qmny[11]),signmap.get(qmny[12]),true);
            if(hb != null && hb.length > 0){
                chzw = imgHandle(qmny[11]+qmny[12], Base64.getEncoder().encodeToString(hb), 0, 100, 25,false);
            }else{
                chzw = imgHandle(qmny[11]+qmny[12], "", 0, 100, 25,false);
            }
        }else{
            chzw = imgHandle(qmny[11]+qmny[12], signmap.get(qmny[11]+qmny[12]), 0, 100, 25,false);
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
        SECURITY_TAB.addCell(ch);
        SECURITY_TAB.addCell(chzw);
        document.add(SECURITY_TAB);
        // 画斜线
        PdfPrintUtil.writeObliqueLine(writer, obliqueLineEvents, sHeight);
        long end = new Date().getTime();
        log.debug(getBusinessId()+":智能办案区,创建安全检查信息tab【"+(end-begin)+"】");
    }

    private void appendTableCellByText(String text,PdfPTable INFO_TAB) {
        INFO_TAB.addCell(PdfPrintUtil.getTableCell(text, qdTableContentFont,0, qdTableRowMiniHeight));
    }

    private void appendTableCellByText(String text,Integer colspan,PdfPTable INFO_TAB) {
        PdfPCell cell = PdfPrintUtil.getTableCell(text, qdTableContentFont, 0, qdTableRowMiniHeight);
        cell.setColspan(colspan);
        INFO_TAB.addCell(cell);
    }

    private void appendTableCellByText(String text,Integer colspan,Integer rowspan,Integer elementType,PdfPTable INFO_TAB) {
        PdfPCell cell = PdfPrintUtil.getTableCell(text, qdTableContentFont, 0, qdTableRowMiniHeight);
        cell.setColspan(colspan);
        cell.setRowspan(rowspan);
        if(elementType != null){
            cell.setHorizontalAlignment(elementType);
        }
        INFO_TAB.addCell(cell);
    }

    private void appendTableCellByText(String text,Integer colspan,Integer elementType,PdfPTable INFO_TAB) {
        PdfPCell cell = PdfPrintUtil.getTableCell(text, qdTableContentFont, 0, qdTableRowMiniHeight);
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(elementType);
        INFO_TAB.addCell(cell);
    }

    private PdfPTable createTableEmbedSign(Integer columns,int[] columnsWidth,PdfPCell first,PdfPCell second) throws Exception{
        PdfPTable pdfPTable = new PdfPTable(columns);
        pdfPTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setWidths(columnsWidth);
        first.setPaddingTop(0);
        second.setPaddingTop(0);
        pdfPTable.addCell(first);
        pdfPTable.addCell(second);
        return pdfPTable;
    }

    private PdfPTable createTableEmbedSign(Integer columns, int[] columnsWidth, PdfPCell first, PdfPCell second, DocumentCellType documentCellType, int height) throws Exception{
        if(documentCellType.equals(DocumentCellType.HORIZONTAL)){  //横
            PdfPTable pdfPTable = new PdfPTable(1);
            pdfPTable.setWidthPercentage(100);
            pdfPTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.setWidths(columnsWidth);
            first.setPadding(0);
            second.setPadding(0);
            int height_ = height / 2;
            if(height_ < 20) height_ = 20;
            first.setMinimumHeight(height_);
            second.setMinimumHeight(height_);
            pdfPTable.addCell(first);
            pdfPTable.addCell(second);
            return pdfPTable;
        }else if(documentCellType.equals(DocumentCellType.VERTICAL)){  //竖
            PdfPTable pdfPTable = new PdfPTable(columns);
            pdfPTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.setWidthPercentage(100);
            pdfPTable.setWidths(columnsWidth);
            first.setPaddingTop(0);
            second.setPaddingTop(0);
            pdfPTable.addCell(first);
            pdfPTable.addCell(second);
            return pdfPTable;
        }
        return null;
    }

    private PdfPTable createTableEmbedSign(Integer columns,Integer pdfWidth,int[] columnsWidth,PdfPCell first,PdfPCell second) throws Exception{
        PdfPTable pdfPTable = new PdfPTable(columns);
        pdfPTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.setWidthPercentage(pdfWidth);
        pdfPTable.setWidths(columnsWidth);
        first.setPaddingTop(0);
        second.setPaddingTop(0);
        pdfPTable.addCell(first);
        pdfPTable.addCell(second);
        if(columnsWidth.length > 2){
            for (int i = 2; i < columnsWidth.length; i++) {
                //填充空白，用于布局
                PdfPCell cell = PdfPrintUtil.getTableCell("",null,0l,0l);
                cell.setBorder(0);
                pdfPTable.addCell(cell);
            }
        }
        return pdfPTable;
    }

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

    public PdfPCell imgHandle(String type, String imgBody, float percent, float width, float height,boolean refine) {
        PdfPCell cell = null;
        try {
            if(!isNewDoc()){
                imgBody = "";
            }
            if(StringUtils.isNotEmpty(imgBody)) {
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] zp = decoder.decodeBuffer(imgBody);
                if(refine){
                    BufferedImage bufferedImage = PdfComprehensiveUtil.byte2BufferedImage(zp);
                    bufferedImage = PdfComprehensiveUtil.drawImageByImage2(bufferedImage,BufferedImage.TYPE_4BYTE_ABGR);
                    zp = PdfComprehensiveUtil.bufferedImage2Byte(bufferedImage);
                }
                Image img= Image.getInstance(zp);
                if (percent==0) {
                    img.scaleAbsolute(width, height);
                }else {
                    img.scalePercent(percent);
                }
                cell = new PdfPCell(img);
            }else {
                cell = PdfPrintUtil.getTableCell(type, FontFactory.getFont(FontFactory.COURIER,2, Font.NORMAL, new BaseColor(255, 255, 255)),0, qdTableRowMiniHeight);
            }
        }catch (Exception e) {
            e.printStackTrace();
            log.error("createRyjbxxPdfTable:",e);
        }
        cell.setPaddingTop(2);
        return cell;
    }

    public byte[] drawImagetoLargePicture(String imageSrc, List<Map> maps){
        try{
            //添加图片伤情标识
            String icon = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+"/static/chas/pdf/imgs/mark-icon2.png";
            File file = new File(imageSrc);
            FileInputStream fileImageInputStream = new FileInputStream(file);
            JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(fileImageInputStream);
            BufferedImage bufferImg = jpegDecoder.decodeAsBufferedImage();
            //得到画笔对象
            Graphics g = bufferImg.getGraphics();
            ImageIcon imgIcon = new ImageIcon(icon);
            //得到Image对象
            int i = 1;
            if(maps != null){
                for(Map map : maps){
                    String order = (String) map.get("order");
                    if(StringUtils.isNotEmpty(order)){
                        int[] xy = getXyByBodyParts(order);
                        java.awt.Image img = imgIcon.getImage();
                        g.drawImage(img,xy[0],xy[1],null);
                        g.setColor(Color.black);
                        java.awt.Font f = new java.awt.Font("宋体",Font.BOLD,15);
                        g.setFont(f);
                        //图片上绘制字符串内容,位置信息（x,y）
                        g.drawString((i++)+"", xy[0]+20, xy[1]+20);
                    }
                }
            }
            g.dispose();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferImg,"jpg",byteArrayOutputStream);
            fileImageInputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
            log.error("drawImagetoLargePicture:",e);
        }
        return new byte[]{};
    }

    public int[] getXyByBodyParts(String order){
        Map<String,int[]> result = new HashMap<>();
        result.put("1",new int[]{187,20});
        result.put("2",new int[]{187,55});
        result.put("3",new int[]{187,97});
        result.put("4",new int[]{121,160});
        result.put("5",new int[]{160,160});
        result.put("6",new int[]{220,160});
        result.put("7",new int[]{260,160});
        result.put("8",new int[]{111,210});
        result.put("9",new int[]{155,240});
        result.put("10",new int[]{225,240});
        result.put("11",new int[]{265,210});
        result.put("12",new int[]{100,260});
        result.put("13",new int[]{160,260});
        result.put("14",new int[]{220,260});
        result.put("15",new int[]{280,260});
        result.put("16",new int[]{70,330});
        result.put("17",new int[]{190,290});
        result.put("18",new int[]{303,330});
        result.put("19",new int[]{160,360});
        result.put("20",new int[]{220,360});
        result.put("21",new int[]{160,400});
        result.put("22",new int[]{210,400});
        result.put("23",new int[]{160,480});
        result.put("24",new int[]{210,480});
        result.put("25",new int[]{160,560});
        result.put("26",new int[]{210,560});
        if(Integer.parseInt(order) > 26){
            int _order = Integer.parseInt(order);
            if(_order == 46){
                return new int[]{220+297,400};
            }
            if(_order == 48){
                return new int[]{210+297,360};
            }
            if(_order == 35){
                return new int[]{155+297,210};
            }
            if(_order == 36){
                return new int[]{225+297,210};
            }
            if(_order == 43){  //左屁股
                return new int[]{160+297,290};
            }
            if(_order == 53){  //右屁股
                return new int[]{215+297,290};
            }
            int[] position = result.get(String.valueOf(_order - 26));
            position[0] = position[0] + 297;
            return position;
        }else{
            return result.get(order);
        }
    }

    public PdfPTable buildRyGj(ChasBaqryxx chasBaqryxx) throws Exception {

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
        PdfPCell sjtdCell = PdfPrintUtil.getTableCell("时    间", PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, qdTableRowMiniHeight);
        sjtdCell.setColspan(3);
        yyhdTable.addCell(sjtdCell);

        yyhdTable.addCell(PdfPrintUtil.getTableCell("房间名称", PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, qdTableRowMiniHeight));

        PdfPCell hdnr = PdfPrintUtil.getTableCell("活动内容", PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, qdTableRowMiniHeight);
        // hdnr.setColspan(2);
        yyhdTable.addCell(hdnr);
        yyhdTable.addCell(PdfPrintUtil.getTableCell("备注", PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, qdTableRowMiniHeight));
        yyhdTable.addCell(PdfPrintUtil.getTableCell("签名", PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, qdTableRowMiniHeight));

        // 依据人员编号 获取人员轨迹信息
        List<ChasRygj> rygjList = getRyGj(chasBaqryxx);
        Map<String, ChasXtQy> xtqtmap = getXtQy(chasBaqryxx);
        final int rygjSize = rygjList.size();
        int xhs = 11,current_inx = 0;  //规定最多显示10条
        int isdengji = 0;
        int isxxcj = 0;
        // 循环增加活动记录
        for (int i = 0; i < xhs; i++) {
            // for (int i = 0; i < 10 ; i++) {
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
                            if(i > 0) i--;
                            continue;
                        }
                        isdengji++;
                    } else
                        // 信息采集只显示一次窗体哦你
                        if ("2".equals(chasXtQy.getFjlx())) {
                            if (isxxcj > 0) {
                                if(i > 0) i--;
                                continue;
                            }
                            isxxcj++;
                        } else
                            //只显示等候室、审讯室的定位信息
                            if(!StringUtils.contains(new String[]{SYSCONSTANT.FJLX_DHS,SYSCONSTANT.FJLX_SXS},chasXtQy.getFjlx())){
                                if(i > 0) i--;
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
            yyhdTable.addCell(PdfPrintUtil.getTableCell(
                    DicUtil.translate("HDNR", event),
                    PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, 26));
            yyhdTable.addCell(PdfPrintUtil.getTableCell(remark,
                    PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, 26));
            yyhdTable.addCell(PdfPrintUtil.getTableCell("",
                    PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, 26));
        }
        return yyhdTable;
    }

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
        param.put("temporary","1");
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

    @Override
    public void handlePrint() throws Exception {

        Document document = null;
        ByteArrayOutputStream baos = null;

        try {
            long begin = new Date().getTime();
            ChasBaqryxx chasBaqryxx = ryxxService.findById(getBusinessId());
            if (chasBaqryxx != null) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("rybh", chasBaqryxx.getRybh());
                List<ChasSign> signlist = chasSignService.findList(params, null);
                Map<String, String> signmap = new HashMap<String, String>();
                for (ChasSign sign : signlist) {
                    signmap.put(sign.getSignType(), sign.getImgBody());
                }
                long end = new Date().getTime();
                log.debug(getBusinessId()+":查询人员签字数据耗时【"+(end-begin)+"】");
                begin = new Date().getTime();
                // 初始化文档
                document = new Document(PageSize.A4);
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
                Chunk formChunk = new Chunk("办案区使用情况登记表", PdfPrintUtil.getCnFSongHFont(18, Font.BOLD));
                formChunk.setCharacterSpacing(0);
                PdfPCell formCell = new PdfPCell(new Phrase(formChunk));
                formCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                formCell.setBorder(0);
                formCell.setColspan(2);
                headTable.addCell(formCell);

                // 填表单位===办案区名称
                String tbdw = "填表单位:" + (StringUtils.isEmpty(chasBaqryxx.getBaqmc()) ? "" : chasBaqryxx.getBaqmc());
                PdfPCell tbdwCell = PdfPrintUtil.getNoBorderTableCell(tbdw, qdTableContentFont);
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
                end = new Date().getTime();
                log.debug(getBusinessId()+":添加头部表格【"+(end-begin)+"】");
                // 人员基本信息表格
                PdfPTable ryjbxxTab = createBasicInfo(chasBaqryxx, signmap);// ①
                // 添加人员基本信息表格
                document.add(ryjbxxTab);
                // 创建安全检查表格
                createSecurityInfo(document, writer, chasBaqryxx, signmap);
                // 第二联处理
                document.newPage();
                // 采集信息

                PdfPTable t = new PdfPTable(5);
                t.setHorizontalAlignment(Element.ALIGN_CENTER);
                t.setWidthPercentage(100);
                t.setWidths(new int[] { 39, 92, 93, 90, 225 });
                t.setTotalWidth(540);
                t.setSpacingBefore(0);

                PdfPCell td1 = PdfPrintUtil.getTableCell("采集\n信息", PdfPrintUtil.getCnFSongHFont(12, Font.BOLD), 0, qdTableRowMiniHeight);
                t.addCell(td1);

                t.addCell(PdfPrintUtil.getTableCell("信息采集", PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, qdTableRowMiniHeight));
                PdfPCell xxcjCell = PdfPrintUtil.getTableCell("", PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, qdTableRowMiniHeight);
                xxcjCell.setCellEvent(new PdfCheckBoxEvent("SFYTHCJ", 20,
                        new String[] { "是", "否" }, new boolean[] {
                        chasBaqryxx.getSfythcj() == 1,
                        chasBaqryxx.getSfythcj() != 1 }));
                t.addCell(xxcjCell);

                t.addCell(PdfPrintUtil.getTableCell("信息入库", PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, qdTableRowMiniHeight));
                PdfPCell xxrkCell = PdfPrintUtil.getTableCell("", PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, qdTableRowMiniHeight);
                xxrkCell.setCellEvent(new PdfCheckBoxEvent("sftb", 20, new String[] { "是", "否" }, new boolean[] {chasBaqryxx.getSftb() == 1,chasBaqryxx.getSftb() != 1 }));
                t.addCell(xxrkCell);

                document.add(t);
                begin = new Date().getTime();
                // 询问、讯问、等候、休息、饮食等活动记录
                document.add(buildRyGj(chasBaqryxx));
                end = new Date().getTime();
                log.debug(getBusinessId()+":轨迹封装【"+(end-begin)+"】");
                begin = new Date().getTime();
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
                List<ChasYmCssp> ryhjList = csspService.findList(param, "lksj desc");
                if (ryhjList.size() > 0) {
                    if (chasBaqryxx.getCCssj() != null) {
                        zhlksj = DateTimeUtils.doFormatDate("yyyy年MM月dd日 HH时mm分ss秒", chasBaqryxx.getCCssj());
                    }
                    csyy = StringUtils.isNotEmpty(ryhjList.get(0).getLkyydm()) ? ryhjList.get(0).getLkyydm() : "";
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
                end = new Date().getTime();
                log.debug(getBusinessId()+":出入记录【"+(end-begin)+"】");
                begin = new Date().getTime();
                // 随身物品处理
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
                    end = new Date().getTime();
                    log.debug(getBusinessId()+":非智能办案区，随身物品处理【"+(end-begin)+"】");
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
                    end = new Date().getTime();
                    log.debug(getBusinessId()+":智能办案区，随身物品处理【"+(end-begin)+"】");
                }
                begin = new Date().getTime();
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
                    byte[] hb = PdfComprehensiveUtil.hbqznytp(signmap.get(qmny[13]),signmap.get(qmny[14]),true);
                    if(hb != null && hb.length > 0){
                        lqrqmCell = imgHandle(qmny[13]+qmny[14], Base64.getEncoder().encodeToString(hb), 0, 100, 40,false);
                    }else{
                        lqrqmCell = imgHandle(qmny[13]+qmny[14], "", 0, 100, 40,false);
                    }
                }else{
                    lqrqmCell = imgHandle(qmny[13]+qmny[14],signmap.get(qmny[13]+qmny[14]), 0, 100, 40,false);
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
                PdfPTable t5 = new PdfPTable(2);
                t5.setHorizontalAlignment(Element.ALIGN_CENTER);
                t5.setWidthPercentage(100);
                t5.setWidths(new int[] { 79, 461 });
                t5.setTotalWidth(540);
                t5.setSpacingBefore(0);
                t5.addCell(PdfPrintUtil.getTableCell("管理员签名", PdfPrintUtil.getCnFSongHFont(12, Font.NORMAL), 0, qdTableRowMiniHeight));
                PdfPCell jzrCell = imgHandle(qmny[15],signmap.get(qmny[15]), 0, 100, 30,true);
                //jzrCell.setPadding(3);
                //jzrCell.setPaddingLeft(20);
                jzrCell.setBorder(0);
                jzrCell.setHorizontalAlignment(Element.ALIGN_LEFT);

                PdfPCell jzrCell_xb = imgHandle(qmny[16],signmap.get(qmny[16]), 0, 100, 30,true);
                //jzrCell_xb.setPadding(3);
                //jzrCell_xb.setPaddingLeft(20);
                jzrCell_xb.setBorder(0);
                jzrCell_xb.setHorizontalAlignment(Element.ALIGN_LEFT);

                PdfPCell merge_cell = new PdfPCell(createTableEmbedSign(6,100,new int[]{20,20,20,20,20,20},jzrCell,jzrCell_xb));
                merge_cell.setPadding(3);
                merge_cell.setPaddingLeft(20);
                t5.addCell(merge_cell);
                document.add(t5);
                // 关闭文档
                document.close();
                end = new Date().getTime();
                log.debug(getBusinessId()+":物品位置，签字捺印放置【"+(end-begin)+"】");
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
}

enum DocumentCellType{
    VERTICAL("vertical"),HORIZONTAL("horizontal");

    private String type;

    DocumentCellType(String type) {
        this.type = type;
    }
}
