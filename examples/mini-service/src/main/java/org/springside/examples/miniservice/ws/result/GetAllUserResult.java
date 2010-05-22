package org.springside.examples.miniservice.ws.result;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.springside.examples.miniservice.ws.WsConstants;
import org.springside.examples.miniservice.ws.dto.UserDTO;

/**
 * GetAllUser方法的返回结果.
 * 
 * @author calvin
 */
@XmlType(name = "GetAllUserResult", namespace = WsConstants.NS)
public class GetAllUserResult extends WSResult {

	private List<UserDTO> userList;

	@XmlElementWrapper(name = "userList")
	@XmlElement(name = "user")
	public List<UserDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}
}
