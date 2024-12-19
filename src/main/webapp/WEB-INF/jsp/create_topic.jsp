<%@ include file="/WEB-INF/jsp/header.jsp" %>
<!DOCTYPE html>
<html lang="${sessionScope.currentLocale.language}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="createTopic.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<div class="container my-5">
    <h1 class="mb-4"><fmt:message key="createTopic.heading"/></h1>
    <c:if test="${not empty errorInputData}">
        <div class="alert alert-danger" role="alert">
            <fmt:message key="${errorInputData}"/>
        </div>
    </c:if>
    <form method="POST" action="${pageContext.request.contextPath}/create_topic.do">
        <div class="mb-3">
            <label for="title" class="form-label"><fmt:message key="createTopic.label.title"/></label>
            <input type="text" class="form-control" id="title" name="title" value="${param.TOPIC_TITLE}" required>
        </div>
        <div class="mb-3">
            <label for="body" class="form-label"><fmt:message key="createTopic.label.body"/></label>
            <textarea class="form-control" id="body" name="body" rows="5" required>${param.TOPIC_BODY}</textarea>
        </div>
        <div class="mb-3">
            <label for="category" class="form-label"><fmt:message key="createTopic.label.category"/></label>
            <select class="form-select" id="category" name="category" required>
                <option value=""><fmt:message key="createTopic.option.selectCategory"/></option>
                <c:forEach var="category" items="${categories}">
                    <option value="${category}" <c:if test="${param.TOPIC_CATEGORY == category}">selected</c:if>>${category}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary"><fmt:message key="createTopic.button.create"/></button>
        <a href="${pageContext.request.contextPath}/topics.do" class="btn btn-secondary"><fmt:message key="createTopic.button.cancel"/></a>
    </form>
</div>
<footer class="footer">
    <div class="container text-center">
        <p>&copy; 2024 <fmt:message key="footer.organization"/></p>
        <p>
            <a href="#" class="text-dark me-2"><fmt:message key="footer.privacy"/></a> |
            <a href="#" class="text-dark ms-2"><fmt:message key="footer.terms"/></a>
        </p>
    </div>
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
