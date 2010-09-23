package org.springside.modules.unit.security.utils;

import java.io.IOException;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springside.modules.security.utils.DigestUtils;

public class DigestUtilsTest {

	@Test
	public void digestString() {
		String input = "foo message";

		System.out.println("sha1 in hex result              :" + DigestUtils.sha1ToHex(input));
		System.out.println("sha1 in base64 result           :" + DigestUtils.sha1ToBase64(input));
		System.out.println("sha1 in base64 url result       :" + DigestUtils.sha1ToBase64UrlSafe(input));
	}

	@Test
	public void digestFile() throws IOException {
		Resource resource = new ClassPathResource("/log4j.properties");

		System.out.println("md5: " + DigestUtils.md5ToHex(resource.getInputStream()));
		System.out.println("sha1:" + DigestUtils.sha1ToHex(resource.getInputStream()));
	}
}
