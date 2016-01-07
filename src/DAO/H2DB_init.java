package DAO;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcStatement;

public class H2DB_init {

	private JdbcStatement stat;
	private ResultSet rSet;

	public void init() {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}

		try {
			JdbcConnection connection = (JdbcConnection) DriverManager.getConnection("jdbc:h2:mem:test", "root", "1234");
			stat = (JdbcStatement) connection.createStatement();
			// memeber란 db가 없으면 생성
			
			String sql = "CREATE TABLE IF NOT EXISTS `member`(" + "num int(11) auto_increment, "
					+ "id VARCHAR(20) primary key, " + "password VARCHAR(200) NOT NULL, " + "age INT ,"
					+ "phone VARCHAR(20)," + "mileage INT);";

			stat.execute(sql);
			
			
			String sql2 = "show tables";

			String sql1 = "INSERT INTO member(id,password,phone,age) VALUES(\'test\',\'1234\',\'010-1234-5678\',27);";
			stat.execute(sql1);
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
