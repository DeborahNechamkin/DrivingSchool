
import java.awt.GridLayout;
import java.sql.Connection;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LogInPanel extends JPanel
{
	private JTextField userNameTXT;
	private JPasswordField passwordTXT;

	public LogInPanel()
	{
		this.setLayout(new GridLayout(2, 2));
		this.add(new JLabel("User Name:"));
		userNameTXT = new JTextField(20);
		this.add(userNameTXT);
		this.add(new JLabel("Password"));
		passwordTXT = new JPasswordField(20);
		this.add(passwordTXT);
		this.setVisible(true);

	}

	public String getUserName()
	{
		try
		{
			return userNameTXT.getText();
		}
		catch (NullPointerException ex)
		{
			return null; // blank user name
		}
	}

	public char[] getPassword()
	{
		try
		{
			return passwordTXT.getPassword(); // is now in plain text
		}
		catch (NullPointerException ex)
		{
			return null;
		}
	}

}
