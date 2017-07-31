import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Employee extends JFrame{
	
	static DAO dao= new DAO();
	
	JTextField textField1;
	JTextField textField2;
	JTextField textField3;
	JTextField textField4;
	JTextField textField5;
	
	JButton button1;
	JButton button2;
	JButton button3;
	JButton button4;
	JButton button5;
	JButton button6;
	JButton button7;
	
	JLabel label1;
	JLabel label2;
	
	private int empID;
	private String empName;
	private double empWage;
	private double empTaxRate;
	ResultSet rs;
	
	float[] wages;
	
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
				   empWage =rs.getDouble("empWage");
				   empTaxRate =rs.getDouble("empTaxRate");
			}
		}catch(Exception ex){
			
		}
		
		ListenForButton lForButton = new ListenForButton();
		
		button1 = new JButton("Register Employee");
		button1.addActionListener(lForButton);
		thePanel.add(button1);
		
		button2 = new JButton("Search Employees");
		button2.addActionListener(lForButton);
		thePanel.add(button2);
		
		thePanel.add(Box.createRigidArea(new Dimension(0,10)));
		thePanel.add(new JSeparator(JSeparator.HORIZONTAL));
		thePanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		textField1 = new JTextField("", 15);
		textField1.setText(empName);
		thePanel.add(textField1);
		textField2 = new JTextField("", 15);
		textField2.setText(""+empWage);
		thePanel.add(textField2);
		textField3 = new JTextField("", 15);
		textField3.setText(""+empTaxRate);
		thePanel.add(textField3);
		
		thePanel.add(Box.createRigidArea(new Dimension(0,10)));
		thePanel.add(new JSeparator(JSeparator.HORIZONTAL));
		thePanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		button3 = new JButton("Edit Employee Details");
		button3.addActionListener(lForButton);
		thePanel.add(button3);
		
		button4 = new JButton("Delete Employee");
		button4.addActionListener(lForButton);
		thePanel.add(button4);
		
		thePanel.add(Box.createRigidArea(new Dimension(0,10)));
		thePanel.add(new JSeparator(JSeparator.HORIZONTAL));
		thePanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		button5 = new JButton("Add Hours");
		button5.addActionListener(lForButton);
		thePanel.add(button5);
		
		button6 = new JButton("History");
		button6.addActionListener(lForButton);
		thePanel.add(button6);
		
		button7 = new JButton("Calculate Outstanding Wages");
		button7.addActionListener(lForButton);
		thePanel.add(button7);
		
		label1 = new JLabel("");
		thePanel.add(label1);
		label2 = new JLabel("");
		thePanel.add(label2);
			
		//}
		this.add(thePanel);
		this.setVisible(true);
	}
	
private class ListenForButton implements ActionListener{
		
		// This method is called when an event occurs
		
		public void actionPerformed(ActionEvent e){
			
			if(e.getSource() == button1){
				RegisterEmployee.main(null);
			}else if(e.getSource() == button2){
			    SearchEmployees.main(null);
			}else if(e.getSource() == button3){
				String[] args = new String[] {""+empID , ""+empName , ""+empWage , ""+empTaxRate};
				EditEmployee.main(args);
			}else if(e.getSource() == button4){
				int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to delete "+empName+" from employees?");
				if(dialogResult == JOptionPane.YES_OPTION){
				  try {
					dao.deleteEmployee(empID);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				  SearchEmployees.main(null);
				}else{
					String[] args = new String[] {""+empID};
					Employee.main(args);
				}
			}else if(e.getSource() == button5){
				String[] args = new String[] {""+empID};
				AddHours.main(args);
			}else if(e.getSource() == button6){
				String[] args = new String[] {""+empID, empName};
				History.main(args);
			}else if(e.getSource() == button7){
				wages=dao.getWages(empID);
				label1.setText("Outstanding confirmed wages: "+wages[0]);
				label2.setText("Outstanding confirmed wages after tax: "+wages[1]);
			}
			
			if(e.getSource() == button5 || e.getSource() == button6 || e.getSource() == button7){
				
			}else{
			dispose();
			}
		}
	}
}



