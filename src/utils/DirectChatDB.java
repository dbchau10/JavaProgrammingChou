package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import views.User;
import utils.ChatMessage;

public class DirectChatDB {
	Connection cnt;
	User u;
	static Vector<ChatMessage> messagehis = new Vector<ChatMessage>();
	public DirectChatDB(Connection conn, User you){
		cnt = conn;
		u = you;
	}
	
	public String GetDRChatID(String friendname) {
		String DRChatID="";
		String friendid="";

		Statement stmt=null;
		try {
			cnt.setAutoCommit(false);
			String sqlfindid = "SELECT * from users where user_name='" + friendname + "'";
			System.out.println(sqlfindid);
			stmt = cnt.createStatement();
			ResultSet rsid = stmt.executeQuery(sqlfindid);
			cnt.commit();
			if(rsid.next()) {
				friendid=rsid.getString(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(friendid);
		try {
			stmt = cnt.createStatement();
			String sql = "SELECT drchat_id from direct_chat where user_id=" + u.getID() +" and user_id2 ='"+friendid+"'" ;
			sql+= "UNION SELECT drchat_id from direct_chat where user_id2=" + u.getID() +" and user_id1 ='"+friendid+"'" ;
			ResultSet rs = stmt.executeQuery(sql);
			cnt.commit();
			if(rs.next()) {
				DRChatID = rs.getString(1);
				return DRChatID;
			}
			else {
				RandomPasswordGenerator gn = new RandomPasswordGenerator();
				DRChatID = gn.getNewDRChatID();
				String check = "SELECT * from direct_chat where drchat_id ='" + DRChatID +"'";
				ResultSet rscheck = stmt.executeQuery(check);
				cnt.commit();
				while(rscheck.next()) {
					rscheck.close();
					DRChatID = gn.getNewDRChatID();
					check = "SELECT * from direct_chat where drchat_id ='" + DRChatID +"'";
					rscheck = stmt.executeQuery(check);
					cnt.commit();
				}
				String sqlcreate = "INSERT INTO direct_chat VALUES('" + DRChatID + "'," + u.getID() + "," + friendid + ")";
				System.out.print(sqlcreate);
				stmt.executeUpdate(sqlcreate);
				cnt.commit();
			}
		} catch (SQLException e) {
			
		}
		
		return DRChatID;
	}
	
	public ChatMessage SaveMessage(String msg, String id) {
		Statement stmt=null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		ChatMessage result = new ChatMessage(id, dtf.format(now), u.getID(), msg, u.getName());
		try {
			cnt.setAutoCommit(false);
			stmt = cnt.createStatement();
			String sql = "INSERT INTO direct_chatmessage VALUES('" + id+ "','"+dtf.format(now)+"',"+u.getID()+",'"+msg+"')" ;
			stmt.executeUpdate(sql);
			cnt.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public void DeleteMessage(ChatMessage del) {
		Statement stmt=null;
		try {
			cnt.setAutoCommit(false);
			stmt = cnt.createStatement();
			String sql = "INSERT INTO erased_direct_chatmessage VALUES('" + del.getID() + "','" + del.getMessage_date() +"','" + del.getUser_id() +"','" + del.message_inf +"','" + u.getID() +"')";
			stmt.executeUpdate(sql);
			cnt.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void GetMessage(String id,DefaultTableModel chat) {
		Statement stmt=null;
		try {
			cnt.setAutoCommit(false);
			stmt = cnt.createStatement();
			String sql = "select * from ChatHistorydr('"+id+"',"+ u.getID()+")";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				ChatMessage chathis = new ChatMessage(rs.getString(1), rs.getString(2), Integer.parseInt(rs.getString(3)), rs.getString(4), rs.getString(5));
				chat.addRow(new Object[] {chathis.name+":"+chathis.message_inf});
				messagehis.add(chathis);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i =0; i<messagehis.size(); i++) {
			messagehis.elementAt(i).printChatMessage();
		}
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
		DirectChatDB add = new DirectChatDB(conn, test);
		//String id = add.GetDRChatID(7);
		//add.SaveMessage("pp", id);
		
		//add.GetMessage(id);
	}
}
