package network;

import java.net.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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


public class Client  {
	private Socket socket=null;
	private BufferedReader reader=null;
	private PrintWriter sender=null;
	private boolean Run=true;
	private String my_name,friend_name;
//
//	private JTextField txtNhn;
//	private DefaultTableModel chat;
//	private JButton sendBtn;
	public void chat_direct(String friend_name,JTextField txtNhn,
			JButton sendBtn,DefaultTableModel chat) throws IOException {
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					send_message(friend_name,txtNhn,chat);
			}
		});
		
		txtNhn.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode()==KeyEvent.VK_ENTER)
				{
					send_message(friend_name,txtNhn,chat);
				}
			}
		});
		// Thread read from server
		while (true) {

			if (!Run) return;
			
			String message=null;
			while(reader.ready()) {
				message=reader.readLine();
				//String[] result_message=parse_message(message);
				//if (result_message[0].equals("MD")) {
					//String message_rv=result_message[1]+":"+result_message[2];
				chat.addRow(new Object[] {message});
				//}
				
//				textArea.append(message+"\n");
				System.out.println(message);
				if (message!=null) {
					System.out.println(message);
				}
			}
		}
	}
	public Client(String my_name){
		this.my_name=my_name;
//		this.friend_name=friend_name;
//		this.txtNhn=txtNhn;
//		this.sendBtn=sendBtn;
//		this.chat=chat;
		
		
		String local_address="192.168.56.1";
		try {
			InetAddress myhost= InetAddress.getLocalHost();
			local_address=myhost.getHostAddress();
			System.out.println(myhost.getHostAddress());
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		try {
			
			socket= new Socket(local_address,3000);
			System.out.println("connect sucess");
			reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			sender=new PrintWriter(socket.getOutputStream());
			//user_name and password instead of name				
			sender.println(my_name);
			sender.flush();
			//end
			// thread read from jframe and send to server
			//new Thread(new Thread_Read(sender,jfrm,my_name,textArea)).start();
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
//		finally {
//			try {
//				
//				socket.close();
//				reader.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			sender.close();
//		}
	}
	public void send_message(String friend_name,JTextField txtNhn,DefaultTableModel chat) {
		chat.addRow(new Object[] {my_name+":"+txtNhn.getText()});
		sender.println("MD`"+friend_name+"`"+txtNhn.getText());
		sender.flush();
	}
	public String[] parse_message(String message) {
		
		return message.split("`");
	}
	public static void main(String[] args){
		//new Client("khoi");
		// TODO Auto-generated method stub
//		for (String s:parse_message("md`vo khoi`ngu nguoi`cc`")) {
//			System.out.println(s);
//		}
	}

}

