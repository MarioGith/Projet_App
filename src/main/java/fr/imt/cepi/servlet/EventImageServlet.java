package fr.imt.cepi.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Servlet pour récupération d'une image associée à un événement : passer la valeur d'idevent dans le paramètre id de la requête

@WebServlet("/eventImage")
public class EventImageServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
// Lecture du BLOB
            Connection con = (Connection) request.getServletContext().getAttribute("DBConnection");
            PreparedStatement ps = con.prepareStatement("SELECT image_pre FROM tst.evenement WHERE idevent = ?");
            ps.setString(1, request.getParameter("id"));
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                Blob blob = rs.getBlob("image_pre");
                if (blob != null) {
                    rs.close();
                    ps.close();
                    blob.getBinaryStream().transferTo(response.getOutputStream());
                    response.setContentType("image/png");
                }
            }
// Attention au type de l'image : à ajuster en fonction du type de l'image d'origine qu'il faudrait donc mémoriser
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Erreur");
        }
    }
}
