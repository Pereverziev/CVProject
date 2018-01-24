<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<html>
<head>
    <title>Account Page</title>
</head>
<body>
<h2>Account Page</h2>

<a href="${pageContext.servletContext.contextPath}/changePassword">Change password</a> <br/>


<t:insertAttribute name="footer"/>

</body>
</html>
