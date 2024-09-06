package boundary;
import java.util.Scanner;

import control.GestioneCampeggio;
import exception.OperationException;

public class BoundaryGestoreCampeggio {
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {		
		boolean exit = false;
		
		while(!exit) {
			System.out.println("Gestore Campeggio ");
			System.out.println("1. Inserisci Piazzola");
			System.out.println("2. Addio");
			
			
			String op = scan.nextLine();

			if(op.equals("1")) {
				inserisciPiazzola();
			} 
			else if(op.equals("2")){
				exit = true;
			}else{
				System.out.println("Operazione non disponibile");
				System.out.println();
			}
		}	
		
		System.out.println("bye bye ;)");
		
	}
	
private static void inserisciPiazzola() {
	int piazzola=0;
	int settore=0;
	GestioneCampeggio gC = GestioneCampeggio.getInstance();
	String risposta=null;
	boolean InputValido = false;
	boolean prova=true;
	try {
		while(!InputValido) {
			System.out.println("inserire il codice della piazzola \n");
			risposta = scan.nextLine();
			for (int i = 0; i< risposta.length() && prova; i++) {
				if(risposta.charAt(i)<'0' || risposta.charAt(i) > '9'  ) {
					prova = false;
					System.out.println("Mettere solo numeri!!!");
				}
			}
			if(prova) {
				InputValido = true;
				piazzola = Integer.parseInt(risposta);
				}
			}
		
		InputValido = false;
		prova=true;
		while(!InputValido) {
			System.out.println("Inserisci il codice del settore in cui inserire la piazzola: ");
			risposta = scan.nextLine();
			for (int i = 0; i< risposta.length() && prova; i++) {
				if(risposta.charAt(i)<'0' || risposta.charAt(i) > '9'  ) {
					prova = false;
					System.out.println("Mettere solo numeri!!!");
				}
			}
			if(prova) {
				InputValido = true;
				settore = Integer.parseInt(risposta);
				}
			}
		
		gC.InsericiPiazzola(piazzola,settore);
		
	}catch(OperationException e) {
		System.out.println(e.getMessage());
	}
	
	System.out.println("piazzola inserita correttamente!");
	
}
	

	
}
