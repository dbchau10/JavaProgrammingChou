package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;

import components.FriendCom;

public class Thread_PendingTab implements Runnable {
	private User you;
	private Connection conn;
	JPanel listPending;
	Thread_PendingTab(Connection cnt, User you,JPanel listPending){	
		this.you = you;
		this.conn = cnt;
		this.listPending = listPending;
	}
	
	private List<Integer>oldPending = new ArrayList<>();
	public void run()
    {
		while (true) {

			System.out.println("cc sau 5 giay");
			Statement stm =null;
			ResultSet res = null;
			List<User>FriendList = new ArrayList<>();
			List<Integer>newPending = new ArrayList<>();
			try {
				String sql = "SELECT * FROM USERS US LEFT JOIN FRIEND_WAITLINE FR ON US.USER_ID = FR.USER_ID2 WHERE FR.USER_ID1 IN (SELECT U.USER_ID FROM USERS U WHERE U.USER_NAME='"+you.getUsername()+"')";
				
				stm = conn.createStatement();
				res = stm.executeQuery(sql);
				conn.commit();
				while(res.next()) {
					System.out.print("hello");
					Integer id = res.getInt("user_id");
					
					newPending.add(id);
					String username = res.getString("user_name");
					String hoten = res.getString("user_hoten");
					String dob = res.getString("user_ngaysinh");
					String email = res.getString("user_email");
					String address = res.getString("user_diachi");
					User user = new User(id,username,hoten,dob,email,address);
					System.out.print(user.getUsername());
					FriendList.add(user);
					
				}
			} 
			
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			
			System.out.println(FriendList.size());
			if (!oldPending.equals(newPending))
			{
				
				listPending.removeAll();
				listPending.revalidate();
				listPending.repaint();
				oldPending=newPending;
				for (int i = 0 ; i<FriendList.size(); i++)
				{
					FriendCom fr = new FriendCom(conn,you,FriendList.get(i));
					listPending.add(fr.initialize(FriendList.get(i).getUsername(),"Chấp nhận"));
					JSeparator separator = new JSeparator();
					separator.setBounds(10, 33, 353, 2);
					
					listPending.add(separator);
				}

			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}}
	
}
