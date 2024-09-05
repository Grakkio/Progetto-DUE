package entity;

import java.time.*;
import java.util.ArrayList;

public class Prenotazione {
private int codicePrenotazione;
private LocalDate dataInizio;
private LocalDate dataFine;
private float prezzoPrenotazione;
private ArrayList<Piazzola> piazzole;
private ClienteRegistrato cliente;

public Prenotazione(int codpren, LocalDate datai, LocalDate dataf, float prezzopren, ArrayList<Piazzola> piazzole, ClienteRegistrato cl ) {
	this.codicePrenotazione = codpren;
	this.dataInizio = datai;
	this.dataFine = dataf;
	this.prezzoPrenotazione = prezzopren;
	this.piazzole =new ArrayList<Piazzola>();
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

public void setPiazzole (ArrayList<Piazzola> piazzola) {
	this.piazzole = piazzola;
}

public ArrayList<Piazzola> getPiazzole () {
	 return this.piazzole;
}

public ClienteRegistrato getClienteRegistrato () {
	return this.cliente;
}

public void setClienteRegistrato (ClienteRegistrato cliente) {
	this.cliente = cliente;
}


//creare funzione savePrenotazione

}
