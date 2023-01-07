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

import network.Client;
import utils.DirectChatDB;

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
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ChatFrame {

	private JFrame frmChatter;
	private JTextField txtNhn;
	public DefaultTableModel chat;
	private String my_name,friend_name;
	private User you;
	Connection conn = null;
	private JTextField tfSearch;
	private String id_dialogue;
	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	
	public ChatFrame(Connection cnt, User u,String friend_name) throws IOException {
		you = u;
		conn = cnt;
		this.my_name=u.getUsername();
		this.friend_name=friend_name;
		initialize();
	}
	public ChatFrame(String my_name,String friend_name) throws IOException {
		this.my_name=my_name;
		this.friend_name=friend_name;
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
		frmChatter.setBounds(500, 200, 500, 500);
		frmChatter.setResizable(false);
		frmChatter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frmChatter.getContentPane().setBackground(new Color(206,157,217));
		frmChatter.getContentPane().setLayout(null);
		
		//frmChatter.setFocusable(true);
				
		tfSearch = new JTextField();
		tfSearch.setBounds(221, 26, 144, 19);
		frmChatter.getContentPane().add(tfSearch);
		tfSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Tìm kiếm");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSearch.setBounds(376, 25, 85, 21);
		frmChatter.getContentPane().add(btnSearch);

		JTable table = new JTable();
		
		 table.setModel(new javax.swing.table.DefaultTableModel(
		            new Object [][] {
		            	{"Welcome"}
		            },
		            new String [] {
		            		friend_name
		            }
		        ));
		 
		 table.setShowGrid(false);

		chat = (DefaultTableModel) table.getModel();
		
		JScrollPane sp = new JScrollPane(table);
		//sp.setPreferredSize(new Dimension(500,500));
		sp.setBounds(10,72,465,353);
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
		//thuc hien khi nhan send
		DirectChatDB dcdb=new DirectChatDB(conn,you);
		id_dialogue=dcdb.GetDRChatID(friend_name);
		dcdb.GetMessage(id_dialogue,chat);
		
		
		new Client(my_name).chat_direct(friend_name, txtNhn, sendBtn, chat);
		
		JButton btnDelete = new JButton("Xóa");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDelete.setBounds(10, 25, 85, 21);
		frmChatter.getContentPane().add(btnDelete);
		
		
		frmChatter.setVisible(true);
		frmChatter.setFocusable(true);
		frmChatter.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		        // call terminate
		    	try
		    	{ String sql ="UPDATE users SET user_online='false' where user_id="+you.getID();
		    	conn.setAutoCommit(false);
		    	Statement stm = conn.createStatement();
		    	stm.executeUpdate(sql);
		    	conn.commit();
		    	}catch(SQLException ex)
		    	{
		    		try{
		    			conn.rollback();
		    		}catch(SQLException exc)
		    		{
		    			exc.printStackTrace();
		    		}
		    	}
		    }
		});
	}
	
	public static void main(String[] args) {
		
		Connection conn = null;
		final String DB_URL = "jdbc:postgresql://localhost:5432/ChatDatabase";
		final String USER = "postgres";
		final String PASS = "Baochau14102002";
		final String JBDC_DRIVER = "org.postgresql.Driver";
		try {
			Class.forName(JBDC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Success");
		}
		catch (Exception se) {
			se.printStackTrace();
			System.out.print("Cannot connect");
			System.exit(1);
		}
		User test = new User(2);
		
		try {
			new ChatFrame(conn,test,"chacha");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//new ChatFrame("khoi","thao");
	}
}
