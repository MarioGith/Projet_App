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
@WebServlet(name = "Modify_Event", urlPatterns = {"/Modify_Event"})


public class ModifyEventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static Logger logger = Logger.getLogger(ModifyEventServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //HttpSession session = request.getSession();
        //Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
        int NumEvenement = Integer.parseInt(request.getParameter("idevent"));
        String description = request.getParameter("description");
        String horaire = request.getParameter("horaire");
        String prix = request.getParameter("prix");
        String organisateur = request.getParameter("organisateur");
        String typeevent = request.getParameter("typeevent");
        String date = request.getParameter("date");
        Part filePart = request.getPart("image_pre");
        Part filePart2 = request.getPart("menu");
      //  String datec = date+horaire;


        Connection con = (Connection) getServletContext().getAttribute("DBConnection");
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;

        if (organisateur!="" ||  organisateur!=null){
            try {
                ps = con.prepareStatement("UPDATE tst.evenement SET organisateur=? WHERE idevent=?");
                ps.setString(1, organisateur);
                ps.setInt(2,NumEvenement);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (typeevent!="" ||  typeevent!=null){
            try {
                ps = con.prepareStatement("UPDATE tst.evenement SET type_event=? WHERE idevent=?");
                ps.setString(1, typeevent);
                ps.setInt(2,NumEvenement);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (description!="" || description!=null){
            try {
                ps = con.prepareStatement("UPDATE tst.evenement SET description=? WHERE idevent=?");
                ps.setString(1, description);
                ps.setInt(2,NumEvenement);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (prix!="" || prix!= null){
            try {
                ps = con.prepareStatement("UPDATE tst.evenement SET prix=? WHERE idevent=?");
                ps.setString(1, prix);
                ps.setInt(2,NumEvenement);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (filePart!=null){
            try {
                ps1 = con.prepareStatement("UPDATE tst.evenement SET image_pre=? WHERE idevent=?");
                ps1.setBinaryStream(1,filePart.getInputStream());
                ps1.setInt(2,NumEvenement);
                ps1.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (filePart2!=null){
            try {
                ps2 = con.prepareStatement("UPDATE tst.evenement SET menu=? WHERE idevent=?");
                ps2.setBinaryStream(1,filePart2.getInputStream());
                ps2.setInt(2,NumEvenement);
                ps2.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        try {
            ps = con.prepareStatement("select organisateur,type_event,datec,description,prix,idevent,id_createur from tst.evenement where idevent = ?");
            ps.setInt(1, NumEvenement);
            rs = ps.executeQuery();
            if (rs != null && rs.next()) {
                Evenement evenement = new Evenement(rs.getString("organisateur"),rs.getString("type_event"),rs.getInt("idevent"),rs.getString("description"),rs.getString("prix"), rs.getTimestamp("datec"), rs.getInt("id_createur"));
                logger.info("Evenement trouvé" + evenement);
                request.setAttribute("evenement", evenement);
                RequestDispatcher rd = request.getRequestDispatcher("/event.jsp");
                rd.include(request, response);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            logger.error("Problème d'accès à la base de données");
            throw new ServletException("Problème d'accès à la base de données");
        }

        /*if (filePart!=null){
            try {
                ps = con.prepareStatement("UPDATE tst.evenement SET image_pre=? WHERE idevent=?");
                ps.setBinaryStream(1,filePart.getInputStream());
                ps.setInt(2,NumEvenement);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (filePart2!=null){
            try {
                ps = con.prepareStatement("UPDATE tst.evenement SET menu=? WHERE idevent=?");
                ps.setBinaryStream(1,filePart2.getInputStream());
                ps.setInt(2,NumEvenement);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }*/
    }
}
