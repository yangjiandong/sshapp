<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <title>CRUD ExtJS Grid</title>

  <!-- ExtJS css -->
  <link rel="stylesheet" type="text/css" href="../ext/resources/css/ext-all.css" />

  <!-- Row Editor plugin css -->
  <link rel="stylesheet" type="text/css" href="../ext/examples/ux/css/rowEditorCustom.css" />
  <link rel="stylesheet" type="text/css" href="../ext/examples/shared/examples.css" />
  <link rel="stylesheet" type="text/css" href="../ext/examples/ux/css/RowEditor.css" />

  <!-- App custom css -->
  <link rel="stylesheet" type="text/css" href="../css/crudgrid.css" />

  <!-- ExtJS js -->
  <script src="../ext/ext-base.js"></script>
  <script src="../ext/ext-all.js"></script>

  <!-- Row Editor plugin js -->
  <script src="../ext/examples/ux/RowEditor.js"></script>

  <!-- Excel DataGrid drag and drop plugin js -->
  <script src="../js/book/datadrop-plugin/Override.js"></script>
  <script src="../js/book/datadrop-plugin/Ext.ux.DataDrop.js"></script>

  <!-- App js -->
  <script src="../js/book/crud-grid.js"></script>

</head>
<body>
  <div id="crud-grid"></div>
</body>
</html>