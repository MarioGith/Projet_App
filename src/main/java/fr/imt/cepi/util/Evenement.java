package fr.imt.cepi.util;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Evenement implements Serializable {

	private static final long serialVersionUID = 6297385302078200511L;

	private int id;
	private final String organisateur;
	private final String type_event;
	private final String description;
	private final String prix;
	private final String horaire;
	private String nbparticipants = "0";

	public Evenement(String organisateur, String type_event, int id, String description, String prix, String horaire) {
		this.organisateur = organisateur;
		this.id = id;
		this.type_event = type_event;
		this.description = description;
		this.prix = prix;
		this.horaire = horaire;


	}

	public void setNbparticipants(HttpServletRequest request) {

		Connection con = (Connection) request.getServletContext().getAttribute("DBConnection");
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(
					"SELECT COUNT(*) from lien where lien.idevent = ? ");
			ps.setInt(1, this.id);
			rs = ps.executeQuery();

			while(rs.next()){
				this.nbparticipants=rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

			}

	public String getNbparticipants() {
		return nbparticipants;
	}


	public int getId() {
		return id;
	}

	public void setId(int pId) {
		id = pId;
	}

	public String getOrganisateur() {
		return organisateur;
	}

	public String getType_event() {
		return type_event;
	}

	public String getDescription(){return description;}

	public String getPrix(){return prix;}

	public String getHoraire(){return horaire;}

	@Override
	public String toString() {
		return "Event [id=" + id + ", organisateur=" + organisateur + ", type d'évenement=" + type_event + "]";
	}

}
