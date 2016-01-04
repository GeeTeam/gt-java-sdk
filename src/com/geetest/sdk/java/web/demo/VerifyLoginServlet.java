package com.geetest.sdk.java.web.demo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.geetest.sdk.java.GeetestLib;
import com.geetest.sdk.java.web.demo.GeetestConfig;


/**
 * 使用post方式，返回验证结果, request表单中必须包含challenge, validate, seccode
 */
public class VerifyLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 244554953219893949L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		GeetestLib gtSdk = new GeetestLib();
		
		gtSdk.setPrivateKey(GeetestConfig.getPrivate_key());
		
		//从session中获取gt-server状态
		int gt_server_status_code = GeetestLib.getGtServerStatusSession(request);
		
		//从session中获取challenge
		gtSdk.getChallengeSession(request);

		String gtResult = "fail";

		if (gt_server_status_code == 1) {
			//gt-server正常，向gt-server进行二次验证
			gtResult = gtSdk.enhencedValidateRequest(request);
			System.out.println(gtResult);
		} else {
			// gt-server非正常情况下，进行failback模式验证
			System.out.println("failback:use your own server captcha validate");
			gtResult = "fail";
			
			gtResult = gtSdk.failbackValidateRequest(request);
			
			
		}


		if (gtResult.equals(GeetestLib.success_res)) {
			// 验证成功
			PrintWriter out = response.getWriter();
			out.println(GeetestLib.success_res + ":" + gtSdk.getVersionInfo());

		} else if (gtResult.equals(GeetestLib.forbidden_res)) {
			// 验证被判为机器人
			PrintWriter out = response.getWriter();
			out.println(GeetestLib.forbidden_res + ":" + gtSdk.getVersionInfo());
		} else {
			// 验证失败
			PrintWriter out = response.getWriter();
			out.println(GeetestLib.fail_res + ":" + gtSdk.getVersionInfo());
		}

	}
}
