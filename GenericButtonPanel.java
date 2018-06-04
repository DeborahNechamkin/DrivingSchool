
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GenericButtonPanel extends JPanel
{
	private JButton okButton;
	private JButton exitButton;
	private JButton backButton;
	Connection dbConnection;
	private JFrame parentWindow;

	public GenericButtonPanel(JFrame window, Connection dbConnection, ActionListener actionListener)
	{
		this.parentWindow = window;
		this.dbConnection = dbConnection;

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		okButton = new JButton("OK");
		exitButton = new JButton("EXIT");
		backButton = new JButton("BACK");

		this.add(okButton);
		this.add(backButton);
		this.add(exitButton);

		okButton.addActionListener(actionListener);
		exitButton.addActionListener(new ButtonActionListener());
		backButton.addActionListener(new ButtonActionListener());
	}

	private class ButtonActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent event)
		{
			JButton theButton = (JButton) event.getSource();

			if (theButton == exitButton)
			{
				System.out.println("exiting the system");
				System.exit(0);
			}
			else if (theButton == backButton)
			{
				parentWindow.dispose();
				new ClientMenuWindow(dbConnection);
			}
		}
	}

	
}
