package com.fexmechine.control;

import java.sql.Connection;
import java.sql.ResultSet;

import com.fexmechine.dao.FexDao;
import com.fexmechine.model.FexType;
import com.fexmechine.util.DbUtil;

public class CheckWB {
	
	private DbUtil dbUtil = new DbUtil();
	private FexDao fexdao;
	
	public int checkWhite(FexType fextype){
			fexdao = new FexDao();
			Connection con = null;
			FexType newfextype = fextype;
			// �˴�Ӧ����һ����������жϣ�����ֻ������2̨�����
			try {
				con = dbUtil.getCon();
				ResultSet rs = fexdao.Whitelist(con,newfextype);
				// �ж�Result
				System.out.println("fuck");
				while(rs.next()){// ����еĻ�
					System.out.println("123412321412");
					return 1;
				}
				return 0;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					dbUtil.closeCon(con);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return 0;
	}
	
	public int checkBlack(FexType fextype){
		fexdao = new FexDao();
		Connection con = null;
		FexType newfextype = fextype;
		// �˴�Ӧ����һ����������жϣ�����ֻ������2̨�����
		try {
			con = dbUtil.getCon();
			ResultSet rs = fexdao.Blacklist(con,newfextype);
			// �ж�Result
			if(rs.next()){// ����еĻ�
				return 1;
			}
			return 0;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

}
}
