package controller;

import group.Bid;
import group.Item;
import group.UserBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.DBConnectionFactory;

public class CommandConfirmBid implements Command {
	
	private static final String page = "/BidSuccess.jsp";
	private static final String error = "/WEB-INF/error.jsp";
	private static final String login = "/controller?action=loginPage";

	public CommandConfirmBid() {
		super();
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, SQLException {

		if (!Controller.isLoggedIn(request, response))
			return login;

		String itemIdString = request.getParameter("item");
		String bidAmountString = request.getParameter("bidAmount");
		int itemId = 0;
		int bidAmount= 0;
		try {
		    itemId = Integer.parseInt(itemIdString);
		} catch ( Exception e ) {
		    itemId = 0;
		}
		try {
		    bidAmount = Integer.parseInt(bidAmountString);
		} catch ( Exception e ) {
//			e.printStackTrace();
			request.setAttribute("errorMsg", "Invalid bid $"+bidAmountString);
			return error;
		}

		// TODO Change this so initialize user in the transaction
		String username = Controller.getUsername(request, response);
		UserBean user = new UserBean();
		Bid bid = new Bid();
		Item item = new Item();
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection();
			conn.setAutoCommit(false);

			user = UserBean.initializeFromUsername(conn, username);
			System.out.println("amount: " + bidAmount);
			bid.setBid(bidAmount);
			bid.setBidder(user.getUserid());
			bid.setBidTimeAsCurrentTime();
			bid.setItem(itemId);

			item = Item.initializeFromId(conn, itemId);
			if (item.isClosed()) {
				request.setAttribute("errorMsg", "Auction is closed");
				return error;
			}
			if (bid.getBid() < item.getMinimumBid()) {
				request.setAttribute("errorMsg", "Bid is lower than minimum bid");
				return error;
			}
			bid.insert(conn);

			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null)
				conn.rollback();
			return error;
		} finally {
			if (conn != null)
				conn.close();
		}

		request.setAttribute("item", item);
		request.setAttribute("bid", bid);
		return page;
	}


}
