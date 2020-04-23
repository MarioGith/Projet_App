package fr.imt.cepi.servlet;

import fr.imt.cepi.util.Evenement;
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

@WebServlet(name = "eventUnregister", urlPatterns = {"/eventUnregister"})
public class EventUnregisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static Logger logger = Logger.getLogger(EventRegisterServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        String id = request.getParameter("idevenement");
        Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");

        int idEvent = Integer.parseInt(id);
        int idUser = user.getId();
        String errorMsg = null;

        if (request.getParameter("plusInteresser") != null) {

            Connection con = (Connection) getServletContext().getAttribute("DBConnection");
            PreparedStatement ps = null;
            PreparedStatement ps2 = null;
            ResultSet rs = null;

            try {
                ps = con.prepareStatement("select organisateur,type_event,datec,description,prix,idevent,id_createur from tst.evenement where idevent=?");
                ps.setInt(1,idEvent);
                rs = ps.executeQuery();
                if (rs != null && rs.next()) {
                    Evenement evenement = new Evenement(rs.getString("organisateur"), rs.getString("type_event"), rs.getInt("idevent"), rs.getString("description"), rs.getString("prix"), rs.getTimestamp("datec"), rs.getInt("id_createur"));
                    logger.info("Evenement trouvé" + evenement);
                    request.setAttribute("evenement", evenement);
                }

            }catch(SQLException e) {
                e.printStackTrace();
                logger.error("Problème avec la base de données");
                throw new ServletException("Problème d'accès à la base de données.");
            }

            try {
                ps = con.prepareStatement("select * from tst.lien where idevent=? and idutilisateur=?");
                ps.setInt(1, idEvent);
                ps.setInt(2, idUser);
                rs = ps.executeQuery();

                while (!rs.next()) {
                    errorMsg = "Vous ne vous étiez pas inscrit à cet évènement.";
                }

            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("Problème avec la base de données");
                throw new ServletException("Problème d'accès à la base de données.");
            }

            if (errorMsg != null) {
                RequestDispatcher rd = request.getRequestDispatcher("/event.jsp");
                logger.error(errorMsg);
                request.setAttribute("message", "<font color=red>" + errorMsg + "</font>");
                rd.include(request, response);
            } else {

                try {
                    ps2 = con.prepareStatement("delete from tst.lien where idevent = ? and idutilisateur = ?");
                    ps2.setInt(1, idEvent);
                    ps2.setInt(2, idUser);
                    ps2.execute();

                    logger.info("Vous n'êtes plus intéressé par cet évènement.");

                    RequestDispatcher rd = request.getRequestDispatcher("/event.jsp");
                    request.setAttribute("message",
                            "<font color=green>Vous n'êtes plus intéressé par cet évènement.</font>");
                    rd.include(request, response);

                } catch (SQLException e) {
                    e.printStackTrace();
                    logger.error("Problème avec la base de données");
                    throw new ServletException("Problème d'accès à la base de données.");

                } finally {
                    try {
                        assert ps2 != null;
                        ps2.close();
                        assert ps != null;
                        ps.close();
                    } catch (SQLException e) {
                        logger.error("Erreur lors de la fermeture du statement");
                    }
                }
            }
        }
    }
}
