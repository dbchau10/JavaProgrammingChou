package components;

import java.awt.Color;
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
	}

	/**
	 * Initialize the contents of the frame.
	 */
		public JPanel initialize(String username, int index) {
		
		
		
		friend.setBounds(0, 0, 404, 100);
		friend.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{124, 42, 71, 71, 0};
		gridBagLayout.rowHeights = new int[]{43, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		friend.setLayout(gridBagLayout);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 13));
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.insets = new Insets(0, 0, 0, 5);
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 0;
		friend.add(lblUsername, gbc_lblUsername);
		
		JButton btnUnfriend = new JButton("Hủy");
		btnUnfriend.setForeground(new Color(255, 255, 255));
		btnUnfriend.setBackground(new Color(241, 84, 7));
		btnUnfriend.setFont(new Font("Arial", Font.PLAIN, 10));
		GridBagConstraints gbc_btnUnfriend = new GridBagConstraints();
		gbc_btnUnfriend.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUnfriend.insets = new Insets(0, 0, 0, 5);
		gbc_btnUnfriend.gridx = 2;
		gbc_btnUnfriend.gridy = 0;
		friend.add(btnUnfriend, gbc_btnUnfriend);
		JButton btnMess = new JButton("Nhắn");
		btnMess.setFont(new Font("Arial", Font.PLAIN, 10));
		GridBagConstraints gbc_btnMess = new GridBagConstraints();
		gbc_btnMess.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnMess.gridx = 3;
		gbc_btnMess.gridy = 0;
		friend.add(btnMess, gbc_btnMess);
		return friend;
		
	}
}
