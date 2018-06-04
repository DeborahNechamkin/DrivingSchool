import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class ViewBalanceWindow extends JFrame
{
	private Connection dbConnection;
	private JTextField clientText;
	private JFrame window;
	private JLabel resultsLabel;

	public ViewBalanceWindow(Connection dbConnection)
	{
		this.dbConnection = dbConnection;
		this.setTitle("View Balance");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window = this;

		this.setLayout(new GridLayout(3, 2));

		this.add(new JLabel("Client ID"));
		clientText = new JTextField(8);
		this.add(clientText);

		this.add(new GenericButtonPanel(this, dbConnection, new ViewBalanceActionListener()));

		resultsLabel = new JLabel();
		this.add(resultsLabel);
		this.pack();
		this.setVisible(true);

	}

	private class ViewBalanceActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				String sql = "select balance from client where clientid = " + clientText.getText();
				Statement stmt = dbConnection.createStatement();
				ResultSet results = stmt.executeQuery(sql);
				
				resultsLabel.setText(results.getString("balance"));
				window.setVisible(true);
				return;
			}
			catch (SQLException ex)
			{
				JOptionPane.showMessageDialog(null, "database issue - contact IT. " + ex.getMessage());
			}
		}

	}
}
