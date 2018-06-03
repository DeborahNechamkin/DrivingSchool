

import java.awt.BorderLayout;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import java.sql.SQLException;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class MainWindow extends JFrame
{
	private Connection dbConnection;

	public MainWindow()
	{
		// connect to sql server database
		this.setTitle("Driving School");
		this.setLayout(new BorderLayout());
		this.setSize(800, 800);
		// String sql;
		// sql = "jdbc:sqlserver://localhost:1433;user=drivingSchoolLogin;password=dr!v!ngSc00lLogin;databaseName=DrivingSchoolDB";
		// sql =
		// "jdbc:sqlserver://192.168.1.171:1433;instance=DESKTOP-7BF05J8\\SQLEXPRESS;user=drivingSchoolLogin;password=dr!v!ngSc00lLogin;databaseName=DrivingSchoolDB";

		new LogInWindow();
		/*
		 * try {
		 * dbConnection = DriverManager.getConnection(sql);
		 * setUpMenu();
		 * this.setVisible(true);
		 * //new AssignInstructorFrame(dbConnection);
		 * }
		 * catch(SQLException ex) {
		 * JOptionPane.showMessageDialog(null, "cant connection to server -shutting down " + ex.getMessage());
		 * System.exit(1);
		 * }
		 */
		this.addWindowListener(new CloseMainWindowListener());
	}

	private class CloseMainWindowListener extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			try
			{
				dbConnection.close(); // close connection to the database
				System.exit(0);
			}
			catch (SQLException ex)
			{
				System.exit(1); // end the application
			}
		}
	}

	public static void main(String[] args)
	{
		new MainWindow();

	}

}
