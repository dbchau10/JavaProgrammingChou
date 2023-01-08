package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import utils.GroupChatDB;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;

import components.FriendCom;
import components.GroupCom;
import utils.GroupChat;

public class Thread_GroupTab implements Runnable {
	private User you;
	private Connection conn;
	JPanel listGroup;
	Thread_GroupTab(Connection cnt, User you,JPanel listGroup){	
		this.you = you;
		this.conn = cnt;
		this.listGroup = listGroup;
		
	}
	
	private Vector<String>oldGroup = new Vector<>();
	public void run()
    {
		while (true) {
			
			
			GroupChatDB gr = new GroupChatDB(conn,you);
			Vector<GroupChat> groups = gr.getGroupJoin();
			Vector<String> newGroup = new Vector<>();
			    for(GroupChat group : groups) { 
	
			          newGroup.add(group.getID());
			    }
			   
			
			
			if (!oldGroup.equals(newGroup))
			{
				System.out.print("hello");
				listGroup.removeAll();
				listGroup.revalidate();
				listGroup.repaint();
				oldGroup=newGroup;
				for(int i=0; i<groups.size(); i++) {
					System.out.print(groups.get(i).getGroupname());
					
					GroupCom eachgroup = new GroupCom(conn,you, groups.get(i));
					listGroup.add(eachgroup.initialize(groups.get(i).getGroupname()));
					JSeparator separator = new JSeparator();
					separator.setBounds(10, 33, 353, 2);
					
					listGroup.add(separator);
				}

			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}}
		
    
}
