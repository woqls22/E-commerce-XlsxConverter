package com.xlsx.demo.Service;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@Service
public class XlsxService {
  @Value("${spring.servlet.multipart.location}")
  private String attach_path;
  private String output_path="output/";
  public void convertXlsxFile(MultipartHttpServletRequest request) throws IOException, InvalidFormatException {
    File fileDir = new File(attach_path);
    if(!fileDir.exists()){
      fileDir.mkdirs();
    }
    MultipartFile templateMf = request.getFile("template");
    MultipartFile sourceMf = request.getFile("source");

    String templateFilename = templateMf.getOriginalFilename();
    String sourceFilename1 = sourceMf.getOriginalFilename();
    templateFilename = attach_path + templateFilename.replaceAll(" ","");
    sourceFilename1=attach_path + sourceFilename1.replaceAll(" ","");
    templateMf.transferTo(new File( templateFilename));
    sourceMf.transferTo(new File( sourceFilename1));

    FileInputStream source = new FileInputStream(sourceFilename1);
    XSSFWorkbook sourceWorkbook = new XSSFWorkbook(source);

    XSSFSheet sheet = sourceWorkbook.getSheetAt(0);
    int rows = sheet.getPhysicalNumberOfRows(); // 해당 시트의 행 개수
    //source file의 row마다 순회
    for (int rowIdx = 0; rowIdx <= rows; rowIdx++) {
      XSSFRow row = sheet.getRow(rowIdx);
      FileInputStream template = new FileInputStream(templateFilename);
      XSSFWorkbook templateWorkbook = new XSSFWorkbook(template);
      if(row !=null){ // row에 값 존재할 경우
        XSSFCell firstItem = row.getCell(1);
        XSSFCell secondItem = row.getCell(3);
        String firstItemVal=firstItem.getStringCellValue();
        int secondItemVal= (int)(secondItem.getNumericCellValue());
        System.out.println("Date : "+firstItemVal+", Cost : "+secondItemVal);

        XSSFSheet sheet1 = templateWorkbook.getSheetAt(1);
        sheet1.getRow(3).getCell(1).setCellValue(convertDateStringToDate(firstItemVal));
        sheet1.getRow(3).getCell(2).setCellFormula("B4");
        XSSFRow templateRow= sheet1.getRow(1);
        int rowCount=0;
        int sheet1Rows = sheet1.getPhysicalNumberOfRows();
        for(rowCount = 6; rowCount<=sheet1Rows;rowCount++){
          templateRow = sheet1.getRow(rowCount);
          if(templateRow.getCell(4).getCellType()==XSSFCell.CELL_TYPE_BLANK){
            break;
          }else if (templateRow.getCell(4).getCellType()==XSSFCell.CELL_TYPE_FORMULA){
            templateRow.getCell(4).setCellFormula(templateRow.getCell(4).getCellFormula());
          }
        }
        XSSFCell cell1 = templateRow.createCell(4);
        XSSFCell cell2 = templateRow.createCell(9);
        cell1.setCellType(Cell.CELL_TYPE_STRING);
        cell2.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell1.setCellFormula("TEXT($B$4,\"MM월 DD일\")");
        cell2.setCellValue(secondItemVal);

        File outputDir = new File(attach_path+output_path);
        if(!outputDir.exists()){
          outputDir.mkdirs();
        }
        FileOutputStream fileOut = new FileOutputStream(outputDir+"/"+firstItemVal+".xlsx");
        templateWorkbook.write(fileOut);
        fileOut.close();
      }
    }
  }
  public String convertDateStr(String item){
    String[] split = item.split("\\.");
    String year = split[0];
    String month = split[1];
    String day = split[2];
    StringBuilder stringBuilder = new StringBuilder();
    StringBuilder append = stringBuilder.append(month).append("월").append(" ").append(day).append("일");
    return append.toString();
  }
  public String convertDateWithSlash(String item){
    String[] split = item.split("\\.");
    String year = split[0];
    String month = split[1];
    String day = split[2];
    StringBuilder stringBuilder = new StringBuilder();
    StringBuilder append = stringBuilder.append(month).append("/").append(day).append("/").append(year);
    return append.toString();
  }
  public Calendar convertDateStringToDate(String item){
    String[] split = item.split("\\.");
    String year = split[0];
    String month = split[1];
    String day = split[2];
    Calendar date = Calendar.getInstance();
    date.set(Integer.parseInt(year),Integer.parseInt(month)-1,Integer.parseInt(day));
    return date;
  }
}
