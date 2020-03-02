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

public class WhiteRecieveList extends JFrame {
	private JTable WhiteNumberTable;

	private FexDao fexdao;
	private DbUtil dbUtil = new DbUtil();
	
	private int id;
	private JTextField s_fexnumber;
	private JTextField selectnumbertxt;
	
	private String fexnumber_id = "";//������ŵ�idֵ������ɾ��
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public WhiteRecieveList(int id) {
		super();
		this.id = id;
		setTitle("\u767D\u540D\u5355\u5217\u8868");
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
		
		WhiteNumberTable = new JTable();
		WhiteNumberTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				FexnumberMousePressed(e);
			}

		});

		WhiteNumberTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u7F16\u53F7", "\u767D\u540D\u5355\u53F7\u7801"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		WhiteNumberTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		WhiteNumberTable.getColumnModel().getColumn(1).setPreferredWidth(101);
		WhiteNumberTable.getColumnModel().getColumn(1).setMinWidth(90);
		scrollPane.setViewportView(WhiteNumberTable);
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

		//�����Զ���ѯ
		this.fillTable(new FexType());
	}
	
	private void fillTable(FexType fextype){
		fextype.setId(id);
		DefaultTableModel dtm = (DefaultTableModel) WhiteNumberTable.getModel();
		dtm.setRowCount(0); // ���ó�0��
		Connection con = null;
		fexdao = new FexDao();
		try{
			con = dbUtil.getCon();
			ResultSet rs = fexdao.Whitelist(con,fextype);
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("id"));
				v.add(rs.getString("white_name"));
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
	
	// ����Ų�ѯ
	private void FexnumberSearch(ActionEvent evt){
		String serchfexnumber = this.s_fexnumber.getText();
		FexType fextype = new FexType();
		fextype.setRecieveNumber(serchfexnumber);
		this.fillTable(fextype);
	}
	
	// ����е������
	private void FexnumberMousePressed(MouseEvent e) {
		int row = WhiteNumberTable.getSelectedRow();
		fexnumber_id = (String) WhiteNumberTable.getValueAt(row, 0);//��ȡ������ŵ�idֵ
		selectnumbertxt.setText((String) WhiteNumberTable.getValueAt(row, 1));
	}
	
	public void FexnumberDelete(ActionEvent evt) {
		if(StringUtils.isEmptyOrWhitespaceOnly(fexnumber_id)){
			return;
		}
		
		Connection con = null;
		try {
			con = dbUtil.getCon();
			int deleteNum = fexdao.deleteWhite(con, fexnumber_id,id);
			if(deleteNum == 1){	//ɾ��ʧ��
				selectnumbertxt.setText("");
				this.fillTable(new FexType());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}

