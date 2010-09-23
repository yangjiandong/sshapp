#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.springside.modules.security.springsecurity.SpringSecurityUtils" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Mini-Web 角色管理</title>
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
				<th>名称</th>
				<th>授权</th>
				<th>操作</th>
			</tr>

			<s:iterator value="allRoleList">
				<tr>
					<td>${symbol_dollar}{name}</td>
					<td>${symbol_dollar}{authNames}</td>
					<td>&nbsp;
						<security:authorize ifAnyGranted="ROLE_浏览角色">
							<security:authorize ifNotGranted="ROLE_修改角色">
								<a href="role!input.action?id=${symbol_dollar}{id}">查看</a>&nbsp;
							</security:authorize>
						</security:authorize>

						<security:authorize ifAnyGranted="ROLE_修改角色">
							<a href="role!input.action?id=${symbol_dollar}{id}" id="editLink-${symbol_dollar}{name}">修改</a>&nbsp;
							<a href="role!delete.action?id=${symbol_dollar}{id}" id="deleteLink-${symbol_dollar}{name}">删除</a>
						</security:authorize>
					</td>
				</tr>
			</s:iterator>
		</table>
	</div>

	<div>
		<security:authorize ifAnyGranted="ROLE_修改角色">
			<a href="role!input.action">增加新角色</a>
		</security:authorize>
	</div>
	</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>
