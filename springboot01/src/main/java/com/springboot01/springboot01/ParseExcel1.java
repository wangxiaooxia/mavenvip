package com.springboot01.springboot01;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ParseExcel1 {

    public static void main(String[] args) {
        Workbook wb;
        Sheet sheet = null;
        Row row = null;
        List<Map<String,String>> list = null;
        String cellData = null;
        String filePath = "F:/parseexcel/parseExcel.xlsx";
        String columns[] = {"COOPR_SEQ","COOPR_CDE","COOPR_NAME","COOPR_KIND","COOPR_LVL","COOPR_SUB","DIRECT_OFFICER","DIRECT_BCH","COOPR_STR_DT","COOPR_END_DT","COOPR_STS","ID_TYPE","ID_NO","","","","",};
        wb = readExcel(filePath);
        if(wb != null){
            //用来存放表中数据
            list = new ArrayList<Map<String,String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行:wq
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            for (int i = 1; i<rownum; i++) {
                Map<String,String> map = new LinkedHashMap<String,String>();
                row = sheet.getRow(i);
                if(row !=null){
                    for (int j=0;j<colnum;j++){
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        map.put(columns[j], cellData);
                    }
                }else{
                    break;
                }

                //生成sql脚本
                if(!map.get("serviceLine").equals("空")){
                    if(map.get("serviceLine").contains("、")) {
                        String arrs[] = map.get("serviceLine").split("、");
                        for (int j = 0; j < arrs.length; j++) {
                            String b =testCode(arrs[j]);
                            System.out.println("INSERT INTO S_COOPR_BIZ_LINE ( seq,coopr_cde,service_line,maxloanamout)values (LINE_SEQ.Nextval,'"+ map.get("cooprCde")+"','"+b+"', '200000');");
                        }
                    } else  {
                        String a =  testCode(map.get("serviceLine"));
                        System.out.println("INSERT INTO S_COOPR_BIZ_LINE ( seq,coopr_cde,service_line,maxloanamout)values (LINE_SEQ.Nextval,'"+ map.get("cooprCde")+"','"+a+"','200000');");
                    }
                }
                //生成sql脚本
            }
        }
    }

    //读取excel
    public static Workbook readExcel (String filePath) {
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                return wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                return wb = new XSSFWorkbook(is);
            } else {
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
    public static Object getCellFormatValue(Cell cell){
        Object cellValue = null;
        if(cell!=null){
            //判断cell类型
            switch(cell.getCellType()){
                case Cell.CELL_TYPE_NUMERIC:{
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case Cell.CELL_TYPE_FORMULA:{
                    //判断cell是否为日期格式
                    if(DateUtil.isCellDateFormatted(cell)){
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    }else{
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:{
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        }else{
            cellValue = "";
        }
        return cellValue;
    }

    public static String testCode(String str){
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("全品牌新车","qppxc");
        hashMap.put("全品牌准新车","qppzxc");
        hashMap.put("二手车","rsc");
        hashMap.put("LCV","lcvqpp");
        hashMap.put("LCV准新车","lcvzxc");
        hashMap.put("本品牌新车（含small lot）","bppxc");
        return hashMap.get(str.trim());
    }


}
