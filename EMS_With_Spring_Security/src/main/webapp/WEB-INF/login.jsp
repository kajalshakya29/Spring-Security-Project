<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h2>Login Page</h2>
<c:if test="${not empty sessionScope.registrationSuccess}">
        <div style="color: green; font-weight: bold; margin-bottom: 15px;">
            Registration Successful! Please login.
        </div>
        <c:remove var="registrationSuccess" scope="session"/>
    </c:if>
    <c:if test="${not empty error}">
            <div style="color: red; font-weight: bold; margin-bottom: 15px;">
                Error: ${error}
            </div>
        </c:if>
        <c:if test="${not empty message}">
            <div style="color: green; font-weight: bold; margin-bottom: 15px;">
                ${message}
            </div>
        </c:if>
    <form action="${pageContext.request.contextPath}/perform_login" method="post">

        <security:csrfInput/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div>
            <label>Username:</label>
            <input type="text" name="username" required />
        </div>
        <div>
            <label>Password:</label>
            <input type="password" name="password" required />
        </div>
        <p style="margin-top: 5px;">
                   <a href="${pageContext.request.contextPath}/forgot-password">Forgot Password?</a>
                </p>
        <div>
            <label><input type="checkbox" name="remember-me" /> Remember Me</label>
        </div>
        <button type="submit">Login</button>
    </form>

    <p style="margin-top: 15px;">
       To create a new account <a href="${pageContext.request.contextPath}/user-register">Register here</a>
    </p>

    </body>
</html>