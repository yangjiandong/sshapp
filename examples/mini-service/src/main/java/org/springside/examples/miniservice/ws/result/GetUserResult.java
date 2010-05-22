package org.springside.examples.miniservice.ws.result;

import javax.xml.bind.annotation.XmlType;

import org.springside.examples.miniservice.ws.WsConstants;
import org.springside.examples.miniservice.ws.dto.UserDTO;

/**
 * GetUser方法的返回结果.
 * 
 * @author calvin
 */
@XmlType(name = "GetUserResult", namespace = WsConstants.NS)
public class GetUserResult extends WSResult {

	private UserDTO user;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
}
