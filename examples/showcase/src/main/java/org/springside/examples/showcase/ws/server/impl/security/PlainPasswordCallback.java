package org.springside.examples.showcase.ws.server.impl.security;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.common.service.AccountManager;

/**
 * 对WS-Security中Digest式密码的处理Handler.
 * 
 * @author calvin
 */
public class PlainPasswordCallback implements CallbackHandler {

	private AccountManager accountManager;

	/**
	 * 根据用户名查出数据库中用户已散列的密码,对明文密码进行相同的散列后进行比较.
	 * 如果用户名或密码错误则抛出异常.
	 */
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		PasswordEncoder encoder = new ShaPasswordEncoder();
		User user = accountManager.findUserByLoginName(pc.getIdentifier());

		if (user == null) {
			throw new IOException("wrong login name " + pc.getIdentifier());
		}
		//对WSPasswordCallback中的明文密码进行sha1散列, 再与数据库中保存的用户sha1散列密码进行比较.
		if (!encoder.isPasswordValid(user.getShaPassword(), pc.getPassword(), null)) {
			throw new IOException("wrong password " + pc.getPassword() + " for " + pc.getIdentifier());
		}
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}
}
