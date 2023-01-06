package utils;

public class ChatMessage {
	String id;
	String message_date;
	int user_id;
	String message_inf;
	String name;
	
	public ChatMessage( String c_id,String c_m,int c_u,String c_i, String c_n) {
		id = c_id;
		message_date = c_m;
		user_id = c_u;
		message_inf = c_i;
		name = c_n;
	}
	
	public String getID() {
		return id;
	}
	
	public String getMessage_date() {
		return message_date;
	}
	
	public String getMessage_info() {
		return message_inf;
	}
	
	public int getUser_id() {
		return user_id;
	}
	
	public void printChatMessage() {
		System.out.println(name + " :" + message_inf);
	}
}
