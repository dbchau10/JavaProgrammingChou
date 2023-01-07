package components;

import java.awt.Color;
import java.awt.Dimension;
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
		//JPanel jp = new JPanel();
		//jp.setBounds(0, 0, 404, 100);
		//friend.setPreferredSize(new Dimension(404, 100));
		friend.setLayout(null);
		
		JLabel lblUsername = new JLabel("username");
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
		//friend.getContentPane().add(jp);
	}

}
