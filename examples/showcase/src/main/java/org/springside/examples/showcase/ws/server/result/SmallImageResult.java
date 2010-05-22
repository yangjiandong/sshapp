package org.springside.examples.showcase.ws.server.result;

import javax.xml.bind.annotation.XmlType;

import org.springside.examples.showcase.ws.server.WsConstants;

/**
 * 演示以Base64Binary直接编码整个byte数组的二进制数据传输方式.
 * 
 * @author calvin
 */
@XmlType(name = "SmallImageResult", namespace = WsConstants.NS)
public class SmallImageResult extends WSResult {

	private static final long serialVersionUID = 8375875101365439245L;

	private byte[] imageData;

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
}
