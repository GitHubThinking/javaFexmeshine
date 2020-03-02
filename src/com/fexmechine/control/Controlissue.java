package com.fexmechine.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fexmechine.dao.FexDao;
import com.fexmechine.dao.IssueDao;
import com.fexmechine.model.IssueType;
import com.fexmechine.util.DbUtil;

public class Controlissue {
	private ResultSet rs;
	private IssueType iy;
	private FexDao fexdao;
	private IssueDao issuedao;
	private DbUtil dbUtil = new DbUtil();
	private int lastIndex = 0;
	
	
	public void createIssue(){
		issuedao = new IssueDao();
		Connection con = null;
		try {
			con = dbUtil.getCon();
			rs = issuedao.createQuestion(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public IssueType getNextIssue(){
		iy = new IssueType();
		
		try {	
			rs.last(); // ���α��ƶ������һ��
			int size = rs.getRow(); // ��ȡ���һ��
			int index = (int) Math.floor(Math.random()*size);
			// �����ظ�
			while(lastIndex == index){
				index = (int) Math.floor(Math.random()*size);
			}
			lastIndex = index;
			
			// ��ȡĳһ�е���Ŀ
			rs.first();
			int count = 0;
			while(count < index){
				rs.next();
				count++;
			}
					
			iy.setId(rs.getString("id"));
			iy.setQuestion(rs.getString("question"));
			iy.addAnswer(rs.getString("answer1"));
			iy.addAnswer(rs.getString("answer2"));
			iy.addAnswer(rs.getString("answer3"));
			iy.setTrue_ans(rs.getString("true_answer"));
			iy.randomsort();
			
			System.out.println(rs.getString("true_answer"));
			
			return iy;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public int selectAns(int index){
		System.out.println(iy.getAnswer().get(index));
		System.out.println(iy.getTrue_ans());
		if(iy.getAnswer().get(index).equals(iy.getTrue_ans())){
			// �������ȣ��򷵻�ture��������һ�����
			return 1;
		}else{
			return 0;
		}
	}
	
	public int checkVoiceAns(String str){
		if(iy.getTrue_ans().equals(str)){
			return 1;
		}else{
			return 0;
		}
	}
	
	public void addQue_Ans(IssueType itype){
		issuedao = new IssueDao();
		Connection con = null;
		try {
			con = dbUtil.getCon();
			int rs = issuedao.addQuestionAndAnswer(con, itype);
			if(rs == 1){
				System.out.println("������ӳɹ�");
			}else{
				System.out.println("�������ʧ��");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
