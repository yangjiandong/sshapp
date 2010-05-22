<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>WebService高级演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
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
		<h1>WS-*高级协议演示</h1>

		<h2>技术说明:</h2>
		<ul>
			<li>WS-Security认证机制: 使用符合WS互操作规范的标准认证机制, 演示Plain与Digest式的密码认证.</li>
			<li>二进制数据传输机制: 演示直接Base64Binary编码字节数组 与 MTOM附件传输数据流.</li>
		</ul>
		
		<h2>用户故事:</h2>
		<ul>
			<li>Plain式密码, 传输时安全度较低, 但不要求服务端必须保存有客户密码的明文.<br/>
				WSDL: <a href="http://localhost:8080/showcase/services/UserServiceWithPlainPassword?wsdl">http://localhost:8080/showcase/services/UserServiceWithPlainPassword?wsdl</a>
			</li>
			<li>Digest式密码, 传输时较为安全, 但要求服务端必须保存有密码的明文.<br/>
				WSDL: <a href="http://localhost:8080/showcase/services/UserServiceWithDigestPassword?wsdl">http://localhost:8080/showcase/services/UserServiceWithDigestPassword?wsdl</a>
			</li>
			<li>客户端的用户名与密码设定见测试用例.</li>
			<li>Base64Binary直接编码字节数组 , 一般用于传输较小的二进制文件.<br/>
				WSDL: <a href="http://localhost:8080/showcase/services/SmallImageService?wsdl">http://localhost:8080/showcase/services/SmallImageService?wsdl</a>
			</li>
			<li>MTOM附件传输数据流, 一般用于传输较大的二进制文件.<br/>
				WSDL: <a href="http://localhost:8080/showcase/services/LargeImageService?wsdl">http://localhost:8080/showcase/services/LargeImageService?wsdl</a>
			</li>
		</ul>
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>