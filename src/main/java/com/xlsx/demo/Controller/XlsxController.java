package com.xlsx.demo.Controller;

import com.xlsx.demo.Service.XlsxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

@Controller
public class XlsxController {
  @Autowired
  XlsxService xlsxService;

  @PostMapping("/upload")
  public String convertXlsxFiles(MultipartHttpServletRequest request) throws IOException {
    xlsxService.convertXlsxFile(request);
    return "uploaded";
  }
  @GetMapping("/hello")
  public String test(){
    return "Helloworld";
  }
}
//  Optional<MetaDictionaryXlsxfiles> byId = metaDictionaryXlsxfilesRepository.findById(Long.parseLong(dictionaryId));
//        if (byId.isPresent()) {
//            MetaDictionaryXlsxfiles entity = byId.get();
//            String filePath = entity.getDir();
//            String fileName = entity.getFileName();
//            fileName = fileName+".xlsx";
//            String encodedFileName = fileNameEncoding(fileName, request, response);
//
//            File file = new File(filePath);
//            long fileLength = file.length();
////            response.setHeader("Content-Disposition", "attachment; filename=\"" + entity.getFileName()+".xlsx" + "\";");
//            response.setHeader("Content-Disposition", "attachment; filename=" + encodedFileName + ";");
//            response.setHeader("Content-Transfer-Encoding", "binary");
//            response.setHeader("Content-Type", "text/xlsx");
//            response.setHeader("Content-Length", "" + fileLength);
//            response.setHeader("Pragma", "no-cache;");
//            response.setHeader("Expires", "-1;");
//
////            //String codedBy = "211115 SY.Kim"
////            System.out.println("브라우저 종류에 따른 fileName 출력 결과");
////            System.out.println("attachment; filename=\"" + entity.getFileName()+".xlsx" + "\";");
////            System.out.println("FilePath : " + filePath);
////            String MSIEencodedFilename = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
////            String TridentencodedFilename = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
////            String FirefoxencodedFilename = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
////            FirefoxencodedFilename = URLDecoder.decode(FirefoxencodedFilename);
////            String OperaencodedFilename = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
////            StringBuffer sb = new StringBuffer();
////            for (int i = 0; i < fileName.length(); i++) {
////                char c = fileName.charAt(i);
////                if (c > '~') {
////                    sb.append(URLEncoder.encode("" + c, "UTF-8"));
////                } else {
////                    sb.append(c);
////                }
////            }
////            String ChromeencodedFilename = sb.toString();
////            String SafariencodedFilename = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1")+ "\"";
////            SafariencodedFilename = URLDecoder.decode(SafariencodedFilename);
////            System.out.println("MSIE :" + MSIEencodedFilename);
////            System.out.println("Trident :" + TridentencodedFilename);
////            System.out.println("FireFox :" + FirefoxencodedFilename);
////            System.out.println("Opera : " + OperaencodedFilename);
////            System.out.println("Chrome :" + ChromeencodedFilename);
////            System.out.println("Safari :" + SafariencodedFilename);
//
//            try (
//            FileInputStream fis = new FileInputStream(filePath);
//            OutputStream out = response.getOutputStream();
//            ) {
//            int readCount = 0;
//            byte[] buffer = new byte[1024];
//            while ((readCount = fis.read(buffer)) != -1) {
//            out.write(buffer, 0, readCount);
//            }
//            } catch (Exception ex) {
//            throw new RuntimeException("Err] Dictionary - xlsx File Download Error");
//            }
//            }
//            }