package com.fexmechine.view;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.fexmechine.dao.FexDao;
import com.fexmechine.model.FexType;
import com.fexmechine.util.DbUtil;
import com.mysql.cj.util.StringUtils;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class BlackRecieveList extends JFrame {
	private JTable BlackNumberTable;

	private FexDao fexdao;
	private DbUtil dbUtil = new DbUtil();
	
	private int id;
	private JTextField s_fexnumber;
	private JTextField selectnumbertxt;
	
	private String fexnumber_id = "";//传真机号的id值，用于删除
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public BlackRecieveList(int id) {
		super();
		this.id = id;
		setTitle("\u9ED1\u540D\u5355\u5217\u8868");
		setBounds(100, 100, 329, 488);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(69, 57, 163, 313);
		
		JLabel label = new JLabel("\u4F20\u771F\u53F7\uFF1A");
		label.setBounds(30, 28, 48, 15);
		
		s_fexnumber = new JTextField();
		s_fexnumber.setBounds(82, 25, 141, 21);
		s_fexnumber.setColumns(10);
		
		JButton button = new JButton("\u67E5\u8BE2");
		button.setBounds(233, 24, 70, 23);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FexnumberSearch(e);
			}
		});
		
		selectnumbertxt = new JTextField();
		selectnumbertxt.setBounds(82, 391, 141, 21);
		selectnumbertxt.setColumns(10);
		
		JLabel label_1 = new JLabel("\u5DF2\u9009\u62E9");
		label_1.setBounds(30, 394, 36, 15);
		
		BlackNumberTable = new JTable();
		BlackNumberTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				FexnumberMousePressed(e);
			}

		});

		BlackNumberTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u7F16\u53F7", "\u9ED1\u540D\u5355\u53F7\u7801"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		BlackNumberTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		BlackNumberTable.getColumnModel().getColumn(1).setPreferredWidth(101);
		BlackNumberTable.getColumnModel().getColumn(1).setMinWidth(90);
		scrollPane.setViewportView(BlackNumberTable);
		getContentPane().setLayout(null);
		getContentPane().add(label);
		getContentPane().add(s_fexnumber);
		getContentPane().add(button);
		getContentPane().add(label_1);
		getContentPane().add(selectnumbertxt);
		getContentPane().add(scrollPane);
		
		JButton button_1 = new JButton("\u5220\u9664");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				FexnumberDelete(e);
			}
		});
		button_1.setBounds(233, 390, 70, 23);
		getContentPane().add(button_1);

		//进行自动查询
		this.fillTable(new FexType());
	}
	
	private void fillTable(FexType fextype){
		fextype.setId(id);
		DefaultTableModel dtm = (DefaultTableModel) BlackNumberTable.getModel();
		dtm.setRowCount(0); // 设置成0行
		Connection con = null;
		fexdao = new FexDao();
		try{
			con = dbUtil.getCon();
			ResultSet rs = fexdao.Blacklist(con,fextype);
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("id"));
				v.add(rs.getString("black_name"));
				dtm.addRow(v);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// 传真号查询
	private void FexnumberSearch(ActionEvent evt){
		String serchfexnumber = this.s_fexnumber.getText();
		FexType fextype = new FexType();
		fextype.setRecieveNumber(serchfexnumber);
		this.fillTable(fextype);
	}
	
	// 表格行点击处理
	private void FexnumberMousePressed(MouseEvent e) {
		int row = BlackNumberTable.getSelectedRow();
		fexnumber_id = (String) BlackNumberTable.getValueAt(row, 0);//获取传真机号的id值
		selectnumbertxt.setText((String) BlackNumberTable.getValueAt(row, 1));
	}
	
	public void FexnumberDelete(ActionEvent evt) {
		if(StringUtils.isEmptyOrWhitespaceOnly(fexnumber_id)){
			return;
		}
		
		Connection con = null;
		try {
			con = dbUtil.getCon();
			int deleteNum = fexdao.deleteBlack(con, fexnumber_id,id);
			if(deleteNum == 1){	//删除失败
				selectnumbertxt.setText("");
				this.fillTable(new FexType());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}

