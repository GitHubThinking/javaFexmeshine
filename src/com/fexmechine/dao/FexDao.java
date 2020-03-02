package com.fexmechine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fexmechine.model.FexType;
import com.fexmechine.model.UserType;
import com.mysql.cj.util.StringUtils;

public class FexDao {
	private int id;
	
	public FexDao() {
		super();
		// TODO Auto-generated constructor stub
	}

	// 添加到白名单
	public int addWhite(Connection con,FexType fextype)throws Exception{
		
		id = fextype.getId();
		System.out.println(id);
		if(id == 1){// 1号传真机传输后，对传真机1进行操作
			String sql = "insert into white_number1 values(null,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fextype.getRecieveNumber());
			return pstmt.executeUpdate();
		}else if(id == 2){// 2号传真机传输后，对传真机2进行操作
			String sql = "insert into white_number2 values(null,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fextype.getRecieveNumber());
			return pstmt.executeUpdate();
		}	
		
		return 0;	
	}
	
	// 添加到黑名单
	public int addBlack(Connection con,FexType fextype)throws Exception{
		id = fextype.getId();
		if(id == 1){// 1号传真机传输后，对传真机1进行操作
			String sql = "insert into black_number1 values(null,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fextype.getRecieveNumber());
			return pstmt.executeUpdate();
		}else if(id == 2){// 2号传真机传输后，对传真机2进行操作
			String sql = "insert into black_number2 values(null,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fextype.getRecieveNumber());
			return pstmt.executeUpdate();
		}	
		
		return 0;	
	}
	
	// 白名单查找
	public ResultSet Whitelist(Connection con,FexType fextype)throws Exception{
		id = fextype.getId();
		if(id == 1){ // 传真机1
			StringBuffer sb = new StringBuffer("select * from white_number1");
			if(!StringUtils.isEmptyOrWhitespaceOnly(fextype.getRecieveNumber())){
				sb.append(" where white_name like '"+fextype.getRecieveNumber()+"'");
			}
			PreparedStatement pstmt = con.prepareStatement(sb.toString());
			return pstmt.executeQuery();
		}else{ // 传真机2
			StringBuffer sb = new StringBuffer("select * from white_number2");
			if(!StringUtils.isEmptyOrWhitespaceOnly(fextype.getRecieveNumber())){
				sb.append(" where white_name like '"+fextype.getRecieveNumber()+"'");
			}
			PreparedStatement pstmt = con.prepareStatement(sb.toString());
			return pstmt.executeQuery();
		}		
	}
	
	// 黑名单查找
	public ResultSet Blacklist(Connection con,FexType fextype)throws Exception{
		id = fextype.getId();
		if(id == 1){ // 传真机1
			StringBuffer sb = new StringBuffer("select * from black_number1");
			if(!StringUtils.isEmptyOrWhitespaceOnly(fextype.getRecieveNumber())){
				sb.append(" where black_name like '"+fextype.getRecieveNumber()+"'");
			}
			PreparedStatement pstmt = con.prepareStatement(sb.toString());
			return pstmt.executeQuery();
		}else{ // 传真机2
			StringBuffer sb = new StringBuffer("select * from black_number2");
			if(!StringUtils.isEmptyOrWhitespaceOnly(fextype.getRecieveNumber())){
				sb.append(" where black_name like '"+fextype.getRecieveNumber()+"'");
			}
			PreparedStatement pstmt = con.prepareStatement(sb.toString());
			return pstmt.executeQuery();
		}		
	}
	
	// 白名单删除
	public int deleteWhite(Connection con,String ID,int type)throws Exception{
		id = type;
		if(id == 1){
			String sql = "delete from white_number1 where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,ID);
			return pstmt.executeUpdate();
		}else{
			String sql = "delete from white_number2 where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,ID);
			return pstmt.executeUpdate();
		}
		
	}
	
	// 黑名单删除
	public int deleteBlack(Connection con,String ID,int type)throws Exception{
		id = type;
		if(id == 1){
			String sql = "delete from black_number1 where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,ID);
			return pstmt.executeUpdate();
		}else{
			String sql = "delete from black_number2 where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,ID);
			return pstmt.executeUpdate();
		}
		
	}
	
	// 更改密码
	public int updatePassword(Connection con,UserType usertype){

		String sql = "update user set password=? where id = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,usertype.getPassword());
			pstmt.setInt(2, usertype.getId());
			return pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}
	
	// 查找密码
	public ResultSet findPassword(Connection con,UserType usertype)throws Exception{

		StringBuffer sb = new StringBuffer("select * from user");
		if(!StringUtils.isEmptyOrWhitespaceOnly(usertype.getOldpassword())){
			sb.append(" where password like '"+usertype.getOldpassword()+"'");
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		return pstmt.executeQuery();	
	}
}
