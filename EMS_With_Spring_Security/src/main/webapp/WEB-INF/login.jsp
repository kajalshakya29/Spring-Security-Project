<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h2>Login Page</h2>

    <form action="${pageContext.request.contextPath}/perform_login" method="post">

        <%-- ***** CSRF TOKEN ADDED HERE ***** --%>
        <security:csrfInput/>
        <%-- Agar spring security tags use nahi karna chahti, toh neeche wala line use karein:
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> --%>

        <div>
            <label>Username:</label>
            <input type="text" name="username" required />
        </div>
        <div>
            <label>Password:</label>
            <input type="password" name="password" required />
        </div>
        <div>
            <label><input type="checkbox" name="remember-me" /> Remember Me</label>
        </div>
        <button type="submit">Login</button>
    </form>

    </body>
</html>