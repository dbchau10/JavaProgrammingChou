package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;

public class CreateGroupChat {

	private JFrame createGroup;
	private JTextField tfNewGroup;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateGroupChat window = new CreateGroupChat();
					window.createGroup.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CreateGroupChat() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		createGroup = new JFrame();
		createGroup.setTitle("Chatter!");
		createGroup.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\DELL\\CNPM\\JavaProgramming\\background.jpg"));
		createGroup.setBounds(500, 200, 598, 500);
		createGroup.setResizable(false);
		createGroup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createGroup.getContentPane().setLayout(null);
		
		createGroup.getContentPane().setBackground(new Color(206,157,217));
		
		tfNewGroup = new JTextField();
		tfNewGroup.setBounds(121, 46, 331, 19);
		createGroup.getContentPane().add(tfNewGroup);
		tfNewGroup.setColumns(10);
		
		JLabel lblNewGroup = new JLabel("Tên nhóm");
		lblNewGroup.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		lblNewGroup.setBounds(26, 45, 85, 20);
		createGroup.getContentPane().add(lblNewGroup);
	}

}
