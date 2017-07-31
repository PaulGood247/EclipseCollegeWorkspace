import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;

import java.awt.event.*;


public class RegisterEmployee extends JFrame{
	
	JTextField textField1;
	JTextField textField2;
	JTextField textField3;
	JTextField textField4;
	JButton button1,button2;
	JLabel label5;
	static DAO dao=new DAO();

	public static void main(String[] args){
		
		new RegisterEmployee();
		dao.connect();
	}
	
	public RegisterEmployee(){
	
		this.setSize(400, 400);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);
				 
		this.setLocation(xPos, yPos);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		this.setTitle("Register Employee");
				
		JPanel thePanel = new JPanel();
		
		thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.Y_AXIS));
		
		button2 = new JButton("Search Employees");
		ListenForButton lForButton = new ListenForButton();
		button2.addActionListener(lForButton);
		thePanel.add(button2);
		
		JLabel label1 = new JLabel("");
		label1.setText("Name:");
		label1.setToolTipText("Please enter name in the text field");
		thePanel.add(label1);
		
		textField1 = new JTextField("", 15);
		thePanel.add(textField1);
		
		JLabel label4 = new JLabel("");
		label4.setText("Password:");
		label4.setToolTipText("Please enter password in the text field");
		thePanel.add(label4);
		
		textField4 = new JTextField("", 15);
		thePanel.add(textField4);
		
		JLabel label2 = new JLabel("");
		label2.setText("Hourly Wage:");
		label2.setToolTipText("Please enter Hourly Wage in the text field");
		thePanel.add(label2);
		
		textField2 = new JTextField("", 15);
		thePanel.add(textField2);
		
		JLabel label3 = new JLabel("");
		label3.setText("Tax Rate:");
		label3.setToolTipText("Please enter Tax Rate in the text field");
		thePanel.add(label3);
		
		textField3 = new JTextField("", 15);
		thePanel.add(textField3);
		
		button1 = new JButton("Submit");
		button1.addActionListener(lForButton);
		thePanel.add(button1);
		
		label5 = new JLabel("");
		thePanel.add(label5);
		
		ListenForWindow lForWindow = new ListenForWindow();         
		this.addWindowListener(lForWindow);
		
		this.add(thePanel);
		this.setVisible(true);
	}
	
	private class ListenForButton implements ActionListener{
		
		// This method is called when an event occurs
		
		public void actionPerformed(ActionEvent e){
			
			if(e.getSource() == button1){
				try{
					String name =textField1.getText();
					float hourlyWage =Float.parseFloat(textField2.getText());
					float taxRate =Float.parseFloat(textField3.getText());
					String password =textField4.getText();
					
					dao.addEmployee(name, hourlyWage, taxRate, password);
					label5.setText("Successfully Added");
					SearchEmployees.main(null);
				}catch(Exception ex){
					ex.printStackTrace();
					label5.setText("Unseccessful! Please check information and try again!");
				}
			}else if(e.getSource() == button2){
				String[] arguments = new String[] {"123"};
			    SearchEmployees.main(arguments);
			}
			dispose();
		}
	}
	
	private class ListenForWindow implements WindowListener{

		// Called when window is the active window
		
		public void windowActivated(WindowEvent e) {
			System.out.println("Window2 Activated");
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
			System.out.println("Window2 deActivated");
			//setVisible(false);
		}

		// Called when the window goes from minimized to a normal state
		
		public void windowDeiconified(WindowEvent arg0) {
			System.out.println("Window2 de iconified");
			//setVisible(true);
		}

		// Called when the window goes from normal to a minimized state
		
		public void windowIconified(WindowEvent arg0) {
			System.out.println("Window2 iconified");
			//setVisible(true);
		}

		// Called when the window is first created
		
		public void windowOpened(WindowEvent arg0) {
			System.out.println("Window2 opened");
			//setVisible(false);
		}
		
	}
}

