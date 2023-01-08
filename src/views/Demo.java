package views;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;

import utils.GroupChatDB;
import utils.GroupChat;

public class Demo {
	public static void main(String[] args) {
		Connection conn = null;
		final String DB_URL = "jdbc:postgresql://localhost:5432/chatsystem";
		final String USER = "postgres";
		final String PASS = "Hotai382";
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
		
		User test = new User(1,"joseph");
		GroupChatDB gr = new GroupChatDB(conn, test);
		Vector<GroupChat> allgroup = new Vector<GroupChat>();
		allgroup = gr.getGroupJoin();
		for(int i=0; i<allgroup.size(); i++) {
			gr.getMember(allgroup.get(i));
			System.out.println(allgroup.get(i).getID());
		}
		DeleteMemberPopUp deleteMemberPopUp = new DeleteMemberPopUp(conn, test, allgroup.get(0));
	}
}
