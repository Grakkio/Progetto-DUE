package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.ArrayList;

import entity.Piazzola;
import exception.DAOException;
import exception.DBConnectionException;

public class PiazzolaDAO {
	
	public static void createPiazzola (Piazzola p) throws DAOException, DBConnectionException{
		try {
			Connection conn = DBManager.getConnection();

			String query = "INSERT INTO PIAZZOLE VALUES (?,?);";

			try {
				PreparedStatement stmt = conn.prepareStatement(query);
				
				stmt.setInt(1, p.getIdPiazzola());
				stmt.setInt(2, p.getCodiceSettore());
				stmt.executeUpdate();

			}catch(SQLException e) {
				throw new DAOException("Errore scrittura Piazzola " + e.getMessage() + " " + e.getErrorCode());
			} finally {
				DBManager.closeConnection();
			}
		
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
	}
	
	public static ArrayList<Piazzola> readPiazzoleDisp(int codiceSettore, LocalDate inizio, LocalDate fine) throws DAOException, DBConnectionException {
		ArrayList<Piazzola> vettore = new ArrayList<Piazzola>(); 
		Piazzola eP = null;
		
		try {
			Connection conn = DBManager.getConnection();
			
			try {
				String query = "SELECT * FROM PIAZZOLE P JOIN SETTORI S ON P.SETTORE=? "
						+ "WHERE P.ID IN (SELECT PR.PIAZZOLA FROM PRENOTAZIONI PR WHERE PR.DATAFINE < ? OR PR.DATAINIZIO > ?);";
				
				PreparedStatement stmt = conn.prepareStatement(query);
				
				stmt.setInt(1, codiceSettore);
				stmt.setString(2, inizio.toString());
				stmt.setString(3, fine.toString());
				
				ResultSet result = stmt.executeQuery();
				
				while(result.next()) {
					eP = new Piazzola (result.getInt(1), result.getInt(2));
					vettore.add(eP);
				}
				
				
			}catch(SQLException e) {
				throw new DAOException("Errore lettura della piazzola: " + e.getMessage());
			}finally {
				DBManager.closeConnection();
			}	
		}catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione al DataBase");
		}
	
		if(vettore.size() == 0) {vettore = null;}
		return vettore;
		
	}
	
	public static Piazzola readPiazzola (int id) throws DAOException, DBConnectionException{
		Piazzola p = null;
		try {
			Connection conn = DBManager.getConnection();
			
			String query = "SELECT * FROM PIAZZOLE WHERE ID = ?;";
			
			try {
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setInt(1, id);
				
				ResultSet r = stmt.executeQuery();
				if(r.next()) {
					p = new Piazzola(r.getInt(1), r.getInt(2));
				}
			}catch(SQLException e) {
				throw new DAOException("Errore lettura Piazzola " + e.getMessage());
			}finally {
				DBManager.closeConnection();
			}
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
		return p;
	}
	
	public static void updatePiazzola(Piazzola p, int codice) throws DAOException, DBConnectionException{
		try {
			Connection conn = DBManager.getConnection();
			String query = "UPDATE PIAZZOLE SET SETTORE = ? WHERE ID = ?;";
			try {
				PreparedStatement stmt = conn.prepareStatement(query);
				
				stmt.setInt(1, p.getCodiceSettore());
				stmt.setInt(2, codice);
				
				stmt.executeUpdate();
			}catch(SQLException e) {
				throw new DAOException("Errore aggiornamento Piazzola");
			}
			finally {
				DBManager.closeConnection();
			}
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione database");
		}
	}
	
	public static void deletePiazzola(int codice) throws DAOException, DBConnectionException{
		try {
			Connection conn = DBManager.getConnection();
			String query = "DELETE FROM PIAZZOLE WHERE ID = ?;";
			try {
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setInt(1, codice);
				stmt.executeUpdate();
			}catch(SQLException e) {
				throw new DAOException("Errore cancellazione Piazzola");
			}
			finally {
				DBManager.closeConnection();
			}
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
	}

}