package group;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconn {

private	Connection conn = null;
//private String sql = "";
private String url = "jdbc:postgresql://localhost:5432/JDBC";
private String driver = "org.postgresql.Driver";
private String dbusername = "postgres";
private String dbpassword = "1234";
public DBconn() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	Class.forName(driver).newInstance();
	conn = DriverManager.getConnection(url,dbusername, dbpassword);
	
}
public Connection getConn() {
	return conn;
}
public void setConn(Connection conn) {
	this.conn = conn;
}
}
