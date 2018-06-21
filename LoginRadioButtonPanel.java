

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


@SuppressWarnings("serial")
public class LoginRadioButtonPanel extends JPanel
{
	private JRadioButton instructorButton;
    private  JRadioButton clientButton;
    
    private String userChoice;
    
	public LoginRadioButtonPanel() {
		
		Box HorizontalBox = Box.createHorizontalBox();
		Box.createRigidArea(new Dimension (20,20));
		//ButtonGroup choicesGRP = new ButtonGroup();
		instructorButton = new JRadioButton("Instructor");
		clientButton = new JRadioButton("Client");
		
		
		
		/*choicesGRP.add(shekelButton);
		choicesGRP.add(euroButton);
		choicesGRP.add(britishPoundButton);
		choicesGRP.add(yenButton);*/
		
		//add each of the radio buttons to a Box
		HorizontalBox.add(instructorButton);
		HorizontalBox.add(clientButton);
		
		
		//now add the box to the panel
		this.add(HorizontalBox);
		
		//add actionlistener
		instructorButton.addActionListener(new UserLoginTypeListener());
		clientButton.addActionListener(new UserLoginTypeListener());
		
		
		//initialize userChoice
		userChoice = null;
		
	}
    public String getUserChoice() {
   	 return userChoice;
    }
	
	private class UserLoginTypeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			JRadioButton theButton = (JRadioButton)event.getSource();
			
			if (theButton == instructorButton) {
				userChoice = "instructor";
			}
			else if (theButton == clientButton) {
			
				userChoice = "client";
			}
			
		}
		
	}
}
