package exception;

@SuppressWarnings("serial")
public class DAOException extends Exception {
	public DAOException() {}
	
	public DAOException(String msg) {
		super(msg);
	}
}
