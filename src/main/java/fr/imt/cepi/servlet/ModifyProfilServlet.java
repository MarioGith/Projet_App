package fr.imt.cepi.servlet;

import fr.imt.cepi.util.Utilisateur;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

@WebServlet(name = "ModifyProfil", urlPatterns = { "/ModifyProfil" })
public class ModifyProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(ModifyProfilServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pass = request.getParameter("password");
		String cha = request.getParameter("chambre");
		Part filePart = request.getPart("pp");

		Connection con = (Connection) getServletContext().getAttribute("DBConnection");
		PreparedStatement ps = null;
		ResultSet rs = null;
		HttpSession session = request.getSession();
		Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
		int id = user.getId();

		// Si on veut modifier mdp
		if (pass!=""){
			try {
				ps = con.prepareStatement("UPDATE tst.utilisateurs SET password=? WHERE idutiliateur=?");
				ps.setString(1, pass);
				ps.setInt(2,id);
				ps.executeQuery();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		if (cha!=""){
			try {
				ps = con.prepareStatement("UPDATE tst.utilisateurs SET chambre=? WHERE idutiliateur=?");
				ps.setString(1,cha);
				ps.setInt(2,id);
				ps.executeQuery();
				user.modifyNumChambre(cha);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		if (filePart!=null){
			InputStream ppm = filePart.getInputStream();
			try {
				ps = con.prepareStatement("UPDATE tst.utilisateurs SET ppm=? WHERE idutiliateur=?");
				ps.setBinaryStream(1,ppm);
				ps.setInt(2,id);
				ps.executeQuery();
				user.modifyPp((Blob) ppm);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		RequestDispatcher rd = request.getRequestDispatcher("/profil.jsp");
		rd.include(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher("/register.jsp");
		rd.include(req, resp);
	}
}
