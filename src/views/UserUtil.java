package views;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.Color;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static utils.checkDateFormat.checkDate;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import javax.swing.JSeparator;
import javax.swing.JScrollBar;
import com.toedter.calendar.JDateChooser;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserUtil {
	
	private User you;
	Connection conn = null;
	private JFrame frmChatter;
	private JTextField tfUsername;
	private JTextField tfFullname;
	private JTextField tfEmail;
	private JTextField tfAddress;
	private JTextField tfSearch;


	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserUtil window = new UserUtil();
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
	public UserUtil(Connection cnt, User u) {
		you = u;
		conn = cnt;

		initialize();
	}
	
	public void setVis() {
		frmChatter.setVisible(true);
	}
	
	class WindowEventHandler extends WindowAdapter {
		  public void windowClosing(WindowEvent evt) {
			  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
			  LocalDateTime now = LocalDateTime.now();
			  try {
				Statement stmt=null;
				stmt = conn.createStatement();
				String getTime = "UPDATE users SET user_ngaycuoidangnhap='"+dtf.format(now)+ "'WHERE user_name='"+ you.getUsername() + "'";
				stmt.executeUpdate(getTime);
				conn.commit();
				String setstatus = "UPDATE users SET user_online=false WHERE user_name='"+ you.getUsername() + "'";
				stmt.executeUpdate(setstatus);
				conn.commit();
				String updatelsdn = "update users set user_lichsudangnhap = user_lichsudangnhap || '{"+dtf.format(now)+"}' where user_id =" + you.getID();
				stmt.executeUpdate(updatelsdn);
				conn.commit();
			  } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		  }
		}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		System.out.println(you.getID());
		
		frmChatter = new JFrame();
		frmChatter.setTitle("Chatter!");
		frmChatter.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\DELL\\CNPM\\JavaProgramming\\background.jpg"));
		frmChatter.setBounds(500, 200, 500, 500);
		frmChatter.setResizable(false);
		frmChatter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatter.getContentPane().setLayout(null);
		
		frmChatter.getContentPane().setBackground(new Color(206,157,217));
		
		frmChatter.addWindowListener(new WindowEventHandler());
	    
	    JPanel UserPanel = new JPanel();
	    
	    
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 470, 440);
	
		UserPanel.setBackground(new Color(240, 240, 240));
		tabbedPane.addTab("User", UserPanel);
		UserPanel.setLayout(null);
		
		JLabel lblUserInfoTitle = new JLabel("Thông tin cá nhân");
		lblUserInfoTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserInfoTitle.setFont(new Font("Arial", Font.BOLD, 16));
		lblUserInfoTitle.setBounds(127, 10, 198, 27);
		UserPanel.add(lblUserInfoTitle);
		
		JLabel lblUsername = new JLabel("Tên đăng nhập");
		lblUsername.setFont(new Font("Arial", Font.BOLD, 13));
		lblUsername.setBounds(36, 88, 102, 13);
		UserPanel.add(lblUsername);
		
		JLabel lblFullname = new JLabel("Họ tên");
		lblFullname.setFont(new Font("Arial", Font.BOLD, 13));
		lblFullname.setBounds(36, 128, 102, 13);
		UserPanel.add(lblFullname);
		
		JLabel lblDOB = new JLabel("Ngày sinh");
		lblDOB.setFont(new Font("Arial", Font.BOLD, 13));
		lblDOB.setBounds(36, 168, 102, 13);
		UserPanel.add(lblDOB);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Arial", Font.BOLD, 13));
		lblEmail.setBounds(36, 208, 102, 13);
		UserPanel.add(lblEmail);
		
		JLabel lblAddress = new JLabel("Địa chỉ");
		lblAddress.setFont(new Font("Arial", Font.BOLD, 13));
		lblAddress.setBounds(36, 248, 102, 13);
		UserPanel.add(lblAddress);
		
		tfUsername = new JTextField();
		tfUsername.setEnabled(false);
		tfUsername.setEditable(false);
		tfUsername.setFont(new Font("Arial", Font.PLAIN, 13));
		tfUsername.setText(you.getUsername());
		tfUsername.setBounds(169, 88, 156, 19);
		UserPanel.add(tfUsername);
		tfUsername.setColumns(10);
		
		tfFullname = new JTextField();
		tfFullname.setEditable(false);
		tfFullname.setText(you.getName());
		tfFullname.setFont(new Font("Arial", Font.PLAIN, 13));
		tfFullname.setColumns(10);
		tfFullname.setBounds(169, 128, 156, 19);
		UserPanel.add(tfFullname);
		
		tfEmail = new JTextField();
		tfEmail.setEditable(false);
		tfEmail.setText(you.getEmail());
		tfEmail.setFont(new Font("Arial", Font.PLAIN, 13));
		tfEmail.setColumns(10);
		tfEmail.setBounds(169, 208, 156, 19);
		UserPanel.add(tfEmail);
		
		tfAddress = new JTextField();
		tfAddress.setEditable(false);
		tfAddress.setText(you.getAddress());
		tfAddress.setFont(new Font("Arial", Font.PLAIN, 13));
		tfAddress.setColumns(10);
		tfAddress.setBounds(169, 248, 156, 19);
		UserPanel.add(tfAddress);
		
		JButton btnFullname = new JButton("Sửa");
		btnFullname.setFont(new Font("Arial", Font.PLAIN, 10));
		btnFullname.setBounds(359, 128, 65, 21);
		UserPanel.add(btnFullname);
		
		btnFullname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfFullname.getText().equals("") || tfFullname.getText()==null)
				{
					JOptionPane.showMessageDialog(null, "Value invalid. Please try again");
				}
				else {
					if (tfFullname.isEditable())
					{
						Statement stm = null;

						try {
							stm = conn.createStatement();
		 					String sql = "UPDATE users SET user_hoten='"+tfFullname.getText()+"' where user_name='" + tfUsername.getText() + "'";
		 					System.out.print(sql);
		 					conn.setAutoCommit(false);
		 					stm.executeUpdate(sql);
		 					conn.commit();
							
						} catch(SQLException es)
						{
							try {
								conn.rollback();
							}
							catch(SQLException e1) {
								e1.printStackTrace();
							}
						}
						finally {
							you.setName(tfFullname.getText());
						}
					}
					tfFullname.setEditable(!tfFullname.isEditable());
				}
					

			}
		});
		
		
		tfFullname.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode()==KeyEvent.VK_ENTER)
				{
					
					if (tfFullname.getText().equals("") || tfFullname.getText()==null)
					{
						JOptionPane.showMessageDialog(null, "Value invalid. Please try again");
					}
					else {
						if (tfFullname.isEditable())
						{
							Statement stm = null;

							try {
								stm = conn.createStatement();
			 					String sql = "UPDATE users SET user_hoten='"+tfFullname.getText()+"' where user_name='" + tfUsername.getText() + "'";
			 					System.out.print(sql);
			 					conn.setAutoCommit(false);
			 					stm.executeUpdate(sql);
			 					conn.commit();
								
							} catch(SQLException es)
							{
								try {
									conn.rollback();
								}
								catch(SQLException e1) {
									e1.printStackTrace();
								}
							}
							finally {
								you.setName(tfFullname.getText());
							}
						}
						tfFullname.setEditable(!tfFullname.isEditable());
					}
				}
				
			}
		});
		
		JDateChooser tfDOB = new JDateChooser();
		tfDOB.setDateFormatString("yyyy-MM-dd");
		try {
			tfDOB.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(you.getDOB()));
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		tfDOB.setBounds(170, 168, 155, 19);
		UserPanel.add(tfDOB);
		
		JButton btnDOB = new JButton("Sửa");
		btnDOB.setFont(new Font("Arial", Font.PLAIN, 10));
		btnDOB.setBounds(359, 168, 65, 21);
		UserPanel.add(btnDOB);
		
		btnDOB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(tfDOB.getDate()==null)
				{
					JOptionPane.showMessageDialog(null, "Value invalid. Please try again");
				}
				else 
				{
		
						Statement stm = null;
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						String dob = dateFormat.format(tfDOB.getDate());

						try {
							stm = conn.createStatement();
		 					String sql = "UPDATE users SET user_ngaysinh='"+dob+"' where user_name='" + tfUsername.getText() + "'";
		 					System.out.print(sql);
		 					conn.setAutoCommit(false);
		 					stm.executeUpdate(sql);
		 					conn.commit();
							
						} catch(SQLException es)
						{
							try {
								conn.rollback();
							}
							catch(SQLException e1) {
								e1.printStackTrace();
							}
						}
						finally {
							you.setDOB(dob);
						}
				}

			}
		});
		
		JButton btnEmail = new JButton("Sửa");
		btnEmail.setFont(new Font("Arial", Font.PLAIN, 10));
		btnEmail.setBounds(361, 208, 65, 21);
		UserPanel.add(btnEmail);
		
		btnEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfEmail.getText().equals("") || tfEmail.getText()==null)
				{
					JOptionPane.showMessageDialog(null, "Value invalid. Please try again");
				}
				else {
					if (tfEmail.isEditable())
					{
						Statement stm = null;

						try {
							stm = conn.createStatement();
		 					String sql = "UPDATE users SET user_email='"+tfEmail.getText()+"' where user_name='" + tfUsername.getText() + "'";
		 					System.out.print(sql);
		 					conn.setAutoCommit(false);
		 					stm.executeUpdate(sql);
		 					conn.commit();
							
						} catch(SQLException es)
						{
							try {
								conn.rollback();
							}
							catch(SQLException e1) {
								e1.printStackTrace();
							}
						}
						finally {
							you.setEmail(tfEmail.getText());
						}
					}
					tfEmail.setEditable(!tfEmail.isEditable());
				}
			}
		});
		
		
		tfEmail.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode()==KeyEvent.VK_ENTER)
				{
					
					if (tfEmail.getText().equals("") || tfEmail.getText()==null)
					{
						JOptionPane.showMessageDialog(null, "Value invalid. Please try again");
					}
					else {
						if (tfEmail.isEditable())
						{
							Statement stm = null;

							try {
								stm = conn.createStatement();
			 					String sql = "UPDATE users SET user_email='"+tfEmail.getText()+"' where user_name='" + tfUsername.getText() + "'";
			 					System.out.print(sql);
			 					conn.setAutoCommit(false);
			 					stm.executeUpdate(sql);
			 					conn.commit();
								
							} catch(SQLException es)
							{
								try {
									conn.rollback();
								}
								catch(SQLException e1) {
									e1.printStackTrace();
								}
							}
							finally {
								you.setEmail(tfEmail.getText());
							}
						}
						tfEmail.setEditable(!tfEmail.isEditable());
					}
				}
				
			}
		});
		JButton btnAddress = new JButton("Sửa");
		btnAddress.setFont(new Font("Arial", Font.PLAIN, 10));
		btnAddress.setBounds(359, 248, 65, 21);
		UserPanel.add(btnAddress);
		btnAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfAddress.getText().equals("") || tfAddress.getText()==null)
				{
					JOptionPane.showMessageDialog(null, "Value invalid. Please try again");
				}
				else {
					if (tfAddress.isEditable())
					{
						Statement stm = null;

						try {
							stm = conn.createStatement();
		 					String sql = "UPDATE users SET user_diachi='"+tfAddress.getText()+"' where user_name='" + tfUsername.getText() + "'";
		 					System.out.print(sql);
		 					conn.setAutoCommit(false);
		 					stm.executeUpdate(sql);
		 					conn.commit();
							
						} catch(SQLException es)
						{
							try {
								conn.rollback();
							}
							catch(SQLException e1) {
								e1.printStackTrace();
							}
						}
						finally {
							you.setAddress(tfAddress.getText());
						}
					}
					tfAddress.setEditable(!tfAddress.isEditable());
				}
			}
		});
		
		
		tfAddress.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode()==KeyEvent.VK_ENTER)
				{
					
					if (tfAddress.getText().equals("") || tfAddress.getText()==null)
					{
						JOptionPane.showMessageDialog(null, "Value invalid. Please try again");
					}
					else {
						if (tfAddress.isEditable())
						{
							Statement stm = null;

							try {
								stm = conn.createStatement();
			 					String sql = "UPDATE users SET user_diachi='"+tfAddress.getText()+"' where user_name='" + tfUsername.getText() + "'";
			 					System.out.print(sql);
			 					conn.setAutoCommit(false);
			 					stm.executeUpdate(sql);
			 					conn.commit();
								
							} catch(SQLException es)
							{
								try {
									conn.rollback();
								}
								catch(SQLException e1) {
									e1.printStackTrace();
								}
							}
							finally {
								you.setAddress(tfAddress.getText());
							}
						}
						tfAddress.setEditable(!tfAddress.isEditable());
					}
				}
				
			}
		});
		
		
		JButton btnResetPassword = new JButton("Đổi mật khẩu");
		btnResetPassword.setFont(new Font("Arial", Font.BOLD, 13));
		btnResetPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame changepass = new JFrame("Change pass");
				JPanel change = new JPanel();
				
				JLabel jold=new JLabel("Nhập mật khẩu cũ:");
				JPasswordField old = new JPasswordField(25);
				
				JLabel jnew=new JLabel("Nhập mật khẩu mới:");
				JPasswordField newp = new JPasswordField(25);
				
				JLabel jcheck=new JLabel("Xác nhận mật khẩu mới:");
				JPasswordField check = new JPasswordField(25);
				
				JButton changeb = new JButton("OK");
				changeb.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String newpass = newp.getText();
						String verifyp = check.getText();
						String oldpass = old.getText();
						
						if(newpass.equals("")) {
			            	 jnew.setForeground(Color.yellow);
			            }
						
						if(oldpass.equals("")) {
			            	 jold.setForeground(Color.yellow);
			            }
						
						if(verifyp.equals("")) {
			            	 jcheck.setForeground(Color.yellow);
			            }
						
						if(!newpass.equals("") && !oldpass.equals("") && !verifyp.equals("")) {
							
							try {
								conn.setAutoCommit(false);
								Statement stmt = null;
								stmt = conn.createStatement();
								String sql = "SELECT user_password from users where user_name='" + you.getUsername() + "'";
			 					ResultSet rs = stmt.executeQuery(sql);
			 					conn.commit();
			 					
			 					if(rs.next()) {
			 						if(rs.getString(1).equals(oldpass)) {
			 							if(verifyp.equals(newpass)) {
			 								String changepassword = "UPDATE users SET user_password='" + newpass +"' where user_name='" + you.getUsername() + "'";
				 							stmt.executeUpdate(changepassword);
				 							conn.commit();
				 							changepass.dispose();
			 							}
			 							else {
			 								jcheck.setForeground(Color.red);
			 							}
			 						}
			 						else {
			 							jold.setForeground(Color.red);
			 						}
			 					}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				});
				
				change.add(jold);
				change.add(old);
				change.add(jnew);
				change.add(newp);
				change.add(jcheck);
				change.add(check);
				change.add(changeb);
				changepass.add(change);
				changepass.setSize(300,300);
				changepass.setLocationRelativeTo(null);
				changepass.setVisible(true);
			}
		});
		btnResetPassword.setBackground(new Color(235, 209, 105));
		btnResetPassword.setBounds(169, 318, 156, 27);
		UserPanel.add(btnResetPassword);
		
		
		
		
//		new Thread(new Thread_OnlineTab(conn, you,tabbedPane)).start();
		OnlineTab OnlinePanel = new OnlineTab(conn, you);
		tabbedPane.addTab("Online", OnlinePanel.createPanel());
		
		AllTab AllPanel = new AllTab(conn,you);
		tabbedPane.addTab("Tất cả", AllPanel.createPanel());
		
		PendingTab PendingPanel = new PendingTab(conn,you);
		tabbedPane.addTab("Pending", PendingPanel.createPanel());
		
		//GroupTab GroupPanel = new GroupTab(conn, you);
		//tabbedPane.addTab("Nhóm", GroupPanel.createPanel());
		//listOnline.setLayout(new GridLayout(1, 0, 0, 0));
		
		//JPanel listOnline = new JPanel();
		
		//JScrollPane scrollPane = new JScrollPane();
		//listOnline.setBounds(34, 62, 405, 271);
		//listOnline.add(scrollPane);
		//JLabel username = new JLabel("alsophanie");
		//JButton mess = new JButton("Nhắn tin");
		//JPanel line = new JPanel();
		//line.add(username);
		//line.add(mess);
		
		//scrollPane.add(line);
		
		//OnlinePanel.add(listOnline);
		
		//Label GroupTab = new Label("Label 4");
		//GroupTab.setBackground(new Color(255, 255, 255));
		//tabbedPane.addTab("Nhóm", GroupTab);
		frmChatter.getContentPane().add(tabbedPane);
		frmChatter.setVisible(true);
	}
}
