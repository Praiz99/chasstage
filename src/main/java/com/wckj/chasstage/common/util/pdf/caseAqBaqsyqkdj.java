package com.wckj.chasstage.common.util.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.wckj.chasstage.common.util.DateTimeUtils;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.SignbsUtil;
import com.wckj.chasstage.common.util.SignqmnyConfig;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class caseAqBaqsyqkdj extends PdfPrintHandler {
    protected static Logger log = LoggerFactory.getLogger(caseAqBaqsyqkdj.class);

    public String[] qmny = {};

	@Override
	public void handlePrint() throws Exception {
	}
	
	public PdfPTable createRyjbxxPdfTable(ChasBaqryxx chasBaqryxx, Map<String, String> signmap) throws Exception{
        SignqmnyConfig config = SignbsUtil.AnalysisByConfig(chasBaqryxx.getBaqid(),true);
        qmny = config.getQmny();
		 //人员基本信息表格
        PdfPTable ryjbxxTab = new PdfPTable(9);
		ryjbxxTab.setHorizontalAlignment(Element.ALIGN_CENTER);
		ryjbxxTab.setWidthPercentage(100);
		ryjbxxTab.setWidths(new int[]{38,64,90,48,40,60,70,40,90});
		ryjbxxTab.setSpacingBefore(2);
       
		PdfPCell rr = PdfPrintUtil.getTableCell("人\n\n员\n\n基\n\n本\n\n情\n\n况", PdfPrintUtil.getCnFSongHFont(12, Font.BOLD),0, qdTableRowMiniHeight);
		rr.setRowspan(7);
		ryjbxxTab.addCell(rr);
		
		//第一行===姓名
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell("姓 名", qdTableContentFont,0, qdTableRowMiniHeight));
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell(chasBaqryxx.getRyxm(), qdTableContentFont,0, qdTableRowMiniHeight));
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell("性 别", qdTableContentFont,0, qdTableRowMiniHeight));
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell(DicUtil.translate("CHAS_ZD_ZB_XB", chasBaqryxx.getXb()), qdTableContentFont,0, qdTableRowMiniHeight));
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell("出 生\n日 期", qdTableContentFont,0, qdTableRowMiniHeight));
        //去除出生日期的时分秒
       String csrq = "";
       if(chasBaqryxx.getCsrq()!=null) {
    	   csrq = DateTimeUtils.getDateStr(chasBaqryxx.getCsrq(), 13);
           if (csrq!=null&&csrq.indexOf(" ")>0)csrq=csrq.substring(0,csrq.indexOf(" "));
       }
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell(csrq, qdTableContentFont,0, qdTableRowMiniHeight));
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell("联 系\n方 式", qdTableContentFont,0, qdTableRowMiniHeight));
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell(chasBaqryxx.getLxdh(), qdTableContentFont,0, qdTableRowMiniHeight));
       
      //第二行
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell("身份证\n证种类", qdTableContentFont,0, qdTableRowMiniHeight));
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell(DicUtil.translate("CHAS_ZD_CASE_ZJLX", chasBaqryxx.getZjlx()), qdTableContentFont,0, qdTableRowMiniHeight));
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell("身份证\n证号码", qdTableContentFont,0, qdTableRowMiniHeight));
       PdfPCell zjhm = PdfPrintUtil.getTableCell(chasBaqryxx.getRysfzh(), qdTableContentFont,0, qdTableRowMiniHeight);
       zjhm.setColspan(3);
       ryjbxxTab.addCell(zjhm);
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell("到 案 \n方 式", qdTableContentFont,0, qdTableRowMiniHeight));
       String dafs = chasBaqryxx.getDafs();
        if(SYSCONSTANT.DAFS_QT.equals(dafs)){
            dafs=chasBaqryxx.getDafsText();
        }else{
            dafs= DicUtil.translate("DAFS",dafs);
        }
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell(dafs, qdTableContentFont,0, qdTableRowMiniHeight));
       
       //第三行
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell("户籍地", qdTableContentFont,0, qdTableRowMiniHeight));
       PdfPCell hjd = PdfPrintUtil.getTableCell(chasBaqryxx.getHjdxz(), qdTableContentFont,0, qdTableRowMiniHeight);
       hjd.setHorizontalAlignment(Element.ALIGN_LEFT);
       hjd.setColspan(7);
       ryjbxxTab.addCell(hjd);
       
       //第四行
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell("现住址", qdTableContentFont,0, qdTableRowMiniHeight));
       PdfPCell zz = PdfPrintUtil.getTableCell(chasBaqryxx.getXzdxz(), qdTableContentFont,0, qdTableRowMiniHeight);
       zz.setHorizontalAlignment(Element.ALIGN_LEFT);
       zz.setColspan(7);
       ryjbxxTab.addCell(zz);       
       
       
      //第五行
        String jqbh_ = chasBaqryxx.getJqbh();
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell("警情编号", qdTableContentFont,0, qdTableRowMiniHeight));
       PdfPCell jqbh = PdfPrintUtil.getTableCell(jqbh_, qdTableContentFont,0, qdTableRowMiniHeight);
       jqbh.setHorizontalAlignment(Element.ALIGN_CENTER);
       jqbh.setColspan(4);
       ryjbxxTab.addCell(jqbh);
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell("案由", qdTableContentFont,0, qdTableRowMiniHeight));
     //  String rsy = mainRs.getString("RSYY");
       String rsyystr=chasBaqryxx.getRyzaybh();
       if(SYSCONSTANT.DAFS_QT.equals(rsyystr)){
    	   rsyystr="其他("+chasBaqryxx.getRyzaymc()+")";
       }else{
    	   rsyystr= DicUtil.translate("RSYY",rsyystr);
       }
       PdfPCell rsyy = PdfPrintUtil.getTableCell(rsyystr, qdTableContentFont,0, qdTableRowMiniHeight);
       rsyy.setHorizontalAlignment(Element.ALIGN_CENTER);
       rsyy.setColspan(2);
       ryjbxxTab.addCell(rsyy);
       
       
       //
        String ajbh_ = chasBaqryxx.getAjbh();
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell("案件编号", qdTableContentFont,0, qdTableRowMiniHeight));
       //---PdfPCell wsbh = PdfPrintUtil.getTableCell(mainRs.getString("wsbh"), qdTableContentFont,0, qdTableRowMiniHeight);
       PdfPCell wsbh = PdfPrintUtil.getTableCell(ajbh_, qdTableContentFont,0, qdTableRowMiniHeight);
       wsbh.setHorizontalAlignment(Element.ALIGN_CENTER);
       wsbh.setColspan(4);
       ryjbxxTab.addCell(wsbh);
       
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell("办案民警\n(签名)", qdTableContentFont,0, qdTableRowMiniHeight));
       
       PdfPCell bamj = imgHandle(qmny[0],signmap.get(qmny[0]),0,60,25,true);
       bamj.setHorizontalAlignment(Element.ALIGN_CENTER);
       bamj.setColspan(2);
       ryjbxxTab.addCell(bamj);

       //第六行
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell("入区时间", qdTableContentFont,0, qdTableRowMiniHeight));
       PdfPCell rssj = PdfPrintUtil.getTableCell(DateTimeUtils.getDateStr(chasBaqryxx.getRRssj(), 15), qdTableContentFont,0, qdTableRowMiniHeight);
       rssj.setHorizontalAlignment(Element.ALIGN_CENTER);
       rssj.setColspan(4);
       ryjbxxTab.addCell(rssj);
       //-----没获取到
       ryjbxxTab.addCell(PdfPrintUtil.getTableCell("管 理 员\n（签名）", qdTableContentFont,0, qdTableRowMiniHeight));
       
       PdfPCell glyqm = imgHandle(qmny[1], signmap.get(qmny[1]),0,60,25,true);
       glyqm.setHorizontalAlignment(Element.ALIGN_CENTER);
       glyqm.setColspan(2);
       ryjbxxTab.addCell(glyqm);
       
       return ryjbxxTab;
	}
	private PdfPCell imgHandle(String type, String imgBody, float percent, float width, float height,boolean refine) {
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
}
