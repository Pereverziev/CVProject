<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/CSS.css">
</head>
<body>

 <div class="center">
    <div class="form">
        <form method="post">
            <input type="text" placeholder="Login or Email" name="username" value="${loginVal}"/>
            <input type="password" placeholder="Password" name="password"/>
            <c:if test="${loginError!=null}">
                <span class="error">${loginError}</span>
            </c:if>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <button>login</button>
            <p class="message">Not registered? <a href="${pageContext.servletContext.contextPath}/registration">Create an account</a></p>
            <p class="message">Forgot password? <a href="${pageContext.servletContext.contextPath}/restorePassword">Restore password</a></p>
        </form>
    </div>
</div>

</body>

</html>