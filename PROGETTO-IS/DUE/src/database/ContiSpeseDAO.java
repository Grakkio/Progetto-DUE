package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.ContoSpese;
import exception.DAOException;
import exception.DBConnectionException;

public class ContiSpeseDAO {

	public static void createContoSpese (ContoSpese c) throws DAOException, DBConnectionException{
		try {
			Connection conn = DBManager.getConnection();


			try {
				
				String query = "INSERT INTO CONTISPESE(STATO, CLIENTE) VALUES (?,?);";
				
				PreparedStatement stmt = conn.prepareStatement(query);
			
				stmt.setString(1, c.getStato());
				stmt.setString(2, c.getClienteRegistrato());
				
				stmt.executeUpdate();

			}catch(SQLException e) {
				throw new DAOException("Errore scrittura Conto Spese " + e.getMessage() + " " + e.getErrorCode());
			} finally {
				DBManager.closeConnection();
			}
		
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
	}
	
	public static ContoSpese readContoSpese(int codice) throws DAOException, DBConnectionException{
		ContoSpese c = null;
		try {
			Connection conn = DBManager.getConnection();
			
			String query = "SELECT * FROM CONTISPESE WHERE CODICE = ?;";
			
			try {
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setInt(1, codice);
				
				ResultSet r = stmt.executeQuery();
				
				//ClienteRegistrato cl = ClienteRegistratoDAO.readClienteRegistrato(r.getString(3));
				
				if(r.next()) {
					c = new ContoSpese(
							r.getInt(1),
							r.getString(2),
							r.getString(3)
						);
				}
			}catch(SQLException e) {
				throw new DAOException("Errore lettura Conto Spese " + e.getMessage());
			}finally {
				DBManager.closeConnection();
			}
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
		return c;
	}
	
	public static void updateContoSpese(ContoSpese c, int codice) throws DAOException, DBConnectionException{
		try {
			Connection conn = DBManager.getConnection();
			String query = "UPDATE CONTISPESE SET STATO = ?, CLIENTE = ? WHERE CODICE = ?;";
			try {
				PreparedStatement stmt = conn.prepareStatement(query);
				
				stmt.setString(1, c.getStato());
				stmt.setString(2, c.getClienteRegistrato());
				stmt.setInt(3, codice);
				
				stmt.executeUpdate();
			}catch(SQLException e) {
				throw new DAOException("Errore aggiornamento Conto Spese");
			}
			finally {
				DBManager.closeConnection();
			}
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
	}
	
	public static void deleteContoSpese(int codice) throws DAOException, DBConnectionException{
		try {
			Connection conn = DBManager.getConnection();
			String query = "DELETE FROM CONTISPESE WHERE CODICE = ?;";
			try {
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setInt(1, codice);
				stmt.executeUpdate();
			}catch(SQLException e) {
				throw new DAOException("Errore cancellazione Conto Spese");
			}
			finally {
				DBManager.closeConnection();
			}
		}catch(SQLException e) {
			throw new DBConnectionException("Errore connessione al database");
		}
	}
	
	
	public static float getTotaleCorrente(int codiceConto) throws DAOException, DBConnectionException{
	    
	    float totaleCorrente = 0;
	    
	    try {
	      
	      Connection conn = DBManager.getConnection();
	      
	      try {
	        String query = "SELECT SUM(S.PREZZO) AS TOTALECORRENTE FROM SERVIZI S JOIN ACQUISTI A ON S.ID = A.SERVIZIO"
	            + " WHERE A.CONTOSPESA = ?;";
	        
	        PreparedStatement stmt = conn.prepareStatement(query);
	        
	        stmt.setInt(1, codiceConto);
	    
	        ResultSet result = stmt.executeQuery();
	        
	        if(result.next()) {
	          totaleCorrente = result.getFloat(1);  

	        }
	        
	        
	      }catch(SQLException e) {
	        throw new DAOException("Errore lettura del totale corrente");
	      }finally {
	        DBManager.closeConnection();
	      }  
	    }catch (SQLException e) {
	      throw new DBConnectionException("Errore di connessione al DataBase");
	    }
	    
	    return totaleCorrente;
	    
	  }
	
	public static ArrayList<Integer> getContiSpesaAttivi() throws DAOException, DBConnectionException{
	    
	    ArrayList<Integer> vettore= new ArrayList<Integer>(); 
	    
	    try {
	      
	      Connection conn = DBManager.getConnection();
	      
	      try {
	        String query = "SELECT CODICE FROM CONTISPESE WHERE STATO = 'ATTIVO'";
	        
	        PreparedStatement stmt = conn.prepareStatement(query);
	        
	        ResultSet result = stmt.executeQuery();
	        
	        while(result.next()) {
	          
	          vettore.add(result.getInt(1));
	        }
	        
	        
	      }catch(SQLException e) {
	        throw new DAOException("Errore lettura dei conti attivi");
	      }finally {
	        DBManager.closeConnection();
	      }  
	    }catch (SQLException e) {
	      throw new DBConnectionException("Errore di connessione al DataBase");
	    }
	    
	    if(vettore.size() == 0) {vettore=null;}
	    
	    return vettore;
	    
	  }
}