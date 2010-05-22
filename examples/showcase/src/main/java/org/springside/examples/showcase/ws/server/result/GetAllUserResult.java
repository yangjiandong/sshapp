package org.springside.examples.showcase.ws.server.result;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.springside.examples.showcase.ws.server.WsConstants;
import org.springside.examples.showcase.ws.server.dto.UserDTO;

/**
 * GetAllUser方法的返回结果类型.
 * 
 * @author calvin
 */
@XmlType(name = "GetAllUserResult", namespace = WsConstants.NS)
public class GetAllUserResult extends WSResult implements Serializable {
	private static final long serialVersionUID = 4739558570080898049L;
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
