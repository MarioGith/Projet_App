package fr.imt.cepi.servlet;

import fr.imt.cepi.util.EmailUtility;
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
import java.util.UUID;

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
		String token=null;

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
		String msg=null;

		try {
			ps = con.prepareStatement("SELECT * from tst.utilisateurs where utilisateurs.email = ?");
			ps.setString(1, email);
			rs = ps.executeQuery();

			if(rs.first()) {

				if(rs.getBoolean("valide")) {
					errorMsg = "Un compte est déjà associé à cette email";
				}

				else if(!rs.getBoolean("valide")){
					msg= "Votre compte est déjà en attente de confirmation, un nouvel email de confirmation vient de vous être envoyé";
					}
			}


		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Problème avec la base de données");
			throw new ServletException("Problème d'accès à la base de données.");
		}


		if ( errorMsg!=null ) {
			RequestDispatcher rd = request.getRequestDispatcher("/register.jsp");
			request.setAttribute("message", "<font color=red>" + errorMsg + "</font>");
			rd.include(request, response);
		} else {

			try {
				if (!rs.first()) {
					ps = con.prepareStatement("insert into tst.utilisateurs(nom,password,chambre,token,valide,email) values (?,?,?,?,?,?)");
					}

				else{ // Si on se réinscrit une 2ème fois alors que le compte est encore en attente de validation, mais on veut recevoir de nouveau le mail de confirmation ou changer son mdp ou nom ou chambre...
					ps = con.prepareStatement("UPDATE tst.utilisateurs SET nom=?,password=?,chambre=?,token=?,valide=? where email =?");
					}

				token =UUID.randomUUID().toString().toUpperCase();
				ps.setString(1, nom);
				ps.setString(2, password);
				ps.setString(3, chambre);
				ps.setString(4, token);
				ps.setBoolean(5, false);
				ps.setString(6, email);

				ps.execute();

				logger.info("Utilisateur enregistré avec l'email " + email);

				// forward to login page to login
				RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");



				if( msg == null){ // Si le msg n'est pas égal a "on va renvoyer un message", alors c'est la 1ère inscription
					msg="Un email de validation vient de vous être envoyé, cliquez sur le lien qu'il contient pour confirmer votre compte";				}

				request.setAttribute("message","<font color=green>"+ msg+"</font>");


				// Envoie de l'email de confirmation
				String recipient = email;
				String subject = "Confirmez votre inscription à AppEvent";
				String content = "Veuillez cliquer sur ce lien pour confirmer votre inscription : "+"http://localhost:8080/Gradle___org_example___IJServeltExample_1_0_SNAPSHOT_war/ValidateServlet?nom="+nom+"&token="+token;

				String Errormsgmail=null;
				try {
					EmailUtility.sendEmail(recipient, subject, content);
				} catch (Exception ex) {
					ex.printStackTrace();
					Errormsgmail = "Nous n'avons pas pu envoyer le mail: " + ex.getMessage();
					request.setAttribute("message", "<font color=red>"+ Errormsgmail+"</font>");
				} finally {

					rd.include(request, response);

				}

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
