package com.geetest.sdk.java;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Java SDK--打包了短信验证的库
 * 
 * @author Zheng
 * @time 2014年7月10日 下午3:29:09
 */
public class GeetestMsgLib extends GeetestLib {

	private static final String gt_msg_session_key = "gt_msg";

	private static final String msgBaseUrl = "messageapi.geetest.com";
	// private final String msgBaseUrl = "192.168.1.213";
	// private final int com_port = 8888;// 通讯端口号

	private final String sendMsgUrl = "/send";// 发送短信请求
	private final String validateMsgUrl = "/validate";// 验证短信请求

	// Web表单值，在前端是不允许变化的-name of message code form
	private final String fn_geetest_msg_code = "geetest_msg_code";
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

	/*
	 * (non-Javadoc) 判断提交短信验证的表单是否合法
	 * 
	 * @see com.geetest.sdk.java.GeetestLib#resquestIsLegal(javax.servlet.http.
	 * HttpServletRequest)
	 */
	public boolean resquestIsLegal(HttpServletRequest request) {

		if (objIsEmpty(request.getParameter(this.fn_geetest_msg_phone))) {
			return false;
		}

		if (objIsEmpty(request.getParameter(this.fn_geetest_challenge))) {
			return false;
		}

		if (objIsEmpty(request.getParameter(this.fn_geetest_validate))) {
			return false;
		}

		if (objIsEmpty(request.getParameter(this.fn_geetest_seccode))) {
			return false;
		}

		return true;
	}

	
	/**
	 * 根据服务器端返回值来提取码
	 * @param msgRes
	 * @return
	 * @throws JSONException
	 */
	private int getMsgResCode(String msgRes) throws JSONException
	{
		gtlog("response: " + msgRes);
		JSONObject resObj = new JSONObject(msgRes);
		int resCode = resObj.getInt("res");
		
		return resCode;
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
	public int sendMsgCodeReq(HttpServletRequest request) {

		if (!resquestIsLegal(request)) {
			gtlog("the request form is wrong");
			return -10;
		}

		String phoneNum = request.getParameter(this.fn_geetest_msg_phone);
		String challenge = request.getParameter(this.fn_geetest_challenge);
		String validate = request.getParameter(this.fn_geetest_validate);
		String seccode = request.getParameter(this.fn_geetest_seccode);

		if (!checkResultByPrivate(challenge, validate)) {
			gtlog("the request form check not pass");
			return -10;
		}

		String query = String.format("seccode=%s&sdk=%s&phone=%s&msg_id=%s",
				seccode, (this.sdkLang + "_" + this.verName), phoneNum,
				this.getCaptchaId());
		gtlog(query);

		String response = "";

		try {
			response = postValidate(msgBaseUrl, sendMsgUrl, query, com_port);
			

			int resCode = getMsgResCode(response);

			gtlog(String.format("message server res : %s", resCode));

			return resCode;

		} catch (Exception e) {
			e.printStackTrace();
		}

		gtlog("unknow exception");
		return -10;
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
	public int validateMsgCode(HttpServletRequest request) throws Exception {

		String phoneNum = request.getParameter(this.fn_geetest_msg_phone);
		String msgCode = request.getParameter(this.fn_geetest_msg_code);

		String query = String.format("phone=%s&code=%s&msg_id=%s", phoneNum,
				msgCode, this.getCaptchaId());
		String response = "";

		response = postValidate(msgBaseUrl, validateMsgUrl, query, com_port);

		return getMsgResCode(response);
	}
}
