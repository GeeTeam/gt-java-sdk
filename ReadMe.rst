
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

15.1.29.2
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


