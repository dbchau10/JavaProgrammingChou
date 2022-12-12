package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class OnlineTab {
	JPanel OnlinePanel;
	OnlineTab(){
		OnlinePanel = new JPanel();
	}
	
	JPanel createPanel() {
		OnlinePanel.setFocusable(true);
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
		OnlinePanel.add(listOnline);
		listOnline.setLayout(null);
		
		JLabel lblUsername1 = new JLabel("alsophanie");
		lblUsername1.setFont(new Font("Arial", Font.PLAIN, 13));
		lblUsername1.setBounds(10, 10, 95, 13);
		listOnline.add(lblUsername1);
		
		JButton btnMess1 = new JButton("Nhắn");
		btnMess1.setFont(new Font("Arial", Font.PLAIN, 10));
		btnMess1.setBounds(287, 6, 71, 17);
		listOnline.add(btnMess1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 33, 353, 2);
		listOnline.add(separator);
		
		JLabel lblUsername2 = new JLabel("alixekka");
		lblUsername2.setFont(new Font("Arial", Font.PLAIN, 13));
		lblUsername2.setBounds(10, 46, 95, 13);
		listOnline.add(lblUsername2);
		
		JButton btnMess2 = new JButton("Nhắn");
		btnMess2.setFont(new Font("Arial", Font.PLAIN, 10));
		btnMess2.setBounds(287, 45, 71, 17);
		listOnline.add(btnMess2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(392, 251, 2, -239);
		listOnline.add(scrollPane);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(377, 8, 17, 233);
		listOnline.add(scrollBar);
		
		JButton btnNewButton = new JButton("Hủy");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(241, 84, 7));
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 10));
		btnNewButton.setBounds(206, 6, 71, 17);
		listOnline.add(btnNewButton);
		
		JButton btnHy = new JButton("Hủy");
		btnHy.setForeground(Color.WHITE);
		btnHy.setFont(new Font("Arial", Font.PLAIN, 10));
		btnHy.setBackground(new Color(241, 84, 7));
		btnHy.setBounds(206, 45, 71, 17);
		listOnline.add(btnHy);
		
		return OnlinePanel;
	}
}
