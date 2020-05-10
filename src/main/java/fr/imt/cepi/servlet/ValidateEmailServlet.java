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

@WebServlet("/ValidateServlet")
public class ValidateEmailServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");

        Connection con = (Connection) getServletContext().getAttribute("DBConnection");
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
        ps = con.prepareStatement("select * from tst.utilisateurs where token = ?"); // On cherche dans la DB si un compte correspond à l'adresse d'activation
        ps.setString(1, token);
        rs=ps.executeQuery();

        if(rs.next()) {
            if (!rs.getBoolean("valide")) {
                // On vérifie qu'il ne s'est pas écoulé 2 heures, sinon l'enregistrement n'a pas lieu
                Date utilDate = new Date();
                java.sql.Timestamp maintenant = new java.sql.Timestamp(utilDate.getTime());
                java.sql.Timestamp regtime = rs.getTimestamp("regtime");
                long tempsbonus = 2 * 60 * 60*1000; // 2h en milis
                java.sql.Timestamp timelimit = new java.sql.Timestamp(regtime.getTime() + tempsbonus);
                int id=rs.getInt("idutilisateur");

                if (maintenant.before(timelimit)) {
                    ps = con.prepareStatement("UPDATE tst.utilisateurs SET valide =? where token = ?");
                    ps.setBoolean(1, true);
                    ps.setString(2, token);
                    ps.execute();
                    // On supprime ensuite le token de la DB pour l'utilisateur en question
                    ps = con.prepareStatement("UPDATE tst.utilisateurs SET token=? where idutilisateur=?");
                    ps.setString(1,null);
                    ps.setInt(2, id);
                    ps.execute();

                    request.setAttribute("message", "<font color=green> Félicitation ! Vous avez confirmé votre compte, vous pouvez maintenant vous connecter !");
                    RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                    rd.include(request, response);
                }
                else{
                    request.setAttribute("message", "<font color=red> La durée de validité du lien est écoulée, inscrivez à nouveau.");
                    RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                    rd.include(request, response);


                }
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
