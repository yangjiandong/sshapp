<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.springside.modules.security.springsecurity.SpringSecurityUtils" %>
<%@ page import="org.springside.examples.showcase.security.OperatorDetails" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>综合演示用例</title>
	<%@ include file="/common/meta.jsp" %>
	<link href="${ctx}/css/yui.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>

	<script language="javascript">
		function disableUsers() {
			$("#mainForm").submit();
		}
	</script>
</head>

<body>
<div id="doc3" class="yui-t2">
<%@ include file="/common/header.jsp" %>
<div id="bd">
	<%@ include file="/common/left.jsp" %>
	<div id="yui-main">
		<div class="yui-b">
		<h1>综合演示用例</h1>
		<%if(SpringSecurityUtils.getCurrentUser()!=null){ %>
		<div>你好, 用户<%=SpringSecurityUtils.getCurrentUserName()%>在<%=((OperatorDetails)SpringSecurityUtils.getCurrentUser()).getLoginTime()%>从<%=SpringSecurityUtils.getCurrentUserIp()%>登录.&nbsp;&nbsp;</div>
		<%} %>
		<div>
			<form id="mainForm" action="user!disableUsers.action" method="post">
				<table id="contentTable">
					<tr>
						<th>&nbsp;</th>
						<th>登录名</th>
						<th>姓名</th>
						<th>电邮</th>
						<th>角色</th>
						<th>状态</th>
						<th>操作</th>
					</tr>

					<s:iterator value="allUserList">
						<tr>
							<td><input type="checkbox" name="checkedUserIds" value="${id}"/></td>
							<td>${loginName}&nbsp;</td>
							<td>${name}&nbsp;</td>
							<td>${email}&nbsp;</td>
							<td>${roleNames}&nbsp;</td>
							<td>${status}&nbsp;</td>
							<td><a href="user/${id}.htm" id="editLink-${id}">修改</a></td>
						</tr>
					</s:iterator>
				</table>
				<input type="submit" value="暂停选中用户"/>
			</form>
		</div>
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>
