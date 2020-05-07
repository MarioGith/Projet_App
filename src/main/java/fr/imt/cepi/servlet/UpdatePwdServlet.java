package fr.imt.cepi.servlet;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/UpdatePwdServlet")
public class UpdatePwdServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String email = request.getParameter("email");
        String newpass = request.getParameter("NewPass");
        String token =request.getParameter("token");

        Connection con = (Connection) getServletContext().getAttribute("DBConnection");
        PreparedStatement ps = null;
        ResultSet rs = null;
        String errormsg=null;

        try {
            ps = con.prepareStatement("SELECT * from tst.utilisateurs where (email,token) = (?,?)");
            ps.setString(1, email);
            ps.setString(2, token);

            rs = ps.executeQuery();

            if (rs.first()) { // Normalement on arrive toujours ici car on filtre les cas dans SendEmailNewPass.java, mais par précaution on met un if...

                    ps = con.prepareStatement("UPDATE tst.utilisateurs SET password=? where (email ,token)=(?,?) ");
                    ps.setString(1, newpass);
                    ps.setString(2, email);
                    ps.setString(3, token);
                    ps.execute();
                    RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                    request.setAttribute("message", "<font color=green>" + "Votre mot de passe a bien été modifié" + "</font>");
                    rd.include(request, response);

            }

            else{ errormsg="Nous n'avons pas trouvé de compte lié à ce lien de confirmation";  // Normalement nous n'arrivons jamais ici car on filtre bien les cas dans SendEmailNewPass.java, mais bon, on ne sait jamais...
                  RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                  request.setAttribute("message", "<font color=red>" + errormsg + "</font>");
                  rd.include(request, response);

            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Problème d'accès à la base de données.");
        }


    }
}