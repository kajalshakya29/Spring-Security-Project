<form action="${pageContext.request.contextPath}/update" method="POST">

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <input type="hidden" name="eid" value="${employee.eid}"/>

    Name: <input type="text" name="ename" value="${employee.ename}"><br>
    Department: <input type="text" name="edept" value="${employee.edept}"><br>

    <input type="submit" value="Update Employee">
</form>