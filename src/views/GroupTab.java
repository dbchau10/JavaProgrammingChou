package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import components.GroupCom;
import utils.GroupChatDB;
import utils.GroupChat;

public class GroupTab {
	private JPanel GroupPanel;
	private User you;
	private Connection conn = null;
	public static JPanel listGroup = new JPanel();
	GroupTab(Connection cnt, User you){
		GroupPanel = new JPanel();
		this.you = you;
		this.conn = cnt;
	}
	
	JPanel createPanel() {
		GroupPanel.setBackground(new Color(240, 240, 240));
		GroupPanel.setLayout(null);
		
		
		JButton create = new JButton("Create new group");
		create.setBounds(25,30,425,20);
		GroupPanel.add(create);
		
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame newgroup = new JFrame("Create Group");
				JPanel groupname = new JPanel();
				
				JLabel lblGroupName=new JLabel("Nhập tên:");
				JTextField tfGroupName = new JTextField(10);
				
				JButton btnSave = new JButton("OK");
				btnSave.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String name = tfGroupName.getText();
						
						if(name.equals("")) {
			            	 lblGroupName.setForeground(Color.red);
			            }
						else 
							{
							
							CreateGroupChat cr = new CreateGroupChat(you,conn,name);
							}
					}
				});
				
				groupname.add(lblGroupName);
				groupname.add(tfGroupName);
				groupname.add(btnSave);
				newgroup.add(groupname);
				
				newgroup.setSize(300,100);
				newgroup.setLocationRelativeTo(null);
				newgroup.setVisible(true);
				}
		});
				
			
		listGroup.setBounds(35, 81, 404, 257);
		listGroup.setLayout(new BoxLayout(listGroup,BoxLayout.Y_AXIS));
		
		new Thread(new Thread_GroupTab(conn, you,listGroup)).start();
		
		JScrollPane js = new JScrollPane(listGroup, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		js.setBounds(25, 81, 430, 257);
		GroupPanel.add(js);
		return GroupPanel;
	}
}
