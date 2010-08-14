<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>定时任务演示</title>
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
		<h1>定时任务演示</h1>

		<h2>技术说明：</h2>
		<ul>
			<li>JDK5.0 ScheduledExecutorService Timer式任务定义, 支持Graceful Shutdown演示.</li>
			<li>Quartz Timer式与Cron式任务定义.</li>
			<li>Quartz 任务在内存或数据库中存储, 单机或集群执行演示.</li>
		</ul>

		<h2>用户故事：</h2>
		<ul>
			<li>简单的定时在Console打印当前用户数量.</li>
			<li>设法同时运行两个实例, 演示集群运行的效果.</li>
		</ul>
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>