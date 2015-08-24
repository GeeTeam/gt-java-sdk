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

	<script src="http://api.geetest.com/get.php"></script>
	<div class="wrap">
		<h1>JavaEE站点安装Demo页面</h1>
		<form method="post" action="VerifyMsgServlet">
			<div class="row">
				<label for="name">邮箱</label> <input type="text" id="email"
					name="email" value="geetest@126.com" />
			</div>
			<div class="row">
				<label for="passwd">密码</label> <input type="password" id="passwd"
					name="passwd" value="gggggggg" />
			</div>
			<div class="row">
				<label for="name">手机</label> <input type="text" name="phone"
					id="phone" />
			</div>
			<div class="row">
				<div id="div_id_embed"></div>

				<br />
				<div class="box msg-box" id="gt-msg-component">
					<label>短信验证</label> <br /> <input type="text" class="mes-input"
						name="geetest_msg_code" value="" />
					<button type="button" class="mes-button" onClick="getMsgCode()">获取短信验证码</button>
				</div>

				<script type="text/javascript">
					//get phone message validate code
					function getMsgCode() {

						post_gt_validate_data = gt_captcha_obj.getValidate();
						post_gt_validate_data.phone = $("input[name='phone']")
								.val();

						$
								.ajax({
									url : "VerifyGeetestServlet",
									type : "post",
									data : post_gt_validate_data,
									success : function(sdk_result) {

										if (sdk_result == 1) {
											console
													.log("the picture capthca is success,and wait for message");
										}

										console.log(sdk_result);
										if (sdk_result == -6) {
											console
													.log("the picture capthca is fail");
										}

										if (sdk_result == -10) {
											console.log("the form is wrong");
										}

										//TODO  set the notice before message captcha
									}
								});
					}
				</script>


				<div class="row">
					<input type="submit" value="登录" id="submit-button" />
				</div>

				<script type="text/javascript">
					//get  geetest server status, use the failback solution
					$
							.ajax(
									{
										url : "StartMsgCaptchaServlet",
										type : "get",
										dataType : 'JSON',
										success : function(result) {
											console.log(result);
											if (result.success) {
												//1. use geetest capthca
												window.gt_captcha_obj = new window.Geetest(
														{
															gt : result.gt,
															challenge : result.challenge,
															product : 'embed'
														});

												gt_captcha_obj
														.appendTo("#div_id_embed");

												$("#gt-msg-component").show();

												//Ajax request demo,if you use submit form ,then ignore it 
												gt_captcha_obj
														.onSuccess(function() {
															console
																	.log('define your own callback');
														});

											} else {
												//failback :use your own captcha template
												//Geetest Server is down,Please use your own captcha system	in your web page
												//or use the simple geetest failback solution
												$("#div_id_embed")
														.html(
																'failback:gt-server is down ,please use your own captcha front');

												$("#gt-msg-component").hide();
												//document.write('gt-server is down ,please use your own captcha')
											}

										}
									})
				</script>
				<div class="row">
					<input type="button" value="测试自定义刷接口" onclick="geetest_refresh()" />
				</div>
			</div>

			<script type="text/javascript">
				function geetest_refresh() {
					console.log("you can use this api in your own js function");
					gt_captcha_obj.refresh();
				}
			</script>

		</form>
	</div>
</body>
</html>
