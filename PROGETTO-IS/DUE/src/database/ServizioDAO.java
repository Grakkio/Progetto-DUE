package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Servizio;
import exception.DAOException;
import exception.DBConnectionException;

public class ServizioDAO {

	public static Servizio readServizio(int id) throws DAOException, DBConnectionException {
		Servizio eS = null;
		try {
			Connection conn = DBManager.getConnection();
			try {
				String query = "SELECT * FROM SERVIZI WHERE ID=?";
				
				PreparedStatement stmt = conn.prepareStatement(query);
				
				stmt.setInt(1, id);

				ResultSet result = stmt.executeQuery();
				
				if(result.next()) {
					eS = new Servizio(
							result.getFloat(1),
							result.getString(2),
							result.getInt(3)
						);
				}
			}catch(SQLException e) {
				throw new DAOException("Errore lettura del servizio");
			}finally {
				DBManager.closeConnection();
			}	
		}catch (SQLException e) {
			throw new DBConnectionException("Errore di connessione al database");
		}
		return eS;
	}
	
	public static void createServizio (Servizio s) throws DAOException, DBConnectionException{
			try {
				Connection conn = DBManager.getConnection();
				try {
					String query = "INSERT INTO SERVIZI VALUES (?,?,?);";
					
					PreparedStatement stmt = conn.prepareStatement(query);
					
					stmt.setFloat(1, s.getPrezzoServizio());
					stmt.setString(2, s.getTipoServizio());
					stmt.setInt(3, s.getIdServizio());
					
					stmt.executeUpdate();
	
				}catch(SQLException e) {
					throw new DAOException("Errore scrittura Servizio " + e.getMessage() + " " + e.getErrorCode() );
				} finally {
					DBManager.closeConnection();
				}
			
			}catch(SQLException e) {
				throw new DBConnectionException("Errore connessione al database");
			}
		}
	
	public static void updateServizio(Servizio s, int codice) throws DAOException, DBConnectionException{
		try {
			Connection conn = DBManager.getConnection();
			try {
				String query = "UPDATE SERVIZI SET PREZZO = ?, TIPO = ? WHERE ID = ?;";
				
				PreparedStatement stmt = conn.prepareStatement(query);
				
				stmt.setFloat(1, s.getPrezzoServizio());
				stmt.setString(2, s.getTipoServizio());
				
				stmt.executeUpdate();
			}catch(SQLException e) {
				throw new DAOException("Errore aggiornamento Servizio");
			}
			finally {
				DBManager.closeConnection();
			}
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
	}
	
	public static void deleteServizio(int id) throws DAOException, DBConnectionException{
		try {
			Connection conn = DBManager.getConnection();
			try {
				String query = "DELETE FROM SERVIZI WHERE ID = ?;";
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setInt(1, id);
				stmt.executeUpdate();
			}catch(SQLException e) {
				throw new DAOException("Errore cancellazione Servizio");
			}
			finally {
				DBManager.closeConnection();
			}
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
	}
	
}
 
