package com.excel2datamap;

/**
 * Factory for creating instances of {@link DataMapBuilder} ,@{@link ExcelFileTypeDetector}.
 */
public final class ExcelConversionFactory {

    private ExcelConversionFactory() {
    }

    /**
     * Create a new instance of {@link DataMapBuilder}
     *
     * @return {@link DataMapBuilder}
     */
    public static DataMapBuilder getDataMapBuilder() {
        ExcelFileTypeDetector excelFileTypeDetector = new ExcelFileTypeDetector();
        return new DataMapBuilder(excelFileTypeDetector);
    }

    /**
     * Create a new instance of {@link ExcelFileTypeDetector}
     *
     * @return {@link ExcelFileTypeDetector}
     */
    public static ExcelFileTypeDetector getExcelFileTypeDetector() {
        return new ExcelFileTypeDetector();
    }

}
