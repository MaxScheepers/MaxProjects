package package1;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI {

	public static void logInWindow() {

		// Log In GUI wird erstellt
		JFrame frame = new JFrame("Log in");

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 500, 400);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lb_headline = new JLabel("BankingApplication");
		lb_headline.setFont(new Font("Arial Black", Font.BOLD, 20));
		lb_headline.setHorizontalAlignment(SwingConstants.CENTER);
		lb_headline.setBounds(10, 11, 464, 70);
		contentPane.add(lb_headline);

		JLabel lb_User = new JLabel("Username:");
		lb_User.setHorizontalAlignment(SwingConstants.RIGHT);
		lb_User.setFont(new Font("Arial", Font.BOLD, 12));
		lb_User.setBounds(142, 92, 95, 25);
		contentPane.add(lb_User);

		JTextField tf_logInUser = new JTextField();
		tf_logInUser.setFont(new Font("Arial", Font.BOLD, 12));
		tf_logInUser.setBounds(245, 94, 86, 20);
		contentPane.add(tf_logInUser);
		tf_logInUser.setColumns(10);

		JLabel lb_password = new JLabel("Password:");
		lb_password.setHorizontalAlignment(SwingConstants.RIGHT);
		lb_password.setFont(new Font("Arial", Font.BOLD, 12));
		lb_password.setBounds(142, 123, 95, 25);
		contentPane.add(lb_password);

		JPasswordField tf_logInPassword = new JPasswordField();
		tf_logInPassword.setBounds(245, 125, 86, 20);
		contentPane.add(tf_logInPassword);

		JButton bt_logIn = new JButton("Log in!");

		// Wenn LogIn Button gedrueckt...
		bt_logIn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// Name und Passwort werden gespeichert
				Main.logIn_User = tf_logInUser.getText();
				Main.logIn_Password = tf_logInPassword.getText();

				// Wenn Log In Erfolgreich
				if (MySQL.logIn()) {

					// Zur Startseite
					frame.dispose();
					Homepage.homepageUI();

				} else {

					// Wenn nicht, Fehlermeldung
					JOptionPane.showMessageDialog(contentPane, MySQL.message);

				}

			}
		});

		bt_logIn.setFont(new Font("Arial", Font.BOLD, 12));
		bt_logIn.setBounds(245, 159, 86, 32);
		contentPane.add(bt_logIn);

		JButton bt_registrate = new JButton("Registrate!");

		// Wenn registrieren Button gedrueckt wird...
		bt_registrate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// Name und Passwort werden gespeichert
				Main.logIn_User = tf_logInUser.getText();
				Main.logIn_Password = tf_logInPassword.getText();

				// Wenn registrieren erfolgreich...
				if (MySQL.registrate()) {

					// Bestäsitung --> Einloggen --> Startseite
					JOptionPane.showMessageDialog(contentPane, MySQL.message);
					MySQL.logIn();
					Homepage.homepageUI();
					frame.dispose();

				} else {

					// Wenn nicht Fehlermeldung
					JOptionPane.showMessageDialog(contentPane, MySQL.message);
				}

			}
		});

		bt_registrate.setFont(new Font("Arial", Font.BOLD, 12));
		bt_registrate.setBounds(245, 200, 86, 32);
		contentPane.add(bt_registrate);

		frame.setVisible(true);

	}

}
