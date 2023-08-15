package com.excel2datamap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains method to read the excel file and convert it into datamap @{@link ExcelDataMap}
 */
public class DataMapBuilder {

    private final ExcelDataMap excelDataMap;
    private final ExcelFileTypeDetector excelFileTypeDetector;

    public DataMapBuilder(ExcelFileTypeDetector excelFileTypeDetector) {
        this.excelFileTypeDetector = excelFileTypeDetector;
        excelDataMap = new ExcelDataMap();
    }

    /**
     * This method reads the Excel files and determine if they are of the supported formats
     * if so it converts them into datamaps, The first row is considered as the header row which is basically
     * the key of the data map followed by all the values in that column as the values of the key.
     * The data map is of type @{@link ExcelDataMap}
     *
     * @param fileName The path where the file is stored.
     * @throws @{@link InvalidFileFormatException} File format exception
     */
    public void readFile(String fileName) throws Exception {
        if (fileName.matches(".*\\.(xls|xlsx)$")) {
            if (excelFileTypeDetector.isExcelFile(fileName)) {
                readExcelFile(fileName);
            }
        } else if (fileName.endsWith(".csv")) {
            readCsvFile(fileName);
        } else {
            throw new InvalidFileFormatException();
        }
    }

    /**
     * This method takes in the file path and reads the Excel file using apache poi library
     * It supports multiple sheet support and formats such as XLS and XLSX.
     *
     * @param filePath The path where the file is stored.
     * @throws IOException Exception in opening the file
     */
    void readExcelFile(String filePath) throws IOException {
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

    /**
     * This method takes in the file path and reads the Excel file using apache common csv library
     * It supports multiple sheet support and CSV file format.
     *
     * @param filePath The path where the file is stored.
     * @throws IOException Exception in opening the file
     */
    void readCsvFile(String filePath) throws IOException {
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

    /**
     * This method returns the generated datamaps
     *
     * @return @{@link ExcelDataMap}
     */
    public ExcelDataMap getDataMap() {
        return excelDataMap;
    }

    /**
     * This method returns the values of a column from the sheet based on the column index
     *
     * @param excelData   The data per excel sheet
     * @param columnIndex The index of the column whose data are to be extracted from the Excel sheet.
     * @return {@code List<String>}
     */
    List<String> getColumnValues(List<String[]> excelData, int columnIndex) {
        List<String> columnValues = new ArrayList<>();
        for (int i = 1; i < excelData.size(); i++) {
            String[] rowData = excelData.get(i);
            if (columnIndex < rowData.length) {
                columnValues.add(rowData[columnIndex]);
            }
        }
        return columnValues;
    }

}
