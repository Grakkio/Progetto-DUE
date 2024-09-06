package entity;

public class ContoSpese {
	private int codiceContoSpesa;
	private String stato;
	//private float totaleCorrente;
	private String cliente;
	
	public ContoSpese(String stato, String cliente) {
		
		//this.codiceContoSpesa = codiceConto;
		this.stato = stato;
		//this.totaleCorrente = totale;
		this.cliente = cliente;
		
	}
	
	public void setCodiceContoSpesa(int codiceConto) {
		this.codiceContoSpesa = codiceConto;
	} 
	
	public int getCodiceConto() {
		return this.codiceContoSpesa;
	}
	
	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public String getStato() {
		return this.stato;
	}
	
	/*
	public void setTotaleCorrente(float totale) {
		
		this.totaleCorrente = totale;
	}
	
	public float getTotaleCorrente() {
		return this.totaleCorrente;
	}*/
	
	public String getClienteRegistrato () {
		return this.cliente;
	}
	
	public void setClienteRegistrato (String cliente) {
		this.cliente = cliente;
	}
}
