package com.excel2datamap.example;

import com.excel2datamap.DataMapBuilder;
import com.excel2datamap.ExcelDataMap;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExampleUsage {
    public static void main(String[] args) {
        DataMapBuilder dataMapBuilder = new DataMapBuilder();
        try {
            dataMapBuilder.readFile("C:\\Users\\MBABU3\\Excel2DataMap\\src\\main\\resources\\Book2.csv");
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
