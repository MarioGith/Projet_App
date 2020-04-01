package fr.imt.cepi.util;

import java.io.Serializable;

public class Evenement implements Serializable {

	private static final long serialVersionUID = 6297385302078200511L;

	private int id;
	private final String organisateur;
	private final String type_event;
	private final String description;
	private final String prix;
	private final String horaire;

	public Evenement(String organisateur, String type_event, int id, String description, String prix, String horaire) {
		this.organisateur = organisateur;
		this.id = id;
		this.type_event = type_event;
		this.description = description;
		this.prix = prix;
		this.horaire = horaire;
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
