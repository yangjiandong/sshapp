#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div id="menu">
	<h3>
	<a href="${symbol_dollar}{ctx}/security/user.action">帐号列表</a>
	<a href="${symbol_dollar}{ctx}/security/role.action">角色列表</a> 
	<a href="${symbol_dollar}{ctx}/j_spring_security_logout">退出登录</a>
	</h3> 
</div>
