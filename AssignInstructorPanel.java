

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AssignInstructorPanel extends JPanel{
    private JTextField clientID_txt;
    private JComboBox<String> instructorList_combo;
    private Connection dbConnection;
    private GridBagLayout layoutManager;
    
	public AssignInstructorPanel(Connection dbConnection) {
		//store reference to back end
		this.dbConnection = dbConnection;
		//set up the panel
		this.instructorList_combo = null;
		layoutManager = new GridBagLayout();
		this.setLayout(layoutManager);
		boolean result = setUpPanel();
		if (result) {
			   this.validate();
		       this.setVisible(true);
		}
		else {
			return ;
		}
	}
    
	private boolean setUpPanel() {
        GridBagConstraints constraints = new GridBagConstraints();
		//set constraints for the next to be added to the panel	
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx =0;  //first column
		constraints.gridy=0;   //first row
		constraints.gridwidth =1;
		
		JLabel client_lbl = new JLabel("ClientID:");
		//apply the GridBagConstraints to the label object
		layoutManager.setConstraints(client_lbl, constraints);
		//now add the label to the panel
		this.add(client_lbl);
		//set constraints for next object to be added to the panel
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx =1;  //second column
		constraints.gridy=0;   //first row
		constraints.gridwidth =1;
		clientID_txt = new JTextField(10);
		//apply the GridBagConstraints to the textfield object
		layoutManager.setConstraints(clientID_txt, constraints);
		//now add the textfield to the panel
		this.add(clientID_txt);
		
		setupInstructorList();
		if (instructorList_combo == null) {
			JOptionPane.showMessageDialog(null, "no instructors are available");
			return false;   //this shouldn't happen
		}
		
		//the list should span both columns
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2 ;   //span two columns
		constraints.gridx =0;  //first column
		constraints.gridy=1;   //first row
		//apply the constraints to the combolist
		layoutManager.setConstraints(instructorList_combo, constraints);
		//add the list to the panel
		this.add(instructorList_combo);
		return true;
	
		
	}
	
	private void setupInstructorList() {
		//retrieve current instructor id and first name and last name from the database
		String sql = "select employeeID , emp_fname, emp_lname from employee" +
		  " inner join instructor on employee.employeeid = " +
				" instructor.instructorid";
		try {
		      Statement stmt = dbConnection.createStatement();
		      //execute the query outlined above
		      ResultSet results = stmt.executeQuery(sql);
		      ArrayList<String> instructorData = new ArrayList<String>();
		      //now iterate through the results 
		      //extract the data and place into a list
		     
		      StringBuilder info =new StringBuilder();
		      while (results.next()) {
		    	  System.out.println("retrieved instructor data");
		    	  info.append(results.getInt("employeeid") + " " );
		          info.append( results.getString("emp_fname") + " " );
		          info.append( results.getString("emp_lname"));
		          System.out.println(info.toString());
		          instructorData.add(info.toString());
		          info.delete(0, info.length());  //reset the info
		      }
		      //set up the combo box based on this list
		      //instantiate an array of strings
		      String[] temp = new String[instructorData.size()];
		      //convert the arraylist of string to an array of string 
		      //instantiate the combobox populated with this array of strings
		      instructorList_combo = new JComboBox<String>(instructorData.toArray(temp));
		      this.add(instructorList_combo);
		      return ;
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "database issue - contact IT. " + ex.getMessage());
			instructorList_combo=null;
			return ;  //can't set up the list
		}
		
		
	}
	
	
	public Integer getClientID() {
		try {
			return Integer.valueOf(clientID_txt.getText());
		}
		catch(Exception ex) {
			System.out.println("no client data entered");
		    return null;   //not available or cant be converted to an integer
		}
	}
	
	public Integer getInstructorID() {
		try {
			if (instructorList_combo == null) {
				return null;
			}
			String instructorData = (String) instructorList_combo.getSelectedItem();
			//retrieve first item in the instructor data which is the instructorid
			String [] data = instructorData.split("\\s+"); //use spaces as the delimiter
			
			return Integer.valueOf(data[0]);
		}
		catch(Exception ex) {
		    return null;   //not available or cant be converted to an integer
		}
	}
	
	
	
	
}
