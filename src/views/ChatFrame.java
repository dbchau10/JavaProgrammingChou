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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
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

	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	
	public ChatFrame(Connection cnt, User u,String friend_name) throws IOException {
		you = u;
		conn = cnt;
		this.my_name=u.getName();
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
		tfSearch.setBounds(221, 19, 144, 19);
		frmChatter.getContentPane().add(tfSearch);
		tfSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Tìm kiếm");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSearch.setBounds(376, 18, 85, 21);
		frmChatter.getContentPane().add(btnSearch);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 68, 471, 1);
		frmChatter.getContentPane().add(separator);
		
		JLabel chatter = new JLabel(friend_name);
		chatter.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		chatter.setHorizontalAlignment(SwingConstants.CENTER);
		chatter.setBounds(211, 44, 58, 19);
		frmChatter.getContentPane().add(chatter);
		
		JTable table = new JTable();
		
		 table.setModel(new javax.swing.table.DefaultTableModel(
		            new Object [][] {
		            	{"hello"},{"hi"}
		            },
		            new String [] {
		            		"message"
		            }
		        ));
		 
		 table.setShowGrid(false);

		chat = (DefaultTableModel) table.getModel();
		
		JScrollPane sp = new JScrollPane(table);
		//sp.setPreferredSize(new Dimension(500,500));
		sp.setBounds(10,79,465,346);
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
		new Client(my_name).chat_direct(friend_name, txtNhn, sendBtn, chat);
		
		
		frmChatter.setVisible(true);
		frmChatter.setFocusable(true);
	}
	
	
}
