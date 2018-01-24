<%@ page isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/resources/CSS.css">
    <title>Main Page</title>
</head>
<body>

<div class="center">
    <div class="form">
        <form>
            <input class="mainPageButtons" type="button" value="Login"
                   onclick="window.location.href='${pageContext.servletContext.contextPath}/login'">
            <input class="mainPageButtons" type="button" value="Registration"
                   onclick="window.location.href='${pageContext.servletContext.contextPath}/registration'">
            <input class="mainPageButtons" value="About" type="button"
                   onclick="window.location.href='${pageContext.servletContext.contextPath}/about'">
        </form>
    </div>
</div>

</body>
</html>
