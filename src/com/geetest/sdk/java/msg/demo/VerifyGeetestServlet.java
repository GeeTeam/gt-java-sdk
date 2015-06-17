package com.geetest.sdk.java.msg.demo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.geetest.sdk.java.GeetestLib;
import com.geetest.sdk.java.GeetestMsgLib;

/**
 * Msg套件下的拼图一次验证
 * 
 * @author zheng
 *
 */
public class VerifyGeetestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 244554953219893949L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// get session to share the object
		GeetestMsgLib gtMsgSdk = (GeetestMsgLib) GeetestMsgLib
				.getGtMsgSession(request);
		int gt_server_status_code = GeetestLib
				.getGtServerStatusSession(request);

		int gtResult = -10;

		if (gt_server_status_code == 1) {
			gtResult = gtMsgSdk.sendMsgCodeReq(request);
			System.out.println(gtResult);
		} else {
			// TODO use you own system when geetest-server is down:failback
			System.out.println("failback:use your own server captcha validate");
			gtResult = -10;
		}


		if (gtResult == 1) {
			// TODO handle the Success result
			PrintWriter out = response.getWriter();
			out.println(gtResult);
		} else {
			// TODO handle the Fail result
			PrintWriter out = response.getWriter();
			out.println(gtResult);
		}

	}
}
