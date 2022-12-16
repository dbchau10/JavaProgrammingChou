package views;

public class User {
	private int id;
	private String username;
	private String hoten;
	private String diachi;
	
	User(int u_id, String u_username, String u_name, String u_address){
		id=u_id;
		username=u_username;
		hoten = u_name;
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
	
	String getAddress() {
		return diachi;
	}
}
