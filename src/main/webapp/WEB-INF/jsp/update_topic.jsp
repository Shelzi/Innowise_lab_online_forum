<!-- /WEB-INF/jsp/update_topic.jsp -->
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<!DOCTYPE html>
<html lang="${sessionScope.currentLocale.language}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="updateTopic.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container my-5">
    <h1 class="mb-4"><fmt:message key="updateTopic.heading"/></h1>
    <c:if test="${not empty errorTopicUpdate}">
        <div class="alert alert-danger" role="alert">
            <fmt:message key="updateTopic.error.updateFailed"/>
        </div>
    </c:if>
    <form method="POST" action="${pageContext.request.contextPath}/update_topic.do">
        <input type="hidden" name="TOPIC_ID" value="${topic.id}">
        <div class="mb-3">
            <label for="title" class="form-label"><fmt:message key="updateTopic.label.title"/></label>
            <input type="text" class="form-control" id="title" name="TOPIC_TITLE" value="${topic.title}" required>
        </div>
        <div class="mb-3">
            <label for="body" class="form-label"><fmt:message key="updateTopic.label.body"/></label>
            <textarea class="form-control" id="body" name="TOPIC_BODY" rows="5" required>${topic.body}</textarea>
        </div>
        <div class="mb-3">
            <label for="category" class="form-label"><fmt:message key="updateTopic.label.category"/></label>
            <select class="form-select" id="category" name="TOPIC_CATEGORY" required>
                <option value=""><fmt:message key="updateTopic.option.selectCategory"/></option>
                <c:forEach var="category" items="${categories}">
                    <option value="${category}"
                            <c:if test="${topic.category == category}">selected</c:if>>${category}</option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-3 form-check">
            <input type="checkbox" class="form-check-input" id="pinned" name="TOPIC_PINNED"
                   <c:if test="${topic.pinned}">checked</c:if>>
            <label class="form-check-label" for="pinned"><fmt:message key="updateTopic.label.pinned"/></label>
        </div>
        <div class="mb-3">
            <label for="rating" class="form-label"><fmt:message key="updateTopic.label.rating"/></label>
            <input type="number" class="form-control" id="rating" name="TOPIC_RATING" value="${topic.rating}" min="0">
        </div>
        <button type="submit" class="btn btn-primary"><fmt:message key="updateTopic.button.update"/></button>
        <a href="${pageContext.request.contextPath}/topics.do" class="btn btn-secondary"><fmt:message
                key="updateTopic.button.cancel"/></a>
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
