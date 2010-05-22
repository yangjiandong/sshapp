<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>JMS演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<link href="${ctx}/css/yui.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
</head>

<body>
<div id="doc3" class="yui-t2">
<%@ include file="/common/header.jsp" %>
<div id="bd">
	<%@ include file="/common/left.jsp" %>
	<div id="yui-main">
		<div class="yui-b">
		<h1>JMS演示</h1>

		<h2>技术说明：</h2>
		<ul>
			<li>演示基于ActiveMQ的JMS Topic/Queue</li>
			<li>演示基于Spring CachingConnectionFactory, JmsTemplate, DefaultMessageListener的应用</li>
			<li>演示使用默认值的Simple模式与高级设置的Advanced模式</li>
		</ul>

		<h2>用户故事：</h2>
		<ul>
			<li>在综合演示用例中保存用户时,异步发送通知消息邮件</li>
			<li>可使用JConsole查看ActiveMQ Server运行情况.(路径service:jmx:rmi:///jndi/rmi://localhost:1099/showcase,名称org.activemq)
			</li>
		</ul>
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>