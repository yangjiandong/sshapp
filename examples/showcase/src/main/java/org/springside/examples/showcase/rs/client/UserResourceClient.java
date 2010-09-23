package org.springside.examples.showcase.rs.client;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springside.examples.showcase.rs.dto.UserDTO;
import org.springside.modules.utils.web.ServletUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * 使用Jersey Client的User REST客户端.
 * 在Mini-Service演示的基础上添加更多演示.
 * 
 * @author calvin
 */
public class UserResourceClient {

	private static Logger logger = LoggerFactory.getLogger(UserResourceClient.class);

	private WebResource client;

	@Required
	public void setBaseUrl(String baseUrl) {
		Client jerseyClient = Client.create(new DefaultClientConfig());
		client = jerseyClient.resource(baseUrl);
	}

	/**
	 * 访问有SpringSecurity安全控制的页面, 进行HttpBasic的登录.
	 */
	public List<UserDTO> getAllUser() {
		String authentication = ServletUtils.encodeHttpBasic("admin", "admin");
		return client.path("/users").header(ServletUtils.AUTHENTICATION_HEADER, authentication).accept(
				MediaType.APPLICATION_JSON).get(new GenericType<List<UserDTO>>() {
		});
	}

	public UserDTO getUser(Long id) {
		return client.path("/users/" + id).accept(MediaType.APPLICATION_JSON).get(UserDTO.class);
	}

	/**
	 * 返回html格式的特定内容.
	 */
	public String searchUserHtml(String name) {
		return client.path("/users/search").queryParam("name", name).queryParam("format", "html").get(String.class);
	}

	/**
	 * 无公共DTO类定义, 取得返回JSON字符串后自行转换DTO.
	 */
	public UserDTO searchUserJson(String name) throws IOException {
		String json = client.path("/users/search").queryParam("name", name).get(String.class);
		ObjectMapper mapper = new ObjectMapper();

		try {
			UserDTO user = mapper.readValue(json, UserDTO.class);
			return user;
		} catch (JsonParseException e) {
			logger.error("JSON response error:" + json, e);
		} catch (JsonMappingException e) {
			logger.error("JSON response error:" + json, e);
		}

		return null;
	}
}
