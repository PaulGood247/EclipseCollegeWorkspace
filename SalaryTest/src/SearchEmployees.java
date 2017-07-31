import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

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

public class SearchEmployees extends JFrame {

	static DAO dao = new DAO();
	static ResultSet rs;
	static ArrayList<Integer> empIDs = new ArrayList<Integer>();
	protected static int empID;
	ArrayList<String> emps = new ArrayList<String>();
	
	JTextField textField1;
	JTextField textField2;
	JTextField textField3;
	JTextField textField4;
	JButton button1,button2;
	JLabel label4;
	
	//protected static Employee e = new Employee();
	
	public static void main(String[] args){
		dao.connect();	
		
		rs= dao.getAllEmployees();
		
		new SearchEmployees();
		
	}
	
	public SearchEmployees(){
		
		this.setSize(400, 400);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);
				 
		this.setLocation(xPos, yPos);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		this.setTitle("Search Employees");
				
		JPanel thePanel = new JPanel();
		
		thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.Y_AXIS));
		
		button2 = new JButton("Register Employee");
		ListenForButton lForButton = new ListenForButton();
		button2.addActionListener(lForButton);
		thePanel.add(button2);
		
		rs= dao.getAllEmployees();
		
		try{
			while(rs.next()){
				   String empName= rs.getString("EmpName");
				   int empID = rs.getInt("empId");
				   empIDs.add(empID);
				   emps.add("ID: "+empID+  ".........Name: "+empName);
			}
		}catch(Exception ex){
			
		}
		
		
	    JList list = new JList(emps.toArray());
	    thePanel.add(new JScrollPane(list));
	    
		button1 = new JButton("Submit");
		button1.addActionListener(lForButton);
		thePanel.add(button1);
		
		
		list.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
            	empID =list.getSelectedIndex();
                System.out.println("Hello you selected me!  "+
                list.getSelectedIndex());
            }
        });
		
		label4 = new JLabel("");
		thePanel.add(label4);
		
		System.out.println(empID);
		
		this.add(thePanel);
		this.setVisible(true);
	}
	
private class ListenForButton implements ActionListener{
		
		// This method is called when an event occurs
		
		public void actionPerformed(ActionEvent e){
			
			if(e.getSource() == button1){
				//SearchEmployees.e.setEmpID(empID);
				System.out.println(empID);
				String[] args = new String[] {""+empID};
			    Employee.main(args);
			}
		}
}
	
	
	private class ListenForWindow implements WindowListener{

		// Called when window is the active window
		
		public void windowActivated(WindowEvent e) {
			System.out.println("Window1 Activated");
			//setVisible(false);
		}

		// Called when window is closed using dispose
		// this.dispose(); can be used to close a window
		
		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		// Called when the window is closed from the menu
		
		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		// Called when a window is no longer the active window
		
		public void windowDeactivated(WindowEvent e) {
			System.out.println("Window1 deActivated");
			//setVisible(false);
		}

		// Called when the window goes from minimized to a normal state
		
		public void windowDeiconified(WindowEvent arg0) {
			System.out.println("Window1 de iconified");
			//setVisible(true);
		}

		// Called when the window goes from normal to a minimized state
		
		public void windowIconified(WindowEvent arg0) {
			System.out.println("Window1 iconified");
			//setVisible(true);
		}

		// Called when the window is first created
		
		public void windowOpened(WindowEvent arg0) {
			System.out.println("Window1 opened");
			//setVisible(false);
		}
		
	}

}
