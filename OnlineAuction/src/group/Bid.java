package group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import exceptions.ServiceLocatorException;
import jdbc.DBConnectionFactory;
public class Bid {
	private int id = 0;
	private int bid = 0;
	private Timestamp bidTime = new Timestamp(System.currentTimeMillis());
	private int bidder = 0;
	private int item = 0;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public Timestamp getBidTime() {
		return bidTime;
	}
	public void setBidTime(Timestamp bidTime) {
		this.bidTime = bidTime;
	}
	public void setBidTimeAsCurrentTime() {
		this.bidTime = new Timestamp(System.currentTimeMillis());
	}
	public int getBidder() {
		return bidder;
	}
	public void setBidder(int bidder) {
		this.bidder = bidder;
	}
	public int getItem() {
		return item;
	}
	public void setItem(int item) {
		this.item = item;
	}
	public void insert(Connection conn) throws SQLException {
		PreparedStatement st = null;
		try {
			String sqlQuery = "INSERT INTO Bids(bid, bidTime, bidder, item) VALUES(?, ?, ?, ?)";
			st = conn.prepareStatement(sqlQuery);
			st.setInt(1, getBid());
			st.setTimestamp(2, getBidTime());
			st.setInt(3, getBidder());
			st.setInt(4, getItem());
			st.executeUpdate();
		} finally {
			if (st != null)
				st.close();
		}
	}

	public static Bid initializeFromId(Connection conn, int id) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Bid item = new Bid();
		try {
			String sqlQuery = "SELECT * FROM Bids WHERE id = ?";
			st = conn.prepareStatement(sqlQuery);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()){
				item = makeItem(rs);
			} else {
				System.out.println("Bid id "+id+" not found.");
				throw new SQLException();
			}
		} finally {
			if (st != null)
				st.close();
			if (rs != null)
				rs.close();
		}
		return item;
	}
	
	private static Bid makeItem(ResultSet rs) throws SQLException {
		Bid item = new Bid();
		item.setId(rs.getInt(1));
		item.setBid(rs.getInt(2));
		item.setBidTime(rs.getTimestamp(3));
		item.setBidder(rs.getInt(4));
		item.setItem(rs.getInt(5));
		return item;

	}

	private Hashtable<String, String> errors= new Hashtable<String, String>();

	public boolean validate() {
		boolean okAll = true;
//		if (!title.matches("[0-9a-zA-Z ,!?.-]+")) {
//			errors.put("title","invalid input, only numbers, letters ',' '.' '!' '?' allowed");
//			System.out.println("1");
//			okAll = false;
//			}
//		if(!title.matches("^\\W*(\\w+(\\W+|$)){1,10}$")){
//			errors.put("title","invalid input, 10 words max");
//			okAll = false;
//		}
//		if(!description.matches("[0-9a-zA-Z ,!?.-]+")){
//		    errors.put("descrption", "invalid input, only numbers, letters ',' '.' '!' '?' allowed");
//		    System.out.println("2");
//		    okAll = false;
//		   }
//		if(!description.matches("^\\W*(\\w+(\\W+|$)){1,10}$")){
//			errors.put("description","invalid input, 100 words max");
//			okAll = false;
//		}
//		if(!postage.matches("[0-9a-zA-Z ,!?.-]+")) {
//			errors.put("postage", "invalid input, only numbers, letters ',' '.' '!' '?' allowed");
//			System.out.println("3");
//			okAll = false;
//		}
//		if(rprice < 0) {
//			errors.put("rprice", "invalid price");
//			System.out.println("4");
//			okAll = false;
//		}
//		if(sprice < 0){
//			errors.put("sprice", "invalid price");
//			System.out.println("5");
//			okAll = false;	
//				}
//		if(bincre <= 0 ) {
//			errors.put("iprice", "invalid price");
//			System.out.println("6");
//			okAll = false;
//		}
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
