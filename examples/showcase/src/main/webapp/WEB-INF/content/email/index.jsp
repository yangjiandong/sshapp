<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>邮件演示</title>
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
		<h1>邮件演示</h1>

		<h2>技术说明:</h2>
		<ul>
			<li>简单文本邮件演示.</li>
			<li>带附件的MIME邮件演示, 使用Freemarker模板创建HTML内容.</li>
		</ul>
		
		<h2>用户故事：</h2>
		<p>
			在综合演示用例中保存用户时，发送两种邮件.<br/>
			演示邮箱名为springside3.demo@gmail.com, 密码为demoforyou.
		</p>
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>