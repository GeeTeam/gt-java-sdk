package com.geetest.sdk.java;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用Get的方式返回：challenge和capthca_id 此方式以实现前后端完全分离的开发模式
 * 专门实现failback
 * @author zheng
 *
 */
public class StartCapthcaServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		GeetestLib geetestSdk = new GeetestLib();
		geetestSdk.setCaptchaId(GeetestConfig.getCaptcha_id());
		geetestSdk.setPrivateKey(GeetestConfig.getPrivate_key());

		request.setAttribute("geetest", geetestSdk);

		String responseStr = "{}";

		if (geetestSdk.preProcess() != 1) {
			responseStr = String.format("{\"register\":%s}", 0);
		} else {
			responseStr = String.format(
					"{\"success\":%s,\"gt\":\"%s\",\"challenge\":\"%s\"}", 1,
					geetestSdk.getCaptchaId(), geetestSdk.getChallengeId());
		}

		PrintWriter out = response.getWriter();
		out.println(responseStr);

	}
}