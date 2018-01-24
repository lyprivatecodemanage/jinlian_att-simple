package com.xiangshangban.att_simple.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 导出记录(出入记录、门禁异常、签到签退情况、操作日志)到Excel
 */
public class ExportRecordUtil {

    /**
     * @param exportData 要导出的数据
     * @param excelName 导出后的表格的名称
     * @param headers 表头
     * @param out 输出流
     * @param flag 标志位：
     *             0 ---->公司人员班次
     */
    public static void exportAnyRecordToExcel(List<?> exportData, String excelName, String[] headers,OutputStream out,int flag){
        if(exportData!=null && exportData.size()>0){
            // 第一步，创建一个webbook，对应一个Excel文件
            HSSFWorkbook workbook = new HSSFWorkbook();
            //生成一个表格
            HSSFSheet sheet = workbook.createSheet(excelName);
            //设置表格默认列宽度为15个字符
            sheet.setDefaultColumnWidth(15);
            //生成一个样式，用来设置标题样式
            HSSFCellStyle style = workbook.createCellStyle();
            //设置这些样式
            style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //生成一个字体
            HSSFFont font = workbook.createFont();
            font.setColor(HSSFColor.VIOLET.index);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            //把字体应用到当前的样式
            style.setFont(font);

            // 生成并设置另一个样式,用于设置内容样式
            HSSFCellStyle contentStyle = workbook.createCellStyle();
            contentStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
            contentStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            // 生成另一个字体
            HSSFFont font2 = workbook.createFont();
            font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            // 把字体应用到当前的样式
            contentStyle.setFont(font2);

            //TODO ==========产生表格标题行===========
            HSSFRow row = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                //创建一个个的表格
                HSSFCell cell = row.createCell(i);
                //设置标题表格的样式
                cell.setCellStyle(style);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                //设置表格内容
                cell.setCellValue(text);
            }
            //TODO  =========保存数据部分============
            switch (flag){
                case 0: //TODO 当前公司人员班次信息
                	System.out.println("-------《导出人员班次信息》------");
                    for(int a = 0;a<exportData.size();a++){
                        Map empClassesMap = (Map)exportData.get(a);
                        //从表格的第二行开始
                        row = sheet.createRow(a + 1);
                        //创建字符串数组，保存每一行表格中的内容
                        String[] doorCellContent = new String[11];
                        doorCellContent[0] = empClassesMap.get("empName").toString().trim();
                        doorCellContent[1] = empClassesMap.get("postName").toString().trim();
                        doorCellContent[2] = empClassesMap.get("deptName").toString().trim();
                        doorCellContent[10] = empClassesMap.get("thisWeekHours").toString().trim(); 
                        
                        Object object = empClassesMap.get("classesList");
                        JSONArray parseArray = JSONArray.parseArray(JSONArray.toJSONString(object));
                        
                        for(int y=0;y<parseArray.size();y++){
                        	JSONObject parseObject = JSONObject.parseObject(parseArray.get(y).toString());
                        	String classesName = parseObject.get("classesName").toString().trim();
                        	doorCellContent[y+3] = classesName;
                        }
                        for(int x=0;x<headers.length;x++){
                            HSSFCell cell = row.createCell(x);
                            //设置显示的内容
                            cell.setCellValue(doorCellContent[x]);
                        }
                    }
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                    default:
                        break;
            }
            try {
                //将数据写入输出流
            	System.out.println("workBook============》"+workbook.toString());
                workbook.write(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
