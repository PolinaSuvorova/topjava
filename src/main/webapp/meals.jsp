<%--
  Created by IntelliJ IDEA.
  User: polin
  Date: 03.06.2023
  Time: 11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ru.javawebinar.topjava.util.DateUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список приёмов еды</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h1>Ведение калорий за день</h1>
<p><a href="meals?action=edit">Добавить</a></p>
<table border="1" cellpadding="8" cellspacing>
    <tr>
        <th>Дата</th>
        <th>Название</th>
        <th>Калории</th>
        <th></th>
        <th></th>
    </tr>

    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <c:set var="linecolor" value="${meal.excess ? '#ffc0cb' : '#7fff00'}"/>
        <tr bgcolor=${linecolor}>
            <td><%=DateUtil.outputDateTime(meal.getDateTime())%></td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?id=${meal.id}&action=delete">Удалить</a></td>
            <td><a href="meals?id=${meal.id}&action=edit">Редактировать</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>