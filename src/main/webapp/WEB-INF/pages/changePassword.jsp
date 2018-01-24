<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Change Password</title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/CSS.css" type="text/css">
</head>
<body>

<div class="center">
    <div class="form">
        <form:form method="post" action="changePassword" modelAttribute="POJO">
            <input type="password" placeholder="Current Password" name="currentPassword"/>
            <c:if test="${changeError!=null}">
                <span class="error">${changeError}</span>
            </c:if>
            <input type="password" placeholder="New Password" name="newPassword1"/>
            <input type="password" placeholder="Retype New Password" name="newPassword2"/>
            <c:if test="${equalsError!=null}">
                <span class="error">${equalsError}</span>
            </c:if>
            <form:errors path="newPassword2" cssClass="error"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <button>login</button>
            <p class="message">Forgot password? <a href="${pageContext.servletContext.contextPath}/restorePassword">Restore password</a></p>
        </form:form>
    </div>
</div>

</body>
</html>
