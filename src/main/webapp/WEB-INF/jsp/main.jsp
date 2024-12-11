<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="header.forumTitle"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding-top: 56px;
        }

        .banner {
            background-color: #f8f9fa;
            padding: 60px 0;
            text-align: center;
        }

        .footer {
            background-color: #343a40;
            color: white;
            padding: 20px 0;
            margin-top: 40px;
        }

        .category-card {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<c:if test="${not empty sessionScope.successMessage}">
    <div class="alert alert-success text-center" role="alert">
        <fmt:message key="message.successRegistration"/>
    </div>
    <c:remove var="successMessage" scope="session"/>
</c:if>

<div class="banner">
    <div class="container">
        <h1 class="display-4"><fmt:message key="main.banner.title"/></h1>
        <p class="lead"><fmt:message key="main.banner.desc"/></p>
        <a href="#" class="btn btn-primary btn-lg"><fmt:message key="main.startNow"/></a>
    </div>
</div>

<div class="container my-5">
    <h2 class="mb-4"><fmt:message key="main.categories"/></h2>
    <div class="row">
        <div class="col-md-4">
            <div class="card category-card">
                <div class="card-body">
                    <h5 class="card-title"><fmt:message key="main.category.technology"/></h5>
                    <p class="card-text"><fmt:message key="main.category.technology.desc"/></p>
                    <a href="#" class="btn btn-primary"><fmt:message key="main.button.go"/></a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card category-card">
                <div class="card-body">
                    <h5 class="card-title"><fmt:message key="main.category.travel"/></h5>
                    <p class="card-text"><fmt:message key="main.category.travel.desc"/></p>
                    <a href="#" class="btn btn-primary"><fmt:message key="main.button.go"/></a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card category-card">
                <div class="card-body">
                    <h5 class="card-title"><fmt:message key="main.category.sport"/></h5>
                    <p class="card-text"><fmt:message key="main.category.sport.desc"/></p>
                    <a href="#" class="btn btn-primary"><fmt:message key="main.button.go"/></a>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="container my-5">
    <h2 class="mb-4"Тесты</h2>
    <div class="list-group">
        <a href="${pageContext.request.contextPath}/topics" class="list-group-item list-group-item-action">Список топиков</a>
        <a href="${pageContext.request.contextPath}/create_topic" class="list-group-item list-group-item-action">Создать новый топик</a>
        <a href="${pageContext.request.contextPath}/topic/1" class="list-group-item list-group-item-action">Просмотр топика с ID 1</a>
    </div>
</div>


<footer class="footer">
    <div class="container text-center">
        <p><fmt:message key="footer.copyright"/>.
            <fmt:message key="footer.rights"/></p>
        <p>
            <a href="#" class="text-white me-2"><fmt:message key="footer.privacy"/></a>
            |
            <a href="#" class="text-white ms-2"><fmt:message key="footer.terms"/></a>
        </p>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
