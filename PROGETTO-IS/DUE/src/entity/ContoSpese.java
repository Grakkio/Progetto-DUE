package entity;

public class ContoSpese {
	private String codiceContoSpesa;
	private String stato;
	private float totaleCorrente;
	
	public ContoSpese(String codiceConto, String stato, float totale) {
		
		this.codiceContoSpesa = codiceConto;
		this.stato = stato;
		this.totaleCorrente = totale;
		
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
	
	
}
