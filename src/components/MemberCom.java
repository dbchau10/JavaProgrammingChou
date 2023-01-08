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
import java.util.Vector;

public class MemberCom {

	private JPanel friend;

	private User you;
	private Connection conn = null;
	private User other;
	Vector<Integer> member;
	JButton btn = new JButton();
	public MemberCom(Connection conn, User you, User other, Vector<Integer>member ) {
		friend = new JPanel();
		friend.setVisible(true);
		this.conn = conn;
		this.you = you;
		this.other = other;
		this.member = member;

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
		
		changeButtonContent(button);
		btn.setForeground(new Color(255, 255, 255));
		btn.setBackground(new Color(241, 84, 7));
		btn.setFont(new Font("Arial", Font.PLAIN, 10));
		btn.setBounds(160, 20, 150, 17);
		friend.add(btn);
		if (button=="Đã thêm")
		{
			btn.setEnabled(false);
		}
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btn.getText() == "Thêm") {
					member.add(other.getID());
					btn.setText("Đã thêm");
					btn.setEnabled(false);
				}
			}
			});
		
		
		
		return friend;
		
		
		
	}
}
