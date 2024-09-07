package entity;


public class Piazzola {
	
	private int idPiazzola;
	private int codiceSettore;
	
	
	
	//COSTRUTTORE
	public Piazzola (int piaz, int settore) {
		this.idPiazzola = piaz;
		this.codiceSettore = settore;
		
	}
	
	
	//GET E SET
	
	public int getIdPiazzola() {
		return idPiazzola;
	}
	
	public void setIdPiazzola(int idPiazzola) {
		this.idPiazzola = idPiazzola;
	}
	
	public void setCodiceSettore(int c) {
		this.codiceSettore = c; 
	} 
	
	public int getCodiceSettore() {
		return this.codiceSettore; 
	} 
	
	
	
	//trigger per controllsre disponibilità piazzola per prenotazione
	

}
