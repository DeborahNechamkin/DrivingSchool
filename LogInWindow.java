

import java.awt.BorderLayout;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class LogInWindow extends JFrame
{

	private LogInPanel loginPanel;
	private LoginButtonPanel loginButtonPanel;
	private LoginRadioButtonPanel loginRadioButtonPanel;

	public LogInWindow()
	{
		this.setTitle("Login Window");
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		loginPanel = new LogInPanel();
		loginRadioButtonPanel = new LoginRadioButtonPanel();
		loginButtonPanel = new LoginButtonPanel(this, loginPanel, loginRadioButtonPanel);

		this.add(loginRadioButtonPanel, BorderLayout.NORTH);
		this.add(loginPanel, BorderLayout.CENTER);
		this.add(loginButtonPanel, BorderLayout.SOUTH);
		this.pack();
		this.setVisible(true);

	}

	public void close()
	{
		System.out.println("getting ready to close this window");
		this.dispose();
	}

}
