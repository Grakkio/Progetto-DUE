package entity;

public class ClienteRegistrato {
	
	private String nome;
	private String cognome;
	private String mail;
	private String username;
	private String password;
	private int numeroTelefono;
	
	public ClienteRegistrato(String mail, String nome, String cognome, String username, String password, int numero) {
		
		this.nome = nome;
		this.cognome = cognome;
		this.mail = mail;
		this.username = username;
		this.password = password;
		this.numeroTelefono = numero;
	}
	
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return this.cognome;
	}
	
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMail() {
		return this.mail;
	}
	
	public void setUsername(String username) {
		this.username = username;

	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setPassword(String password) {
		this.password = password;

	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setNumeroTelefono(int numero) {
		this.numeroTelefono = numero;
	}
	
	public int getNumeroTelefono() {
		return this.numeroTelefono;
	}
	
	
}
