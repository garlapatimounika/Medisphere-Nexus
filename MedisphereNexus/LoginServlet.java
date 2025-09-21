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

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void dopost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String email = request.getParameter("email");
	        String password = request.getParameter("password");
		
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/medisphere", "root", "password");

            // Only patient login (optional: add role='patient' in your query)
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name"); // or "full_name" if column is full_name
                response.sendRedirect("HomeServlet?login=success&name=" + name);
            } else {
                response.sendRedirect("HomeServlet?login=failed");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("HomeServlet?login=error");
        }

}
}
