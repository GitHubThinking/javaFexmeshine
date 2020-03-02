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
			rs.last(); // 将游标移动到随后一个
			int size = rs.getRow(); // 获取最后一行
			int index = (int) Math.floor(Math.random()*size);
			// 避免重复
			while(lastIndex == index){
				index = (int) Math.floor(Math.random()*size);
			}
			lastIndex = index;
			
			// 获取某一行的题目
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
			// 如果答案相等，则返回ture，进行下一题答题
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
				System.out.println("问题添加成功");
			}else{
				System.out.println("问题添加失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
