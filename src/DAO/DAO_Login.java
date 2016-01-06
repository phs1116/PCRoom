package DAO;

import java.sql.*;

import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcPreparedStatement;


import assets.DBConnectionMgr;

public class DAO_Login {
	public static void main(String[] args) {
		DAO_Login loginService = new DAO_Login();
		boolean flag = loginService.loginService("test","1234");
		System.out.println(flag);
	}

	public boolean loginService(String id, String password) {
		boolean flag = false;

		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		JdbcConnection conn = null;
		JdbcPreparedStatement pstat = null;
		ResultSet rSet = null;
		String sql = null;
		String getPass = null;

		try {
			conn = (JdbcConnection) pool.getConnection();
			sql = "select password from member where id=?";
			pstat = (JdbcPreparedStatement) conn.prepareStatement(sql);
			pstat.setString(1, id);

			rSet = pstat.executeQuery();

			if (rSet.next()) {
				getPass = rSet.getString("password");
				if (getPass.equals(password)) {
					System.out.println("받아온 비밀번호 : " + getPass);
					flag = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstat, rSet);
		}
		return flag;
	}

}
