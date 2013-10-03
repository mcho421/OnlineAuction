package group;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import jdbc.DBConnectionFactory;

import jdbc.DBConnectionFactory;

public class UserBean {
	private int userid;
	private String username = "";
	private String userpwd = "";
	private String useremail= "";
	private String fname = "";
	private String lname = "";
	private String yearofbirth = "";
	private String fulladdress = "";
	private String creditcard = "";
	private int status = 0;
	private boolean confirmed = false;
	private String namemd5 = "";
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
	public String getNameMd5() {
		return namemd5;
	}
	public void setNameMd5(String namemd5) {
		this.namemd5 = namemd5;
	}
	public void Initialize(String Username) {

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			try {
				conn = DBConnectionFactory.getConnection();
				if(conn!=null) System.out.println("connected");
				String sqlQuery = "SELECT username, password, email, status, "
						+ "confirmed, namemd5, yearofbirth, fname, "
						+ "lname, fulladdress, creditcard "
						+ "FROM Users where username = ?";
				st = conn.prepareStatement(sqlQuery);
				st.setString(1, Username);
				rs = st.executeQuery();
				if(rs.next()){
		            username = rs.getString(1);
		            userpwd = rs.getString(2);
		            useremail = rs.getString(3);
		            status = rs.getInt(4);
		            confirmed = rs.getBoolean(5);
		            namemd5 = rs.getString(6);
		            yearofbirth = rs.getString(7);
		            if(yearofbirth==null) yearofbirth = "";
		            fname = rs.getString(8);
		            if(fname==null) fname = "";
		            lname = rs.getString(9);
		            if(lname==null) lname = "";
		            fulladdress = rs.getString(10);
		            if(fulladdress==null) fulladdress = "";
		            creditcard = rs.getString(11); 
		            if(creditcard==null) creditcard = "";
				}
			}catch  (Exception e) {
				e.printStackTrace();
			} finally {
				st.close();
				rs.close();
				conn.close();
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
	}
	private Hashtable<String, String> errors= new Hashtable<String, String>();
	public boolean validate() {
		boolean okAll = true;
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
		if(!fname.matches("[a-zA-Z-]+")) {
			errors.put("fname", "invalid first name.");
			okAll = false;
		}
		if(!lname.matches("[a-zA-Z-]+")) {
			errors.put("lname", "invalid last name.");
			okAll = false;
		}
		if(yearofbirth.length()!=4||!yearofbirth.matches("[0-9-]+")) {
			errors.put("yearofbirth", "invalid, Year of birth must be 4 numbers like : 1949");
			okAll = false;
		}
		if(creditcard.length()!=16||!creditcard.matches("[0-9-]+")) {
			errors.put("creditcard", "invalid credit card number.");
			okAll = false;
		}
		if(!fulladdress.matches("[0-9a-zA-Z ,-]+")){
			errors.put("address", "invalid address.");
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
