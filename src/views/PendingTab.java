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
						String findusername = tfSearch.getText();
					
						boolean exist = false;
						Statement stm =null;
						ResultSet res = null;
						try {
							conn.setAutoCommit(false);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String findid = "SELECT * FROM users where user_name like '%" + findusername + "%'";
						Vector<User> fid = new Vector<User>();
						try {
							
							stm = conn.createStatement();
							res = stm.executeQuery(findid);
							conn.commit();

							while(res.next()) {
								exist = true;
								Integer id = res.getInt("user_id");
								String username = res.getString("user_name");
								String hoten = res.getString("user_hoten");
								String dob = res.getString("user_ngaysinh");
								String email = res.getString("user_email");
								String address = res.getString("user_diachi");
								User user = new User(id,username,hoten,dob,email,address);
								
								fid.add(user);
								

							}
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(exist);
						listPending.removeAll();
						listPending.revalidate();
						listPending.repaint();
						if(exist) {
						
							for(int i = 0; i<fid.size(); i++) {
								String friendsql = "SELECT us_id from getfriendlist(" + you.getID() + ") where us_id=" + fid.get(i).getID();
								try {
									System.out.print(friendsql);
									stm = conn.createStatement();
									res = stm.executeQuery(friendsql);
									conn.commit();
									if(res.next()) {
										FriendCom fr = new FriendCom(conn,you,fid.get(i));
										listPending.add(fr.initialize(fid.get(i).getUsername(),"Hủy"));
										JSeparator separator = new JSeparator();
										separator.setBounds(10, 33, 353, 2);
										
										listPending.add(separator);
										System.out.print("hey");
									}
									else {
										String waitlinesql = "SELECT user_id2 from friend_waitline where user_id1=" + fid.get(i).getID();
										try {
											System.out.print(waitlinesql);
											stm = conn.createStatement();
											res = stm.executeQuery(waitlinesql);
											conn.commit();
											if(res.next()) {
												System.out.println("hello" + res.getString(1));
												FriendCom fr = new FriendCom(conn,you,fid.get(i));
												listPending.add(fr.initialize(fid.get(i).getUsername(),"Pending"));
												JSeparator separator = new JSeparator();
												separator.setBounds(10, 33, 353, 2);
												
												listPending.add(separator);
											}
											else
											{
												System.out.println("hel");
												FriendCom fr = new FriendCom(conn,you,fid.get(i));
												listPending.add(fr.initialize(fid.get(i).getUsername(),"Kết bạn"));
												JSeparator separator = new JSeparator();
												separator.setBounds(10, 33, 353, 2);
												
												listPending.add(separator);

												
											}
										} catch (SQLException ex) {
											// TODO Auto-generated catch block
											
											
												ex.printStackTrace();
											
										}
									}
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							
							
						}
					}
				}
			}
		});
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!tfSearch.getText().trim().equals("") && !tfSearch.getText().trim().equals("Nhập tên"))
				{
					
			
						String findusername = tfSearch.getText();
					
						boolean exist = false;
						Statement stm =null;
						ResultSet res = null;
						try {
							conn.setAutoCommit(false);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String findid = "SELECT * FROM users where user_name like '%" + findusername + "%'";
						Vector<User> fid = new Vector<User>();
						try {
							
							stm = conn.createStatement();
							res = stm.executeQuery(findid);
							conn.commit();

							while(res.next()) {
								exist = true;
								Integer id = res.getInt("user_id");
								String username = res.getString("user_name");
								String hoten = res.getString("user_hoten");
								String dob = res.getString("user_ngaysinh");
								String email = res.getString("user_email");
								String address = res.getString("user_diachi");
								User user = new User(id,username,hoten,dob,email,address);
								
								fid.add(user);
								

							}
							
						} catch (SQLException exc) {
							// TODO Auto-generated catch block
							exc.printStackTrace();
						}
						System.out.println(exist);
						listPending.removeAll();
						listPending.revalidate();
						listPending.repaint();
						if(exist) {
						
							for(int i = 0; i<fid.size(); i++) {
								String friendsql = "SELECT us_id from getfriendlist(" + you.getID() + ") where us_id=" + fid.get(i).getID();
								try {
									System.out.print(friendsql);
									stm = conn.createStatement();
									res = stm.executeQuery(friendsql);
									conn.commit();
									if(res.next()) {
										FriendCom fr = new FriendCom(conn,you,fid.get(i));
										listPending.add(fr.initialize(fid.get(i).getUsername(),"Hủy"));
										JSeparator separator = new JSeparator();
										separator.setBounds(10, 33, 353, 2);
										
										listPending.add(separator);
										System.out.print("hey");
									}
									else {
										String waitlinesql = "SELECT user_id2 from friend_waitline where user_id1=" + fid.get(i).getID();
										try {
											System.out.print(waitlinesql);
											stm = conn.createStatement();
											res = stm.executeQuery(waitlinesql);
											conn.commit();
											if(res.next()) {
												System.out.println("hello" + res.getString(1));
												FriendCom fr = new FriendCom(conn,you,fid.get(i));
												listPending.add(fr.initialize(fid.get(i).getUsername(),"Pending"));
												JSeparator separator = new JSeparator();
												separator.setBounds(10, 33, 353, 2);
												
												listPending.add(separator);
											}
											else
											{
												System.out.println("hel");
												FriendCom fr = new FriendCom(conn,you,fid.get(i));
												listPending.add(fr.initialize(fid.get(i).getUsername(),"Kết bạn"));
												JSeparator separator = new JSeparator();
												separator.setBounds(10, 33, 353, 2);
												
												listPending.add(separator);

												
											}
										} catch (SQLException ex) {
											// TODO Auto-generated catch block
											ex.printStackTrace();
										}
									}
								} catch (SQLException exc) {
									// TODO Auto-generated catch block
									exc.printStackTrace();
								}
							}

							
							
						}
					}					
				
		
			}
		});
		
		return OnlinePanel;
	}
	
	
}
