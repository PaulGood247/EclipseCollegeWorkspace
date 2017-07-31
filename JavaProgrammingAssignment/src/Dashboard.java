import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Dashboard extends JFrame {
	
	static DAO dao = new DAO();
	JButton button1,button2;
	
	public static void main(String[] args){
		dao.connect();	
		
		new Dashboard();
		
	}
	
	public Dashboard(){
			
			this.setSize(250, 100);
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dim = tk.getScreenSize();
			int xPos = (dim.width / 2) - (this.getWidth() / 2);
			int yPos = (dim.height / 2) - (this.getHeight() / 2);
					 
			this.setLocation(xPos, yPos);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					
			this.setTitle("Dashboard");
					
			JPanel thePanel = new JPanel();
			
			thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.Y_AXIS));
			
			ListenForButton lForButton = new ListenForButton();
			
			button1 = new JButton("Employer Sign-in");
			button1.addActionListener(lForButton);
			thePanel.add(button1);
			
			button2 = new JButton("Employee Sign-in");
			button2.addActionListener(lForButton);
			thePanel.add(button2);
			
			this.add(thePanel);
			this.setVisible(true);
	}
	
	private class ListenForButton implements ActionListener{
		// This method is called when an event occurs
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == button1){
			
				JPanel panel = new JPanel();
				JLabel label = new JLabel("Enter a password:");
				JPasswordField pass = new JPasswordField(10);
				panel.add(label);
				panel.add(pass);
				String[] options = new String[]{"OK"};
				int option = JOptionPane.showOptionDialog(null, panel, "Enter password",
				                         JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE,
				                         null, options, options[0]);
				
				if(option == JOptionPane.OK_OPTION) // pressing OK button
				{
				    char[] password = pass.getPassword();
				    if(dao.checkAdminPassword(new String(password))){
				    	SearchEmployees.main(null);
				    	dispose();
				    }
				    System.out.println("Your password is: " + new String(password));
				}
			}else if(e.getSource() == button2){
			
				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				JLabel label1 = new JLabel("Enter an EmpoyeeID:");
				panel.add(label1);
				JTextField textField = new JTextField("", 15);
				panel.add(textField);
				JLabel label2 = new JLabel("Enter a password:");
				JPasswordField pass = new JPasswordField(10);
				panel.add(label2);
				panel.add(pass);
				String[] options = new String[]{"OK"};
				int option = JOptionPane.showOptionDialog(null, panel, "Enter password",
				                         JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE,
				                         null, options, options[0]);
				
				if(option == JOptionPane.OK_OPTION) // pressing OK button
				{
				    String empID = textField.getText();
					char[] password = pass.getPassword();
				    if(dao.checkEmpPassword(empID, new String(password))){
				    	String[] args = new String[] {""+empID};
				    	Emp.main(args);
				    	dispose();
				    }
				    System.out.println("Your password is: " + new String(password));
				}
			}
			
		}
	}
}
