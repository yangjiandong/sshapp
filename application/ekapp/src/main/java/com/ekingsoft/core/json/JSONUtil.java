package com.ekingsoft.core.json;

import java.io.PrintWriter;

//Thanks: http://extjs.com/forum/showthread.php?t=7481
public class JSONUtil {

    public static void buildJSONDataResponse(PrintWriter printWriter, JSONDataReader jsonReader) throws Exception {
        JSONDataResponse jsonDataResponse = new JSONDataResponse();
        jsonDataResponse.setStatusCode(StatusCode.SC_OK);
        jsonDataResponse.setData(jsonReader.toString());
        printWriter.write(jsonDataResponse.toJSON());
    }

    public static void buildJSONResponse(PrintWriter printWriter, JSONDataReader data) throws Exception {
        printWriter.write(data.toString());
    }
    
}
