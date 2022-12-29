package components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class FriendCom {

	private JPanel friend;

	
	public FriendCom() {
		friend = new JPanel();
		friend.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
		public JPanel initialize(String username) {
		
		
		//friend.setBounds(0, 0, 404, 100);
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
		
		JButton btnUnfriend = new JButton("Hủy");
		btnUnfriend.setForeground(new Color(255, 255, 255));
		btnUnfriend.setBackground(new Color(241, 84, 7));
		btnUnfriend.setFont(new Font("Arial", Font.PLAIN, 10));
		btnUnfriend.setBounds(195, 20, 71, 17);
		friend.add(btnUnfriend);
		return friend;
		
	}
}
