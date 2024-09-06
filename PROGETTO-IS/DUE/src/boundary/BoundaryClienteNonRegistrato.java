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

public class BoundaryClienteNonRegistrato {

	static Scanner scan = new Scanner(System.in);
	public static void main(String[] args) {
		boolean exit = false;
		
		while(!exit) {
			System.out.println("Operatore");
			System.out.println("1. Visualizza Disponibilita'");
			System.out.println("2. Registrazione");
			System.out.println("3. Addio!");
			
			String op = scan.nextLine();

			if(op.equals("1")) {
				VisualizzaDisponibilità();
			} else if(op.equals("2")){
				Registrazione();
			}
			else if(op.equals("3")){
				exit = true;
			}else{
				System.out.println("Operazione non disponibile");
				System.out.println();
			}
		}	
		
		System.out.println("bye bye ;)");
		
	}


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
			}catch (IllegalArgumentException error) {
				//System.out.println("Errore nell'ordine dei soggiorni, riprovare (metti bene grazie)\n");
				}
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
				System.out.println(e.getMessage());
		}
	}
	
	private static void Registrazione() {
		String nome=null;
		String cognome=null;
		String email=null;
		String username=null;
		String password=null;
		String conferma=null;
		String risposta=null;
		int tel=0;
		GestioneCampeggio gestioneCampeggio = GestioneCampeggio.getInstance();
		boolean InputValido=false;
		boolean prova=true;
		
		
		
		while(!InputValido) {
			while(!prova) {
			prova=true;
			System.out.println("Inserire il proprio nome: \n");
			nome=scan.nextLine();
			for(int i=0; i<nome.length() && prova; i++) {
				if(nome.charAt(i)<='a'|| nome.charAt(i)>='z' && nome.charAt(i)<='A' || nome.charAt(i)>='Z') {
					prova=false;
					System.out.println("Inserire solo caratteri nel nome!!\n");
				}
			}
			}
			if(nome.length()>20){
				System.out.println("Non si possono inserire più di 20 caratteri! \n");
				InputValido=false;
				}else InputValido=true;
			}
		
			
		InputValido=false;
		while(!InputValido) {
			while(!prova) {
			prova=true;
			System.out.println("Inserire il proprio cognome: \n");
			cognome=scan.nextLine();
			for(int i=0; i<cognome.length() && prova; i++) {
				if(cognome.charAt(i)<='a'|| cognome.charAt(i)>='z' && cognome.charAt(i)<='A' || cognome.charAt(i)>='Z') {
					prova=false;
					System.out.println("Inserire solo caratteri nel cognome!!\n");
				}
			}
			}
			if(cognome.length()>20){
				System.out.println("Non si possono inserire più di 20 caratteri! \n");
				InputValido=false;
				}else InputValido=true;
			}
		
		
		InputValido = false;
		while(!InputValido) {
			System.out.println("Inserire email -esempiomail@...- \n");
			email = scan.nextLine();
			if (email.contains("@") && email.contains(".")) {
				InputValido = true;
			} else {
				System.out.println("Email non valida.");
			}
		}
		
		InputValido = false;
		while(!InputValido) {
			System.out.println("Impostare il proprio username -esempio.username03- \n");
			username = scan.nextLine();
			if(username.length()>20){
				System.out.println("Non si possono inserire più di 20 caratteri! \n");
				InputValido=false;
			}else InputValido=true;
		}
		
		InputValido = false;
		while(!InputValido) {
			System.out.println("Impostare la password dell'account: \n");
			password = scan.nextLine();
			if(username.length()<8){
				System.out.println("La password deve contenere almeno 8 caratteri \n");
				InputValido=false;
			}else InputValido=true;
		}
		
		prova = true;
		InputValido = false;
		while(!InputValido) {
			System.out.println("inserire il numero di telefono \n");
			risposta = scan.nextLine();
			for (int i = 0; i< risposta.length() && prova; i++) {
				if(risposta.charAt(i)<'0' || risposta.charAt(i) > '9'  ) {
					prova = false;
					System.out.println("Mettere solo numeri!!!");
				}
			}
			if(prova) {
				if(risposta.length()!=10) {
					System.out.println("Il numero di telefono è composto da 10 numeri!!!");
					InputValido = false;
				}else {
					tel= Integer.parseInt(risposta);
					InputValido = true;
				}
			}
		}
		
		try {
			conferma = gestioneCampeggio.Registrazione( nome, cognome, email, username, password, tel);
			System.out.println(conferma);
		}catch(OperationException e) {
			System.out.println(e.getMessage());
	}
		
		}
		
		
		
	}
	
	

	
	

	


	
	

