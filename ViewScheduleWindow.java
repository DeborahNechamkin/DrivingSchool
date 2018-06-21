import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;

@SuppressWarnings("serial")
public class ViewScheduleWindow extends JFrame
{
	Connection dbconnection;
	private JTextField dateText;
	private JTextField id;
	private JFrame window;
	private JLabel resultsLabel;

	public ViewScheduleWindow(Connection dbConnection)
	{
		this.dbconnection = dbConnection;
		this.setTitle("View Schedule");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window = this;

		this.setLayout(new GridLayout(8, 1));
		this.add(new JLabel("Instructor ID:"));
		id = new JTextField(8);
		this.add(id);
		this.add(new JLabel("Pick your date:"));
		dateText = new JTextField(10);
		this.add(dateText);
		this.add(new GenericButtonPanel(this, dbConnection, new ViewScheduleActionListener()));

		this.add(new JLabel(""));
		resultsLabel = new JLabel();
		this.add(resultsLabel);
		this.pack();
		this.setVisible(true);
	}

	private class ViewScheduleActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			String sql = "select lessondate, drivinglessonid, starttime, endtime from DrivingLesson where instructorID = ?";
			try
			{
				PreparedStatement stmt = dbconnection.prepareStatement(sql);
				stmt.setInt(1, Integer.parseInt(id.getText()));

				ResultSet results = stmt.executeQuery();
				results.next();
				StringBuilder info = new StringBuilder();

				LocalDate date = LocalDate.parse(dateText.getText());

				boolean found = false;
				while (results.next())
				{
					LocalDate databaseDate = LocalDate.parse(results.getString("LessonDate"));
					if (databaseDate.equals(date))
					{
						info.append("\nLesson ID: ");
						info.append(results.getInt("DrivingLessonID") + ": ");
						info.append(results.getTime("StartTime") + "-");
						info.append(results.getTime("EndTime"));
						found = true;
					}
				}
				resultsLabel.setText(info.toString());
				
				if (found==false)
				{
					resultsLabel.setText("Instructor has no lessons today");
				}
				
				setVisible(true);

			}
			catch (SQLException e)
			{
				JOptionPane.showMessageDialog(null, "database issue - contact IT. " + e.getMessage());
			}
			catch (DateTimeParseException e)
			{
				JOptionPane.showMessageDialog(null, "Please enter proper Date format YYYY-MM-DD");
			}
		}

	}
}
