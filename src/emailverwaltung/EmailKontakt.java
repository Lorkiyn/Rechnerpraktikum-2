package emailverwaltung;

public class EmailKontakt {
	
	private int id = 0;
	private String firstName = null;
	private String name = null;
	private String email = null;
	
	public EmailKontakt(String vorname, String nachname, String email) {
		this.firstName = vorname;
		this.name = nachname;
		this.email = email;
		
	}
	
	public int getId() {
		return id;
		
	}
	
	public void setId(int id) {
		this.id = id;
		
	}
	
	public String getFirstName() {
		return firstName;
		
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		
	}
	
	public String getName() {
		return name;
		
	}
	
	public void setName(String name) {
		this.name = name;
		
	}
	
	public String getEmail() {
		return email;
		
	}
	
	public void setEmail(String email) {
		this.email = email;
		
	}
	
	public String toString() {
		return id +" " +firstName +" " +name +" " +email;
		
	}

}
