package org.springside.examples.showcase.ws.client;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

/**
 * 为客户端发起的请求设置WS-Security密码的Handler.
 * 
 * 用户已在外部程序中配置, 密码则为了安全而放在此handler中设置.
 * 设置明文密码后, 框架会按需求负责后面的Digest等加工操作.
 * 
 * @author calvin
 */
public class PasswordCallback implements CallbackHandler {

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		//为admin用户设置密码"admin", 为其他用户统一设置密码"user".
		if ("admin".equals(pc.getIdentifier())) {
			pc.setPassword("admin");
		} else {
			pc.setPassword("user");
		}
	}
}
