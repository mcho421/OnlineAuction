package jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import exceptions.DataSourceException;
import exceptions.ServiceLocatorException;

/**
 * Code copied from Lab example
 */
public class DBConnectionFactory {
	
	static Logger logger = Logger.getLogger(DBConnectionFactory.class.getName());
	private static DBConnectionFactory factory = null;
	private DataSource ds = null;
	private InitialContext ctx;
	private Context subctx;
	
	private DBConnectionFactory() throws ServiceLocatorException{
		try{
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/database");
			logger.info("Database found:"+ds.toString());
		}catch(NamingException e){
			logger.severe("Cannot find context, throwing exception"+e.getMessage());
			e.printStackTrace();
			throw new ServiceLocatorException();
		}
	}
	
	public DataSource getDataSource(){
		return ds;
	}
	
	public static Connection getConnection() throws ServiceLocatorException, SQLException{
		
		if(factory==null)
			factory = new DBConnectionFactory();
		Connection conn = factory.getDataSource().getConnection();
//		System.out.println("Got a database connection");
		
		return conn;
	}

}
