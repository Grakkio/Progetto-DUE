package entity;

import java.time.*;

public class Acquisto {
	
	private int contoSpesa;
	private int servizio;
	private LocalDate dataAcquisto;
	//private LocalTime oraAcquisto;
	private int codiceAcquisto;
	
	//COSTRUTTORE
	
	public Acquisto(int conto, int serv, LocalDate data, int cod) {
		this.contoSpesa=conto;
		this.servizio=serv;
		this.dataAcquisto=data;
		this.codiceAcquisto=cod;
	}
	
	public Acquisto(int conto, int serv) {
		this.contoSpesa=conto;
		this.servizio=serv;
		//this.dataAcquisto=data;
		//this.codiceAcquisto=cod;
	}
	//GET E SET

	public int getContoSpesa() {
		return contoSpesa;
	}

	public void setContoSpesa(int contoSpesa) {
		this.contoSpesa = contoSpesa;
	}

	public int getServizio() {
		return servizio;
	}

	public void setServizio(int servizio) {
		this.servizio = servizio;
	}

	
	public LocalDate getDataAcquisto() {
		return dataAcquisto;
	}
/*
	public void setDataAcquisto(LocalDate dataAcquisto) {
		this.dataAcquisto = dataAcquisto;
	}*/

	public int getCodiceAcquisto() {
		return codiceAcquisto;
	}

	public void setCodiceAcquisto(int codiceAcquisto) {
		this.codiceAcquisto = codiceAcquisto;
	}

	
	/*
	public LocalTime getOraAcquisto() {
		return oraAcquisto;
	}

	public void setOraAcquisto(LocalTime oraAcquisto) {
		this.oraAcquisto = oraAcquisto;
	}
	
	*/

}
