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
				Evenement event = new Evenement(rs.getString("organisateur"), rs.getString("type_event"), rs.getInt("idevent"), rs.getString("description"), rs.getString("prix"), rs.getTimestamp("datec"), rs.getInt("id_createur"));
				liste.add(event);
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}


	public String getEventSearch() {
		String fin="";
		for (int i=0;i<liste.size();i++) {
			fin+="<br>";
			fin+= "<div class='display-1'>"+liste.get(i).getDate();
			fin+="<div class='event'>";
			fin += "<img src=\"eventImage?id=" + liste.get(i).getId() + "\""+"class=\"logoevent rounded mx-auto d-block\" alt=\"...\">";
			fin+="<p>"+liste.get(i).getDescription()+"</p>";
			fin+="<div class=\"text-right\">";
			fin+="<form action=\"ShowEvent\" method=\"post\">";
			fin+="<input type='hidden' value='"+liste.get(i).getId()+"' name='idevent'/>";
			fin+="<button class='pagevent' name='NumEvenement' type='submit'>";
			fin+="voir plus";
			fin+="</button>";
			fin+="</form>";
			fin+="</div>";
			fin+="</div>";
			fin+="</div>";
			fin+="<br>";
			fin+="<hr class=\"bordure\">";
			fin+="<br>";
		}
		return fin;
	}
}
