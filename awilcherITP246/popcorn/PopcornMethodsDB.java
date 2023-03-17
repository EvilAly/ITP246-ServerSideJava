package popcorn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class PopcornMethodsDB {

	static Connection conn = null;
	static Statement stmt = null;
	private static Scanner scan = new Scanner(System.in);

	public static Connection createConnection() {

		String user = "DBUser";
		String pass = "DBUser";
		String name = "popcorn";
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/" + name;

		System.out.println(driver);
		System.out.println(url);

		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, user, pass);
			System.out.println("Connection really is from : " + conn.getClass().getName());
			System.out.println("Connection successful!");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
				// stmt.close();
				System.out.println("The connection was successfully closed");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void checkConnect() {
		if (conn == null) {
			conn = createConnection();
		}
		if (stmt == null) {
			try {
				stmt = conn.createStatement();
			} catch (SQLException e) {
				System.out.println("Cannot create the statement");
			}

		}
	}

	public static void printPopcorn() {
		checkConnect();
		String query = "SELECT * FROM products";
		try {
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(query);
			System.out.println(" ");
			System.out.println("ID  Item Size   Cost  Number in Stock  Category");

			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String size = rs.getString(3);
				double cost = rs.getDouble(4);
				int num = rs.getInt(5);
				String cat = rs.getString(6);

				System.out.println(
						id + "     " + name + "       " + size + "      " + cost + "    " + num + "     " + cat);
			}
		}

		catch (SQLException e) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		}

	}

	public static void printUsers() {
		checkConnect();
		String query = "SELECT * FROM buyers";
		try {
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(query);
			System.out.println(" ");
			System.out.println("ID  First   Last     Email");

			while (rs.next()) {
				int id = rs.getInt(1);
				String first = rs.getString(2);
				String last = rs.getString(3);
				String email = rs.getString(4);

				System.out.println(id + "     " + first + "       " + last + "      " + email);
			}
		}

		catch (SQLException e) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		}

	}

	public static void sell() {
		checkConnect();
		Scanner scan = new Scanner(System.in);
		System.out.println("What is the ID of the item you wish to buy?");
		int itemID = scan.nextInt();
		double price = 0;
		// verify itemID
		String verifyItem = "SELECT cost FROM products WHERE productID = " + itemID;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(verifyItem);
			if (rs.next() == false) {
				System.err.println("Invalid Product ID.");
				sell();
				return;
			}
			do {
				price = rs.getDouble(1);
			} while (rs.next());

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL insert Exception");
		}

		System.out.println("What is the buyer's id?");
		int buyerID = scan.nextInt();

		// verify buyerID
		String verifyBuyer = "SELECT * FROM buyers WHERE buyerID = " + buyerID;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(verifyBuyer);
			if (rs.next() == false) {
				System.err.println("Invalid Buyer ID.");
				sell();
				return;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL insert Exception");
		}

		System.out.println("How many do they want to purchase?");
		int num = scan.nextInt();

		/*
		 * Need to add an entry to the order table Need to add an entry to the line item
		 * table Need to decrease the inventory in the products table
		 */

		String queryAdd = "INSERT INTO orders (orderDate, custID) VALUES " + "('" + LocalDate.now() + "'," + buyerID
				+ ")";

		// get orderID from inserted order
		int orderID = 0;

		String getOrderID = "SELECT orderID FROM orders ORDER BY orderID";
		 
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(getOrderID);
			
			while (rs.next()) {
				orderID = rs.getInt(1);
			}
			orderID++;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL insert Exception");
		}

		String queryLineItem = "INSERT INTO lineitem (orderId, productID, numOrdered, price)" + " VALUES (" + orderID
				+ "," + itemID + "," + num + "," + price + ")";
		String updateQuery = "UPDATE products SET numInStock = numInStock - " + num + " WHERE productID = " + itemID;

		System.out.println(queryAdd);
		System.out.println(queryLineItem);
		System.out.println(updateQuery);

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(queryAdd);
			stmt.executeUpdate(queryLineItem);
			stmt.executeUpdate(updateQuery);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL insert Exception");
		}

	}

	public static void addItem() {
		checkConnect();

		// ask for name of item
		System.out.println("What is the name of the item you are adding?");
		String item = scan.nextLine();
		// ask for size of item
		System.out.println("What size is the " + item + "?");
		String size = scan.nextLine();
		size = "'" + size + "'";
		// ask for cost of item
		System.out.println("How much does " + item + " cost?");
		double cost = scan.nextDouble();
		// ask for stock quantity
		System.out.println("How many " + item + " are in stock?");
		int stock = scan.nextInt();
		scan.nextLine();
		// ask for category
		System.out.println("What category is " + item + "?");
		String category = scan.nextLine();
		category = "'" + category + "'";
		item = "'" + item + "'";
		
		// create SQL Statement
		String addItem = "INSERT into products (item, size, cost, numInStock, category) VALUES (" + item + ", " + size
				+ ", " + cost + ", " + stock + ", " + category + ")";

		// try to execute
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(addItem);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL insert Exception");
		}
	}

	public static void addBuyer() {
		checkConnect();

		// ask for first name
		System.out.println("What is the new buyer's first name?");
		String firstName = scan.nextLine();
		// ask for last name
		System.out.println("What is " + firstName + "'s last name?");
		String lastName = scan.nextLine();
		// ask for email address
		System.out.println("What is " + firstName + "'s email address?");
		String email = scan.nextLine();

		// format variables
		firstName = "'" + firstName + "'";
		lastName = "'" + lastName + "'";
		email = "'" + email + "'";

		// create SQL Statement
		String addBuyer = "INSERT into buyers (firstName, lastName, email) VALUES (" + firstName + ", " + lastName
				+ ", " + email + ")";

		// try execute
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(addBuyer);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL insert Exception");
		}

	}

	public static void changePrice() {
		checkConnect();
		
		System.out.println("What is the ID of the item you wish to change the price of?");
		int itemID = scan.nextInt();
		scan.nextLine();
		String item = "";
		
		// verify itemID
		String verifyItem = "SELECT item FROM products WHERE productID = " + itemID;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(verifyItem);
			if (rs.next() == false) {
				System.err.println("Invalid Product ID.");
				changePrice();
				return;
			} do {
				item = rs.getString(1);
			}while (rs.next());
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL insert Exception");
		}
		// verify item with user
		System.out.println("Please confirm that you are updating the price on " + item +". (Yes or No)");
		String check = scan.nextLine();
		if (check.equalsIgnoreCase("no") || check.equalsIgnoreCase("n")) {
			changePrice();
			return;
		}
		// ask for new price
		System.out.println("What is the new price for " + item + "?");
		double price = scan.nextDouble();

		// create SQL query
		String updatePrice = "UPDATE products SET cost = " + price + " WHERE productID = " + itemID;
		
		// try execute
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(updatePrice);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL insert Exception");
		}

	}

}
