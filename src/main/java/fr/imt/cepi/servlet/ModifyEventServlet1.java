package fr.imt.cepi.servlet;
import fr.imt.cepi.util.Evenement;
import fr.imt.cepi.util.Liste_Event;
import fr.imt.cepi.util.Utilisateur;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@MultipartConfig
@WebServlet(name = "Modify_Event1", urlPatterns = {"/Modify_Event1"})


public class ModifyEventServlet1 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static Logger logger = Logger.getLogger(ModifyEventServlet1.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int NumEvenement = Integer.parseInt(request.getParameter("idevent"));

        Connection con = (Connection) request.getServletContext().getAttribute("DBConnection");
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("select organisateur,type_event,datec,description,prix,idevent,id_createur from tst.evenement where idevent = ?");
            ps.setInt(1, NumEvenement);
            rs = ps.executeQuery();
            if (rs != null && rs.next()) {
                Evenement evenement = new Evenement(rs.getString("organisateur"),rs.getString("type_event"),rs.getInt("idevent"),rs.getString("description"),rs.getString("prix"), rs.getDate("datec"), rs.getInt("id_createur"));
                logger.info("Evenement trouvé" + evenement);
                request.setAttribute("evenement", evenement);
                RequestDispatcher rd = request.getRequestDispatcher("/modify_event.jsp");
                rd.include(request, response);
            } else {
                Liste_Event liste = new Liste_Event(request);
                request.setAttribute("liste", liste);
                RequestDispatcher rd = request.getRequestDispatcher("/home.jsp");
                PrintWriter out = response.getWriter();
                logger.error("Evenement introuvable");
                out.println(
                        "<font color=red>L'evenement n'existe plus</font>");
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
