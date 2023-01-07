package views;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Toolkit;

public class ChangePassword {

	private JFrame frmChatter;
	private JTextField tfUsername;
	private JPasswordField tfPassword;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangePassword window = new ChangePassword();
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
	public ChangePassword() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChatter = new JFrame();
		frmChatter.setTitle("Chatter!");
		frmChatter.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\DELL\\CNPM\\JavaProgramming\\background.jpg"));
		frmChatter.setBounds(500, 200, 1000, 500);
		frmChatter.setResizable(false);
		frmChatter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatter.getContentPane().setLayout(null);
		
		frmChatter.getContentPane().setBackground(new Color(206,157,217));
		
		
		
		ImageIcon background=new ImageIcon("background.jpg");
	    Image img=background.getImage();
	    Image temp=img.getScaledInstance(500,500,Image.SCALE_SMOOTH);
	    background=new ImageIcon(temp);
	    JLabel back=new JLabel(background);
	    back.setLayout(null);
	    back.setBounds(500,0,500,500);
	    
	    frmChatter.getContentPane().add(back);
	    
	    JLabel lblNewLabel = new JLabel("Email");
	    lblNewLabel.setFont(new Font("Arial", Font.BOLD, 13));
	    lblNewLabel.setBounds(105, 154, 45, 13);
	    frmChatter.getContentPane().add(lblNewLabel);
	    
	    textField = new JTextField();
	    textField.setBounds(160, 151, 96, 19);
	    frmChatter.getContentPane().add(textField);
	    textField.setColumns(10);
		
		
	}
}
