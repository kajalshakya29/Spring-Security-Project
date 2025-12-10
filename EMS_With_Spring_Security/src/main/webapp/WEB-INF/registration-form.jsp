<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>User Registration</title>
</head>
<body>
    <h2>New User Registration</h2>
<c:if test="${not empty registrationError}">
    <div style="color: red; font-weight: bold; margin-bottom: 15px;">
        Error: ${registrationError}
    </div>
</c:if>
${pageContext.request.contextPath}/register
    <form:form action="${pageContext.request.contextPath}/register"
               method="POST"
               modelAttribute="user"
               enctype="multipart/form-data">

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <label>Username:</label>
        <form:input path="username" required="true" />
        <div style="color: red; font-size: 0.9em;">${usernameError}</div>
        <br><br>

        <label>Email:</label>
                <form:input path="email" type="email" required="true" />
                <div style="color: red; font-size: 0.9em;">${emailError}</div>
                <br><br>

        <label>Password:</label>
        <form:password path="password" required="true" />
        <div style="color: red; font-size: 0.9em;">${passwordError}</div>
        <br><br>
        <div style="margin-top: 0px; margin-left: 0px; font-size: 0.9em; color: red;">
                    <p>Password must contain:</p>
                    <ul>
                        <li>Min 8 and Max 20 characters.</li>
                        <li>Must contain at least one **letter** and one **digit**.</li>
                        <li>Must contain **exactly two** special characters.</li>
                        <li>Allowed Special Characters: **! @ # $ % ^ & * _ + - =**</li>
                    </ul>
                </div>
             <br><br>

        <label for="profilePicture">Profile Picture:</label>
            <input type="file" id="profilePicture" name="profilePicture" accept="image/*" />
            <br/>

        <input type="submit" value="Register here">
    </form:form>


    <p>
        If already have account **<a href="${pageContext.request.contextPath}/login">Login here</a>**.
    </p>

</body>
</html>