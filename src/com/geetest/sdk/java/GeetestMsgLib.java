package com.geetest.sdk.java;

import javax.servlet.http.HttpServletRequest;

/**
 * Java SDK--打包了短信验证的库
 * 
 * @author Zheng
 * @time 2014年7月10日 下午3:29:09
 */
public class GeetestMsgLib extends GeetestLib {

	private static final String gt_msg_session_key = "gt_msg";

	private static final String msgBaseUrl = "http://messageapi.geetest.com";
	private static final String sendMsgUrl = "/send";// 发送短信请求
	private static final String validateMsgUrl = "/validate";// 验证短信请求

	// Web表单值，在前端是不允许变化的
	private final String fn_geetest_msg_code = "geetest_msg_code";// name of
																	// message
																	// code form
	private final String fn_geetest_msg_phone = "phone";

	private String phoneNum = "";// 手机号码
	private String msgCode = "";// 短信验证码

	public final String getPhoneNum() {
		return phoneNum;
	}

	public final void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public final String getMsgCode() {
		return msgCode;
	}

	public final void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	/**
	 * 一个无参构造函数
	 */
	public GeetestMsgLib() {
	}

	/**
	 * 发起短信验证码发送请求，前置条件是极验验证能够通过,而且要和短信验证进行绑定
	 * 
	 * @param challenge
	 * @param validate
	 * @param seccode
	 * @param phoneNum
	 * @return
	 */
	public String sendMsgCodeReq(HttpServletRequest request) {

		String phoneNum = request.getParameter("phone");
		String seccode = request.getParameter("geetest_seccode");

		String query = String.format("seccode=%s&sdk=%s&phone=%s&msg_id=%s",
				seccode, (this.sdkLang + "_" + this.verName), phoneNum,
				this.getCaptchaId());

		String response = "";

		gtlog(query);
		try {
			response = postValidate(msgBaseUrl, sendMsgUrl, query,
					this.com_port);
			gtlog("response: " + response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.equals(md5Encode(seccode))) {
			return GeetestLib.success_res;
		} else {
			return response;
		}
	}

	/**
	 * 设置得session
	 * 
	 * @param request
	 */
	public void setGtMsgSession(HttpServletRequest request) {
		request.getSession().setAttribute(gt_msg_session_key, this);// set
																	// session
	}

	/**
	 * 获得session
	 * 
	 * @param request
	 * @return
	 */
	public static GeetestMsgLib getGtMsgSession(HttpServletRequest request) {
		return (GeetestMsgLib) request.getSession().getAttribute(
				gt_msg_session_key);
	}

	/**
	 * 二次的短信验证
	 * 
	 * @param phoneNum
	 * @param msgCode
	 * @return
	 * @throws Exception
	 */
	public String validateMsgCode(HttpServletRequest request) throws Exception {

		String phoneNum = request.getParameter(this.fn_geetest_msg_phone);
		String msgCode = request.getParameter(this.fn_geetest_msg_code);

		String query = String.format("phone=%s&code=%s&msg_id=%s", phoneNum,
				msgCode, this.getCaptchaId());
		String response = "";

		response = postValidate(msgBaseUrl, validateMsgUrl, query,
				this.com_port);

		// TODO 处理完毕返回值文档

		return response;
	}
}
