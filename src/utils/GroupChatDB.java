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
				System.out.println(rs.getString(1));
				GroupChat a = new GroupChat(rs.getString(1), rs.getString(2), rs.getString(3));
				group.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return group;
	}
	
	public Vector<Integer> getMember(GroupChat gr){
		Vector<Integer> mem = new Vector<Integer>();
		Statement stmt = null;
		String sql = "SELECT user_id from group_member where grchat_id='" + gr.getID() + "'";
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
	
	public void addMember(GroupChat gr, String username) {
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
				String addsql = "INSERT INTO group_member VALUES('" + gr.getID() + "'," + rs.getString(1) + ",false)";
				stmt.executeUpdate(addsql);
				cnt.commit();
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
			cnt.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean adminStatus(GroupChat gr) {
		String check = "SELECT user_admin from group_member where grchat_id='" + gr.getID() + "' and user_id=" + u.getID();
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
	
	public void giveAdmin(GroupChat gr, String username) {
		Statement stmt=null;
		if (!adminStatus(gr)) {
			System.out.println("Ko phai admin");
			return;
		}
		String sql = "SELECT user_id from users where user_name='" + username +"'";
		try {
			cnt.setAutoCommit(false);
			stmt = cnt.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			cnt.commit();
			if(rs.next()) {
				String id = rs.getString(1);
				String upsql = "UPDATE group_member SET user_admin=true where user_id=" + id + " and grchat_id='" + gr.getID() +"'";
				stmt.executeUpdate(upsql);
				cnt.commit();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteMember(GroupChat gr, String username) {
		Statement stmt=null;
		if (!adminStatus(gr)) {
			System.out.println("Ko phai admin");
			return;
		}
		String sql = "SELECT user_id from users where user_name='" + username +"'";
		System.out.println(sql);
		try {
			cnt.setAutoCommit(false);
			stmt = cnt.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			cnt.commit();
			if(rs.next()) {
				String id = rs.getString(1);
				String delsql = "DELETE from group_member where user_id=" + id + " and grchat_id='" + gr.getID() +"'";
				System.out.println(delsql);
				stmt.executeUpdate(delsql);
				cnt.commit();
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
	
	public ChatMessage SaveMessage(String msg, GroupChat gr) {
		Statement stmt=null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		ChatMessage result = new ChatMessage(gr.getID(), dtf.format(now), u.getID(), msg, u.getName());
		try {
			cnt.setAutoCommit(false);
			stmt = cnt.createStatement();
			String sql = "INSERT INTO group_chatmessage VALUES('" + gr.getID()+ "','"+dtf.format(now)+"',"+u.getID()+",'"+msg+"')" ;
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
	
	public void GetMessage(GroupChat gr) {
		Statement stmt=null;
		try {
			cnt.setAutoCommit(false);
			stmt = cnt.createStatement();
			String sql = "select * from ChatHistorygr('"+gr.getID()+"',"+ u.getID()+")";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				ChatMessage chathis = new ChatMessage(rs.getString(1), rs.getString(2), Integer.parseInt(rs.getString(3)), rs.getString(4), rs.getString(5));
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
		
		/*Statement stmt=null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String sql = "SELECT * from getfriendlist(2)";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				System.out.println(rs.getString(1));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
