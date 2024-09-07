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

public class BoundaryTempo {
	static Scanner scan = new Scanner(System.in);
	static long attesa= 120000;
	public static void main(String[] args) {
		try {
		Thread thread = new Thread();
	    thread.start();
	    while(thread.isAlive()) {
	    InvioElencoSpese();
	    thread.sleep(attesa);
	    }
		
		}catch(InterruptedException e) {
			System.out.println("oPS qualcosa e' andato storto.\n");
			}
		
	}
	
	
	private static void InvioElencoSpese() {
		try {
		GestioneCampeggio gestioneCampeggio = GestioneCampeggio.getInstance();  
		gestioneCampeggio.invioElencoSpese();
		}catch(OperationException e) {
			System.out.println(e.getMessage());}
	}

}