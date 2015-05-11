<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!doctype html>
<html>
<head>
<base href="<%=basePath%>">

<title>极意网络</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style>
body {
	background-color: #FEFEFE;
}

.wrap {
	width: 960px;
	margin: 100px auto;
	font-size: 125%;
}

.row {
	margin: 30px 0;
}
</style>
</head>

<body>
	<div class="wrap">
		<h1>JavaEE站点安装Demo页面</h1>
		<form method="post" action="VerifyLoginServlet">
			<div class="row">
				<label for="name">邮箱</label> <input type="text" id="email"
					name="email" value="geetest@126.com" />
			</div>
			<div class="row">
				<label for="passwd">密码</label> <input type="password" id="passwd"
					name="passwd" value="gggggggg" />
			</div>

			<%--Start  Code--%>
			<div class="row">

				<jsp:useBean id="geetestSdk" class="com.geetest.sdk.java.GeetestLib"
					scope="request" />
				<%
					//TODO： replace your own ID here  after create a Captcha App in 'my.geetest.com'
					String captcha_id = "626ddf82c800a41272d1da5591905ca9";//It's a capthca whihc needs to be register
					geetestSdk.setCaptchaId(captcha_id);
					//geetestSdk.setIsHttps(true);
					//geetestSdk.setProductType("popup");
					//geetestSdk.setSubmitBtnId("submit-button");
					//geetestSdk.setIsHttps(true);
				%>
				<%
					if (geetestSdk.preProcess() != 1) {
				%>
				<h1>Geetest Server is down,Please use your own captcha system
					in your web page</h1>
				<%
					} else {
				%>
				<%=geetestSdk.getGtFrontSource()%>
				<%
					}
				%>
			</div>
			<%--End  Code--%>


			<div class="row">
				<input type="submit" value="登录" id="submit-button" />
			</div>
			<script src="http://libs.baidu.com/jquery/1.9.0/jquery.js"></script>

			<!-- 			ajax post demo -->
			<script type="text/javascript">
				function gt_custom_ajax(result, selector, message) {

					console.log(result);

					var challenge = selector(".geetest_challenge").value;
					var validate = selector(".geetest_validate").value;
					var seccode = selector(".geetest_seccode").value;
					// 					$.ajax({
					// 						url : "VerifyLoginServlet",
					// 						type : "post",
					// 						data : {
					// 							geetest_challenge : challenge,
					// 							geetest_validate : validate,
					// 							geetest_seccode : seccode
					// 						},
					// 						success : function(result) {
					// 							console.log(result);
					// 						}
					// 					})

				}
			</script>



		</form>
	</div>
</body>
</html>
