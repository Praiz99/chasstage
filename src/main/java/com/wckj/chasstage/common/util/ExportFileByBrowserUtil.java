package com.wckj.chasstage.common.util;

import com.wckj.framework.core.data.set.MapArrayDataSet;
import com.wckj.framework.core.iae.column.DefaultExportColumn;
import com.wckj.framework.core.iae.column.IExportColumn;
import com.wckj.framework.core.iae.export.ExcelExport;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.jdone.modules.com.export.entity.JdoneExport;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 定制浏览器导出工具类
 */
public class ExportFileByBrowserUtil {
    protected static Logger log = LoggerFactory.getLogger(ExportFileByBrowserUtil.class);

    public static void getDownFileDataByUrl(String fileName, HttpServletRequest request, HttpServletResponse response){
        FileInputStream inputStream = null;
        OutputStream out = null;
        try {
            String suffix = request.getParameter("suffix");
            String separator = File.separator+"";
            String realPath = request.getSession().getServletContext().getRealPath("");
            realPath = realPath.substring(0, realPath.indexOf(separator));
            LocalDate todayDate = LocalDate.now();
            String directoryName = todayDate.toString();
            fileName = URLEncoder.encode(fileName,"UTF-8");
            inputStream = new FileInputStream(realPath+"/chasExcel/"+directoryName+"/"+fileName+suffix);
            if(".xls".equals(suffix)){
                Workbook workbook = new HSSFWorkbook(inputStream);
                response.setContentType("application/octet-stream");//告诉浏览器输出内容为流;设置了头文件才会有弹框;
                response.addHeader("Content-Disposition", "attachment;filename="+ fileName + suffix);
                out = response.getOutputStream();
                workbook.write(out);
            }else if(".pdf".equals(suffix)){
                response.setContentType("application/octet-stream");//告诉浏览器输出内容为流;设置了头文件才会有弹框;
                response.addHeader("Content-Disposition", "attachment;filename="+ fileName + suffix);
                out = response.getOutputStream();
                IOUtils.copyLarge(inputStream, out);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("getDownFileDataByUrl:",e);
        }finally {
            try {
                inputStream.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写入导出数据到本地磁盘（因为新版浏览器不支持原本的方式下载）
     * @param request
     */
    public static String writeFiletoDisk(HttpServletRequest request, JdoneExport jdoneExport, Object exportData, String suffix){
        String separator = File.separator+"";
        String realPath = request.getSession().getServletContext().getRealPath("");
        realPath = realPath.substring(0, realPath.indexOf(separator));
        String fileName = jdoneExport.getExportFileName();
        LocalDate todayDate = LocalDate.now();
        String directoryName = todayDate.toString();
        if (StringUtils.isEmpty(fileName)) {
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            fileName = "exportData_" + df.format(day);
        }
        FileOutputStream outputStream = null;
        try{
            fileName = fileName.replaceAll(" ","");
            fileName = URLEncoder.encode(fileName,"UTF-8");

            //删除历史的文件
            removeHistoryDir(realPath+"/chasExcel/",directoryName);

            //新增当天的文件
            File directory = new File(realPath+"/chasExcel/"+directoryName);
            if(!directory.exists()){
                directory.mkdirs();
            }
            File file = new File(realPath+"/chasExcel/"+directoryName+"/"+fileName+suffix);
            if(!file.exists()){
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            getDataByWriteExcel(jdoneExport,exportData,outputStream);
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
            log.error("写入导出人员excel异常:",e);
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    public static String writeFiletoDisk(HttpServletRequest request,String fileName,byte[] data,String suffix){
        String separator = File.separator+"";
        String realPath = request.getSession().getServletContext().getRealPath("");
        realPath = realPath.substring(0, realPath.indexOf(separator));
        LocalDate todayDate = LocalDate.now();
        String directoryName = todayDate.toString();

        FileOutputStream outputStream = null;
        try{
            fileName = URLEncoder.encode(fileName,"UTF-8");

            //删除历史的文件
            removeHistoryDir(realPath+"/chasExcel/",directoryName);

            //新增当天的文件
            File directory = new File(realPath+"/chasExcel/"+directoryName);
            if(!directory.exists()){
                directory.mkdirs();
            }
            File file = new File(realPath+"/chasExcel/"+directoryName+"/"+fileName+suffix);
            if(!file.exists()){
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            outputStream.write(data);
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
            log.error("写入导出人员登记表异常:",e);
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    public static void removeHistoryDir(String path,String directoryName) {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        if(tempList != null){
            for (int i = 0; i < tempList.length; i++) {
                if (tempList[i].isDirectory()) {
                    files.add(tempList[i].toString());
                    if(!directoryName.equals(tempList[i].getName())){
                        deleteDir(new File(path+tempList[i].getName()));
                    }
                }
            }
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public static void getDataByWriteExcel(JdoneExport jdoneExport, Object datamap, OutputStream outputStream) throws Exception{
        List<IExportColumn> columns = new ArrayList();
        List<Map<String, Object>> exColumns = jdoneExport.getExportColumns();

        for(int i = 0; i < exColumns.size(); ++i) {
            DefaultExportColumn column = new DefaultExportColumn();
            column.setSourceFieldName((String)((Map)exColumns.get(i)).get("field"));
            column.setDestFieldName((String)((Map)exColumns.get(i)).get("title"));
            if (StringUtils.isNotEmpty((String)((Map)exColumns.get(i)).get("valueType"))) {
                column.setValueType((String)((Map)exColumns.get(i)).get("valueType"));
            }

            if (((Map)exColumns.get(i)).get("dicInfo") != null) {
                column.setDicInfo(((Map)exColumns.get(i)).get("dicInfo"));
                column.setDicType((String)((Map)exColumns.get(i)).get("dicType"));
            }

            columns.add(column);
        }

        MapArrayDataSet rs = new MapArrayDataSet();
        List<Map> exportDataList = com.wckj.framework.json.jackson.JsonUtil.getListFromJsonString(com.wckj.framework.json.jackson.JsonUtil.getJsonString(datamap), Map.class);
        Map[] exportMaps = new Map[exportDataList.size()];

        for(int i = 0; i < exportDataList.size(); ++i) {
            exportMaps[i] = (Map)exportDataList.get(i);
            if(exportMaps[i].containsKey("wpzt")){
                String wpzt = exportMaps[i].get("wpzt")==null?"":exportMaps[i].get("wpzt").toString();
                if("02".equals(wpzt)){
                    Object obj=exportMaps[i].get("wpclztName");
                    exportMaps[i].put("wpztName", obj);
                }
            }
        }
        rs.setMapData(exportMaps);
        ExcelExport export = new ExcelExport();
        export.setData(rs);
        export.setExportColumns(columns);
        export.setExcelType(jdoneExport.getExportFileType());
        export.export(outputStream);
    }

    public byte[] toByteArray(String filename) throws IOException {
        File f = new File(filename);
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("读取本地PDF文件异常:",e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
        return null;
    }
}
