package org.springside.examples.showcase.ws.server.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springside.examples.showcase.ws.server.WsConstants;

/**
 * Web Service传输User信息的DTO.
 * 
 * @author calvin
 */
@XmlType(name = "User", namespace = WsConstants.NS)
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 5417916547256861781L;

	private Long id;
	private String loginName;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long value) {
		id = value;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String value) {
		loginName = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
