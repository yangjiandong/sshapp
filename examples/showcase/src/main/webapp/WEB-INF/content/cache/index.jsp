<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Cache演示</title>
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
		<h1>Cache演示</h1>

		<h2>技术说明：</h2>
		<ul>
			<li>演示Memcached的使用</li>
			<li>演示Spring与Ehcache的集成</li>
			<li>演示基于Google Collection的简单MapCache, 每个元素一天后过期, 当key不存在时需要重新计算时能避免并发访问造成的重复计算</li>
		</ul>
		
		<h2>用户故事：</h2>
		<ul>
			<li>在AccountManager.java中演示了对Memcached的使用</li>
			<li>EhcacheDemo.java演示了Ehcache与Spring的集成</li>
			<li>MapCacheDemo.java演示了基于Google Collection实现的MapCache</li>
		</ul>
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>