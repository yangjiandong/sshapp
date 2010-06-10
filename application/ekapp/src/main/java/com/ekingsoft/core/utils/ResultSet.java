package com.ekingsoft.core.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "unchecked", "serial" })
public class ResultSet implements Serializable {
    private List columnName = new ArrayList();
    private List dataSet = new ArrayList();

    public ResultSet() {
        setColumnName(new ArrayList());
        setDataSet(new ArrayList());
    }

    public void setColumnName(List tmp) {
        this.columnName = tmp;
    }

    public List getColumnName() {
        return this.columnName;
    }

    public void setDataSet(List tmp) {
        this.dataSet = tmp;
    }

    public List getDataSet() {
        return this.dataSet;
    }
}