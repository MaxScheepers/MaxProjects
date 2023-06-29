package package1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class MySQL {

	// Datenbank Daten speichern
	private static final String host = "localhost";
	private static final String port = "3306";
	private static final String database = "banking";
	private static final String username = "root";
	private static final String password = "";

	// Tabellennamen speichern
	private static final String tableUser = "username";
	private static final String tablePassword = "password";
	private static final String tableHistory = "history";

	// Fehlermeldung
	public static String message = "Error!";

	// Format fuer das Datum
	private static final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);

	// LogIn Funktion
	public static boolean logIn() {

		try {

			// Verbindung und Statement erstellen
			Connection myConn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database,
					username, password);

			java.sql.Statement myStmt = myConn.createStatement();

			// Alle User auslesen
			ResultSet myRs = ((java.sql.Statement) myStmt).executeQuery("SELECT user FROM " + tableUser);

			// Ueberpruefen ob der angegebene User existiert
			while (myRs.next()) {

				String dbKtnr = myRs.getString("user");

				// Wenn ja...
				if (dbKtnr.equals(Main.logIn_User)) {

					// Passwort und Kontostand fuer den angegebenen User auslesen
					ResultSet myRs1 = ((java.sql.Statement) myStmt)
							.executeQuery("SELECT * FROM " + tableUser + " JOIN " + tablePassword + " on " + tableUser
									+ ".id = " + tablePassword + ".id WHERE user = '" + Main.logIn_User + "'");

					myRs1.next();

					// Passwort und Kontostand speichern
					String dbPassword = myRs1.getString("password");
					int dbBalance = myRs1.getInt("balance");

					// Wenn Passwort uebereinstimmt
					if (Main.logIn_Password.equals(MySQL.decrypt(dbPassword))) {

						// Globalen Kontostand aktualisieren
						Main.logIn_Saldo = dbBalance;

						return true;

					} else {

						// Wenn nicht, Fehlermeldung
						MySQL.message = "Given password is incorrect!";

						return false;
					}
				}

			}

		} catch (Exception e) {

			e.printStackTrace();

			// Wenn Datenbank nicht antwortet
			MySQL.message = "Error while connecting to database!";

			return false;
		}

		// Username wurde nicht gefunden
		MySQL.message = "Username doesn't exist!";

		return false;
	}

	// Ueberweisungs Funktion
	public static boolean transfer(String string_recipient, String string_amount) {

		try {

			int int_amount = -1;

			try {

				// String zu Int
				int_amount = Integer.parseInt(string_amount);

				// Betrag muss positiv sein
				if (int_amount < 0) {

					MySQL.message = "The amount must be a positive value!";

					return false;

				}

				// Betrag darf nicht groesser als der Kontostand sein
				if (int_amount > Main.logIn_Saldo) {

					MySQL.message = "Your balance is to low!";

					return false;

				}

			} catch (Exception e) {

				// Falls es eine ungerade Zahl ist
				MySQL.message = "Whole numbers only!";

				return false;
			}

			// Verbundung und Statement erstellen...
			Connection myConn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database,
					username, password);

			java.sql.Statement myStmt = myConn.createStatement();

			// Alle User mit dem Empfaengernamen auslesen
			ResultSet myRs = myStmt
					.executeQuery("SELECT * from " + tableUser + " WHERE user = '" + string_recipient + "'");

			int count = 0;

			// Anzahl ueberpruefen
			while (myRs.next()) {

				count++;
			}

			// Wenn Anzahl 0 oder > 1...
			if (count == 0 || count > 1) {

				// ...Fehler
				MySQL.message = "The recipient is incorrect!";

				return false;
			}

			// Empfaenger Kontostand aendern
			ResultSet myRs1 = myStmt
					.executeQuery("SELECT balance from " + tableUser + " WHERE user = '" + string_recipient + "'");

			myRs1.next();

			int recipientBalance = myRs1.getInt("balance");

			recipientBalance = recipientBalance + int_amount;

			myStmt.execute("UPDATE " + tableUser + " SET balance = '" + recipientBalance + "' WHERE user = '"
					+ string_recipient + "'");

			// Auftraggeber Kontostand aendern
			ResultSet myRs2 = myStmt
					.executeQuery("SELECT balance from " + tableUser + " WHERE user = '" + Main.logIn_User + "'");

			myRs2.next();

			int userBalance = myRs2.getInt("balance");

			userBalance = userBalance - int_amount;

			myStmt.execute("UPDATE " + tableUser + " SET balance = '" + userBalance + "' WHERE user = '"
					+ Main.logIn_User + "'");

			// Globalen Kontostand aktualisieren
			Main.logIn_Saldo = userBalance;

			// Ereignis in den Verlauf eunfuegen
			myStmt.execute("INSERT INTO `" + tableHistory + "`(`user`, `amount`, `action`, `date`) VALUES ('"
					+ Main.logIn_User + "', '" + int_amount + "','Transfer to " + string_recipient + "', '"
					+ df.format(new GregorianCalendar().getTime()) + "')");

			myStmt.execute("INSERT INTO `" + tableHistory + "`(`user`, `amount`, `action`, `date`) VALUES ('"
					+ string_recipient + "', '" + int_amount + "','Transfer from " + Main.logIn_User + "', '"
					+ df.format(new GregorianCalendar().getTime()) + "')");

		} catch (Exception e) {

			e.printStackTrace();

			// Wenn Datenbank nicht antwortet
			MySQL.message = "Error while connecting to database!";

			return false;
		}

		return true;

	}

	// Einzahlungs Funktion
	public static boolean deposit(String string_amount) {

		try {

			// String zu int
			int int_amount = Integer.parseInt(string_amount);

			// Wenn Betrag < 0
			if (int_amount < 0) {

				// ...Fehler
				MySQL.message = "The amount must be a potitive value!";

				return false;
			}

			// Verbindung und Statement erstellen
			Connection myConn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database,
					username, password);

			java.sql.Statement myStmt = myConn.createStatement();

			// Kontostand updaten
			int saldo = Main.logIn_Saldo + int_amount;

			myStmt.execute(
					"UPDATE " + tableUser + " SET balance = '" + saldo + "' WHERE user = '" + Main.logIn_User + "'");

			myStmt.execute("INSERT INTO `" + tableHistory + "`(`user`, `amount`, `action`, `date`) VALUES ('"
					+ Main.logIn_User + "', '" + int_amount + "','Deposit', '"
					+ df.format(new GregorianCalendar().getTime()) + "')");

			// Globales Saldo updaten
			Main.logIn_Saldo = saldo;

		} catch (Exception e) {

			e.printStackTrace();

			// Wenn Datenbank nicht antwortet
			MySQL.message = "Error while connecting to database!";

			return false;
		}

		return true;
	}

	// Auszahlung Funktion
	public static boolean cashOut(String string_amount) {

		try {

			// String zu int
			int int_amount = Integer.parseInt(string_amount);

			// Wenn Betrag < 0...
			if (int_amount < 0) {

				// ...Fehler
				MySQL.message = "The amount must be a positive value!";

				return false;

			}

			// Wenn Betrag > Kontostand...
			if (int_amount > Main.logIn_Saldo) {

				// ...Fehler
				MySQL.message = "Your balance is to low!";

				return false;
			}

			// Verbindung und Statment erstellen
			Connection myConn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database,
					username, password);

			java.sql.Statement myStmt = myConn.createStatement();

			// Kontostand berechnen und updaten
			int saldo = Main.logIn_Saldo - int_amount;

			myStmt.execute(
					"UPDATE " + tableUser + " SET balance = '" + saldo + "' WHERE user = '" + Main.logIn_User + "'");

			myStmt.execute("INSERT INTO `" + tableHistory + "`(`user`, `amount`, `action`, `date`) VALUES ('"
					+ Main.logIn_User + "', '" + int_amount + "','Cash out', '"
					+ df.format(new GregorianCalendar().getTime()) + "')");

			// Globales Saldo aktualisieren
			Main.logIn_Saldo = saldo;

		} catch (Exception e) {

			e.printStackTrace();

			// Wenn Datenbank nicht antwortet
			MySQL.message = "Error while connecting to database!";

			return false;
		}

		return true;
	}

	// Verlauf Funktion
	public static String[][] showHistory() {

		// Raster erstellen
		String[][] result = { { "", "", "" }, { "", "", "" }, { "", "", "" }, { "", "", "" }, { "", "", "" } };

		try {

			// Verbindung und Statement erstellen...
			Connection myConn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database,
					username, password);

			java.sql.Statement myStmt = myConn.createStatement();

			// Verlauf fuer den angemeldeten User auslesen
			ResultSet myRs = myStmt.executeQuery("SELECT * FROM " + tableHistory + " WHERE user = '" + Main.logIn_User
					+ "' ORDER BY id DESC LIMIT 5");

			// Verlauf speichern
			for (int i = 0; i <= 4; i++) {

				if (myRs.next()) {

					for (int j = 0; j <= 2; j++) {

						if (j == 0) {

							result[i][j] = myRs.getString("date");
						}

						if (j == 1) {

							result[i][j] = myRs.getString("action");
						}

						if (j == 2) {

							result[i][j] = myRs.getString("amount");
						}
					}
				}

			}

		} catch (Exception e) {

			e.printStackTrace();

			// Wenn Datenbank nicht antwortet
			MySQL.message = "Error while connecting to database!";
		}

		// Verlauf wird zurueckgegeben
		return result;

	}

	// Registrieren Funktion
	public static boolean registrate() {

		try {

			// Verbindung und Statement erstellen
			Connection myCon = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database,
					username, password);

			java.sql.Statement myStm = myCon.createStatement();

			// Anzahl der Accounts mit dem Namen auslesen
			ResultSet myRs = myStm
					.executeQuery("SELECT COUNT(*) FROM " + tableUser + " WHERE user = '" + Main.logIn_User + "'");

			myRs.next();

			// Wenn Count > 0...
			if (myRs.getInt("COUNT(*)") > 0) {

				// ...Fehler
				MySQL.message = "Username is already taken!";
				return false;
			}

			// Wenn Name Leerzeile enthaelt...
			if (Main.logIn_User.contains(" ")) {

				// ...Fehler
				MySQL.message = "No space bar may be used!";
				return false;
			}

			// Wenn Name laneger als 30 Zeichen ist...
			if (Main.logIn_User.length() > 30) {

				// Fehler
				MySQL.message = "Maximum of 30 letters!";
				return false;
			}

			// Passwort muss min 4 und max 10 Zeichen enthalten
			if (Main.logIn_Password.length() < 4 || Main.logIn_Password.length() > 10) {

				// sonst, Fehler
				MySQL.message = "The password must have at least 4 charcters and can have a maximum of 10!";
				return false;
			}

			// Wenn Passwort Leerzeichen enthaelt...
			if (Main.logIn_Password.contains(" ")) {

				// ...Fehler
				MySQL.message = "No space bar may be used!";
				return false;
			}

			// Konto wird erstellt
			myStm.execute("INSERT INTO " + tableUser + " (`user`, `balance`) VALUES ('" + Main.logIn_User + "', '0')");

			// id auslesen
			myRs = myStm.executeQuery("SELECT `id`FROM " + tableUser + " WHERE user = '" + Main.logIn_User + "'");

			myRs.next();

			// Passwort verschluesseln und speichern
			myStm.execute("INSERT INTO `" + tablePassword + "` (`id`, `password`) VALUES ('" + myRs.getInt("id")
					+ "', '" + MySQL.encrypt(Main.logIn_Password) + "')");

			// Bestaetigung
			MySQL.message = "You have been registered!";

		} catch (Exception e) {

			e.printStackTrace();

			// Wenn Datenbank nicht antwortet
			MySQL.message = "Error while connecting to database!";

			return false;
		}

		return true;
	}

	// Verschluesselungs Funktion
	public static String encrypt(String decryptedPassword) {

		String encryptedPassword = new String();

		ArrayList<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i < decryptedPassword.length(); i++) {

			char c = decryptedPassword.charAt(i);

			int castAscii = (int) c + 5;

			list.add(castAscii);

		}

		return encryptedPassword = list.toString();
	}

	// Entschluesselungs Funktion
	public static String decrypt(String encryptedPassword) {

		String decryptedPassword = new String();

		encryptedPassword = encryptedPassword.replace("[", "");
		encryptedPassword = encryptedPassword.replace("]", "");

		String[] decryptList = encryptedPassword.split(", ");

		for (int i = 0; i < decryptList.length; i++) {

			int asciiValue = Integer.parseInt(decryptList[i]) - 5;

			char letter = (char) asciiValue;

			decryptedPassword = decryptedPassword + letter;
		}

		System.out.println(decryptedPassword);

		return decryptedPassword;

	}

}
