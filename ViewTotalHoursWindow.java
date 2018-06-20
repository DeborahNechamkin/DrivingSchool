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
import java.util.Locale;
import java.util.TimeZone;

public class ViewTotalHoursWindow extends JFrame
{
	private Connection dbconnection;
	private JFrame window;
	private JTextField id;
	private JTextField start;
	private JTextField end;
	private JLabel resultsLabel;
	private int total = 0;

	public ViewTotalHoursWindow(Connection dbconnection)
	{
		this.dbconnection = dbconnection;

		this.setTitle("View Total Hours");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window = this;
		
		this.setLayout(new GridLayout(5, 1));
		this.add(new JLabel("Instructor ID:"));
		id = new JTextField(8);
		this.add(id);
		this.add(new JLabel("Start date:"));
		start = new JTextField(10);
		this.add(start);
		this.add(new JLabel("End date:"));
		end = new JTextField(10);
		this.add(end);
		
		this.add(new GenericButtonPanel(this, dbconnection, new ViewTotalActionListener()));
		
		this.add(new JLabel(""));
		resultsLabel = new JLabel();
		this.add(resultsLabel);
		this.pack();
		this.setVisible(true);
	}

	private class ViewTotalActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			String sql = "select count(drivinglessonid) from drivinglesson"
					+ " where instructorID = ? and LessonDate >= ? and LessonDate <= ?";
			int myid = Integer.parseInt(id.getText());
			Date startDate = null;
			Date endDate = null;
			try
			{
				PreparedStatement stmt = dbconnection.prepareStatement(sql);
				
				stmt.setInt(1, myid);
				
				// converts to timestamp and sets date
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Long date = sdf.parse(start.getText()).getTime();
				java.sql.Timestamp timestamp = new java.sql.Timestamp(date);
				stmt.setTimestamp(2, timestamp);
				
				date = sdf.parse(end.getText()).getTime();
				timestamp = new java.sql.Timestamp(date);
				stmt.setTimestamp(3, timestamp);
				

				ResultSet results = stmt.executeQuery();

				StringBuilder info = new StringBuilder();
				results.next();

				resultsLabel.setText(String.valueOf(results.getInt(1)) + " Hours");
				setVisible(true);

			}
			catch (SQLException | ParseException ex)
			{
				JOptionPane.showMessageDialog(null, "database issue - contact IT. " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
}
