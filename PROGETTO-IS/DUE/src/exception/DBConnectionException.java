package exception;

@SuppressWarnings("serial")
public class DBConnectionException extends Exception{
	public DBConnectionException(String msg) {
		super(msg);
	}
}
