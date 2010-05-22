<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Ajax演示</title>
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
		<h1>Ajax演示</h1>
		<ul>
			<li><a href="${ctx}/jmx/jmx-client.action">标准Ajax演示</a> 在JMX示例中演示html/json内容的动态获取与表单的动态提交.</li>
			<li><a href="${ctx}/ajax/mashup/mashup-client.action">跨域Mashup演示</a> 演示基于JSONP实现跨域Ajax.</li>
			<li><a href="${ctx}/ajax/dojo/index.action">Dojo base演示</a> 演示基于Dojo Base的Javascript文件Package化与Script方法OO化.</li>
		</ul>
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>

</html>