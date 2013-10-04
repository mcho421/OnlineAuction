package group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jdbc.DBConnectionFactory;

public class DatabasePoller implements Runnable {
	
	private static final int NTHREDS = 2;
	private ExecutorService executor;

	public DatabasePoller() {
		executor = Executors.newFixedThreadPool(NTHREDS);
	}

	@Override
	public void run() {
		try {
	        while (true) {
				Timestamp time = new Timestamp(System.currentTimeMillis());
				System.out.println("Polling database: "+time);
				executor.execute(new DatabasePollerWorker(time));
	            Thread.sleep(5 * 1000);
	        }
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }

	}
}

class DatabasePollerWorker implements Runnable {
	
	private Timestamp time;
	
	public DatabasePollerWorker(Timestamp time) {
		this.time = time;
	}

	@Override
	public void run() {
		System.out.println("In worker: "+time);
		Connection conn = null;
		List<Item> result = new ArrayList<Item>();
		try {
			try {
				conn = DBConnectionFactory.getConnection();
				result = Item.getAuctionsFinishing(conn, time);
				for (Item i : result) {
					System.out.println(i.getTitle()+" "+i.getCtime());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (exceptions.ServiceLocatorException e) {
				e.printStackTrace();
			} finally {
				if (conn != null)
					conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
