<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>数据库访问高级演示</title>
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
		<h1>数据库访问高级演示</h1>

		<h2>Hibernate高级应用：</h2>
		<ul>
			<li>自定义主键生成</li>
			<li>Event，在修改User对象时自动加入审计信息。(综合示例)</li>
			<li>@Version字段，在Struts2 Prepareable Action里的使用。(综合示例)</li>
			<li>继承，Post与Subject/Reply类的同表继承。(PostDao) </li>
			<li>Clob字段。(PostDao)</li>
			<li>查询时预加载Lazy Load关联对象。(UserDao)</li>
			<li>批量更新操作的HQL。(UserDao)</li>
			<li>扩展Dialect，加入数据库特性语句。(UserDao)</li>
		</ul>
		
		<h2>Spring JdbcTemplate应用(UserJdbcDao)</h2>
		<ul>
			<li>在高性能要求的时候使用Spring JdbcTemplate访问和更新数据库。</li>
			<li>在高性能要求的时候使用Spring TransactionTemplate精确控制事务。</li>
			<li>SqlBuilder支持用Velocity模板引擎根据查询条件动态生成SQL, SQL模板定义在XML中, 可以优雅的换行缩进。</li>
		</ul>
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>