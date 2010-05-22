<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>XML操作演示</title>
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
		<h1>XML/JSON操作演示</h1>

		<h2>技术说明：</h2>
		<ul>
			<li>基于JAXB2.0的Java-XML绑定, 演示根元素是List, 属性是List与Map等特殊情况. 见JaxbTest</li>
			<li>万能老倌Dom4j, 见JaxbTest.</li>
			<li>最快的JSON转换类Jackson, 见JacksonTest.</li>
		</ul>

		<h2>用户故事：</h2>
		<p>在JaxbTest测试用例中演示XML与Java对象的转换及Dom4j的使用.</p>
		<p>在JacksonTest测试用例中演示JSON字符串与Bean, Map, List<String>, List<Bean>, Bean[]之间的转换.</p>
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>