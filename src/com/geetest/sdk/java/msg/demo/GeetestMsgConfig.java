package com.geetest.sdk.java.msg.demo;

/**
 * GeetestMsg的配置
 * @author zheng
 *
 */
public class GeetestMsgConfig {

	// TODO: replace the these two string with your own msg-captcha's id/key
	private static final String captcha_id = "4a0eb5e6f780944308954bc602814a53";
	private static final String private_key = "03e0d35820a741d74445761c2b6d38c5";
	
	
	public static final String getCaptcha_id() {
		return captcha_id;
	}

	public static final String getPrivate_key() {
		return private_key;
	}

}
