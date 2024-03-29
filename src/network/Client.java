package network;

import java.net.*;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import utils.DirectChatDB;
import utils.GroupChat;
import utils.GroupChatDB;
import views.ChatFrame;
import views.User;

import java.io.*;
import java.util.*;
import javax.net.ssl.*;
import static java.lang.Thread.currentThread;

import java.awt.*;
import java.awt.event.*;

//class Thread_Read implements Runnable {
////	public static PrintWriter sender;
////	Thread_Read (PrintWriter send,JFrame jf,String name, JTextArea mess) throws IOException{
////		sender=send;
////		jfrm=jf;
////		my_name=name;
////		textArea=mess;
////	}
////    public Thread_Read(JTextArea textArea2) {
////		// TODO Auto-generated constructor stub
////	}
//	public void run()
//    {			
//		if (!Client.Run) return;
//		idInput.addKeyListener(new KeyAdapter() {
//			public void keyPressed(KeyEvent e) {
//				if (!Client.Run) return;
////				if(!idInput.getText() .equals(""))System.out.println(idInput.getText());
//		        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
//		        	String text = idInput.getText();
//		            if (text.equals("Quit")) {
//						Client.sender.println("Q"); // send "Q"
//						Client.sender.flush();
//						jfrm.setVisible(false);
//						
//						Client.Run=false;
//						currentThread().interrupt();
//						return;
//						
//						
////						break;
//					}
//					else if (text!=null) {
//						String tag="M";
//						textArea.append(my_name+": "+text+ "\n");
//						System.out.println(my_name+": "+text);
//						sender.println(tag+" "+text);
//						sender.flush();
//						idInput.setText("");
//					}
//		        }
//			}
//		});
//    }
//}

public class Client {
	private Socket socket = null;
	private BufferedReader reader = null;
	private PrintWriter sender = null;
	private boolean Run = true;
	private String my_name;
	private DefaultTableModel chat_direct;
	public HashMap<String, DefaultTableModel> Name2TableChatDirect = new HashMap<String, DefaultTableModel>();
	public HashMap<String, DefaultTableModel> Name2TableChatGroup = new HashMap<String, DefaultTableModel>();

	// private Connection conn = null;
	// private String id_DC;
	//
	// private JTextField txtNhn;
	// private DefaultTableModel chat;
	// private JButton sendBtn;
	public void chat_direct(String friend_name, JTextField txtNhn,
			JButton sendBtn, DefaultTableModel chat, String id_dialogue, DirectChatDB dcdb) throws IOException {
		// this.conn=conn;
		// this.id_DC=id_dialogue;
		this.Name2TableChatDirect.put("MD" + friend_name, chat);
		// this.chat_direct=chat;
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtNhn.getText().equals("")) {
					send_message_direct(friend_name, txtNhn, chat);

					dcdb.SaveMessage(txtNhn.getText(), id_dialogue);
					txtNhn.setText("");
				}

			}
		});

		txtNhn.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {

				if (event.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!txtNhn.getText().equals("")) {
						send_message_direct(friend_name, txtNhn, chat);
						dcdb.SaveMessage(txtNhn.getText(), id_dialogue);
						txtNhn.setText("");
					}

				}
			}
		});
		// Thread read from server

	}

	public void chat_group(GroupChat gn, String group_name, JTextField txtNhn,
			JButton sendBtn, DefaultTableModel chat, String id_dialogue, GroupChatDB dcdb) throws IOException {
		// this.conn=conn;
		// this.id_DC=id_dialogue;
		this.Name2TableChatGroup.put("MG" + group_name, chat);
		// this.chat_direct=chat;
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtNhn.getText().equals("")) {
					send_message_group(group_name, txtNhn, chat);
					dcdb.SaveMessage(txtNhn.getText(), gn);
					txtNhn.setText("");
				}

			}
		});

		txtNhn.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {

				if (event.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!txtNhn.getText().equals("")) {
						send_message_group(group_name, txtNhn, chat);
						dcdb.SaveMessage(txtNhn.getText(), gn);
						txtNhn.setText("");
					}

				}
			}
		});
		// Thread read from server

	}

	public void chat_direct_test(String friend_name, JTextField txtNhn,
			JButton sendBtn, DefaultTableModel chat, String id_dialogue) throws IOException {
		// this.conn=conn;
		// this.id_DC=id_dialogue;
		this.Name2TableChatDirect.put("MD" + friend_name, chat);
		// this.chat_direct=chat;
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtNhn.getText().equals("")) {
					send_message_direct(friend_name, txtNhn, chat);
					txtNhn.setText("");
				}

			}
		});

		txtNhn.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {

				if (event.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!txtNhn.getText().equals("")) {
						send_message_direct(friend_name, txtNhn, chat);
						txtNhn.setText("");
					}

				}
			}
		});
		// Thread read from server

	}

	public void read_and_show() throws IOException {
		while (true) {

			if (!Run)
				return;

			String message = null;
			while (reader.ready()) {
				message = reader.readLine();
				System.out.println(message + "???????");
				String[] result_message = parse_message(message);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				// if (result_message[0].equals("MD")) {
				// String message_rv=result_message[1]+":"+result_message[2];
				if (result_message[0].equals("MD")) {

					// this.chat_direct.addRow(new Object[] {result_message[1]});
					String id_table = result_message[1];
					if (Name2TableChatDirect.get(id_table) != null) {
						Name2TableChatDirect.get(id_table)
								.addRow(new Object[] { dtf.format(now) + "-" + result_message[2] });
					}
				} else if (result_message[0].equals("MG")) {
					String id_table = result_message[1];
					if (Name2TableChatGroup.get(id_table) != null) {
						Name2TableChatGroup.get(id_table)
								.addRow(new Object[] { dtf.format(now) + "-" + result_message[2] });
					}
				}
				// }

				// textArea.append(message+"\n");
				System.out.println(message);
				if (message != null) {
					System.out.println(message);
				}
			}
		}
	}

	public Client(String my_name) {
		this.my_name = my_name;
		// this.friend_name=friend_name;
		// this.txtNhn=txtNhn;
		// this.sendBtn=sendBtn;
		// this.chat=chat;

		String local_address = "192.168.56.1";
		try {
			InetAddress myhost = InetAddress.getLocalHost();
			local_address = myhost.getHostAddress();
			System.out.println(myhost.getHostAddress());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {

			socket = new Socket(local_address, Server.port);
			System.out.println("connect sucess");
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			sender = new PrintWriter(socket.getOutputStream());
			// user_name and password instead of name
			sender.println(my_name);
			sender.flush();

			Thread t3 = new Thread(new Runnable() {
				public void run() {
					try {
						read_and_show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t3.start();
			// end
			// thread read from jframe and send to server
			// new Thread(new Thread_Read(sender,jfrm,my_name,textArea)).start();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// finally {
		// try {
		//
		// socket.close();
		// reader.close();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// sender.close();
		// }
	}

	public void send_message_group(String group_name, JTextField txtNhn, DefaultTableModel chat) {
		// chat.addRow(new Object[] {my_name+":"+txtNhn.getText()});
		// sender.println("MD`"+my_name+"`"+friend_name+"`"+txtNhn.getText());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		String id_table = "MG" + group_name;
		if (Name2TableChatGroup.get(id_table) != null) {
			Name2TableChatGroup.get(id_table)
					.addRow(new Object[] { dtf.format(now) + "-" + my_name + ":" + txtNhn.getText() });
		}
		sender.println("MG`" + group_name + "`" + txtNhn.getText());
		sender.flush();
	}

	public void send_message_direct(String friend_name, JTextField txtNhn, DefaultTableModel chat) {
		// chat.addRow(new Object[] {my_name+":"+txtNhn.getText()});
		// sender.println("MD`"+my_name+"`"+friend_name+"`"+txtNhn.getText());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String id_table = "MD" + friend_name;
		if (Name2TableChatDirect.get(id_table) != null) {
			Name2TableChatDirect.get(id_table)
					.addRow(new Object[] { dtf.format(now) + "-" + my_name + ":" + txtNhn.getText() });
		}
		sender.println("MD`" + friend_name + "`" + txtNhn.getText());
		sender.flush();
	}

	public String[] parse_message(String message) {

		return message.split("`");
	}

	public static void main(String[] args) {
		// new Client("khoi");
		// TODO Auto-generated method stub
		// for (String s:parse_message("md`vo khoi`ngu nguoi`cc`")) {
		// System.out.println(s);
		// }
	}

}
