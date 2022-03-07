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
		
		// assumption: User class name: User
		System.out.print("Enter 'L' for login and 'S' for sign-up");
		String x = userInputString.nextLine();
		
		// Login or Sign up
		if (x.equals("L") || x.equals("l")) { // User wants to login
			System.out.println("Username");
			String username = userInputString.nextLine();
			System.out.println("Password");
			String password = userInputString.nextLine();
			
			
			// Should go through the txt file and find the correct lines. 
			findBankAccount();
			
		} if (x.equals("S") || x.equals("s")) { // User wants to create new bank account
			BankAccount[] bankArray1 = new BankAccount[1];
			String name;
			System.out.print("Enter name: ");
			name = userInputString.nextLine();
			
			// Read latest bankID and IBAN and add 1
		}
		
		// For testing (both account 1 and 2)
		BankAccount acc1 = new BankAccount();
		acc1.balance = 10000;
		acc1.bankID = 000001;
		acc1.iban = "NL01PFMB1234567890";
		bankAccounts[0] = acc1;
		
		BankAccount acc2 = new BankAccount();
		acc2.balance = 42342;
		acc2.bankID = 000002;
		acc2.iban = "NL01PFMB1234999899";
		bankAccounts[1] = acc2;
		
		
		
		System.out.print("Enter '1' for Balance overview");
		String y = userInputString.nextLine();
		if (y.equals("1")) {
			showOverview();
		}


	}

	public static void showOverview() {
		// TODO Auto-generated method stub
		System.out.println("***************************************");
		System.out.println("*************** Overview **************");
		System.out.println("***************************************");
		System.out.printf("BankID: %06d\n", bankAccounts[1].bankID);
		System.out.printf("BankID: %s\n", bankAccounts[1].iban);
		System.out.printf("Bank balance: %.2f\n", bankAccounts[1].balance);
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public int getBankID() {
		return bankID;
	}

	public void setBankID(int bankID) {
		this.bankID = bankID;
	}


}