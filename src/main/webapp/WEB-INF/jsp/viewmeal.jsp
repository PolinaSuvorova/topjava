<%--
  Created by IntelliJ IDEA.
  User: polin
  Date: 03.06.2023
  Time: 18:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="ru.javawebinar.topjava.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="../../css/style.css">
  <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo" scope="request"/>
  <title>Просмотр записи приёма пищи</title>
</head>
<body>

<section>
  <a href="meals?id=<%=meal.getId()%>&action=edit">Редактировать</a><br>
  <p>Наименование : <%=meal.getDescription()%> <br>
  <p>Дата время   : <%=DateUtil.outputDateTime(meal.getDateTime())%> <br>
  <p>Калории      :  <%=meal.getCalories()%> <br>
  <br>
  <button onclick="window.history.back()">ОК</button>
</section>
</body>
</html>
