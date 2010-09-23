#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.springside.modules.security.springsecurity.SpringSecurityUtils" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Mini-Web 资源权限列表</title>
	<%@ include file="/common/meta.jsp" %>
	<link href="${symbol_dollar}{ctx}/css/yui.css" type="text/css" rel="stylesheet"/>
	<link href="${symbol_dollar}{ctx}/css/style.css" type="text/css" rel="stylesheet"/>
</head>

<body>
<div id="doc3">
<%@ include file="/common/header.jsp" %>
<div id="bd">
	<div id="yui-main">
	<div class="yui-b">
	<div id="message"><s:actionmessage theme="custom" cssClass="success"/></div>

	<div id="filter">你好, <%=SpringSecurityUtils.getCurrentUserName()%>.</div>

	<div id="content">
		<table id="contentTable">
			<tr>
				<th>顺序</th>
				<th>URL</th>
				<th>授权列表</th>
			</tr>

			<s:iterator value="allResourceList">
				<tr>
					<td>${symbol_dollar}{position}</td>
					<td>${symbol_dollar}{value}</td>
					<td>${symbol_dollar}{authNames}</td>
				</tr>
			</s:iterator>
		</table>
	</div>
	</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>
