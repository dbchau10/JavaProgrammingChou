package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import network.Client;
import utils.GroupChat;
import utils.GroupChatDB;
import views.GroupChatFrame;
import views.User;

public class GroupCom {

	private JPanel group;

	private User you;
	private Connection conn = null;
	private GroupChat grp;
	Client cl;
	
	public GroupCom(Connection conn, User you, GroupChat gr, Client cl) {
		group = new JPanel();
		group.setVisible(true);
		this.conn = conn;
		this.you = you;
		this.grp = gr;
		this.cl = cl;
	}
	
	public JPanel initialize(String groupname) {
		group.setPreferredSize(new Dimension(404, 50));
		group.setLayout(null);
		
		JLabel lblGroupname = new JLabel(groupname);
		lblGroupname.setFont(new Font("Arial", Font.PLAIN, 13));
		lblGroupname.setBounds(29, 10, 140, 43);
		group.add(lblGroupname);
		
		JButton btnMess = new JButton("Nhắn");
		btnMess.setFont(new Font("Arial", Font.PLAIN, 10));
		btnMess.setBounds(286, 20, 71, 17);
		group.add(btnMess);
		btnMess.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					GroupChatFrame gcf = new GroupChatFrame(conn,you,grp,cl);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		
		JButton btnOut = new JButton("Rời");
		btnOut.setForeground(new Color(255, 255, 255));
		btnOut.setBackground(new Color(241, 84, 7));
		btnOut.setBounds(195, 20, 71, 17);
		group.add(btnOut);
		btnOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GroupChatDB datahandle = new GroupChatDB(conn, you);
				datahandle.leaveGroup(grp);
			}
		});
		
		
		return group;
	}
}
