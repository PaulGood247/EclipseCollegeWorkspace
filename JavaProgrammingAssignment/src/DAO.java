import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

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
	
	public void addEmployee(String empName,float empWage, float empTaxRate, String empPassword ) throws SQLException{
		String query = " insert into employee (empName,  empWage, empTaxRate, empPassword)"
		        + " values (?, ?, ? , ?)";
		 
		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = conn.prepareStatement(query);
		      preparedStmt.setString(1, empName);
		      preparedStmt.setFloat(2, empWage);
		      preparedStmt.setFloat (3, empTaxRate);
		      preparedStmt.setString(4, empPassword);
		 
		      // execute the preparedstatement
		      preparedStmt.execute();
	}
	
	public void editEmployee(int empID, String empName,float empWage, float empTaxRate ) throws SQLException{
		String query="UPDATE employee SET empName =?, empWage =? ,empTaxRate=?"
	               + " WHERE empID = ?";
		
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setString(1, empName);
		preparedStmt.setFloat(2, empWage);
		preparedStmt.setFloat(3, empTaxRate);
		preparedStmt.setInt(4, empID);
		preparedStmt.executeUpdate();
	}
	
	public void deleteEmployee(int empID ) throws SQLException{
		String query="DELETE FROM employee WHERE empID = ?";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setInt(1, empID);
		preparedStmt.executeUpdate();
		
		String query1="ALTER TABLE employee AUTO_INCREMENT = 1";
		PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
		preparedStmt1.executeUpdate();
	}
	
	public void addShift(int empID,String day, String start, String end, float breakTime, boolean paid, boolean confirmedByEmployer ) throws SQLException{
		String query = " insert into shift(empID, day, start, end, break, paid, confirmedByEmployer)"
		        + " values (? , ? , ? , ? , ? , ?, ?)";
		
		      PreparedStatement preparedStmt = conn.prepareStatement(query);
		      preparedStmt.setInt(1, empID);
		      preparedStmt.setString(2, day);
		      preparedStmt.setString (3, start);
		      preparedStmt.setString (4, end);
		      preparedStmt.setFloat (5, breakTime);
		      preparedStmt.setBoolean (6, paid);
		      preparedStmt.setBoolean (7, confirmedByEmployer);
		 
		      // execute the preparedstatement
		      preparedStmt.execute();
	}
	
	public ResultSet getWorkHistory(int x){
		ResultSet rs=null;
		try {
			String query = "SELECT * FROM shift "
					+ "WHERE empID= ? ";
			PreparedStatement pstmt = conn.prepareStatement(query);
		    pstmt.setInt(1, x); 
		    rs = pstmt.executeQuery();
		    
		} catch (SQLException e) {
		}
		return rs;
	}
	
	public void setPaid(int x) throws SQLException{
		String query="UPDATE shift SET paid =?"
	               + " WHERE shiftID = ?";
		
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setBoolean(1, true);
		preparedStmt.setInt(2, x);
		preparedStmt.executeUpdate();
	}
	
	public void setConfirmed(int x) throws SQLException{
		String query="UPDATE shift SET confirmedByEmployer =?"
	               + " WHERE shiftID = ?";
		
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setBoolean(1, true);
		preparedStmt.setInt(2, x);
		preparedStmt.executeUpdate();
	}
	
	public float[] getWages(int x){
		ResultSet rs=null;
		ResultSet rs1=null;
		
		float[] total = new float[2];
		float empWage = 0;
		float empTaxRate = 0;
		float totalHours = 0;
		try {
		    
		    String query1 = "SELECT empWage, empTaxRate FROM employee "
					+ "WHERE empID= ? ";
			PreparedStatement pstmt1 = conn.prepareStatement(query1);
		    pstmt1.setInt(1, x); 
		    rs1 = pstmt1.executeQuery();
		  
		    while(rs1.next()){
		    	empWage=rs1.getFloat("empWage");
		    	empTaxRate=rs1.getFloat("empTaxRate");
		    }
		    
		    String query = "SELECT * FROM shift "
					+ "WHERE empID= ? AND paid= false AND confirmedByEmployer= true";
			PreparedStatement pstmt = conn.prepareStatement(query);
		    pstmt.setInt(1, x); 
		    rs = pstmt.executeQuery();
		    
			while(rs.next()){
				
				String start=rs.getString("start");
				String end=rs.getString("end");
				float breakTime=rs.getFloat("break");
				
				String temp1 = start;
            	int hours1= Integer.parseInt(temp1.substring(0,2));
            	int mins1= Integer.parseInt(temp1.substring(3,5));
            	
            	String temp2 = end;
            	int hours2= Integer.parseInt(temp2.substring(0,2));
            	int mins2= Integer.parseInt(temp2.substring(3,5));
            	
            	int k=hours2-hours1;
            	int l= mins2-mins1;
            	if(l<0){
            		l=l+60;
            		k++;
            	}
            	float hoursDone = Float.parseFloat(k+"."+l);
            	totalHours+=hoursDone;
            	totalHours=totalHours-breakTime;
			}
		    
			total[0] = empWage* totalHours;
			total[1] = ((empWage* totalHours)/(empTaxRate+100))*100;
		} catch (SQLException e) {
		}
		return total;
	}
	
	public boolean checkAdminPassword(String password){
		ResultSet rs=null;
		try {
			String query = "SELECT * FROM admin "
					+ "WHERE AdminPassword= ? ";
			PreparedStatement pstmt = conn.prepareStatement(query);
		    pstmt.setInt(1, Integer.parseInt(password)); 
		    rs = pstmt.executeQuery();
		    
		    if(rs.first()){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}
	
	public boolean checkEmpPassword(String empID, String password){
		ResultSet rs=null;
		try {
			String query = "SELECT * FROM employee "
					+ "WHERE empID= ? AND empPassword= ? ";
			PreparedStatement pstmt = conn.prepareStatement(query);
		    pstmt.setInt(1, Integer.parseInt(empID)); 
		    pstmt.setInt(2, Integer.parseInt(password));
		    rs = pstmt.executeQuery();
		    
		    if(rs.first()){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}
}