package com.xlsx.demo.Service;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import java.io.IOException;

@Service
public class XlsxService {
  @Value("${spring.servlet.multipart.location}")
  private String attach_path;
  public void convertXlsxFile(MultipartHttpServletRequest request) throws IOException {
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
    for (int rowIdx = 1; rowIdx < rows; rowIdx++) {
      XSSFRow row = sheet.getRow(rowIdx);
      if(row !=null){ // row에 값 존재할 경우
        XSSFCell firstItem = row.getCell(1);
        XSSFCell secondItem = row.getCell(3);
        String firstItemVal=firstItem.getStringCellValue();
        int secondItemVal= (int)(secondItem.getNumericCellValue());
        System.out.println("Date : "+firstItemVal+" Cost : "+secondItemVal);
      }
    }
  }

}
