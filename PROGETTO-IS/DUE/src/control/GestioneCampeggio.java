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
		ArrayList<Piazzola> PiazzoleDisponibili = null;
		float prezzoTot = 0;
		String mailCliente = null;
		String categoria = null;
		String tipologia = null;
		
		try {
			//controllo esistenza settore
			
			categoria = Categoria;
			tipologia = tipo;
			settore = SettoreDAO.read(NomeSettore, categoria, tipologia);
			
			if(settore == null) {throw new OperationException("Settore non trovato");
			}
		
		//controllo piazzola libera
		
		
		
			PiazzoleDisponibili = PiazzolaDAO.readPiazzoleDisp(settore.getCodiceSettore());
			//controllo se ci sono abbastanza piazzole
			if(PiazzoleDisponibili == null){throw new OperationException("Piazzola non disponibile");}
			
			//controllo cliente registrato
			
			cliente = ClienteRegistratoDAO.readClienteRegistrato(Email);
			
			if(cliente==null){throw new OperationException("Cliente non registrato!!");}
			
			PiazzolaInAttesa=PiazzoleDisponibili.get(0);
			
			prezzoTot=settore.getCosto();
			
			prenotazione = new Prenotazione();
			
			prenotazione.setDataInizio(DataInizio);
			prenotazione.setDataFine(DataFine);
			prenotazione.setPrezzoPrenotazione(prezzoTot);
			prenotazione.setPiazzola(PiazzolaInAttesa.getIdPiazzola());
			prenotazione.setClienteRegistrato(Email);
			
	
			
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno applicazione!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, qualcosa e' andato storto..");
		}
		
		return prenotazione;
		
	}
	
	
	public void salvaPrenotazione(Prenotazione prenotazione) {
	  try{
		PrenotazioneDAO.create(prenotazione.getClienteRegistrato(), prenotazione.getDataInizio(), prenotazione.getDataFine(), prenotazione.getPiazzola(), prenotazione.getPrezzoPrenotazione());
	  }catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno applicazione!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, errore prenotazione non registrata");
		}
		
	}
	
	 public void AnnullaPrenotazione(Prenotazione prenotazione) {
		 System.out.println("Prenotazione annullata.\n"); //il rifferimento verrà eliminato dal garbage collector in quanto sarà inutilizzata o sarà riutilizzata in altre prenotazioni (overridden)
	 }
	
	public void InvioCodice(String email) {
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
	
	public String AcquistaServizio(String CodiceConto, int IdServizio) {
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
		StoricoDAO.updateStorico(codiceConto,idServizio, this.servizio.getPrezzoServizio());
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
	
	
	public void invioElencoSpese(String codiceConto) {
		ContoSpese conto=null;
		float totale;
		ArrayList<Servizio> ElencoSpese = null;
		try {
			conto = ContoSpeseDAO.readContoSpesa(codiceConto);
			totale=conto.getTotaleCorrente();
			ElencoSpese = new ArrayList<Servizio>(StoricoDAO.readElencoSpese(codiceConto));
			for(Servizio p : ElencoSpese ) {
				System.out.println(p.getIdServizio()+ " "+ p.getTipoServizio()+ " "+ p.getPrezzoServizio()+ "\n");
			}
			System.out.println("Torale corrente: "+ totale+ "\n");
			
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno aggiorno conto spese!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, errore conto non aggiornato!\n");
		}
	}
	
	public String RegistrazionePagamento(String CodiceConto) {
		
		ContoSpese conto = null;
		//ClienteRegistrato cliente = null;
		String codiceConto = CodiceConto;
		String email;
		String conferma = new String(); 
		float totale = 0;
		try {
			conto = ContoSpeseDAO.readContoSpesa(codiceConto);
			if(conto == null) {throw new OperationException("Conto non presente!!\n");}
			if(!(conto.getStato().equals("ATTIVO"))) {throw new OperationException("Conto non attivo!!\n");}
			if(conto.getTotaleCorrente() == 0) {throw new OperationException("Conto vuoto!!\n");}
			this.conto = new ContoSpese(conto.getCodiceConto(), conto.getStato(), conto.getTotaleCorrente(), conto.getClienteRegistrato());
			stampaContoFinale(this.conto);
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRegistrazione pagamento!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, pagamento non avvenuto!\n");
		}
	}
			/*totale = registraPagamento(this.conto);
			email=this.conto.getClienteRegistrato();
			InvioContoFinale(email, totale);
			conferma = ChiusuraConto(this.conto);
			return conferma;
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRegistrazione pagamento!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, pagamento non avvenuto!\n");
		}
	}
	*/ 
	
	
	private void stampaContoFinale(ContoSpese c) {
		System.out.println("Conto totale: " + c.getTotaleCorrente()+ "\n"); //deve stampare conto dettagliato ->anche conto spese
	}
	
	
	public String salvaPagamento() {
		
		ContoSpese conto = this.conto;
		String conferma = new String(); 
		try {
		conto.setStato("PAGATO");
		ContoSpeseDAO.updateStatoContoSpesa("PAGATO");
		InvioContoFinale(conto);
		conferma = ChiusuraConto(conto);
		return conferma;
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno registra pagamento!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, errore conto non chiuso!\n");
		}
	}
	
	
	private void InvioContoFinale(ContoSpese conto) {	
		String email = null;
		float prezzo = 0;
		//try {
			email = new String(conto.getClienteRegistrato());
			prezzo = conto.getTotaleCorrente();
			System.out.println("email: " +email+ "\ntotale: "+ prezzo+ "\n");
		//}catch(DBConnectionException dEx){
		//	throw new OperationException("\nRiscontrato problema interno invia mail!\n");
		//}catch(DAOException ex) {
		//	throw new OperationException("Ops, errore mail non inviata!\n");
		//}
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
	
	public String AperturaConto(String Email) {
		ContoSpese conto = new ContoSpese("ATTIVO", 0, Email);
		try {
		ContoSpeseDAO.create(conto);
		conto.setCodiceContoSpesa(ContoSpeseDAO.RestituisciID(Email));
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno ad apertura conto!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, errore conto non aperto!\n");
		}
		
		return conto.getCodiceConto();
	}
	
	public String Registrazione(String Nome, String Cognome,String Email,String Username, String Password, int Telefono) {
		String conferma = new String ("Avvenuta registrazione");
		try {
			ClienteRegistrato utente = new ClienteRegistrato(Nome, Cognome, Email, Username, Password, Telefono);
			ClienteRegistratoDAO.createCliente(utente);
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno a registrazione!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, registrazione non avvenuta :( !\n");
		}
		return conferma;
		
	}
	
	public void VisualizzaDisponibilità(LocalDate DataInizio, LocalDate DataFine, int NomeSettore, String Categoria, String Tipo) {
		ArrayList<Piazzola> PiazzoleDisponibili = null;
		Settore settore = null;
		try {
		settore = SettoreDAO.read(NomeSettore, Categoria, Tipo);
		
		if(settore == null) {throw new OperationException("Settore non trovato");
		}
	
	//controllo piazzola libera
		PiazzoleDisponibili = PiazzolaDAO.readPiazzoleDisp(settore.getCodiceSettore());
		//controllo se ci sono abbastanza piazzole
		if(PiazzoleDisponibili == null){throw new OperationException("Piazzola non disponibile");}
		for(Piazzola p : PiazzoleDisponibili ) {
			System.out.println(p.getCodiceSettore()+ " "+p.getIdPiazzola()+ " \n");
		}
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno a visualizzazione settore!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, visualizzazione non disponibile!\n");
		}
		
	}
	
	public void InsericiPiazzola(int idPiazzola, int idSettore) {
		Settore settore = null;
		Piazzola piazzola = new Piazzola(idSettore, idPiazzola);
		try{
			PiazzolaDAO.createPiazzola(piazzola);
			settore = SettoreDAO.readSettore(idSettore);
			settore.getPiazzole().add(piazzola);
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno a inserimentoPiazzola!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, piazzola non aggiunta!\n");
		}
	}
	
}