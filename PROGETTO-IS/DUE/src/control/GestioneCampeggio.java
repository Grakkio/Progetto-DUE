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
	
	protected GestioneCampeggio() {
		PiazzoleInAttesa = new ArrayList<Piazzola>();
	}
	
	public static GestioneCampeggio getInstance() {
		
		if (gC == null) {
			gC = new GestioneCampeggio();
		}
		return gC;
	}
	
	public ArrayList<String> EffettuaPrenotazione(LocalDate DataInizio, LocalDate DataFine, int NomeSettore, String Categoria, String tipo, String Email, int NumeroPiazzole)throws OperationException{
		
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
			settore = SettoreDAO.read(NomeSettore, categoria, tipo);
			
			if(settore == null) {throw new OperationException("Settore non trovato");
			}
		}*/
		
		//controllo piazzole libere
		
		int numPiazzole = NumeroPiazzole;
		
		/*try{
			
			
		}*/
		
	}
	
	
}