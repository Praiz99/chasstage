package com.wckj.chasstage.common.util.pdf;

import DBstep.iMsgServer2000;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.baqry.service.impl.ChasBaqryxxServiceImpl;
import com.wckj.chasstage.modules.signconfig.entity.ChasXtSignConfig;
import com.wckj.chasstage.modules.signconfig.service.ChasXtSignConfigService;
import com.wckj.chasstage.modules.signconfig.service.impl.ChasXtSignConfigServiceImpl;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.frws.file.IByteFileObj;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.frws.sdk.FrwsApiForThirdPart;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;

/**
 * PDF 工具类
 */
public class PdfComprehensiveUtil {
    protected static Logger log = LoggerFactory.getLogger(PdfComprehensiveUtil.class);

    public static byte[] buildMsgObjectRyxx_Interface(String businessId,boolean showImg) throws Exception {
        // 二维码是否需要加密
        boolean isEncryptBarcode = true;
        long begin = new Date().getTime();
        log.debug(businessId+":开始查询人员信息");
        ChasBaqryxxService baqryxxService = ServiceContext.getServiceByClass(ChasBaqryxxServiceImpl.class);
        ChasBaqryxx baqryxx = baqryxxService.findByRybh(businessId);
        long end = new Date().getTime();
        log.debug(businessId + ":结束查询人员信息," + (end - begin));
        ChasXtSignConfigService configService = ServiceContext.getServiceByClass(ChasXtSignConfigServiceImpl.class);
        ChasXtSignConfig signConfig = configService.findByBaqid(baqryxx.getBaqid());
        if(signConfig == null){
            signConfig = new ChasXtSignConfig();
        }
        if(StringUtil.equals(signConfig.getXbqz(),"1")){
            //采用协办签字模板
            RegisterDocumentTemplate handler = new RegisterDocumentTemplate(baqryxx.getBaqid());
            handler.setBusinessId(baqryxx.getId());
            handler.setEncryptBarcode(isEncryptBarcode);
            handler.setNewDoc(showImg);  //是否添加签字捺印图片
            handler.handlePrint();
            long totalEnd = new Date().getTime();
            log.debug(businessId+":登记表封装数据总耗时"+(totalEnd - begin));
            return handler.getContent();
        }

        caseAqBaqsyqkdjNew handler = new caseAqBaqsyqkdjNew();
        handler.setBusinessId(baqryxx.getId());
        handler.setEncryptBarcode(isEncryptBarcode);
        handler.setNewDoc(showImg);  //是否添加签字捺印图片
        handler.handlePrint();
        long totalEnd = new Date().getTime();
        log.debug(businessId+":登记表封装数据总耗时"+(totalEnd - begin));
        return handler.getContent();
    }

    /**
     * 尿检PDF
     * @param businessId
     * @param msgObj
     * @param isLoadTemplate
     * @throws ServletException
     * @throws IOException
     * @throws DocumentException
     */
    public static void buildMsgObjectNj(String businessId,iMsgServer2000 msgObj, boolean isLoadTemplate,String bizType)throws ServletException, IOException, DocumentException {
        try {
            // 加载已生成的文档
            if (isLoadTemplate) {
                //从文件服务器上下载这个PDF，如果存在则返回。
                //bizType  NJWS  NYWS 根据不同类型获取文书PDF
                IByteFileObj fileObj = FrwsApiForThirdPart.downloadByteFileByBizIdBizType(businessId,bizType,"");
                if(fileObj != null){
                    msgObj.MsgFileBody(fileObj.getBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("buildMsgObjectNj:",e);
        }
    }

    public static byte[] buildMsgObjectNj(String businessId,String bizType)throws Exception {
        try {
            // 加载已生成的文档
            if (true) {
                //从文件服务器上下载这个PDF，如果存在则返回。
                IByteFileObj fileObj = FrwsApiForThirdPart.downloadByteFileByBizId(businessId);
                if(fileObj != null){
                    return fileObj.getBytes();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("buildMsgObjectNj:",e);
        } finally {

        }
        return null;
    }

    /**
     * base64图片数据 签名捺印合并
     * @param qzBase64 签字
     * @param nyBase64 捺印
     * @return
     */
    public static byte[] hbqznytp(String qzBase64, String nyBase64) {
        try {
            if(StringUtil.isEmpty(qzBase64) && StringUtil.isEmpty(nyBase64)){
                return null;
            }
            if(StringUtil.isEmpty(qzBase64) && StringUtil.isNotEmpty(nyBase64)){
                return tptmh(DatatypeConverter.parseBase64Binary(nyBase64));
            }
            if(StringUtil.isEmpty(nyBase64) && StringUtil.isNotEmpty(qzBase64)){
                return DatatypeConverter.parseBase64Binary(qzBase64);
            }
            byte[] qzbyte = DatatypeConverter.parseBase64Binary(qzBase64);
            byte[] nybyte = DatatypeConverter.parseBase64Binary(nyBase64);
            /*//先透明化捺印图片。
            nybyte = tptmh(nybyte);
            qzbyte = tptmh(qzbyte);
            //合并签字捺印图片
            byte[] bytes = mergeImage(qzbyte, nybyte);*/
            BufferedImage bufferedImage = byte2BufferedImage(qzbyte);
            BufferedImage bufferedImage1 = byte2BufferedImage(nybyte);
            BufferedImage bufferedImage2 = mergeImage(bufferedImage, bufferedImage1);
            byte[] bytes = bufferedImage2Byte(bufferedImage2);
            return bytes;
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] hbqznytp(String qzBase64, String nyBase64,boolean refine) {
        try {
            if(StringUtil.isNotEmpty(qzBase64)){
                if(StringUtil.isEmpty(nyBase64)){
                    byte[] bytes = DatatypeConverter.parseBase64Binary(qzBase64);
                    BufferedImage bufferedImage = byte2BufferedImage(bytes);
                    if(refine){
                        bufferedImage = drawImageByImage2(bufferedImage,BufferedImage.TYPE_4BYTE_ABGR);
                    }
                    return bufferedImage2Byte(bufferedImage);
                }
            }else if(StringUtil.isNotEmpty(nyBase64)){
                return DatatypeConverter.parseBase64Binary(nyBase64);
            }else{
                return null;
            }
            byte[] qzbyte = DatatypeConverter.parseBase64Binary(qzBase64);
            byte[] nybyte = DatatypeConverter.parseBase64Binary(nyBase64);
            BufferedImage bufferedImage = byte2BufferedImage(qzbyte);
            if(refine){
                bufferedImage = drawImageByImage2(bufferedImage,BufferedImage.TYPE_4BYTE_ABGR);
            }
            BufferedImage bufferedImage1 = byte2BufferedImage(nybyte);
            BufferedImage bufferedImage2 = mergeImage(bufferedImage, bufferedImage1);
            byte[] bytes = bufferedImage2Byte(bufferedImage2);
            /*//先透明化捺印图片。
            nybyte = tptmh2(nybyte);
            //合并签字捺印图片
            byte[] bytes = mergeImage(qzbyte,nybyte);*/
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字节数据 签字捺印合并
     * @param qzbyte 签字
     * @param nybyte 捺印
     * @return
     */
    public static byte[] hbqznytp(byte[] qzbyte, byte[] nybyte) {
        try {
            //先透明化捺印图片。
            nybyte = tptmh(nybyte);
            //合并签字捺印图片
            byte[] bytes = mergeImage(qzbyte, nybyte);
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 透明图片
     *
     * @param nyImg
     * @return
     * @throws Exception
     */
    public static byte[] tptmh(byte[] nyImg) throws Exception{
        BufferedImage image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(nyImg));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // 高度和宽度
        int height = image.getHeight();
        int width = image.getWidth();

        // 生产背景透明和内容透明的图片
        ImageIcon imageIcon = new ImageIcon(image);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics(); // 获取画笔
        g2D.drawImage(imageIcon.getImage(), 0, 0, null); // 绘制Image的图片，使用了imageIcon.getImage()，目的就是得到image,直接使用image就可以的

        int alpha = 0; // 图片透明度
        // 外层遍历是Y轴的像素
        for (int y = bufferedImage.getMinY(); y < bufferedImage.getHeight(); y++) {
            // 内层遍历是X轴的像素
            for (int x = bufferedImage.getMinX(); x < bufferedImage.getWidth(); x++) {
                int rgb = bufferedImage.getRGB(x, y);
                // 对当前颜色判断是否在指定区间内
                if (colorInRange(rgb)) {
                    alpha = 0;
                } else {
                    // 设置为不透明
                    alpha = 255;
                }
                // #AARRGGBB 最前两位为透明度
                rgb = (alpha << 24) | (rgb & 0x00ffffff);
                bufferedImage.setRGB(x, y, rgb);
            }
        }
        // 绘制设置了RGB的新图片,这一步感觉不用也可以只是透明地方的深浅有变化而已，就像蒙了两层的感觉
        g2D.drawImage(bufferedImage, 0, 0, null);

        // 生成图片为PNG
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
        byte[] bytes = outputStream.toByteArray();
        outputStream.close();
        return bytes;
    }

    public static byte[] tptmh2(byte[] nyImg) throws Exception{
        BufferedImage image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(nyImg));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // 高度和宽度
        int height = image.getHeight();
        int width = image.getWidth();

        // 生产背景透明和内容透明的图片
        ImageIcon imageIcon = new ImageIcon(image);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics(); // 获取画笔
        g2D.drawImage(imageIcon.getImage(), 0, 0, null); // 绘制Image的图片，使用了imageIcon.getImage()，目的就是得到image,直接使用image就可以的

        int alpha = 0; // 图片透明度
        // 外层遍历是Y轴的像素
        for (int y = bufferedImage.getMinY(); y < bufferedImage.getHeight(); y++) {
            // 内层遍历是X轴的像素
            for (int x = bufferedImage.getMinX(); x < bufferedImage.getWidth(); x++) {
                int rgb = bufferedImage.getRGB(x, y);
                // 对当前颜色判断是否在指定区间内
                if (colorInRange2(rgb)) {
                    alpha = 0;
                } else {
                    // 设置为不透明
                    alpha = 255;
                }
                // #AARRGGBB 最前两位为透明度
                rgb = (alpha << 24) | (rgb & 0x00ffffff);
                bufferedImage.setRGB(x, y, rgb);
            }
        }
        // 绘制设置了RGB的新图片,这一步感觉不用也可以只是透明地方的深浅有变化而已，就像蒙了两层的感觉
        g2D.drawImage(bufferedImage, 0, 0, null);

        // 生成图片为PNG
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
        byte[] bytes = outputStream.toByteArray();
        outputStream.close();
        return bytes;
    }

    public static byte[] tptmh3(byte[] nyImg) throws Exception{
        BufferedImage image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(nyImg));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // 高度和宽度
        int height = image.getHeight();
        int width = image.getWidth();

        // 生产背景透明和内容透明的图片
        ImageIcon imageIcon = new ImageIcon(image);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics(); // 获取画笔
        g2D.drawImage(imageIcon.getImage(), 0, 0, null); // 绘制Image的图片，使用了imageIcon.getImage()，目的就是得到image,直接使用image就可以的

        int alpha = 0; // 图片透明度
        // 外层遍历是Y轴的像素
        for (int y = bufferedImage.getMinY(); y < bufferedImage.getHeight(); y++) {
            // 内层遍历是X轴的像素
            for (int x = bufferedImage.getMinX(); x < bufferedImage.getWidth(); x++) {
                int rgb = bufferedImage.getRGB(x, y);
                // 对当前颜色判断是否在指定区间内
                if (colorInRange3(rgb)) {
                    alpha = 0;
                } else {
                    // 设置为不透明
                    alpha = 255;
                }
                // #AARRGGBB 最前两位为透明度
                rgb = (alpha << 24) | (rgb & 0x00ffffff);
                bufferedImage.setRGB(x, y, rgb);
            }
        }
        // 绘制设置了RGB的新图片,这一步感觉不用也可以只是透明地方的深浅有变化而已，就像蒙了两层的感觉
        g2D.drawImage(bufferedImage, 0, 0, null);

        // 生成图片为PNG
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
        byte[] bytes = outputStream.toByteArray();
        outputStream.close();
        return bytes;
    }

    // 判断是背景还是内容
    public static boolean colorInRange(int color) {
        int red = (color & 0xff0000) >> 16;// 获取color(RGB)中R位
        int green = (color & 0x00ff00) >> 8;// 获取color(RGB)中G位
        int blue = (color & 0x0000ff);// 获取color(RGB)中B位
        if(red == 0 && green == 0 && blue == 0){  //黑色背景也透明化
            return true;
        }
        // 通过RGB三分量来判断当前颜色是否在指定的颜色区间内
        if (red >= color_range && green >= color_range && blue >= color_range) {
            return true;
        }
        return false;
    }

    public static boolean colorInRange2(int color) {
        int red = (color & 0xff0000) >> 16;// 获取color(RGB)中R位
        int green = (color & 0x00ff00) >> 8;// 获取color(RGB)中G位
        int blue = (color & 0x0000ff);// 获取color(RGB)中B位
        if(red == 255 && green == 255 && blue == 255){  //白色背景也透明化
            return true;
        }
        // 通过RGB三分量来判断当前颜色是否在指定的颜色区间内
        if (red >= color_range && green >= color_range && blue >= color_range) {
            return true;
        }
        return false;
    }

    public static boolean colorInRange3(int color) {
        int red = (color & 0xff0000) >> 16;// 获取color(RGB)中R位
        int green = (color & 0x00ff00) >> 8;// 获取color(RGB)中G位
        int blue = (color & 0x0000ff);// 获取color(RGB)中B位
        if(red == 255 && green == 255 && blue == 255){  //黑色背景透明化
            return true;
        }
        if(red == 0 && green == 0 && blue == 0){  //白色背景也透明化
            return true;
        }
        // 通过RGB三分量来判断当前颜色是否在指定的颜色区间内
        if (red >= color_range && green >= color_range && blue >= color_range) {
            return true;
        }
        return false;
    }

    //色差范围0~255
    public static int color_range = 210;

    /**
     * 合并图片
     *
     * @param qzImg 签字图片
     * @param nyImg 捺印图片
     * @return
     * @throws IOException
     */
    public static byte[] mergeImage(byte[] qzImg, byte[] nyImg) throws IOException {
        ByteArrayInputStream qzinput = null;
        ByteArrayInputStream nyinput = null;
        BufferedImage small = null;
        BufferedImage big = null;
        ByteArrayOutputStream outputStream = null;
        try {
            nyinput = new ByteArrayInputStream(nyImg);
            small = ImageIO.read(nyinput);
            qzImg = changeSize(small.getWidth(), small.getHeight(), qzImg);
            qzinput = new ByteArrayInputStream(qzImg);
            big = ImageIO.read(qzinput);
            Graphics2D g = big.createGraphics();
            float fx = Float.parseFloat("0");
            float fy = Float.parseFloat("0");
            int x_i = (int) fx;
            int y_i = (int) fy;
            g.drawImage(small, x_i, y_i, small.getWidth(), small.getHeight(), null);
            g.dispose();
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(big, "png", outputStream);
            byte[] bytes = outputStream.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closeInputStream(qzinput);
            closeInputStream(nyinput);
            closeOutputStream(outputStream);
        }
    }

    public static byte[] createBackgroundImg(byte[] srcByte) {
        BufferedInputStream in = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ByteArrayInputStream qzinput = new ByteArrayInputStream(srcByte);
        try {
            in = new BufferedInputStream(qzinput);
            //字节流转图片对象
            Image bi = ImageIO.read(in);
            BufferedImage bufferedImage = new BufferedImage(bi.getWidth(null), bi.getHeight(null), BufferedImage.TYPE_INT_RGB);
            String colorCode = "rgba(255,255,255)";
            String[] colorArr = formatColorCode(colorCode);
            byteArrayOutputStream = new ByteArrayOutputStream();
            Graphics g = bufferedImage.getGraphics();
            g.setColor(new Color(Integer.parseInt(colorArr[0]), Integer.parseInt(colorArr[1]), Integer.parseInt(colorArr[2])));

            for (int i = 0; i < bufferedImage.getWidth(); i++) {
                for (int j = 0; j < bufferedImage.getHeight(); j++) {
                    g.drawLine(i, j, bufferedImage.getWidth(), bufferedImage.getHeight());
                }
            }
            g.dispose();
            boolean val = false;

            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            byte[] resultByte = mergeImage(bytes, srcByte);
            return resultByte;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (byteArrayOutputStream != null) {
                closeInputStream(in);
                closeInputStream(qzinput);
                closeOutputStream(byteArrayOutputStream);
            }
        }
    }

    /**
     * 改变图片大小
     *
     * @param newWidth
     * @param newHeight
     * @param qzImg
     * @return
     */
    public static byte[] changeSize(int newWidth, int newHeight, byte[] qzImg) {
        BufferedInputStream in = null;
        ByteArrayInputStream qzinput = new ByteArrayInputStream(qzImg);
        ByteArrayOutputStream outputStream = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(qzinput);
            //字节流转图片对象
            Image bi = ImageIO.read(in);
            //构建图片流
            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            //绘制改变尺寸后的图
            tag.getGraphics().drawImage(bi, 0, 0, newWidth, newHeight, null);
            //输出流
            outputStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(outputStream);
            ImageIO.write(tag, "PNG", out);
            byte[] bytes = outputStream.toByteArray();
            return bytes;
        } catch (IOException e) {
            return null;
        } finally {
            closeInputStream(in);
            closeOutputStream(out);
            closeOutputStream(outputStream);
        }
    }

    /**
     * 关不输入流
     *
     * @param inputStream
     */
    public static void closeInputStream(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭输出流
     *
     * @param outputStream
     */
    public static void closeOutputStream(OutputStream outputStream) {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] formatColorCode(String colorCode) {
        if (!colorCode.contains("rgba")) {
            return null;
        }
        colorCode = colorCode.replaceAll("rgba\\(", "").replaceAll("\\)", "");

        return colorCode.split(",");
    }

    private static BufferedImage mergeImage(BufferedImage sign, BufferedImage finger) {
        int width = finger.getWidth();
        int height = finger.getHeight();
        if(sign.getWidth() > finger.getWidth()){
            width = sign.getWidth();
        }
        if(sign.getHeight() > finger.getHeight()){
            height = sign.getHeight();
        }
        BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_4BYTE_ABGR);
        paintedImage(sign,bufferedImage);
        finger = drawImageByImage(finger,BufferedImage.TYPE_4BYTE_ABGR);
        paintedImage(finger,bufferedImage);
        return bufferedImage;
    }

    private static void paintedImage(BufferedImage sitter,BufferedImage bufferedImage) {
        int relativeX = (bufferedImage.getWidth() - sitter.getWidth()) / 2;
        int relativeY = (bufferedImage.getHeight() - sitter.getHeight()) / 2;
        // 当前方法会覆盖原先已绘制好的图，所以采用graphics 模式。
        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(sitter,relativeX,relativeY,null);
        graphics.dispose();
    }

    public static BufferedImage drawImageByImage(BufferedImage sitter,int type){
        if(sitter.getType() == BufferedImage.TYPE_4BYTE_ABGR){
            return sitter;
        }
        BufferedImage bufferedImage = new BufferedImage(sitter.getWidth(),sitter.getHeight(),type);
        for (int i = 0; i < sitter.getWidth(); i++) {
            for (int j = 0; j < sitter.getHeight(); j++) {
                int color = sitter.getRGB(i,j);
                int red = (color & 0xff0000) >> 16;// 获取color(RGB)中R位
                int green = (color & 0x00ff00) >> 8;// 获取color(RGB)中G位
                int blue = (color & 0x0000ff);// 获取color(RGB)中B位
                if(red != 255 && green != 255 && blue != 255){  // 白底不合并
                    bufferedImage.setRGB(i,j,color);
                }
            }
        }
        return bufferedImage;
    }

    public static BufferedImage drawImageByImage2(BufferedImage sitter,int type){
        BufferedImage bufferedImage = new BufferedImage(sitter.getWidth(),sitter.getHeight(),type);
        for (int i = 0; i < sitter.getWidth(); i++) {
            for (int j = 0; j < sitter.getHeight(); j++) {
                int color = sitter.getRGB(i,j);
                int red = (color & 0xff0000) >> 16;// 获取color(RGB)中R位
                int green = (color & 0x00ff00) >> 8;// 获取color(RGB)中G位
                int blue = (color & 0x0000ff);// 获取color(RGB)中B位
                if(red <= 150 && green <= 150 && blue <= 150){  // 只写入黑色
                    bufferedImage.setRGB(i,j,color);
                }
            }
        }
        return bufferedImage;
    }

    private static void createPdfOrderImg(BufferedImage read,String path,String newPath) throws IOException, DocumentException {
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        FileOutputStream fileOutputStream = new FileOutputStream(new File(newPath));
        PdfReader pdfReader = new PdfReader(fileInputStream);
        PdfStamper pdfStamper = new PdfStamper(pdfReader,fileOutputStream);
        PdfContentByte overContent = pdfStamper.getOverContent(1);
        com.itextpdf.text.Image instance = com.itextpdf.text.Image.getInstance(bufferedImage2Byte(read));
        instance.setAbsolutePosition(100,100);
        overContent.addImage(instance);
        pdfStamper.close();
    }

    public static byte[] bufferedImage2Byte(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage,"png",byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static BufferedImage byte2BufferedImage(byte[] bytes) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        return ImageIO.read(in);
    }
}
