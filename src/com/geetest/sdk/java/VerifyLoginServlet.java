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

		// TODO add your own privateKey Here
		String privateKey = "0f1a37e33c9ed10dd2e133fe2ae9c459";
		GeetestLib geetest = new GeetestLib(privateKey);

		if (geetest.resquestIsLegal(request)) {

			boolean gtResult = geetest.validateRequest(request);
			if (gtResult) {
				// TODO handle the pass result
				System.out.println("Yes!");
			} else {
				// TODO handle the fail result
				System.out.println("No!");
			}
		} else {
			// TODO use you own system
			System.out.println("Geetest error~!");
		}

	}
}
