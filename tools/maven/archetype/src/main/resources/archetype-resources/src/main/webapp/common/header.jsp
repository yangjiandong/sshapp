#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="org.springside.modules.security.springsecurity.SpringSecurityUtils" %>
<%@ include file="/common/taglibs.jsp" %>
<div id="hd">
	<div id="title">
		<h1>Mini-Web示例</h1>
		<h2>--CRUD管理界面演示</h2>
	</div>
	<div id="menu">
		<ul>
			<li><a href="${symbol_dollar}{ctx}/account/user.action">帐号列表</a></li>
			<li><a href="${symbol_dollar}{ctx}/account/role.action">角色列表</a></li>
			<li><a href="${symbol_dollar}{ctx}/j_spring_security_logout">退出登录</a></li>
		</ul>
	</div>
</div>