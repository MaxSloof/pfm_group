package groupPackage;

import java.util.Scanner;

public class BankAccount {
	String iban;
	int bankID;
	double balance;
	static BankAccount[] bankAccounts = new BankAccount[100];
	
	static Scanner userInputInt = new Scanner(System.in);
	static Scanner userInputString = new Scanner(System.in);
	static Scanner userInputDouble = new Scanner(System.in);



	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		// New account
		System.out.print("Press 'L' for login and 'S' for sign-up");
		String x = userInputString.nextLine();
		if (x.equals("L") || x.equals("l")) {
			System.out.println("Username");
			String username = userInputString.nextLine();
			System.out.println("Password");
			String password = userInputString.nextLine();
		} if (x.equals("S") || x.equals("s")) {
			
		}
		
		// For testing
		BankAccount acc1 = new BankAccount();
		acc1.balance = 10000;
		acc1.bankID = 000001;
		acc1.iban = "NL01PFMB1234567890";
		bankAccounts[0] = acc1;
		
		System.out.print("Press 'O' for Balance overview");
		String y = userInputString.nextLine();
		if (y.equals("O") || y.equals("o")) {
			showOverview();
		}


	}

	public static void showOverview() {
		// TODO Auto-generated method stub
		System.out.printf("BankID: %06d\n", bankAccounts[0].bankID);
		System.out.printf("BankID: %s\n", bankAccounts[0].iban);
		System.out.printf("Bank balance: %.2f\n", bankAccounts[0].balance);
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}



	public void viewBalance() {
		// TODO Auto-generated method stub
		
	}

}