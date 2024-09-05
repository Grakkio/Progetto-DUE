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
	private ContoSpese conto = null;
	private Servizio servizio = null;
	
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
		try {
		codice = PrenotazioneDAO.getMaxCodicePrenotazione(email);
		prenotazione.setCodicePrenotazione(codice);
		System.out.println("email: "+prenotazione.getClienteRegistrato()+"\n" + prenotazione.getDataInizio()+ "\n"+ prenotazione.getDataFine()+ "\n"+ prenotazione.getPrezzoPrenotazione() + "\n"+ prenotazione.getCodicePrenotazione()+"\n"+ prenotazione.getPiazzola()+"\n");
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno invio codice!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, errore prenotazione non registrata\n");
		}
		}
	
	public String UsufruisciServizio(String CodiceConto, int IdServizio) {
		String codiceConto = CodiceConto;
		int idServizio = IdServizio;
		ContoSpese conto = null;
		Servizio servizio = null;
		
		try {
		conto = ContoSpeseDAO.readContoSpesa(codiceConto);
		servizio = ServizioDAO.readServizio(idServizio);
		//controllo se conto spese attivo
		if(conto == null) {throw new OperationException("Conto non presente!!\n");}
		if(!(conto.equals("ATTIVO"))) {throw new OperationException("Conto non attivo!!\n");}
		if(servizio == null) {throw new OperationException("Servizio inesistente!!\n");}
		this.conto = new ContoSpese(conto.getCodiceConto(), conto.getStato(), conto.getTotaleCorrente(), conto.getClienteRegistrato());
		this.servizio = new Servizio (servizio.getPrezzoServizio(), servizio.getTipoServizio(), servizio.getIdServizio());
		aggiornaConto(this.conto, this.servizio);
		String s = new String ("Utilizzo servizio confermato. \n");
		return s;
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno usufruisci servizio!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, errore utilizzo servizio!\n");
		}
		
	}
	
	private void aggiornaConto (ContoSpese c, Servizio s) {
		ContoSpese conto = c;
		Servizio servizio = s;
		float prezzo = 0;
		try {
		prezzo = servizio.getPrezzoServizio() + conto.getTotaleCorrente();
		conto.setTotaleCorrente(prezzo);
		ContoSpeseDAO.updateTotaleContoSpesa(prezzo);
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno aggiorno conto spese!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, errore conto non aggiornato!\n");
		}
		
	}
	
	public String RegistrazionePagamento(String CodiceConto, String Email) {
		
		ContoSpese conto = null;
		ClienteRegistrato cliente = null;
		String codiceConto = CodiceConto;
		String email = Email;
		String conferma = new String(); 
		float totale = 0;
		try {
			conto = ContoSpeseDAO.readContoSpesa(codiceConto);
			if(conto == null) {throw new OperationException("Conto non presente!!\n");}
			if(!(conto.equals("ATTIVO"))) {throw new OperationException("Conto non attivo!!\n");}
			if(conto.getTotaleCorrente() == 0) {throw new OperationException("Conto vuoto!!\n");}
			this.conto = new ContoSpese(conto.getCodiceConto(), conto.getStato(), conto.getTotaleCorrente(), conto.getClienteRegistrato());
			totale = registraPagamento(this.conto);
			InvioContoFinale(email, totale);
			conferma = ChiusuraConto(this.conto);
			return conferma;
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRegistrazione pagamento!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, pagamento non avvenuto!\n");
		}
	} 
	
	private float registraPagamento(ContoSpese Conto) {
		ContoSpese conto = Conto;
		try {
		conto.setStato("PAGATO");
		ContoSpeseDAO.updateStatoContoSpesa("PAGATO");
		return conto.getTotaleCorrente();
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno registra pagamento!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, errore conto non chiuso!\n");
		}
	}
	
	private void InvioContoFinale(String Email, float totale) {
		//try {
			System.out.println("email: " +Email+ "\n"+ totale+ "\n");
		//}catch(DBConnectionException dEx){
			//throw new OperationException("\nRiscontrato problema interno invia mail!\n");
		//}catch(DAOException ex) {
			//throw new OperationException("Ops, errore mail non inviata!\n");
		}
		
	
	
	private String ChiusuraConto (ContoSpese Conto) {
		try {
		ContoSpese conto = Conto;
		String conferma = new String ("conferma conto chiuso sium");
		conto.setStato("CHIUSO");
		ContoSpeseDao.updateStatoContoSpesa("CHIUSO");
		return conferma;
	}catch(DBConnectionException dEx){
		throw new OperationException("\nRiscontrato problema interno chiusura conto!\n");
	}catch(DAOException ex) {
		throw new OperationException("Ops, errore conto non chiuso!\n");
	}
		
	}
	
	
}