package com.excel2datamap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DataMapBuilder {
    private final ExcelDataMap excelDataMap;

    public DataMapBuilder() {
        excelDataMap = new ExcelDataMap();
    }

    public void readFile(String fileName) throws IOException {
        if (fileName.matches(".*\\.(xls|xlsx)$")) {
            if (isExcelFile(fileName)) {
                readExcelFile(fileName);
            }
        } else if (fileName.endsWith(".csv")) {
            readCsvFile(fileName);
        } else {

            return;
        }
    }

    public void readExcelFile(String filePath) throws IOException {
        ExcelReader excelReader = new ExcelReader();
        List<Sheet> sheets = excelReader.readExcel(filePath);
        for (Sheet sheet : sheets) {
            List<String[]> excelData = excelReader.readSheet(sheet);
            if (excelData.size() > 0) {
                String[] headerRow = excelData.get(0);
                for (int i = 0; i < headerRow.length; i++) {
                    String columnName = headerRow[i];
                    List<String> columnValues = getColumnValues(excelData, i);
                    excelDataMap.addData(columnName, columnValues);
                }
            }
        }
    }

    public void readCsvFile(String filePath) throws IOException {
        CSVFormat csvFormat = CSVFormat.DEFAULT.withDelimiter('\t');
        try (CSVParser csvParser = new CSVParser(new FileReader(filePath), csvFormat)) {
            List<String> headers = new ArrayList<>();
            // Read the header from the first record
            CSVRecord firstRecord = csvParser.iterator().next();
            for (String header : firstRecord) {
                headers.add(header);
            }
            // Process the remaining records
            for (CSVRecord record : csvParser) {
                for (int i = 0; i < headers.size(); i++) {
                    String header = headers.get(i);
                    if (!excelDataMap.getDataMap().containsKey(header)) {
                        excelDataMap.getDataMap().put(header, new ArrayList<>());
                    }
                    excelDataMap.getDataMap().get(header).add(record.get(i));
                }
            }
        }

    }

    public ExcelDataMap getDataMap() {
        return excelDataMap;
    }

    private List<String> getColumnValues(List<String[]> excelData, int columnIndex) {
        List<String> columnValues = new ArrayList<>();
        for (int i = 1; i < excelData.size(); i++) {
            String[] rowData = excelData.get(i);
            if (columnIndex < rowData.length) {
                columnValues.add(rowData[columnIndex]);
            }
        }
        return columnValues;
    }

    public boolean isExcelFile(String filePath) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            byte[] magicNumber = new byte[8];
            int bytesRead = inputStream.read(magicNumber);

            if (bytesRead >= 8) {
                return (magicNumber[0] == 0x50 && magicNumber[1] == 0x4B && magicNumber[2] == 0x03 && magicNumber[3] == 0x04 &&
                        magicNumber[4] == 0x14 && magicNumber[5] == 0x00 && magicNumber[6] == 0x06 && magicNumber[7] == 0x00) ||
                        (magicNumber[0] == (byte) 0xD0 && magicNumber[1] == (byte) 0xCF && magicNumber[2] == (byte) 0x11 && magicNumber[3] == (byte) 0xE0 &&
                                magicNumber[4] == (byte) 0xA1 && magicNumber[5] == (byte) 0xB1 && magicNumber[6] == (byte) 0x1A && magicNumber[7] == (byte) 0xE1);
            }
        }
        return false;
    }
}

