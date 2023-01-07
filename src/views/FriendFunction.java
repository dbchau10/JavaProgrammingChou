package views;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FriendFunction {
	Connection cnt;
	User u;
	public FriendFunction(Connection conn, User you){
		cnt = conn;
		u = you;
	}
	
	public int DeleteFriend(String username) {
		Statement stmt=null;
		String friendid ="";
		
		try {
			cnt.setAutoCommit(false);
			stmt = cnt.createStatement();
			String sql = "SELECT user_id from users where user_name='" + username + "'";
			ResultSet rs = stmt.executeQuery(sql);
			cnt.commit();
			
			if(rs.next()) {
				System.out.println(rs.getString(1));
				friendid = rs.getString(1);
			}
			
			String del="";
			
			String delsql = "SELECT * from friendlist where user_id1=" + u.getID() +" and user_id2=" +friendid;
			ResultSet rs1 = stmt.executeQuery(delsql);
			cnt.commit();
			if(rs1.next()) {
				del = "DELETE from friendlist where user_id1=" + u.getID() +" and user_id2=" +friendid;
			}
			else {
				del = "DELETE from friendlist where user_id2=" + u.getID() +" and user_id1=" +friendid;
			}
			
			stmt.executeUpdate(del);
			cnt.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 1;
	}
	
	public int DeleteRequest(String username) {
		Statement stmt=null;
		String friendid ="";
		
		try {
			cnt.setAutoCommit(false);
			stmt = cnt.createStatement();
			String sql = "SELECT user_id from users where user_name='" + username + "'";
			ResultSet rs = stmt.executeQuery(sql);
			cnt.commit();
			
			if(rs.next()) {
				System.out.println(rs.getString(1));
				friendid = rs.getString(1);
			}
			
			String delsql = "DELETE from friend_waitline where user_id1=" + u.getID() +" and user_id2=" +friendid;
			System.out.println(delsql);
			stmt.executeUpdate(delsql);
			cnt.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return 1;
	}
	
	public int AcceptRequest(String username) {
		Statement stmt=null;
		String friendid ="";
		
		try {
			cnt.setAutoCommit(false);
			stmt = cnt.createStatement();
			String sql = "SELECT user_id from users where user_name='" + username + "'";
			ResultSet rs = stmt.executeQuery(sql);
			cnt.commit();
			
			if(rs.next()) {
				System.out.println(rs.getString(1));
				friendid = rs.getString(1);
			}
			
			String reqsql = "INSERT INTO friendlist values(" + friendid + "," + u.getID()+")";
			System.out.println(reqsql);
			stmt.executeUpdate(reqsql);
			cnt.commit();
			
			String delsql = "DELETE from friend_waitline where user_id1=" + u.getID() +" and user_id2=" +friendid;
			System.out.println(delsql);
			stmt.executeUpdate(delsql);
			cnt.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 1;
	}
	
	public int FriendRequest(String username){
		Statement stmt=null;
		String wannabe = "";
		try {
			cnt.setAutoCommit(false);
			stmt = cnt.createStatement();
			String sql = "SELECT user_id from users where user_name='" + username + "'";
			ResultSet rs = stmt.executeQuery(sql);
			cnt.commit();

			if(rs.next()) {
				System.out.println(rs.getString(1));
				wannabe = rs.getString(1);
			}
			else {
				System.out.println("No username exists");
				return 0;
			}
			
			String checksame = "SELECT * from friend_waitline where user_id1=" + wannabe +" and user_id2=" +u.getID();
			System.out.println(checksame);
			ResultSet check = stmt.executeQuery(checksame);
			cnt.commit();
			
			if(check.next()) {
				check.close();
				System.out.println("Already sent");
				return -1;
			}
			
			String checksent = "SELECT * from friend_waitline where user_id2=" + wannabe +" and user_id1=" +u.getID();
			check = stmt.executeQuery(checksent);
			cnt.commit();
			if(check.next()) {
				check.close();
				System.out.println("Person is in your waitline");
				return -2;
			}
			
			String reqsql = "INSERT INTO friend_waitline values(" + wannabe + "," + u.getID()+")";
			System.out.println(reqsql);
			stmt.executeUpdate(reqsql);
			cnt.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}
		return 1;
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
	}
}
