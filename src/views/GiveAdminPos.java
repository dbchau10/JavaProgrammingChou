package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import utils.GroupChat;
import utils.GroupChatDB;

public class GiveAdminPos extends JFrame implements ActionListener{
	Connection cnt;
	User user;
	GroupChat gr = null;
	
	JTextField userName;
	JPanel panel;
	JFrame jfadmin;
	
	public GiveAdminPos(Connection conn, User u, GroupChat groupChat) {
		cnt = conn;
		user = u;
		gr = groupChat;
		
		JLabel jUsernameLabel = new JLabel("User name: ");
	    userName = new JTextField(25);
	    
	    panel = new JPanel();
	    panel.add(jUsernameLabel);
	    panel.add(userName);
	    
	    jfadmin = new JFrame("Trao quyền admin");
	    
	    JButton btn_deleteButton = new JButton("Ok");
	    
	    panel.add(btn_deleteButton);
	    
	    btn_deleteButton.addActionListener(this);
	    
	    jfadmin.setLocationRelativeTo(null);
	    jfadmin.setSize(300, 150);
	    jfadmin.add(panel);
	    

	    panel.setVisible(true);
	    jfadmin.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comStr = e.getActionCommand();
		if(comStr.equals("Ok")) {
			String getUserName = userName.getText();
			if (!getUserName.equals(null)) {
				GroupChatDB group = new GroupChatDB(cnt, user);
				group.giveAdmin(gr, getUserName);
				jfadmin.dispose();
			}
		}
	}
	
//	public static void main(String[] args) {
//	Connection conn = null;
//	final String DB_URL = "jdbc:postgresql://localhost:5432/chatsystem";
//	final String USER = "postgres";
//	final String PASS = "Hotai382";
//	final String JBDC_DRIVER = "org.postgresql.Driver";
//	try {
//		Class.forName(JBDC_DRIVER);
//		System.out.println("Connecting to database...");
//		conn = DriverManager.getConnection(DB_URL, USER, PASS);
//		System.out.println("Success");
//	}
//	catch (Exception se) {
//		se.printStackTrace();
//		System.out.print("Cannot connect");
//		System.exit(1);
//	}
//	
//	User test = new User(2,"minh");
//	GroupChatDB gr = new GroupChatDB(conn, test);
//	Vector<GroupChat> allgroup = new Vector<GroupChat>();
//	allgroup = gr.getGroupJoin();
//	for(int i=0; i<allgroup.size(); i++) {
//		gr.getMember(allgroup.get(i));
//		System.out.println(allgroup.get(i).getID());
//	}
//	GiveAdminPos giveAdminPos = new GiveAdminPos(conn, test, allgroup.get(0));
//}
	
}
