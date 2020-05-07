package fr.imt.cepi.servlet;

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
       // Part filePart = request.getPart("image_pre");
       // Part filePart2 = request.getPart("menu");
      //  String datec = date+horaire;


        Connection con = (Connection) getServletContext().getAttribute("DBConnection");
        PreparedStatement ps = null;
        ResultSet rs = null;

        if (organisateur!=""){
            try {
                ps = con.prepareStatement("UPDATE tst.evenement SET organisateur=? WHERE idevent=?");
                ps.setString(1, organisateur);
                ps.setInt(2,NumEvenement);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (typeevent!=""){
            try {
                ps = con.prepareStatement("UPDATE tst.evenement SET type_event=? WHERE idevent=?");
                ps.setString(1, typeevent);
                ps.setInt(2,NumEvenement);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (description!=""){
            try {
                ps = con.prepareStatement("UPDATE tst.evenement SET description=? WHERE idevent=?");
                ps.setString(1, description);
                ps.setInt(2,NumEvenement);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (prix!=""){
            try {
                ps = con.prepareStatement("UPDATE tst.evenement SET prix=? WHERE idevent=?");
                ps.setString(1, prix);
                ps.setInt(2,NumEvenement);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (description!=""){
            try {
                ps = con.prepareStatement("UPDATE tst.evenement SET description=? WHERE idevent=?");
                ps.setString(1, description);
                ps.setInt(2,NumEvenement);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
        RequestDispatcher rd = request.getRequestDispatcher("/event.jsp");
        rd.include(request, response);
    }
}
