package com.fexmechine.control;

import java.sql.Connection;
import java.sql.ResultSet;

import com.fexmechine.dao.FexDao;
import com.fexmechine.model.FexType;
import com.fexmechine.model.UserType;
import com.fexmechine.util.DbUtil;

public class Control {
	private FexDao fexdao;
	private DbUtil dbUtil = new DbUtil();
	private CheckWB checkwb = new CheckWB();
	
	public int Judgement(FexType fextype){
		if(checkwb.checkBlack(fextype) == 1){
			return 2;	
		}		
		else if(checkwb.checkWhite(fextype) == 1){
			System.out.println("���ǰ�����");
			return 1;
		}else{
			return 0;
			// �����ʴ�ϵͳ
		}

	}
	
	public void addW(FexType fextype)throws Exception{
		fexdao = new FexDao();
		Connection connect = null;
		try{
			connect = dbUtil.getCon();
			int flag = fexdao.addWhite(connect, fextype);
			if(flag == 1){
				System.out.println("��ӳɹ�");
			}else{
				System.out.println("���ʧ��");
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("���ʧ��");
		}finally{
			dbUtil.closeCon(connect);
		}
	}
	
	public void addB(FexType fextype)throws Exception{
		fexdao = new FexDao();
		Connection connect = null;
		try{
			connect = dbUtil.getCon();
			int flag = fexdao.addBlack(connect, fextype);
			if(flag == 1){
				System.out.println("��ӳɹ�");
			}else{
				System.out.println("���ʧ��");
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("���ʧ��");
		}finally{
			dbUtil.closeCon(connect);
		}
	}

	public int updatePassword(UserType usertype){
		fexdao = new FexDao();
		Connection connect = null;
		try {
			connect = dbUtil.getCon();
			ResultSet rs = fexdao.findPassword(connect, usertype);
			if(rs.next()){
				fexdao.updatePassword(connect,usertype);
				return 1;
			}else{
				return 0;
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public String getPassword(){
		fexdao = new FexDao();
		Connection connect = null;
		try {
			connect = dbUtil.getCon();
			ResultSet rs = fexdao.findPassword(connect, new UserType());
			rs.next();
			return rs.getString("password");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
