package entity;

import java.time.*;


public class Prenotazione {
private int codicePrenotazione;
private LocalDate dataInizio;
private LocalDate dataFine;
private float prezzoPrenotazione;
private int piazzola;
private String cliente;

public Prenotazione(int codpren, LocalDate datai, LocalDate dataf, float prezzopren, String cl, int p) {
	this.codicePrenotazione = codpren;
	this.dataInizio = datai;
	this.dataFine = dataf;
	this.prezzoPrenotazione = prezzopren;
	this.piazzola = p;
	this.cliente = cl;
}

public Prenotazione(LocalDate datai, LocalDate dataf, float prezzopren, String cl, int p) {
	this.dataInizio = datai;
	this.dataFine = dataf;
	this.prezzoPrenotazione = prezzopren;
	this.piazzola = p;
	this.cliente = cl;
}
public LocalDate getDataInizio() {
	return this.dataInizio;
}


public void setDataInizio (LocalDate datai) {
	this.dataInizio = datai;
}

public LocalDate getDataFine() {
	return this.dataFine;
	
}

public void setDataFine(LocalDate dataf) {
	this.dataFine = dataf;
}


public int getCodicePrenotazione () {
	
	return this.codicePrenotazione;
}

public void setCodicePrenotazione(int codice) {
	
	this.codicePrenotazione = codice;
}


public float getPrezzoPrenotazione() {
	return this.prezzoPrenotazione;
}

public void setPrezzoPrenotazione(float prezzo) {
	
	this.prezzoPrenotazione = prezzo;
}

public void setPiazzola (int piazzola) {
	this.piazzola = piazzola;
}

public int getPiazzola () {
	 return this.piazzola;
}

public String getClienteRegistrato () {
	return this.cliente;
}

public void setClienteRegistrato (String cliente) {
	this.cliente = cliente;
}


//creare funzione savePrenotazione

}
