package org.springside.examples.showcase.functional.webservice.ws;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.activation.DataHandler;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springside.examples.showcase.functional.BaseFunctionalTestCase;
import org.springside.examples.showcase.ws.server.LargeImageWebService;
import org.springside.examples.showcase.ws.server.SmallImageWebService;
import org.springside.examples.showcase.ws.server.WsConstants;
import org.springside.examples.showcase.ws.server.result.LargeImageResult;
import org.springside.examples.showcase.ws.server.result.SmallImageResult;

/**
 * WebService传输二进制数据测试.
 * 
 * @author calvin
 */
public class BinaryWebServiceTest extends BaseFunctionalTestCase {

	@Test
	public void getSmallImage() throws IOException {

		//创建SmallImageService
		URL wsdlURL = new URL("http://localhost:8080/showcase/services/SmallImageService?wsdl");
		QName serviceName = new QName(WsConstants.NS, "SmallImageService");
		Service service = Service.create(wsdlURL, serviceName);
		SmallImageWebService imageService = service.getPort(SmallImageWebService.class);

		//调用SmallImageService
		SmallImageResult result = imageService.getImage();
		assertTrue(result.getImageData().length > 0);

		//保存图片文件并校验
		String tempFilePath = System.getProperty("java.io.tmpdir") + "smalllogo.jpg";
		OutputStream os = new FileOutputStream(tempFilePath);
		IOUtils.write(result.getImageData(), os);
		IOUtils.closeQuietly(os);
		System.out.println("图片已保存至" + tempFilePath);
	}

	@Test
	public void getLargeImage() throws IOException {

		//创建LargeImageService
		URL wsdlURL = new URL("http://localhost:8080/showcase/services/LargeImageService?wsdl");
		QName serviceName = new QName(WsConstants.NS, "LargeImageService");
		Service service = Service.create(wsdlURL, serviceName);
		LargeImageWebService imageService = service.getPort(LargeImageWebService.class);

		//调用LargeImageService
		LargeImageResult result = imageService.getImage();
		DataHandler dataHandler = result.getImageData();

		//保存图片文件并校验
		String tempFilePath = System.getProperty("java.io.tmpdir") + "largelogo.jpg";

		InputStream is = dataHandler.getInputStream();
		OutputStream os = new FileOutputStream(tempFilePath);

		IOUtils.copy(is, os);
		IOUtils.closeQuietly(is);
		IOUtils.closeQuietly(os);
		System.out.println("图片已保存至" + tempFilePath);

		File tempFile = new File(tempFilePath);
		assertTrue(tempFile.length() > 0);
	}

}
