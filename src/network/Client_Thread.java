package network;


import java.net.*;
import javax.net.ssl.*;
import java.io.*;
import java.util.*;
import static java.lang.Thread.currentThread;

class Client_Thread implements Runnable {
	private String my_name;
	private Socket socket=null;
	BufferedReader reader;
	PrintWriter sender;
	public String[] parse_message(String message) {
		
		return message.split("`");
	}
	Client_Thread (Socket thisSocket) throws IOException{
		socket=thisSocket;
		try {
			reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			sender=new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		// authenication username and password from client 
		while (!reader.ready()) {
		}
		if (reader.ready()) {
			my_name=reader.readLine();
		
		}
		System.out.println("Start chat for "+my_name);
		
		// end
		
		
//		try {
//        for (Map.Entry me : Server.Name2Socket.entrySet()) {
//            System.out.println("Key: "+me.getKey());
//            
//            PrintWriter sender_User=null;
//			
//			sender_User=new PrintWriter(((Socket) me.getValue()).getOutputStream());
//			
//			sender_User.println(my_name+" has joined");
//			sender_User.flush();
//         }
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		Server.Name2Socket.put(my_name, socket);
//		
		
//		sender.println(my_name);
//		sender.flush();
	}
    public void run()
    {
    	try {
	    	while (true) {
	    		if (reader.ready()) {
	    			String mess=reader.readLine();
	    			String[] result_message=parse_message(mess);
					if (result_message[0].equals("MD")) { // Me message
						String message_rv="MD`"+my_name+":"+result_message[2];
						Socket scSend=Server.Name2Socket.get(result_message[1]);
						if (scSend!=null) {
							PrintWriter sender_User=new PrintWriter(scSend.getOutputStream());
							if (sender_User!=null) {
							sender_User.println(message_rv);
							sender_User.flush();
							System.out.println("send from "+my_name+" to "+result_message[1]+" message "+result_message[2]);
							}
						}
					}
					else if (result_message[0].equals("MG")) { // Qi Quit
						String message_rv="MG`"+my_name+":"+result_message[2];
						for (Map.Entry me : Server.Name2Socket.entrySet()) {
							if (!(my_name.equals(me.getKey()))){
								System.out.println("send from "+my_name+" to "+me.getKey());
									PrintWriter sender_User=new PrintWriter(((Socket) me.getValue()).getOutputStream());
									
									sender_User.println(message_rv);
									sender_User.flush();
								}
							}
				         }
					}
	    		}
    	}
    	catch (IOException e) {
		// TODO Auto-generated catch blocka
		e.printStackTrace();
		}
    }
}