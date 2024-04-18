<%@ page import="com.example.lab4.Employee" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add/Update Employee</title>
</head>

<body>

<h2>Add/Update Employee</h2>

<form id="employeeForm"
      <%if (request.getParameter("action") != null && request.getParameter("action").equals("update")){ %>action="EmployeeServlet?action=update&id=<%=Integer.parseInt(request.getParameter("id"))%>" method="post"
        <%  } else { %>
      action="EmployeeServlet" method="post"
        <%  } %>
      >
    <% Map<Integer, Employee> employees = (Map<Integer, Employee>) request.getAttribute("employees");
        if (employees != null && request.getParameter("action") != null && request.getParameter("action").equals("update")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Employee employee = employees.get(id);
            if (employee != null) { %>
    Id = <%=Integer.parseInt(request.getParameter("id"))%>
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="<%= employee.getId() %>">
    <label for="name">Name:</label><br>
    <input type="text" id="name" name="name" value="<%= employee.getName() %>"><br>
    <label for="surname">Surname:</label><br>
    <input type="text" id="surname" name="surname" value="<%= employee.getSurname() %>"><br>
    <label for="position">Position:</label><br>
    <input type="text" id="position" name="position" value="<%= employee.getPosition() %>"><br><br>
    <input type="submit" value="Update">
    <%  } else { %>
    <p>No such employee exists.</p>
    <%  }
    } else { %>
    <input type="hidden" name="action" value="add">
    <label for="name">Name:</label><br>
    <input type="text" id="name" name="name"><br>
    <label for="surname">Surname:</label><br>
    <input type="text" id="surname" name="surname"><br>
    <label for="position">Position:</label><br>
    <input type="text" id="position" name="position"><br><br>
    <input type="submit" value="Add">
    <% } %>
</form>

</body>
</html>
