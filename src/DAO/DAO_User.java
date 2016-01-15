package DAO;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Vector;

import javax.sound.midi.Patch;
import javax.swing.text.DefaultEditorKit.PasteAction;

import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcPreparedStatement;

import com.mysql.jdbc.log.Log;

import Component.JPanel.SeatPanel;
import assets.DBConnectionMgr;

public class DAO_User {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DBConnectionMgr pool = DBConnectionMgr.getInstance();
	JdbcConnection conn = null;
	JdbcPreparedStatement pstat = null;
	ResultSet rSet = null;

	public static void main(String[] args) {
		DAO_Login loginService = new DAO_Login();
		boolean flag = loginService.loginService("test", "1234");
		System.out.println(flag);
	}

	public boolean UsingService(String id, String seatNum) {
		boolean flag = true;
		String sql = null;
		String getUsingID = null;
		int getUsingSeat = 0;

		try {
			conn = (JdbcConnection) pool.getConnection();
			sql = "select id,seatNum from user_state where state=?";
			pstat = (JdbcPreparedStatement) conn.prepareStatement(sql);
			pstat.setString(1, "사용중");

			rSet = pstat.executeQuery();

			while (rSet.next()) {
				getUsingID = rSet.getString("id");
				getUsingSeat = rSet.getInt("seatNum");
				if (getUsingID.equals(id)) {
					System.out.println("사용중인 아이디 :" + getUsingID);
					flag = false;
				} else if (getUsingSeat == Integer.parseInt(seatNum)) {
					System.out.println("사용중인 좌석 :" + getUsingID);
					flag = false;
				}
			}
			pstat.close();
			if (flag == true) {
				String insert = "INSERT INTO user_state (id, seatNum, start) VALUES (?,?,?)";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				pstat = (JdbcPreparedStatement) conn.prepareStatement(insert);
				pstat.setString(1, id);
				pstat.setInt(2, Integer.parseInt(seatNum));
				pstat.setString(3, sdf.format(new Date()));
				pstat.execute();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstat, rSet);
		}
		return flag;
	}

	public boolean UserStop(int seatNum) {
		boolean flag = false;
		String sql = null;
		String getStart = null;
		int getUsingSeat = 0;

		try {
			conn = (JdbcConnection) pool.getConnection();
			sql = "select start from user_state where state=? and seatNum=?";
			pstat = (JdbcPreparedStatement) conn.prepareStatement(sql);
			pstat.setString(1, "사용중");
			pstat.setInt(2, seatNum);
			rSet = pstat.executeQuery();
			if (rSet.next()) {
				getStart = rSet.getString("start");
				System.out.println(getStart);
			}
			// "UPDATE user_state (end, state, credit) SET (?,?,?)";
			rSet = pstat.executeQuery();
			String update = "UPDATE user_state SET end = ? where seatNum=? and state=?";
			String update2 = "UPDATE user_state SET state = ? where seatNum=? and state=?";
			String update3 = "UPDATE user_state SET credit = ? where seatNum=? and state=?";

	
			Date date = sdf.parse(getStart);
			Calendar start = Calendar.getInstance();
			start.setTime(date);
			Calendar end = Calendar.getInstance();

			long intervalTime = (end.getTimeInMillis() - start.getTimeInMillis()) / 1000;

			Date date2 = end.getTime();

			pstat = (JdbcPreparedStatement) conn.prepareStatement(update);
			pstat.setString(1, sdf.format(date2));
			pstat.setInt(2, seatNum);
			pstat.setString(3, "사용중");
			pstat.execute();
			pstat = (JdbcPreparedStatement) conn.prepareStatement(update2);
			pstat.setString(1, "정지");
			pstat.setInt(2, seatNum);
			pstat.setString(3, "사용중");
			pstat.execute();
			pstat = (JdbcPreparedStatement) conn.prepareStatement(update3);
			pstat.setInt(1, (int) intervalTime * 100);
			pstat.setInt(2, seatNum);
			pstat.setString(3, "사용중");
			pstat.execute();

			flag = true;

		} catch (

		Exception e)

		{
			e.printStackTrace();
		} finally

		{
			pool.freeConnection(conn, pstat, rSet);
		}
		return flag;
	}

	public Vector<String> UserList(int seatNum) {
		Vector<String> userinfo = null;
		String sql = null;
		String getStart = null;
		try {
			conn = (JdbcConnection) pool.getConnection();
			sql = "select * from user_state where state=? AND SEATNUM=??";
			pstat = (JdbcPreparedStatement) conn.prepareStatement(sql);
			pstat.setString(1, "사용중");
			pstat.setInt(2, seatNum);
			rSet = pstat.executeQuery();
			if (rSet.next()) {
				getStart = rSet.getString("start");
				Date date = sdf.parse(getStart);
				Calendar start = Calendar.getInstance();
				start.setTime(date);
				Calendar end = Calendar.getInstance();

				long intervalTime = (end.getTimeInMillis() - start.getTimeInMillis()) / 1000;
				int credit = (int)intervalTime*10;
				System.out.println(credit);
				
				userinfo = new Vector<>();
				userinfo.addElement(rSet.getString("SEATNUM"));
				userinfo.addElement(rSet.getString("ID"));
				userinfo.addElement(credit+"");
				System.out.println(userinfo);
			}

		} catch (

		Exception e)

		{
			e.printStackTrace();
		} finally

		{
			pool.freeConnection(conn, pstat, rSet);
		}
		return userinfo;
	}
}