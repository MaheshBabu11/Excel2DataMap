package com.excel2datamap;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    public List<Sheet> readExcel(String filePath) throws IOException {
        List<Sheet> sheets = new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            sheets.add(workbook.getSheetAt(i));
        }
        workbook.close();
        fileInputStream.close();
        return sheets;
    }

    public List<String[]> readSheet(Sheet sheet) {
        List<String[]> data = new ArrayList<>();
        for (Row row : sheet) {
            String[] rowData = new String[row.getLastCellNum()];
            for (int i = 0; i < row.getLastCellNum(); i++) {
                Cell cell = row.getCell(i);
                if (cell != null) {
                    rowData[i] = cell.toString();
                }
            }
            data.add(rowData);
        }
        return data;
    }
}
