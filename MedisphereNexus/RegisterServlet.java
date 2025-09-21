package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String contactno = request.getParameter("contactno");
        String dob = request.getParameter("dob");
        int age = Integer.parseInt(request.getParameter("age"));
        String address = request.getParameter("address");
        String password = request.getParameter("password");

        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/medisphere", "root", "password");

            String sql = "INSERT INTO users(name,email,contactno,dob,age,address,password) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, contactno);
            pst.setString(4, dob);
            pst.setInt(5, age);
            pst.setString(6, address);
            pst.setString(7, password);

            int row = pst.executeUpdate();

            if (row > 0) {
                // Redirect to Home page after successful registration
                response.sendRedirect("HomePage.html");
            } else {
                
                response.sendRedirect("register.html");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("RegisterPage.html"); 
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
