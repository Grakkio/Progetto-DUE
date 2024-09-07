package control;

import java.time.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import database.AcquistoDAO;
import database.ClienteRegistratoDAO;
import database.ContiSpeseDAO;
import database.PiazzolaDAO;
import database.PrenotazioneDAO;
import database.ServizioDAO;
import database.SettoreDAO;
import entity.Acquisto;
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
		//String mailCliente = null;
		//String categoria = null;
		//String tipologia = null;
		
		try {
			//controllo esistenza settore
			
			//categoria = Categoria;
			//tipologia = tipo;
			settore = SettoreDAO.readSettore(NomeSettore);
			
			if(settore == null) {throw new OperationException("Settore non trovato");
			}
		
		//controllo piazzola libera
		
		
		
			PiazzoleDisponibili = PiazzolaDAO.readPiazzoleDisp(settore.getCodiceSettore(),DataInizio,DataFine);
			//controllo se ci sono abbastanza piazzole
			if(PiazzoleDisponibili == null){throw new OperationException("Piazzola non disponibile");}
			
			//controllo cliente registrato
			
			cliente = ClienteRegistratoDAO.readClienteRegistrato(Email);
			
			if(cliente==null){throw new OperationException("Cliente non registrato!!");}
			
			PiazzolaInAttesa=PiazzoleDisponibili.get(0);
			
			prezzoTot=settore.getCosto();
			
			prenotazione = new Prenotazione(DataInizio, DataFine, prezzoTot, Email, PiazzolaInAttesa.getIdPiazzola());
	
			
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno all'applicazione!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, qualcosa e' andato storto..");
		}
		
		return prenotazione;
		
	}
	
	
	public void salvaPrenotazione(Prenotazione prenotazione)throws OperationException {
	  try{
		PrenotazioneDAO.createPrenotazione(prenotazione);
	  }catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno all'applicazione!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, errore prenotazione non salvata");
		}
		
	}
	/*
	 public void AnnullaPrenotazione(Prenotazione prenotazione){
		 System.out.println("Prenotazione annullata.\n"); //il rifferimento verrà eliminato dal garbage collector in quanto sarà inutilizzata o sarà riutilizzata in altre prenotazioni (overridden)
	 }*/
	
	public void InvioCodice(String email)throws OperationException {
		int codice = 0;
		try {
		codice = PrenotazioneDAO.getMaxCodicePrenotazione(email);
		prenotazione.setCodicePrenotazione(codice);
		System.out.println("email: "+prenotazione.getClienteRegistrato()+"\n" + prenotazione.getDataInizio()+ "\n"+ prenotazione.getDataFine()+ "\n"+ prenotazione.getPrezzoPrenotazione() + "\n"+ prenotazione.getCodicePrenotazione()+"\n"+ prenotazione.getPiazzola()+"\n");
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato errore invio codice. Problema interno!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, errore codice non inviato\n");
		}
	}
	
	public String AcquistaServizio(int CodiceConto, int IdServizio)throws OperationException {
		int codiceConto = CodiceConto;
		int idServizio = IdServizio;
		ContoSpese conto = null;
		Servizio servizio = null;
		Acquisto acquisto= null;
		
		try {
		conto = ContiSpeseDAO.readContoSpese(codiceConto);
		servizio = ServizioDAO.readServizio(idServizio);
		//controllo se conto spese attivo
		if(conto == null) {throw new OperationException("Conto non presente!!\n");}
		if(!(conto.getStato().equals("ATTIVO"))) {throw new OperationException("Conto non attivo!!\n");}
		if(servizio == null) {throw new OperationException("Servizio inesistente!!\n");}
		//this.conto = new ContoSpese(conto.getCodiceConto(), conto.getStato(), conto.getTotaleCorrente(), conto.getClienteRegistrato());
		//this.servizio = new Servizio (servizio.getPrezzoServizio(), servizio.getTipoServizio(), servizio.getIdServizio());
		acquisto= new Acquisto(CodiceConto, IdServizio);
		AcquistoDAO.createAcquisto(acquisto);
		//aggiornaConto(this.conto, this.servizio);
		String s = new String ("Utilizzo servizio confermato. \n");
		return s;
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato errore nell'acquisto. Problema interno!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, errore servizio non utilizzato!\n");
		}
		
	}
	/*
	private void aggiornaConto (ContoSpese c, Servizio s) throws OperationException {
		ContoSpese conto = c;
		Servizio servizio = s;
		float prezzo = 0;
		try {
		prezzo = servizio.getPrezzoServizio() + conto.getTotaleCorrente();
		conto.setTotaleCorrente(prezzo);
		ContiSpeseDAO.updateTotaleContoSpesa(conto, conto.getCodiceConto());
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno aggiorno conto spese!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, errore conto non aggiornato!\n");
		}
		
	}
	*/
	
	public void invioElencoSpese() throws OperationException{
		ContoSpese conto=null;
		String email=null;
		float totale;
		ArrayList<Servizio> ElencoSpese = null;
		ArrayList<Integer> ElencoCodiciSpesa = null;
		try {
			ElencoCodiciSpesa = new ArrayList<Integer>(ContiSpeseDAO.getContiSpesaAttivi());
			for(Integer c : ElencoCodiciSpesa ) {
				conto = ContiSpeseDAO.readContoSpese(c);
			//if(conto == null) {throw new OperationException("Conto non presente!!\n");}
			//if(!(conto.getStato().equals("ATTIVO"))) {throw new OperationException("Conto non attivo!!\n");}
				email = new String(conto.getClienteRegistrato());
				ElencoSpese = new ArrayList<Servizio>(AcquistoDAO.getListaServizi(c)); //aggiungere al datbase
				totale=ContiSpeseDAO.getTotaleCorrente(c);
				System.out.println("email: "+email+"\n");
				for(Servizio p : ElencoSpese ) {
					System.out.println(p.getIdServizio()+ " "+ p.getTipoServizio()+ " "+ p.getPrezzoServizio()+ "\n");
				}
				System.out.println("Torale corrente: "+ totale+ "\n");
			}	
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno per l'invio mail. Problema interno!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, errore mail non inviata!\n");
		}
	}
	
	public void RegistrazionePagamento(int CodiceConto) throws OperationException{
		
		ContoSpese conto = null;
		//ClienteRegistrato cliente = null;
		int codiceConto = CodiceConto;
		float totale = 0;
		try {
			conto = ContiSpeseDAO.readContoSpese(codiceConto);
			if(conto == null) {throw new OperationException("Conto non presente!!\n");}
			if(!(conto.getStato().equals("ATTIVO"))) {throw new OperationException("Conto non attivo!!\n");}
			totale = ContiSpeseDAO.getTotaleCorrente(codiceConto);
			if(totale== 0) {throw new OperationException("Conto vuoto!!\n");}
			//this.conto = new ContoSpese(conto.getStato(), conto.getTotaleCorrente(), conto.getClienteRegistrato());
			stampaContoFinale(conto,totale);
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato errore nella registrazione del pagamento!\n");
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
	
	
	private void stampaContoFinale(ContoSpese c, float tot) throws OperationException {
		ArrayList<Servizio> ElencoSpese = null;
	
		try {	
		ElencoSpese = new ArrayList<Servizio>(AcquistoDAO.getListaServizi(conto.getCodiceConto()));
		for(Servizio p : ElencoSpese ) {
			System.out.println(p.getIdServizio()+ " "+ p.getTipoServizio()+ " "+ p.getPrezzoServizio()+ "\n");
		}
		System.out.println("Conto totale: " + tot + "\n"); //deve stampare conto dettagliato ->anche conto spese
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno alla stampa del conto!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, stampa del conto non riuscita!\n");
		}
	}
	
	
	public String salvaPagamento()throws OperationException {
		
		ContoSpese conto = this.conto;
		String conferma = new String(); 
		try {
		conto.setStato("PAGATO");
		ContiSpeseDAO.updateContoSpese(conto, conto.getCodiceConto());
		InvioContoFinale(conto);
		conferma = ChiusuraConto(conto);
		return conferma;
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno salva pagamento!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, salvataggio non effettuato!\n");
		}
	}
	
	
	private void InvioContoFinale(ContoSpese conto)throws OperationException{	
		String email = null;
		float prezzo = 0;
		ArrayList<Servizio> ElencoSpese = null;
		try {
			email = new String(conto.getClienteRegistrato());
			ElencoSpese = new ArrayList<Servizio>(AcquistoDAO.getListaServizi(conto.getCodiceConto())); //aggiungere al datbase
			prezzo=ContiSpeseDAO.getTotaleCorrente(conto.getCodiceConto());
			System.out.println("email: "+email+"\n");
			for(Servizio p : ElencoSpese ) {
				System.out.println(p.getIdServizio()+ " "+ p.getTipoServizio()+ " "+ p.getPrezzoServizio()+ "\n");
			}
			System.out.println("Conto totale: "+ prezzo+ "\n");
		
			//invioElencoSpese(conto.getCodiceConto());
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno invia mail!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, errore mail non inviata!\n");
		}
	}
	
	
	private String ChiusuraConto (ContoSpese Conto)throws OperationException {
		try {
		ContoSpese conto = Conto;
		String conferma = new String ("conferma conto chiuso sium");
		conto.setStato("CHIUSO");
		ContiSpeseDAO.updateContoSpese(conto, conto.getCodiceConto());
		return conferma;
	}catch(DBConnectionException dEx){
		throw new OperationException("\nRiscontrato problema interno chiusura conto!\n");
	}catch(DAOException ex) {
		throw new OperationException("Ops, errore conto non chiuso!\n");
	}
		
	}
	
	public int AperturaConto(String Email) throws OperationException {
		ClienteRegistrato cliente;
		ContoSpese conto = new ContoSpese("ATTIVO", Email);
		try {
			cliente =ClienteRegistratoDAO.readClienteRegistrato(Email);
			if(cliente == null) {throw new OperationException("Cliente non presente!!\n");}
			ContiSpeseDAO.createContoSpese(conto);
			return conto.getCodiceConto();
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno ad apertura conto!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, errore conto non aperto!\n");
		}
		
		
	}
	
	public String Registrazione(String Nome, String Cognome,String Email,String Username, String Password, int Telefono) throws OperationException{
		String conferma = new String ("Avvenuta registrazione");
		try {
			ClienteRegistrato utente = new ClienteRegistrato(Nome, Cognome, Email, Username, Password, Telefono);
			ClienteRegistratoDAO.createClienteRegistrato(utente);
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno a registrazione!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, registrazione non avvenuta :( !\n");
		}
		return conferma;
		
	}
	
	public void VisualizzaDisponibilità(LocalDate DataInizio, LocalDate DataFine, int NomeSettore, String Categoria, String Tipo)throws OperationException {
		ArrayList<Piazzola> PiazzoleDisponibili = null;
		Settore settore = null;
		try {
		settore = SettoreDAO.readSettore(NomeSettore);
		
		if(settore == null) {throw new OperationException("Settore non trovato");
		}
	
	//controllo piazzola libera
		PiazzoleDisponibili = PiazzolaDAO.readPiazzoleDisp(settore.getCodiceSettore(),DataInizio,DataFine);
		//controllo se ci sono abbastanza piazzole
		if(PiazzoleDisponibili == null){throw new OperationException("Piazzola non disponibile");}
		for(Piazzola p : PiazzoleDisponibili ) {
			System.out.println(p.getCodiceSettore()+ " "+p.getIdPiazzola()+ " \n");
		}
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno a visualizzazione piazzole!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, visualizzazione non disponibile!\n");
		}
		
	}
	
	public void InsericiPiazzola(int idPiazzola, int idSettore)throws OperationException {
		Settore settore = null;
		Piazzola piazzola = null;
		try{
			settore = SettoreDAO.readSettore(idSettore);
			if(settore == null) {throw new OperationException("Settore non trovato");}
			piazzola = new Piazzola(idSettore, idPiazzola);
			PiazzolaDAO.createPiazzola(piazzola);
			settore.getPiazzole().add(piazzola);
		}catch(DBConnectionException dEx){
			throw new OperationException("\nRiscontrato problema interno a inserimento piazzola!\n");
		}catch(DAOException ex) {
			throw new OperationException("Ops, piazzola non aggiunta!\n");
		}
	}
	
}