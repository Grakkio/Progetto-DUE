package entity;

public class Servizio {
	private float prezzoServizio;
	private String tipoServizio;
	private int idServizio;
	
	public Servizio(float prezzo, String tipo, int id) {
		this.prezzoServizio = prezzo;
		this.tipoServizio = tipo;
		this.idServizio = id;
	}
	
	public void setPrezzoServizio(float prezzo) {
		this.prezzoServizio = prezzo;
	}
	
	public float getPrezzoServizio() {
		return this.prezzoServizio;
	}
	
	public void setTipoServizio(String tipo) {
		this.tipoServizio = tipo;
	}
	
	public String getTipoServizio () {
		return this.tipoServizio;
	}
	
	public void setIdServizio(int id) {
		this.idServizio = id;
	}
	
	public int getIdServizio() {
		return this.idServizio;
	}
	
}
