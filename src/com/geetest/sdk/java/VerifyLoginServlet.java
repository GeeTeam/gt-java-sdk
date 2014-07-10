package com.geetest.sdk.java;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VerifyLoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 244554953219893949L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("succeed in GtSdk post!");
		
		// TODO add your own privateKey Here
		String privateKey = "0f1a37e33c9ed10dd2e133fe2ae9c459";

		GeetestLib geetest = new GeetestLib(privateKey);
		boolean result = geetest.validate(
				request.getParameter("geetest_challenge"),
				request.getParameter("geetest_validate"),
				request.getParameter("geetest_seccode"));
		if (result) {
			// TODO
			System.out.println("Yes!");
		} else {
			// TODO
			System.out.println("No!");
		}
	}
}
