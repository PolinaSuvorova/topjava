<%--
  Created by IntelliJ IDEA.
  User: polin
  Date: 03.06.2023
  Time: 18:51
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
</head>
<body>

<section>
    <c:if test="${validate != null}">
        <c:forEach var="valueerror" items="${validate}">
            <p style="background-color:tomato;">${valueerror}.</p>
        </c:forEach>
    </c:if>
    <form name='theForm' method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value="<%=meal.getId()%>">
        <h1>Наименование</h1>
        <dl>
            <input type="text"
                   name="description"
                   required="true"
                   size=55
                   value="<%=meal.getDescription()%>">
        </dl>
        <h2>Дата время</h2>
        <dl>
            <input type="text"
                   required="true"
                   name="datetime" size=15
                   value="<%=DateUtil.outputDateTime(meal.getDateTime())%>"
                   placeholder="<%=DateUtil.DATE_PATTERN%>">
        </dl>
        <h2>Калории</h2>
        <dl>
            <input type="number"
                   required="true"
                   name="calories"
                   size=5
                   value="<%=meal.getCalories()%>">
        </dl>
        <hr>
        <button type="button" name="action" value="save" onclick='setVal("save")'>Сохранить</button>
        <button type="button" onclick="window.history.go(-1)" name="action" value="back">Отменить</button>
        <input type="hidden" name='status'>
    </form>
</section>
</body>
</html>
<script>
    function setVal(buttonCmd) {
        document.forms['theForm'].status.value = buttonCmd;
        document.forms['theForm'].submit();
        return false;
    }
</script>