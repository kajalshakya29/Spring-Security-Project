<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Reset Password</title></head>
<body>
    <h2>Set New Password</h2>

    <form action="${pageContext.request.contextPath}/reset-password" method="post">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="token" value="${token}" />

        <label>New Password:</label>
        <input type="password" name="password" maxlength="20" required/>
        <br><br>
<div style="margin-top: 0px; margin-left: 0px; font-size: 0.9em; color: red;">
    <p>Password Rules:</p>
    <ul>
        <li>Min 8 and Max 20 characters.</li>
        <li>Must contain at least one **letter** and one **digit**.</li>
        <li>Must contain **exactly two** special characters.</li>
        <li>Allowed Special Characters: **! @ # $ % ^ & * _ + - =**</li>
    </ul>
</div>
        <label>Confirm Password:</label>
        <input type="password" name="confirmPassword" required />
        <br><br>

        <button type="submit">Reset Password</button>
    </form>
</body>
</html>