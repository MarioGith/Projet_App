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
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@MultipartConfig
@WebServlet(name = "Modify_Menu_Event", urlPatterns = {"/Modify_Menu_Event"})
public class ModifyMenuEventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static Logger logger = Logger.getLogger(ModifyImageEventServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int NumEvenement = Integer.parseInt(request.getParameter("idevent"));
        Part filePart = request.getPart("menu");



        Connection con = (Connection) getServletContext().getAttribute("DBConnection");
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {
            ps = con.prepareStatement("UPDATE tst.evenement SET menu=? WHERE idevent=?");
            ps.setBinaryStream(1,filePart.getInputStream());
            ps.setInt(2,NumEvenement);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ps = con.prepareStatement("select organisateur,type_event,datec,description,prix,idevent,id_createur from tst.evenement where idevent = ?");
            ps.setInt(1, NumEvenement);
            rs = ps.executeQuery();
            if (rs != null && rs.next()) {
                Evenement evenement = new Evenement(rs.getString("organisateur"),rs.getString("type_event"),rs.getInt("idevent"),rs.getString("description"),rs.getString("prix"), rs.getTimestamp("datec"), rs.getInt("id_createur"));
                //logger.info("Evenement trouvé" + evenement);
                request.setAttribute("evenement", evenement);
                RequestDispatcher rd = request.getRequestDispatcher("/modify_event.jsp");
                request.setAttribute("message", "<font color=green>" + "Le Menu a ete modifie.  " + "</font>");
                rd.include(request, response);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            logger.error("Problème d'accès à la base de données");
            throw new ServletException("Problème d'accès à la base de données");
        }
    }
}
