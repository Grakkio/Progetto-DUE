package boundary;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate; 
import java.time.format.DateTimeFormatter; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import control.GestioneCampeggio;
import exception.OperationException;

public class BoundaryClienteRegistrato {
	static Scanner scan = new Scanner(System.in);
	public static void main(String[] args) {
		boolean exit = false;
		while(!exit) {
			//scelta dell'operazione
			System.out.println("Cliente Registrato\n");
			System.out.println("1. Visualizza Disponibilita' \n");
			System.out.println("2. Effettua Prenotazione \n");
			System.out.println("3. Acquista Servizio \n");
			System.out.println("inserire il numero dell'opzione desiderata: \n");
			
			String risposta = scan.nextLine(); //acquisisce input
			
			if(risposta.equals("1")) {
				VisualizzaDisponibilità();
				exit = true;	
			}else if(risposta.equals("2")) {
				EffettuaPrenotazione();
				exit = true;
			}else if(risposta.equals("3")) {
				//AcquistaServizio();
				exit = true;
			}else System.out.println(risposta+ " non è un valore valido\n");
		}
		
		System.out.println("Bye Bye ;) \n");
		
	}
	
//un metodo static non richiede l'istanziazione di un oggetto di quella classe per il suo utilizzo	
	
	private static void VisualizzaDisponibilità() {
		
		LocalDate DataInizio= null;
		LocalDate DataFine = null;

		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-gg");

		GestioneCampeggio gestioneCampeggio = GestioneCampeggio.getInstance();  
		int NomeSettore = 0;
		String Categoria = null; //premium o economy
		String Tipo = null; //camper o tende
		boolean inputvalido = false;
		String risposta; 
		while(!inputvalido){
		System.out.println("Inserire data inizio yyyy-mm-gg\n");
		risposta = scan.nextLine();
		while(!inputvalido) {
			try {
				//conversione String in LocalDate
				DataInizio = LocalDate.parse(risposta);
				inputvalido = true;
			}catch (IllegalArgumentException error) {
				System.out.println("Errore nell'acquisizione di data inizio soggiorno, riprovare (metti bene grazie)\n");
				//System.out.println();//nuovo inserimento
				risposta = scan.nextLine();
				//scan.close();
		}
	}
		inputvalido = false;
		//inserire data fine soggiorno
		System.out.println("Inserire data fine gg-mm-yyyy\n");
		risposta = scan.nextLine();
		//scan.close();
		while(!inputvalido) {
			
			try {
				//conversione String in LocalDate
				DataFine = LocalDate.parse(risposta);
				inputvalido = true;
			}catch (IllegalArgumentException error) {
				System.out.println("Errore nell'acquisizione di data fine soggiorno, riprovare (metti bene grazie)\n");
				//System.out.println();//nuovo inserimento
				risposta = scan.nextLine();
				//scan.close();
			}
		}
		inputvalido = false;
		try {
		if(DataFine.isBefore(DataInizio)) {System.out.println("Errore nell'ordine dei soggiorni, riprovare (metti bene grazie)\n");}
		else inputvalido = true;
		}catch (IllegalArgumentException error) {}
		}
		inputvalido = false;
		
		System.out.println("Inserire il tipo -camper- o -tende-\n");
		Tipo = scan.nextLine();
		//scan.close();
		while(!inputvalido) {
			//controllo se la risposta è camper o tenda
		if(!((Tipo.equals("camper"))||(Tipo.equals("Camper"))||(Tipo.equals("CAMPER"))||(Tipo.equals("tenda"))||(Tipo.equals("Tenda"))||(Tipo.equals("TENDA")))) {
			System.out.println("Errore. Inserire risposta corretta \n");
			Tipo = scan.nextLine();
		}else inputvalido	= true;
		}
		
		inputvalido = false;
		while(!inputvalido);{
			System.out.println("Inserire categoria -economy- o -premium- \n");
			Categoria = scan.nextLine();
			//controllo se la risposta è economy o premium
			if(!((Categoria.equals("economy"))||(Categoria.equals("Economy"))||(Categoria.equals("ECONOMY"))||(Categoria.equals("premium"))||(Categoria.equals("Premium"))||(Categoria.equals("PREMIUM")))) {
				System.out.println("Errore. Inserire risposta corretta \n");
				Categoria = scan.nextLine();
			}else inputvalido= true;
		}
		if((Tipo.equals("camper"))||(Tipo.equals("Camper"))||(Tipo.equals("CAMPER"))) {
			if((Categoria.equals("economy"))||(Categoria.equals("Economy"))||(Categoria.equals("ECONOMY"))) {
				NomeSettore = 2; 
			}else {NomeSettore = 1;}
		}else if((Categoria.equals("economy"))||(Categoria.equals("Economy"))||(Categoria.equals("ECONOMY"))) {
				NomeSettore = 3;
			}else {NomeSettore = 4;}
		try {
		gestioneCampeggio.VisualizzaDisponibilità(DataInizio, DataFine, NomeSettore, Categoria, Tipo);
		}catch(OperationException e) {
			System.out.print(e.getMessage());}
	}
	
private static void EffettuaPrenotazione() {
		
		LocalDate DataInizio= null;
		LocalDate DataFine = null;

		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-gg");

		GestioneCampeggio gestioneCampeggio = GestioneCampeggio.getInstance();  
		int NomeSettore = 0;
		String Categoria = null; //premium o economy
		String Tipo = null; //camper o tende
		boolean inputvalido = false;
		String email = null;
		String risposta; 
		
		while(!inputvalido){
		System.out.println("Inserire data inizio yyyy-mm-gg\n");
		risposta = scan.nextLine();
		while(!inputvalido) {
			try {
				//conversione String in LocalDate
				DataInizio = LocalDate.parse(risposta);
				inputvalido = true;
			}catch (IllegalArgumentException error) {
				System.out.println("Errore nell'acquisizione di data inizio soggiorno, riprovare (metti bene grazie)\n");
				//System.out.println();//nuovo inserimento
				risposta = scan.nextLine();
				//scan.close();
		}
	}
		inputvalido = false;
		//inserire data fine soggiorno
		System.out.println("Inserire data fine gg-mm-yyyy\n");
		risposta = scan.nextLine();
		//scan.close();
		while(!inputvalido) {
			
			try {
				//conversione String in LocalDate
				DataFine = LocalDate.parse(risposta);
				inputvalido = true;
			}catch (IllegalArgumentException error) {
				System.out.println("Errore nell'acquisizione di data fine soggiorno, riprovare (metti bene grazie)\n");
				//System.out.println();//nuovo inserimento
				risposta = scan.nextLine();
				//scan.close();
			}
		}
		inputvalido = false;
		try {
		if(DataFine.isBefore(DataInizio)) {System.out.println("Errore nell'ordine dei soggiorni, riprovare (metti bene grazie)\n");}
		else inputvalido = true;
		}catch (IllegalArgumentException error) {}
			
		}
		inputvalido = false;
		
		System.out.println("Inserire il tipo -camper- o -tende-\n");
		Tipo = scan.nextLine();
		//scan.close();
		while(!inputvalido) {
			//controllo se la risposta è camper o tenda
		if(!((Tipo.equals("camper"))||(Tipo.equals("Camper"))||(Tipo.equals("CAMPER"))||(Tipo.equals("tenda"))||(Tipo.equals("Tenda"))||(Tipo.equals("TENDA")))) {
			System.out.println("Errore. Inserire risposta corretta \n");
			Tipo = scan.nextLine();
		}else inputvalido	= true;
		}
		
		inputvalido = false;
		while(!inputvalido);{
			System.out.println("Inserire categoria -economy- o -premium- \n");
			Categoria = scan.nextLine();
			//controllo se la risposta è economy o premium
			if(!((Categoria.equals("economy"))||(Categoria.equals("Economy"))||(Categoria.equals("ECONOMY"))||(Categoria.equals("premium"))||(Categoria.equals("Premium"))||(Categoria.equals("PREMIUM")))) {
				System.out.println("Errore. Inserire risposta corretta \n");
				Categoria = scan.nextLine();
			}else inputvalido= true;
		}
		inputvalido = false;
		while(!inputvalido) {
			System.out.println("Inserire email -esempiomail@...- \n");
			email = scan.nextLine();
			if (email.contains("@") && email.contains(".")) {
				inputvalido = true;
			} else {
				System.out.println("Email non valida.");
			}
		}
		if((Tipo.equals("camper"))||(Tipo.equals("Camper"))||(Tipo.equals("CAMPER"))) {
			if((Categoria.equals("economy"))||(Categoria.equals("Economy"))||(Categoria.equals("ECONOMY"))) {
				NomeSettore = 2; 
			}else {NomeSettore = 1;}
		}else if((Categoria.equals("economy"))||(Categoria.equals("Economy"))||(Categoria.equals("ECONOMY"))) {
				NomeSettore = 3;
			}else {NomeSettore = 4;}
		try {
			gestioneCampeggio.EffettuaPrenotazione(DataInizio, DataFine, NomeSettore, Categoria, Tipo, email);
			}catch(OperationException e) {System.out.println(e.getMessage());}	
		}

	private static void AcquistaServizio() {
		
		int codiceConto = 0;
		int codiceServizio = 0;
		String risposta = null;
		
		
		boolean inputvalido = false;
		boolean prova= true;
		GestioneCampeggio gestioneCampeggio = GestioneCampeggio.getInstance(); 
		while(!inputvalido) {
			System.out.println("inserire codice conto \n");
			risposta = scan.nextLine();
			for (int i = 0; i< risposta.length() && prova; i++) {
				if(risposta.charAt(i)<'0' || risposta.charAt(i) > '9'  ) {
					prova = false;
					System.out.println("Mettere solo numeri!!!");
				}
			}
			if(prova) {
				inputvalido = true;
				codiceConto = Integer.parseInt(risposta);
			}
		}
		prova = true;
		inputvalido = false;
		while(!inputvalido) {
			System.out.println("inserire codice servizio \n");
			risposta = scan.nextLine();
			for (int i = 0; i< risposta.length() && prova; i++) {
				if(risposta.charAt(i)<'0' || risposta.charAt(i) > '9'  ) {
					prova = false;
					System.out.println("Mettere solo numeri!!!");
				}
			}
			if(prova) {
				inputvalido = true;
				codiceServizio = Integer.parseInt(risposta);
			}
		}
		try {
			String acquisto = gestioneCampeggio.AcquistaServizio(codiceConto, codiceServizio);
			System.out.println(acquisto);
			}catch(OperationException e) {
				System.out.println(e.getMessage());}	
		}
		
	}