package package1;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Transfer {

	public static void transferUI() {

		//Ueberweisungs GUI wird erstellt...
		JFrame frame = new JFrame();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

		JLabel lb_transferRecipient = new JLabel("Recipient:");
		lb_transferRecipient.setHorizontalAlignment(SwingConstants.RIGHT);
		lb_transferRecipient.setFont(new Font("Arial", Font.BOLD, 12));
		lb_transferRecipient.setBounds(142, 92, 95, 25);
		contentPane.add(lb_transferRecipient);

		JTextField tf_transferRecipient = new JTextField();
		tf_transferRecipient.setFont(new Font("Arial", Font.BOLD, 12));
		tf_transferRecipient.setBounds(245, 94, 86, 20);
		contentPane.add(tf_transferRecipient);
		tf_transferRecipient.setColumns(10);

		JLabel lb_transferAmount = new JLabel("Amount:");
		lb_transferAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		lb_transferAmount.setFont(new Font("Arial", Font.BOLD, 12));
		lb_transferAmount.setBounds(142, 123, 95, 25);
		contentPane.add(lb_transferAmount);

		JTextField tf_transferAmount = new JTextField();
		tf_transferAmount.setBounds(245, 125, 86, 20);
		contentPane.add(tf_transferAmount);

		JButton bt_transfer = new JButton("Transfer!");
		
		//Wenn Ueberweisungsbutton gedrueckt wird
		bt_transfer.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				//Empfaenger und Betrag werden gespeichert
				String recipient = tf_transferRecipient.getText();
				String amount = tf_transferAmount.getText();

				//Wenn Ueberweisung erfolgreich war
				if (MySQL.transfer(recipient, amount)) {

					//GUI wird geschlossen und Startseite aufgerufen
					frame.dispose();
					Homepage.homepageUI();

				} else {

					//Wenn nicht, Fehlermeldung
					JOptionPane.showMessageDialog(contentPane, MySQL.message);
				}

			}

		});

		bt_transfer.setFont(new Font("Arial", Font.BOLD, 12));
		bt_transfer.setBounds(245, 159, 86, 32);
		contentPane.add(bt_transfer);

		frame.setVisible(true);

	}

}