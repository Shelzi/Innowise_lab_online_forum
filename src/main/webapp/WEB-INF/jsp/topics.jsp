<%@ include file="/WEB-INF/jsp/header.jsp" %>
<%@ taglib uri="https://example.com/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="${sessionScope.currentLocale.language}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="topics.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container my-5">
    <h1 class="mb-4"><fmt:message key="topics.heading"/></h1>

    <c:if test="${not empty currentCategory}">
        <div class="alert alert-info">
            <strong><fmt:message key="topics.filter"/>:</strong> ${currentCategory}
            <a href="${pageContext.request.contextPath}/topics" class="btn btn-sm btn-secondary ms-3">
                <fmt:message key="topics.reset"/>
            </a>
        </div>
    </c:if>

    <c:if test="${empty topicList}">
        <div class="alert alert-warning"><fmt:message key="topics.noTopics"/></div>
    </c:if>

    <c:forEach var="topic" items="${topicList}" varStatus="loop">
        <div class="card mb-3">
            <div class="card-body">
                <h5 class="card-title">
                    <a href="${pageContext.request.contextPath}/topic/${topic.id}">
                            ${topic.title}
                    </a>
                </h5>
                <h6 class="card-subtitle mb-2 text-muted">
                    <fmt:message key="topics.author"/>: ${topic.user.username} |
                    <fmt:message key="topics.createdAt"/>: ${fn:formatLocalDateTime(topic.createdAt, 'dd-MM-yyyy HH:mm:ss')} |
                    <fmt:message key="topics.rating"/>: ${topic.rating}
                </h6>
                <p class="card-text">${topic.body}</p>
                <a href="${pageContext.request.contextPath}/topic/${topic.id}" class="card-link">
                    <fmt:message key="topics.view"/>
                </a>

                <div class="mt-2">
                    <a href="${pageContext.request.contextPath}/topic/${topic.id}/like"
                       class="btn btn-sm btn-outline-success">
                        <fmt:message key="topics.like"/>
                    </a>
                    <a href="${pageContext.request.contextPath}/topic/${topic.id}/dislike"
                       class="btn btn-sm btn-outline-danger">
                        <fmt:message key="topics.dislike"/>
                    </a>

                    <c:if test="${sessionScope.currentRole == 'ADMIN'}">
                        <a href="${pageContext.request.contextPath}/admin/pin_topic/${topic.id}"
                           class="btn btn-sm btn-outline-primary ms-2">
                            <fmt:message key="topics.pin"/>
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/delete_topic/${topic.id}"
                           class="btn btn-sm btn-outline-danger ms-2">
                            <fmt:message key="topics.delete"/>
                        </a>
                    </c:if>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<footer class="footer">
    <div class="container text-center">
        <p>&copy; <fmt:message key="footer.copyright"/>
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
