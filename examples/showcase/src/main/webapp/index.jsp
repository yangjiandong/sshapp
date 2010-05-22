<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<link href="${ctx}/css/yui.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
	<title>Showcase 示例</title>
</head>
<body>
<!-- #doc3 = 100% width, #yui-t2 = 180 on left in bd div--> 
<div id="doc3" class="yui-t2">
<%@ include file="/common/header.jsp" %>
<div id="bd">
	<%@ include file="/common/left.jsp" %>
	<div id="yui-main">
		<div class="yui-b">
			<p>各式主流的、实用的、好玩的开源项目大派对。</p>
			<p>Coming Soon....</p>
			<ul>
				<li>Memcached演示</li>
				<li>Jetty + JSW 演示</li>
				<li>Key-Value数据库演示.</li>
				<li>ActiveMQ高性能/高可用性演示</li>
			</ul>
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>