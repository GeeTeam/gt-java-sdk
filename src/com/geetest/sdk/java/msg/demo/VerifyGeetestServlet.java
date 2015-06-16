package com.geetest.sdk.java.msg.demo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.geetest.sdk.java.GeetestLib;
import com.geetest.sdk.java.GeetestMsgLib;

public class VerifyGeetestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 244554953219893949L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// get session to share the object
		GeetestMsgLib geetest_msg = (GeetestMsgLib)GeetestMsgLib.getGtSession(request);
		int gt_server_status_code = GeetestLib
				.getGtServerStatusSession(request);

		String gtResult = "fail";

		if (gt_server_status_code == 1) {
			gtResult = geetest_msg.enhencedValidateRequest(request);
			System.out.println(gtResult);
		} else {
			// TODO use you own system when geetest-server is down:failback
			System.out.println("failback:use your own server captcha validate");
			gtResult = "fail";
		}

		if (gtResult.equals(GeetestLib.success_res)) {
			// TODO handle the Success result
			geetest_msg.sendMsgCodeReq(challenge, validate, seccode, phoneNum);
			
			PrintWriter out = response.getWriter();
			out.println(GeetestLib.success_res + ":" + geetest_msg.getVersionInfo());

		} else if (gtResult.equals(GeetestLib.forbidden_res)) {
			// TODO handle the Forbidden result
			PrintWriter out = response.getWriter();
			out.println(GeetestLib.forbidden_res + ":"
					+ geetest_msg.getVersionInfo());
		} else {
			// TODO handle the Fail result
			PrintWriter out = response.getWriter();
			out.println(GeetestLib.fail_res + ":" + geetest_msg.getVersionInfo());
		}

	}
}
