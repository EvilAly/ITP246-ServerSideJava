package simplifiedConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SimpleConnectToMySQL {
	static Connection con = null;
	static Statement stmt = null;

	static public void main(String args[]) {
		int value = 0;
		while (value != 4) {
			value = menu();

			if (value == 1)
				checkConnect();
			else if (value == 2) {
				if (con == null)
					con = makeAConnection();
				else
					System.out.println("One already exists!  Use that one!!");
			} else if (value == 3)
				closeConnection();
			else if (value == 4)
				System.exit(0);
		}

	}

	public static int menu() {
		Scanner scan = new Scanner(System.in);
		System.out.println("1.  Check if a connection is present");
		System.out.println("2.  Make a connection (if one does not exist)");
		System.out.println("3.  Close the connection");
		System.out.println("4.  END");
		System.out.println("Pick one...");
		int num = scan.nextInt();
		return num;

	}

	public static Connection makeAConnection() {
		Scanner scan = new Scanner(System.in);
		Connection connection = null;

		System.out.println("What is the name of the database?");
		String name = scan.nextLine();
		String url = "jdbc:mysql://localhost:3306/" + name;

		System.out.println("Username:");
		String uid = scan.nextLine();
		System.out.println("Password:");
		String pass = scan.nextLine();

		String driver = "com.mysql.jdbc.Driver";

		try { // load the driver
			Class.forName(driver);
		} catch (Exception e) { // problem loading driver, class not exist?
			e.printStackTrace();

		}
		try {
			connection = DriverManager.getConnection(url, uid, pass);
			System.out.println("Connection successful!");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
		System.out.println("A new connection was made");
		return connection;
	}

	public static void closeConnection() {
		if (con != null) {
			try {
				con.close();
				con = null;
				//stmt.close();
				System.out.println("The connectionwas successfully closed");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void checkConnect() {
		if (con == null) {
			System.out.println("A connection does not exist");
		} else
			System.out.println("A connection already existed so we will use it");
		/*
		 * if (stmt == null) { try { stmt = con.createStatement(); } catch (SQLException
		 * e) { System.out.println("Cannot create the statement"); }
		 * 
		 * }
		 */
	}

}