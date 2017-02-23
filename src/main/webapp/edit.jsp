<%--
  Created by IntelliJ IDEA.
  User: olymp
  Date: 23.02.2017
  Time: 20:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
    <form action="/list" method="post">
        <input type="text" name="uuid" style = "display:none;" value="${uuid}">
        <table>
            <tr>
                <td>Логин</td>
                <td><input type="text" name="login" value="${login}"></td>
            </tr>
            <tr>
                <td>Пароль</td>
                <td><input type="text" name="password" value="${password}"></td>
            </tr>
            <tr>
                <td>Роль</td>
                <td><input type="number" name="role" value="${role}"></td>
            </tr>
            <tr>
                <td>Электронная почта</td>
                <td><input type="text" name="email" value="${email}"></td>
            </tr>
            <tr>
                <td>Отображаемое имя</td>
                <td><input type="text" name="visible_name" value="${visible_name}"></td>
            </tr>
        </table>
        <input type="submit" value="Изменить">
    </form>
</body>
</html>
