package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import views.User;

public class GroupChatDB {
	Connection cnt;
	User u;
	Vector<ChatMessage> messagehis = new Vector<ChatMessage>();
	public GroupChatDB(Connection conn, User you){
		cnt = conn;
		u = you;
	}
	
	public String createGroupChat(Vector<Integer> ID, String groupname) {
		String GRChatID="";
		if(ID.size() <=0) {
			return "Not enough members";
		}
		
		RandomPasswordGenerator gn = new RandomPasswordGenerator();
		GRChatID = gn.getNewGRChatID();
		
		Statement stmt = null;
		
		try {
			cnt.setAutoCommit(false);
			stmt = cnt.createStatement();
			String check = "SELECT * from group_chat where grchat_id ='" + GRChatID +"'";
			ResultSet rscheck = stmt.executeQuery(check);
			cnt.commit();
			while(rscheck.next()) {
				rscheck.close();
				GRChatID = gn.getNewDRChatID();
				check = "SELECT * from group_chat where grchat_id ='" + GRChatID +"'";
				rscheck = stmt.executeQuery(check);
				cnt.commit();
			}
			String sqlcreate ="INSERT INTO group_chat VALUES('" + GRChatID + "','" + groupname + "')";
			System.out.print(sqlcreate);
			stmt.executeUpdate(sqlcreate);
			cnt.commit();
			
			String sqladdu = "INSERT INTO group_member VALUES('" + GRChatID + "'," + u.getID() + ",true)";
			stmt.executeUpdate(sqladdu);
			cnt.commit();
			
			for(int i=0; i<ID.size(); i++) {
				String sqladd = "INSERT INTO group_member VALUES('" + GRChatID + "'," + ID.get(i) + ",false)";
				stmt.executeUpdate(sqladd);
				cnt.commit();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return GRChatID;
	}
	
	public static void main(String[] args) {
		Connection conn = null;
		final String DB_URL = "jdbc:postgresql://localhost:5432/test";
		final String USER = "postgres";
		final String PASS = "192002";
		final String JBDC_DRIVER = "org.postgresql.Driver";
		try {
			Class.forName(JBDC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Success");
		}
		catch (Exception se) {
			se.printStackTrace();
			System.out.print("Cannot connect");
			System.exit(1);
		}
		
		User test = new User(2);
		GroupChatDB add = new GroupChatDB(conn, test);
		
		Vector<Integer> list = new Vector<Integer>();
		list.add(3);
		list.add(4);
		add.createGroupChat(list, "test");
	}
}
