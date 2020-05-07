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

@WebServlet("/ValidateServlet")
public class ValidateEmailServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        String token = request.getParameter("token");
        String nom = request.getParameter("nom");

        Connection con = (Connection) getServletContext().getAttribute("DBConnection");
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
        ps = con.prepareStatement("select * from tst.utilisateurs where (nom, token) = (?,?)"); // On cherche dans la DB si un compte correspond à l'adresse d'activation
        ps.setString(1, nom);
        ps.setString(2, token);
        rs=ps.executeQuery();

        if(rs.next()) {
            if (!rs.getBoolean("valide")) {
                ps = con.prepareStatement("UPDATE tst.utilisateurs SET valide =? where  (nom, token) = (?,?)");
                ps.setBoolean(1, true);
                ps.setString(2, nom);
                ps.setString(3, token);
                ps.execute();


                request.setAttribute("message", "<font color=green> Félicitation ! Vous avez confirmé votre compte, vous pouvez maintenant vous connecter !");
                RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                rd.include(request, response);
            }
            else{
                request.setAttribute("message", "<font color=red> Votre compte est déjà activé");
                RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                rd.include(request, response);

            }
        }

        else{
            request.setAttribute("message", "<font color=red> Nous n'avons touvé aucun compte relié à ce lien d'activation");
            RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
            rd.include(request, response);
        }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
