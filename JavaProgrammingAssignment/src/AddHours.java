import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.event.*;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;


public class AddHours extends JFrame{
	
	JTextField textField1;
	JTextField textField2;
	JTextField textField3;
	JTextField textField4;
	JButton button1,button2;
	JLabel label;
	
	Date selectedDate;
	String start;
	String end;
	
	public UtilDateModel model;
	public JDatePanelImpl datePanel;
	public JDatePickerImpl datePicker;
	
	JSpinner timeSpinner;
	JSpinner timeSpinner1;
	
	static DAO dao=new DAO();
	
	static int empID;
	
	static boolean employee=false;

	public static void main(String[] args){
		empID=Integer.parseInt(args[0]);
		if(args.length ==2){
			employee=true;
		}
		new AddHours();
		dao.connect();
		
	}
	
	public AddHours(){
	
		this.setSize(300, 300);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);
				 
		this.setLocation(xPos, yPos);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		this.setTitle("Add Hours");
				
		JPanel thePanel = new JPanel();
		
		thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.Y_AXIS));
		
		JLabel label1 = new JLabel("");
		label1.setText("Date:");
		label1.setToolTipText("Please enter date");
		thePanel.add(label1);
		
		//textField1 = new JTextField("", 15);
		//thePanel.add(textField1);
		
		model = new UtilDateModel();
		//model.setDate(20,04,2014);
		// Need this...
		Properties p = new Properties();
		p.put("text.today", "");
		p.put("text.month", "");
		p.put("text.year", "");
		datePanel = new JDatePanelImpl(model, p);
		// Don't know about the formatter, but there it is...
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		thePanel.add(datePicker);
		
		JLabel label2 = new JLabel("");
		label2.setText("Start Time:");
		label2.setToolTipText("Please enter start time in the text field");
		thePanel.add(label2);
		
		//textField2 = new JTextField("", 15);
		//thePanel.add(textField2);
		timeSpinner = new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
		timeSpinner.setEditor(timeEditor);
		timeSpinner.setValue(new Date());
		
		thePanel.add(timeSpinner);
		
		
		JLabel label3 = new JLabel("");
		label3.setText("End Time:");
		label3.setToolTipText("Please enter end time in the text field");
		thePanel.add(label3);
		
		//textField3 = new JTextField("", 15);
		//thePanel.add(textField3);
		
		timeSpinner1 = new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor timeEditor1 = new JSpinner.DateEditor(timeSpinner1, "HH:mm");
		timeSpinner1.setEditor(timeEditor1);
		timeSpinner1.setValue(new Date());
		
		thePanel.add(timeSpinner1);
		
		JLabel label4 = new JLabel("");
		label4.setText("Break Duration (hrs):");
		label4.setToolTipText("Please enter break duration in the text field");
		thePanel.add(label4);
		
		textField4 = new JTextField("", 15);
		textField4.setText("1.5");
		thePanel.add(textField4);
		
		ListenForButton lForButton = new ListenForButton();
		
		button1 = new JButton("Submit");
		button1.addActionListener(lForButton);
		thePanel.add(button1);
		
		label = new JLabel("");
		thePanel.add(label);
		
		this.add(thePanel);
		this.setVisible(true);
	}
	
	private class ListenForButton implements ActionListener{
		
		// This method is called when an event occurs
		
		public void actionPerformed(ActionEvent e){
			
			if(e.getSource() == button1){
				try{
					float breakTime =Float.parseFloat(textField4.getText());
					selectedDate = (Date) datePicker.getModel().getValue();
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
					String date = format1.format(selectedDate);
					
					start=new SimpleDateFormat("HH:mm").format(timeSpinner.getValue());
					end=new SimpleDateFormat("HH:mm").format(timeSpinner1.getValue());
					
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to add shift: "+date+" stating at "+start + " ,ending at "+end +" with a break of "+breakTime+"hours");
					if(dialogResult == JOptionPane.YES_OPTION){
						if(employee){
							dao.addShift(empID, date, start, end , breakTime, false,false);
							String[] arguments = new String[] {""+empID};
							Emp.main(arguments);
						}else{
							dao.addShift(empID, date, start, end , breakTime, false,true);
							String[] arguments = new String[] {""+empID};
						    SearchEmployees.main(arguments);
						}
						
						dispose();
					}
				}catch(Exception ex){
					ex.printStackTrace();
					label.setText("Please make sure all fields are filled correctly!");	
				}
			}
			//dispose();
		}	
	}
	public class DateLabelFormatter extends AbstractFormatter {
		 
	    private String datePattern = "yyyy-MM-dd";
	    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
	     
	    @Override
	    public Object stringToValue(String text) throws ParseException {
	        return dateFormatter.parseObject(text);
	    }
	 
	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	            Calendar cal = (Calendar) value;
	            return dateFormatter.format(cal.getTime());
	        }
	         
	        return "";
	    }

	}
}

