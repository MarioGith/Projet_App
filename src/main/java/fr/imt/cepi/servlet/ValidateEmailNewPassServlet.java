package fr.imt.cepi.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/ValidateServletNewPass")
public class ValidateEmailNewPassServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        String token = request.getParameter("token");
        String email = request.getParameter("email");

        Connection con = (Connection) getServletContext().getAttribute("DBConnection");
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement("select * from tst.utilisateurs where (email, token) = (?,?)"); // On cherche dans la DB si un compte correspond à l'adresse d'activation
            ps.setString(1, email);
            ps.setString(2, token);
            rs = ps.executeQuery();

            if (rs.next()) {


                request.setAttribute("message", "<font color=green> Choisissez votre nouveau mot de passe");
                request.setAttribute("token", token);
                request.setAttribute("email", email);

                RequestDispatcher rd = request.getRequestDispatcher("/ForgetPwd2.jsp");
                rd.include(request, response);
            } else {
                request.setAttribute("message", "<font color=red> Nous n'avons touvé aucun compte relié à ce lien de confirmation");
                RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                rd.include(request, response);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
