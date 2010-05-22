package org.springside.examples.showcase.ajax;

import java.util.Collections;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springside.modules.web.struts2.Struts2Utils;

import com.opensymphony.xwork2.ActionSupport;

/**
 * JSONP Mashup 服务端.
 * 
 * @author calvin
 */
@Namespace("/ajax/mashup")
@SuppressWarnings("serial")
public class MashupServerAction extends ActionSupport {

	@Override
	public String execute() {
		//获取JQuery客户端动态生成的callback函数名.
		String callbackName = Struts2Utils.getParameter("callback");

		//设置需要被格式化为JSON字符串的内容.
		Map<String, String> map = Collections.singletonMap("html", "<p>Hello World!</p>");

		//渲染返回结果.
		Struts2Utils.renderJsonp(callbackName, map);
		return null;
	}
}
