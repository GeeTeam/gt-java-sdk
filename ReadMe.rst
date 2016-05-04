
.. contents:: 目录



文件描述
==========


注意：本项目提供的Demo的前端实现方法均是面向PC端的。
如果需要移动端的canvas功能，请参考canvas的前端文档。


在进行此项目前，请您务必仔细阅读了：极验用户引导手册 [#geetest-user-guid]_


.. [#geetest-user-guid] `极验用户必读 <http://www.geetest.com/install/sections/idx-main-frame.html>`__


核心SDK库
---------------------

1. GeetestLib.java
    提供只带拼图行为验证的功能

    
极验行为验证Demo
------------------------------------

包名：*com.geetest.sdk.java.web.demo*


1. GeetestConfig.java
	Web用户配置文件。此处填写用户自己申请的验证模块ID/KEY
#. StartCaptchaServlet.java
	用户判断极验服务器是否Down机的示例Servlet,页面生成前置处理
#. VerifyLoginServlet.java
	提交验证调用Sdk的示例Servlet
#. login.jsp
	行为验证的前端示范页面
	
目前这个demo页面比以前复杂的原因如下：

1. 实现failback的前端逻辑，在正常和非正常之间形成无缝自动化切换。
#. 在极验服务器down机的情况下，避免同步请求长达20s的阻塞页面
#. 在极验服务down机头部下，仍然先优先使用异步加载，把加载时间减少一半。
	



搭建开发环境 
===================

搭建Eclipse+Tomcat的开发环境

一分钟运行Demo 
=========================

1. 从GitHub中clone一份到本地
#. 使用Eclipse直接import一个项目
#. 打开tomcat
#. 在浏览器中访问http://localhost:8080/gt-java-sdk/login.jsp即可看到Demo界面

集成验证服务到自己项目
=========================

1. 将com.geetest.sdk.java这个包引入项目中
#. 根据自身使用情况修改前端代码调用验证码,前端文档 http://www.geetest.com/install/sections/idx-client-sdk.html#web
#. 修改后台代码，具体可以参考demo中的后台进行修改

failback效果展示
=========================

如果极验服务器出现故障，会全自动切换到备选验证，以确保网站主的正常功能能够进行。请有开发能力的网站请务必完成failback后的本地化代码功能，以减少不必要的损失。

正常效果
-----------------

.. image::  ./2015-06-16-001.png


failback效果
---------------------- 

.. image::  ./2015-06-16-002.png



联系作者
=============

QQ:383449573


发布日志（由新到旧）
==================================='

3.2.0
---------------------------
- 增加新接口，可以在两次验证过程中传入自定义userid,如果两次传入的userid不同，验证无法通过



3.1.0
---------------------------
- 将注册完后获取的challenge进行一层加密再返回



3.0.1
---------------------------

- 删除无参数构造函数,精简接口
- 修改demo前端


3.0.0
---------------------------

- Sdk不再对session进行直接操作，这部分由开发者自己完成
- 删除一些不再使用的接口


2.0.0
---------------------------

- 不再将整个sdk实例放入session中
- 添加API文档
- 删除一些不再使用的接口



