package controller;

import exceptions.MailSenderException;
import exceptions.ServiceLocatorException;
import group.Bid;
import group.Item;
import group.Message;
import group.UserBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.DBConnectionFactory;
import jdbc.MailSenderService;

public class CommandAcceptRejectBid implements Command {
	
	private static final String page = "/BidAcceptRejectSent.jsp";
	private static final String error = "/WEB-INF/error.jsp";
	private static final String login = "/controller?action=loginPage";

	public CommandAcceptRejectBid() {
		super();
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, SQLException {

		if (!Controller.isLoggedIn(request, response))
			return login;
		if(Controller.isAdmin(request, response))
			return login;

		String itemIdString = request.getParameter("item");
		String statusString = request.getParameter("status");
		
		boolean accept = false;
		if (statusString == null) {
			request.setAttribute("errorMsg", "You did not accept/reject the bid");
			return error;
		} else if (statusString.equals("Accept")) {
			accept = true;
		} else if (statusString.equals("Reject")) {
			accept = false;
		} else {
			request.setAttribute("errorMsg", "You did not accept/reject the bid");
			return error;
		}
		request.setAttribute("accept", accept);
		
		System.out.println("status:"+statusString);
		int itemId = 0;
		try {
		    itemId = Integer.parseInt(itemIdString);
		} catch ( Exception e ) {
		    itemId = 0;
		}

		String username = Controller.getUsername(request, response);
		UserBean user = new UserBean();
		UserBean buyer = new UserBean();
		Item item = new Item();
		Bid bid = new Bid();
		Connection conn = null;
		
		try {
			conn = DBConnectionFactory.getConnection();
			//conn.setAutoCommit(false);
			
			item = Item.initializeFromId(conn, itemId);
			user = UserBean.initializeFromUsername(conn, username);
			bid = Bid.initializeFromId(conn, item.getCurrentBidId());
			
			if (user.getUserid() != item.getSeller()) {
				request.setAttribute("errorMsg", "You are not the owner of this item");
				return error;
			}
			if (item.canAcceptReject(conn, username) == false) {
				request.setAttribute("errorMsg", "You cannot accept/reject this bid");
				return error;
			}
			buyer = UserBean.initializeFromId(conn, item.getCurrentBidder());
			if (accept == true) {
				System.out.println("ACCEPTING BID");
				try {
					//send msg
					Message.sendMsg("You have accepted the bid on the item:"+item.getTitle(), user.getUsername());
					
					MailSenderService mail = MailSenderService.getMailSender();
					StringBuffer text = new StringBuffer();
					text.append("You have accepted the bid on the item:\n");
					text.append(item.getTitle());
					text.append("\nLink to auction:\n");
					String link = request.getScheme() + "://"
							+ request.getServerName() +":" + request.getServerPort()
							+ request.getContextPath() + "/"
							+ "controller?action=itemPage&item="
							+ item.getId();
					text.append(link);
					mail.sendMessage(user.getUseremail(), "You accepted the bid on '"+item.getTitle()+"'", text);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					//send msg
					Message.sendMsg("Your bid has been accepted on the item:"+item.getTitle(), user.getUsername());
					
					MailSenderService mail = MailSenderService.getMailSender();
					StringBuffer text = new StringBuffer();
					text.append("Your bid has been accepted on the item:\n");
					text.append(item.getTitle());
					text.append("\nLink to auction:\n");
					String link = request.getScheme() + "://"
							+ request.getServerName() +":" + request.getServerPort()
							+ request.getContextPath() + "/"
							+ "controller?action=itemPage&item="
							+ item.getId();
					text.append(link);
					mail.sendMessage(user.getUseremail(), "Your bid has been accepted on '"+item.getTitle()+"'", text);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("REJECTING BID");
				try {
					//send msg
					Message.sendMsg("You have rejected the bid on the item:"+item.getTitle(), user.getUsername());
					
					MailSenderService mail = MailSenderService.getMailSender();
					StringBuffer text = new StringBuffer();
					text.append("You have rejected the bid on the item:\n");
					text.append(item.getTitle());
					text.append("\nLink to auction:\n");
					String link = request.getScheme() + "://"
							+ request.getServerName() +":" + request.getServerPort()
							+ request.getContextPath() + "/"
							+ "controller?action=itemPage&item="
							+ item.getId();
					text.append(link);
					mail.sendMessage(user.getUseremail(), "You rejected the bid on '"+item.getTitle()+"'", text);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					//send msg
					Message.sendMsg("Your bid has been rejected on the item:"+item.getTitle(), user.getUsername());
					
					MailSenderService mail = MailSenderService.getMailSender();
					StringBuffer text = new StringBuffer();
					text.append("Your bid has been rejected on the item:\n");
					text.append(item.getTitle());
					text.append("\nLink to auction:\n");
					String link = request.getScheme() + "://"
							+ request.getServerName() +":" + request.getServerPort()
							+ request.getContextPath() + "/"
							+ "controller?action=itemPage&item="
							+ item.getId();
					text.append(link);
					mail.sendMessage(user.getUseremail(), "Your bid has been rejected on '"+item.getTitle()+"'", text);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Item.accept(conn, item.getId());
			//conn.commit();
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
