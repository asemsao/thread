import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


public class Manager {
	private static String sql = "";
	private static Connector conn;
	private static ResultSet rs; 
	
	public static String selectNumberEmployee(){
		String numberEmployee= "";
		sql = "select count(*) from employees";
		try {
			conn = new Connector();
			conn.connect();	
			
			rs = conn.executeSelectQuery(sql);
			while (rs.next()) {
				numberEmployee = rs.getString(1);
			}
						
		} catch (SQLException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn = null;			
		}
		return numberEmployee;
	}	
	
	public static String sumSalaryEmployee(){
		String sumSalaryEmployee= "";
		sql = "select sum(salary) from employees";
		try {
			conn = new Connector();
			conn.connect();	
			
			rs = conn.executeSelectQuery(sql);
			while (rs.next()) {
				sumSalaryEmployee = rs.getString(1).trim();
			}
				sumSalaryEmployee = formatDecimal(sumSalaryEmployee);		
		} catch (SQLException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn = null;			
		}
		return sumSalaryEmployee;
	}	
	
	public static String avgSalaryEmployee(){
		String avgSalaryEmployee= "";
		sql = "select avg(salary) from employees";
		try {
			conn = new Connector();
			conn.connect();	
			
			rs = conn.executeSelectQuery(sql);
			while (rs.next()) {
				avgSalaryEmployee = rs.getString(1).trim();
			}
				avgSalaryEmployee = formatDecimal(avgSalaryEmployee);		
		} catch (SQLException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn = null;			
		}
		return avgSalaryEmployee;
	}	
	
	public static String employeeHighestSalary(){
		String employeeHighestSalary= "";
		sql = "select employee_id, first_name, last_name from employees " +
				"where salary = (select max(salary)from employees)";
		try {
			conn = new Connector();
			conn.connect();	
			
			rs = conn.executeSelectQuery(sql);
			while (rs.next()) {
				employeeHighestSalary = rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3);
			}
						
		} catch (SQLException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn = null;			
		}
		return employeeHighestSalary;
	}
	
	public static String employeeLowestSalary(){
		String employeeLowestSalary= "";
		sql = "select employee_id, first_name, last_name from employees " +
				"where salary = (select min(salary)from employees)";
		try {
			conn = new Connector();
			conn.connect();	
			
			rs = conn.executeSelectQuery(sql);
			while (rs.next()) {
				employeeLowestSalary = rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3);
			}
						
		} catch (SQLException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn = null;			
		}
		return employeeLowestSalary;
	}
	
	private static String formatDecimal(String input){
		String output = "";
		NumberFormat nf = NumberFormat.getNumberInstance(new Locale("de", "DE"));
		DecimalFormat df = (DecimalFormat)nf;
		df.applyPattern("###,###.###");
		output = df.format(Double.parseDouble(input));
		
		return output;
	}
}
