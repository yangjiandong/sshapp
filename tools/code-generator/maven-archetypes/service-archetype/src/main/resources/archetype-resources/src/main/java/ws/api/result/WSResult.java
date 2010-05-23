#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ws.api.result;

import javax.xml.bind.annotation.XmlType;

import ${package}.ws.api.Constants;

/**
 * WebService返回结果基类,定义所有返回码.
 * 
 * @author calvin
 */
@XmlType(name = "WSResult", namespace = Constants.NS)
public class WSResult {

	// 返回代码定义 //
	// 按项目的规则进行定义，比如1xx代表客户端参数错误，2xx代表业务错误等.
	public static final String SUCCESS = "0";
	public static final String PARAMETER_ERROR = "101";
	public static final String AUTH_ERROR = "201";
	public static final String SYSTEM_ERROR = "300";

	// WSResult基本属性 //
	private String code = SUCCESS;
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 设置返回结果.
	 */
	public void setResult(String resultCode, String resultMessage) {
		code = resultCode;
		message = resultMessage;
	}

	/**
	 * 设置为默认的系统内部未知错误.
	 */
	public void setSystemError() {
		setResult(SYSTEM_ERROR, "系统未知运行时错误");
	}
}
