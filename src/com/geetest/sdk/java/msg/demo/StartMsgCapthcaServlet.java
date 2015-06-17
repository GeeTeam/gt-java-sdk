package com.geetest.sdk.java.msg.demo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.geetest.sdk.java.GeetestMsgLib;

/**
 * 使用Get的方式返回：challenge和capthca_id 此方式以实现前后端完全分离的开发模式 专门实现failback
 * 
 * @author zheng
 *
 */
public class StartMsgCapthcaServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Conifg the parameter of the geetest object
		GeetestMsgLib gtMsgSdk = new GeetestMsgLib();
		gtMsgSdk.setCaptchaId(GeetestMsgConfig.getCaptcha_id());
		gtMsgSdk.setPrivateKey(GeetestMsgConfig.getPrivate_key());
		gtMsgSdk.setDebugCode(true);

		gtMsgSdk.setGtMsgSession(request);
		

		String resStr = "{}";

		if (gtMsgSdk.preProcess() == 1) {
			// gt server is in use
			resStr = gtMsgSdk.getSuccessPreProcessRes();
			gtMsgSdk.setGtServerStatusSession(request, 1);

		} else {
			// gt server is down
			resStr = gtMsgSdk.getFailPreProcessRes();
			gtMsgSdk.setGtServerStatusSession(request, 0);
		}

		PrintWriter out = response.getWriter();
		out.println(resStr);

	}
}