<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: olymp
  Date: 23.02.2017
  Time: 9:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
  <title>Login</title>
</head>
<body>
<div>

  <form action="/" method="post">
    <label for="login">Login:</label>
    <input type="text" name="login" id="login" value=<c:choose>
    <c:when test="${login!=null}">
        <%="\""+request.getAttribute("login")+"\""%>
    </c:when>
      <c:otherwise>""</c:otherwise>
    </c:choose>
            placeholder="Input"><br>
    <label for="password">Password:</label>
    <input type="password" name="password" id="password" value="" placeholder="Input">
    <input type="submit" value="Submit">
  </form>
  <c:if test="${auth!=null&&!auth}"><p>Неверный логин или пароль</p></c:if>
</div>
<a href="/registration/"></a>
</body>
</html>
