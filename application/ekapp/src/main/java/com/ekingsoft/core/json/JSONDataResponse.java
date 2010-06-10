package com.ekingsoft.core.json;

public class JSONDataResponse {

    private String statusCode;
    private String message;
    private String data;

    public void setStatusCode(String statusCode) {
	this.statusCode = statusCode;
    }
    public String getStatusCode() {
	return statusCode;
    }
    public void setMessage(String message) {
	this.message = message;
    }
    public String getMessage() {
	return message;
    }
    public void setData(String data) {
	this.data = data;
    }
    public String getData() {
	return data;
    }

    public String toJSON() {
        try {
            return toJSONObject().toString();
        }
        catch (Exception e) {
            //LOGGER.debug("toJSON exception\n" + e);
            return null;
        }
    }

    public JSONObject toJSONObject() {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("statusCode", statusCode);
            jsonObject.put("message", message);
            jsonObject.put("data", data);

            return jsonObject;
        }
        catch (Exception e) {
            //LOGGER.error("toJSON exception\n" + e);
            return null;
        }
    }

}
