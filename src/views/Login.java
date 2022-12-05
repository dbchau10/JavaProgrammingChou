package views;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Toolkit;

public class Login {

	private JFrame frmChatter;
	private JTextField tfUsername;
	private JPasswordField tfPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmChatter.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChatter = new JFrame();
		frmChatter.setTitle("Chatter!");
		frmChatter.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\DELL\\CNPM\\JavaProgramming\\background.jpg"));
		frmChatter.setBounds(500, 200, 1000, 500);
		frmChatter.setResizable(false);
		frmChatter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatter.getContentPane().setLayout(null);
		
		frmChatter.getContentPane().setBackground(new Color(206,157,217));
		
		
		JPanel LoginPanel = new JPanel();
		LoginPanel.setBounds(149, 158, 193, 101);
		LoginPanel.setBackground(new Color(206,157,217));
		frmChatter.getContentPane().add(LoginPanel);
		LoginPanel.setLayout(null);
		
		ImageIcon background=new ImageIcon("background.jpg");
	    Image img=background.getImage();
	    Image temp=img.getScaledInstance(500,500,Image.SCALE_SMOOTH);
	    background=new ImageIcon(temp);
	    JLabel back=new JLabel(background);
	    back.setLayout(null);
	    back.setBounds(500,0,500,500);
	    
	    frmChatter.getContentPane().add(back);
		
		tfUsername = new JTextField();
		tfUsername.setBounds(0, 23, 193, 19);
		LoginPanel.add(tfUsername);
		tfUsername.setColumns(10);
		
		tfPassword = new JPasswordField();
		tfPassword.setBounds(0, 82, 193, 19);
		LoginPanel.add(tfPassword);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(0, 0, 96, 13);
		LoginPanel.add(lblUsername);
		lblUsername.setFont(new Font("Arial", Font.BOLD, 13));
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(0, 59, 96, 13);
		LoginPanel.add(lblPassword);
		lblPassword.setFont(new Font("Arial", Font.BOLD, 13));
		
		JButton btnLogin = new JButton("Đăng nhập");
		btnLogin.setForeground(new Color(0, 0, 0));
		btnLogin.setBackground(new Color(235, 209, 105));
		btnLogin.setFont(new Font("Arial", Font.BOLD, 13));
		btnLogin.setBounds(174, 286, 139, 21);
		frmChatter.getContentPane().add(btnLogin);
		
		JButton btnSignup = new JButton("Đăng kí");
		btnSignup.setForeground(new Color(0, 0, 0));
		btnSignup.setFont(new Font("Arial", Font.BOLD, 13));
		btnSignup.setBackground(new Color(235, 209, 105));
		btnSignup.setBounds(174, 317, 139, 21);
		frmChatter.getContentPane().add(btnSignup);
		
		ImageIcon account=new ImageIcon("account2.png");
	    Image logo=account.getImage();
	    Image temp1=logo.getScaledInstance(90,90,Image.SCALE_SMOOTH);
	    background=new ImageIcon(temp1);
	    JLabel backtext=new JLabel(background);
	    backtext.setLayout(null);
	    backtext.setBounds(200,60,90,90);
		frmChatter.getContentPane().add(backtext);
		
		JButton btnReset = new JButton("Quên mật khẩu");
		btnReset.setForeground(Color.BLACK);
		btnReset.setFont(new Font("Arial", Font.BOLD, 13));
		btnReset.setBackground(new Color(235, 209, 105));
		btnReset.setBounds(174, 348, 139, 21);
		frmChatter.getContentPane().add(btnReset);
	    
		//JLabel lblNewLabel = new JLabel("Hello World!");
		//lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		//lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
		//lblNewLabel.setBounds(149, 107, 193, 21);
		//frmChatter.getContentPane().add(lblNewLabel);
	}
}
