package fr.imt.cepi.servlet;

import fr.imt.cepi.util.Liste_Event;
import fr.imt.cepi.util.Utilisateur;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static Logger logger = Logger.getLogger(LoginServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String errorMsg = null;
        if (email == null || email.equals("")) {
            errorMsg = "L'email est obligatoire";
        }
        if (password == null || password.equals("")) {
            errorMsg = "Le mot de passe est obligatoire";
        }

        if (errorMsg != null) {
            RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
            request.setAttribute("message", "<font color=red>" + errorMsg + "</font>");
            rd.include(request, response);
        } else {

            Connection con = (Connection) request.getServletContext().getAttribute("DBConnection");
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = con.prepareStatement(
                        "select idutilisateur, nom, email from tst.utilisateurs where email=? and password=? limit 1");
                ps.setString(1, email);
                ps.setString(2, password);
                rs = ps.executeQuery();

                if (rs != null && rs.next()) {
                    Utilisateur utilisateur = new Utilisateur(rs.getString("nom"), rs.getString("email"),
                            rs.getInt("idutilisateur"));
                    Liste_Event liste = new Liste_Event(request);
                    logger.info("Utilisateur trouvé :" + utilisateur);
                    HttpSession session = request.getSession();
                    session.setAttribute("utilisateur", utilisateur);
                    request.setAttribute("liste", liste);
                    RequestDispatcher rd = request.getRequestDispatcher("/home.jsp");
                    rd.include(request, response);

                } else {
                    RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                    logger.error("Utilisateur introuvable =" + email);
                    request.setAttribute("message", "<font color=red>" + "Utilisateur introuvable, veuillez vous enregistrer " + "</font>");
                    rd.include(request, response);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("Problème d'accès à la base de données");
                throw new ServletException("Problème d'accès à la base de données");
            } finally {
                try {
                    assert rs != null;
                    rs.close();
                    ps.close();
                } catch (SQLException e) {
                    logger.error("Exception lors de la fermeture du Statement ou du ResultSet");
                }

            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
        rd.include(req, resp);
    }
}
