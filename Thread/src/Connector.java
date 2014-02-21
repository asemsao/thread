import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector {
	public final String user = "HR";
    public final String pass="tiko";
    public final String url="jdbc:oracle:thin:@//localhost:1521/XE";
    
	private Connection con = null;
	private boolean conIsFree = true;
	private static Connector instance = null;
	
	public static Connector getInstance(){
		if (instance == null) {
			try {
				instance = new Connector();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return instance;
	}
	
	public Connector() throws Exception{
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		}catch (Exception e){
				e.printStackTrace();
				throw new Exception();
		}
		try{
			connect();
		}catch(SQLException e ){
			e.printStackTrace();
			throw new Exception();
		}
	}
	
	private synchronized Connection getConnection () {
		while(!conIsFree) {
			try{
				wait();
			}catch(InterruptedException e){}
		}
		conIsFree = false;
		notify();
		return con;
	}
	
	private synchronized void releaseConnection() {
		while(conIsFree){
			try{
				wait();
			}catch(InterruptedException e){}
		}
		conIsFree = true;
		notify();
	}
	
	public void connect() throws SQLException{
		con=DriverManager.getConnection(url,user,pass);
	}
	
	public void terminateConnection(){
		try{
			if(con!=null) con.close();
		}catch(SQLException e){}
	}
	
	public boolean executeUpdateQuery(String sqls){
		boolean returnValue = true;
		getConnection();
		boolean oldCommitMode = false;
		boolean commitModeChecked = false;
		try{
			oldCommitMode = con.getAutoCommit();
			commitModeChecked = true;
			con.setAutoCommit(true);
			Statement statment = con.createStatement();
			int batchState = statment.executeUpdate(sqls);
			boolean batchSuccessful = true;
			if(batchState==0){
				batchSuccessful = false;
			}
					
			if(!batchSuccessful){
				returnValue = false;
				con.rollback();
			}else {
				con.commit();
			}
			
		}catch (Exception e ){
			returnValue = false;
		}finally{
			if(commitModeChecked){
				try{
					con.setAutoCommit(oldCommitMode);					
				}catch(SQLException e){}
			}
			releaseConnection();
		}
		
		return returnValue;
	}
	
	public ResultSet executeSelectQuery(String sql) throws SQLException{
		getConnection();
		boolean ok = true;
		ResultSet rs = null;
		try{
			Statement statement = con.createStatement();
			rs = statement.executeQuery(sql);
			
			con.commit();
		}catch(Exception e){
			e.printStackTrace();
			ok = false;
		}finally{
			releaseConnection();
		}
		if(!ok){
			throw new SQLException();
		}
		return rs;
	}
	
}
	