package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import entity.Servizio;
import entity.Acquisto;
import exception.DAOException;
import exception.DBConnectionException;

public class AcquistoDAO {
	
	public static void createAcquisto(Acquisto a) throws DAOException, DBConnectionException{
		
		//String data = a.getDataAcquisto().toString();
	//	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		//String orario = a.getOraAcquisto().format(formatter);

		try {
			Connection conn = DBManager.getConnection();

			String query = "INSERT INTO ACQUISTI(CONTOSPESA, SERVIZIO, DATAACQUISTO) VALUES (?, ?, SYSDATE());";

			try {
				PreparedStatement stmt = conn.prepareStatement(query);
				
				stmt.setInt(1, a.getContoSpesa());
				stmt.setInt(2, a.getServizio());
				//stmt.setString(3, data);
				//stmt.setString(4, orario);
				stmt.executeUpdate();


			}catch(SQLException e) {
				throw new DAOException("Errore scrittura Acquisto");
			} finally {
				DBManager.closeConnection();
			}
		
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione database");
		}
	}
	
	
	public static ArrayList<Servizio> getListaServizi(int codice) throws DAOException, DBConnectionException {
		
		ArrayList<Servizio> vettore = new ArrayList<Servizio>();
		Servizio eS;
		
		try {
			Connection conn = DBManager.getConnection();
			
			try {
				String query = "SELECT * FROM SERVIZI S JOIN ACQUISTI A ON S.ID = A.SERVIZIO "
						+ "WHERE A.CONTOSPESA = ?";
				
				PreparedStatement stmt = conn.prepareStatement(query);
				
				stmt.setInt(1, codice);
				
				ResultSet result = stmt.executeQuery();
				
				while(result.next()) {
					eS = new Servizio(result.getFloat(1), result.getString(2), result.getInt(3));
					vettore.add(eS);
				}
				
				
			}catch(SQLException e) {
				throw new DAOException("Errore lettura dell'acquisto: " + e.getMessage());
			}finally {
				DBManager.closeConnection();
			}	
		}catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione al DataBase");
		}
		
		if (vettore.size() == 0) {vettore = null;}
		
		return vettore;
	}
	
	
	public static Acquisto readAquisto(int codice) throws DAOException, DBConnectionException{
		Acquisto a = null;
		
		try {
			Connection conn = DBManager.getConnection();
			
			
			try {
				
				String query = "SELECT * FROM ACQUISTI WHERE CODICEACQUISTO = ?;";
				
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setInt(1, codice);
				
				ResultSet r = stmt.executeQuery();
				
				if(r.next()) {
					a = new Acquisto(r.getInt(1),
							r.getInt(2),
							LocalDate.parse(r.getString(3), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
							r.getInt(4)
						);
				}
				else {
					throw new DAOException("Errore: Nessun acquisto effettuato!");
				}
				
			}catch(SQLException e) {
				throw new DAOException("Errore lettura Acquisto " + e.getMessage());
			}finally {
				DBManager.closeConnection();
			}
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
		return a;
	}
	
	public static void deleteAcquisto (int codice) throws DAOException, DBConnectionException{
		try {
			Connection conn = DBManager.getConnection();
			String query = "DELETE FROM ACQUISTI WHERE CODICE = ?;";
			try {
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setInt(1, codice);
				stmt.executeUpdate();
//				System.out.println("debug");
			}catch(SQLException e) {
				throw new DAOException("Errore cancellazione acquisto..." + e.getMessage());
			}
			finally {
				DBManager.closeConnection();
			}
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione database...");
		}
	}
	
	
	
	public static void updateAcquisto(Acquisto a, int codice) throws DAOException, DBConnectionException{
		try {
			Connection conn = DBManager.getConnection();
			String query = "UPDATE ACQUISTI SET CONTOSPESA = ?, SERVIZIO = ?, DATAACQUISTO = ? WHERE CODICE = ?;";
			try {
				PreparedStatement stmt = conn.prepareStatement(query);

				stmt.setInt(1, a.getContoSpesa());
				stmt.setInt(2, a.getServizio());
				stmt.setString(3, a.getDataAcquisto().toString());
				stmt.setInt(4, codice);
				
				stmt.executeUpdate();
			}catch(SQLException e) {
				throw new DAOException("Errore aggiornamento Prenotazione");
			}
			finally {
				DBManager.closeConnection();
			}
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
	}
	
	

}
