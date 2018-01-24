<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/CSS.css">
</head>

<body>

<div class="center">
    <div class="form">
        <form:form method="post" modelAttribute="account" action="registration">
            <input type="text" placeholder="Login" name="login" value="${account.getLogin()}"/>
            <form:errors path="login" cssClass="error"/>
            <input type="password" placeholder="Password" name="password"/>
            <form:errors path="password" cssClass="error"/>
            <input type="text" placeholder="Email" name="email" value="${account.getEmail()}">
            <form:errors path="email" cssClass="error" cssStyle="margin-bottom: 15px"/>
            <c:choose>
                <c:when test="${existingError!=null}">
                    <span class="error">${existingError}</span>
                </c:when>
            </c:choose>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <button>Register</button>
            <p class="message">Already registered? <a href="${pageContext.servletContext.contextPath}/login">Log in</a></p>
        </form:form>
    </div>
</div>


</body>

</html>
