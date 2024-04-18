package com.example.lab4;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "EmployeeServleServlet", value = "/EmployeeServlet")
public class EmployeeServle extends HttpServlet {
    private Map<Integer, Employee> employees = new HashMap<>();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null && action.equals("add")) {
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String position = request.getParameter("position");
            Employee newEmployee = new Employee(generateId(), name, surname, position);
            employees.put(newEmployee.getId(), newEmployee);
            response.sendRedirect("EmployeeServlet");
        }
        if (action != null && action.equals("update")) {
            response.getWriter().println("<p>Nie ma takiego pracownika</p>");
            doPut(request,response);
        }
    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Employee empToUpdate = employees.get(id);
        if (empToUpdate != null) {
            String newName = request.getParameter("name");
            String newSurname = request.getParameter("surname");
            String newPosition = request.getParameter("position");
            empToUpdate.setName(newName);
            empToUpdate.setSurname(newSurname);
            empToUpdate.setPosition(newPosition);
            response.sendRedirect("EmployeeServlet");
        } else {
            response.getWriter().println("<p>Nie ma takiego pracownika</p>");
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

        if (action == null) {
            out.println("<ul>");
            out.println("<li><a href='EmployeeServlet?action=getall'>Wszystkie pracowniki</a></li>");
            out.println("<li>");
            out.println("<form action='EmployeeServlet' method='get'>");
            out.println("Pokaż pracownika o ID: ");
            out.println("<input type='number' name='id' min='1'>");
            out.println("<input type='hidden' name='action' value='get'>");
            out.println("<input type='submit' value='Pokaż'>");
            out.println("</form>");
            out.println("</li>");
            out.println("<li><a href='/Lab4_war_exploded/add.jsp'>Dodaj pracownika</a></li>");
            out.println("<li>");
            out.println("<form action='EmployeeServlet' method='get'>");
            out.println("Aktualizuj pracownika o ID: <input type='text' name='id'>");
            out.println("<input type='hidden' name='action' value='update'>");
            out.println("<input type='submit' value='Aktualizuj'>");
            out.println("</form>");
            out.println("</li>");
            out.println("</ul>");
        } else {
            switch (action) {
                case "get":
                    int id = Integer.parseInt(request.getParameter("id"));
                    Employee employee = employees.get(id);
                    if (employee != null) {
                        out.println("<h2>Dane pracownika</h2>");
                        out.println("<p>ID: " + employee.getId() + "</p>");
                        out.println("<p>Imię: " + employee.getName() + "</p>");
                        out.println("<p>Nazwisko: " + employee.getSurname() + "</p>");
                        out.println("<p>Stanowisko: " + employee.getPosition() + "</p>");
                    } else {
                        out.println("<p>Niema takiego pracownika</p>");
                    }
                    break;
                case "getall":
                    out.println("<h2>Wszystkie pracowniki</h2>");
                    for (Employee emp : employees.values()) {
                        out.println("<p>ID: " + emp.getId() + ", Name: " + emp.getName() + ", Surname: " + emp.getSurname() + ", Position: " + emp.getPosition() + "</p>");
                    }
                    break;
                case "update":
                    request.setAttribute("employees", employees);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/add.jsp");
                    dispatcher.forward(request, response);
            }
        }

    }

    private int generateId() {
        int maxId = 0;
        for (int id : employees.keySet()) {
            if (id > maxId) {
                maxId = id;
            }
        }
        return maxId + 1;
    }
}
