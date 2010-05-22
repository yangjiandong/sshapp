package org.springside.examples.showcase.ws.server.result;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;

import org.springside.examples.showcase.ws.server.WsConstants;

/**
 * 演示以MTOM附件协议传输Streaming DataHandler的二进制数据传输的方式. 
 * 
 * @author calvin
 */
@XmlType(name = "LargeImageResult", namespace = WsConstants.NS)
public class LargeImageResult extends WSResult {

	private static final long serialVersionUID = 8375875101365439245L;

	private DataHandler imageData;

	@XmlMimeType("application/octet-stream")
	public DataHandler getImageData() {
		return imageData;
	}

	public void setImageData(DataHandler imageData) {
		this.imageData = imageData;
	}
}
