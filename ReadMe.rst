
.. contents:: 目录



文件描述
==========

1. GeetestLib.java
	极验的JavaSDK库
#. VerifyLoginServlet.java
	调用Sdk的示例Servlet
#. Login.jsp
	使用验证码的前端示范页面

搭建开发环境 
===================

搭建Eclipse+Tomcat的开发环境

一分钟运行Demo 
=========================

1. 从GitHub中clone一份到本地
#. 使用Eclipse直接import一个项目
#. 打开tomcat
#. 在浏览器中访问http://localhost:8080/GtJavaSdkDemo/Login.jsp即可看到Demo界面

API调用示例
=========================


设置id
-------------------------------

.. code:: java

        String captcha_id = "a40fd3b0d712165c5d13e6f747e948d4";
        geetestSdk.setCaptchaId(captcha_id);
        


设置key
------------------------------

.. code:: java

        String private_key = "0f1a37e33c9ed10dd2e133fe2ae9c459";
		GeetestLib geetest = new GeetestLib(private_key);


设置产品样式
-----------------------------

.. code:: java

        geetestSdk.setProductType("embed");



设置提交按钮
-----------------------------------------

如果是popup的产品形式，则还需要设置submit-buttion-id

.. code:: java

        geetestSdk.setProductType("popup");
        geetestSdk.setSubmitBtnId("submit-button");


设置是否是https页面
-----------------------------------------

默认是http页面，经过设置后可以支持https页面

.. code:: java

        geetestSdk.setIsHttps(true);



联系作者：
Email:dreamzsm@gmail.com


发布日志（由新到旧）
===================================


2.15.5.22.1
---------------------

1. 修改了检查down机的时间超时为2s


2.15.4.1.4
-----------------------

1. 加入了前端中https的设置函数
#. 使用新的版本编号。加入了一个前缀的大版本号。
#. 在二次验证的时候，加入了SDK版本号和SDK语言类型。
#. 修改了sdk版本的表示字段为sdk，内容一致



15.3.31.1
-----------------------

1. 统一命名方式



15.2.2.2
-----------------------

1. chanllege的生成函数转移到服务器端
#. 服务器端重复的failback去掉

15.2.2.1
-----------------------

1. chanllege的生成函数转移到服务器端

15.1.29.3
-----------------------------------------

1. 修复了一些小bug
#. 和服务端联调成功

15.1.28.1
-----------------------------------------
1. SDK加入了自主生成id的接口
#. 前端src引用有了加强版
#. SDK的版本编号规则发生变化



Vc8Vn14.12.01
-----------------------------------------

让forbidden的情况在仍然提示通过，但客户服务器仍然能够知晓原因。

1. GeetestLib.java 中加入了增强型的验证结果判定的API
#. VerifyLoginServlet.java 中重新对新接口进行了引用。


Vc7Vn2.1
-------------------
1. 规范sdk的发布流程，形成持续集成
#. 调整结构，简化开发人员使用步骤。做到1分钟入门的Demo
#. 加入failback“备胎”验证码的功能
#. 检验服务器数据读取时长限定为1s后就算超时，就报超时


