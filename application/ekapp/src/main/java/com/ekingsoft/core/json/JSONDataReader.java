package com.ekingsoft.core.json;

//Custom JSONReader.java to wrap JSON for Ext JSON reader format
//
//Thanks: http://extjs.com/forum/showthread.php?t=7481
public class JSONDataReader extends JSONArray {

    private int recordCount = 0;

    public JSONDataReader() {
        super();
    }

    public JSONDataReader(Integer recordCount) {
        this();
        this.setRecordCount(recordCount);
    }

    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append("{\"totalCount\":" + recordCount + ",\"rows\":");
        sb.append(super.toString());
        sb.append("}");
        return sb.toString();
    }

    public void setRecordCount(int recordCount) {
	this.recordCount = recordCount;
    }

    public int getRecordCount() {
	return recordCount;
    }

}