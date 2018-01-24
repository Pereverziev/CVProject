<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Restore password</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/CSS.css">
</head>
<body>

<div class="center">
    <div class="form">
        <form:form method="post" modelAttribute="POJO" action="">
            <input type="password" placeholder="New Password" name="password1"/>
            <input type="password" placeholder="Retype new password" name="password2"/>
            <form:errors path="password2" cssClass="error"/>
            <c:if test="${equalError!=null}">
                <span class="error">${equalError}</span>
            </c:if>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <button>Set new password</button>
        </form:form>
    </div>
</div>

</body>
</html>
