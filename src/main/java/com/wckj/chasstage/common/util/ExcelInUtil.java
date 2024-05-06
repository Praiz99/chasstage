package com.wckj.chasstage.common.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelInUtil {
    public static class ExcelColumn {
        private String name;
        private String fieldName;

        public ExcelColumn() {
        }

        public ExcelColumn(String name, String fieldName) {
            this.name = name;
            this.fieldName = fieldName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }
    }

    public static List<Map<String, Object>> readExcel(InputStream inputStream, List<ExcelColumn> columnList) throws Exception {

        Workbook wb = new HSSFWorkbook(inputStream);

        Sheet sheet = wb.getSheetAt(0);
        List<Map<String, Object>> list = new ArrayList();
        int firstRowIndex = sheet.getFirstRowNum();
        int lastRowIndex = sheet.getLastRowNum();
        List<HSSFPictureData> pictures = ((HSSFWorkbook) wb).getAllPictures();
        if (pictures.size() < lastRowIndex - firstRowIndex) {
            //return list;
        }
        List<String> columnNameList = getTableHeadInfo(sheet,columnList);
        for (int rIndex = firstRowIndex+1; rIndex <= lastRowIndex; ++rIndex) {
            Map<String, Object> map = new HashMap();
            Row row = sheet.getRow(rIndex);
            if (row != null) {
                int firstCellIndex = row.getFirstCellNum();
                int lastCellIndex = row.getLastCellNum();
                int cIndex = firstCellIndex;
                Map<String, PictureData> sheetIndexPicMap;
                while (true) {
                    if (cIndex >= lastCellIndex) {
                        break;
                    }
                    Cell cell = row.getCell(cIndex);
                    Object value = "";
                    if (cell != null) {
                        value = getCellValue(cell);

                            if (cIndex == 5) {
                                sheetIndexPicMap = getSheetPictrues03(rIndex, (HSSFSheet) sheet, (HSSFWorkbook) wb);
                                if (sheetIndexPicMap.get(rIndex + "_5") != null && !"".equals(sheetIndexPicMap.get(rIndex + "_5"))) {
                                    PictureData pictureData = sheetIndexPicMap.get(rIndex + "_5");
                                    map.put("photo", pictureData.getData());
                                    map.put("mimeType", pictureData.getMimeType().replace("image/", ""));
                                } else {
                                    /*sheetIndexPicMap = getCellPictrues03(rIndex, (HSSFWorkbook) wb);
                                    if (sheetIndexPicMap.get(rIndex + "_5") != null && !"".equals(sheetIndexPicMap.get(rIndex + "_5"))) {
                                        PictureData pictureData = sheetIndexPicMap.get(rIndex + "_5");
                                        map.put("photo", pictureData.getData());
                                        map.put("mimeType", pictureData.getMimeType().replace("image/", ""));
                                    }*/
                                }
                            }

                            String columnName = columnNameList.get(cIndex);
                            if (value == null || value.toString().trim().equals("")) {
                                value = "";
                            }
                            if ("".equals(value)) {
                                //map.put("error","1");
                                break;
                            }
                            map.put(columnName, value);
                            map.put("rowNum", rIndex + 1);

                    }
                    ++cIndex;
                }
            }
            if (!map.isEmpty() && map.values() != null) {
                list.add(map);
            }
        }
        return list;
    }

    public static List<String> getTableHeadInfo(Sheet sheet, List<ExcelColumn> columnList)throws Exception{
        List<String> columnNameList = new ArrayList();
        int rIndex = sheet.getFirstRowNum();
            Row row = sheet.getRow(rIndex);
            if (row != null) {
                int firstCellIndex = row.getFirstCellNum();
                int lastCellIndex = row.getLastCellNum();
                for(int cIndex=firstCellIndex;cIndex <= lastCellIndex;cIndex++){
                    Cell cell = row.getCell(cIndex);
                    Object value = "";
                    if (cell != null) {
                        value = getCellValue(cell);
                        //如果第一行

                            if (!value.toString().equals("")) {
                                boolean belongColumn = false;
                                for (int i = 0; i < columnList.size(); ++i) {
                                    String columnNameCn = columnList.get(i).getName();
                                    if (columnNameCn.trim().equals(value.toString().trim())) {
                                        columnNameList.add(columnList.get(i).getFieldName());
                                        belongColumn = true;
                                        break;
                                    }
                                }
                                if (!belongColumn) {
                                    throw new Exception(value.toString() + "表头字段不符合规范！请检查格式");
                                }
                            }

                    }
                }

            }
            return columnNameList;
    }
    public static Object getCellValue(Cell cell) {
        Object cellValue = null;
        if (cell == null) {
            return cellValue;
        } else {
            switch (cell.getCellType()) {
                case 0:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                        cellValue = formater.format(date);
                    } else {
                        DecimalFormat df = new DecimalFormat("0");
                        cellValue = df.format(cell.getNumericCellValue());
                    }
                    break;
                case 1:
                    cellValue = cell.getStringCellValue().trim();
                    break;
                case 2:
                    cellValue = cell.getCellFormula();
                    break;
                case 3:
                    cellValue = null;
                    break;
                case 4:
                    cellValue = cell.getBooleanCellValue();
                    break;
                case 5:
                    cellValue = "";
                    break;
                default:
                    cellValue = "";
            }
            return cellValue;
        }
    }

    /**
     * 获取Excel2003图片
     *
     * @param sheetNum 当前sheet编号
     * @param sheet    当前sheet对象
     * @param workbook 工作簿对象
     * @return Map key:图片单元格索引（0_1）String，value:图片流PictureData
     * @throws IOException
     */
    public static Map<String, PictureData> getSheetPictrues03(int sheetNum, HSSFSheet sheet, HSSFWorkbook workbook) {
        Map<String, PictureData> sheetIndexPicMap = new HashMap<>();
        List<HSSFPictureData> pictures = workbook.getAllPictures();
        if (pictures.size() != 0 && sheet.getDrawingPatriarch() != null) {
            for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) {
                HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
                if (shape instanceof HSSFPicture) {
                    HSSFPicture pic = (HSSFPicture) shape;
                    int pictureIndex = pic.getPictureIndex() - 1;
                    HSSFPictureData picData = pictures.get(pictureIndex);
                    String picIndex = String.valueOf(anchor.getRow1()) + "_"
                            + String.valueOf(anchor.getCol1());
                    if (sheetNum == anchor.getRow1()) {
                        sheetIndexPicMap.put(picIndex, picData);
                        return sheetIndexPicMap;
                    }
                }
            }
        }
        return sheetIndexPicMap;
    }

    public static Map<String, PictureData> getCellPictrues03(int sheetNum, HSSFWorkbook workbook) {
        Map<String, PictureData> sheetIndexPicMap = new HashMap<>();
        List<HSSFPictureData> pictures = workbook.getAllPictures();
        if (pictures.size() != 0 && sheetNum <= pictures.size()) {
            HSSFPictureData pictureData = pictures.get(sheetNum - 1);
            if (pictureData != null) {
                sheetIndexPicMap.put(sheetNum + "_5", pictureData);
            }
        }
        return sheetIndexPicMap;
    }

    public static void main(String[] args) throws Exception {
        List<ExcelColumn> columnList = new ArrayList<>();
        columnList.add(new ExcelColumn("序号", "num"));
        columnList.add(new ExcelColumn("单位", "dwdm"));
        columnList.add(new ExcelColumn("民警姓名", "mjxm"));
        columnList.add(new ExcelColumn("身份证号", "mjsfzh"));
        columnList.add(new ExcelColumn("警号", "mjjh"));
        columnList.add(new ExcelColumn("照片", "zpid"));
        columnList.add(new ExcelColumn("备注", "bz"));
        File file = new File("F:\\民警照片.xls");
        InputStream inputStream = new FileInputStream(file);
        readExcel(inputStream, columnList);
    }
}

