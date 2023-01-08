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

public class NewMemberAdd extends JFrame implements ActionListener{
	Connection cnt;
	User user;
	GroupChat gr = null;
	
	JTextField userName;
	JPanel panel;
	JFrame jfnew;
	
	public NewMemberAdd(Connection conn, User u, GroupChat groupChat) {
		cnt = conn;
		user = u;
		gr = groupChat;
		
		JLabel jUsernameLabel = new JLabel("User name: ");
	    userName = new JTextField(25);
	    
	    panel = new JPanel();
	    panel.add(jUsernameLabel);
	    panel.add(userName);
	    
	    jfnew = new JFrame("Thêm thành viên");
	    
	    JButton btn_addButton = new JButton("Ok");
	    
	    panel.add(btn_addButton);
	    
	    btn_addButton.addActionListener(this);
	    
	    jfnew.setLocationRelativeTo(null);
	    jfnew.setSize(300, 150);
	    jfnew.add(panel);
	    

	    panel.setVisible(true);
	    jfnew.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comStr = e.getActionCommand();
		if(comStr.equals("Ok")) {
			String getUserName = userName.getText();
			if (!getUserName.equals(null)) {
				GroupChatDB group = new GroupChatDB(cnt, user);
				group.addMember(gr, getUserName);
				jfnew.dispose();
			}
		}
	}
	
}
