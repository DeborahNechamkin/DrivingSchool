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
			String sql = "select StartTime, EndTime from DrivingLesson where instructorID = ? and LessonDate = ? ";
			try
			{
				PreparedStatement stmt = dbconnection.prepareStatement(sql);
				stmt.setInt(1, Integer.parseInt(id.getText()));
				
				// converts to timestamp and sets date
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Long date = sdf.parse(dateText.getText()).getTime();
				java.sql.Timestamp timestamp = new java.sql.Timestamp(date);
				stmt.setTimestamp(2, timestamp);

				ResultSet results = stmt.executeQuery();
				results.next();
				StringBuilder info = new StringBuilder();
				//System.out.println(results.);
				System.out.println(results.next());
				while (results.next())
				{
					// how to retrieve the data from the result set.
					System.out.println("hi" + results.getString(1));
					info.append(results.getString(2) + "-");
					info.append(results.getTime(1) + ". ");
				}
				System.out.println(info.toString());
				resultsLabel.setText(info.toString());
				setVisible(true);
			}
			catch (SQLException | ParseException e)
			{
				JOptionPane.showMessageDialog(null, "database issue - contact IT. " + e.getMessage());

			}
		}

	}
}
