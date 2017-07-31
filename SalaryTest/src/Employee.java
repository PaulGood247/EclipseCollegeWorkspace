import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Employee extends JFrame{
	
	static DAO dao= new DAO();
	
	JTextField textField1;
	
	private int empID;
	private String empName;
	private int empWage;
	private int empTaxRate;
	ResultSet rs;
	public static void main(String[] args){
		dao.connect();
		if (args.length == 0) {
	        System.out.println("Error- please type a string");
	    } else {
	    	new Employee(args[0]);
	    }
	}
	
	public Employee(String s){
		
		empID=Integer.parseInt(s);
		this.setSize(400, 400);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);
				 
		this.setLocation(xPos, yPos);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		this.setTitle("Employee");
				
		JPanel thePanel = new JPanel();
		
		thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.Y_AXIS));
		
		System.out.println(empID);
		
		rs= dao.getEmployee(empID);
		
		try{
			while(rs.next()){
				   empName= rs.getString("EmpName");
				   empWage =rs.getInt("empWage");
				   empTaxRate =rs.getInt("empTaxRate");
			}
		}catch(Exception ex){
			
		}
		
		textField1 = new JTextField("", 15);
		textField1.setText(empID+" "+ empName+" "+empWage);
		thePanel.add(textField1);
		//}catch(Exception e){
			
		//}
		this.add(thePanel);
		this.setVisible(true);
	}
}



