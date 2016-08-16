<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  	<form action="list.action" method="post">
  		<table align="left" width="80%" style="padding-left:140px;">
  		<tr><td>
  		书名:<input type="text" name="book.name" value="${book.name }"/>
  		类型:<select name="book.category.id">
  			<option value="0">请选择</option>
  			<c:forEach items="${categoryList }" var="bean">
  				<option value="${bean.id }">${bean.name }</option>
  			</c:forEach>
  		</select>
  		<input type="submit" value="查询"/>
  		</td>
  		</tr>
  		</table>
  	</form>
   <table width="80%" align="center">
   <tr>
   <td>编号</td>
   <td>名称</td>
   <td>作者</td>
    <td>价格</td>
   <td>类型</td>
   <td>日期</td>
   </tr>
   <c:forEach items="${bookList }" var="bean">
    <tr>
   <td>${bean.id }</td>
   <td>${bean.name }</td>
   <td>${bean.author }</td>
   <td>${bean.price }</td>
   <td>${bean.category.name }</td>
   <td>${bean.pubDate }</td>
   </tr>
   </c:forEach>
   </table>
  </body>
</html>
