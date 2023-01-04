package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	public void run()
    {
		while (true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listPending.removeAll();
			listPending.revalidate();
			listPending.repaint();
			
			System.out.println("cc sau 5 giay");
			PreparedStatement stm =null;
			ResultSet res = null;
			List<User>FriendList = new ArrayList<>();
			try {
				String sql = "SELECT * FROM USERS US LEFT JOIN FRIEND_WAITLINE FR ON US.USER_ID = FR.USER_ID1 WHERE FR.USER_ID2 IN (SELECT U.USER_ID FROM USERS U WHERE U.USER_NAME=?)";
				stm = conn.prepareStatement(sql);
				stm.setString(1, you.getUsername());
				res = stm.executeQuery();
				while(res.next()) {
					
					Integer id = res.getInt("user_id");
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
			
			for (int i = 0 ; i<FriendList.size(); i++)
			{
				FriendCom fr = new FriendCom(conn,you,FriendList.get(i));
				listPending.add(fr.initialize(FriendList.get(i).getUsername(),"Đồng ý"));
				JSeparator separator = new JSeparator();
				separator.setBounds(10, 33, 353, 2);
				
				listPending.add(separator);
			}
		}
    }
	
}
