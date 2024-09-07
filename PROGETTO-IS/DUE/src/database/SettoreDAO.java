package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Settore;
import exception.DAOException;
import exception.DBConnectionException;

public class SettoreDAO {
	
	public static ArrayList<Settore> getSettori() throws DAOException, DBConnectionException {
		ArrayList<Settore> settori = new ArrayList<Settore>();
		try {
			Connection conn = DBManager.getConnection();
			try {
				String query = "SELECT * FROM SETTORI";
				
				PreparedStatement stmt = conn.prepareStatement(query);
				
				ResultSet result = stmt.executeQuery();
				
				while(result.next()) {
					Settore s = new Settore(result.getInt(1), result.getFloat(2), result.getString(3), result.getString(4));
					settori.add(s);
				}
				
			}catch(SQLException e) {
				throw new DAOException("Errore lettura del settore");
			}finally {
				DBManager.closeConnection();
			}	
		}catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione al DataBase");
		}
		if(settori.isEmpty()) {
			return null;
		}
		return settori;
	}
	
	public static Settore readSettore(int nomeSettore) throws DAOException, DBConnectionException {
		Settore eS = null;
		try {
			Connection conn = DBManager.getConnection();
			try {
				String query = "SELECT * FROM SETTORI WHERE CODICE=?;";
				
				PreparedStatement stmt = conn.prepareStatement(query);
				
				stmt.setInt(1, nomeSettore);

				ResultSet result = stmt.executeQuery();
				
				if(result.next()) {
					eS = new Settore(result.getInt(1), result.getFloat(2), result.getString(3), result.getString(4));	
				}
				
				
			}catch(SQLException e) {
				throw new DAOException("Errore lettura del settore");
			}finally {
				DBManager.closeConnection();
			}	
		}catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione al DataBase");
		}
		return eS;
	}
	
	
	public static void createSettore (Settore s) throws DAOException, DBConnectionException{
		try {
			Connection conn = DBManager.getConnection();
			try {
				String query = "INSERT INTO SETTORI VALUES (?,?,?,?);";
				
				PreparedStatement stmt = conn.prepareStatement(query);
				
				stmt.setInt(1, s.getCodiceSettore());
				stmt.setFloat(2, s.getCosto());
				stmt.setString(3, s.getCategoria());
				stmt.setString(4, s.getTipoSettore());
				
				stmt.executeUpdate();
			}catch(SQLException e) {
				throw new DAOException("Errore scrittura Settore " + e.getMessage() + " " + e.getErrorCode() );
			} finally {
				DBManager.closeConnection();
			}
		
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
	}
	
	public static void updateSettore(Settore s, int codice) throws DAOException, DBConnectionException{
		try {
			Connection conn = DBManager.getConnection();
			try {
				String query = "UPDATE SETTORI SET COSTO = ?, CATEGORIA = ?, TIPO = ? WHERE CODICE = ?;";
				
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setFloat(1, s.getCosto());
				stmt.setString(2, s.getCategoria());
				stmt.setString(3, s.getTipoSettore());
				stmt.setInt(4, codice);
				
				stmt.executeUpdate();
			}catch(SQLException e) {
				throw new DAOException("Errore aggiornamento Settore");
			}
			finally {
				DBManager.closeConnection();
			}
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
	}
	
	public static void deleteSettore(int codice) throws DAOException, DBConnectionException{
		try {
			Connection conn = DBManager.getConnection();
			try {
				String query = "DELETE FROM SETTORI WHERE CODICE = ?;";
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setInt(1, codice);
				stmt.executeUpdate();
			}catch(SQLException e) {
				throw new DAOException("Errore cancellazione Settore");
			}
			finally {
				DBManager.closeConnection();
			}
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
	}

}

