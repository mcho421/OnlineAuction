package controller;

import group.DatabasePoller;

import javax.servlet.ServletContextEvent;

import jdbc.MailSenderService;

public class ServerStartup implements javax.servlet.ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Executing startup code...");
		(new Thread(new DatabasePoller())).start();
	}

}
