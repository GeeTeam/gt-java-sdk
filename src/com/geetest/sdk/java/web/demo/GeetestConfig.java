package com.geetest.sdk.java.web.demo;

/**
 * GeetestWeb配置文件
 * 
 * @author zheng
 *
 */
public class GeetestConfig {

	// TODO: replace the these two string with your own captcha's id/key,the id/key below is just for demo
	private static final String captcha_id = "b46d1900d0a894591916ea94ea91bd2c";
	private static final String private_key = "36fc3fe98530eea08dfc6ce76e3d24c4";

	public static final String getCaptcha_id() {
		return captcha_id;
	}

	public static final String getPrivate_key() {
		return private_key;
	}

}
