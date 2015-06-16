package com.geetest.sdk.java.msg.demo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.geetest.sdk.java.GeetestConfig;
import com.geetest.sdk.java.GeetestLib;

/**
 * 使用Get的方式返回：challenge和capthca_id 此方式以实现前后端完全分离的开发模式 专门实现failback
 * 
 * @author zheng
 *
 */
public class ValidateMsgServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// get session to share the object
		GeetestLib geetest = GeetestLib.getGtSession(request);
		int gt_server_status_code = GeetestLib
				.getGtServerStatusSession(request);

		String gtResult = "fail";

		if (gt_server_status_code == 1) {
			gtResult = geetest.enhencedValidateRequest(request);
			System.out.println(gtResult);
		} else {
			// TODO use you own system when geetest-server is down:failback
			System.out.println("failback:use your own server captcha validate");
			gtResult = "fail";
		}

		if (gtResult.equals(GeetestLib.success_res)) {
			// TODO handle the Success result
			PrintWriter out = response.getWriter();
			out.println(GeetestLib.success_res + ":" + geetest.getVersionInfo());

		} else if (gtResult.equals(GeetestLib.forbidden_res)) {
			// TODO handle the Forbidden result
			PrintWriter out = response.getWriter();
			out.println(GeetestLib.forbidden_res + ":"
					+ geetest.getVersionInfo());
		} else {
			// TODO handle the Fail result
			PrintWriter out = response.getWriter();
			out.println(GeetestLib.fail_res + ":" + geetest.getVersionInfo());
		}

	}
}