package package1;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class History {
	
	public static void history() {
	
		//Verlauf GUI wird erstellt...
		JFrame frame = new JFrame("History");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 500, 400);
		frame.setResizable(false);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lb_headline = new JLabel("BankingApplication");
		lb_headline.setFont(new Font("Arial Black", Font.BOLD, 20));
		lb_headline.setHorizontalAlignment(SwingConstants.CENTER);
		lb_headline.setBounds(10, 11, 464, 70);
		contentPane.add(lb_headline);
	    
	    String columnNames[]= {"Date","Action","Amount"};
	    
	    //Verlauf wird abgerufen und angezeigt
	    JTable table = new JTable(MySQL.showHistory(), columnNames);
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	    table.getTableHeader().setReorderingAllowed(false);
	    table.setFillsViewportHeight(true);
	    table.setEnabled(false);
	    JScrollPane scrollPane = new JScrollPane(table);
	    scrollPane.setBounds(10, 100, 464, 250);
	    contentPane.add(scrollPane);
	    
	   frame.setVisible(true);
		
		
	}

}
