package groupPackage;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class BankAccount {
	String iban;
	int bankID;
	double balance;
	int userID; // User class still needs to be added

	static BankAccount[] bankAccounts = new BankAccount[100];

	static Scanner userInputInt = new Scanner(System.in);
	static Scanner userInputString = new Scanner(System.in);
	static Scanner userInputDouble = new Scanner(System.in);



	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		System.out.print("Enter 'L' for login and 'S' for sign-up");
		String x = userInputString.nextLine();

		// Login or Sign up
		if (x.equals("L") || x.equals("l")) { // User wants to login
			System.out.println("Username");
			String username = userInputString.nextLine();
			System.out.println("Password");
			String password = userInputString.nextLine();

			readFile(username, password);
			System.out.print("Enter '1' for Balance overview");
			String y = userInputString.nextLine();
			if (y.equals("1")) {
				showOverview();
			}
			// Should go through the txt file and find the correct lines. 
			//findBankAccount();

		} if (x.equals("S") || x.equals("s")) { // User wants to create new bank account 

			System.out.print("Enter new username: ");
			String username = userInputString.nextLine();
			
			System.out.print("Enter new password: ");
			String password = userInputString.nextLine();
			System.out.print("Confirm password: ");
			String passwordConfirm = userInputString.nextLine();
			while (!password.equals(passwordConfirm)) {
				System.out.println("Please re-enter password");
				System.out.print("Enter new password: ");
				password = userInputString.nextLine();
				System.out.print("Confirm password: ");
				passwordConfirm = userInputString.nextLine();
			}

			//Create new IBAN 
			DecimalFormat dfIban = new DecimalFormat("0000000000");
			String tempIban = "NL01PFMB" + dfIban.format(returnIndex() + 1);
			System.out.printf("New IBAN: %s\n", tempIban); 

			// Create new bankID and UserID, by adding 1 to the latest one. 
			int tempBankID = returnIndex() + 1;
			int tempUserID = returnIndex() + 100001;
			double tempBalance = 0;		// Balance (= 0, because new account)
			writeNewAccount(tempIban, tempBankID, tempBalance, tempUserID);
			System.out.println("The current balance: 0.00");
			System.out.println("You can now deposit money");
			
			BankAccount currAcc = new BankAccount();
			currAcc.balance = tempBalance;
			currAcc.bankID = tempBankID;
			currAcc.iban = tempIban;
			bankAccounts[0] = currAcc;
			
			
		
		} else {
			System.out.println("Key invalid."
					+ "\n"
					+ "Restart the program");
		}

		
		// Method deposit money //////////////////



		// For testing (both account 1 and 2)
		BankAccount currAcc = new BankAccount();
		currAcc.balance = 10000;
		currAcc.bankID = 000001;
		currAcc.iban = "NL01PFMB1234567890";
		bankAccounts[0] = currAcc;

		BankAccount acc2 = new BankAccount();
		acc2.balance = 42342;
		acc2.bankID = 000002;
		acc2.iban = "NL01PFMB1234999899";
		bankAccounts[1] = acc2;
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

	// Setter and getter methods
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
	
	// Creating new account when user wants to sign-up
	public static void writeNewAccount(String newIban, int newBankID, double newBalance, int newUserID) {
		// TODO Auto-generated method stub
		BankAccount[] bankArray1 = new BankAccount[1];
		String tempIban = newIban;
		int tempBankID = newBankID;
		double tempBalance = newBalance;
		int tempUserID = newUserID;

		bankArray1[0] = new BankAccount(tempIban, tempBankID, tempBalance, tempUserID);
		writeFile(bankArray1);
	}

	// Writing new account to bankaccounts file
	public static void writeFile(BankAccount[] bankArray1) {
		// TODO Auto-generated method stub
		try{ 
			PrintWriter myFile = new PrintWriter ( 
					new BufferedWriter ( new FileWriter ("bankaccounts.txt", true))); 
			myFile.printf("\n%s\t%06d\t%f\t%06d",bankArray1[0].iban, bankArray1[0].bankID,
					bankArray1[0].balance, bankArray1[0].userID); 


			myFile.close(); 
		} catch(IOException e){ 
			System.out.print("Wrong! (writing)"); 
		} 
	}
	
	// Reading from the bankaccounts file
	public static BankAccount[] readFile(String username, String password){ 
		BankAccount[] stTemp = new BankAccount[100]; // 100 here is an upper bound 
		int stIndex = 0; // keeps track of the line number 
		try{ 
			BufferedReader myFile = new BufferedReader (new FileReader("bankaccounts.txt")); 
			String sCurrentLine; 
			String[] uCurrent = new String[4]; 

			while ((sCurrentLine = myFile.readLine()) != null){ 
				uCurrent = sCurrentLine.split("\t"); 
				stTemp[stIndex] = new BankAccount(uCurrent[0], Integer.parseInt(uCurrent[1]),
						Double.parseDouble(uCurrent[2]), Integer.parseInt(uCurrent[3])); 
				stIndex++; 
			} 
			myFile.close(); 

		} catch(IOException e){ 
			System.out.print("Wrong! (Reading)"); 
		} 
		BankAccount[] baArray = new BankAccount[stIndex]; 
		System.arraycopy(stTemp, 0, baArray, 0, stIndex); 
		return baArray; 

	}

	// Returning the index of the latest line, so that I can create a new file
	public static int returnIndex(){ 
		BankAccount[] stTemp = new BankAccount[100]; // 100 here is an upper bound 
		int stIndex = 0; // keeps track of the line number 
		try{ 
			BufferedReader myFile = new BufferedReader (new FileReader("bankaccounts.txt")); 
			String sCurrentLine; 
			String[] uCurrent = new String[4]; 

			while ((sCurrentLine = myFile.readLine()) != null){ 
				uCurrent = sCurrentLine.split("\t"); 
				stTemp[stIndex] = new BankAccount(uCurrent[0], Integer.parseInt(uCurrent[1]),
						Double.parseDouble(uCurrent[2]), Integer.parseInt(uCurrent[3])); 
				stIndex++; 
			} 
			myFile.close(); 

		} catch(IOException e){ 
			System.out.print("Wrong! (Reading)"); 
		} 
		BankAccount[] baArray = new BankAccount[stIndex]; 
		System.arraycopy(stTemp, 0, baArray, 0, stIndex); 
		return stIndex; 

	}

	
// Constructors
	public BankAccount(String iban, int bankID, double balance, int userID) {
		this.iban = iban;
		this.bankID = bankID;
		this.balance = balance;
		this.userID = userID;
	}

	public BankAccount() {
		// TODO Auto-generated constructor stub
	} 
} 
