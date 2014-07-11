package com.geetest.sdk.java;

import java.io.IOException;
import java.io.PrintWriter;

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

				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<title>极验验证</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h1>Geetest验证通过</h1>");
				out.println("<h4>" + "当前版本：" + geetest.getVersionInfo()
						+ "</h4>");
				out.println("</body>");
				out.println("</html>");

			} else {
				// TODO handle the fail result
				System.out.println("No!");

				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<title>极验验证</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h1>Geetest验证未通过</h1>");
				out.println("<h4>" + "当前版本：" + geetest.getVersionInfo()
						+ "</h4>");
				out.println("</body>");
				out.println("</html>");
			}
		} else {
			// TODO use you own system
			System.out.println("Geetest Server error~!");

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>极验验证</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>请使用您自己的验证码判断</h1>");
			out.println("</body>");
			out.println("</html>");
		}

	}
}
