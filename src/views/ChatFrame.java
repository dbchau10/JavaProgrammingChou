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

public class ChatFrame {

	private JFrame frmChatter;
	private JTextField txtNhn;
	public DefaultTableModel chat;
	private String my_name, friend_name;
	private User you;
	Connection conn = null;
	private JTextField tfSearch;
	private String id_dialogue;
	private Client cl;

	/**
	 * Launch the application.
	 * 
	 * @throws IOException
	 */

	public ChatFrame(Connection cnt, User u, String friend_name, Client cl) throws IOException {
		you = u;
		conn = cnt;
		this.my_name = u.getUsername();
		this.friend_name = friend_name;
		this.cl = cl;
		initialize();
	}

	public ChatFrame(String my_name, String friend_name, Client cl) throws IOException {
		this.my_name = my_name;
		this.friend_name = friend_name;
		this.cl = cl;
		initialize();
	}

	/**
	 * Create the application.
	 */
	public void connect() {

	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		frmChatter = new JFrame();
		frmChatter.setTitle("Chatter!");
		frmChatter
				.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\DELL\\CNPM\\JavaProgramming\\background.jpg"));
		frmChatter.setBounds(500, 200, 500, 500);
		frmChatter.setResizable(false);
		frmChatter.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frmChatter.getContentPane().setBackground(new Color(206, 157, 217));
		frmChatter.getContentPane().setLayout(null);

		// frmChatter.setFocusable(true);

		tfSearch = new JTextField();
		tfSearch.setBounds(221, 26, 144, 19);
		frmChatter.getContentPane().add(tfSearch);
		tfSearch.setColumns(10);

		JButton btnSearch = new JButton("Tìm kiếm");

		btnSearch.setBounds(376, 25, 85, 21);
		btnSearch.setBackground(new Color(255, 128, 0));
		btnSearch.setFont(new Font("Arial", Font.BOLD, 10));
		frmChatter.getContentPane().add(btnSearch);

		JTable table = new JTable();

		table.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						my_name + "to" + friend_name, "num"
				}));

		table.setShowGrid(false);

		chat = (DefaultTableModel) table.getModel();

		JScrollPane sp = new JScrollPane(table);
		// sp.setPreferredSize(new Dimension(500,500));
		sp.setBounds(10, 72, 465, 353);
		table.getColumnModel().getColumn(1).setMinWidth(0);
		table.getColumnModel().getColumn(1).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setWidth(0);

		frmChatter.getContentPane().add(sp);
		txtNhn = new JTextField();
		txtNhn.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtNhn.getText().trim().equals("Nhập tin nhắn...")) {
					txtNhn.setText("");
					txtNhn.setFont(new Font("Arial", Font.PLAIN, 12));
					txtNhn.setForeground(new Color(0, 0, 0));

				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtNhn.getText().trim().equals("")) {
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
		// thuc hien khi nhan send
		DirectChatDB dcdb = new DirectChatDB(conn, you);
		id_dialogue = dcdb.GetDRChatID(friend_name);
		dcdb.GetMessage(id_dialogue, chat);

		JButton btn_del = new JButton("Xóa");
		btn_del.setBounds(10, 25, 85, 21);
		btn_del.setFont(new Font("Arial", Font.PLAIN, 10));
		btn_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pos = table.getSelectedRow();
				if (pos != -1) {
					ChatMessage cm = null;
					cm = dcdb.getChooseMessage(pos);
					dcdb.DeleteMessage(cm);

					dcdb.changeNumRow(pos);

					chat.removeRow(pos);
				}
			}
		});
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					conn.setAutoCommit(false);
					Statement stm = conn.createStatement();
					// Vector<ChatMessage> messagehis = new Vector<ChatMessage>();
					String mes = "SELECT * FROM (SELECT * FROM direct_chatmessage where drchat_id='" + id_dialogue
							+ "'and message_inf like '%" + tfSearch.getText()
							+ "%' except select drchat_id,message_date,user_id,message_inf from  erased_direct_chatmessage where deleted_user_id='"
							+ you.getID() + "'and drchat_id='" + id_dialogue
							+ "')as foo left join users on foo.user_id = users.user_id order by foo.message_date asc";

					ResultSet rs = stm.executeQuery(mes);
					chat.setRowCount(0);
					int num = 0;
					while (rs.next()) {

						ChatMessage chathis = new ChatMessage(num, rs.getString("drchat_id"),
								rs.getString("message_date"), Integer.parseInt(rs.getString("user_id")),
								rs.getString("message_inf"), rs.getString("user_name"));
						chat.addRow(new Object[] { chathis.getName() + ":" + chathis.getMessage_info() });
						// messagehis.add(chathis);
						num += 1;
					}

				}

				catch (SQLException exc) {
					// TODO Auto-generated catch block
					exc.printStackTrace();
				}
			}
		});

		tfSearch.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						conn.setAutoCommit(false);
						Statement stm = conn.createStatement();
						// Vector<ChatMessage> messagehis = new Vector<ChatMessage>();
						String mes = "SELECT * FROM (SELECT * FROM direct_chatmessage where drchat_id='" + id_dialogue
								+ "'and message_inf like '%" + tfSearch.getText()
								+ "%' except select drchat_id,message_date,user_id,message_inf from  erased_direct_chatmessage where deleted_user_id='"
								+ you.getID() + "'and drchat_id='" + id_dialogue
								+ "')as foo left join users on foo.user_id = users.user_id order by foo.message_date asc";

						ResultSet rs = stm.executeQuery(mes);
						chat.setRowCount(0);
						int num = 0;
						while (rs.next()) {

							ChatMessage chathis = new ChatMessage(num, rs.getString("drchat_id"),
									rs.getString("message_date"), Integer.parseInt(rs.getString("user_id")),
									rs.getString("message_inf"), rs.getString("user_name"));
							chat.addRow(new Object[] { chathis.getName() + ":" + chathis.getMessage_info() });
							// messagehis.add(chathis);
							num += 1;
						}

					}

					catch (SQLException exc) {
						// TODO Auto-generated catch block
						exc.printStackTrace();
					}
				}

			}
		});
		frmChatter.add(btn_del);
		frmChatter.setVisible(true);
		frmChatter.setFocusable(true);

		// cl.chat_direct(friend_name, txtNhn, sendBtn, chat,id_dialogue,dcdb);
		cl.chat_direct_test(friend_name, txtNhn, sendBtn, chat, id_dialogue);
	}

	public static void main(String[] args) {

		// Connection conn = null;
		// final String DB_URL = "jdbc:postgresql://localhost:5432/ChatDatabase";
		// final String USER = "postgres";
		// final String PASS = "Baochau14102002";
		// final String JBDC_DRIVER = "org.postgresql.Driver";
		// try {
		// Class.forName(JBDC_DRIVER);
		// System.out.println("Connecting to database...");
		// conn = DriverManager.getConnection(DB_URL, USER, PASS);
		// System.out.println("Success");
		// }
		// catch (Exception se) {
		// se.printStackTrace();
		// System.out.print("Cannot connect");
		// System.exit(1);
		// }
		//// User test = new User(7,"chacha");
		// User test = new User(2,"abc");
		// //System.out.println(test.getUsername()+"????S");
		// try {
		// new ChatFrame(conn,test,"chacha");
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// new ChatFrame("khoi","thao");
		// Client cl=new Client("chau");
		// Thread t1 = new Thread(new Runnable() {
		// public void run()
		// {
		// try {
		// new ChatFrame("chau","tai",cl);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }});
		// t1.start();
		// Thread t2 = new Thread(new Runnable() {
		// public void run()
		// {
		// try {
		// new ChatFrame("chau","khoi",cl);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }});
		// t2.start();
		//
		// Client cl2=new Client("tai");
		// t1 = new Thread(new Runnable() {
		// public void run()
		// {
		// try {
		// new ChatFrame("tai","chau",cl2);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }});
		// t1.start();
		// t2 = new Thread(new Runnable() {
		// public void run()
		// {
		// try {
		// new ChatFrame("tai","khoi",cl2);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }});
		// t2.start();
		//
		// Client cl3=new Client("khoi");
		// t1 = new Thread(new Runnable() {
		// public void run()
		// {
		// try {
		// new ChatFrame("khoi","chau",cl3);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }});
		// t1.start();
		// t2 = new Thread(new Runnable() {
		// public void run()
		// {
		// try {
		// new ChatFrame("khoi","tai",cl3);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }});
		// t2.start();
	}
}
