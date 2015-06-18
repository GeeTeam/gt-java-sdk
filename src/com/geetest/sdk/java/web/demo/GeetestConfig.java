package com.geetest.sdk.java.web.demo;

/**
 * GeetestWeb配置文件
 * 
 * @author zheng
 *
 */
public class GeetestConfig {

	// TODO: replace the these two string with your own captcha's id/key,the id/key below is just for demo
	private static final String captcha_id = "626ddf82c800a41272d1da5591905ca9";
	private static final String private_key = "24a612ae8c62203f724c81a5a9b4e761";

	public static final String getCaptcha_id() {
		return captcha_id;
	}

	public static final String getPrivate_key() {
		return private_key;
	}

}
