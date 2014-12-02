
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


.. image:: Res/QQ20140711140909.png



联系作者：
Email:dreamzsm@gmail.com


发布日志（由新到旧）
===================



Vc8Vn14.12.01（2014122_193953）
-----------------------------------------

让forbidden的情况在仍然提示通过，但客户服务器仍然能够知晓原因。

1. GeetestLib.java 中加入了增强型的验证结果判定的API
#. VerifyLoginServlet.java 中重新对新接口进行了引用。


