package views;

import utils.RandomPasswordGenerator;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login {

	
	private JFrame frmChatter;
	private JTextField tfUsername;
	private JPasswordField tfPassword;
	private Connection conn = null;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				Connection conn = null;
				final String dbServer = "postgresql-100470-0.cloudclusters.net";
				final String dbName = "Demochat";
				int dbPort = 10121; // change it to your database server port
				final String userName = "admin";
				final String password = "192002Nhuy";
				//final String JBDC_DRIVER = "org.postgresql.Driver";
				String url = String.format("jdbc:postgresql://%s:%d/%s?user=%s&password=%s", 
                          dbServer, dbPort, dbName, userName, password);
				try {
					conn = DriverManager.getConnection(url);
					System.out.println("Success");
				}
				catch (Exception se) {
					se.printStackTrace();
					System.out.print("Cannot connect");
					System.exit(1);
				}
				try {
					Login window = new Login(conn);
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
	public Login(Connection cnt) {
		conn = cnt;
		initialize();
	}
	
	public void setVis() {
		frmChatter.setVisible(true);
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
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Statement stmt = null;
				String username = tfUsername.getText();
				String password = tfPassword.getText();
				if(password.equals("") && username.equals("")) {
	            	 lblUsername.setForeground(Color.red);
	            	 lblPassword.setForeground(Color.red);
	             }
	             else if(password.equals("")) {
	            	 lblPassword.setForeground(Color.red);
	             }
	             else if(username.equals("")) {
	            	 lblUsername.setForeground(Color.red);
	             }
	             else {
	            	 try {
		 					stmt = conn.createStatement();
		 					String sql = "SELECT * from users where user_name='" + username + "'";
		 					System.out.print(sql);
		 					ResultSet rs = stmt.executeQuery(sql);
		 					/*
		 					if(rs.next() == false) {
		 						System.out.println("Không có tài khoản tồn tại");
		 					}
		 					else {
		 					*/	
		 						while(rs.next()) {
			 						if(!password.equals(rs.getString(3))) {
			 							System.out.print("Sai");
			 							
			 						}
			 						else {
			 							System.out.print("Ok");
			 							User you = new User(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(4), rs.getString(6),rs.getString(8),rs.getString(5));
			 							System.out.println(you.getID());
			 							frmChatter.dispose();
			 							UserUtil u = new UserUtil(conn, you);
			 							u.setVis();
			 						}
			 					}
		 					//}
		 					
		 				} catch (SQLException e1) {
		 					// TODO Auto-generated catch block
		 					e1.printStackTrace();
		 				}
	             }
			}
			
		});
		
		JButton btnSignup = new JButton("Đăng kí");
		btnSignup.setForeground(new Color(0, 0, 0));
		btnSignup.setFont(new Font("Arial", Font.BOLD, 13));
		btnSignup.setBackground(new Color(235, 209, 105));
		btnSignup.setBounds(174, 317, 139, 21);
		frmChatter.getContentPane().add(btnSignup);
		btnSignup.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 frmChatter.dispose();
	             Signup sn = new Signup(conn);
	             sn.setVis();
	          }
	       });
		
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
		btnReset.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 String username = tfUsername.getText();
	        	 if(username.equals("")) {
	            	 lblUsername.setForeground(Color.red);
	             }
	        	 else {
	        		String newpass;
	        		RandomPasswordGenerator gn = new RandomPasswordGenerator();
	        		newpass = gn.getPassword();
	        		
	        		 //popup
	 	        	
	        	 }
	          }
	       });
	    
		//JLabel lblNewLabel = new JLabel("Hello World!");
		//lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		//lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
		//lblNewLabel.setBounds(149, 107, 193, 21);
		//frmChatter.getContentPane().add(lblNewLabel);
	}
}
