package org.springside.modules.security.springsecurity.cxf;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 清除SecurityContext的Interceptor.
 * 
 * @author calvin
 */
public class SpringSecurityOutInterceptor extends AbstractPhaseInterceptor<Message> {

	public SpringSecurityOutInterceptor() {
		super(Phase.PRE_PROTOCOL);
	}

	public void handleMessage(Message message) throws Fault {
		SecurityContextHolder.clearContext();
	}
}
