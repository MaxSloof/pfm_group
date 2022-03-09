package groupPackage;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class BankAccount {
	String iban;
	String bankID;
	double balance;
	String userID; // User class still needs to be added
	static String currUserID;

	static BankAccount[] bankAccounts = new BankAccount[100];

	static Scanner userInputInt = new Scanner(System.in);
	static Scanner userInputString = new Scanner(System.in);
	static Scanner userInputDouble = new Scanner(System.in);

	BankAccount currAcc = new BankAccount();

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

			currUserID = Usersmax.checkLogin(username, password);
			System.out.printf("UserID: %s\n",currUserID);
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

			// Keep retrying until password equals confirmPassword
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
			DecimalFormat dfBankID = new DecimalFormat("000000");
			String tempBankID = dfBankID.format(returnIndex() + 1);
			
			DecimalFormat dfUserID = new DecimalFormat("000000");
			String tempUserID = dfUserID.format(returnIndex() + 100001);
			
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
	}

	// Method deposit money //////////////////

	public static void showOverview() {
		// TODO Auto-generated method stub
		System.out.println("***************************************");
		System.out.println("*************** Overview **************");
		System.out.println("***************************************");
		System.out.printf("BankID: %s\n", bankAccounts[0].bankID);
		System.out.printf("BankID: %s\n", bankAccounts[0].iban);
		System.out.printf("Bank balance: %.2f\n", bankAccounts[0].balance);
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

	public String getBankID() {
		return bankID;
	}

	public void setBankID(String bankID) {
		this.bankID = bankID;
	}

	// Creating new account when user wants to sign-up
	public static void writeNewAccount(String newIban, String newBankID, double newBalance, String newUserID) {
		// TODO Auto-generated method stub
		BankAccount[] bankArray1 = new BankAccount[1];
		String tempIban = newIban;
		String tempBankID = newBankID;
		double tempBalance = newBalance;
		String tempUserID = newUserID;

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
	public static void readFile(String username, String password){ 
		BankAccount[] stTemp = new BankAccount[100]; // 100 here is an upper bound 
		int stIndex = 0; // keeps track of the line number 
		try{ 
			BufferedReader myFile = new BufferedReader (new FileReader("bankaccounts.txt")); 
			String sCurrentLine; 
			String[] uCurrent = new String[4]; 

			while ((sCurrentLine = myFile.readLine()) != null){ 
				uCurrent = sCurrentLine.split("\t"); 
				stTemp[stIndex] = new BankAccount(uCurrent[0], uCurrent[1],
						Double.parseDouble(uCurrent[2]),uCurrent[3]); 
				stIndex++; 
			} 
			myFile.close(); 

		} catch(IOException e){ 
			System.out.print("Wrong! (Reading)"); 
		} 
		BankAccount[] baArray = new BankAccount[stIndex]; 
		System.arraycopy(stTemp, 0, baArray, 0, stIndex); 
		
		for(int i = 0; i < baArray.length; i++) {
			if (baArray[i].userID.equals(currUserID)) {
				currAcc.balance = baArray[i].balance;
				currAcc.bankID = baArray[i].bankID;
				currAcc.iban = baArray[i].iban;
				currAcc.userID = currUserID;
				bankAccounts[0] = currAcc;
			}
		} 

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
				stTemp[stIndex] = new BankAccount(uCurrent[0], uCurrent[1],
						Double.parseDouble(uCurrent[2]), uCurrent[3]); 
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
	public BankAccount(String iban, String bankID, double balance, String userID) {
		this.iban = iban;
		this.bankID = bankID;
		this.balance = balance;
		this.userID = userID;
	}

	public BankAccount() {
		// TODO Auto-generated constructor stub
	} 
} 
