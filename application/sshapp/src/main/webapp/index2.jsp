<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="common/taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />
<link rel="shortcut icon" href="resources/img/icon/extjs.ico" />
</head>

<body>
<a href="<c:url value="/init/exampleData"/>">初始数据dd</a>
<br><br>

  <FORM METHOD=POST ACTION="user/login">
    <INPUT TYPE="text" NAME="loginName" value="登录名"><br><br>
    <INPUT TYPE="text" NAME="plainPassword" value="密码"><br><br>
    <INPUT TYPE="submit">
 <br>
 <A HREF="book/add" target="_blank" >add</A>
      <br>
    <A HREF="book/1234567" target="_blank">id:1234567</A>
 </FORM>

<br><br>

<FORM METHOD=POST ACTION="book/getBookByTitle">
    <INPUT TYPE="text" NAME="title" value="请输入book 's Title"><br><br>

    <INPUT TYPE="submit"  value=" test book ">
 <br>
</FORM>

<FORM METHOD=POST ACTION="book/getBooksBySql">
    <INPUT TYPE="text" NAME="title" value="请输入book 's Title"><br><br>

    <INPUT TYPE="submit"  value=" getBooksBySql ">
 <br>
</FORM>

<FORM METHOD=POST ACTION="book/getContactByProjections">
    <INPUT TYPE="text" NAME="name" value="请输入Contact 's Name"><br><br>

    <INPUT TYPE="submit"  value=" getContactByProjections ">
 <br>
</FORM>
<FORM METHOD=POST ACTION="book/getContactByDetachedCriteria">
    <INPUT TYPE="text" NAME="name" value="请输入Contact 's Name"><br><br>

    <INPUT TYPE="submit"  value=" 列出大于平均值的记录 ">
 <br>
</FORM>

<FORM METHOD=POST ACTION="book/getContactByDetachedCriteria2">
    <INPUT TYPE="text" NAME="name" value="请输入Contact 's Name"><br><br>

    <INPUT TYPE="submit"  value=" getContactByDetachedCriteria2. ">
 <br>
</FORM>
<FORM METHOD=POST ACTION="book/getContactByNaturalId">
    <INPUT TYPE="text" NAME="name" value="请输入Contact 's Name"><br><br>

    <INPUT TYPE="submit"  value=" 采用naturalid提高缓存效果 ">
 <br>
</FORM>
<FORM METHOD=POST ACTION="book/getContactBySql">
    <INPUT TYPE="text" NAME="name" value="请输入Contact 's Name"><br><br>

    <INPUT TYPE="submit"  value=" 采用原生的sql ">
 <br>
</FORM>

</body>
</html>