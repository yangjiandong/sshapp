package org.springside.modules.unit.security.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springside.modules.security.utils.CryptoUtils;
import org.springside.modules.utils.encode.EncodeUtils;

public class CryptoUtilsTest {
	@Test
	public void mac() {
		String input = "foo message";

		//key可为任意字符串
		//byte[] key = "a foo key".getBytes();
		byte[] key = CryptoUtils.generateMacSha1Key();
		assertEquals(20, key.length);

		String macHexResult = CryptoUtils.hmacSha1ToHex(input, key);
		String macBase64Result = CryptoUtils.hmacSha1ToBase64(input, key);
		String macBase64UrlResult = CryptoUtils.hmacSha1ToBase64UrlSafe(input, key);
		System.out.println("hmac-sha1 key in hex            :" + EncodeUtils.hexEncode(key));
		System.out.println("hmac-sha1 in hex result         :" + macHexResult);
		System.out.print("hmac-sha1 in base64 result      :" + macBase64Result);
		System.out.println("hmac-sha1 in base64 url result  :" + macBase64UrlResult);

		assertTrue(CryptoUtils.isHexMacValid(macHexResult, input, key));
		assertTrue(CryptoUtils.isBase64MacValid(macBase64Result, input, key));
	}

	@Test
	public void des() {
		byte[] key = CryptoUtils.generateDesKey();
		assertEquals(8, key.length);
		String input = "foo message";

		String encryptHexResult = CryptoUtils.desEncryptToHex(input, key);
		String descryptHexResult = CryptoUtils.desDecryptFromHex(encryptHexResult, key);

		String encryptBase64Result = CryptoUtils.desEncryptToBase64(input, key);
		String descryptBase64Result = CryptoUtils.desDecryptFromBase64(encryptBase64Result, key);

		System.out.println("des key in hex                  :" + EncodeUtils.hexEncode(key));
		System.out.println("des encrypt in hex result       :" + encryptHexResult);
		System.out.print("des encrypt in base64 result    :" + encryptBase64Result);

		assertEquals(input, descryptHexResult);
		assertEquals(input, descryptBase64Result);
	}

	@Test
	public void aes() {
		byte[] key = CryptoUtils.generateAesKey();
		assertEquals(16, key.length);
		String input = "foo message";

		String encryptHexResult = CryptoUtils.aesEncryptToHex(input, key);
		String descryptHexResult = CryptoUtils.aesDecryptFromHex(encryptHexResult, key);

		String encryptBase64Result = CryptoUtils.aesEncryptToBase64(input, key);
		String descryptBase64Result = CryptoUtils.aesDecryptFromBase64(encryptBase64Result, key);

		System.out.println("aes key in hex                  :" + EncodeUtils.hexEncode(key));
		System.out.println("aes encrypt in hex result       :" + encryptHexResult);
		System.out.print("aes encrypt in base64 result    :" + encryptBase64Result);

		assertEquals(input, descryptHexResult);
		assertEquals(input, descryptBase64Result);
	}
}
