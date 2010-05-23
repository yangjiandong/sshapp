#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter"%>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter"%>
<%@ page import="org.springframework.security.AuthenticationException"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Mini-Web 登录页</title>
	<%@ include file="/common/meta.jsp"%>
	<link href="${symbol_dollar}{ctx}/css/default.css" type="text/css" rel="stylesheet" />
	<script src="${symbol_dollar}{ctx}/js/jquery.js" type="text/javascript"></script>
	<script src="${symbol_dollar}{ctx}/js/validate/jquery.validate.js" type="text/javascript"></script>
	<script src="${symbol_dollar}{ctx}/js/validate/messages_cn.js" type="text/javascript"></script>
  	<script>
  		${symbol_dollar}(document).ready(function(){
    		${symbol_dollar}("${symbol_pound}loginForm").validate();
    	});
  	</script>
</head>
<body>
	<%
		if (session.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY) != null) {
	%>
			<span style="color:red"> 登录失败，请重试.</span>
	<%
		}
	%>
	<h2>Mini-Web示例</h2>
	<h3>--CRUD管理界面演示</h3>
	<form id="loginForm" action="${symbol_dollar}{ctx}/j_spring_security_check" method="post">
		<table class="inputView">
			<tr>
				<td>用户名:</td>
				<td>
					<input type='text' name='j_username' class="required"
					<s:if test="not empty param.error"> value='<%=session.getAttribute(AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY)%>'</s:if>/>
				</td>
			</tr>
			<tr>
				<td>密码:</td>
				<td><input type='password' name='j_password' class="required" /></td>
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="_spring_security_remember_me" />
				</td>
				<td>两周内记住我</td>
			</tr>
			<tr>
				<td colspan='2'><input value="登录" type="submit" /></td>
			</tr>
		</table>
	</form>
	<p>（管理员<b>admin/admin</b> ,普通用户<b>user/user</b>）</p>
</body>
</html>

