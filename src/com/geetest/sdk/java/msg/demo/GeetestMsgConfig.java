package com.geetest.sdk.java.msg.demo;

/**
 * GeetestMsg的配置
 * @author zheng
 *
 */
public class GeetestMsgConfig {

	// TODO: replace the these two string with your own msg-captcha's id/key
	private static final String captcha_id = "a40fd3b0d712165c5d13e6f747e948d4";
	private static final String private_key = "0f1a37e33c9ed10dd2e133fe2ae9c459";
	
	
	public static final String getCaptcha_id() {
		return captcha_id;
	}

	public static final String getPrivate_key() {
		return private_key;
	}

}
