package DAO;

import java.sql.ResultSet;

import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcPreparedStatement;


import assets.DBConnectionMgr;
import model.member;

public class DAO_Join {
	public boolean joinService(member member) {
		boolean flag = false;
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		JdbcConnection conn = null;
		JdbcPreparedStatement pstat = null;
		ResultSet rSet = null;
		String sql1 = null;
		String sql2 = null;
		String getPass = null;

		sql1 = "select id from member where id=?";
		sql2 = "insert into member(id,password,phone,age) values('" + member.getId() + "'" + ",'" + member.getPassword()
				+ "','" + member.getPhone() + "'," + member.getAge() + ");";
		try {
			conn = (JdbcConnection) pool.getConnection();

			pstat = (JdbcPreparedStatement) conn.prepareStatement(sql1);
			pstat.setString(1, member.getId());
			rSet = pstat.executeQuery();
			if (rSet.next()) {
				getPass = rSet.getString("id");
				if (getPass.equals(member.getId())) {
					System.out.println("일치하는 id :" + getPass);
					flag = false;
				}

			} else {
				pstat.close();
				rSet.close();

				pstat = (JdbcPreparedStatement) conn.prepareStatement(sql2);
				pstat.execute();
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstat, rSet);
		}

		return flag;
	}
}
