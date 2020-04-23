package fr.imt.cepi.util;

import java.io.Serializable;
import java.sql.Blob;

public class Utilisateur implements Serializable {

	private static final long serialVersionUID = 6297385302078200511L;

	private int id;
	private final String nom;
	private final String email;
	public String numChambre;
	public Blob pp;

	public Utilisateur(String nom, String email, int id, String num, Blob blob) {
		this.nom = nom;
		this.email = email;
		this.id = id;
		pp=blob;
		numChambre=num;
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

	public String getEmail() { return email; }

	public String getNumChambre() { return numChambre; }

	public String getPp(){ return "<img src=\"profilImage?id=" + id + "\" class=\"mx-auto img-fluid img-circle d-block\" alt=\"avatar\">";}

	public void modifyNumChambre(String num) {
		numChambre=num;
	}

	public void modifyPp(Blob ppm){ pp=ppm;}

	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", nom=" + nom + ", email=" + email + "]";
	}

}
