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
import java.sql.Connection;

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

public class UserUtil {
	
	private User you;
	Connection conn = null;
	private JFrame frmChatter;
	private JTextField tfUsername;
	private JTextField tfFullname;
	private JTextField tfDOB;
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
		
		tfDOB = new JTextField();
		tfDOB.setEditable(false);
		tfDOB.setText("01/01/2001");
		tfDOB.setFont(new Font("Arial", Font.PLAIN, 13));
		tfDOB.setColumns(10);
		tfDOB.setBounds(169, 168, 156, 19);
		UserPanel.add(tfDOB);
		
		tfEmail = new JTextField();
		tfEmail.setEditable(false);
		tfEmail.setText("xxx@yahoo.com");
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
				else tfFullname.setEditable(!tfFullname.isEditable());

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
					else tfFullname.setEditable(!tfFullname.isEditable());
				}
				
			}
		});
		
		
		JButton btnDOB = new JButton("Sửa");
		btnDOB.setFont(new Font("Arial", Font.PLAIN, 10));
		btnDOB.setBounds(359, 168, 65, 21);
		UserPanel.add(btnDOB);
		
		btnDOB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!checkDate(tfDOB.getText()))
				{
					JOptionPane.showMessageDialog(null, "Value invalid. Please try again");
				}
				else tfDOB.setEditable(!tfDOB.isEditable());

			}
		});
		
		
		tfDOB.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode()==KeyEvent.VK_ENTER)
				{
					
					if(!checkDate(tfDOB.getText()))
					{
						JOptionPane.showMessageDialog(null, "Value invalid. Please try again");
					}
					else tfDOB.setEditable(!tfDOB.isEditable());
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
				else tfEmail.setEditable(!tfEmail.isEditable());
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
					else tfEmail.setEditable(!tfEmail.isEditable());
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
				else tfAddress.setEditable(!tfAddress.isEditable());
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
					else tfAddress.setEditable(!tfAddress.isEditable());
				}
				
			}
		});
		
		
		JButton btnResetPassword = new JButton("Đổi mật khẩu");
		btnResetPassword.setFont(new Font("Arial", Font.BOLD, 13));
		btnResetPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnResetPassword.setBackground(new Color(235, 209, 105));
		btnResetPassword.setBounds(169, 318, 156, 27);
		UserPanel.add(btnResetPassword);
		
		
		
		OnlineTab OnlinePanel = new OnlineTab(conn, you);
		tabbedPane.addTab("Online", OnlinePanel.createPanel());
		
		
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
		Label AllTab = new Label("Label 3");
		AllTab.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Tất cả", AllTab);
		Label GroupTab = new Label("Label 4");
		GroupTab.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Nhóm", GroupTab);
		frmChatter.getContentPane().add(tabbedPane);
	}
}
