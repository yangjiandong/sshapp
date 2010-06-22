<%@ page contentType="text/html;charset=UTF-8"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <title>登陆</title>
    <style type="text/css">
div.error {
    width: 260px;
    border: 2px solid red;
    background-color: yellow;
    text-align: center;
}

div.hide {
    display: none;
}
    </style>
  </head>
  <body>
    <div class="error ${param.error == true ? '' : 'hide'}">
      登陆失败<br>
      ${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}
    </div>
    <form action="${pageContext.request.contextPath}/j_spring_security_check" method="post" style="width:260px;text-align:center;">
      <fieldset>
        <legend>登陆</legend>
        用户： <input type="text" name="j_username" style="width:150px;" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}"/><br />
        密码： <input type="password" name="j_password" style="width:150px;" /><br />
        <input type="checkbox" name="_spring_security_remember_me" />两周之内不必登陆<br />
        <input type="submit" value="登陆"/>
        <input type="reset" value="重置"/>
      </fieldset>
    </form>
  </body>
</html>
