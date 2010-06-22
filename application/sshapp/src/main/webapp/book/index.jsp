<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div>username : <sec:authentication property="name"/></div>
<hr>
<a href="admin.jsp">admin.jsp</a>
<a href="../j_spring_security_logout">logout</a>
