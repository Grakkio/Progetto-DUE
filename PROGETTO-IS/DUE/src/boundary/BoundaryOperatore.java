package boundary;


import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import control.GestioneCampeggio;
import exception.OperationException;


public class BoundaryOperatore {
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {		
		boolean exit = false;
		
		while(!exit) {
			System.out.println("Operatore");
			System.out.println("1. Apertura Conto");
			System.out.println("2. Registrazione Pagamento");
			System.out.println("3. Addio!");
			
			String op = scan.nextLine();

			if(op.equals("1")) {
				aperturaConto();
			} else if(op.equals("2")){
				registrazionePagamento();
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



private static void aperturaConto() {
	String email= null;
	int codice=0;
	GestioneCampeggio gC = GestioneCampeggio.getInstance();
	
	try {
		System.out.println("Inserisci l'email del cliente: ");
		email = scan.nextLine();
		codice=gC.AperturaConto(email);
	}catch(OperationException e) {
		System.out.println(e.getMessage());
	}
	
	System.out.println("Il codice del conto spesa del cliente e' : " + codice + "\n");
	
}

private static void registrazionePagamento() {
	GestioneCampeggio gC = GestioneCampeggio.getInstance();
	int codiceConto=0;
	String risposta=null;
	String pagamento=null;
	boolean InputValido = false;
	boolean prova=true;
	
	try {
			while(!InputValido) {
				System.out.println(" Inserisci il codice del conto spesa del cliente: ");
				risposta = scan.nextLine();
				for (int i = 0; i< risposta.length() && prova; i++) {
					if(risposta.charAt(i)<'0' || risposta.charAt(i) > '9'  ) {
						prova = false;
						System.out.println("Mettere solo numeri!!!");
					}
				}
				if(prova) {
					InputValido = true;
					codiceConto = Integer.parseInt(risposta);
					}
				}
		
		gC.RegistrazionePagamento(codiceConto);
	}catch(OperationException e) {
		System.out.println(e.getMessage());
	}
	
	System.out.println("Digita 'Yes' per confermare il pagamento o qualunque altro carattere per annullare..");
	String conferma = scan.nextLine();
	
	try {
	if (!conferma.equals("Yes") || !conferma.equals("yes") || !conferma.equals("YES") ) {
		System.out.println("Pagamento non avvenuto \n");
	}
	else {
		System.out.println();
		System.out.println("Pagamento in corso..");
		TimeUnit.SECONDS.sleep(3);
		System.out.println("Pagamento effettuato!");
		pagamento=gC.salvaPagamento();
		System.out.println(pagamento);
	}
	}catch(OperationException e) {
		System.out.println(e.getMessage());
	}catch (Exception e) {
		System.out.println("Unexpected exception, riprovare..");
	}
	
}


}






