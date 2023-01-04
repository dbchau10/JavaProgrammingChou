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
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import components.FriendCom;

public class PendingTab {
	private JPanel OnlinePanel;
	private User you;
	private Connection conn = null;
	PendingTab(Connection cnt, User you){
		OnlinePanel = new JPanel();
		this.you = you;
		this.conn = cnt;
	}
	
	JPanel createPanel() {
		OnlinePanel.setBackground(new Color(240, 240, 240));
		JTextField tfSearch = new JTextField();
		tfSearch.setBounds(34, 26, 310, 19);
		tfSearch.setForeground(new Color(115, 115, 115));
		tfSearch.setFont(new Font("Arial", Font.ITALIC, 10));
		tfSearch.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (tfSearch.getText().trim().equals("Nhập tên"))
				{
					tfSearch.setText("");
					tfSearch.setForeground(new Color(0, 0, 0));
					tfSearch.setFont(new Font("Arial", Font.PLAIN, 10));
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (tfSearch.getText().trim().equals(""))
				{
					tfSearch.setText("Nhập tên");
					tfSearch.setForeground(new Color(115, 115, 115));
					tfSearch.setFont(new Font("Arial", Font.ITALIC, 10));
				}
			}
		});
		OnlinePanel.setLayout(null);
		tfSearch.setText("Nhập tên");
		OnlinePanel.add(tfSearch);
		tfSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Tìm kiếm");
		btnSearch.setBackground(new Color(235, 209, 105));
		btnSearch.setBounds(354, 25, 85, 21);
		btnSearch.setFont(new Font("Arial", Font.PLAIN, 10));
		OnlinePanel.add(btnSearch);
		
		
		JPanel listPending = new JPanel();
		listPending.setBounds(35, 81, 404, 257);
		listPending.setLayout(new BoxLayout(listPending,BoxLayout.Y_AXIS));
		//listOnline.setLayout(null);
		//js.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//JPanel friend = new JPanel();
		//JPanel friend [] = new JPanel[10];
		new Thread(new Thread_PendingTab(conn, you,listPending)).start();
		
		// list online
		
		JScrollPane js = new JScrollPane(listPending, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//js.setPreferredSize(new Dimension(404,257));
		js.setBounds(25, 81, 430, 257);
		//JScrollPane js = new JScrollPane(listOnline);
		OnlinePanel.add(js);
		
		tfSearch.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode()==KeyEvent.VK_ENTER)
				{
					if(!tfSearch.getText().trim().equals("") && !tfSearch.getText().trim().equals("Nhập tên"))
					{
						boolean exist = false;
						PreparedStatement stm =null;
						ResultSet res = null;
						List<User>FriendList = new ArrayList<>();
						try {
							String sql = "SELECT * FROM USERS US LEFT JOIN FRIENDLIST FR ON US.USER_ID = FR.USER_ID1 WHERE FR.USER_ID2 IN (SELECT U.USER_ID FROM USERS U WHERE U.USER_NAME=?) AND US.USER_NAME LIKE ? AND US.USER_ONLINE='TRUE'";
							stm = conn.prepareStatement(sql);
							stm.setString(1, you.getUsername());
							stm.setString(2,'%'+tfSearch.getText()+'%');
							res = stm.executeQuery();
							while(res.next()) {
								exist=true;
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
							listPending.removeAll();
							listPending.revalidate();
							listPending.repaint();
							FriendCom fr;
							if (exist==true) {
								for (int i = 0 ; i<FriendList.size(); i++)
								{
									fr = new FriendCom(conn,you,FriendList.get(i));
									listPending.add(fr.initialize(FriendList.get(i).getUsername(),"Hủy"));
									JSeparator separator = new JSeparator();
									separator.setBounds(10, 33, 353, 2);
									
									listPending.add(separator);
								}
							}
							else {
								sql = "SELECT * FROM USERS US WHERE US.USER_NAME LIKE ?";
								stm = conn.prepareStatement(sql);
								stm.setString(1,'%'+tfSearch.getText()+'%');
								res = stm.executeQuery();
								while(res.next()) {
									exist=true;
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
								for (int i = 0 ; i<FriendList.size(); i++)
								{
									fr = new FriendCom(conn,you,FriendList.get(i));
									listPending.add(fr.initialize(FriendList.get(i).getUsername(),"Kết bạn"));
									JSeparator separator = new JSeparator();
									separator.setBounds(10, 33, 353, 2);
									
									listPending.add(separator);
								}
							}
						} catch(SQLException ex)
						{
							ex.printStackTrace();
						}
						
					}
				}
			}
		});
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!tfSearch.getText().trim().equals("") && !tfSearch.getText().trim().equals("Nhập tên"))
				{
					boolean exist = false;
					PreparedStatement stm =null;
					ResultSet res = null;
					List<User>FriendList = new ArrayList<>();
					try {
						String sql = "SELECT * FROM USERS US LEFT JOIN FRIENDLIST FR ON US.USER_ID = FR.USER_ID1 WHERE FR.USER_ID2 IN (SELECT U.USER_ID FROM USERS U WHERE U.USER_NAME=?) AND US.USER_NAME LIKE ?";
						stm = conn.prepareStatement(sql);
						stm.setString(1, you.getUsername());
						stm.setString(2,'%'+tfSearch.getText()+'%');
						res = stm.executeQuery();
						while(res.next()) {
							exist=true;
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
						listPending.removeAll();
						listPending.revalidate();
						listPending.repaint();
						FriendCom fr;
						if (exist==true) {
							for (int i = 0 ; i<FriendList.size(); i++)
							{
								fr = new FriendCom(conn,you,FriendList.get(i));
								listPending.add(fr.initialize(FriendList.get(i).getUsername(),"Hủy"));
								JSeparator separator = new JSeparator();
								separator.setBounds(10, 33, 353, 2);
								
								listPending.add(separator);
							}
						}
						else {
							sql = "SELECT * FROM USERS US WHERE US.USER_NAME LIKE ?";
							stm = conn.prepareStatement(sql);
							stm.setString(1,'%'+tfSearch.getText()+'%');
							res = stm.executeQuery();
							while(res.next()) {
								exist=true;
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

							for (int i = 0 ; i<FriendList.size(); i++)
							{
								fr = new FriendCom(conn,you,FriendList.get(i));
								listPending.add(fr.initialize(FriendList.get(i).getUsername(),"Kết bạn"));
								JSeparator separator = new JSeparator();
								separator.setBounds(10, 33, 353, 2);
								
								listPending.add(separator);
							}
						}
					} catch(SQLException ex)
					{
						ex.printStackTrace();
					}
					
				}
			}
		});
		
		return OnlinePanel;
	}
	
	
}
