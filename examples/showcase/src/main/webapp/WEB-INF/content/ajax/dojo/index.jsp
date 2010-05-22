<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Dojo Base演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<link href="${ctx}/css/yui.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
	<script src="${ctx}/js/dojo/dojo.js" type="text/javascript"></script>
	<script type="text/javascript">
		//载入js/showcase/shape.js
		dojo.require("showcase.shape");

		//演示函数,显示Circle对象的内容.
		function demo() {
			c1 = new CircleWidget("red", 100);
			dojo.byId("demoContent").innerHTML = c1.generateContent();
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
		<h1>Dojo Base演示</h1>
	
		<h2>技术说明：</h2>
		<p>演示基于Dojo Base的Javascript文件Package化与Script方法OO化.</p>
		<p><input type="button" value="获取演示内容" onclick="demo();"/></p>
		<p>Dojo Base演示内容:</p>
		<div id="demoContent"/>
		</div>
	</div>
	<%@ include file="/common/footer.jsp" %>
</div>
</div>
</body>
</html>