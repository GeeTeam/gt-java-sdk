<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
					if (((geetestSdk.getGtServerStatus()) != 1)) {
				%>
				<h1>请插入自己服务器上的备选验证码</h1>

				<%
					} else {
				%>
				<script type="text/javascript"
					src="http://api.geetest.com/get.php?gt=a40fd3b0d712165c5d13e6f747e948d4&product=embed"></script>
				<%
					}
				%>

			</div>
			<%--End  Code--%>


			<div class="row">
				<input type="submit" value="登录" />
			</div>
			<script src="http://libs.baidu.com/jquery/1.9.0/jquery.js"></script>

			<!-- 			ajax post demo -->
			<script type="text/javascript">
				function gt_custom_ajax(result, selector, message) {

					console.log(result);

					var challenge = selector(".geetest_challenge").value;
					var validate = selector(".geetest_validate").value;
					var seccode = selector(".geetest_seccode").value;
					$.ajax({
						url : "VerifyLoginServlet",
						type : "post",
						data : {
							geetest_challenge : challenge,
							geetest_validate : validate,
							geetest_seccode : seccode
						},
						success : function() {
						}
					})

				}
			</script>



		</form>
	</div>
</body>
</html>
