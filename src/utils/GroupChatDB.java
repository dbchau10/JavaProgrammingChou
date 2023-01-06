package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	
	public Vector<GroupChat> getGroupJoin(){
		Vector<GroupChat> group = new Vector<GroupChat>();
		Statement stmt = null;
		String sql = "select group_chat.* from group_chat join group_member on group_chat.grchat_id = group_member.grchat_id where group_member.user_id ="+u.getID();
		
		try {
			cnt.setAutoCommit(false);
			stmt = cnt.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			cnt.commit();
			while(rs.next()) {
				GroupChat a = new GroupChat(rs.getString(1), rs.getString(2), rs.getString(3));
				group.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return group;
	}
	
	public Vector<Integer> getMember(String GRChatID){
		Vector<Integer> mem = new Vector<Integer>();
		Statement stmt = null;
		String sql = "SELECT user_id from group_member where grchat_id='" + GRChatID + "'";
		try {
			cnt.setAutoCommit(false);
			stmt = cnt.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			cnt.commit();
			while(rs.next()) {
				mem.add(Integer.parseInt(rs.getString(1)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mem;
	}
	
	public void addMember(String GRChatID, String username) {
		Statement stmt=null;
		String sql = "SELECT user_id from users where user_name='" + username +"'";
		boolean exsist = false;
		try {
			cnt.setAutoCommit(false);
			stmt = cnt.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			cnt.commit();
			if(rs.next()) {
				exsist = true;
				String addsql = "INSERT INTO group_member VALUES('" + GRChatID + "'," + rs.getString(1) + ",false)";
				stmt.executeUpdate(addsql);
			}
			if(exsist = false) {
				System.out.println("Ko ton tai");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void changeGroupName(String newname, GroupChat gr) {
		Statement stmt=null;
		String id = gr.getID();
		String sql = "UPDATE group_chat SET group_name ='" + newname + "' where grchat_id ='" + gr.getID() + "'"; 
		try {
			cnt.setAutoCommit(false);
			stmt = cnt.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean adminStatus(String GRChatID) {
		String check = "SELECT user_admin from group_member where grchat_id='" + GRChatID + "' and user_id=" + u.getID();
		Statement stmt = null;
		
		try {
			cnt.setAutoCommit(false);
			stmt = cnt.createStatement();
			ResultSet rscheck = stmt.executeQuery(check);
			cnt.commit();
			if(rscheck.next()) {
				if(rscheck.getString(1).equals("f")) {
					return false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public void deleteMember(String GRChatID, String username) {
		Statement stmt=null;
		String sql = "SELECT user_id from users where user_name='" + username +"'";
		try {
			cnt.setAutoCommit(false);
			stmt = cnt.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			cnt.commit();
			if(rs.next()) {
				String id = rs.getString(1);
				String delsql = "DELETE from group_member where user_id=" + id;
				stmt.executeUpdate(delsql);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String createGroupChat(Vector<Integer> ID, String groupname) {
		String GRChatID="";
		if(ID.size() <=0) {
			return "Not enough members";
		}
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
		LocalDateTime now = LocalDateTime.now();  
		
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
			String sqlcreate ="INSERT INTO group_chat VALUES('" + GRChatID + "','" + groupname + "','" + dtf.format(now)+"')";
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
	
	public ChatMessage SaveMessage(String msg, String id) {
		Statement stmt=null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		ChatMessage result = new ChatMessage(id, dtf.format(now), u.getID(), msg, u.getName());
		try {
			cnt.setAutoCommit(false);
			stmt = cnt.createStatement();
			String sql = "INSERT INTO group_chatmessage VALUES('" + id+ "','"+dtf.format(now)+"',"+u.getID()+",'"+msg+"')" ;
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
			String sql = "INSERT INTO erased_group_chatmessage VALUES('" + del.getID() + "','" + del.getMessage_date() +"','" + del.getUser_id() +"','" + del.message_inf +"','" + u.getID() +"')";
			stmt.executeUpdate(sql);
			cnt.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		GroupChatDB add = new GroupChatDB(conn, test);
		//Vector<Integer> us = new Vector<Integer>();
		//us.add(3);
		//us.add(4);
		add.getGroupJoin();
	}
}
