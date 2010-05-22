package org.springside.modules.unit.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.utils.EncodeUtils;

public class EncodeUtilsTest extends Assert {

	@Test
	public void hexEncode() {
		String input = "haha,i am a very long message";
		String result = EncodeUtils.hexEncode(input.getBytes());
		assertEquals(input, new String(EncodeUtils.hexDecode(result)));
	}

	@Test
	public void base64Encode() {
		String input = "haha,i am a very long message";
		String result = EncodeUtils.base64Encode(input.getBytes());
		assertEquals(input, new String(EncodeUtils.base64Decode(result)));
	}

	@Test
	public void base64UrlSafeEncode() {
		String input = "haha,i am a very long message";
		String result = EncodeUtils.base64UrlSafeEncode(input.getBytes());
		assertEquals(input, new String(EncodeUtils.base64Decode(result)));
	}
}
