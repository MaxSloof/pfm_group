package BankingApp;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.text.SimpleDateFormat;

public class Transaction {

	static Scanner userInputInt = new Scanner(System.in);
	static Scanner userInputString = new Scanner(System.in);
	static Scanner userInputDouble = new Scanner(System.in);
	 
	String fromIban;
	String toIban;
	String date;
	
	double amount;
	double balance;
	static int numTra = 0; // Keeps track of number of Transactions
	
	
	static Transaction[] transactions = new Transaction[100];
// if user chooses to deposit funds into their account
	public static void depositFunds(int loggedInUserID) {
		Transaction[] transactions1 = new Transaction[1];
		double amount = 0;
		
		String loggedInIban = BankAccount.returnIban(loggedInUserID); //call loggedIn Iban
		Date currentDate = new Date();									//retrieve the current date when executing transaction
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String date = dateFormat.format(currentDate);		//formatting date in standardized form.
		String fromIban = "------------------";
		String toIban = loggedInIban; 
		
		//catch mismatch exception		
		boolean done = false;
		
		while(!done) {
			try {
				System.out.print("Please enter the amount to be deposited (+): ");
				userInputDouble.useLocale(Locale.US);		//forces decimal point
				amount = userInputDouble.nextDouble();		//gets user input
				done = true;
				
			} catch (InputMismatchException e) {
				System.out.println("Invalid Input! Enter a number (Format: 0000 OR 00000.00");
				userInputDouble.nextLine();			//requires the user to input again if format not correct 
		}
		}
		double newBalance = BankAccount.returnBalance(loggedInUserID) + amount;		//retrieve balanceo of user and update it
		
		BankAccount.overwriteBalance(newBalance, toIban);		//update BankAccounts.txt file with new balance
		System.out.println("****************************************************************"); 
		System.out.print("New Balance is: ");
		System.out.printf("%.2f %n",  newBalance);		//present user with their updated balance.

		transactions1[0] = new Transaction(date, fromIban, toIban, amount); 
		writeFile(transactions1);		//write to transactions.txt


		}
//If user chooses to withdraw funds form their account
	public static void withdrawFunds(int loggedInUserID) {
		Transaction[] transactions1 = new Transaction[1];
		String loggedInIban = BankAccount.returnIban(loggedInUserID); //get user's iban
		
		boolean done = false;
		double balance = BankAccount.returnBalance(loggedInUserID); //logged in user's balance
		double amount = 0;
		
		Date currentDate = new Date();					//Retreive today's date 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");		
		String date = dateFormat.format(currentDate);			//storing date in the correct format
		String fromIban = loggedInIban; 
		String toIban = "------------------";
		
		//catch input mismatches
		while(!done) {
			try {
		System.out.print("Please enter the amount to be withdrawn: ");
		userInputDouble.useLocale(Locale.US);
		amount = userInputDouble.nextDouble();
		
			while (amount > balance) {			//Ensures the user cannot withdraw/ transact more than their balance
				System.out.println("****************************************************************"); 
				System.out.println("Withdrawal value exceeded your balance!");
				System.out.print("Please enter the amount to be withdrawn: ");
				userInputDouble.useLocale(Locale.US);
				amount = userInputDouble.nextDouble();
			} done = true;
			} catch (InputMismatchException e) {
				System.out.println("Invalid Input! Enter a number (Format: 0000 OR 00000.00");
				userInputDouble.nextLine();
		}
		}
			double newBalance = balance - amount;
		
		BankAccount.overwriteBalance(newBalance, loggedInIban);		//update user's balance in BankAccount file
		System.out.println("****************************************************************"); 
		System.out.print("New Balance is: ");
		System.out.printf("%.2f %n", newBalance);			//print user's new balance

		transactions1[0] = new Transaction(date, fromIban, toIban, amount); 
		writeFile(transactions1);
	}

	
	//If user chooses to transfer funds to another account
	public static void transferFunds(int loggedInUserID) {
		Transaction[] transactions1 = new Transaction[1];
		
		double amount = 0;
		String localLogIban = BankAccount.returnIban(loggedInUserID);		//get loggedIn user iban
		double fromBalance = BankAccount.returnBalance(loggedInUserID);		// get loggedIn user's balance
		boolean done = false;
		
		Date currentDate = new Date();				//get today's date
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");		
		String date = dateFormat.format(currentDate);		//store date in the correct format.
		String fromIban = localLogIban; 
		String toIban = "";
		
		while(!done) {		
			try {
				System.out.print("Please enter the iban of recepient: ");
				toIban = userInputString.nextLine();
				done = true;
			} catch (InputMismatchException e) {
			System.out.println("Invalid Input! Please try again.");
			userInputString.nextLine();
	}
		}
		int toUserID = BankAccount.returnUserID(toIban);		//if the entered Iban exists in with our app, retrieves the userID of recepient 
																//	BUT logged In user cannot see the userID of the receiver.

		boolean noError = false;
		
		while(!noError) {				//catch input mismatches
			try {
				System.out.print("Please enter the amount to be transferred: ");
				userInputDouble.useLocale(Locale.US);				//force decimal point
				amount = userInputDouble.nextDouble();
					
					while (amount > fromBalance) {							//Ensures the user cannot withdraw/ transact more than their balance
						System.out.println("****************************************************************"); 
						System.out.println("Insufficient Funds!");
						System.out.print("Please enter the amount to be transferred: ");
						userInputDouble.useLocale(Locale.US);
						amount = userInputDouble.nextDouble();
				} noError = true;
			} catch (InputMismatchException e) {
				System.out.println("Invalid Input! Enter a number (Format: 0000 OR 00000.00");
				userInputDouble.nextLine();
			}
		}
		
		
		double toBalance = BankAccount.returnBalance(toUserID);				//based on reterived UserID, gets thier balance if IBan is registered with our app.

		//local variables that represent new balances of both accounts 
		double updatedFromBalance = fromBalance - amount;
		double updatedToBalance = toBalance + amount;
			
		BankAccount.overwriteBalance(updatedFromBalance, fromIban);			//updates loggedIn user's balance
		System.out.println("****************************************************************"); 
		System.out.println("New Balance is: " + updatedFromBalance);
		
		BankAccount.overwriteBalance(updatedToBalance, toIban); 			//updatesss receiver's balance, IF Iban entered is registered with our app.
			
		transactions1[0] = new Transaction(date, fromIban, toIban, amount);
		writeFile(transactions1);
	}
	
	//if user wants to see certain dates of transaction
	public static void searchTransactions(int loggedInUserID) {
		Transaction[] traArray = readFile();
		String loggedInIban = BankAccount.returnIban(loggedInUserID);

		String[] returnedDate = new String[100];
		String[] returnedFromIban = new String[100];
		String[] returnedToIban = new String[100];
		double[] returnedAmount = new double[100];
		
		int j = 0; // Counter for returned.. arrays

		//getting user input for specific dates
		System.out.print("Please enter the date of transactions you want to see (YYYY/MM/DD): ");
		String localDate = userInputString.nextLine();
		
		System.out.println("\nYour IBAN: " + loggedInIban);
		System.out.println("--------------------------------------------------------");
		System.out.println("Transaction History: ");
		  
		for(int i= 0; i < numTra; i++) {			//To find transactions made ONLY by loggedIn user depending on date
			if (traArray[i].date.equals(localDate) && (traArray[i].fromIban.equals(loggedInIban) || traArray[i].toIban.equals(loggedInIban))) {
				returnedDate[j] = traArray[i].date;
				returnedFromIban[j] = traArray[i].fromIban;
				returnedToIban[j] = traArray[i].toIban;
				returnedAmount[j] = traArray[i].amount;
				j++;
				}
		
		}
		if(returnedDate[0] != null) {		//prints transactions in console if found, otherwise prints "no transactions"
			System.out.println("Date 	   | From Bank Account  | To Bank Account    | Amount (Euros)");
			for(int i = 0; i < j;i++) {
				System.out.println(returnedDate[i] + " | " + returnedFromIban[i] + " | " + returnedToIban[i] + " | " +
					returnedAmount[i]); }
		} else System.out.println("No Transactions found");
		
	}
	
	// Gets an array of transaction objects and appends it to the file 
	 public static void writeFile(Transaction[] transactions1){ 
		 try{ 
				PrintWriter myFile = new PrintWriter ( 
						new BufferedWriter ( new FileWriter ("transactions.txt", true))); 
				myFile.printf("%s,%s,%s,%.2f %n",transactions1[0].date, transactions1[0].fromIban,
						transactions1[0].toIban, transactions1[0].amount); 


				myFile.close(); 
			} catch(IOException e){ 
				System.out.print("Wrong! (writing)"); 
			} 
		}
	 
	 public static Transaction[] readFile() {
			// TODO Auto-generated method stub
			Transaction[] tra_local = new Transaction[100]; // 100 here is an upper bound 
			String date;
			String fromIban, toIban;
			double amount;
			String[] current_line = new String[3]; 
			numTra = 0;
			try{ 
				BufferedReader myFile = new BufferedReader (new FileReader("transactions.txt")); 
				String input_line; 

				while ((input_line = myFile.readLine()) != null){ 
					current_line = input_line.split(","); 

					date = current_line[0];
					fromIban = current_line[1];
					toIban = current_line[2];
					amount = Double.parseDouble(current_line[3]);

					tra_local[Transaction.numTra] = new Transaction(date, fromIban, 
					toIban, amount);
					Transaction.numTra++;
				} 
				myFile.close();
			} catch(IOException e){ 
				System.out.print("Wrong! (writing)"); 
			} 
		
		return(tra_local);
		}
	 
	  // Constructors 
		public Transaction(String date, String fromIban, String toIban, double amount) {
			this.date = date;
			this.fromIban = fromIban;
			this.toIban = toIban;
			this.amount = amount; 
		}

		public Transaction() {
		// TODO Auto-generated constructor stub
		}
	 } 
	

