#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.springside.modules.security.springsecurity.SpringSecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Mini-Web 角色管理</title>
	<%@ include file="/common/meta.jsp"%>
	<link href="${symbol_dollar}{ctx}/css/default.css" type="text/css" rel="stylesheet">
</head>

<body>
<%@ include file="/common/menu.jsp"%>
<div id="message"><s:actionmessage theme="mytheme"/></div>

<div id="filter">你好,<%=SpringSecurityUtils.getCurrentUserName()%>.</div>

<div id="listContent">
<table id="listTable">
	<tr>
		<th><b>名称</b></th>
		<th><b>授权</b></th>
		<th><b>操作</b></th>
	</tr>

	<s:iterator value="roleList">
		<tr>
			<td>${symbol_dollar}{name}</td>
			<td>${symbol_dollar}{authNames}</td>
			<td>&nbsp; 
				<security:authorize ifAnyGranted="A_VIEW_ROLE">
				<security:authorize ifNotGranted="A_MODIFY_ROLE">
					<a href="role!input.action?id=${symbol_dollar}{id}">查看</a>&nbsp;
				</security:authorize>
				</security:authorize>
	
				<security:authorize ifAnyGranted="A_MODIFY_ROLE">
					<a href="role!input.action?id=${symbol_dollar}{id}">修改</a>&nbsp;
					<a href="role!delete.action?id=${symbol_dollar}{id}">删除</a>
				</security:authorize>
			</td>
		</tr>
	</s:iterator>
</table>
</div>

<div id="footer">
	<security:authorize ifAnyGranted="A_MODIFY_ROLE">
		<a href="role!input.action">增加新角色</a>
	</security:authorize>
</div>

<div id="comment">本页面为单纯的白板,各式Table Grid组件的应用见Showcase项目(开发中).</div>
</body>
</html>