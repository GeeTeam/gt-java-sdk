package com.geetest.sdk.java;

/**
 * 使用Get的方式返回：challenge和capthca_id 此方式以实现前后端完全分离的开发模式，如果是多实例，可以以此为模板，多定义几个GeetestConfig文件。分别初始化即可 
 * 
 * @author zheng
 *
 */
public class GeetestConfig {

	// TODO: replace the these two string with your own captcha's id/key
	private static final String captcha_id = "626ddf82c800a41272d1da5591905ca9";
	private static final String private_key = "24a612ae8c62203f724c81a5a9b4e761";

	public static final String getCaptcha_id() {
		return captcha_id;
	}

	public static final String getPrivate_key() {
		return private_key;
	}

}
