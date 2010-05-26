<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="common/taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />
<link rel="shortcut icon" href="img/icon/extjs.ico" />

<script type="text/javascript">
      // Deployment type: Production(PROD) or development(DEV). In development mod does not cache
      CFG_DEPLOYMENT_TYPE = 'DEV';
      //javascript
      CFG_PATH_EXTJS = 'ext';
      CFG_PATH_JSLIB = 'js/lib';
      CFG_PATH_ICONS = 'img';

      document.writeln('<link rel="stylesheet" type="text/css" href="'+CFG_PATH_EXTJS+'/resources/css/ext-all.css" \/>');
      document.writeln('<script type="text/javascript" src="'+CFG_PATH_EXTJS+'/ext-base.js" ><\/script>');
      document.writeln('<script type="text/javascript" src="'+CFG_PATH_EXTJS+'/ext-all.js" ><\/script>');
      document.writeln('<script type="text/javascript" src="'+CFG_PATH_EXTJS+'/ext-lang-zh_CN.js" ><\/script>');
</script>

<script type="text/javascript"  src="./app/Api.js"  ></script>
<script type="text/javascript"  src="./js/labels_srv.js" ></script>
<link rel="stylesheet" href="<c:url value='css/n21ebs.css'/>" type="text/css"  />

<script type="text/javascript">
      // Product version. Do not change!
      CFG_PRODUCT_VERSION = L("/Application/Version");
      CFG_DEFAULT_LANGUAGE = 'en';
      CFG_AUTHSERVER_URL = 'auth.htm';
      CFG_CLIENT_URL =  'js/app';
      CFG_EXT_3RD = 'js/3rdparty';

</script>
<script type="text/javascript" src="./js/app/UiLoadingHelper.js"></script>
<script type="text/javascript" src="./DcIncludesMap.js"></script>

<link rel="stylesheet" type="text/css" href="css/loading.css" />
<script>
      document.write('<title>' + L("/Application/Name")+'-'+ CFG_PRODUCT_VERSION + '</title>');
</script>
</head>
<body>
<div id="n21-loading-mask" style=""></div>
<div id="n21-loading">
<div class="n21-loading-indicator">
<span id="n21-loading-logo">
<script>
      s=L("/Application/Name")+'-'+ CFG_PRODUCT_VERSION;

      Ext.Direct.addProvider(Ext.appsys.REMOTING_API);
      AppSysAction.doEcho('', function(result, e){
          var t = e.getTransaction();
          if(e.status){
              s=result;
              //alert('test' + s);
          }else{
          }

      });
      document.write(s);
</script>
</span><br>
<span id="n21-loading-logo-text"></span> <br>
<img src="img/icon/extanim32.gif" /> <br>
<span id="n21-loading-msg">装载中...</span></div>
</div>

<div id="west"></div>
<div id="north" style="height: 32"></div>
<div id="south" style="margin: 0; padding: 0;"></div>
<div style="width: 100%; height: 100%; overflow: hidden;" id="content">
<iframe id="content_iframe" src=""
  style="border: 0; width: 100%; height: 100%; overflow-y: hidden; margin: 0; padding: 0;"
  FRAMEBORDER="no"></iframe></div>

<script type="text/javascript">document.getElementById('n21-loading-msg').innerHTML = '装载资源...';</script>
<script type="text/javascript" src="<c:url value='js/n21ExtjsLib/lib.js'/>"></script>
<script type="text/javascript" src="<c:url value='js/app/DcLogin.js'/>"></script>
<script type="text/javascript" src="<c:url value='js/app/DcMenuTree.js'/>"></script>
<script>
    ///document.writeln('<link rel="stylesheet" type="text/css" href="'+CFG_PATH_EXTJS+'/resources/css/ext-all.css"/>');
    //document.writeln('<link rel="stylesheet" type="text/css" href="css/n21ebs.css"/>');
    document.writeln('<link rel="stylesheet" type="text/css" href="'+'css/xtheme-nbs.css"/>');
</script>
<script type="text/javascript">document.getElementById('n21-loading-msg').innerHTML = '初始化...';</script>

<script type="text/javascript" src="./main.js"></script>

<script>
</script>

</body>
</html>