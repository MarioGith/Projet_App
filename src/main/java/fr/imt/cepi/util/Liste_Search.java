package fr.imt.cepi.util;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Liste_Search implements Serializable {

	private static final long serialVersionUID = 6297385302078200511L;

	public ArrayList<Evenement> liste = new ArrayList();

	public Liste_Search(HttpServletRequest request) {
		String recherche = request.getParameter("recherche");
		Connection con = (Connection) request.getServletContext().getAttribute("DBConnection");
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(
					"select * from tst.evenement where organisateur=? or description=? or type_event=?");
			ps.setString(1, recherche);
			ps.setString(2, recherche);
			ps.setString(3, recherche);
			rs = ps.executeQuery();
			while (rs.next()) {
				Evenement event = new Evenement(rs.getString("organisateur"), rs.getString("type_event"), rs.getInt("idevent"), rs.getString("description"), rs.getString("prix"), rs.getString("horaire"));
				liste.add(event);
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}


	public String getEventSearch() {
		String fin="";
		for (int i=0;i<liste.size();i++) {
			fin+="<div class='event'>";
			fin += "<img src=\"eventImage?id=" + liste.get(i).getId() + "\""+">";
			fin += "<h3>";
			fin += "#" + liste.get(i).getId() + ", organisé par : " + liste.get(i).getOrganisateur() + ", Nombre de participants : " + liste.get(i).getNbparticipants() + "<br>";
			fin+="</h3>";
			fin+="</div>";
			fin+="<br>";
		}
		return fin;
	}
}
