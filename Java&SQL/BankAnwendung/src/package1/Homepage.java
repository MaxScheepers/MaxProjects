package package1;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Homepage {

	public static void homepageUI() {

		
		//Startseite GUI wird erstellt...
		JFrame frame = new JFrame("User: " + Main.logIn_User);

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

		JLabel lb_ktnr = new JLabel("Balance:");
		lb_ktnr.setHorizontalAlignment(SwingConstants.RIGHT);
		lb_ktnr.setFont(new Font("Arial", Font.BOLD, 18));
		lb_ktnr.setBounds(125, 92, 90, 25);
		contentPane.add(lb_ktnr);

		JLabel lb_saldo = new JLabel(Integer.toString(Main.logIn_Saldo));
		lb_saldo.setHorizontalAlignment(SwingConstants.LEFT);
		lb_saldo.setFont(new Font("Arial", Font.BOLD, 18));
		lb_saldo.setBounds(225, 92, 249, 25);
		contentPane.add(lb_saldo);

		//Wenn Ueberweisungsbutton gedrueckt...
		JButton transfer = new JButton("Transfer");
		transfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//Startseite geschlossen und Ueberweisungs GUI wird aufgerufen
				frame.dispose();
				Transfer.transferUI();
			}
		});
		transfer.setFont(new Font("Arial", Font.BOLD, 11));
		transfer.setBounds(135, 128, 86, 32);
		contentPane.add(transfer);

		
		JButton bt_history = new JButton("History");
		
		//Wenn Verlaufbutton gedrueckt...
		bt_history.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//Verlauf GUI wird aufgerufen
				History.history();
			}
		});
		bt_history.setFont(new Font("Arial", Font.BOLD, 11));
		bt_history.setBounds(235, 128, 86, 32);
		contentPane.add(bt_history);

		JButton bt_withdraw = new JButton("Cash out");
		
		//Wenn Auszahlungsbutton gedrueckt...
		bt_withdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//wird Startseite geschlossen und Auszahlungs GUI aufgerufen
				frame.dispose();
				CashOut.cashOut();
			}
		});
		bt_withdraw.setFont(new Font("Arial", Font.BOLD, 11));
		bt_withdraw.setBounds(135, 183, 86, 32);
		contentPane.add(bt_withdraw);

		JButton bt_deposit = new JButton("Deposit");
		
		//Wenn Einzahlungsbuttom gedrueckt...
		bt_deposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//wird Startseite geschlossen und Einzahlungs GUI aufgerufen
				frame.dispose();
				Deposit.deposit();
			}
		});
		bt_deposit.setFont(new Font("Arial", Font.BOLD, 11));
		bt_deposit.setBounds(235, 183, 86, 32);
		contentPane.add(bt_deposit);

		JButton bt_logout = new JButton("LogOut");
		
		//Wenn LogOutbutton gedrueckt...
		bt_logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//Startseite geschlossen
				frame.dispose();

				//logIn Daten reseted
				Main.logIn_User = "";
				Main.logIn_Password = "";
				Main.logIn_Saldo = 0;

				//LogIn GUI aufgerufen
				GUI.logInWindow();

			}
		});
		bt_logout.setFont(new Font("Arial", Font.BOLD, 11));
		bt_logout.setBounds(388, 318, 86, 32);
		contentPane.add(bt_logout);

		frame.setVisible(true);

	}

}
