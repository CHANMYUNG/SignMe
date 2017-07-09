package com.nooheat.support;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NooHeat on 10/07/2017.
 */
public class Document {

    public static void makeDocument(String title, List<DocumentResource> documentResources) {
        XSSFWorkbook document = new XSSFWorkbook();
        XSSFSheet sheet = document.createSheet("API");

        // 첫째 열
        XSSFRow rowHead = sheet.createRow(0);
        rowHead.createCell(0).setCellValue("Category");
        rowHead.createCell(1).setCellValue("Summary");
        rowHead.createCell(2).setCellValue("URI");
        rowHead.createCell(3).setCellValue("Http Method");
        rowHead.createCell(4).setCellValue("Params");
        rowHead.createCell(5).setCellValue("RequestBody");
        rowHead.createCell(6).setCellValue("Response");
        rowHead.createCell(7).setCellValue("SuccessCode");
        rowHead.createCell(8).setCellValue("FailureCode");
        rowHead.createCell(9).setCellValue("Etc");

        // 그 밑 열 :: 값이 들어감
        int count = 1;
        for (DocumentResource resource : documentResources) {
            XSSFRow row = sheet.createRow(count++);

            row.createCell(0).setCellValue(resource.category);
            row.createCell(1).setCellValue(resource.summary);
            row.createCell(2).setCellValue(resource.uri);
            row.createCell(3).setCellValue(resource.httpMethod);
            row.createCell(4).setCellValue(valueSplit(resource.params));
            row.createCell(5).setCellValue(valueSplit(resource.requestBody));
            row.createCell(6).setCellValue(valueSplit(resource.response));
            row.createCell(7).setCellValue(resource.successCode);
            row.createCell(8).setCellValue(resource.failureCode);
            row.createCell(9).setCellValue(resource.etc);
        }

        try {
            document.write(new FileOutputStream(title+".xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String valueSplit(String str) {

        StringBuilder builder = new StringBuilder();
        String[] splitedStrs = str.split(",");

        for (String splitedStr : splitedStrs) {
            builder.append(splitedStr.trim()).append("\n");
        }


        return builder.toString().substring(0, builder.toString().length() - 1);
    }
}
