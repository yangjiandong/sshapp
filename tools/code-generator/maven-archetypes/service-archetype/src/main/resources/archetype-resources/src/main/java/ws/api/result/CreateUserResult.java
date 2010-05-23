#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ws.api.result;

import javax.xml.bind.annotation.XmlType;

import ${package}.ws.api.Constants;

/**
 * CreateUser方法的返回结果类型.
 * 
 * @author calvin
 */
@XmlType(name = "CreateUserResult", namespace = Constants.NS)
public class CreateUserResult extends WSResult {

	private Long userId;

	/**
	 * 新建用户的ID.
	 */
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
