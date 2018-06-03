

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.*;

@SuppressWarnings("serial")
public class ClientMenuWindow extends JFrame
{
	Connection dbConnection;
	JButton viewInstructorAvailabilityButton;
	JButton scheduleLessonButton;
	JButton viewTestResultsButton;
	JButton viewBalanceButton;
	JButton makePaymentButton;
	Box verticalBox;

	public ClientMenuWindow(Connection dbConnection)
	{
		this.dbConnection = dbConnection;	
		this.setUpWindow();
		this.setVisible(true);
	}

	private void setUpWindow()
	{
		this.setTitle("Client Menu Window");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(5, 5);

		viewInstructorAvailabilityButton = new JButton("View Instructor Availability");
		scheduleLessonButton = new JButton("Schedule Lessons");
		viewTestResultsButton = new JButton("View Test Results");
		viewBalanceButton = new JButton("View Balance");
		makePaymentButton = new JButton("Make Payment");
		
		verticalBox = Box.createVerticalBox();
		
		assignButtons(viewInstructorAvailabilityButton);
		assignButtons(scheduleLessonButton);
		assignButtons(viewTestResultsButton);
		assignButtons(viewBalanceButton);
		assignButtons(makePaymentButton);
		
		this.add(verticalBox);
	/*	
		JMenuBar menuBar = new JMenuBar();
		JMenu menu;
		menu = new JMenu("View Instructor Availability");
		menu.addActionListener(new ClientMenuListener());
		
		menuBar.add(new JMenu("Schedule Lessons"));
		menuBar.add(new JMenu("View Test Results"));
		menuBar.add(new JMenu("View Balance"));
		menuBar.add(new JMenu("Make Payment"));
		
		this.setJMenuBar(menuBar);
		*/
	}
	
	public void assignButtons(JButton button)
	{
		verticalBox.add(button);
		button.addActionListener(new ClientMenuListener());
	}

	private class ClientMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {

			JButton theButton = (JButton)event.getSource();
			
			if (theButton == viewInstructorAvailabilityButton) {
				new viewInstructorAvailabilityWindow(dbConnection);
			}
			else if (theButton == scheduleLessonButton) {		
				//new scheduleLessonWindow(dbConnection);
			}
			else if (theButton == viewTestResultsButton) {
				//new viewTestResultsWindow(dbConnection);
			}
			else if (theButton == viewBalanceButton) {
				//new viewBalanceWindow(dbConnection);
			}
			else if (theButton == makePaymentButton) {
				//new makePaymentWindow(dbConnection);
			}	
		}	
	}
}
