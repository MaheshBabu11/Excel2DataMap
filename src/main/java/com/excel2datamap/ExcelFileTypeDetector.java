package com.excel2datamap;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ExcelFileTypeDetector {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\MBABU3\\Excel2DataMap\\src\\main\\resources\\Book2.csv";
        try {
            String fileType = detectExcelFileType(filePath);
            System.out.println("Excel file type: " + fileType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String detectExcelFileType(String filePath) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            byte[] magicNumber = new byte[8]; // Read the first 8 bytes for magic number
            int bytesRead = inputStream.read(magicNumber);

            if (bytesRead >= 8) {
                if (isXLSX(magicNumber)) {
                    return "XLSX";
                } else if (isXLS(magicNumber)) {
                    return "XLS";
                }
                // Add more checks for other Excel formats here
            }
        }
        return "Unknown";
    }

    public static boolean isXLSX(byte[] magicNumber) {
        // Check for the magic number of XLSX files (Office Open XML format)
        return magicNumber[0] == 0x50 && magicNumber[1] == 0x4B && magicNumber[2] == 0x03 && magicNumber[3] == 0x04 &&
                magicNumber[4] == 0x14 && magicNumber[5] == 0x00 && magicNumber[6] == 0x06 && magicNumber[7] == 0x00;
    }

    public static boolean isXLS(byte[] magicNumber) {
        // Check for the magic number of XLS files (Binary Excel format)
        return magicNumber[0] == (byte) 0xD0 && magicNumber[1] == (byte) 0xCF && magicNumber[2] == (byte) 0x11 && magicNumber[3] == (byte) 0xE0 &&
                magicNumber[4] == (byte) 0xA1 && magicNumber[5] == (byte) 0xB1 && magicNumber[6] == (byte) 0x1A && magicNumber[7] == (byte) 0xE1;
    }
}
