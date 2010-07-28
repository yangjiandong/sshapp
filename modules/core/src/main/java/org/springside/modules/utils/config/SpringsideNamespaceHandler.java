package org.springside.modules.utils.config;

import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * {@link NamespaceHandler} for petcare configuration namespace: contains extension tags that will eventually be integrated back into Spring.
 *
 * @author Keith Donald
 * @since 3.0
 */
public class SpringsideNamespaceHandler extends NamespaceHandlerSupport {

	public void init() {
		registerBeanDefinitionParser("resources", new ResourcesBeanDefinitionParser());
	}

}

