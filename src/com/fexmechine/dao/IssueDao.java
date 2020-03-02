package com.fexmechine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fexmechine.model.FexType;
import com.fexmechine.model.IssueType;
import com.mysql.cj.util.StringUtils;

public class IssueDao {
	
	public IssueDao() {
		super();
	}
	
	// 获取问题
	public ResultSet createQuestion(Connection con)throws Exception{
		StringBuffer sb = new StringBuffer("select * from question_table");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	// 添加问题和答案
	public int addQuestionAndAnswer(Connection con,IssueType itype){
			
		String sql = "insert into question_table values(null,?,?,?,?,?)";
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, itype.getQuestion());
			if(itype.getAnswer().size() > 0){
				pstmt.setString(2, (String) itype.getAnswer().get(0));
				pstmt.setString(3, (String) itype.getAnswer().get(1));
				pstmt.setString(4, (String) itype.getAnswer().get(2));
			}else{
				pstmt.setString(2,null);
				pstmt.setString(3,null);
				pstmt.setString(4,null);
			}
			pstmt.setString(5, itype.getTrue_ans());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
