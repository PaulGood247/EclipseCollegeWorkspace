import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class History extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static DAO dao = new DAO();
	static ResultSet rs;
	ArrayList<String> shifts = new ArrayList<String>();
	
	JTextField textField1;
	JTextField textField2;
	JTextField textField3;
	JTextField textField4;
	JButton button1,button2,button3;
	
	JLabel label1;
	JLabel label2;
	JLabel label4;
	
	static int empID;
	static String empName;
	static int shiftID;
	//protected static Employee e = new Employee();
	
	static boolean employee=false;
	
	public static void main(String[] args){
		dao.connect();	
		
		empID= Integer.parseInt(args[0]);
		empName= args[1];
		if(args.length ==3){
			employee=true;
		}
		
		rs= dao.getWorkHistory(empID);
		
		new History();
		
	}
	
	public History(){
		
		this.setSize(500, 300);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);
				 
		this.setLocation(xPos, yPos);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		this.setTitle("Work History");
				
		JPanel thePanel = new JPanel();
		
		thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.Y_AXIS));
		
		ListenForButton lForButton = new ListenForButton();
		
		label1 = new JLabel("EmployeeID: "+empID);
		thePanel.add(label1);
		
		label2 = new JLabel("Employee Name: "+empName);
		thePanel.add(label2);
		
		try{
			while(rs.next()){
				 int tempShiftID = rs.getInt("shiftID");
				 String day = rs.getString("day");
				 String start = rs.getString("start");
				 String end = rs.getString("end");
				 float breakTime = rs.getFloat("break");
				 boolean paid =rs.getBoolean("paid");
				 boolean confirmedByEmployer =rs.getBoolean("confirmedByEmployer");
				 shifts.add("Shift: "+tempShiftID+ "  Day:"+day + " Duration:"+ start+"-"+end+" Break:"+breakTime+" Paid:"+paid+" Confirmed by Employer: "+confirmedByEmployer);
			}
		}catch(Exception ex){
			
		}
		
		
	    JList list = new JList(shifts.toArray());
	    thePanel.add(new JScrollPane(list));
	    
	    if(!employee){
			button1 = new JButton("Set to Paid");
			button1.addActionListener(lForButton);
			thePanel.add(button1);
			
			button3 = new JButton("Confirm these hours");
			button3.addActionListener(lForButton);
			thePanel.add(button3);
			
			button2 = new JButton("Cancel");
			button2.addActionListener(lForButton);
			thePanel.add(button2);
	    }
		
		list.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
            	//empID =list.getSelectedIndex()+1;
            	String temp = (String) list.getSelectedValue();
            	String s= temp.substring(6,10);
            	String r = s.trim();
            	shiftID = Integer.parseInt(r);
            }
        });
		
		label4 = new JLabel("");
		thePanel.add(label4);
		
		this.add(thePanel);
		this.setVisible(true);
	}
	
private class ListenForButton implements ActionListener{
		// This method is called when an event occurs
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == button1){
				System.out.println(shiftID);
				if(shiftID!=0){
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want set shift "+shiftID+" to paid?");
					if(dialogResult == JOptionPane.YES_OPTION){
						try {
							dao.setPaid(shiftID);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					String[] args = new String[] {""+empID, empName};
					History.main(args);
				}
			}else if(e.getSource() == button3){
				System.out.println(shiftID);
				if(shiftID!=0){
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want set shift "+shiftID+" to confirmed?");
					if(dialogResult == JOptionPane.YES_OPTION){
						try {
							dao.setConfirmed(shiftID);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					String[] args = new String[] {""+empID, empName};
					History.main(args);
				}
			}else if(e.getSource() == button2){
				dispose();
			}
			
		}
}

}
