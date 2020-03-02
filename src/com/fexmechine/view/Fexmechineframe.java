package com.fexmechine.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.fexmechine.control.Control;
import com.fexmechine.model.FexType;

import javax.swing.JToolBar;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class Fexmechineframe extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JButton button_13;//发送传真按钮
	private Control control;
	private Fexmechineframe2 frame2; 
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 * @param string 
	 */
	public Fexmechineframe(String string) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(string);
		setBounds(100, 100, 756, 448);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("1");
		button.setBounds(42, 157, 48, 27);
		contentPane.add(button);
		
		JButton button_1 = new JButton("2");
		button_1.setBounds(100, 157, 51, 27);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("3");
		button_2.setBounds(161, 157, 48, 27);
		contentPane.add(button_2);
		
		JButton button_3 = new JButton("4");
		button_3.setBounds(42, 194, 48, 27);
		contentPane.add(button_3);
		
		JButton button_4 = new JButton("5");
		button_4.setBounds(100, 194, 51, 27);
		contentPane.add(button_4);
		
		JButton button_5 = new JButton("6");
		button_5.setBounds(161, 194, 48, 27);
		contentPane.add(button_5);
		
		JButton button_6 = new JButton("7");
		button_6.setBounds(42, 237, 48, 27);
		contentPane.add(button_6);
		
		JButton button_7 = new JButton("8");
		button_7.setBounds(100, 237, 51, 27);
		contentPane.add(button_7);
		
		JButton button_8 = new JButton("9");
		button_8.setBounds(161, 237, 48, 27);
		contentPane.add(button_8);
		
		JButton button_9 = new JButton("*");
		button_9.setBounds(42, 280, 48, 27);
		contentPane.add(button_9);
		
		JButton button_10 = new JButton("0");
		button_10.setBounds(100, 280, 51, 27);
		contentPane.add(button_10);
		
		JButton button_11 = new JButton("#");
		button_11.setBounds(161, 280, 48, 27);
		contentPane.add(button_11);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(249, 196, 174, 68);
		contentPane.add(textArea);
		
		JButton button_12 = new JButton("\u9ED1\u540D\u5355");
		button_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BlackRecieveList blacklist = new BlackRecieveList(1);
				blacklist.setVisible(true);
			}
		});
		button_12.setBounds(603, 111, 93, 36);
		contentPane.add(button_12);
		
		JButton btnNewButton = new JButton("\u767D\u540D\u5355");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WhiteRecieveList whitelist = new WhiteRecieveList(1);
				whitelist.setVisible(true);
			}
		});
		btnNewButton.setBounds(603, 171, 93, 36);
		contentPane.add(btnNewButton);
		
		// 发送传真
		JButton button_13 = new JButton("\u4F20\u771F/\u5F00\u59CB");
		button_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sendfrom = textField.getText();// 获取发送方传真号
				 String sendto = textField_1.getText();// 获取接收方传真号
				 
				 FexType fextype = new FexType(1,sendfrom,sendto);
				 try {
					control = new Control();
					//此处进行发送			
//					int flag = control.Judgement(fextype);
//					if(flag == 2){
//						System.out.println("该传真号码为黑名单号码，拒绝传真");
//					}else if(flag == 1){
//						System.out.println("该传真号码为白名单号码，接收传真");
//					}else{
//						System.out.println("该传真号码为陌生号码，进入问答阶段");
//					}
					// 出现第二个frame2传真机
					if(frame2 != null){
						frame2.setVisible(false);
						frame2.dispose();
					}
					frame2 = new Fexmechineframe2(fextype); // 此时传真机2出现
					frame2.setVisible(true);
					
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_13.setBounds(525, 270, 110, 99);
		contentPane.add(button_13);
		
		JButton button_14 = new JButton("\u505C\u6B62");
		button_14.setBounds(453, 270, 62, 62);
		contentPane.add(button_14);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(249, 274, 174, 36);
		contentPane.add(textArea_1);
		
		JLabel lblNewLabel = new JLabel("\u6765\u7535\u663E\u793A");
		lblNewLabel.setBounds(249, 127, 79, 19);
		contentPane.add(lblNewLabel);
		
		JLabel label = new JLabel("\u53F7\u7801\u663E\u793A");
		label.setBounds(249, 152, 174, 36);
		contentPane.add(label);
		
		JButton btnNewButton_1 = new JButton("\u590D\u5370");
		btnNewButton_1.setBounds(645, 270, 68, 62);
		contentPane.add(btnNewButton_1);
		
		JLabel label_1 = new JLabel("\u672C\u673A\u53F7\u7801\uFF1A");
		label_1.setBounds(42, 32, 77, 19);
		contentPane.add(label_1);
		
		textField = new JTextField();
		textField.setBounds(42, 61, 137, 23);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel label_2 = new JLabel("\u63A5\u6536\u65B9\u53F7\u7801\uFF1A");
		label_2.setBounds(42, 94, 97, 19);
		contentPane.add(label_2);
		
		textField_1 = new JTextField();
		textField_1.setBounds(42, 119, 137, 23);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		
	}

}
