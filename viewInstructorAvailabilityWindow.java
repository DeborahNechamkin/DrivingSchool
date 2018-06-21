import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class viewInstructorAvailabilityWindow extends JFrame
{
	private Connection dbConnection;
	private JComboBox<String> instructorList_combo;
	private JTextField dateText;
	private JTextField timeText;
	private JFrame window;
	private JLabel resultsLabel;

	public viewInstructorAvailabilityWindow(Connection dbConnection)
	{
		this.dbConnection = dbConnection;
		this.setTitle("View Availability");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window = this;

		this.setLayout(new GridLayout(5, 2));
		setupInstructorList();
		this.add(new JLabel("Instructor"));
		this.add(instructorList_combo);

		dateText = new JTextField(10);
		timeText = new JTextField(8);
		this.add(new JLabel("Date"));
		this.add(dateText);
		this.add(new JLabel("Time"));
		this.add(timeText);

		this.add(new GenericButtonPanel(this, dbConnection, new ViewAvailabilityActionListener()));

		this.add(new JLabel(""));
		resultsLabel = new JLabel();
		this.add(resultsLabel);
		this.pack();
		this.setVisible(true);

	}

	private void setupInstructorList()
	{

		String sql = "select employeeID , emp_fname, emp_lname from employee"
				+ " inner join instructor on employee.employeeid = instructor.instructorid";
		try
		{
			Statement stmt = dbConnection.createStatement();

			ResultSet results = stmt.executeQuery(sql);
			ArrayList<String> instructorData = new ArrayList<String>();

			StringBuilder info = new StringBuilder();
			while (results.next())
			{
				info.append(results.getInt("employeeid") + " ");
				info.append(results.getString("emp_fname") + " ");
				info.append(results.getString("emp_lname"));
				instructorData.add(info.toString());
				info.delete(0, info.length()); // reset the info
			}

			String[] temp = new String[instructorData.size()];

			instructorList_combo = new JComboBox<String>(instructorData.toArray(temp));
		}
		catch (SQLException ex)
		{
			JOptionPane.showMessageDialog(null, "database issue - contact IT. " + ex.getMessage());
			instructorList_combo = null;
		}
	}

	public Integer getInstructorID()
	{
		try
		{
			if (instructorList_combo == null)
			{
				return null;
			}
			String instructorData = (String) instructorList_combo.getSelectedItem();
			String[] data = instructorData.split("\\s+");

			return Integer.valueOf(data[0]);
		}
		catch (Exception ex)
		{
			return null;
		}
	}

	private class ViewAvailabilityActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				String sql = "select fromdate, todate from instructorvacation where instructorid = ?";
				PreparedStatement stmt = dbConnection.prepareStatement(sql);
				stmt.setInt(1, getInstructorID());

				ResultSet results = stmt.executeQuery();

				// checks if instructor will be on vacation
				LocalDate date = LocalDate.parse(dateText.getText());
				while (results.next())
				{
					LocalDate fromDate = LocalDate.parse(results.getString("fromdate"));
					LocalDate toDate = LocalDate.parse(results.getString("todate"));
					if (!date.isBefore(fromDate) && !date.isAfter(toDate))
					{
						resultsLabel.setText("Instructor Is Unavailable");
						window.setVisible(true);
						return;
					}
				}

				// checks if the instructor already has a lesson for that day/time
				sql = "select lessondate, starttime, endtime from drivinglesson where instructorid = ?";
				stmt = dbConnection.prepareStatement(sql);
				stmt.setInt(1, getInstructorID());
				results = stmt.executeQuery();

				LocalTime time = LocalTime.parse(timeText.getText());
				while (results.next())
				{
					LocalDate lessonDate = LocalDate.parse(results.getString("lessondate"));
					LocalTime startTime = LocalTime.parse(results.getString("starttime"));
					LocalTime endTime = LocalTime.parse(results.getString("endtime"));
					if (date.equals(lessonDate) && !time.isBefore(startTime) && time.isBefore(endTime))
					{
						resultsLabel.setText("Instructor Is Unavailable");
						window.setVisible(true);
						return;
					}
				}

				resultsLabel.setText("Instructor Is Available");
				window.setVisible(true);

			}
			catch (SQLException ex)
			{
				JOptionPane.showMessageDialog(null, "database issue - contact IT. " + ex.getMessage());
			}
			catch(DateTimeParseException d){
				JOptionPane.showMessageDialog(null,"Please enter a valid Date/Time Format YYYY-MM-DD HH:MM");
			}
		}

	}

}