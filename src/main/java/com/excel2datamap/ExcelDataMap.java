package com.excel2datamap;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelDataMap {
    private Map<String, List<String>> dataMap = new LinkedHashMap<>();

    public void addData(String columnName, List<String> values) {
        dataMap.put(columnName, values);
    }

    public Map<String, List<String>> getDataMap() {
        return dataMap;
    }
}
