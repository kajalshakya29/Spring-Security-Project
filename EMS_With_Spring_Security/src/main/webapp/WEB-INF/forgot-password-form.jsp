<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Forgot Password</title></head>
<body>
    <h2>Forgot Password</h2>

    <c:if test="${not empty error}">
        <div style="color: red;">${error}</div>
    </c:if>
    <c:if test="${not empty message}">
        <div style="color: green;">${message}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/forgot-password" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <label>Enter Registered Email:</label>
        <input type="email" name="email" required />
        <br><br>
        <button type="submit">Send Reset Link</button>
    </form>

    <p><a href="${pageContext.request.contextPath}/login">Back to Login</a></p>
</body>
</html>