package entity;

import java.util.ArrayList;

public class Settore {
	
	private int codiceSettore;
	private float costo;
	private String categoria;
	private String tipoSettore;
	private ArrayList<Piazzola> piazzole; //contenimento stretto
	
	
	//COSTRUTTORE
	public Settore(int cod, float costo, String cat, String tipo, ArrayList<Piazzola> vet) {
		
		this.codiceSettore = cod;
		this.costo=costo;
		this.categoria = cat;
		this.tipoSettore = tipo;
		this.piazzole = new ArrayList<Piazzola>();
		this.piazzole = vet;
		
	}
	
	
	//GET E SET
	public int getCodiceSettore() {
		return codiceSettore;
	}
	
	public void setCodiceSettore(int codiceSettore) {
		this.codiceSettore = codiceSettore;
	}
	
	public float getCosto() {
		return costo;
	}
	
	public void setCosto(float costo) {
		this.costo = costo;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getTipoSettore() {
		return tipoSettore;
	}
	
	public void setTipoSettore(String tipoSettore) {
		this.tipoSettore = tipoSettore;
	}
	
	//rivedere arraylist piazzole
	public ArrayList<Piazzola> getPiazzole() {
		return piazzole;
	}
	
	public void setPiazzole(ArrayList<Piazzola> piazzole) {
		this.piazzole = piazzole;
	}
	
}
