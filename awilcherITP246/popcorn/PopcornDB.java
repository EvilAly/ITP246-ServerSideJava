package popcorn;

import java.util.Scanner;

public class PopcornDB {

	public static void main(String[] args) {
		
		int choice = 0;
		while(choice !=7) {
			choice = menu();
			if(choice == 1)
				PopcornMethodsDB.printPopcorn();
			else if (choice == 2)
				PopcornMethodsDB.printUsers();
			else if (choice == 3)
				PopcornMethodsDB.addItem();
			else if (choice == 4)
				PopcornMethodsDB.addBuyer();
			else if (choice == 5)
				PopcornMethodsDB.changePrice();
			else if (choice == 6)
				PopcornMethodsDB.sell();
			else if (choice == 7) {
				PopcornMethodsDB.closeConnection();
				System.exit(0);
			}
			
		}
		
	}
	
	 public static int menu() {
			Scanner scan = new Scanner(System.in);
			System.out.println("\nMenu:");
			System.out.println("1.  Print the inventory of popcorn");
			System.out.println("2.  Print the buyers");
			System.out.println("3.  Add a new inventory item");
			System.out.println("4.  Add a new buyer");		
			System.out.println("5.  Change the price on a popcorn type");
			System.out.println("6.  Sell some popcorn");
			System.out.println("7.  END");
			int ans = scan.nextInt();
			return ans;

		
	}

}
