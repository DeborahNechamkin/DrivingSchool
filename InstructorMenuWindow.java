
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class InstructorMenuWindow extends JFrame

{

	Connection dbConnection;
	JButton viewScheduleButton;
	JButton viewtotalhoursButton;
	InstructorMenuWindow window;
	Box verticalBox;

	public InstructorMenuWindow(Connection dbConnection)
	{
		this.dbConnection = dbConnection;
		window = this;
		this.setLayout(new FlowLayout());
		this.setUpWindow();

		this.pack();
		this.setVisible(true);

	}

	public void setUpWindow()
	{
		this.setTitle("Instructor Menu Window");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(5, 5);

		viewScheduleButton = new JButton("View Schedule by Date");
		viewtotalhoursButton = new JButton("View Total Hours");

		verticalBox = Box.createVerticalBox();
		assignButtons(viewScheduleButton);
		assignButtons(viewtotalhoursButton);
		this.add(verticalBox);
	}

	public void assignButtons(JButton button)
	{
		verticalBox.add(button);
		button.addActionListener(new InstructorMenuListener());
	}

	private class InstructorMenuListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent event)
		{
			JButton theButton = (JButton) event.getSource();
			if (theButton == viewScheduleButton)
			{
				// window.dispose();
				new ViewScheduleWindow(dbConnection);
			}
			else if (theButton == viewtotalhoursButton)
			{
				// window.dispose();
				new ViewTotalHoursWindow(dbConnection);
			}
		}
	}
}
