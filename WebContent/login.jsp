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

<script src="http://libs.baidu.com/jquery/1.9.0/jquery.js"></script>

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
		<div id="captcha"></div>

				<%--End  Code--%>


				<div class="row">
					<input type="submit" value="登录" id="submit-button" />
				</div>

				<script src="http://static.geetest.com/static/tools/gt.js"></script>
				<script>
				    var handler = function (captchaObj) {
				         // 将验证码加到id为captcha的元素里
				         captchaObj.appendTo("#captcha");
				     };
				    $.ajax({
				        // 获取id，challenge，success（是否启用failback）
				        url: "StartCaptchaServlet",
				        type: "get",
				        dataType: "json", // 使用jsonp格式
				        success: function (data) {
				            // 使用initGeetest接口
				            // 参数1：配置参数，与创建Geetest实例时接受的参数一致
				            // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它做appendTo之类的事件
				            initGeetest({
				                gt: data.gt,
				                challenge: data.challenge,
				                product: "embed", // 产品形式
				                offline: !data.success
				            }, handler);
				        }
				    });
				</script>
			</div>



		</form>
	</div>
</body>
</html>
