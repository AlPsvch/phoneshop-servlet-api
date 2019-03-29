<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>404 - Product Not Found Exception</title>
</head>
<body>
    <h1>404 - ProductNotFoundException</h1>
    <%
        out.println(exception.getMessage());
    %>
</body>
</html>
