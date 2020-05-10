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
import java.util.Date;
import java.util.UUID;

@WebServlet("/sendmailnewpass")
public class SendEmailNewPass extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       // String newpass= request.getParameter("Newpass");
        String email = request.getParameter("email");
        Connection con = (Connection) getServletContext().getAttribute("DBConnection");
        PreparedStatement ps = null;
        ResultSet rs = null;
        String token=null;
        String errormsg=null;

        try {
            ps = con.prepareStatement("SELECT * from tst.utilisateurs where email = ?");
            ps.setString(1, email);

            rs = ps.executeQuery();

            if (rs.first()) {

                if (rs.getBoolean("valide")) {

                    try {
                        Date utilDate = new Date();
                        java.sql.Timestamp maintenant= new java.sql.Timestamp(utilDate.getTime());

                        ps = con.prepareStatement("UPDATE tst.utilisateurs SET token=?,newpassdemandtime=? where email =?");

                        ps.setTimestamp(2,maintenant);
                        ps.setString(3, email);

                        // On s'assure de générer un token sans doublon dans la DB
                        java.sql.SQLIntegrityConstraintViolationException e = new java.sql.SQLIntegrityConstraintViolationException();
                        while(e!=null){// On s'assure de générer un token jusqu'à ce qu'il ne soit pas déjà dans la DB
                            try{
                                token = UUID.randomUUID().toString().replace("-", "");
                                ps.setString(1, token);
                                ps.execute();
                                e=null;// Si on arrive à cette ligne, alors il n'y a pas eu d'erreur
                            }
                            catch (java.sql.SQLIntegrityConstraintViolationException E) {
                                e=E;
                            }

                        }



                    } catch (SQLException e) {
                        e.printStackTrace();
                        errormsg="Problème avec la DB";
                    }

                    // Envoie de l'email de confirmation de changement de password
                    String recipient = email;
                    String subject = "Confirmez votre changement de mot de passe AppEvent";
                    String content = "Veuillez cliquer sur ce lien pour confirmer votre changement de mot de passe : "+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/Gradle___org_example___IJServeltExample_1_0_SNAPSHOT_war/ValidateServletNewPass?token="+token+" Ce lien restera valide 2 heures.";
                    request.setAttribute("message","<font color=green>"+ "Un email de confirmation vient de vous être envoyé, cliquez sur le lien qu'il contient pour changer de mot de passe. Ce lien restera valide 2 heures.</font>");
                    System.out.println(content);

                    try {
                        EmailUtility.sendEmail(recipient, subject, content);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        errormsg = "Nous n'avons pas pu envoyer le mail: " + ex.getMessage()+"Réessayez votre demande.";
                        request.setAttribute("message", "<font color=red>"+ errormsg+"</font>");
                    } finally {

                        RequestDispatcher rd = request.getRequestDispatcher("/ForgetPwd.jsp");
                        rd.include(request, response);

                    }



                } else {
                    RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                    errormsg = "Votre compte est en attente de confirmation. Confirmez votre compte ou inscrivez vous à nouveau avec cette même adresse pour recevoir un nouveau mail de confirmation.";
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
