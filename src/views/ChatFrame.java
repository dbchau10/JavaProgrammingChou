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
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;

public class ChatFrame {

	private JFrame frmChatter;
	private JTextField txtNhn;
	DefaultTableModel chat;
	private Socket socket=null;
	private BufferedReader reader=null;
	private PrintWriter sender=null;
	private String my_name=null;
	private User you;
	Connection conn = null;

	/**
	 * Launch the application.
	 */
	
	public ChatFrame(Connection cnt, User u) {
		you = u;
		conn = cnt;

		initialize();
	}
	

	/**
	 * Create the application.
	 */
	public void connect() {
		
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
		frmChatter.setBounds(500, 200, 500, 500);
		frmChatter.setResizable(false);
		frmChatter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frmChatter.getContentPane().setBackground(new Color(206,157,217));
		frmChatter.setLayout(null);
		
		frmChatter.setFocusable(true);
		
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
		sp.setBounds(10,10,465,415);
		frmChatter.add(sp);
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
		frmChatter.add(txtNhn);
		txtNhn.setColumns(10);
		
		JButton sendBtn = new JButton("Nhắn");
		sendBtn.setBackground(new Color(235, 209, 105));
		sendBtn.setFont(new Font("Arial", Font.BOLD, 12));
		sendBtn.setBounds(391, 432, 85, 22);
		frmChatter.add(sendBtn);
		//thuc hien khi nhan send
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		txtNhn.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode()==KeyEvent.VK_ENTER)
				{
				}
			}
		});
		
	}
}
