package org.springside.modules.binder;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jackson的简单封装.
 * 
 * @author calvin
 */
public class JsonBinder {

	private static Logger logger = LoggerFactory.getLogger(JsonBinder.class);

	private ObjectMapper mapper = new ObjectMapper();

	public JsonBinder() {
	}

	public JsonBinder(Inclusion inclusion) {
		setInclusion(inclusion);
	}

	public void setInclusion(Inclusion inclusion) {
		mapper.getSerializationConfig().setSerializationInclusion(inclusion);
	}

	/**
	 * 读取单个对象.
	 * 
	 * 如需读取集合如List使用如下语句:
	 * List<String> stringList = binder.getMapper().readValue(listString, new TypeReference<List<String>>() {});		
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz) {
		try {
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	public String toJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			logger.warn("write to json string error:" + object, e);
			return null;
		}
	}

	public ObjectMapper getMapper() {
		return mapper;
	}
}
