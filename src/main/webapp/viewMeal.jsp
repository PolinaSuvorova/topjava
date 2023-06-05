<%@ page import="ru.javawebinar.topjava.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="css/style.css">
  <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
  <title>Просмотр записи приёма пищи</title>
</head>
<body>

<section>
  <a href="meals?id=${meal.id}&action=edit">Редактировать</a><br>
  <p>Наименование : ${meal.description}<br>
  <p>Дата время   : <%=DateUtil.outputDateTime(meal.getDateTime())%> <br>
  <p>Калории      :  ${meal.calories} <br>
  <br>
  <button onclick="window.history.back()">ОК</button>
</section>
</body>
</html>
