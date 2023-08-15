# Excel2DataMap

---

This is a java library that is used to convert Excel files from  various formats like .xls,xls,csv to 
datamaps so that they can be directly used to insert data into database tables.

### Usage :
```java
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
            String BASE_PATH = System.getProperty("user.dir");
            System.out.println(excelFileTypeDetector.detectExcelFileType(BASE_PATH +"\\src\\main\\resources\\Book1.xlsx"));
            dataMapBuilder.readFile(BASE_PATH +"\\src\\main\\resources\\Book1.xlsx");
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


```

