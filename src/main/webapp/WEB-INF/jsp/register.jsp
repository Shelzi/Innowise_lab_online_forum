<%@ include file="/WEB-INF/jsp/header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="registration.title"/></title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
          crossorigin="anonymous">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h4 class="mb-0"><fmt:message key="registration.title"/></h4>
                </div>
                <div class="card-body">
                    <c:if test="${not empty errorInputData}">
                        <div class="alert alert-danger" role="alert">
                                ${errorInputData}
                        </div>
                    </c:if>

                    <form name="registrationForm" method="POST" action="register.do">
                        <div class="mb-3">
                            <label for="userName" class="form-label"><fmt:message key="registration.username"/></label>
                            <input type="text" class="form-control" id="userName" name="userName" value="${userName}"
                                   required>
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label"><fmt:message key="registration.email"/></label>
                            <input type="email" class="form-control" id="email" name="email" value="${email}" required>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label"><fmt:message key="registration.password"/></label>
                            <input type="password" class="form-control" id="password" name="password"
                                   value="${password}" required>
                        </div>

                        <div class="mb-3">
                            <label for="repeatedPassword" class="form-label"><fmt:message
                                    key="registration.repeatPassword"/></label>
                            <input type="password" class="form-control" id="repeatedPassword" name="repeatedPassword"
                                   value="${repeatedPassword}" required>
                        </div>

                        <div class="d-flex justify-content-between">
                            <button type="submit" class="btn btn-primary"><fmt:message
                                    key="registration.registerButton"/></button>
                            <a href="${pageContext.request.contextPath}/login" class="btn btn-secondary">
                                <fmt:message key="registration.backToLogin"/>
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

</body>
</html>
