package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Signup {

	private JFrame frmChatter;
	private JTextField tfUsername;
	private JPasswordField tfPassword;
	private JTextField tfUsernamesu;
	private JPasswordField tfPasswordsu;
	private JTextField tfEmail;
	private JTextField tfName;
	private JTextField tfAddress;
	Connection conn = null;

	/**
	 */

	/**
	 * Create the application.
	 */
	public Signup(Connection cnt) {
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
		
		
		
		
		ImageIcon background=new ImageIcon("background.jpg");
	    Image img=background.getImage();
	    Image temp=img.getScaledInstance(500,500,Image.SCALE_SMOOTH);
	    background=new ImageIcon(temp);
	    JLabel back=new JLabel(background);
	    back.setLayout(null);
	    back.setBounds(500,0,500,500);
	    
	    frmChatter.getContentPane().add(back);
	    
	    JPanel mainPanel = new JPanel();
	    mainPanel.setBackground(new Color(240, 207, 240));
	    mainPanel.setBounds(102, 88, 289, 315);
	    frmChatter.getContentPane().add(mainPanel);
	    mainPanel.setLayout(null);
	    
	    JPanel panelSignup = new JPanel();
	    panelSignup.setBounds(23, 29, 240, 219);
	    mainPanel.add(panelSignup);
	    panelSignup.setBackground(new Color(240, 207, 240));
	    panelSignup.setLayout(null);
	    
	    JLabel lblUsername = new JLabel("Username");
	    lblUsername.setBounds(0, 0, 81, 16);
	    panelSignup.add(lblUsername);
	    lblUsername.setFont(new Font("Arial", Font.BOLD, 13));
	    
	    tfUsernamesu = new JTextField();
	    tfUsernamesu.setBounds(91, 0, 149, 19);
	    panelSignup.add(tfUsernamesu);
	    tfUsernamesu.setColumns(10);
	    
	    JLabel lblPassword = new JLabel("Password");
	    lblPassword.setBounds(0, 40, 81, 16);
	    panelSignup.add(lblPassword);
	    lblPassword.setFont(new Font("Arial", Font.BOLD, 13));
	    
	    tfPasswordsu = new JPasswordField();
	    tfPasswordsu.setBounds(91, 40, 149, 19);
	    panelSignup.add(tfPasswordsu);
	    
	    JLabel lblEmail = new JLabel("Email");
	    lblEmail.setBounds(0, 80, 81, 16);
	    panelSignup.add(lblEmail);
	    lblEmail.setFont(new Font("Arial", Font.BOLD, 13));
	    
	    tfEmail = new JTextField();
	    tfEmail.setBounds(91, 80, 149, 19);
	    panelSignup.add(tfEmail);
	    tfEmail.setColumns(10);
	    
	    JLabel lblName = new JLabel("Họ tên");
	    lblName.setBounds(0, 120, 81, 16);
	    panelSignup.add(lblName);
	    lblName.setFont(new Font("Arial", Font.BOLD, 13));
	    
	    tfName = new JTextField();
	    tfName.setBounds(91, 120, 149, 19);
	    panelSignup.add(tfName);
	    tfName.setColumns(10);
	    
	    JLabel lblDOB = new JLabel("DOB");
	    lblDOB.setBounds(0, 160, 81, 16);
	    panelSignup.add(lblDOB);
	    lblDOB.setFont(new Font("Arial", Font.BOLD, 13));
	    
	    JLabel lblAddress = new JLabel("Địa chỉ");
	    lblAddress.setBounds(0, 200, 81, 16);
	    panelSignup.add(lblAddress);
	    lblAddress.setFont(new Font("Arial", Font.BOLD, 13));
	    
	    tfAddress = new JTextField();
	    tfAddress.setBounds(91, 200, 149, 19);
	    panelSignup.add(tfAddress);
	    tfAddress.setColumns(10);
	    
	    JDateChooser tfDOB = new JDateChooser();
	    tfDOB.setBounds(91, 160, 149, 19);
	    panelSignup.add(tfDOB);
	    
	    JButton btnSubmit = new JButton("Đăng kí");
	    btnSubmit.setBounds(99, 273, 85, 21);
	    mainPanel.add(btnSubmit);
	    btnSubmit.setBackground(new Color(235, 209, 105));
	    btnSubmit.setForeground(new Color(0, 0, 0));
	    btnSubmit.setFont(new Font("Arial", Font.BOLD, 13));
	    
	    JLabel lblTitle = new JLabel("Chatter!");
	    lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
	    lblTitle.setFont(new Font("Arial", Font.BOLD, 17));
	    lblTitle.setBounds(102, 60, 289, 27);
	    frmChatter.getContentPane().add(lblTitle);
	    
	    
	    JButton btnNewButton = new JButton();
	    btnNewButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 frmChatter.dispose();
	             Login lg = new Login(conn);
	             lg.setVis();
	          }
	       });;
	    btnNewButton.setBackground(new Color(206, 157, 207));
	    ImageIcon retn=new ImageIcon("back.png");
	    Image button=retn.getImage();
	    Image temp1=button.getScaledInstance(50,50,Image.SCALE_SMOOTH);
	    retn=new ImageIcon(temp1);
	    btnNewButton.setIcon(retn);
	    btnNewButton.setBounds(10, 21, 46, 41);
	    frmChatter.getContentPane().add(btnNewButton);
	    
	    btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.print("OK");
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				
				String username = tfUsernamesu.getText();
				String password = tfPasswordsu.getText();
				String email = tfEmail.getText();
				String name = tfName.getText();
				String address = tfAddress.getText();
				
				if(password.equals("")) {
	            	 lblPassword.setForeground(Color.red);
	             }
				
				if(username.equals("")) {
					lblUsername.setForeground(Color.red);
				}
				
				if (email.equals("")) {
					lblEmail.setForeground(Color.red);
				}
				
				if(name.equals("")) {
					lblName.setForeground(Color.red);
				}
				
				if(address.equals("")) {
					lblAddress.setForeground(Color.red);
				}
				
				if(tfDOB.getDate() !=null) {
					String dob = dateFormat.format(tfDOB.getDate());
				}
				else {
					lblDOB.setForeground(Color.red);
				}
				
				   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				   LocalDateTime now = LocalDateTime.now();  
				   System.out.println(dtf.format(now));  
				
				if(!password.equals("") && tfDOB.getDate() !=null && !address.equals("") && !name.equals("") && !email.equals("") && !name.equals("")) {
					Statement stmt = null;
					
					try {
						conn.setAutoCommit(false);
						stmt = conn.createStatement();
						String check = "Select user_name from users where user_name='" + username + "'";
						ResultSet rs = stmt.executeQuery(check);
						conn.commit();
						
						if(!rs.next()) {
							String sql = "INSERT INTO users(user_id, user_name, user_password) VALUES(8, '" + username + "','" + password + "')";
							System.out.print(sql);
							stmt.executeUpdate(sql);
							conn.commit();
						}
						else {
							System.out.print("No");
						}
						
					}
					catch (SQLException ae){
						System.out.println("Error: Unable to insert");
					}
					finally {
						try {
							stmt.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
	    	
	    });
	}
}
