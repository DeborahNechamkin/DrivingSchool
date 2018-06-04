import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;
import javax.swing.*;

@SuppressWarnings("serial")
public class ScheduleLessonWindow extends JFrame
{
	private Connection dbConnection;
	private JComboBox<String> instructorList_combo;
	private JTextField dateText;
	private JTextField timeText;
	private JTextField clientText;
	private JFrame window;

	public ScheduleLessonWindow(Connection dbConnection)
	{
		this.dbConnection = dbConnection;
		this.setTitle("Schedule Lesson");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window = this;

		this.setLayout(new GridLayout(5, 3));

		this.add(new JLabel("Client ID"));
		clientText = new JTextField(8);
		this.add(clientText);

		setupInstructorList();
		this.add(new JLabel("Instructor"));
		this.add(instructorList_combo);

		dateText = new JTextField(10);
		timeText = new JTextField(8);
		this.add(new JLabel("Date"));
		this.add(dateText);
		this.add(new JLabel("Time"));
		this.add(timeText);

		this.add(new GenericButtonPanel(this, dbConnection, new ScheduleLessonActionListener()));
		this.pack();
		this.setVisible(true);

	}

	private void setupInstructorList()
	{

		String sql = "select employeeID , emp_fname, emp_lname from employee"
				+ " inner join instructor on employee.employeeid = " + " instructor.instructorid";
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

	private class ScheduleLessonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			try
			{
				PreparedStatement pstmt = dbConnection
						.prepareStatement("insert into DrivingLesson (DrivingLessonID, InstructorID, "
								+ "ClientID, CarVIN, LessonDate, StartTime, EndTime) values (?, ?, ?, ?, ?, ?, ?)");

				pstmt.setInt(1, 3016);
				pstmt.setInt(2, getInstructorID());
				pstmt.setInt(3, Integer.parseInt(clientText.getText()));
				pstmt.setString(4, "123123126");

				// converts to timestamp and sets date
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Long date = sdf.parse(dateText.getText()).getTime();
				java.sql.Timestamp timestamp = new java.sql.Timestamp(date);
				pstmt.setTimestamp(5, timestamp);

				// converts time to milliseconds
				sdf = new SimpleDateFormat("HH:mm");
				sdf.setTimeZone(TimeZone.getTimeZone("EST"));
				Long time = sdf.parse(timeText.getText()).getTime();

				timestamp = new java.sql.Timestamp(time);
				System.out.println(timestamp.toString());
				pstmt.setTimestamp(6, timestamp);

				// sets end time at an hour after start time
				timestamp = new java.sql.Timestamp(time + 3600000);
				pstmt.setTimestamp(7, timestamp);

				pstmt.execute(); // execute insert statement
				System.out.println("executed the update statement");
				pstmt.close();
				window.dispose();
				new ClientMenuWindow(dbConnection);

			}
			catch (SQLException | ParseException ex)
			{
				JOptionPane.showMessageDialog(null, "database issue - contact IT. " + ex.getMessage());
			}
		}
	}
}