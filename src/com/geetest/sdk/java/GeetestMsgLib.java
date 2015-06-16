package com.geetest.sdk.java;

import javax.sound.sampled.Port;

/**
 * Java SDK--打包了短信验证的库
 * 
 * @author Zheng
 * @time 2014年7月10日 下午3:29:09
 */
public class GeetestMsgLib extends GeetestLib {

	private final String msgBaseUrl = "http://messageapi.geetest.com";
	private final String sendMsgUrl = "/send";// 发送短信请求
	private final String validateMsgUrl = "/validate";// 验证短信请求
	

	/**
	 * 一个无参构造函数
	 */
	public GeetestMsgLib() {
	}

	/**
	 * 发起短信验证码发送请求，前置条件是极验验证能够通过
	 * 
	 * @param challenge
	 * @param validate
	 * @param seccode
	 * @param phoneNum
	 * @return
	 */
	public String sendMsgCodeReq(String challenge, String validate,
			String seccode, String phoneNum) {

		// String query = "seccode=" + seccode + "&sdk=" + this.sdkLang + "_"
		// + this.verName;

		String query = String.format("seccode=%s&sdk=%s&phone=%s&msg_id=%s",
				seccode, (this.sdkLang + "_" + this.verName), phoneNum,
				this.getCaptchaId());

		String response = "";

		gtlog(query);
		try {
			if (validate.length() <= 0) {
				return GeetestLib.fail_res;
			}

			if (!checkResultByPrivate(challenge, validate)) {
				return GeetestLib.fail_res;
			}

			response = postValidate(msgBaseUrl, sendMsgUrl, query, this.com_port);
			gtlog("response: " + response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		gtlog("md5: " + md5Encode(seccode));

		if (response.equals(md5Encode(seccode))) {
			return GeetestLib.success_res;
		} else {
			return response;
		}
	}

	/**
	 * 二次的短信验证
	 * @param phoneNum
	 * @param msgCode
	 * @return
	 * @throws Exception
	 */
	public String validateMsgCode(String phoneNum, String msgCode) throws Exception {

		String query = String.format("phone=%s&code=%s&msg_id=%s", phoneNum,
				msgCode, this.getCaptchaId());
		String response = "";

		response = postValidate(msgBaseUrl, validateMsgUrl, query, this.com_port);
		return response;
	}
}
