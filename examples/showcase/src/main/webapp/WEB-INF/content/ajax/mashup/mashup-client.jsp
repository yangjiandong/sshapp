<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>跨域Mashup演示</title>
	<%@ include file="/common/meta.jsp" %>
	<link href="${ctx}/css/yui.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
	<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript">
		var remoteUrl = "http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";

		//使用JQuery.getJSON(url?callback=?)方式跨域访问内容
		function getMashupContent() {
			$.getJSON(remoteUrl + "/ajax/mashup/mashup-server.action?callback=?", function(data) {
				$('#mashupContent').html(data.html);
				$('#mashupContent').show();
			});
		}
	</script>
</head>

<body>
<div id="doc3" class="yui-t2">
<%@ include file="/common/header.jsp" %>
<div id="bd">
	<%@ include file="/common/left.jsp" %>
	<div id="yui-main">
		<div class="yui-b">
		<h1>Mashup演示</h1>
	
		<h2>技术说明：</h2>
		<p>
			演示基于JQuery的JSONP实现，绕过浏览器对Ajax访问跨域名网站内容的限制.<br/>
			请先将本页另存为本地html文件，打开本地文件访问应用服务器即为跨域访问的场景.
		</p>
		<p><input type="button" value="获取内容" onclick="getMashupContent();"/></p>	
		<p>跨域页面内容:</p>
		<div id="mashupContent" style="display:none"/>
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>