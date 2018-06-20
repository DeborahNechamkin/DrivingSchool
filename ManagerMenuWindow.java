

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;



@SuppressWarnings("serial")
public class ManagerMenuWindow extends JFrame
{
	
		Connection dbConnection;
		JButton viewRevenueButton;
		JButton viewMostPopularInstructorButton;
		ManagerMenuWindow window;
		Box verticalBox;
		
	public ManagerMenuWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;	
		window = this;
		this.setUpWindow();
		this.setLayout(new FlowLayout());
		this.pack();
		this.setVisible(true);
System.out.println("hi");
		}

	public void setUpWindow(){
		
		this.setTitle("Manager Menu Window");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(5, 5);

		viewRevenueButton = new JButton("View revenue by branch");
		viewMostPopularInstructorButton = new JButton("View most popular instructor");
		
		verticalBox = Box.createVerticalBox();
		assignButtons(viewRevenueButton);
		assignButtons(viewMostPopularInstructorButton);
		this.add(verticalBox);
	}


	public void assignButtons(JButton button)
	{
		verticalBox.add(button);
		button.addActionListener(new ManagerMenuListener());
	}
	private class ManagerMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			JButton theButton = (JButton)event.getSource();
			if(theButton==viewRevenueButton){
				//window.dispose();
				new ViewRevenueWindow(dbConnection);
			}
			else if(theButton==viewMostPopularInstructorButton){
				//window.dispose();
				new ViewMostPopular(dbConnection);
			}
		}
		}
	}

