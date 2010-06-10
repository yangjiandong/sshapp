package com.ekingsoft.core.utils;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONResponseUtil {

	/**
	 * 响应生成Grid格式的JSON数据集 <br>
	 * 'totalCount':Grid的JSONReader的totalProperty属性必须设置为"totalCount" <br>
	 * 'rows':Grid的JSONReader的root属性必须设置为"rows"
	 */
	public static void buildJSONDataResponse(HttpServletResponse response,
			String jsonString, Long total) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"totalCount\":" + total + ",\"rows\":");
		sb.append(jsonString);
		sb.append("}");

		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(sb.toString());
	}

	/**
	 * 响应生成Grid格式的JSON数据集 <br>
	 * 'totalCount':Grid的JSONReader的totalProperty属性必须设置为"totalCount" <br>
	 * 'rows':Grid的JSONReader的root属性必须设置为"rows"
	 */
	public static void buildJSONDataResponse(HttpServletResponse response,
			List<? extends Object> data, Long total) throws Exception {
		JSONArray jsonArray = JSONArray.fromObject(data);
		StringBuffer sb = new StringBuffer();
		sb.append("{\"totalCount\":" + total + ",\"rows\":");
		sb.append(jsonArray.toString());
		sb.append("}");

		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(sb.toString());
	}

	/**
	 * 响应生成Grid格式的JSON数据集 <br>
	 * JSON对象的属性可以根据需要自定义设置，通过Map添加键值即可<br>
	 * 如: Map<String,Object> map = new HashMap<String,Object>();<br>
	 * map.put("totalCount",125);<br>
	 * map.put("success",true);<br>
	 * map.put("data",list);
	 */
	public static void buildCustomJSONDataResponse(
			HttpServletResponse response, Map<String, ? extends Object> data)
			throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(data);
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
	}

	/**
	 * 响应非Grid形式的JSON数据集的请求
	 */
	public static void buildJSONResponse(HttpServletResponse response,
			List<? extends Object> data) throws Exception {
		JSONArray jsonArray = JSONArray.fromObject(data);
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(jsonArray.toString());
	}

	/**
	 * 响应非Grid形式的JSON数据集的请求
	 */
	public static void buildJSONObjectResponse(HttpServletResponse response,
			JSONObject data) throws Exception {
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(data.toString());
	}
}
