<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>极验行为式验证Java类网站安装测试页面</title>
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
		background-color: #9e9;
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
		<h1>极验行为式验证 Java 类网站安装测试页面</h1>
		<form method="post" action="VerifyLoginServlet">
			<div class="row">
				<label for="name">邮箱</label>
				<input type="text" id="email" name="email" value="geetest@126.com"/>
			</div>
			<div class="row">
				<label for="passwd">密码</label>
				<input type="password" id="passwd" name="passwd" value="gggggggg"/>
			</div>
			<div class="row">
				<script type="text/javascript" src="http://api.geetest.com/get.php?gt=79398e04fa187e9d3d391e867843b20a"></script>
			</div>
			<div class="row">
				<input type="submit" value="登录" />
			</div>
		</form>
	</div>	
  </body>
</html>
