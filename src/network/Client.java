package network;

import java.net.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.io.*;

import static java.lang.Thread.currentThread;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import java.net.*;
import javax.net.ssl.*;
import java.io.*;
import java.util.*; 



class Thread_Read implements Runnable {
	private JFrame jfrm;
	private PrintWriter sender;
	public static JTextField idInput;
	private String my_name;
	private JTextArea textArea;
	Thread_Read (PrintWriter send,JFrame jf,String name, JTextArea mess) throws IOException{
		sender=send;
		jfrm=jf;
		my_name=name;
		textArea=mess;
	}
    public Thread_Read(JTextArea textArea2) {
		// TODO Auto-generated constructor stub
	}
	public void run()
    {
		idInput = new JTextField();
		idInput.setFont(new Font("Tahoma", Font.PLAIN, 13));
		idInput.setBounds(34, 386, 639, 20);
		jfrm.getContentPane().add(idInput);
		idInput.setColumns(10);
		if (!Client.Run) return;
		idInput.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (!Client.Run) return;
//				if(!idInput.getText() .equals(""))System.out.println(idInput.getText());
		        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
		        	String text = idInput.getText();
		            if (text.equals("Quit")) {
						sender.println("Q"); // send "Q"
						sender.flush();
						jfrm.setVisible(false);
						
						Client.Run=false;
						currentThread().interrupt();
						return;
						
						
//						break;
					}
					else if (text!=null) {
						String tag="M";
						textArea.append(my_name+": "+text+ "\n");
						System.out.println(my_name+": "+text);
						sender.println(tag+" "+text);
						sender.flush();
						idInput.setText("");
					}
		        }
			}
		});
    }
}


public class Client  {
	private Socket socket=null;
	private BufferedReader reader=null;
	private PrintWriter sender=null;
	private String my_name=null;
	public JTextArea textArea;
	public static boolean Run=true;
	Client(){
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
			
			socket= new Socket(local_address,3005);
			System.out.println("connect sucess");
			reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			sender=new PrintWriter(socket.getOutputStream());
			
			
			String name = JOptionPane.showInputDialog(null, "Input your name");

			
			my_name=name;
				
			sender.println(my_name);
			sender.flush();

			
			JFrame jfrm = new JFrame("BTTL10_20127045_20127306");
			jfrm.getContentPane().setBackground(new Color(250, 187, 22));
			jfrm.setResizable(false);
			jfrm.getContentPane().setEnabled(true);
			jfrm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			jfrm.setBounds(100, 100, 720, 480);

			jfrm.setVisible(true);
			jfrm.getContentPane().setLayout(null);

			
			JLabel idLabel = new JLabel("Input your message (Type 'Quit' to exit) or (Enter to send message)");
			idLabel.setForeground(new Color(0, 0, 128));
			idLabel.setBounds(34, 355, 639, 21);
			idLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			idLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
			jfrm.getContentPane().add(idLabel);
			
			JLabel lblNewLabel = new JLabel("CHAT UI");
			lblNewLabel.setForeground(new Color(0, 0, 128));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblNewLabel.setBounds(237, 20, 218, 32);
			jfrm.getContentPane().add(lblNewLabel);
			
			JLabel lblNewLabel_1 = new JLabel("User: "+my_name);
			lblNewLabel_1.setForeground(new Color(255, 255, 255));
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNewLabel_1.setBounds(509, 20, 154, 27);
			jfrm.getContentPane().add(lblNewLabel_1);
			
			JTextArea textArea = new JTextArea("");
			textArea.setBounds(34, 62, 639, 276);
			textArea.setFont(new Font("Tahoma", Font.PLAIN, 13));
			textArea.setEditable(false);
			jfrm.getContentPane().add(textArea);
			
			new Thread(new Thread_Read(sender,jfrm,my_name,textArea)).start();
			
			
			
			

			while (true) {

				if (!Run) return;
				
				String message=null;
				while(reader.ready()) {

					message=reader.readLine();
					

					textArea.append(message+"\n");
					System.out.println(message);
					if (message!=null) {
						System.out.println(message);
					}
				}
			
				


				

				
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		finally {
			try {
				
				socket.close();
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sender.close();
		}
	}
	private Container getContentPane() {
		// TODO Auto-generated method stub
		return null;
	}
	public static void main(String[] args){
		// TODO Auto-generated method stub
		
		new Client();
		
	}

}

