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

public class DeleteMemberPopUp extends JFrame implements ActionListener{
	Connection cnt;
	User user;
	GroupChat gr = null;
	
	JTextField userName;
	JPanel panel;
	JFrame jfxoa;
	
	public DeleteMemberPopUp(Connection conn, User u, GroupChat groupChat) {
		cnt = conn;
		user = u;
		gr = groupChat;
		
		JLabel jUsernameLabel = new JLabel("User name: ");
	    userName = new JTextField(25);
	    
	    panel = new JPanel();
	    panel.add(jUsernameLabel);
	    panel.add(userName);
	    
	    jfxoa = new JFrame("Xóa thành viên");
	    
	    JButton btn_deleteButton = new JButton("Ok");
	    
	    panel.add(btn_deleteButton);
	    
	    btn_deleteButton.addActionListener(this);
	    
	    jfxoa.setLocationRelativeTo(null);
	    jfxoa.setSize(300, 150);
	    jfxoa.add(panel);
	    

	    panel.setVisible(true);
	    jfxoa.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comStr = e.getActionCommand();
		if(comStr.equals("Ok")) {
			String getUserName = userName.getText();
			if (!getUserName.equals(null)) {
				GroupChatDB group = new GroupChatDB(cnt, user);
				group.deleteMember(gr, getUserName);
				jfxoa.dispose();
			}
		}
	}
	
}
