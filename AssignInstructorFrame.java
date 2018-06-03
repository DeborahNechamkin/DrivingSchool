

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class AssignInstructorFrame extends JFrame{
	private Connection dbConnection;
    private AssignInstructorPanel assignInstructorPanel;
    private JButton okButton;
    private JButton cancelButton;
    
	public AssignInstructorFrame(Connection dbConnection) {
		//save reference to db back end
		this.dbConnection = dbConnection;
		//set up the frame
		this.setTitle("Request Instructor Form");
		this.setLayout(new BorderLayout());
		this.setSize(700, 700);
		this.setLocationRelativeTo(null); //start default location
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		
	    this.add(new JLabel ("Must Enter Client ID and select an  Instructor "),BorderLayout.NORTH);
	   
	    assignInstructorPanel = new AssignInstructorPanel(dbConnection);
	    
	    this.add(assignInstructorPanel, BorderLayout.CENTER);
	    
	    //set up the buttons
	    Box theBox = Box.createHorizontalBox();
	    okButton = new JButton("OK");
	    okButton.addActionListener(new OkButtonListener());
	    theBox.add(okButton);
	    cancelButton = new JButton("CANCEL");
	    cancelButton.addActionListener(new CancelButtonListener(this));
	    theBox.add(cancelButton);
	    //now add the box to the frame
	    this.add(theBox,BorderLayout.SOUTH);
	    
	    this.setVisible(true);
	}
	
	private class OkButtonListener implements ActionListener{
         
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int qty;
			Integer clientID, instructorID;
			String fname, lname;
			String sql;
			PreparedStatement pstmt;
			ResultSet resultset;
			//retrieve user input from the panel
			clientID = assignInstructorPanel.getClientID();
			if (clientID == null) {
				JOptionPane.showMessageDialog(null, "must provide client id");
				return;   //end method
			}
			instructorID = assignInstructorPanel.getInstructorID();
		
			try {
			if (instructorID == null) {
				
					JOptionPane.showMessageDialog(null,"must provide instructor data");
					
					return;  //exit this method
			     }
			
		
			
			//now verify if the instructor is already overloaded
			
			sql = "select count(*) from client where instructorID = ? ";
			
			PreparedStatement stmt = dbConnection.prepareStatement(sql);
			stmt.setInt(1,instructorID);
			resultset = stmt.executeQuery();
			if (resultset.next() ) {
				//other clients have chosen this instructor 
				qty = resultset.getInt(1); //find out how many
				System.out.println("quantity of clients who have chosen this instructor " + qty);
				if (qty >3) {
					System.out.println("More than three clients have already ");
					//more than three clients can't assign the instructor 
					JOptionPane.showMessageDialog(null, "this instructor not available at this time");
				    resultset.close();
				    stmt.close();
					return;
				}
			}
			
			//set up a Callable statement that can execute the stored procedure
			
			//the procedure returns a value
			 CallableStatement cStmt = dbConnection.prepareCall("{?=call usp_RequestInstructor(?, ?,?,?)}");
			 //register the output parameter
			 cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			 
			 //set up the input parameters
		      System.out.println("ready to pass client " + clientID + " instructor " + instructorID);			 
			 cStmt.setInt(2,clientID);
             cStmt.setInt(3, instructorID);
             cStmt.setString(4, null);  //first name not available
             cStmt.setString(5, null);   //last name not available
             //execute the procedure
             try {
                cStmt.execute();
                int result = cStmt.getInt(1);
               
                if (result == 0) {
			      //display results to user
			      JOptionPane.showMessageDialog(null, "request processed, instructor assigned to client");
			      cStmt.close();
			      resultset.close();
			       stmt.close();
               }
                else {
                	JOptionPane.showMessageDialog(null,"request not processed");
                }
             }
             catch(SQLException ex2) {
            	 JOptionPane.showMessageDialog(null,ex2.getMessage() );
             }
			}  //end try
			catch(SQLException ex) {
				 JOptionPane.showMessageDialog(null, ex.getMessage());
			}
			}//end actionPerformed() method
			
		}//end OKButtonListener
	
	  private class CancelButtonListener implements ActionListener{
		   private AssignInstructorFrame parentWindow;
             public CancelButtonListener (AssignInstructorFrame window) {
            	 parentWindow = window;
             }
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//close the window
				parentWindow.dispose();
				
			}
		  
	  }
		
}


