package fr.imt.cepi.servlet;

import fr.imt.cepi.util.EmailUtility;

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
import java.util.UUID;

@WebServlet("/sendmailnewpass")
public class SendEmailNewPass extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String newpass= request.getParameter("Newpass");
        String email = request.getParameter("email");
        Connection con = (Connection) getServletContext().getAttribute("DBConnection");
        PreparedStatement ps = null;
        ResultSet rs = null;
        // ICI on regarde si l'adresse email est déjà utilisée
        String token=null;
        String errormsg=null;



        try {
            ps = con.prepareStatement("SELECT * from tst.utilisateurs where email = ?");
            ps.setString(1, email);

            rs = ps.executeQuery();

            if (rs.first()) {

                if (rs.getBoolean("valide")) { // On envoie le mail

                    try {
                        ps = con.prepareStatement("UPDATE tst.utilisateurs SET token=? where email =?");
                        token = UUID.randomUUID().toString().toUpperCase();
                        ps.setString(1, token);
                        ps.setString(2, email);
                        ps.execute();

                    } catch (SQLException e) {
                        e.printStackTrace();
                        errormsg="Problème avec la DB";
                    }

                    // Envoie de l'email de confirmation de changement de password
                    String recipient = email;
                    String subject = "Confirmez votre changement de mot de passe AppEvent";
                    String content = "Veuillez cliquer sur ce lien pour confirmer votre changement de mot de passe : "+"http://localhost:8080/Gradle___org_example___IJServeltExample_1_0_SNAPSHOT_war/ForgetPwdServlet?token="+token+"&newpass="+newpass+"&email="+email;
                    request.setAttribute("message","<font color=green>"+ "Un email de confirmation vient de vous être envoyé, cliquez sur le lien qu'il contient pour confirmer le changement de mot de passe</font>");

                    try {
                        EmailUtility.sendEmail(recipient, subject, content);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        errormsg = "Nous n'avons pas pu envoyer le mail: " + ex.getMessage();
                        request.setAttribute("message", "<font color=red>"+ errormsg+"</font>");
                    } finally {

                        RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                        rd.include(request, response);

                    }



                } else {
                    RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                    errormsg = "Votre compte est en attente de confirmation. Confirmez votre compte ou réinscrivez vous avec la même adresse.";
                    request.setAttribute("message", "<font color=red>"+ errormsg +"</font>");
                    rd.include(request, response);

                }
            } else {
                errormsg = "Aucun compte n'est associé à cette adresse mail, créez vous un compte";
                RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                request.setAttribute("message", "<font color=red>"+ errormsg +"</font>");
                rd.include(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Problème d'accès à la base de données.");
        }



    }


}
