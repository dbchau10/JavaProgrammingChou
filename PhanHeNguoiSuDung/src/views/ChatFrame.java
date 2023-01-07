package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChatFrame {

	private JFrame frmChatter;
	private JTextField txtNhn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatFrame window = new ChatFrame();
					window.frmChatter.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChatter = new JFrame();
		frmChatter.setTitle("Chatter!");
		frmChatter.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\DELL\\CNPM\\JavaProgramming\\background.jpg"));
		frmChatter.setBounds(500, 200, 500, 500);
		frmChatter.setResizable(false);
		frmChatter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frmChatter.getContentPane().setBackground(new Color(206,157,217));
		frmChatter.getContentPane().setLayout(null);
		
		frmChatter.setFocusable(true);
		txtNhn = new JTextField();
		txtNhn.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtNhn.getText().trim().equals("Nhập tin nhắn..."))
				{
					txtNhn.setText("");
					txtNhn.setFont(new Font("Arial", Font.PLAIN, 12));
					txtNhn.setForeground(new Color(0, 0, 0));
					
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (txtNhn.getText().trim().equals(""))
				{
					txtNhn.setText("Nhập tin nhắn...");
					txtNhn.setFont(new Font("Arial", Font.ITALIC, 12));
					txtNhn.setForeground(new Color(104, 104, 104));
				}
			}
		});
		txtNhn.setForeground(new Color(104, 104, 104));
		txtNhn.setText("Nhập tin nhắn...");
		txtNhn.setFont(new Font("Arial", Font.ITALIC, 12));
		txtNhn.setBounds(10, 432, 371, 21);
		frmChatter.getContentPane().add(txtNhn);
		txtNhn.setColumns(10);
		
		JButton sendBtn = new JButton("Nhắn");
		sendBtn.setBackground(new Color(235, 209, 105));
		sendBtn.setFont(new Font("Arial", Font.BOLD, 12));
		sendBtn.setBounds(391, 432, 85, 22);
		frmChatter.getContentPane().add(sendBtn);
	}
}
