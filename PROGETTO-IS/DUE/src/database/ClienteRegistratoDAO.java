package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.ClienteRegistrato;
import exception.DAOException;
import exception.DBConnectionException;

public class ClienteRegistratoDAO {
	
	public static void createClienteRegistrato (ClienteRegistrato c) throws DAOException, DBConnectionException{
		try {
			Connection conn = DBManager.getConnection();

			
			try {
				
				String query = "INSERT INTO CLIENTIREGISTRATI VALUES (?,?,?,?,?,?);";
				
				PreparedStatement stmt = conn.prepareStatement(query);
				
				stmt.setString(1, c.getMail());
				stmt.setString(2, c.getNome());
				stmt.setString(3, c.getCognome());
				stmt.setString(4, c.getUsername());
				stmt.setString(5, c.getPassword());
				stmt.setInt(6, c.getNumeroTelefono());
				
				stmt.executeUpdate();

			}catch(SQLException e) {
				throw new DAOException("Errore scrittura Cliente Registrato " + e.getMessage() + " " + e.getErrorCode());
			} finally {
				DBManager.closeConnection();
			}
		
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
	}
	
	public static ClienteRegistrato readClienteRegistrato (String email) throws DAOException, DBConnectionException {
		
		ClienteRegistrato eC = null;
		
		try {
			
			Connection conn = DBManager.getConnection();
			
			try {
				String query = "SELECT * FROM CLIENTIREGISTRATI WHERE EMAIL=?;";
				
				PreparedStatement stmt = conn.prepareStatement(query);
				
				stmt.setString(1, email);
		
				ResultSet result = stmt.executeQuery();
				
				if(result.next()) {
					eC = new ClienteRegistrato (result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getInt(6));	

				}
				
				
			}catch(SQLException e) {
				throw new DAOException("Errore lettura del cliente Registrato");
			}finally {
				DBManager.closeConnection();
			}	
		}catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione al DataBase");
		}
		
		
		return eC;
		
	}
	
	public static void updateClienteRegistrato(ClienteRegistrato c, String email) throws DAOException, DBConnectionException{
		try {
			Connection conn = DBManager.getConnection();
			String query = "UPDATE CLIENTIREGISTRATI SET NOME = ?, COGNOME = ?, USERNAME = ?, PASSWORD = ?, TELEFONO = ? WHERE EMAIL = ?;";
			try {
				PreparedStatement stmt = conn.prepareStatement(query);

				stmt.setString(1, c.getNome());
				stmt.setString(2, c.getCognome());
				stmt.setString(3, c.getUsername());
				stmt.setString(4, c.getPassword());
				stmt.setInt(5, c.getNumeroTelefono());
				
				stmt.executeUpdate();
			}catch(SQLException e) {
				throw new DAOException("Errore aggiornamento Cliente Registrato");
			}
			finally {
				DBManager.closeConnection();
			}
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
	}
	
	public static void deleteClienteRegistrato(String email) throws DAOException, DBConnectionException {
		try {
			Connection conn = DBManager.getConnection();
			String query = "DELETE FROM CLIENTIREGISTRATI WHERE EMAIL = ?;";
			try {
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setString(1, email);
				stmt.executeUpdate();
			}catch(SQLException e) {
				throw new DAOException("Errore cancellazione Cliente Registrato");
			}
			finally {
				DBManager.closeConnection();
			}
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
	}
	

}