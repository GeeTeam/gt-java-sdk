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
		
		System.out.println("succeed in post!");
		
		String privateKey = "a40fd3b0d712165c5d13e6f747e948d4";
		GeetestLib geetest = new GeetestLib(privateKey);
		boolean result = geetest.validate(
				request.getParameter("geetest_challenge"),
				request.getParameter("geetest_validate"),
				request.getParameter("geetest_seccode"));
		if (result) {
			System.out.println("Yes!");
		} else {
			System.out.println("No!");
		}
	}
}
