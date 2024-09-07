package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import entity.Prenotazione;
import exception.DAOException;
import exception.DBConnectionException;

public class PrenotazioneDAO {
	
	public static int getMaxCodicePrenotazione(String email) throws DAOException, DBConnectionException {
		
		int codice = -1;
		
		try {
			
			Connection conn = DBManager.getConnection();
			
			try {
				String query = "SELECT MAX(CODICE) AS MASSIMO FROM PRENOTAZIONI WHERE CLIENTE=?;";
				
				PreparedStatement stmt = conn.prepareStatement(query);
				
				stmt.setString(1, email);
		
				ResultSet result = stmt.executeQuery();
				
				if(result.next()) {
					codice = result.getInt(1);	

				}
				
				
			}catch(SQLException e) {
				throw new DAOException("Errore lettura del codice della prenotazione");
			}finally {
				DBManager.closeConnection();
			}	
		}catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione al DataBase");
		}
		
		
		return codice;
		
	}
	
	public static void createPrenotazione(Prenotazione p) throws DAOException, DBConnectionException{
		try {
			Connection conn = DBManager.getConnection();

			String query = "INSERT INTO PRENOTAZIONI(DATAINIZIO, DATAFINE, PREZZO, CLIENTE, PIAZZOLA) VALUES (?,?,?,?,?);";

			try {
				PreparedStatement stmt = conn.prepareStatement(query);
				
				stmt.setString(1, p.getDataInizio().toString());
				stmt.setString(2, p.getDataFine().toString());
				stmt.setFloat(3, p.getPrezzoPrenotazione());
				stmt.setString(4, p.getClienteRegistrato());
				stmt.setInt(5, p.getPiazzola());
				
				stmt.executeUpdate();
			}catch(SQLException e) {
				throw new DAOException("Errore scrittura Prenotazione " + e.getMessage() + " " + e.getErrorCode());
			} finally {
				DBManager.closeConnection();
			}
		
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
	}
	
	public static Prenotazione readPrenotazione (int codice) throws DAOException, DBConnectionException{
		Prenotazione p = null;
		try {
			Connection conn = DBManager.getConnection();
			
			
			try {
				
				String query = "SELECT * FROM PRENOTAZIONI WHERE CODICE = ?;";

				PreparedStatement stmt = conn.prepareStatement(query);

				stmt.setInt(1, codice);
				
				ResultSet r = stmt.executeQuery();
				
				//ClienteRegistrato c = ClienteRegistratoDAO.readClienteRegistrato(r.getString(5));
				//Piazzola pp = PiazzolaDAO.readPiazzola(r.getInt(6));
				
				if(r.next()) {
					p = new Prenotazione(
							r.getInt(1),
							LocalDate.parse(r.getString(2), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
							LocalDate.parse(r.getString(3), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
							r.getFloat(4),
							r.getString(5), 
							r.getInt(4)
						);
				}
			}catch(SQLException e) {
				throw new DAOException("Errore lettura Prenotazione " + e.getMessage());
			}finally {
				DBManager.closeConnection();
			}
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
		return p;
	}
	
	public static void updatePrenotazione(Prenotazione p, int codice) throws DAOException, DBConnectionException{
		try {
			Connection conn = DBManager.getConnection();
			String query = "UPDATE PRENOTAZIONI SET DATAINIZIO = ?, DATAFINE = ?, PREZZO = ?, CLIENTE = ?, PIAZZOLA = ?, WHERE CODICE = ?;";
			try {
				PreparedStatement stmt = conn.prepareStatement(query);

				stmt.setString(1, p.getDataInizio().toString());
				stmt.setString(2, p.getDataFine().toString());
				stmt.setFloat(3, p.getPrezzoPrenotazione());
				stmt.setString(4, p.getClienteRegistrato());
				stmt.setInt(5, p.getPiazzola());
				stmt.setInt(6, codice);
				
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
	
	public static void deletePrenotazione(int codice) throws DAOException, DBConnectionException{
		try {
			Connection conn = DBManager.getConnection();
			String query = "DELETE FROM PRENOTAZIONI WHERE CODICE = ?;";
			try {
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setInt(1, codice);
				stmt.executeUpdate();
			}catch(SQLException e) {
				throw new DAOException("Errore cancellazione Prenotazione");
			}
			finally {
				DBManager.closeConnection();
			}
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
	}
}

