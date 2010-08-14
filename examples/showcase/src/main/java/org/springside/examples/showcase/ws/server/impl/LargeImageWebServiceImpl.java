package org.springside.examples.showcase.ws.server.impl;

import java.io.File;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springside.examples.showcase.ws.server.LargeImageWebService;
import org.springside.examples.showcase.ws.server.WsConstants;
import org.springside.examples.showcase.ws.server.result.LargeImageResult;
import org.springside.examples.showcase.ws.server.result.WSResult;

/**
 * LargeImageWebService服务端实现类.
 * 
 * 客户端实现见功能测试用例.
 * 
 * @see LargeImageWebService
 * 
 * @author calvin
 */
@WebService(serviceName = "LargeImageService", portName = "LargeImageServicePort", endpointInterface = "org.springside.examples.showcase.ws.server.LargeImageWebService", targetNamespace = WsConstants.NS)
public class LargeImageWebServiceImpl implements LargeImageWebService, ApplicationContextAware {

	private static Logger logger = LoggerFactory.getLogger(LargeImageWebServiceImpl.class);

	private ApplicationContext applicationContext;

	/**
	 * @see LargeImageWebService#getImage()
	 */
	public LargeImageResult getImage() {

		try {
			//采用applicationContext获取Web应用中的文件.
			File image = applicationContext.getResource("/img/logo.jpg").getFile();

			//采用activation的DataHandler实现Streaming传输.
			DataSource dataSource = new FileDataSource(image);
			DataHandler dataHandler = new DataHandler(dataSource);

			LargeImageResult result = new LargeImageResult();
			result.setImageData(dataHandler);
			return result;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return WSResult.buildResult(LargeImageResult.class, WSResult.IMAGE_ERROR, "Image reading error.");
		}

	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
