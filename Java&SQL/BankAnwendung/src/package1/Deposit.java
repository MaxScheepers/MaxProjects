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

public class Deposit {

	public static void deposit() {

		// Einzahlungs GUI wird erstellt...
		JFrame frame = new JFrame("Deposit");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 500, 400);
		frame.setResizable(false);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		JLabel lb_headline = new JLabel("BankingApplication");
		lb_headline.setFont(new Font("Arial Black", Font.BOLD, 20));
		lb_headline.setHorizontalAlignment(SwingConstants.CENTER);
		lb_headline.setBounds(10, 11, 464, 70);
		contentPane.add(lb_headline);

		JLabel lb_depositAmount = new JLabel("Amount:");
		lb_depositAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		lb_depositAmount.setFont(new Font("Arial", Font.BOLD, 12));
		lb_depositAmount.setBounds(142, 92, 95, 25);
		contentPane.add(lb_depositAmount);

		JTextField tf_depositAmount = new JTextField();
		tf_depositAmount.setFont(new Font("Arial", Font.BOLD, 12));
		tf_depositAmount.setBounds(245, 94, 86, 20);
		contentPane.add(tf_depositAmount);
		tf_depositAmount.setColumns(10);

		JButton bt_transfer = new JButton("Deposit!");
		bt_transfer.addActionListener(new ActionListener() {

			// Wenn Button gedruckt
			public void actionPerformed(ActionEvent e) {

				// Wert wird gespeichert
				String amount = tf_depositAmount.getText();

				// Wenn Einzahlung erfolgreich war...
				if (MySQL.deposit(amount)) {

					// zurueck zur Startseite
					frame.dispose();
					Homepage.homepageUI();
				} else {

					// wenn nicht, Fehlermeldung
					JOptionPane.showMessageDialog(contentPane, MySQL.message);
				}

			}

		});

		bt_transfer.setFont(new Font("Arial", Font.BOLD, 12));
		bt_transfer.setBounds(245, 125, 86, 32);
		contentPane.add(bt_transfer);

		frame.setContentPane(contentPane);

		frame.setVisible(true);

	}

}
