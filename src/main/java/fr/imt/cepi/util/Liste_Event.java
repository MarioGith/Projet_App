package fr.imt.cepi.util;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Liste_Event implements Serializable {

	private static final long serialVersionUID = 6297385302078200511L;

	public ArrayList<Evenement> liste = new ArrayList();

	public Liste_Event(HttpServletRequest request) {
		ArrayList<Integer> id = new ArrayList();

		Connection con = (Connection) request.getServletContext().getAttribute("DBConnection");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Date utilDate = new Date();
		java.sql.Timestamp maintenant= new java.sql.Timestamp(utilDate.getTime());
		java.sql.Timestamp jourfin = new java.sql.Timestamp(utilDate.getTime()+1209600000);

		try {
			ps = con.prepareStatement(
					"select * from tst.evenement where datec between ? and ? order by datec ASC");
			ps.setTimestamp(1,maintenant);
			ps.setTimestamp(2,jourfin);
			rs = ps.executeQuery();
			while(rs.next()){
				Evenement event = new Evenement(rs.getString("organisateur"),rs.getString("type_event"),rs.getInt("idevent"),rs.getString("description"),rs.getString("prix"),rs.getDate("datec"), rs.getInt("id_createur"));
				liste.add(event);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void setNbparticipantsliste(HttpServletRequest req){
		for (Evenement e: liste){
			e.setNbparticipants(req);
		}
	}



	public String getEvent() {
		String s = liste.get(0).getDate();
		System.out.println(s);
		String fin="";
		for (int i=0;i<liste.size();i++) {
			int y=i+1;
			fin+="<div class='event'>";
			fin += "<img src=\"eventImage?id=" + liste.get(i).getId() + "\""+">";
			fin += "<h3>";
			fin += "#" + liste.get(i).getId() + ", organisé par : " + liste.get(i).getOrganisateur() + ", Nombre de participants : " + liste.get(i).getNbparticipants() + "<br>";
			fin+="</h3>";
			fin+="<form action=\"ShowEvent\" method=\"post\">";
			fin+="<input type='hidden' value='"+liste.get(i).getId()+"' name='idevent'/>";
			fin+="<button name=\"NumEvenement\" type=\"submit\">";
			fin+="voir evenement";
			fin+="</button>";
			fin+="</form>";
			fin+="</div>";
			fin+="<br>";
		}
		return fin;
	}

}