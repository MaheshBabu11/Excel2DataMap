package com.excel2datamap;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class ExcelFileTypeDetectorTest {

    String BASE_PATH = System.getProperty("user.dir");

    @Test
    void testDetectExcelFileTypeXLSX() throws IOException {
        String filePath = BASE_PATH + "\\src\\main\\resources\\" + "Book1.xlsx"; // Provide a valid path to an XLSX file
        String fileType = ExcelFileTypeDetector.detectExcelFileType(filePath);
        assertEquals("XLSX", fileType, "File type should be XLSX");
    }

    @Test
    void testDetectExcelFileTypeXLS() throws IOException {
        String filePath = BASE_PATH + "\\src\\main\\resources\\" + "Book3.xls"; // Provide a valid path to an XLS file
        String fileType = ExcelFileTypeDetector.detectExcelFileType(filePath);
        assertEquals("XLS", fileType, "File type should be XLS");
    }

    @Test
    void testDetectExcelFileTypeUnknown() throws IOException {
        String filePath = BASE_PATH + "\\src\\main\\resources\\" + "Book2.csv"; // Provide a path to a file with an unknown format
        String fileType = ExcelFileTypeDetector.detectExcelFileType(filePath);
        assertEquals("Unknown", fileType, "File type should be Unknown");
    }

    @Test
    void testIsExcelFileXLSX() throws IOException {
        String filePath = BASE_PATH + "\\src\\main\\resources\\" + "Book3.xls"; // Provide a valid path to an XLSX file
        ExcelFileTypeDetector detector = new ExcelFileTypeDetector();
        assertTrue(detector.isExcelFile(filePath), "File should be detected as an Excel file");
    }

    @Test
    void testIsExcelFileXLS() throws IOException {
        String filePath = BASE_PATH + "\\src\\main\\resources\\" + "Book3.xls"; // Provide a valid path to an XLS file
        ExcelFileTypeDetector detector = new ExcelFileTypeDetector();
        assertTrue(detector.isExcelFile(filePath), "File should be detected as an Excel file");
    }

    @Test
    void testIsExcelFileNonExcel() throws IOException {
        String filePath = BASE_PATH + "\\src\\main\\resources\\" + "book.txt"; // Provide a path to a non-Excel file
        ExcelFileTypeDetector detector = new ExcelFileTypeDetector();
        assertFalse(detector.isExcelFile(filePath), "File should not be detected as an Excel file");
    }
}
