package group;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;

public class UserBean {
	private int userid;
	private String username;
	private String userpwd;
	private String useremail;
	private String fname;
	private String lname;
	private String yearofbirth;
	private String fulladdress;
	private String creditcard;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getYearofbirth() {
		return yearofbirth;
	}
	public void setYearofbirth(String yearofbrirth) {
		this.yearofbirth = yearofbrirth;
	}
	public String getFulladdress() {
		return fulladdress;
	}
	public void setFulladdress(String fulladdress) {
		this.fulladdress = fulladdress;
	}
	public String getCreditcard() {
		return creditcard;
	}
	public void setCreditcard(String creditcard) {
		this.creditcard = creditcard;
	}
	public void Initialize(String Username) {
		Connection conn = null;
		String url = "jdbc:postgresql://localhost:5432/JDBC";
		String driver = "org.postgresql.Driver";
		String dbname = "postgres";
		String password = "1234";
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url,dbname, password);
			String sqlQuery = "SELECT username, userpwd, useremail,fname, lname, yearofbirth, fulladdress, creditcard FROM user_info; where username =  '"+Username+"'";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sqlQuery);
            username = rs.getString(1);
            userpwd = rs.getString(2);
            useremail = rs.getString(3);
            yearofbirth = rs.getString(4);
            fname = rs.getString(5);
            lname = rs.getString(6);
            fulladdress = rs.getString(7);
            creditcard = rs.getString(8);            
			st.close();
			rs.close();
		}catch  (Exception e) {
			e.printStackTrace();
		}
	}
	private Hashtable<String, String> errors= new Hashtable<String, String>();
	public boolean validte() {
		boolean okAll = true;
		if (username.length()>10 || username.length()<6) {
			errors.put("name", "The length of username must be between 6 and 10.");
			okAll = false;
		}
		if(!username.matches("[a-zA-Z0-9_-]+")) {
			errors.put("name", "The content of username must be numbers, alphabets or mixing above.");
			okAll = false;
		}
		if (userpwd.length() > 10 ||userpwd.length() < 6) {
		    errors.put("password","The length of password must be between 6 and 10");
		    okAll = false;
		}
		if (!userpwd.matches("[a-zA-Z0-9_-]+")) {
			errors.put("password","The content of password must be numbers, alphabets or mixing above.");
			okAll = false;
			}

		if(!useremail.matches("[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)+")){
		    errors.put("email", "Invalide email address, all the alphabets must be lower case.");
		    okAll = false;
		}
		return okAll;
	}
	public void setErrorMsg(String err,String errMsg) {
		if (err != null && errMsg !=null) {
		errors.put(err, errMsg);
		}
		}
		public String getErrorMsg(String err) {
		Object message = (String)errors.get(err);
		return (String) ((message == null) ? "" : message);
		}
}
