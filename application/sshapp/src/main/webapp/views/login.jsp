<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

  <head>
    <title>Spring Security + ExtJS Login</title>

<script type="text/javascript">
      //javascript
      CFG_PATH_EXTJS = 'ext';

      document.writeln('<link rel="stylesheet" type="text/css" href="'+CFG_PATH_EXTJS+'/resources/css/ext-all.css" \/>');
      document.writeln('<script type="text/javascript" src="'+CFG_PATH_EXTJS+'/ext-base.js" ><\/script>');
      document.writeln('<script type="text/javascript" src="'+CFG_PATH_EXTJS+'/ext-all.js" ><\/script>');
      document.writeln('<script type="text/javascript" src="'+CFG_PATH_EXTJS+'/ext-lang-zh_CN.js" ><\/script>');
</script>
  <!-- login form -->
  <script src="<c:url value='js/login.js'/>"></script>

  </head>

  <body>
  <br><br><br><br><br>
    <center>
      <div id="login"></div>
    </center>
  </body>
</html>
