package group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Hashtable;

import exceptions.ServiceLocatorException;
import jdbc.DBConnectionFactory;
public class Item {
private String title="";
private int category;
private String imageurl="";
private String description="";
private String postage="";
private int rprice;
private int sprice;
private int bincre;
private Timestamp ctime = new Timestamp(System.currentTimeMillis());
private int seller;
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public int getCategory() {
	return category;
}
public void setCategory(int category) {
	this.category = category;
}
public String getImageurl() {
	return imageurl;
}
public void setImageurl(String imageurl) {
	this.imageurl = imageurl;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getPostage() {
	return postage;
}
public void setPostage(String postage) {
	this.postage = postage;
}
public int getRprice() {
	return rprice;
}
public void setRprice(int rprice) {
	this.rprice = rprice;
}
public int getSprice() {
	return sprice;
}
public void setSprice(int sprice) {
	this.sprice = sprice;
}
public int getBincre() {
	return bincre;
}
public void setBincre(int bincre) {
	this.bincre = bincre;
}
public Timestamp getCtime() {
	return ctime;
}
public void setCtime(Timestamp ctime) {
	this.ctime = ctime;
}
public void Initialize(String Title) {

	try {
		Connection conn = DBConnectionFactory.getConnection();
		if(conn!=null) System.out.println("connected");
		String sqlQuery = "SELECT items.title, categories.category, items.picturepath, items.description, items.postagedetails,"+ 
                          "items.reserveprice, items.biddingstartprice, items.biddingincrements, items.closingtime, users.username"+
                          "FROM items"+
                          "join categories on items.category = categories.id"+
                          "join users on items.seller = users.id where items.title =  '"+Title+"'";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sqlQuery);
		if(rs.next()){
        title= rs.getString(1);
        category = rs.getInt(2);
        imageurl = rs.getString(3);
        description = rs.getString(4);
        postage = rs.getString(5);
        rprice = rs.getInt(6);
        sprice = rs.getInt(7);
        bincre = rs.getInt(8);
        ctime  = rs.getTimestamp(9);
        seller = rs.getInt(10);
		st.close();
		rs.close();
		}
	}catch  (Exception e) {
		e.printStackTrace();
	}
}
public void Insert(String username) {

	Connection conn;
	try {
		conn = DBConnectionFactory.getConnection();
		if(conn!=null) System.out.println("connected");
		String sqlQuery = "select id from users where username = '"+username+"'";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sqlQuery);
		if(rs.next()) this.seller = rs.getInt(1);
		st.close();
		rs.close();
		sqlQuery = "INSERT INTO items( title, category, picturepath, description, postagedetails,"+
                          "reserveprice, biddingstartprice, biddingincrements, closingtime,"+
                          "seller) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pres = conn.prepareStatement(sqlQuery);
		pres.setString(1, this.title);
		pres.setInt(2, this.category);
		pres.setString(3, this.imageurl);
		pres.setString(4, this.description);
		pres.setString(5, this.postage);
		pres.setInt(6, this.rprice);
		pres.setInt(7, this.sprice);
		pres.setInt(8, this.bincre);
		pres.setTimestamp(9, this.ctime);
		pres.setInt(10, this.seller);
		pres.executeUpdate();		
                          
	} catch (ServiceLocatorException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
private Hashtable<String, String> errors= new Hashtable<String, String>();
public boolean validate() {
	boolean okAll = true;
	if (!title.matches("[0-9a-zA-Z ,!?.-]+")) {
		errors.put("title","invalid input, only numbers, letters ',' '.' '!' '?' allowed");
		System.out.println("1");
		okAll = false;
		}

	if(!description.matches("[0-9a-zA-Z ,!?.-]+")){
	    errors.put("descrption", "invalid input, only numbers, letters ',' '.' '!' '?' allowed");
	    System.out.println("2");
	    okAll = false;
	   }
	if(!postage.matches("[0-9a-zA-Z ,!?.-]+")) {
		errors.put("postage", "invalid input, only numbers, letters ',' '.' '!' '?' allowed");
		System.out.println("3");
		okAll = false;
	}
	if(rprice < 0) {
		errors.put("rprice", "invalid price");
		System.out.println("4");
		okAll = false;
	}
	if(sprice < 0){
		errors.put("sprice", "invalid price");
		System.out.println("5");
		okAll = false;	
			}
	if(bincre <= 0 ) {
		errors.put("iprice", "invalid price");
		System.out.println("6");
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
	public int getSeller() {
		return seller;
	}
	public void setSeller(int seller) {
		this.seller = seller;
	}
}
