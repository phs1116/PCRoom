package DAO;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcStatement;

public class H2DB_init {

	private JdbcStatement stat;
	private ResultSet rSet;

	public static void main(String[] args){
		new H2DB_init().init();
		new DAO_User().UsingService("asdf", "14");
	}
	public void init() {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		//CREATE TABLE IF NOT EXISTS 'user'(num int(11) not null primary key auto_increment, id vchar[11], seatNum int, state char[2]) 

		try {
			JdbcConnection connection = (JdbcConnection) DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "root", "1234");
			stat = (JdbcStatement) connection.createStatement();
			// memeber란 db가 없으면 생성
			
			String member_sql = "CREATE TABLE IF NOT EXISTS `member`(" + "num int(11) auto_increment, "
					+ "id VARCHAR(20) primary key, " + "password VARCHAR(200) NOT NULL, " + "age INT ,"
					+ "phone VARCHAR(20)," + "mileage INT);";
			
			// user 디비 없으면 생성
			String user_sql = "CREATE TABLE IF NOT EXISTS `user_state` (num int(11) not null primary key auto_increment,"
					+" id varchar(20),start DATETIME,end DATETIME ,seatNum int(2), state char(10) default '사용중',"
					+"credit int(11),FOREIGN KEY(id) REFERENCES member(id))";

			stat.execute(member_sql);
			stat.execute(user_sql);
			
			
			String sql2 = "show tables";

		//	String sql1 = "INSERT INTO member(id,password,phone,age) VALUES(\'test1\',\'1234\',\'010-1234-5678\',27);";
		//	stat.execute(sql1);
			ResultSet rSet = stat.executeQuery(sql2);

			
			while (rSet.next()) {
				System.out.println(rSet.getObject(1));
			}
			rSet.close();
			stat.close();
			
		} catch (SQLException se) {
			se.printStackTrace();
		}

	}
}
