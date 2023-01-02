package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import views.FriendFunction;
import views.User;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendCom {

	private JPanel friend;

	private User you;
	private Connection conn = null;
	private User other;
	public FriendCom(Connection conn,User you, User other) {
		friend = new JPanel();
		friend.setVisible(true);
		this.conn = conn;
		this.you = you;
		this.other = other;
	}

	/**
	 * Initialize the contents of the frame.
	 */
		public JPanel initialize(String username, String button) {
		
		
		//friend.setBounds(0, 0, 404, 100);
		friend.setPreferredSize(new Dimension(404, 50));
		friend.setLayout(null);
		
		JLabel lblUsername = new JLabel(username);
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 13));
		lblUsername.setBounds(29, 10, 140, 43);
		friend.add(lblUsername);
		JButton btnMess = new JButton("Nhắn");
		btnMess.setFont(new Font("Arial", Font.PLAIN, 10));
		btnMess.setBounds(286, 20, 71, 17);
		friend.add(btnMess);
		
		JButton btn = new JButton(button);
		btn.setForeground(new Color(255, 255, 255));
		btn.setBackground(new Color(241, 84, 7));
		btn.setFont(new Font("Arial", Font.PLAIN, 10));
		btn.setBounds(195, 20, 71, 17);
		friend.add(btn);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btn.getText()=="Hủy")
				{
					FriendFunction del = new FriendFunction(conn, you);
					del.DeleteFriend(other.getUsername());

					/*try {
						String sql = new String("DELETE FROM friendlist WHERE user_id1=? and user_id2=?");
						conn.setAutoCommit(false);
						stm = conn.prepareStatement(sql);
						stm.setInt(1, you.getID());
						stm.setInt(2, other.getID());
						stm.executeUpdate();
						conn.commit();
						
						sql = new String("DELETE FROM friendlist WHERE user_id1=? and user_id2=?");
						conn.setAutoCommit(false);
						stm = conn.prepareStatement(sql);
						stm.setInt(2, you.getID());
						stm.setInt(1, other.getID());
						stm.executeUpdate();
						conn.commit();
						
						
						
					} catch(SQLException ex)
					{
						try {
							conn.rollback();
						}
						catch(SQLException e1) {
							e1.printStackTrace();
						}
					}
					finally {
						redispatchToParent(e);
					}*/
				}
			}
		});
		return friend;
		
	}
		private void redispatchToParent(ActionEvent e){
	        Component source = (Component) e.getSource();
	        source.getParent().dispatchEvent(e);
	    }
}
