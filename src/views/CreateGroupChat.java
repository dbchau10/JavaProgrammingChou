package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import components.FriendCom;
import components.MemberCom;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import utils.GroupChatDB;
public class CreateGroupChat {

	private JFrame createGroup;
	private User you;
	private Connection conn = null;
	Vector<Integer> member;
	String groupname;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the application.
	 */
	public CreateGroupChat(User you, Connection conn, String name) {
		createGroup = new JFrame();
		this.you = you;
		this.conn = conn;
		this.groupname = name;
		member= new Vector<Integer>();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		createGroup.setFocusable(true);
		createGroup.setTitle("Chatter!");
		createGroup.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\DELL\\CNPM\\JavaProgramming\\background.jpg"));
		createGroup.setBounds(500, 200, 598, 500);
		createGroup.setResizable(false);
		createGroup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		createGroup.getContentPane().setBackground(new Color(206,157,217));
		createGroup.getContentPane().setLayout(null);
		
		JTextField tfNewGroup = new JTextField();
		tfNewGroup.setBounds(76, 46, 331, 19);
		createGroup.getContentPane().add(tfNewGroup);
		tfNewGroup.setColumns(10);
		
		tfNewGroup.setForeground(new Color(115, 115, 115));
		tfNewGroup.setFont(new Font("Arial", Font.ITALIC, 10));
		tfNewGroup.setText(groupname);
		tfNewGroup.setEnabled(false);
		
		JTextField tfNewMember = new JTextField();
		tfNewMember.setBounds(76, 93, 331, 19);
		createGroup.getContentPane().add(tfNewMember);
		tfNewMember.setColumns(10);
		
		
		
		tfNewMember.setForeground(new Color(115, 115, 115));
		tfNewMember.setFont(new Font("Arial", Font.ITALIC, 10));
		tfNewMember.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (tfNewMember.getText().trim().equals("Nhập tài khoản"))
				{
					tfNewMember.setText("");
					tfNewMember.setForeground(new Color(0, 0, 0));
					tfNewMember.setFont(new Font("Arial", Font.PLAIN, 10));
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (tfNewMember.getText().trim().equals(""))
				{
					tfNewMember.setText("Nhập tài khoản");
					tfNewMember.setForeground(new Color(115, 115, 115));
					tfNewMember.setFont(new Font("Arial", Font.ITALIC, 10));
				}
			}
		});
		
		tfNewMember.setText("Nhập tài khoản");
		
		
		
		
		
		JPanel listAccount = new JPanel();
		listAccount.setBounds(86, 150, 404, 257);
		listAccount.setLayout(new BoxLayout(listAccount,BoxLayout.Y_AXIS));
		
		//new Thread(new Thread_OnlineTab(conn, you,listOnline)).start();
		
	
		
		JScrollPane js = new JScrollPane(listAccount, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		js.setBounds(76, 150, 430, 257);
	
		createGroup.getContentPane().add(js);
		
		JButton btnSearch = new JButton("Tìm kiếm");
		btnSearch.setBounds(421, 92, 85, 21);
		createGroup.getContentPane().add(btnSearch);
		
		btnSearch.setBackground(new Color(235, 209, 105));
		btnSearch.setFont(new Font("Arial", Font.PLAIN, 10));
		
		JButton create = new JButton("Tạo nhóm");
		create.setBounds(229, 417, 103, 21);
		createGroup.getContentPane().add(create);
		
		create.setBackground(new Color(235, 209, 105));
		create.setFont(new Font("Arial", Font.PLAIN, 15));
		
		tfNewMember.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode()==KeyEvent.VK_ENTER)
				{
					
					if(!tfNewMember.getText().trim().equals("") && !tfNewMember.getText().trim().equals("Nhập tài khoản"))
					{	
						String findusername = tfNewMember.getText();
					
						
						Statement stm =null;
						ResultSet res = null;
						try {
							conn.setAutoCommit(false);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String findid = "SELECT * FROM users where user_name like '%" + findusername + "%'";
						Vector<User> fid = new Vector<User>();
						try {
							
							stm = conn.createStatement();
							res = stm.executeQuery(findid);
							conn.commit();

							while(res.next()) {
								
								Integer id = res.getInt("user_id");
								String username = res.getString("user_name");
								String hoten = res.getString("user_hoten");
								String dob = res.getString("user_ngaysinh");
								String email = res.getString("user_email");
								String address = res.getString("user_diachi");
								User user = new User(id,username,hoten,dob,email,address);
								
								fid.add(user);
								
								

							}
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
						listAccount.removeAll();
						listAccount.revalidate();
						listAccount.repaint();
						
						
							for(int i = 0; i<fid.size(); i++) {
									if (member.contains(fid.get(i).getID()))
									{
										MemberCom fr = new MemberCom(conn,you,fid.get(i),member);
										listAccount.add(fr.initialize(fid.get(i).getUsername(),"Đã thêm"));
									}
									else {
									MemberCom fr = new MemberCom(conn,you,fid.get(i),member);
									listAccount.add(fr.initialize(fid.get(i).getUsername(),"Thêm"));
									
									}
									JSeparator separator = new JSeparator();
									separator.setBounds(10, 33, 353, 2);
									
									listAccount.add(separator);

							}
									
								
							
							
					}
				}
			}
		});
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				if(!tfNewMember.getText().trim().equals("") && !tfNewMember.getText().trim().equals("Nhập tài khoản"))
				{	
					String findusername = tfNewMember.getText();
				
					
					Statement stm =null;
					ResultSet res = null;
					try {
						conn.setAutoCommit(false);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String findid = "SELECT * FROM users where user_name like '%" + findusername + "%'";
					Vector<User> fid = new Vector<User>();
					try {
						
						stm = conn.createStatement();
						res = stm.executeQuery(findid);
						conn.commit();

						while(res.next()) {
							
							Integer id = res.getInt("user_id");
							String username = res.getString("user_name");
							String hoten = res.getString("user_hoten");
							String dob = res.getString("user_ngaysinh");
							String email = res.getString("user_email");
							String address = res.getString("user_diachi");
							User user = new User(id,username,hoten,dob,email,address);
							
							fid.add(user);
							member.add(id);
							

						}
						
					} catch (SQLException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				
					listAccount.removeAll();
					listAccount.revalidate();
					listAccount.repaint();
					
					
					for(int i = 0; i<fid.size(); i++) {
						if (member.contains(fid.get(i).getID()))
						{
							MemberCom fr = new MemberCom(conn,you,fid.get(i),member);
							listAccount.add(fr.initialize(fid.get(i).getUsername(),"Đã thêm"));
						}
						else {
						MemberCom fr = new MemberCom(conn,you,fid.get(i),member);
						listAccount.add(fr.initialize(fid.get(i).getUsername(),"Thêm"));
						
						}
						JSeparator separator = new JSeparator();
						separator.setBounds(10, 33, 353, 2);
						
						listAccount.add(separator);

				}
								
							
						
						
				}
					}					
				
		
		});
		
		
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				GroupChatDB gc = new GroupChatDB(conn,you);
				gc.createGroupChat(member, groupname);
			}
			
		});
		createGroup.setVisible(true);
	}
}
