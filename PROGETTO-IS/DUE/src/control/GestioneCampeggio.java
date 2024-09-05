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
	private Piazzola PiazzolaInAttesa = null;
	private Prenotazione prenotazione = null;
	
	protected GestioneCampeggio() {
	}
	
	public static GestioneCampeggio getInstance() {
		
		if (gC == null) {
			gC = new GestioneCampeggio();
		}
		return gC;
	}
	
	public Prenotazione EffettuaPrenotazione(LocalDate DataInizio, LocalDate DataFine, int NomeSettore, String Categoria, String tipo, String Email)throws OperationException{
		
		Settore settore = null;
		ClienteRegistrato cliente = null;
		Piazzola PiazzolaDisponibile = null;
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
		
		//controllo piazzola libera
		
		
		
			PiazzolaDisponibile = readPiazzoleDisp(settore.codiceSettore);
			//controllo se ci sono abbastanza piazzole
			if(PiazzolaDisponibile == null){throw new perationException("Piazzola non disponibile");}
			
			//controllo cliente registrato
			
			cliente = ClienteRegistratoDAO.readClienteRegistrato(Email);
			
			if(cliente==null){throw new perationException("Cliente non registrato!!");}
			
			PiazzolaInAttesa=PiazzolaDisponibile;
			
			prezzoTot=settore.getCostoSettore();
			
			prenotazione = new Prenotazione();
			
			prenotazione.setDataInizio(DataInizio);
			prenotazione.setDataFine(DataFine);
			prenotazione.setPrezzoPrenotazione(prezzoTot);
			prenotazione.setPiazzola(PiazzolaInAttesa);
			prenotazione.setClienteRegistrato(Email);
			
	
			
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno applicazione!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, qualcosa e' andato storto..");
		}
		*/
		
		return prenotazione;
		
	}
	
	
	/*private void salvaPrenotazione() {
	 * try{
		PrenotazioneDAO.create(prenotazione.getClienteRegistrato(), prenotazione.getDataInizio(), prenotazione.getDataFine(), prenotazione.getPiazzola(), prenotazione.getPrezzoPrenotazione()));
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno applicazione!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, errore prenotazione non registrata");
		}
		
	}*/
	
	private void InvioCodice(String email) {
		int codice = 0;
		codice = PrenotazioneDAO.getMaxCodicePrenotazione(email);
		prenotazione.setCodicePrenotazione(codice);
		System.out.println("email: "+prenotazione.getClienteRegistrato()+"\n" + prenotazione.getDataInizio()+ "\n"+ prenotazione.getDataFine()+ "\n"+ prenotazione.getPrezzoPrenotazione() + "\n"+ prenotazione.getCodicePrenotazione()+"\n"+ prenotazione.getPiazzola()+"\n");
	}
	
}