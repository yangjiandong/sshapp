<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="common/taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />
<link rel="shortcut icon" href="img/icon/extjs.ico" />
</head>

<body>
<a href="<c:url value="/initData.do"/>">初始数据</a>
<br><br>

  <FORM METHOD=POST ACTION="user/login.do">
    <INPUT TYPE="text" NAME="loginName" value="登录名"><br><br>
    <INPUT TYPE="text" NAME="plainPassword" value="密码"><br><br>
    <INPUT TYPE="submit">
 <br>
 <A HREF="topic/add.do" target="_blank" >add</A>
      <br>
    <A HREF="topic/1234567.do" target="_blank">id:1234567</A>
 </FORM>
</body>
</html>