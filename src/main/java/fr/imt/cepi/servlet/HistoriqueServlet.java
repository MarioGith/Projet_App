package fr.imt.cepi.servlet;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "Historique", urlPatterns = {"/Historique"})

public class HistoriqueServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(ShowEventServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection con = (Connection) request.getServletContext().getAttribute("DBConnection");
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Integer> listeMenu= new ArrayList();
        Date utilDate = new Date();
        java.sql.Date dateActuelle= new java.sql.Date(utilDate.getTime());

        try {
            ps = con.prepareStatement("select idevent from tst.evenement where datec < ?");
            ps.setDate(1, dateActuelle);

            rs = ps.executeQuery();
            while (rs.next()) {
                listeMenu.add(rs.getInt("idevent"));
            }

            request.setAttribute("liste", listeMenu);
            RequestDispatcher rd = request.getRequestDispatcher("/historique.jsp");
            rd.include(request, response);

        }catch(SQLException e) {
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
