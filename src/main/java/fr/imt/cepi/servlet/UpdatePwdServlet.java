package fr.imt.cepi.servlet;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

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

        String newpass = request.getParameter("NewPass");
        String token =request.getParameter("token");
        String errormsg=null;
        if (newpass==null || newpass.equals("")){
            errormsg="Le mot de passe est obligatoire";
            RequestDispatcher rd = request.getRequestDispatcher("/ForgetPwd2.jsp");
            request.setAttribute("message", "<font color=red>" + errormsg + "</font>");
            request.setAttribute("token",token);
            rd.forward(request, response);
            return; // Sinon, la suite de la servlet va être executé, on ne le veut pas
        }


        int saltLength = 8; // salt length in bytes
        int hashLength = 8; // hash length in bytes
        int parallelism = 1; // currently not supported by Spring Security
        int memory = 4096;   // memory costs
        int iterations = 3;
        Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder(saltLength,hashLength,parallelism,memory,iterations);
        String encodePass = argon2PasswordEncoder.encode(newpass);

        Connection con = (Connection) getServletContext().getAttribute("DBConnection");
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement("SELECT * from tst.utilisateurs where token = ?");
            ps.setString(1, token);

            rs = ps.executeQuery();

            if (rs.first()) { // Normalement on arrive toujours ici car on filtre les cas dans SendEmailNewPass.java, mais par précaution on met un if...
                    int id = rs.getInt("idutilisateur");
                    ps = con.prepareStatement("UPDATE tst.utilisateurs SET password=? where token=? ");
                    ps.setString(1, encodePass);
                    ps.setString(2, token);
                    ps.execute();

                    ps = con.prepareStatement("UPDATE tst.utilisateurs SET token=? where idutilisateur=? ");
                    ps.setString(1, null);
                    ps.setInt(2,id);
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