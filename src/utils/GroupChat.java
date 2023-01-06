package utils;

public class GroupChat {
	String id;
	String groupname;
	String ngaytao;
	
	public GroupChat(String g_i, String g_n, String g_t){
		id = g_i;
		groupname = g_n;
		ngaytao = g_t;
	}
	
	public String getID() {
		return id;
	}
	
	public String getGroupname() {
		return groupname;
	}
	
}
