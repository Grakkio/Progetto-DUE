package entity;

public class ContoSpese {
	private String codiceContoSpesa;
	private String stato;
	private float totaleCorrente;
	private ClienteRegistrato cliente;
	
	public ContoSpese(String codiceConto, String stato, float totale, ClienteRegistrato cliente) {
		
		this.codiceContoSpesa = codiceConto;
		this.stato = stato;
		this.totaleCorrente = totale;
		this.cliente = cliente;
		
	}
	
	public void setCodiceContoSpesa(String codiceConto) {
		this.codiceContoSpesa = codiceConto;
	} 
	
	public String getCodiceConto() {
		return this.codiceContoSpesa;
	}
	
	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public String getStato() {
		return this.stato;
	}
	
	
	public void setTotaleCorrente(float totale) {
		
		this.totaleCorrente = totale;
	}
	
	public float getTotaleCorrente() {
		return this.totaleCorrente;
	}
	
	public ClienteRegistrato getClienteRegistrato () {
		return this.cliente;
	}
	
	public void setClienteRegistrato (ClienteRegistrato cliente) {
		this.cliente = cliente;
	}
}
