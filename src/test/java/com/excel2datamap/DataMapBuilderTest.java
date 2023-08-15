package com.excel2datamap;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DataMapBuilderTest {

    String BASE_PATH = System.getProperty("user.dir");
    ;
    @Mock
    private ExcelFileTypeDetector mockExcelFileTypeDetector;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReadExcelFile() throws IOException {
        String filePath = BASE_PATH + "\\src\\main\\resources\\" + "test.xlsx";
        DataMapBuilder dataMapBuilder = new DataMapBuilder(new ExcelFileTypeDetector());
        ExcelReader excelReaderMock = mock(ExcelReader.class);
        Sheet sheetMock = mock(Sheet.class);
        when(excelReaderMock.readExcel(filePath)).thenReturn(Arrays.asList(sheetMock));
        when(excelReaderMock.readSheet(sheetMock)).thenReturn(Arrays.asList(new String[]{"Column1", "Column2"}, new String[]{"Value1", "Value2"}));
        dataMapBuilder.readExcelFile(filePath);

        ExcelDataMap excelDataMap = dataMapBuilder.getDataMap();
        assertEquals(Arrays.asList("Value1"), excelDataMap.getDataMap().get("Column1"));
        assertEquals(Arrays.asList("Value2"), excelDataMap.getDataMap().get("Column2"));
    }


    @Test
    void testReadCsvFile() throws IOException {
        DataMapBuilder dataMapBuilder = new DataMapBuilder(mockExcelFileTypeDetector);
        String filePath = BASE_PATH + "\\src\\main\\resources\\" + "test.csv"; // Provide a valid path to a CSV file
        CSVParser mockCsvParser = mock(CSVParser.class);
        when(mockExcelFileTypeDetector.isExcelFile(filePath)).thenReturn(false);
        when(mockCsvParser.iterator()).thenReturn(Arrays.asList(mock(CSVRecord.class)).iterator());
//        when(mockCsvParser.iterator().next()).thenReturn(new CSVRecord(Arrays.asList("Column1", "Column2"), 0));
//        when(mockCsvParser.getRecords()).thenReturn(Arrays.asList(new CSVRecord(Arrays.asList("Value1", "Value2"), 1)));
        dataMapBuilder.readCsvFile(filePath);
        // Assert that data is added to the ExcelDataMap
        ExcelDataMap excelDataMap = dataMapBuilder.getDataMap();
        assertEquals(Arrays.asList("Value1"), excelDataMap.getDataMap().get("Column1"));
        assertEquals(Arrays.asList("Value2"), excelDataMap.getDataMap().get("Column2"));
    }

    @Test
    void testInvalidFileFormat() throws IOException {
        DataMapBuilder dataMapBuilder = new DataMapBuilder(mockExcelFileTypeDetector);
        String filePath = BASE_PATH + "\\src\\main\\resources\\" + "book.txt"; // Provide a path to a file with an unknown format

        when(mockExcelFileTypeDetector.isExcelFile(filePath)).thenReturn(false);

        assertThrows(InvalidFileFormatException.class, () -> dataMapBuilder.readFile(filePath));
    }

    @Test
    void testGetDataMap() {
        DataMapBuilder dataMapBuilder = new DataMapBuilder(mockExcelFileTypeDetector);
        assertNotNull(dataMapBuilder.getDataMap(), "DataMap should not be null");
    }

    @Test
    void testGetColumnValues() {
        DataMapBuilder dataMapBuilder = new DataMapBuilder(mockExcelFileTypeDetector);
        List<String[]> excelData = Arrays.asList(new String[]{"Column1", "Column2"}, new String[]{"Value1", "Value2"});

        List<String> columnValues = dataMapBuilder.getColumnValues(excelData, 1);
        assertEquals(Arrays.asList("Value2"), columnValues, "Column values should match");
    }
}
