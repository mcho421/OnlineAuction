package group;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;

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

		try {
			Connection conn = DBConnectionFactory.getConnection();
			if(conn!=null) System.out.println("connected");
			String sqlQuery = "SELECT username, password, email, fname, lname, yearofbirth, fulladdress, creditcard FROM users where username =  '"+Username+"'";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sqlQuery);
			if(rs.next()){
            username = rs.getString(1);
            userpwd = rs.getString(2);
            useremail = rs.getString(3);
            yearofbirth = rs.getString(6);
            if(yearofbirth==null) yearofbirth = "";
            fname = rs.getString(4);
            if(fname==null) fname = "";
            lname = rs.getString(5);
            if(lname==null) lname = "";
            fulladdress = rs.getString(7);
            if(fulladdress==null) fulladdress = "";
            creditcard = rs.getString(8); 
            if(creditcard==null) creditcard = "";
			}
			st.close();
			rs.close();
		}catch  (Exception e) {
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
