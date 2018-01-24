<%--
  Created by IntelliJ IDEA.
  User: VladimirPC
  Date: 08.11.2017
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Restore password</title>
    <link type="text/css" href="${pageContext.servletContext.contextPath}/resources/CSS.css" rel="stylesheet">
</head>
<body>

<div class="center">
    <div class="form">
        <form method="post">
            <input type="text" placeholder="Email address" name="email"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <button>Restore password</button>
            <p class="message">Not registered? <a href="${pageContext.servletContext.contextPath}/registration">Sign up</a></p>
        </form>
    </div>
</div>

</body>
</html>
