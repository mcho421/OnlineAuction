package group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import exceptions.ServiceLocatorException;

import jdbc.DBConnectionFactory;

public class Message {
private boolean read;
private String message;
private Timestamp time;
public boolean isRead() {
	return read;
}
public void setRead(boolean read) {
	this.read = read;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public Timestamp getTime() {
	return time;
}
public void setTime(Timestamp time) {
	this.time = time;
}
public static List<Message> getMsg(String username){
	@SuppressWarnings({ "rawtypes", "unchecked" })
	List<Message> result = new ArrayList();	
	PreparedStatement st = null;
	ResultSet rs = null;
	try {
		int id = 0;
		Connection conn = DBConnectionFactory.getConnection();
		st = conn.prepareStatement("Select id from users where username=?");
		st.setString(1, username);
		rs=st.executeQuery();
		if(rs.next()) id = rs.getInt(1);
		rs.close();
		st.close();
		st = conn.prepareStatement("Select message, read, senttime from profilemessages where owner = ?");
		st.setInt(1, id);
		rs=st.executeQuery();
		while(rs.next()){
			Message msg = new Message();
			msg.setMessage(rs.getString(1));
			msg.setRead(rs.getBoolean(2));
			msg.setTime(rs.getTimestamp(3));
			result.add(msg);
		}
		rs.close();
		st.close();
		st = conn.prepareStatement("UPDATE profilemessages SET read=true where owner = ?");
		st.setInt(1, id);
		st.executeUpdate();
		st.close();
		conn.close();
		
	} catch (ServiceLocatorException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return result;
}
public static boolean deleteMsg(String username) {
	PreparedStatement st = null;
	ResultSet rs = null;
	try {
		int id = 0;
		Connection conn = DBConnectionFactory.getConnection();
		st = conn.prepareStatement("Select id from users where username=?");
		st.setString(1, username);
		rs=st.executeQuery();
		if(rs.next()) id = rs.getInt(1);
		rs.close();
		st.close();
		st = conn.prepareStatement("Delete from profilemessages where owner = ?");
		st.setInt(1, id);
		st.executeUpdate();
		st.close();
		conn.close();
		return true;
		
	} catch (ServiceLocatorException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return false;
}
public static void sendMsg(String msg,int id){
	PreparedStatement st = null;
	try {
		Connection conn = DBConnectionFactory.getConnection();
		st = conn.prepareStatement("INSERT INTO profilemessages(message, owner)VALUES (?, ?)");
        st.setString(1, msg);
        st.setInt(2, id);
		st.executeUpdate();
		st.close();
		conn.close();
	} catch (ServiceLocatorException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
public static void sendMsg(String msg,String username){
	PreparedStatement st = null;
	ResultSet rs = null;
	try {
		int id = 0;
		Connection conn = DBConnectionFactory.getConnection();
		st = conn.prepareStatement("Select id from users where username=?");
		st.setString(1, username);
		rs=st.executeQuery();
		if(rs.next()) id = rs.getInt(1);
		rs.close();
		st.close();
		st = conn.prepareStatement("INSERT INTO profilemessages(message, owner)VALUES (?, ?)");
        st.setString(1, msg);
        st.setInt(2, id);
		st.executeUpdate();
		st.close();
		conn.close();
	} catch (ServiceLocatorException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
}

