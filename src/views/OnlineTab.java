package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import components.FriendCom;

public class OnlineTab {
	private JPanel OnlinePanel;
	private User you;
	private Connection conn = null;
	OnlineTab(Connection cnt, User you){
		OnlinePanel = new JPanel();
		this.you = you;
		this.conn = cnt;
	}
	
	JPanel createPanel() {
		OnlinePanel.setBackground(new Color(240, 240, 240));
		JTextField tfSearch = new JTextField();
		tfSearch.setBounds(34, 26, 310, 19);
		tfSearch.setForeground(new Color(115, 115, 115));
		tfSearch.setFont(new Font("Arial", Font.ITALIC, 10));
		tfSearch.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (tfSearch.getText().trim().equals("Nhập tên"))
				{
					tfSearch.setText("");
					tfSearch.setForeground(new Color(0, 0, 0));
					tfSearch.setFont(new Font("Arial", Font.PLAIN, 10));
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (tfSearch.getText().trim().equals(""))
				{
					tfSearch.setText("Nhập tên");
					tfSearch.setForeground(new Color(115, 115, 115));
					tfSearch.setFont(new Font("Arial", Font.ITALIC, 10));
				}
			}
		});
		OnlinePanel.setLayout(null);
		tfSearch.setText("Nhập tên");
		OnlinePanel.add(tfSearch);
		tfSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Tìm kiếm");
		btnSearch.setBackground(new Color(235, 209, 105));
		btnSearch.setBounds(354, 25, 85, 21);
		btnSearch.setFont(new Font("Arial", Font.PLAIN, 10));
		OnlinePanel.add(btnSearch);
		
		
		JPanel listOnline = new JPanel();
		listOnline.setBounds(35, 81, 404, 257);
		listOnline.setLayout(new BoxLayout(listOnline,BoxLayout.Y_AXIS));
		//listOnline.setLayout(null);
		
		
		//js.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//JPanel friend = new JPanel();
		//JPanel friend [] = new JPanel[10];
		for (int i = 0 ; i<10; i++)
		{
			FriendCom fr = new FriendCom();
			listOnline.add(fr.initialize("username","Hủy"));
			JSeparator separator = new JSeparator();
			separator.setBounds(10, 33, 353, 2);
			listOnline.add(separator);
		}
		
		JScrollPane js = new JScrollPane(listOnline, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//js.setPreferredSize(new Dimension(404,257));
		js.setBounds(25, 81, 430, 257);
		//JScrollPane js = new JScrollPane(listOnline);
		OnlinePanel.add(js);
		
		//listOnline.add(js);
		//JSeparator separator = new JSeparator();
		//separator.setBounds(10, 33, 353, 2);
		//listOnline.add(separator);
		
		
		
		//JScrollPane scrollPane = new JScrollPane();
		//scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//scrollPane.setBounds(392, 251, 2, -239);
		//listOnline.add(scrollPane);
		
		//JScrollBar scrollBar = new JScrollBar();
		//scrollBar.setBounds(377, 8, 17, 233);
		//listOnline.add(scrollBar);
		
		
		
		
		
		return OnlinePanel;
	}
}
