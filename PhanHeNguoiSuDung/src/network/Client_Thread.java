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
		String name=reader.readLine();
		if (name!=null) {
			my_name=name;
			System.out.println("Start chat for "+my_name);
		}
		else {
			System.out.println("Cannot receive name of Client");
		}
		
		// end
		
		
		try {
        for (Map.Entry me : Server.Name2Socket.entrySet()) {
            System.out.println("Key: "+me.getKey());
            
            PrintWriter sender_User=null;
			
			sender_User=new PrintWriter(((Socket) me.getValue()).getOutputStream());
			
			sender_User.println(my_name+" has joined");
			sender_User.flush();
         }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Server.Name2Socket.put(my_name, socket);
//		
		
//		sender.println(my_name);
//		sender.flush();
	}
    public void run()
    {
    	try {
	    	while (true) {
				
	    		String raw_text=null,message=null,tag=null;
				raw_text = reader.readLine();
				if (raw_text!=null) {
					tag=raw_text.substring(0,1);
			
					if (tag.equals("M")) { // Me message
						System.out.println("raw text "+raw_text);
						message=raw_text.substring(2,raw_text.length());
					}
					else if (tag.equals("Q")) { // Qi Quit
						message=my_name+" has left";
					}
				}

				if (message!=null) {
					
					for (Map.Entry me : Server.Name2Socket.entrySet()) {
						if (!(my_name.equals(me.getKey()))){
							System.out.println("send from "+my_name+" to "+me.getKey());
							
				            PrintWriter sender_User=null;
							sender_User=new PrintWriter(((Socket) me.getValue()).getOutputStream());
							
							
							if (!tag.equals("Q")) sender_User.println(my_name+": "+message);
							else sender_User.println(message);
							
							sender_User.flush();
						}
			         }
				}
				if (tag.equals("Q")) {
					Server.Name2Socket.remove(my_name);
					currentThread().interrupt();
					return;
				}


	    	}
		} 
    	catch (IOException e) {
		// TODO Auto-generated catch blocka
		e.printStackTrace();
		}
    }
}