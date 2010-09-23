<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>日志高级演示</title>
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
		<h1>日志高级演示</h1>

		<h2>技术说明：</h2>
		<ul>
			<li>Log4JMBean: 通过JMX动态查询与改变Logger的日志等级与Appender.</li>
			<li>QueueAppender/JdbcLogWriter:轻量级的日志异步数据库写入框架, 可用于业务日志写入.</li>
			<li>MockLog4jAppender: 在测试用例中验证日志的输出.</li>
			<li>TraceLogAspect/TraceUtils/@Traced: 方便跟踪问题的调试信息使用,详见wiki.</li>
		</ul>
		
		<h2>用户故事：</h2>
		<ul>
			<li>使用JConsole动态修改log4j的日志等级.(路径service:jmx:rmi:///jndi/rmi://localhost:18080/jmxrmi,名称SpringSide:type=Log4jManagement)</li>
			<li>Schedule测试用例使用MockAppender校验日志输出.</li>
			<li>UserWebService服务通过TraceLogAspect, 使用TraceUtils在Log4j MDC中设置TraceId.</li>
			<li>每次进入本页面, logger都会通过AOp自动生成一条调试记录，记录在另外的showcase_trace.log.</li>
			<li>每次进入本页面, logger都会生成一条数据库日志记录，每10次批量插入数据库LOGS表.</li>
		</ul>
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>