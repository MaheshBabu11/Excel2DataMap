package com.excel2datamap.example;

import com.excel2datamap.DataMapBuilder;
import com.excel2datamap.ExcelConversionFactory;
import com.excel2datamap.ExcelDataMap;
import com.excel2datamap.ExcelFileTypeDetector;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExampleUsage {
    public static void main(String[] args) throws Exception {
        DataMapBuilder dataMapBuilder = ExcelConversionFactory.getDataMapBuilder();
        ExcelFileTypeDetector excelFileTypeDetector = ExcelConversionFactory.getExcelFileTypeDetector();
        try {
            System.out.println(excelFileTypeDetector.detectExcelFileType("C:\\Users\\MBABU3\\Excel2DataMap\\src\\main\\resources\\Book3.xls"));
            dataMapBuilder.readFile("C:\\Users\\MBABU3\\Excel2DataMap\\src\\main\\resources\\Book6.xlsb");
            ExcelDataMap excelDataMap = dataMapBuilder.getDataMap();

            for (Map.Entry<String, List<String>> entry : excelDataMap.getDataMap().entrySet()) {
                System.out.println("Column: " + entry.getKey());
                System.out.println("Values: " + entry.getValue());
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
