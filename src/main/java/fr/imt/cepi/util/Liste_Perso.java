package fr.imt.cepi.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Liste_Perso implements Serializable {

	private static final long serialVersionUID = 6297385302078200511L;

	public ArrayList<Evenement> liste = new ArrayList();

	public Liste_Perso(HttpServletRequest request) {
		Connection con = (Connection) request.getServletContext().getAttribute("DBConnection");
		PreparedStatement ps = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		HttpSession session = request.getSession();
		Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
		int id=user.getId();
		Date utilDate = new Date();
		java.sql.Timestamp dateActuelle= new java.sql.Timestamp(utilDate.getTime());

		try {
			ps = con.prepareStatement(
					"select * from tst.lien where idutilisateur=?");
			ps.setInt(1, id);
			rs1 = ps.executeQuery();
			while (rs1.next()) {
				ps = con.prepareStatement(
						"select * from tst.evenement where idevent=? and datec>=? order by datec ASC");
				ps.setInt(1,rs1.getInt("idevent"));
				ps.setTimestamp(2,dateActuelle);
				rs2 = ps.executeQuery();
				while(rs2.next()){
					Evenement event = new Evenement(rs2.getString("organisateur"),rs2.getString("type_event"),rs2.getInt("idevent"),rs2.getString("description"),rs2.getString("prix"),rs2.getTimestamp("datec"), rs2.getInt("id_createur"));
					liste.add(event);
				}
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public String getEventPerso() {
		String fin="";
		for (int i=0;i<liste.size();i++) {
			fin+="<tr>";
			fin += "<td>";
			fin += "<strong> "+liste.get(i).getDate()+" </strong> "+liste.get(i).getType_event()+" organisé par "+liste.get(i).getOrganisateur();
			fin+="</td>";
			fin+="</tr>";
		}
		return fin;
	}
}
