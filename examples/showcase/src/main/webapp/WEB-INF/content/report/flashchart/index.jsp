<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Flash Chart演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<link href="${ctx}/css/yui.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
	<script type="text/javascript" src="${ctx}/amcharts/swfobject.js"></script>
</head>

<body>
<div id="doc3" class="yui-t2">
<%@ include file="/common/header.jsp" %>
<div id="bd">
	<%@ include file="/common/left.jsp" %>
	<div id="yui-main">
		<div class="yui-b">
		<h1>Flash Chart演示</h1>

		<h2>技术说明：</h2>

		<p>使用AmCharts渲染Flash图表</p>
		<ul>
			<li>曲线图：演示精简版配置文件与CSV格式数据文件.</li>
			<li>柱状图：演示完整版配置文件与XML格式数据文件,可点击条柱跳转到子报表页面.</li>
		</ul>
		<div id="report1Content"></div>

		<script type="text/javascript">
			// <![CDATA[
			var so = new SWFObject("${ctx}/amcharts/amline.swf", "report1", "640", "420", "8", "#FFFFFF");
			so.addVariable("settings_file", encodeURIComponent("${ctx}/amcharts/report1-settings.xml"));
			so.addVariable("data_file", encodeURIComponent("${ctx}/report/flashchart/chart-data!report1.action"));
			so.write("report1Content");
			// ]]>
		</script>

		<div id="report2Content"></div>

		<script type="text/javascript">
			// <![CDATA[
			var so = new SWFObject("${ctx}/amcharts/amcolumn.swf", "report2", "640", "420", "8", "#FFFFFF");
			so.addVariable("settings_file", encodeURIComponent("${ctx}/amcharts/report2-settings.xml"));
			so.addVariable("data_file", encodeURIComponent("${ctx}/report/flashchart/chart-data!report2.action"));
			so.write("report2Content");
			// ]]>
		</script>
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>