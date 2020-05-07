package fr.imt.cepi.servlet;

import fr.imt.cepi.util.Utilisateur;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

@MultipartConfig
@WebServlet(name = "ModifyPp", urlPatterns = { "/ModifyPp" })
public class ModifyPp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(ModifyPp.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Part filePart = request.getPart("pp");

		Connection con = (Connection) getServletContext().getAttribute("DBConnection");
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		HttpSession session = request.getSession();
		Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
		int id = user.getId();

		if (filePart!=null){
			InputStream ppm = filePart.getInputStream();
			try {
				ps1 = con.prepareStatement("UPDATE tst.utilisateurs SET pp=? WHERE idutilisateur=?");
				ps1.setBinaryStream(1,ppm);
				ps1.setInt(2,id);
				ps1.executeUpdate();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}

		try{
			ps2 = con.prepareStatement("SELECT pp FROM utilisateurs WHERE idutilisateur=?");
			ps2.setInt(1,id);
			rs = ps2.executeQuery();
			rs.next();
			user.modifyPp(rs.getBlob("pp"));
		} catch (SQLException throwables) {
			throwables.printStackTrace();
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
