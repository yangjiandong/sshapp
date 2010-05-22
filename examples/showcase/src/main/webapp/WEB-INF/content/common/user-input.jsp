<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>综合演示用例</title>
	<%@ include file="/common/meta.jsp" %>
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
		<h2><s:if test="id == null">创建</s:if><s:else>修改</s:else>用户</h2>

		<form id="inputForm" action="${ctx}/common/user!save.action" method="post">
			<input type="hidden" name="id" value="${id}"/>
			<input type="hidden" name="workingVersion" value="${version}"/>
			<table class="noborder">
				<tr>
					<td>登录名:</td>
					<td><input type="text" name="loginName" size="40" value="${loginName}"/></td>
				</tr>
				<tr>
					<td>用户名:</td>
					<td><input type="text" name="name" size="40" value="${name}"/></td>
				</tr>
				<tr>
					<td>密码:</td>
					<td><input type="password" name="plainPassword" size="40" value="${plainPassword}"/></td>
				</tr>
				<tr>
					<td>密码散列:</td>
					<td>${shaPassword}</td>
				</tr>

				<tr>
					<td>创建:</td>
					<td>${createBy} <fmt:formatDate value="${createTime}" type="both"/></td>
				</tr>
				<tr>
					<td>最后修改:</td>
					<td>${lastModifyBy} <fmt:formatDate value="${lastModifyTime}" type="both"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="提交"/>&nbsp;
						<input type="button" value="返回" onclick="history.back()"/>
					</td>
				</tr>
			</table>
		</form>
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>