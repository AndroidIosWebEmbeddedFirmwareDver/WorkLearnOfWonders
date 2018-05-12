package com.wondersgroup.healthSC.services.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by ys on 2017/04/13.
 *
 */
public class ExcelParse {

    /**
     * @param is xlsx格式的文件流
     * @param fieldNames 文件定义的字段列表,必须和实际文件定义的字段顺序一致
     * @param checkedFieldNames 非空字段的检查
     * @return List<Map<String, Object>> map:{fieldName:fieldValue}包含一条{"rowNum":n};//当前为第几行数据
     */
    public static List<Map<String, Object>> parseExcelXssfFile(InputStream is, String[] fieldNames, String[] checkedFieldNames){
        List<Map<String, Object>> datas = new ArrayList<>();
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        int lastRowNum = xssfSheet.getLastRowNum();
        //只有一个头 不处理
        if (lastRowNum == 0){
            return datas;
        }
        Iterator<Row> rowIterator = xssfSheet.iterator();
        Row row = rowIterator.next();//去掉头
        //总列数
        int tdLength = row.getLastCellNum();
        //强制要求数据列和所需要的一样
        if (tdLength != fieldNames.length){
            return datas;
        }
        boolean isCheckNoEmpty = null != checkedFieldNames && checkedFieldNames.length > 0;
        DecimalFormat decimalFormat = new DecimalFormat("#");
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            Map<String, Object> dataRow = new LinkedHashMap<>();
            int rowNum = row.getRowNum()+1;
            dataRow.put("rowNum", rowNum);
            int tmpValueNum = 0;
            for (int i=0; i< tdLength; i++){
                XSSFCell cell = (XSSFCell) row.getCell(i);
                if (null == cell){
                    tmpValueNum++;
                    dataRow.put(fieldNames[i], "");
                    continue;
                }
                Object value;
                switch (cell.getCellType()){
                    case XSSFCell.CELL_TYPE_STRING:
                        value = cell.getStringCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_NUMERIC:
                        value = cell.getRawValue();
                        break;
                    case XSSFCell.CELL_TYPE_FORMULA:
                        value = cell.getDateCellValue();
                        break;
                    default:
                        value = "";
                }
                dataRow.put(fieldNames[i], value);
            }
            //当前行的数据全部为空 不要
            if (tmpValueNum == tdLength){
                continue;
            }
            datas.add(dataRow);
            //非空字段验证
            if (isCheckNoEmpty){
                for (String checkedFieldName : checkedFieldNames){
                    if (dataRow.containsKey(checkedFieldName) && StringUtils.isEmpty(dataRow.get(checkedFieldName).toString())){
                        throw new RuntimeException(String.format("第%s行 字段:%s不能为空! ", rowNum, checkedFieldName));
                    }
                }
            }
        }
        return datas;
    }

    @SuppressWarnings("resource")
    public static File writerAsxlsxExcel(String fileName, List<ArrayList<Object>> list, String titleRow[]) throws IOException {
        String tmpPath = "/tmp/heal_qg/payReoprting";
        return writer(tmpPath, fileName, "xlsx", list, titleRow, "健康双流APP建设银行网络在线支付流水");
    }

    /**
     * @param path 文件存放目录
     * @param fileName 文件名
     * @param fileType 文件格式[支持：xls,xlsx]
     * @param list 数据列表
     * @param titleRow 数据title
     * @param title 第一行标题(为空就不设置)
     * @return
     * @throws IOException
     */
    @SuppressWarnings("resource")
    private static File writer(String path, String fileName, String fileType,List<ArrayList<Object>> list, String titleRow[], String title) throws IOException {
        Workbook wb = null;
        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook();
        } else if(fileType.equals("xlsx")) {
            wb = new XSSFWorkbook();
        } else {
            throw new RuntimeException("文件格式不支持");
        }
        File pathDir = new File(path);
        if (!pathDir.exists()){
            pathDir.mkdirs();
        }
        String excelPath = path + File.separator + fileName+"."+fileType;
        File file = new File(excelPath);
        Sheet sheet =null;
        //创建工作文档对象
        if (!file.exists()) {
            //创建sheet对象
            sheet = (Sheet) wb.createSheet("sheet1");
            OutputStream outputStream = new FileOutputStream(excelPath);
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }
        //创建sheet对象
        if (sheet==null) {
            sheet = (Sheet) wb.createSheet("sheet1");
        }
        //添加表头
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        row.setHeight((short) 540);
        cell.setCellValue(title);    //创建第一行

        CellStyle style = wb.createCellStyle(); // 样式对象
        // 设置单元格的背景颜色为淡蓝色
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);

        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
        style.setAlignment(CellStyle.ALIGN_CENTER);// 水平
        style.setWrapText(true);// 指定当单元格内容显示不下时自动换行

        cell.setCellStyle(style); // 样式，居中

        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 280);
        style.setFont(font);
        // 单元格合并
        // 四个参数分别是：起始行，起始列，结束行，结束列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, titleRow.length-1));
        sheet.autoSizeColumn(5200);

        row = sheet.createRow(1);    //创建第二行
        for(int i = 0;i < titleRow.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(titleRow[i]);
            cell.setCellStyle(style); // 样式，居中
            sheet.setColumnWidth(i, 20 * 256);
        }
        row.setHeight((short) 540);

        //循环写入行数据
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).size() != titleRow.length) {
                continue;
            }
            row = (Row) sheet.createRow(i+2);
            row.setHeight((short) 500);
            for (int j=0; j < titleRow.length; j++) {
                Object value = list.get(i).get(j);
                value = null == value ? "" : value;
                if (value instanceof Double) {
                    row.createCell(j).setCellValue(Double.valueOf(value.toString()));
                }else if (value instanceof Date) {
                    row.createCell(j).setCellValue((Date)value);
                }else {
                    row.createCell(j).setCellValue(value.toString());
                }
            }
        }
        //创建文件流
        OutputStream stream = new FileOutputStream(excelPath);
        //写入数据
        wb.write(stream);
        //关闭文件流
        stream.close();
        return file;
    }

}
