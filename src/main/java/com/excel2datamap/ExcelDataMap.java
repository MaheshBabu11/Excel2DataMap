package com.excel2datamap;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a custom Data Structure used to store the data maps that gets generated from the Excel files
 */
public class ExcelDataMap {
    private final Map<String, List<String>> dataMap = new LinkedHashMap<>();

    public void addData(String columnName, List<String> values) {
        dataMap.put(columnName, values);
    }

    public Map<String, List<String>> getDataMap() {
        return dataMap;
    }
}
