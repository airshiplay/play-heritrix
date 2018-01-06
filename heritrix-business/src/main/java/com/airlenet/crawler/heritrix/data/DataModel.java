package com.airlenet.crawler.heritrix.data;

/**
 * @author airlenet
 * @version 2018-01-05
 */
public class DataModel {
    private String colName;
    private String colValue;
    private boolean isKey;

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColValue() {
        return colValue;
    }

    public void setColValue(String colValue) {
        this.colValue = colValue;
    }

    public boolean isKey() {
        return isKey;
    }

    public void setKey(boolean key) {
        isKey = key;
    }
}
