<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
</head>
<body>

<section>
    <h1></h1>
    <form name='theForm' method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value="${meal.id}">
        <c:set var="headerevent" value="${meal.id != null ? 'Редактировать' : 'Создать'}"/>
        <h1>${headerevent} калории</h1>

        <h2>Наименование</h2>
        <dl>
            <input type="text"
                   name="description"
                   required="true"
                   size=55
                   value="${meal.description}">
        </dl>
        <h2>Дата время</h2>
        <dl>
            <input type="datetime-local"
                   name="datetime"
                   value="${meal.dateTime}"
                   pattern="[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}">
        </dl>
        <h2>Калории</h2>
        <dl>
            <input type="number"
                   name="calories"
                   size=5
                   value="${meal.calories}">
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