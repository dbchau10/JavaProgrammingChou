package components;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class test {

	private JFrame friend;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
					window.friend.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		friend = new JFrame();
		friend.setBounds(0, 0, 404, 100);
		JPanel jp = new JPanel();
		jp.setBounds(0, 0, 404, 100);
		jp.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{124, 42, 71, 71, 0};
		gridBagLayout.rowHeights = new int[]{43, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		jp.setLayout(gridBagLayout);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 13));
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.insets = new Insets(0, 0, 0, 5);
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 0;
		jp.add(lblUsername, gbc_lblUsername);
		
		JButton btnUnfriend = new JButton("Hủy");
		btnUnfriend.setForeground(new Color(255, 255, 255));
		btnUnfriend.setBackground(new Color(241, 84, 7));
		btnUnfriend.setFont(new Font("Arial", Font.PLAIN, 10));
		GridBagConstraints gbc_btnUnfriend = new GridBagConstraints();
		gbc_btnUnfriend.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUnfriend.insets = new Insets(0, 0, 0, 5);
		gbc_btnUnfriend.gridx = 2;
		gbc_btnUnfriend.gridy = 0;
		jp.add(btnUnfriend, gbc_btnUnfriend);
		JButton btnMess = new JButton("Nhắn");
		btnMess.setFont(new Font("Arial", Font.PLAIN, 10));
		GridBagConstraints gbc_btnMess = new GridBagConstraints();
		gbc_btnMess.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnMess.gridx = 3;
		gbc_btnMess.gridy = 0;
		jp.add(btnMess, gbc_btnMess);
		friend.getContentPane().add(jp);
	}

}
