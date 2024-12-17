<%@ include file="/WEB-INF/jsp/header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="header.login"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
          crossorigin="anonymous">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp" />

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <h1 class="mb-4"><fmt:message key="header.login"/></h1>

            <c:if test="${not empty errorInputData}">
                <div class="alert alert-danger" role="alert">
                        ${errorInputData}
                </div>
            </c:if>

            <form name="loginForm" method="POST" action="login.do">
                <div class="mb-3">
                    <label for="login" class="form-label"><fmt:message key="header.login"/></label>
                    <input type="text" class="form-control" id="login" name="login" value=""/>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label"><fmt:message key="header.password"/></label>
                    <input type="password" class="form-control" id="password" name="password" value=""/>
                </div>

                <button type="submit" class="btn btn-primary"><fmt:message key="header.login"/></button>
            </form>

            <hr class="my-4">

            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/register" role="button">
                <fmt:message key="header.register"/>
            </a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>
