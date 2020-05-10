package fr.imt.cepi.servlet;

import fr.imt.cepi.util.Utilisateur;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		if (pass!=null){
			try {

				int saltLength = 8; // salt length in bytes
				int hashLength = 8; // hash length in bytes
				int parallelism = 1; // currently not supported by Spring Security
				int memory = 4096;   // memory costs
				int iterations = 3;

				Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder(saltLength,hashLength,parallelism,memory,iterations);
				String encodePass = argon2PasswordEncoder.encode(pass);

				ps = con.prepareStatement("UPDATE tst.utilisateurs SET password=? WHERE idutilisateur=?");
				ps.setString(1, encodePass);
				ps.setInt(2,id);
				ps.executeUpdate();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		if (cha!=""){
			try {
				ps = con.prepareStatement("UPDATE tst.utilisateurs SET chambre=? WHERE idutilisateur=?");
				ps.setString(1,cha);
				ps.setInt(2,id);
				ps.executeUpdate();
				user.modifyNumChambre(cha);
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
