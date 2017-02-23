<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List</title>
</head>
<body>
<table border="1">
    <caption>Список пользователей</caption>
    <tr><th>Отображаемое имя</th>
    <th>Логин</th>
        <th>Электронная почта</th><th></th><th></th></tr>
    <c:forEach items="${userlist}" var="item">
        <tr>
            <td><c:out value="${item.getVisible_name()}"/></td>
            <td><c:out value="${item.getLogin()}"/></td>
            <td><c:out value="${item.getEmail()}"/></td>
            <td><form id = "<c:out value="${item.getUser_uuid()}"/>" action="./edit" method = "post" style = "display:none;">
                <input name="uuid" value="<c:out value="${item.getUser_uuid()}"/>">
            </form>
                <div onclick = 'document.getElementById("<c:out value="${item.getUser_uuid()}"/>").submit();'>--Редактировать--</div></td>
            <td><form id = "<c:out value="${item.getUser_uuid()}"/>del" action="./list" method = "post" style = "display:none;">
                <input name="uuid" value="<c:out value="${item.getUser_uuid()}"/>">
                <input name="delete" value="delete">
            </form>
                <div onclick = 'document.getElementById("<c:out value="${item.getUser_uuid()}"/>del").submit();'>--Удалить--</div></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
