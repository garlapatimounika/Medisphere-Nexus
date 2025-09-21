package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppointmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String doctorName = request.getParameter("doctor_name");
        String department = request.getParameter("department");
        String appointmentDate = request.getParameter("appointment_date");
        String appointmentTime = request.getParameter("appointment_time");
        String reason = request.getParameter("reason"); // corrected parameter name

        Connection con = null;

        try {
            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Connect to DB
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/medisphere", "root", "password");

            // Get user_id from email
            String getUserIdQuery = "SELECT id FROM users WHERE email = ?";
            PreparedStatement pstUser = con.prepareStatement(getUserIdQuery);
            pstUser.setString(1, email);
            ResultSet rs = pstUser.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");

                // Insert appointment
                String sql = "INSERT INTO appointments(user_id, appointment_date, appointment_time, doctor_name, department, reason) "
                        + "VALUES(?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setInt(1, userId);
                pst.setString(2, appointmentDate);
                pst.setString(3, appointmentTime);
                pst.setString(4, doctorName);
                pst.setString(5, department);
                pst.setString(6, reason); // use correct variable

                int row = pst.executeUpdate();

                response.setContentType("text/html");
                if (row > 0) {
                    response.getWriter().println("<h3>Appointment Booked Successfully!</h3>");
                    response.getWriter().println("<a href='HomePage.html'>Go to Home</a>");
                } else {
                    response.getWriter().println("<h3>Failed to book appointment!</h3>");
                    response.getWriter().println("<a href='AppointmentPage.html'>Try Again</a>");
                }

            } else {
                // User email not found
                response.setContentType("text/html");
                response.getWriter().println("<h3>Email not registered!</h3>");
                response.getWriter().println("<a href='RegisterPage.html'>Register First</a>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("AppointmentPage.html");
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
