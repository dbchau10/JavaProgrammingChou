package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import components.FriendCom;
import network.Client;
import utils.ChatMessage;
import utils.DirectChatDB;
import utils.GroupChat;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GroupChatFrame {

	private JFrame frmChatter;
	private JTextField txtNhn;
	public DefaultTableModel chat;
	private String group_name, my_name;
	private User you;
	Connection conn = null;
	private JTextField tfSearch;
	private String id_dialogue;
	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	
	public GroupChatFrame(Connection cnt, User u,String groupname) throws IOException {
		you = u;
		conn = cnt;
	//	this.my_name=u.getUsername();
		this.my_name="abc";
		this.group_name=groupname;
		initialize();
	}
	
	public GroupChatFrame() throws IOException {
	
		this.my_name="abc";
		this.group_name="def";
		initialize();
	}
	
	/**
	 * Create the application.
	 */
	public void connect() {
		
	}
	
	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frmChatter = new JFrame();
		frmChatter.setTitle("Chatter!");
		frmChatter.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\DELL\\CNPM\\JavaProgramming\\background.jpg"));
		frmChatter.setBounds(500, 200, 700, 500);
		frmChatter.setResizable(false);
		frmChatter.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		frmChatter.getContentPane().setBackground(new Color(206,157,217));
		frmChatter.getContentPane().setLayout(null);
		
		//frmChatter.setFocusable(true);
				
		tfSearch = new JTextField();
		tfSearch.setBounds(221, 26, 339, 19);
		frmChatter.getContentPane().add(tfSearch);
		tfSearch.setColumns(10);
		
		
					
		
		JButton btnSearch = new JButton("Tìm kiếm");
		
		btnSearch.setBackground(new Color(255, 128, 0));
		btnSearch.setFont(new Font("Arial", Font.BOLD, 10));
		
		btnSearch.setBounds(570, 25, 85, 21);
		frmChatter.getContentPane().add(btnSearch);

		JTable table = new JTable();
		
		 table.setModel(new javax.swing.table.DefaultTableModel(
		            new Object [][] {
		            },
		            new String [] {
		            		group_name,"num"
		            }
		        ));
		 
		 table.setShowGrid(false);

		chat = (DefaultTableModel) table.getModel();
		
		JScrollPane sp = new JScrollPane(table);
		//sp.setPreferredSize(new Dimension(500,500));
		sp.setBounds(10,72,465,353);
		table.getColumnModel().getColumn(1).setMinWidth(0);
		table.getColumnModel().getColumn(1).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setWidth(0);
		
		frmChatter.getContentPane().add(sp);
		txtNhn = new JTextField();
		txtNhn.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtNhn.getText().trim().equals("Nhập tin nhắn..."))
				{
					txtNhn.setText("");
					txtNhn.setFont(new Font("Arial", Font.PLAIN, 12));
					txtNhn.setForeground(new Color(0, 0, 0));
					
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (txtNhn.getText().trim().equals(""))
				{
					txtNhn.setText("Nhập tin nhắn...");
					txtNhn.setFont(new Font("Arial", Font.ITALIC, 12));
					txtNhn.setForeground(new Color(104, 104, 104));
				}
			}
		});
		txtNhn.setForeground(new Color(104, 104, 104));
		txtNhn.setText("Nhập tin nhắn...");
		txtNhn.setFont(new Font("Arial", Font.ITALIC, 12));
		txtNhn.setBounds(10, 432, 371, 21);
		frmChatter.getContentPane().add(txtNhn);
		txtNhn.setColumns(10);
		
		JButton sendBtn = new JButton("Nhắn");
		sendBtn.setBackground(new Color(235, 209, 105));
		sendBtn.setFont(new Font("Arial", Font.BOLD, 12));
		sendBtn.setBounds(391, 432, 85, 22);
		frmChatter.getContentPane().add(sendBtn);
		
		
		JButton btn_del = new JButton("Xóa");
		btn_del.setFont(new Font("Arial", Font.PLAIN, 10));
		btn_del.setBounds(10, 25, 85, 21);
		
		
		frmChatter.getContentPane().add(btn_del);
		
		JPanel panel = new JPanel();
		panel.setBounds(487, 72, 168, 365);
		frmChatter.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnAdd = new JButton("Thêm");
		btnAdd.setBackground(new Color(249, 17, 168));
		btnAdd.setForeground(new Color(255, 255, 255));
		btnAdd.setFont(new Font("Arial", Font.PLAIN, 10));
		btnAdd.setBounds(29, 41, 97, 21);
		panel.add(btnAdd);
		
		JButton btnDelete = new JButton("Xóa mem");
		btnDelete.setBackground(new Color(32, 223, 128));
		btnDelete.setFont(new Font("Arial", Font.PLAIN, 10));
		
		btnDelete.setBounds(29, 107, 97, 21);
		panel.add(btnDelete);
		
		JButton btnAdmin = new JButton("Trao quyền");
		btnAdmin.setForeground(new Color(255, 255, 255));
		btnAdmin.setBackground(new Color(129, 158, 248));
		btnAdmin.setFont(new Font("Arial", Font.PLAIN, 10));
		btnAdmin.setBounds(29, 173, 97, 21);
		panel.add(btnAdmin);
		
		JButton btnRename = new JButton("Đổi tên");
		btnRename.setFont(new Font("Arial", Font.PLAIN, 10));
		btnRename.setBounds(29, 239, 97, 21);
		btnRename.setBackground(new Color(235, 209, 105));
		panel.add(btnRename);
		
		JButton btnDeleteGroup = new JButton("Xóa nhóm");
		btnDeleteGroup.setFont(new Font("Arial", Font.PLAIN, 10));
		btnDeleteGroup.setBounds(29, 305, 97, 21);
		btnDeleteGroup.setForeground(new Color(255, 255, 255));
		btnDeleteGroup.setBackground(new Color(241, 84, 7));
		panel.add(btnDeleteGroup);
		frmChatter.setVisible(true);
		frmChatter.setFocusable(true);
			}
	
	public static void main(String[] args) {
	//	Connection conn = null;
	//	User u = new User(1);
		try {
			//new GroupChatFrame(conn,u,"chacha");
			new GroupChatFrame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
