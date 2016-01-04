package com.geetest.sdk.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.print.DocFlavor.STRING;
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
	// private final int verCode = 8;

	/**
	 * SDK版本名称
	 */
	protected final String verName = "2.0.0";
	protected final String sdkLang = "java";// SD的语言类型

	protected final static String gt_challenge_session_key = "geetest_challenge";// challenge的key值(单实例)
	protected final static String gt_server_status_session_key = "gt_server_status";// 极验服务器状态key值（单实例）

	protected final String baseUrl = "api.geetest.com";
	protected final String api_url = "http://" + baseUrl;
	protected final String https_api_url = "https://" + baseUrl;// 一些页面是https
	protected final int com_port = 80;// 通讯端口号

	protected final int defaultIsMobile = 0;
	// private final int defaultMobileWidth = 260;// the default width of the
	// mobile id

	// 一些常量
	public static final String success_res = "success";
	public static final String fail_res = "fail";
	public static final String forbidden_res = "forbidden";

	// 前端验证的表单值--属于接口，不允许修改
	protected final String fn_geetest_challenge = "geetest_challenge";
	protected final String fn_geetest_validate = "geetest_validate";
	protected final String fn_geetest_seccode = "geetest_seccode";

	protected Boolean debugCode = true;// 调试开关，是否输出调试日志
	protected String validateLogPath = "";// 服务器端保存日志的目录//var/log/，请确保有可读写权限

	/**
	 * 公钥
	 */
	private String captchaId = "";

	/**
	 * 私钥
	 */
	private String privateKey = "";

	/**
	 * the challenge
	 */
	private String challengeId = "";

	/**
	 * he captcha product type,default is 'embed'
	 */
	private String productType = "embed";

	/**
	 * is secure
	 */
	private Boolean isHttps = false;

	public Boolean getIsHttps() {
		return isHttps;
	}

	public void setIsHttps(Boolean isHttps) {
		this.isHttps = isHttps;
	}

	/**
	 * when the productType is popup,it needs to set the submitbutton
	 */
	private String submitBtnId = "submit-button";

	public String getSubmitBtnId() {
		return submitBtnId;
	}

	public void setSubmitBtnId(String submitBtnId) {
		this.submitBtnId = submitBtnId;
	}

	/**
	 * 是否是移动端的
	 */
	private int isMobile = defaultIsMobile;// 1--true,0-false
	
	/**
	 * 无参构造函数
	 */
	public GeetestLib() {
	}

	public final Boolean getDebugCode() {
		return debugCode;
	}

	public final void setDebugCode(Boolean debugCode) {
		this.debugCode = debugCode;
	}
	
	public String getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(String challengeId) {
		this.challengeId = challengeId;
	}
	

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public int getIsMobile() {
		return isMobile;
	}

	public void setIsMobile(int isMobile) {
		this.isMobile = isMobile;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public GeetestLib(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getVerName() {
		return verName;
	}

	public String getCaptchaId() {
		return captchaId;
	}

	public void setCaptchaId(String captchaId) {
		this.captchaId = captchaId;
	}

	/**
	 * 获取版本编号
	 */
	public String getVersionInfo() {
		return verName;
	}

	/**
	 * 设置服务器端保存日志的目录
	 */
	public void setValidateLogPath(String validateLogPath) {
		this.validateLogPath = validateLogPath;
	}
	
	/**
	 * 获取服务器端保存日志的目录
	 */
	public String getValidateLogPath() {
		return validateLogPath;
	}

	/**
	 * 设置极验服务器的gt-server状态值
	 * 
	 * @param request
	 * @param statusCode
	 */
	public void setGtServerStatusSession(HttpServletRequest request,
			int statusCode) {
		request.getSession().setAttribute(gt_server_status_session_key,
				statusCode);// set session
	}

	/**
	 * 设置极验服务器的gt-server状态值（用于同一会话多实例模式下）
	 * 
	 * @param request
	 * @param statusCode
	 * @param gt_instance_server_status_session_key
	 */
	public void setGtServerStatusSession(HttpServletRequest request,
			int statusCode, String gt_instance_server_status_session_key) {
		request.getSession().setAttribute(
				gt_instance_server_status_session_key, statusCode);// set
																	// session
	}

	/**
	 * 获取极验服务器的gt-server状态值
	 * 
	 * @param request
	 * @return 0表示不正常，1表示正常，2表示本次请求没有按照流程来请求，直接不予理会
	 */
	public static int getGtServerStatusSession(HttpServletRequest request) {

		Object server_status = request.getSession().getAttribute(
				gt_server_status_session_key);

		if (server_status == null) {
			return 2;// 没有按照流程，直接向客户服务器提交数据
		}
		return (Integer) server_status;
	}

	/**
	 * 获取极验服务器的gt-server状态值(用于同一会话多实例模式下)
	 * 
	 * @param request
	 * @param gt_instance_server_status_session_key
	 * @return 0表示不正常，1表示正常，2表示本次请求没有按照流程来请求，直接不予理会
	 */
	public static int getGtServerStatusSession(HttpServletRequest request,
			String gt_instance_server_status_session_key) {

		Object server_status = request.getSession().getAttribute(
				gt_instance_server_status_session_key);

		if (server_status == null) {
			return 2;// 没有按照流程，直接向客户服务器提交数据
		}

		return (Integer) server_status;
	}
	
	/**
	 * 设置本次验证的challenge
	 * 
	 * @param request
	 */
	public void setChallengeSession(HttpServletRequest request) {
		request.getSession().setAttribute(gt_challenge_session_key,
				this.challengeId);
	}
	
	/**
	 * 设置本次验证的challenge(用于同一会话多实例模式下)
	 * 
	 * @param request
	 */
	public void setGtServerStatusSession(HttpServletRequest request,
			String gt_instance_server_status_session_key) {
		request.getSession().setAttribute(gt_instance_server_status_session_key,
				this.challengeId);
	}
	
	/**
	 * 获取本次验证的challenge
	 * 
	 * @param request
	 */
	public void getChallengeSession(HttpServletRequest request) {
		this.challengeId = (String)request.getSession().getAttribute(
				gt_challenge_session_key);
	}
	
	/**
	 * 获取本次验证的challenge(用于同一会话多实例模式下)
	 * 
	 * @param request
	 * @param gt_instance_server_status_session_key
	 */
	public void getChallengeSession(HttpServletRequest request,
			String gt_instance_server_status_session_key) {
		this.challengeId = (String)request.getSession().getAttribute(
				gt_instance_server_status_session_key);
	}

	/**
	 * 预处理失败后的返回格式串
	 * 
	 * @return
	 */
	public String getFailPreProcessRes() {
		// return String.format("{\"success\":%s}", 0);

		Long rnd1 = Math.round(Math.random() * 100);
		Long rnd2 = Math.round(Math.random() * 100);
		String md5Str1 = md5Encode(rnd1 + "");
		String md5Str2 = md5Encode(rnd2 + "");
		String challenge = md5Str1 + md5Str2.substring(0, 2);
		this.setChallengeId(challenge);

		return String.format(
				"{\"success\":%s,\"gt\":\"%s\",\"challenge\":\"%s\"}", 0,
				this.getCaptchaId(), this.getChallengeId());
	}

	/**
	 * 预处理成功后的标准串
	 * 
	 * @return
	 */
	public String getSuccessPreProcessRes() {
		return String.format(
				"{\"success\":%s,\"gt\":\"%s\",\"challenge\":\"%s\"}", 1,
				this.getCaptchaId(), this.getChallengeId());
	}

	/**
	 * 保存验证的日志，方便后续和极验做一些联调工作,用于可能有前端验证通过，但是后面验证失败的情况
	 * 
	 * @param challenge
	 * @param validate
	 * @param seccode
	 * @param gtUser 用户页面的cookie标识
	 * @param sdkResult
	 */
	public void saveValidateLog(String challenge, String validate,
			String seccode, String sdkResult) {

		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd   hh:mm:ss");
		String date = sDateFormat.format(new java.util.Date());

		String logFormat = String.format(
				"date:%s,challenge:%s,validate:%s,seccode:%s,sdkResult:%s",
				date, challenge, validate, seccode, sdkResult);

		gtlog(logFormat);

	}

	/**
	 * 验证初始化预处理
	 *
	 * @return
	 */
	public int preProcess() {

		if (registerChallenge() != 1) {
			return 0;
		}

		return 1;

	}

	public int getRandomNum() {

		int rand_num = (int) (Math.random() * 100);
		// System.out.print(rand_num);
		return rand_num;
	}

	/**
	 * 用captchaID进行注册，更新challenge
	 * 
	 * @return 1表示注册成功，0表示注册失败
	 */
	private int registerChallenge() {
		try {
			String GET_URL = api_url + "/register.php?gt=" + this.captchaId;
			String result_str = readContentFromGet(GET_URL);
			System.out.println(result_str);
			if (32 == result_str.length()) {
				this.challengeId = result_str;
				return 1;
			} else {
				System.out.println("gServer register challenge failed");
				return 0;
			}
		} catch (Exception e) {
			gtlog("exception:register api:");
			// e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 发送请求，获取服务器返回结果
	 * 
	 * @param getURL
	 * @return 服务器返回结果
	 * @throws IOException
	 */
	private String readContentFromGet(String getURL) throws IOException {

		URL getUrl = new URL(getURL);
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();

		connection.setConnectTimeout(2000);// 设置连接主机超时（单位：毫秒）
		connection.setReadTimeout(2000);// 设置从主机读取数据超时（单位：毫秒）

		// 建立与服务器的连接，并未发送数据
		connection.connect();
		
		// 发送数据到服务器并使用Reader读取返回的数据
		StringBuffer sBuffer = new StringBuffer();

		InputStream inStream = null;
		byte[] buf = new byte[1024];
		inStream = connection.getInputStream();
		for (int n; (n = inStream.read(buf)) != -1;) {
			sBuffer.append(new String(buf, 0, n, "UTF-8"));
		}
		inStream.close();
		connection.disconnect();// 断开连接

		return sBuffer.toString();
	}
	
	
	
	
	/**
	 * 判断一个表单对象值是否为空
	 * 
	 * @param gtObj
	 * @return
	 */
	protected boolean objIsEmpty(Object gtObj) {
		if (gtObj == null) {
			return true;
		}

		if (gtObj.toString().trim().length() == 0) {
			return true;
		}
		// && gtObj.toString().trim().length() > 0

		return false;
	}

	/**
	 * 检查客户端的请求是否合法,三个只要有一个为空，则判断不合法
	 * 
	 * @param request
	 * @return
	 */
	public boolean resquestIsLegal(HttpServletRequest request) {

		if (objIsEmpty(request.getParameter(this.fn_geetest_challenge))) {
			return false;
		}

		if (objIsEmpty(request.getParameter(this.fn_geetest_validate))) {
			return false;
		}

		if (objIsEmpty(request.getParameter(this.fn_geetest_seccode))) {
			return false;
		}

		return true;
	}
	
	
	
	/**
	 * 服务正常的情况下使用的验证方式,向gt-server进行二次验证,获取验证结果
	 * 
	 * @param challenge
	 * @param validate
	 * @param seccode
	 * @return
	 */
	public String enhencedValidateRequest(HttpServletRequest request) {

		if (!resquestIsLegal(request)) {
			return GeetestLib.fail_res;
		}
		gtlog("request legitimate");
		String challenge = request.getParameter(this.fn_geetest_challenge);
		String validate = request.getParameter(this.fn_geetest_validate);
		String seccode = request.getParameter(this.fn_geetest_seccode);
		

		String host = baseUrl;
		String path = "/validate.php";
		int port = 80;
		// String query = "seccode=" + seccode + "&sdk=" + this.sdkLang + "_"
		// + this.verName;

		String query = String.format("seccode=%s&sdk=%s", seccode,
				(this.sdkLang + "_" + this.verName));
		
		String response = "";

		gtlog(query);
		try {
			gtlog(challenge);
			if (validate.length() <= 0) {
				return GeetestLib.fail_res;
			}

			if (!checkResultByPrivate(challenge, validate)) {
				return GeetestLib.fail_res;
			}

			response = postValidate(host, path, query, port);

			gtlog("response: " + response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		gtlog("md5: " + md5Encode(seccode));

		if (response.equals(md5Encode(seccode))) {
			return GeetestLib.success_res;
		} else {
			return response;
		}

	}

	/**
	 * failback使用的验证方式
	 * 
	 * @param request
	 * @return
	 */
	public String failbackValidateRequest(HttpServletRequest request) {

		gtlog("in failback validate");

		if (!resquestIsLegal(request)) {
			return GeetestLib.fail_res;
		}
		gtlog("request legitimate");

		String challenge = request.getParameter(this.fn_geetest_challenge);
		String validate = request.getParameter(this.fn_geetest_validate);
		String seccode = request.getParameter(this.fn_geetest_seccode);

		String[] validateStr = validate.split("_");
		String encodeAns = validateStr[0];
		String encodeFullBgImgIndex = validateStr[1];
		String encodeImgGrpIndex = validateStr[2];

		gtlog(String.format(
				"encode----challenge:%s--ans:%s,bg_idx:%s,grp_idx:%s",
				challenge, encodeAns, encodeFullBgImgIndex, encodeImgGrpIndex));

		int decodeAns = decodeResponse(this.getChallengeId(), encodeAns);
		int decodeFullBgImgIndex = decodeResponse(this.getChallengeId(),
				encodeFullBgImgIndex);
		int decodeImgGrpIndex = decodeResponse(this.getChallengeId(),
				encodeImgGrpIndex);

		gtlog(String.format("decode----ans:%s,bg_idx:%s,grp_idx:%s", decodeAns,
				decodeFullBgImgIndex, decodeImgGrpIndex));

		String validateResult = validateFailImage(decodeAns,
				decodeFullBgImgIndex, decodeImgGrpIndex);

		if (!validateResult.equals(GeetestLib.fail_res)) {
			// 使用一随机标识来丢弃掉此次验证，防止重放
			Long rnd1 = Math.round(Math.random() * 100);
			String md5Str1 = md5Encode(rnd1 + "");
			this.setChallengeId(md5Str1);
		}

		return validateResult;
	}
	
	

	/**
	 *
	 * @param ans
	 * @param full_bg_index
	 * @param img_grp_index
	 * @return
	 */
	private String validateFailImage(int ans, int full_bg_index,
			int img_grp_index) {
		final int thread = 3;// 容差值

		String full_bg_name = md5Encode(full_bg_index + "").substring(0, 9);
		String bg_name = md5Encode(img_grp_index + "").substring(10, 19);

		String answer_decode = "";

		// 通过两个字符串奇数和偶数位拼接产生答案位
		for (int i = 0; i < 9; i++) {
			if (i % 2 == 0) {
				answer_decode += full_bg_name.charAt(i);
			} else if (i % 2 == 1) {
				answer_decode += bg_name.charAt(i);
			} else {
				gtlog("exception");
			}
		}

		String x_decode = answer_decode.substring(4, answer_decode.length());

		int x_int = Integer.valueOf(x_decode, 16);// 16 to 10

		int result = x_int % 200;
		if (result < 40) {
			result = 40;
		}

		if (Math.abs(ans - result) <= thread) {
			return GeetestLib.success_res;
		} else {
			return GeetestLib.fail_res;
		}
	}
	
	
	
	
	/**
	 * 解码随机参数
	 * 
	 * @param encodeStr
	 * @param challenge
	 * @return
	 */
	private int decodeResponse(String challenge, String string) {
		if (string.length() > 100) {
			return 0;
		}

		int[] shuzi = new int[] { 1, 2, 5, 10, 50 };
		String chongfu = "";
		HashMap<String, Integer> key = new HashMap<String, Integer>();
		int count = 0;

		for (int i = 0; i < challenge.length(); i++) {
			String item = challenge.charAt(i) + "";

			if (chongfu.contains(item) == true) {
				continue;
			} else {
				int value = shuzi[count % 5];
				chongfu += item;
				count++;
				key.put(item, value);
			}
		}

		int res = 0;

		for (int j = 0; j < string.length(); j++) {
			res += key.get(string.charAt(j) + "");
		}

		res = res - decodeRandBase(challenge);

		return res;

	}

	/**
	 * 输入的两位的随机数字,解码出偏移量
	 * 
	 * @param randStr
	 * @return
	 */
	private int decodeRandBase(String challenge) {

		String base = challenge.substring(32, 34);
		ArrayList<Integer> tempArray = new ArrayList<Integer>();

		for (int i = 0; i < base.length(); i++) {
			char tempChar = base.charAt(i);
			Integer tempAscii = (int) (tempChar);

			Integer result = (tempAscii > 57) ? (tempAscii - 87)
					: (tempAscii - 48);

			tempArray.add(result);
		}

		int decodeRes = tempArray.get(0) * 36 + tempArray.get(1);
		return decodeRes;

	}
	
	

	/**
	 * 输出debug信息，需要开启debugCode
	 * 
	 * @param message
	 */
	public void gtlog(String message) {
		if (debugCode) {
			System.out.println("gtlog: " + message);
		}
	}

	protected boolean checkResultByPrivate(String challenge, String validate) {
		String encodeStr = md5Encode(privateKey + "geetest" + challenge);
		return validate.equals(encodeStr);
	}

	/**
	 * 貌似不是Post方式，后面重构时修改名字
	 * 
	 * @param host
	 * @param path
	 * @param data
	 * @param port
	 * @return
	 * @throws Exception
	 */
	protected String postValidate(String host, String path, String data,
			int port) throws Exception {
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
		socket.close();
		return response;
	}


	/**
	 * md5 加密
	 * 
	 * @time 2014年7月10日 下午3:30:01
	 * @param plainText
	 * @return
	 */
	private String md5Encode(String plainText) {
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
