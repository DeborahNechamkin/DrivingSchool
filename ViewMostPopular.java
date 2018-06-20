import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class ViewMostPopular extends JFrame{
	Connection dbconnection;
	private JTextField Branchid;
	private JFrame window;
	private JLabel resultsLabel;
	
public ViewMostPopular(Connection dbConnection){
	
	
	
		this.dbconnection = dbConnection;
		this.setTitle("View Most Popular Instructor");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window = this;
		this.setLayout(new GridLayout(8,1));
		this.add(new JLabel("Branch ID:"));
		Branchid = new JTextField(10);
		this.add(Branchid);
		this.add(new GenericButtonPanel(this, dbConnection, new ViewMostPopularListener()));
		resultsLabel= new JLabel();
		this.add(resultsLabel);
		this.pack();
		this.setVisible(true);
}
	private class ViewMostPopularListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String sql= "";
		 int branchid=Integer.parseInt(Branchid.getText());
		
		
			PreparedStatement stmt;
			try {
				stmt = dbconnection.prepareStatement(sql);
			
			ResultSet results = stmt.executeQuery();
			//stmt.addInt(1, branchid);
			results.next();
			StringBuilder info = new StringBuilder();
			while (results.next())
			{
				
				
			}

			resultsLabel.setText(info.toString());
		
			
			}catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "database issue - contact IT. " + e.getMessage());
				
			}
		}
		
	}
	
	
}

