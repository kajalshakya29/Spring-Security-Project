<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Employee List</title>
</head>
<body>
    <h2>Welcome, <sec:authentication property="name" />!</h2>
    <p>Your Role: <sec:authentication property="principal.authorities" /></p>

    <h2>Employee List</h2>
    <table border="1">
        <tr>
            <th>ID</th><th>Name</th><th>Department</th><th>Actions</th>
        </tr>
        <c:forEach var="e" items="${employees}">
            <tr>
                <td>${e.eid}</td>
                <td>${e.ename}</td>
                <td>${e.edept}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/view/${e.eid}">View</a>
                    <sec:authorize access="hasRole('ADMIN')">
                        | <a href="${pageContext.request.contextPath}/update/${e.eid}">Update</a>
                        | <a href="${pageContext.request.contextPath}/delete/${e.eid}"
                           onclick="return confirm('Delete this employee?');">Delete</a>
                    </sec:authorize>
                </td>
            </tr>
        </c:forEach>
    </table>

    <sec:authorize access="hasRole('ADMIN')">
        <p><a href="${pageContext.request.contextPath}/add">Add New Employee</a></p>
    </sec:authorize>

    <form action="${pageContext.request.contextPath}/logout" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button type="submit">Logout</button>
    </form>
</body>
</html>