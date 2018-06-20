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
	private Double lessonPrice;

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
		
		lessonPrice = 45.0;

		this.add(new JLabel(""));
		
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
				
				String sql = "select count(payment) from completedlesson inner join drivinglesson "
						+ "on lessonid = drivinglessonid"
						+ " where clientid = ? and payment=0";
				PreparedStatement stmt = dbConnection.prepareStatement(sql);
				stmt.setString(1, clientText.getText());
				ResultSet results = stmt.executeQuery();
				
				results.next();
				resultsLabel.setText("Balance: " + String.valueOf(results.getDouble(1)*lessonPrice));
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
