package com.fexmechine.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.fexmechine.control.Control;
import com.fexmechine.model.UserType;

import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Passwordframe extends JFrame {
	
	private Control control = new Control();
	
	private JPanel contentPane;
	private JTextPane oldPasswordArea; 
	private JTextPane newPasswordArea;
	private JLabel statelabel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Passwordframe frame = new Passwordframe();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Passwordframe() {
		setBounds(100, 100, 302, 255);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton changebtn = new JButton("\u4FEE\u6539\u5BC6\u7801");
		changebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changePassword();
			}
		});
		changebtn.setBounds(99, 171, 93, 23);
		contentPane.add(changebtn);
		
		oldPasswordArea = new JTextPane();
		oldPasswordArea.setBounds(89, 28, 146, 33);
		contentPane.add(oldPasswordArea);
		
		JLabel label = new JLabel("\u539F\u5BC6\u7801");
		label.setBounds(38, 36, 54, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u65B0\u5BC6\u7801");
		label_1.setBounds(38, 87, 54, 15);
		contentPane.add(label_1);
		
	    newPasswordArea = new JTextPane();
		newPasswordArea.setBounds(89, 82, 146, 33);
		contentPane.add(newPasswordArea);
		
		statelabel = new JLabel("\u8BF7\u4FEE\u6539\u5BC6\u7801");
		statelabel.setHorizontalAlignment(SwingConstants.CENTER);
		statelabel.setBounds(61, 138, 160, 15);
		contentPane.add(statelabel);
	}

	protected void changePassword() {
		String oldpass = oldPasswordArea.getText();
		String newpass = newPasswordArea.getText();
		UserType usertype = new UserType();
		usertype.setId(1);
		usertype.setOldpassword(oldpass);
		usertype.setPassword(newpass);
		
		if(control.updatePassword(usertype) == 1){
			statelabel.setText("–ﬁ∏ƒ√‹¬Î≥…π¶");
		}else{
			statelabel.setText("–ﬁ∏ƒ√‹¬Î ß∞‹");
		}
		oldPasswordArea.setText("");
		newPasswordArea.setText("");
				
	}
}
