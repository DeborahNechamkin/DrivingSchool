import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class MakePaymentWindow extends JFrame
{
	private Connection dbConnection;
	private JTextField clientText;
	private JFrame window;
	private JLabel resultsLabel;

	public MakePaymentWindow(Connection dbConnection)
	{
		this.dbConnection = dbConnection;
		this.setTitle("Make Payment");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window = this;

		this.setLayout(new GridLayout(3, 2));

		this.add(new JLabel("Lesson ID"));
		clientText = new JTextField(8);
		this.add(clientText);

		this.add(new GenericButtonPanel(this, dbConnection, new MakePaymentActionListener()));

		this.add(new JLabel(""));
		resultsLabel = new JLabel();
		this.add(resultsLabel);
		this.pack();
		this.setVisible(true);

	}

	private class MakePaymentActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				String sql = "select payment from completedlesson where lessonid = ?";
				PreparedStatement stmt = dbConnection.prepareStatement(sql);
				stmt.setString(1, clientText.getText());
				ResultSet results = stmt.executeQuery();
				
				results.next();
				if (results.getDouble(1)!=0)
				{
					resultsLabel.setText("Payment Unsuccesful");
					return;
				}
				
				
				sql = "update completedlesson set payment = 45 where lessonid = ?";
				stmt = dbConnection.prepareStatement(sql);
				stmt.setString(1, clientText.getText());
				stmt.execute();

				resultsLabel.setText("Payment Succesful");
				window.setVisible(true);
				return;
			}
			catch (SQLException ex)
			{
				resultsLabel.setText("Payment Unsuccesful");
				JOptionPane.showMessageDialog(null, "database issue - contact IT. " + ex.getMessage());
			}
		}

	}
}
