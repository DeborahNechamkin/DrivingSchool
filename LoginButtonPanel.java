

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LoginButtonPanel extends JPanel
{
	private JButton okButton;
	private JButton exitButton;
	private JButton backButton;
	private Connection dbConnection;
	private LogInWindow parentWindow;
	private LogInPanel loginPanel;
	private LoginRadioButtonPanel loginRadioButtonPanel;

	public LoginButtonPanel(LogInWindow window, LogInPanel loginPanel, LoginRadioButtonPanel loginRadioButtonPanel)
	{
		this.parentWindow = window;
		this.loginPanel = loginPanel;
		this.loginRadioButtonPanel = loginRadioButtonPanel;

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		okButton = new JButton("OK");
		exitButton = new JButton("EXIT");
		backButton = new JButton("BACK");

		this.add(okButton);
		this.add(backButton);
		this.add(exitButton);

		dbConnection = null;

		okButton.addActionListener(new ButtonActionListener());
		exitButton.addActionListener(new ButtonActionListener());
		backButton.addActionListener(new ButtonActionListener());
	}

	public Connection getDatabaseConnection()
	{
		return dbConnection;
	}

	private class ButtonActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent event)
		{
			String userType = loginRadioButtonPanel.getUserChoice();

			JButton theButton = (JButton) event.getSource();
			if (theButton == okButton)
			{
				String username, password;
				username = loginPanel.getUserName();
				password = new String(loginPanel.getPassword());

				final String dbURL = "jdbc:sqlserver://192.168.1.171:1433;instance=DESKTOP-7BF05J8\\SQLEXPRESS;databaseName=DrivingSchoolDB";
				try
				{
					dbConnection = DriverManager.getConnection(dbURL, username, password);
					System.out.println(dbConnection);

					if (userType.equalsIgnoreCase("client"))
					{
						parentWindow.close();
						new ClientMenuWindow(dbConnection);

					}
					else if (userType.equalsIgnoreCase("instructor"))
					{
						parentWindow.close();
						new InstructorMenuWindow(dbConnection);
					}

					else if (userType.equalsIgnoreCase("manager"))
					{
						parentWindow.close();
						new ManagerMenuWindow(dbConnection);
					}

				}
				catch (SQLException sql)
				{
					JOptionPane.showMessageDialog(null, sql.getMessage());
					dbConnection = null;
				}
			}
			
			if (theButton == exitButton)
			{
				System.out.println("exiting the system");
				System.exit(0);
			}
			if (theButton == backButton)
			{
				parentWindow.dispose();
			}
		}
	}
}
