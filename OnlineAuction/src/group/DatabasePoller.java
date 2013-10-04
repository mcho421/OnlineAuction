package group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import jdbc.DBConnectionFactory;
import jdbc.MailSenderService;

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
	            Thread.sleep(30 * 1000);
	        }
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }

	}
}

class DatabasePollerWorker implements Runnable {
	
	private Timestamp time;
	private Timestamp checkTimesBefore;
	
	public DatabasePollerWorker(Timestamp time) {
		this.time = time;
		Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time.getTime());
        cal.add(Calendar.SECOND, 60);
        checkTimesBefore = new Timestamp(cal.getTime().getTime());
	}

	@Override
	public void run() {
		System.out.println("In worker: "+time);
		Connection conn = null;
		List<Item> result = new ArrayList<Item>();
		try {
			try {
				conn = DBConnectionFactory.getConnection();
				result = Item.getAuctionsFinishing(conn, checkTimesBefore);
				conn.setAutoCommit(false);
				for (Item i : result) {
					System.out.println(i.getTitle()+" "+i.getCtime());
					if (i.getCtime().before(time)) {
						if (i.checkAndEndAuction(conn) == true) {
							conn.commit();
							System.out.println("Ended Auction: "+i.getTitle());
							conn.setAutoCommit(true);
							auctionFinished(conn, i);
							conn.setAutoCommit(false);
						} else {
							conn.rollback();
						}

					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("rolling back...");
				if (conn != null)
					conn.rollback();
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
	
	private void auctionFinished(Connection conn, Item item) throws SQLException {
		Item.updateCurrentBid(conn, item);
		UserBean owner = UserBean.initializeFromId(conn, item.getSeller());
		if (item.getCurrentBidder() == 0) {
			// no bidders
			System.out.println("No bidders on item: "+item.getTitle());
			try {
				MailSenderService mail = MailSenderService.getMailSender();
				StringBuffer text = new StringBuffer();
				text.append("There were no bids on item:\n");
				text.append(item.getTitle());
				text.append("\nBetter luck next time!\n");
				mail.sendMessage(owner.getUseremail(), "Auction Finished: '"+item.getTitle()+"'", text);
			} catch (exceptions.ServiceLocatorException e) {
				e.printStackTrace();
			} catch (exceptions.MailSenderException e) {
				e.printStackTrace();
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		} else if (item.getCurrentBiddingPrice() >= item.getRprice()) {
			// send user and owner message
			System.out.println("Winning bid more than reserve: "+item.getTitle());
		} else {
			// send user message and item owner needs to accept or reject
			System.out.println("Winning bid less than reserve: "+item.getTitle());
		}
	}
	
}
