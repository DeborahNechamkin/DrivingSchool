import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeParseException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class viewTestResultsWindow extends JFrame
{
	private Connection dbConnection;
	private JTextField clientText;
	private JFrame window;
	private JLabel resultsLabel;

	public viewTestResultsWindow(Connection dbConnection)
	{
		this.dbConnection = dbConnection;
		this.setTitle("View Test Results");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window = this;

		this.setLayout(new GridLayout(3, 2));

		this.add(new JLabel("Client ID"));
		clientText = new JTextField(8);
		this.add(clientText);

		this.add(new GenericButtonPanel(this, dbConnection, new ViewTestResultsActionListener()));

		this.add(new JLabel(""));
		resultsLabel = new JLabel();
		this.add(resultsLabel);
		this.pack();
		this.setVisible(true);

	}

	private class ViewTestResultsActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				String sql = "select roadtestdate, result from roadtest where clientid = ?";
				PreparedStatement stmt = dbConnection.prepareStatement(sql);
				stmt.setString(1, clientText.getText());
				ResultSet results = stmt.executeQuery();
				StringBuilder builder = new StringBuilder();

				while (results.next())
				{
					builder.append(results.getString("roadtestdate") + ": " + results.getString("result") + ".   ");
				}
				
				resultsLabel.setText(builder.toString());
				window.setVisible(true);
				return;
			}
			catch (SQLException ex)
			{
				JOptionPane.showMessageDialog(null, "database issue - contact IT. " + ex.getMessage());
			}
			catch(DateTimeParseException d){
				JOptionPane.showMessageDialog(null,"Please enter a valid Date Format YYYY-MM-DD");
			}
		}

	}
}
