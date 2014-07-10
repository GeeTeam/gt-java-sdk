package com.geetest.sdk.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

/**
 * Java SDK
 * 
 * @author Zheng
 * @time 2014年7月10日 下午3:29:09
 */
public class GeetestLib {

	/**
	 * SDK版本编号
	 */
	private final int verCode = 6;

	/**
	 * SDK版本名称
	 */
	private final String verName = "2.0";

	/**
	 * 私钥
	 */
	private String privateKey = "";

	/**
	 * 一个无参构造函数
	 */
	public GeetestLib() {
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public GeetestLib(String privateKey) {
		this.privateKey = privateKey;
	}

	public int getVerCode() {
		return verCode;
	}

	public String getVerName() {
		return verName;
	}

	/**
	 * 获取极验的服务器状态
	 * 
	 * @time 2014年7月10日 下午3:38:49
	 * @return
	 */
	public int getGtServerStatus() {
		// TODO 做一个方法处理
		return 0;
	}

	/**
	 * 判断一个表单对象值是否为空
	 * 
	 * @time 2014年7月10日 下午5:54:25
	 * @param gtObj
	 * @return
	 */
	private boolean objIsEmpty(Object gtObj) {
		if (gtObj != null && gtObj.toString().trim().length() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 检查客户端的请求是否为空--三个只要有一个为空，则判断不合法
	 * 
	 * @time 2014年7月10日 下午5:46:34
	 * @param request
	 * @return
	 */
	public boolean resquestIsLegal(HttpServletRequest request) {

		if (objIsEmpty(request.getParameter("geetest_challenge"))) {
			return false;
		}

		if (objIsEmpty(request.getParameter("geetest_validate"))) {
			return false;
		}

		if (objIsEmpty(request.getParameter("geetest_seccode"))) {
			return false;
		}

		return false;
	}

	/**
	 * 检验验证请求 传入的参数为request
	 * 
	 * @time 2014年7月10日 下午6:34:55
	 * @param request
	 * @return
	 */
	public boolean validateRequest(HttpServletRequest request) {

		boolean gtResult = this.validate(
				request.getParameter("geetest_challenge"),
				request.getParameter("geetest_validate"),
				request.getParameter("geetest_seccode"));

		return gtResult;
	}

	public boolean validate(String challenge, String validate, String seccode) {
		String host = "api.geetest.com";
		String path = "/validate.php";
		int port = 80;
		if (validate.length() > 0 && checkResultByPrivate(challenge, validate)) {
			String query = "seccode=" + seccode;
			String response = "";
			try {
				response = postValidate(host, path, query, port);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (response.equals(md5Encode(seccode))) {
				return true;
			}
		}
		return false;

	}

	private boolean checkResultByPrivate(String origin, String validate) {
		String encodeStr = md5Encode(privateKey + "geetest" + origin);
		return validate.equals(encodeStr);
	}

	private String postValidate(String host, String path, String data, int port)
			throws Exception {
		String response = "error";
		// data=fixEncoding(data);
		InetAddress addr = InetAddress.getByName(host);
		Socket socket = new Socket(addr, port);
		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream(), "UTF8"));
		wr.write("POST " + path + " HTTP/1.0\r\n");
		wr.write("Host: " + host + "\r\n");
		wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
		wr.write("Content-Length: " + data.length() + "\r\n");
		wr.write("\r\n"); // 以空行作为分割
		// 发送数据
		wr.write(data);
		wr.flush();
		// 读取返回信息
		BufferedReader rd = new BufferedReader(new InputStreamReader(
				socket.getInputStream(), "UTF-8"));
		String line;
		while ((line = rd.readLine()) != null) {
			System.out.println(line);
			response = line;
		}
		wr.close();
		rd.close();
		return response;
	}

	/**
	 * 转为UTF8编码
	 * 
	 * @time 2014年7月10日 下午3:29:45
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String fixEncoding(String str) throws UnsupportedEncodingException {
		String tempStr = new String(str.getBytes("UTF-8"));
		return URLEncoder.encode(tempStr, "UTF-8");
	}

	/**
	 * md5 加密
	 * 
	 * @time 2014年7月10日 下午3:30:01
	 * @param plainText
	 * @return
	 */
	public String md5Encode(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return re_md5;
	}

}
