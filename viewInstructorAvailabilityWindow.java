

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class viewInstructorAvailabilityWindow extends JFrame
{
	Connection dbConnection;
	//JTextBox instructorChoice;
	JComboBox<String> instructorList_combo;
	
	public viewInstructorAvailabilityWindow(Connection dbConnection)
	 {
		 this.dbConnection = dbConnection;
		 
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
}
