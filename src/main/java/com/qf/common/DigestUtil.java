package com.qf.common;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 编码加密工具类
 * 
 * @author SenWu
 *
 */
public class DigestUtil {

	/**
	 * 对输入字符串进行md5散列.
	 */
	public static String md5Encrypt(String str, String key, String charset) {
		if (StringUtils.isNotBlank(key)) {
			str = str + key;
		}
		return DigestUtils.md5Hex(getContentBytes(str, charset));
	}

	/**
	 * 对输入字符串进行sha1散列.
	 */
	public static String sha1Encrypt(String str, String key, String charset) {
		if (StringUtils.isNotBlank(key)) {
			str = str + key;
		}
		return DigestUtils.sha1Hex(getContentBytes(str, charset));
	}

	/**
	 * 对md5散列进行校验.
	 * 
	 * @param encryptStr
	 * @param str
	 * @param key
	 * @param charset
	 * @return
	 */
	public static boolean md5Verify(String encryptStr, String str, String key, String charset) {
		if (md5Encrypt(str, key, charset).equals(encryptStr)) {
			return true;
		}
		return false;
	}

	/**
	 * 对sha1散列进行校验.
	 * 
	 * @param encryptStr
	 * @param str
	 * @param key
	 * @param charset
	 * @return
	 */
	public static boolean sha1Verify(String encryptStr, String str, String key, String charset) {
		if (sha1Encrypt(str, key, charset).equals(encryptStr)) {
			return true;
		}
		return false;
	}

	private static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5 or SHA1 doesn't support " + charset);
		}
	}
}
