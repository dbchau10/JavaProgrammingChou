package views;

public class User {
	private int id;
	private String username;
	private String hoten;
	private String diachi;
	private String email;
	private String dob;
	
	User(int u_id, String u_username, String u_name, String u_dob, String u_email,String u_address){
		id=u_id;
		username=u_username;
		hoten = u_name;
		dob = u_dob;
		email = u_email;
		diachi = u_address;
	}
	
	int getID(){
		return id;
	}
	
	String getUsername() {
		return username;
	}
	
	String getName() {
		return hoten;
	}
	
	void setName(String name)
	{
		hoten = name;
	}
	
	String getDOB()
	{
		return dob;
	}
	
	void setDOB(String dob) {
		this.dob = dob;
	}
	
	String getEmail() {
		return email;
	}
	
	void setEmail(String email) {
		this.email = email;
	}
	
	
	String getAddress() {
		return diachi;
	}
	
	void setAddress(String address) {
		diachi = address;
	}
}
