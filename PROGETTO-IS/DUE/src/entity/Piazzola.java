package entity;

import java.util.ArrayList;

public class Piazzola {
	
	private int idPiazzola;
	private int codiceSettore;
	private String statoPiazzola;
	
	
	//COSTRUTTORE
	public Piazzola (int piaz, int settore, String stato) {
		this.idPiazzola = piaz;
		this.codiceSettore = settore;
		this.statoPiazzola = stato;
	}
	
	
	//GET E SET
	
	public int getIdPiazzola() {
		return idPiazzola;
	}
	
	public void setIdPiazzola(int idPiazzola) {
		this.idPiazzola = idPiazzola;
	}

	public void setStato(String stato) {
		this.statoPiazzola = stato;
	}
	
	public String getStato() {
		return this.statoPiazzola;
	}
	
	
	
	//trigger per controllsre disponibilità piazzola per prenotazione
	

}
