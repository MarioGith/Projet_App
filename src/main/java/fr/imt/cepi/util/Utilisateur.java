package fr.imt.cepi.util;

import java.io.Serializable;

public class Utilisateur implements Serializable {

	private static final long serialVersionUID = 6297385302078200511L;

	private int id;
	private final String nom;
	private final String email;

	public Utilisateur(String nom, String email, int id) {
		this.nom = nom;
		this.email = email;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int pId) {
		id = pId;
	}

	public String getNom() {
		return nom;
	}

	public String getLogin() {
		return email;
	}

	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", nom=" + nom + ", login=" + email + "]";
	}

}
