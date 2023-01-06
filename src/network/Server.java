package network;

import java.net.*;
import javax.net.ssl.*;
import java.io.*;
import java.util.*;  

public class Server {
	public static int Cnt = 0;
	public static Vector<Socket> user_online= new Vector<Socket>();
	public static HashMap<String, Socket> Name2Socket=new HashMap<String, Socket>();
	private static int port=3000;
	public static void main(String[] args) throws IOException {
	
		try (// TODO Auto-generated method stub
				ServerSocket server = new ServerSocket(port)) {
//			System.out.println("address"+server.get());
			System.out.println("Server is listening on port "+port);

			while(true) {
				Socket socket= server.accept();
				new Thread(new Client_Thread(socket)).start();
			}
			
			
		}
		catch (Exception ex) {
			System.out.println("error "+ex);
		}
		finally {
			
		}
	}

}

