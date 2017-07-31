import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO {

	Connection conn = null;
 	Statement stmt = null;
 	
 	
	public void connect(){
	     //try {
	         
	         try {
				conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/salaryDB","root","1234");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         
	         if(conn!=null){
	        	 System.out.println("Connection Successful");
	         }
	}
	
	public ResultSet getAllEmployees(){
		ResultSet rs=null;
		try {
			stmt = conn.createStatement();

			String sql = "SELECT * FROM Employee";
        
			rs = stmt.executeQuery(sql);
			
			} catch (SQLException e) {
			e.printStackTrace();
			}
		return rs;
	}
	
	public ResultSet getEmployee(int x){
		ResultSet rs=null;
		try {
			String query = "SELECT * FROM Employee "
					+ "WHERE empID= ? ";
			PreparedStatement pstmt = conn.prepareStatement(query);
		    pstmt.setInt(1, x); 
		    rs = pstmt.executeQuery();
		    
		} catch (SQLException e) {
		}
		return rs;
	}
	
	public void addEmployee(String empName,float empWage, float empTaxRate ) throws SQLException{
		String query = " insert into employee (`empName`, `empWage`, `empTaxRate`)"
		        + " values (?, ?, ?)";
		 
		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = conn.prepareStatement(query);
		      preparedStmt.setString (1, empName);
		      preparedStmt.setFloat (2, empWage);
		      preparedStmt.setFloat   (3, empTaxRate);
		 
		      // execute the preparedstatement
		      preparedStmt.execute();
	}
}