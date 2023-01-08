package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import network.Client;
import views.AllTab;
import views.ChatFrame;
import views.FriendFunction;
import views.Signup;
import views.User;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendCom {

	private JPanel friend;

	private User you;
	private Connection conn = null;
	private User other;
	JButton btn = new JButton();
	Client cl;
	public FriendCom(Connection conn, User you, User other, Client cl) {
		friend = new JPanel();
		friend.setVisible(true);
		this.conn = conn;
		this.you = you;
		this.other = other;
		this.cl = cl;

	}
	
	public void changeButtonContent(String content)
	{
		btn.setText(content);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	public JPanel initialize(String username, String button) {

		// friend.setBounds(0, 0, 404, 100);
		friend.setPreferredSize(new Dimension(404, 50));
		friend.setLayout(null);

		JLabel lblUsername = new JLabel(username);
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 13));
		lblUsername.setBounds(29, 10, 140, 43);
		friend.add(lblUsername);
		JButton btnMess = new JButton("Nhắn");
		btnMess.setFont(new Font("Arial", Font.PLAIN, 10));
		btnMess.setBounds(286, 20, 71, 17);
		friend.add(btnMess);

		changeButtonContent(button);
		btn.setForeground(new Color(255, 255, 255));
		btn.setBackground(new Color(241, 84, 7));
		btn.setFont(new Font("Arial", Font.PLAIN, 10));
		btn.setBounds(195, 20, 71, 17);
		friend.add(btn);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btn.getText() == "Hủy") {
					FriendFunction del = new FriendFunction(conn, you);
					del.DeleteFriend(other.getUsername());

					/*
					 * try {
					 * String sql = new
					 * String("DELETE FROM friendlist WHERE user_id1=? and user_id2=?");
					 * conn.setAutoCommit(false);
					 * stm = conn.prepareStatement(sql);
					 * stm.setInt(1, you.getID());
					 * stm.setInt(2, other.getID());
					 * stm.executeUpdate();
					 * conn.commit();
					 * 
					 * sql = new String("DELETE FROM friendlist WHERE user_id1=? and user_id2=?");
					 * conn.setAutoCommit(false);
					 * stm = conn.prepareStatement(sql);
					 * stm.setInt(2, you.getID());
					 * stm.setInt(1, other.getID());
					 * stm.executeUpdate();
					 * conn.commit();
					 * 
					 * 
					 * 
					 * } catch(SQLException ex)
					 * {
					 * try {
					 * conn.rollback();
					 * }
					 * catch(SQLException e1) {
					 * e1.printStackTrace();
					 * }
					 * }
					 */
					PreparedStatement stm =null;
					ResultSet res = null;
					List<User> FriendList = new ArrayList<>();
					try {
						String sql = "SELECT * FROM USERS US LEFT JOIN FRIENDLIST FR ON US.USER_ID = FR.USER_ID1 WHERE FR.USER_ID2 IN (SELECT U.USER_ID FROM USERS U WHERE U.USER_NAME=?)";
						stm = conn.prepareStatement(sql);
						stm.setString(1, you.getUsername());
						res = stm.executeQuery();
						while (res.next()) {

							Integer id = res.getInt("user_id");
							String username = res.getString("user_name");
							String hoten = res.getString("user_hoten");
							String dob = res.getString("user_ngaysinh");
							String email = res.getString("user_email");
							String address = res.getString("user_diachi");
							User user = new User(id, username, hoten, dob, email, address);
							System.out.print(user.getUsername());
							FriendList.add(user);

						}
					} catch (SQLException ex) {
						ex.printStackTrace();
					}

					AllTab.listAll.removeAll();
					AllTab.listAll.revalidate();
					AllTab.listAll.repaint();

					for (int i = 0; i < FriendList.size(); i++) {
						FriendCom fr = new FriendCom(conn, you, FriendList.get(i),cl);
						AllTab.listAll.add(fr.initialize(FriendList.get(i).getUsername(), "Hủy"));
						JSeparator separator = new JSeparator();
						separator.setBounds(10, 33, 353, 2);

						AllTab.listAll.add(separator);
					}

				} else if  (btn.getText() == "Kết bạn") {
					FriendFunction add = new FriendFunction(conn, you);
					add.FriendRequest(other.getUsername());
					
					btn.setText("Pending");
				}
				else if (btn.getText()=="Chấp nhận") {
					FriendFunction accept = new FriendFunction(conn, you);
					accept.AcceptRequest(other.getUsername());
					
				}
			}
		});
		
		
		btnMess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	             try {
	            	
					ChatFrame ch = new ChatFrame(conn,you,username,cl);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		return friend;
		
		
		
	}
}
