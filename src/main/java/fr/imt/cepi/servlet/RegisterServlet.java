package fr.imt.cepi.servlet;

import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "Register", urlPatterns = { "/Register" })
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(RegisterServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String nom = request.getParameter("nom");
		String chambre = request.getParameter("chambre");
		String errorMsg = null;
		if (email == null || email.equals("") || !email.contains("@mines-ales.")) {
			errorMsg = "L'email des Mines est obligatoire.";
		}
		if (password == null || password.equals("")) {
			errorMsg = "Le mot de passe est obligatoire";
		}
		if (nom == null || nom.equals("")) {
			errorMsg = "Le nom est obligatoire";
		}

		Connection con = (Connection) getServletContext().getAttribute("DBConnection");
		PreparedStatement ps = null;
		ResultSet rs = null;
	// ICI on regarde si l'adresse email est déjà utilisée
		try {
			ps = con.prepareStatement("SELECT COUNT(*) from tst.utilisateurs where utilisateurs.email = ? ");
			ps.setString(1, email);
			rs = ps.executeQuery();

			while(rs.next()) {
				int a = Integer.parseInt( rs.getString(1));// Si on compare directement les strings on a toujours True
				if (a !=0) {
					errorMsg = "Un compte est déjà associé à cette email";
				}
			}


		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Problème avec la base de données");
			throw new ServletException("Problème d'accès à la base de données.");
		}



		if (errorMsg != null) {
			RequestDispatcher rd = request.getRequestDispatcher("/register.jsp");
			request.setAttribute("message", "<font color=red>" + errorMsg + "</font>");
			rd.include(request, response);
		} else {

		//	Connection con = (Connection) getServletContext().getAttribute("DBConnection");
		//	PreparedStatement ps = null;
			try {
				ps = con.prepareStatement("insert into tst.utilisateurs(nom, email, password, chambre) values (?,?,?,?)");
				ps.setString(1, nom);
				ps.setString(2, email);
				ps.setString(3, password);
				ps.setString(4, chambre);

				ps.execute();

				logger.info("Utilisateur enregistré avec l'email " + email);

				// forward to login page to login
				RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
				request.setAttribute("message",
						"<font color=green>Enregistrement effectué avec succès, veuillez vous identifier.</font>");
				rd.include(request, response);

			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("Problème avec la base de données");
				throw new ServletException("Problème d'accès à la base de données.");
			} finally {
				try {
					assert ps != null;
					ps.close();
				} catch (SQLException e) {
					logger.error("Erreur lors de la fermeture du statement");
				}
			}
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher("/register.jsp");
		rd.include(req, resp);
	}
}
