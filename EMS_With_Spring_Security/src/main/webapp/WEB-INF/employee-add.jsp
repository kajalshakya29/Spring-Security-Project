<h2>Add Employee</h2>
<form action="save" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    Name: <input type="text" name="ename"><br>
    Department: <input type="text" name="edept"><br>
    <input type="submit" value="Save">
</form>
