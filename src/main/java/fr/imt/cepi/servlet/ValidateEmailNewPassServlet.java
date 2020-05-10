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
import java.util.Date;

@WebServlet("/ValidateServletNewPass")
public class ValidateEmailNewPassServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");

        Connection con = (Connection) getServletContext().getAttribute("DBConnection");
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement("select * from tst.utilisateurs where token = ?"); // On cherche dans la DB si un compte correspond à l'adresse d'activation
            ps.setString(1, token);
            rs = ps.executeQuery();

            if (rs.next()) {

                Date utilDate = new Date();
                java.sql.Timestamp maintenant = new java.sql.Timestamp(utilDate.getTime());
                java.sql.Timestamp demandtime = rs.getTimestamp("newpassdemandtime");
                long tempsbonus = 2 * 60 * 60*1000; // 2h en milis
                java.sql.Timestamp timelimit = new java.sql.Timestamp(demandtime.getTime() + tempsbonus);

                if (maintenant.before(timelimit)) {
                request.setAttribute("message", "<font color=green> Choisissez votre nouveau mot de passe");
                request.setAttribute("token", token);

                RequestDispatcher rd = request.getRequestDispatcher("/ForgetPwd2.jsp");
                rd.include(request, response);
                }
                else{
                    request.setAttribute("message", "<font color=red> Votre lien de confirmation a expiré, veuillez faire une nouvelle demande de changement de mot de passe.");
                    RequestDispatcher rd = request.getRequestDispatcher("/ForgetPwd.jsp");
                    rd.include(request, response);

                }
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
