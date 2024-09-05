package control;

import java.time.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import entity.ClienteRegistrato;
import entity.ContoSpese;
import entity.Piazzola;
import entity.Prenotazione;
import entity.Servizio;
import entity.Settore;

import exception.DAOException;
import exception.DBConnectionException;
import exception.OperationException;


public class GestioneCampeggio{
	
	private static GestioneCampeggio gC = null;
	private ArrayList<Piazzola> PiazzoleInAttesa;
	private Prenotazione prenotazione = null;
	
	protected GestioneCampeggio() {
		PiazzoleInAttesa = new ArrayList<Piazzola>();
	}
	
	public static GestioneCampeggio getInstance() {
		
		if (gC == null) {
			gC = new GestioneCampeggio();
		}
		return gC;
	}
	
	public Prenotazione EffettuaPrenotazione(LocalDate DataInizio, LocalDate DataFine, int NomeSettore, String Categoria, String tipo, String Email, int NumeroPiazzole)throws OperationException{
		
		Settore settore = null;
		ClienteRegistrato cliente = null;
		ArrayList<Piazzola> PiazzoleDisponibili = new ArrayList<Piazzola>();
		float prezzoTot = 0;
		String mailCliente = null;
		String categoria = null;
		String tipologia = null;
		
		/*try {
			//controllo esistenza settore
			
			categoria = Categoria;
			tipologia = tipo;
			settore = SettoreDAO.read(NomeSettore, categoria, tipologia);
			
			if(settore == null) {throw new OperationException("Settore non trovato");
			}
		
		//controllo piazzole libere
		
		int numPiazzole = NumeroPiazzole;
		
		
			PiazzoleDisponibili = readPiazzoleDisp(settore.codiceSettore);
			//controllo se ci sono abbastanza piazzole
			if(PiazzoleDisponibili.size() < numPiazzole){throw new perationException("Numero piazzole non disponibile");}
			
			//controllo cliente registrato
			
			cliente = ClienteRegistratoDAO.readClienteRegistrato(Email);
			
			if(cliente==null){throw new perationException("Numero piazzole non disponibile");}
			
			for(int i=0; i<numPiazzole;i++){
				PiazzoleInAttesa.add(PiazzoleDisponibili[i]);
			}
			
			prezzoTot=settore.getCostoSettore()*numPiazzole;
			
			int codpren= PrenotazioneDAO.getMaxCodice();
			
			int newcodpren= codpren+1;
			
		
			
			prenotazione = new Prenotazione();
			
			prenotazione.setCodicePrenotazione(newcodpren);
			prenotazione.setDataInizio(DataInizio);
			prenotazione.setDataFine(DataFine);
			prenotazione.setPrezzoPrenotazione(prezzoTot);
			prenotazione.setPiazzole(PiazzoleInAttesa);
			prenotazione.setClienteRegistrato(Email);
			
	
			
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno applicazione!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, qualcosa e' andato storto..");
		}
		*/
		
		return prenotazione;
		
	}
	
	/*
	 * 
	 * PrenotazioneDAO.create(newcodpren, DataInizio, DataFine, prezzoTot, PiazzoleInAttesa, Email);
	 * 
	 * private float calcolaPrezzo(Settore es, int num) {
		float prezzo;
		
		if(es.getCategoria().equals("economy")){
			prezzo=30;	
		}else {prezzo=50;}
		
		if(es.getTipoSettore().equals("tenda")) {
			prezzo=prezzo+40;
		}else {
			prezzo=prezzo+60;}
		prezzo=prezzo*num;
		return prezzo;
	}
	*/
	
	/*private void salvaPrenotazione() {
	 * try{
		PrenotazioneDAO.create(prenotazione.getClienteRegistrato(), prenotazione.getDataInizio(), prenotazione.getDataFine(), prenotazione.getPiazzole(), prenotazione.getPrezzoPrenotazione(), prenotazione.getCodicePrenotazione());
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno applicazione!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, errore prenotazione non registrata");
		}
		
	}*/
	
	private void InvioCodice(String s) {
		int codice = 0;
		codice = PrenotazioneDAO.getMaxCodice(s);
		prenotazione.setCodicePrenotazione(codice);
		System.out.println("email: "+prenotazione.getClienteRegistrato()+"\n" + prenotazione.getDataInizio()+ "\n"+ prenotazione.getDataFine()+ "\n"+ prenotazione.getPrezzoPrenotazione() + "\n"+ prenotazione.getCodicePrenotazione()+"\n");
		for(Piazzola p : prenotazione.getPiazzole()) {
			System.out.println(p.getIdPiazzola() + "\n");
		}
	}
	
}