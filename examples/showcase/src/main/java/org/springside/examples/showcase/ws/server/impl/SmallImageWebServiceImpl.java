package org.springside.examples.showcase.ws.server.impl;

import java.io.IOException;
import java.io.InputStream;

import javax.jws.WebService;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springside.examples.showcase.ws.server.SmallImageWebService;
import org.springside.examples.showcase.ws.server.WsConstants;
import org.springside.examples.showcase.ws.server.result.SmallImageResult;
import org.springside.examples.showcase.ws.server.result.WSResult;

/**
 * SmallImageWebService服务端实现类.
 * 
 * 客户端实现见功能测试用例.
 * 
 * @see SmallImageWebService
 * 
 * @author calvin
 */
@WebService(serviceName = "SmallImageService", portName = "SmallImageServicePort", endpointInterface = "org.springside.examples.showcase.ws.server.SmallImageWebService", targetNamespace = WsConstants.NS)
public class SmallImageWebServiceImpl implements SmallImageWebService, ApplicationContextAware {

	private static Logger logger = LoggerFactory.getLogger(SmallImageWebServiceImpl.class);

	private ApplicationContext applicationContext;

	/**
	 * @see SmallImageWebService#getImage()
	 */
	public SmallImageResult getImage() {

		InputStream is = null;
		try {
			//采用applicationContext的getResource()函数获取Web应用中的文件.
			is = applicationContext.getResource("/img/logo.jpg").getInputStream();
			//读取内容到字节数组.
			byte[] imageBytes = IOUtils.toByteArray(is);

			SmallImageResult result = new SmallImageResult();
			result.setImageData(imageBytes);
			return result;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return WSResult.buildResult(SmallImageResult.class, WSResult.IMAGE_ERROR, "Image reading error.");
		} finally {
			IOUtils.closeQuietly(is);
		}

	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
