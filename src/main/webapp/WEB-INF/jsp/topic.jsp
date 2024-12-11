<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="locale.messages"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${topic.title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding-top: 56px;
        }

        .reply {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="container my-5">
    <h1 class="mb-4">${topic.title}</h1>
    <div class="mb-3">
        <div class="text-muted">
            <fmt:message key="topic.author"/>: ${topic.user.username},
            <fmt:message key="topic.createdAt"/>: ${topic.createdAt}
        </div>
        <p>${topic.body}</p>
        <span class="badge bg-secondary"><fmt:message key="topic.rating"/>: ${topic.rating}</span>
    </div>
    <hr/>

    <h2 class="h5 mb-3"><fmt:message key="topic.replies"/></h2>
    <c:if test="${empty topic.replies}">
        <div class="alert alert-info">No replies yet.</div>
    </c:if>
    <c:forEach var="reply" items="${topic.replies}">
        <div class="reply border p-3 rounded">
            <div class="d-flex justify-content-between align-items-center mb-2">
                <div class="text-muted small">
                        ${reply.user.username}, ${reply.createdAt}
                </div>
                <span class="badge bg-secondary">${reply.rating}</span>
            </div>
            <p class="mb-0">${reply.body}</p>
        </div>
    </c:forEach>

    <c:if test="${not empty sessionScope.user}">
        <hr/>
        <form method="POST" action="${pageContext.request.contextPath}/topic/${topic.id}/reply">
            <div class="mb-3">
                <label for="replyBody" class="form-label"><fmt:message key="topic.reply.form.body"/></label>
                <textarea class="form-control" id="replyBody" name="body" rows="3" required></textarea>
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="topic.reply.button"/></button>
        </form>
    </c:if>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
