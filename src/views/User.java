package views;

public class User {
	private int id;
	private String username;
	private String hoten;
	private String diachi;
	private String email;
	private String dob;
	
	public User(int u_id){
		id=u_id;
	}
	
	User(int u_id, String u_username, String u_name, String u_dob, String u_email,String u_address){
		id=u_id;
		username=u_username;
		hoten = u_name;
		dob = u_dob;
		email = u_email;
		diachi = u_address;
	}
	
	public int getID(){
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getName() {
		return hoten;
	}
	
	public void setName(String name)
	{
		hoten = name;
	}
	
	public String getDOB()
	{
		return dob;
	}
	
	public void setDOB(String dob) {
		this.dob = dob;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getAddress() {
		return diachi;
	}
	
	public void setAddress(String address) {
		diachi = address;
	}
}
