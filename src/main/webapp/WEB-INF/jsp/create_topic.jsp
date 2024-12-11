<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="locale.messages"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="topic.create.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding-top: 56px;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="container my-5">
    <h1 class="h3 mb-4"><fmt:message key="topic.create.title"/></h1>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <form method="POST" action="${pageContext.request.contextPath}/create_topic">
        <div class="mb-3">
            <label for="title" class="form-label"><fmt:message key="topic.create.form.title"/></label>
            <input type="text" class="form-control" id="title" name="title" required maxlength="255">
        </div>

        <div class="mb-3">
            <label for="body" class="form-label"><fmt:message key="topic.create.form.body"/></label>
            <textarea class="form-control" id="body" name="body" rows="5" required></textarea>
        </div>

        <div class="mb-3">
            <label for="category" class="form-label"><fmt:message key="topic.create.form.category"/></label>
            <select class="form-select" id="category" name="category">
                <option value="">(Optional)</option>
                <option value="technology"><fmt:message key="main.category.technology"/></option>
                <option value="travel"><fmt:message key="main.category.travel"/></option>
                <option value="sport"><fmt:message key="main.category.sport"/></option>
            </select>
        </div>

        <button type="submit" class="btn btn-primary"><fmt:message key="topic.create.button"/></button>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>