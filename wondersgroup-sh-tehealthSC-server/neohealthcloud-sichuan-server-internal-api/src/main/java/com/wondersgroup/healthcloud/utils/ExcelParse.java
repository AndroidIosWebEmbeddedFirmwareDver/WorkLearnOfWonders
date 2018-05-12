package com.wondersgroup.healthcloud.utils;


import com.wondersgroup.healthcloud.exceptions.CommonException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by ys on 2017/04/13.
 */
public class ExcelParse {

    /**
     * @param file              MultipartFile 解析处理的数据文件
     * @param fieldNames        文件定义的字段列表,必须和实际文件定义的字段顺序一致
     * @param checkedFieldNames 非空字段的检查,顺序无关
     * @return List<Map   <   String   ,       Object>> map:{fieldName:fieldValue}包含一条{"rowNum":n};//当前为第几行数据
     */
    public static List<Map<String, Object>> parseExcelFile(MultipartFile file, String[] fieldNames, String[] checkedFieldNames) {
        InputStream is;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            throw new CommonException(1101, "文件无效");
        }
        Workbook workbook = getWorkbookFromInputStream(is, file.getOriginalFilename());
        return parseWorkbook(workbook, fieldNames, checkedFieldNames);
    }

    /**
     * @param file              MultipartFile 解析处理的数据文件
     * @param fieldNames        文件定义的字段列表,必须和实际文件定义的字段顺序一致
     * @param checkedFieldNames 非空字段的检查,顺序无关
     * @return List<Map   <   String   ,       Object>> map:{fieldName:fieldValue}包含一条{"rowNum":n};//当前为第几行数据
     */
    public static List<Map<String, Object>> parseExcelFile(File file, String[] fieldNames, String[] checkedFieldNames) {
        InputStream is;
        try {
            is = new FileInputStream(file);
        } catch (IOException e) {
            throw new CommonException(1101, "文件无效");
        }
        Workbook workbook = getWorkbookFromInputStream(is, file.getName());
        return parseWorkbook(workbook, fieldNames, checkedFieldNames);
    }


    private static Workbook getWorkbookFromInputStream(InputStream is, String fileName) {
        Workbook workbook;
        try {
            if (StringUtils.endsWith(fileName, ".xls")) {
                workbook = new HSSFWorkbook(is);
            } else if (StringUtils.endsWith(fileName, ".xlsx")) {
                workbook = new XSSFWorkbook(is);
            } else {
                throw new CommonException(1100, "不支持的文件格式");
            }
        } catch (IOException e) {
            throw new CommonException(1101, "格式处理错误");
        }
        return workbook;
    }

    /**
     * @param workbook          Workbook 解析处理数据
     * @param fieldNames        文件定义的字段列表,必须和实际文件定义的字段顺序一致
     * @param checkedFieldNames 非空字段的检查,顺序无关
     * @return List<Map   <   String   ,       Object>> map:{fieldName:fieldValue}包含一条{"rowNum":n};//当前为第几行数据
     */
    private static List<Map<String, Object>> parseWorkbook(Workbook workbook, String[] fieldNames, String[] checkedFieldNames) {
        if (null == workbook) {
            return null;
        }
        Sheet sheet = workbook.getSheetAt(0);
        List<Map<String, Object>> datas = new ArrayList<>();
        int lastRowNum = sheet.getLastRowNum();
        //只有一个头 不处理
        if (lastRowNum == 0) {
            return datas;
        }
        Iterator<Row> rowIterator = sheet.iterator();
        Row row = rowIterator.next();//去掉头
        //总列数
        int tdLength = row.getLastCellNum();
        //强制要求数据列和所需要的一样
        if (tdLength != fieldNames.length) {
            return datas;
        }
        boolean isCheckNoEmpty = null != checkedFieldNames && checkedFieldNames.length > 0;
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            Map<String, Object> dataRow = new LinkedHashMap<>();
            int rowNum = row.getRowNum() + 1;
            dataRow.put("rowNum", rowNum);
            int emptyValueNum = 0;
            for (int i = 0; i < tdLength; i++) {
                Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                if (null == cell) {
                    emptyValueNum++;
                    dataRow.put(fieldNames[i], "");
                    continue;
                }
                Object value;
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        value = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        value = "";
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            value = String.valueOf(cell.getDateCellValue());
                        } else {
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            String tmpV = cell.getStringCellValue();
                            if (tmpV.indexOf(".") > -1) {
                                value = String.valueOf(new Double(tmpV)).trim();
                            } else {
                                value = tmpV.trim();
                            }
                        }
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        value = cell.getDateCellValue();
                        break;
                    default:
                        value = "";
                }
                if (null == value || StringUtils.isEmpty(value.toString())) {
                    emptyValueNum++;
                }
                dataRow.put(fieldNames[i], value);
            }
            //当前行的数据全部为空 不要
            if (emptyValueNum == tdLength) {
                continue;
            }
            datas.add(dataRow);
            //非空字段验证
            if (isCheckNoEmpty) {
                for (String checkedFieldName : checkedFieldNames) {
                    if (dataRow.containsKey(checkedFieldName) && StringUtils.isEmpty(dataRow.get(checkedFieldName).toString())) {
                        throw new CommonException(1090, String.format("第%s行 字段:%s不能为空! ", rowNum, checkedFieldName));
                    }
                }
            }
        }
        return datas;
    }

}
