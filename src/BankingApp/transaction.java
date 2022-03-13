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


	// Max method
	public static void depositFunds(int loggedInUserID, String loggedInIban) {
		Transaction[] transactions1 = new Transaction[1];
		
		
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String date = dateFormat.format(currentDate);
		String fromIban = "-";
		String toIban = loggedInIban; //iban of loggedin userId needs to be added!
		System.out.print("Please enter the amount to be deposited (+): ");
		double amount = userInputDouble.nextDouble();
		
		double newBalance = BankAccount.returnBalance(loggedInUserID) + amount;
		
		BankAccount.overwriteBalance(newBalance, loggedInUserID);

		transactions1[0] = new Transaction(date, fromIban, toIban, amount); // Does not work
		writeFile(transactions1);


		}

	public static void withdrawFunds(int loggedInUserID, String loggedInIban) {
		Transaction[] transactions1 = new Transaction[1];

		double balance = BankAccount.returnBalance(loggedInUserID); //logged in user's balance
		
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String date = dateFormat.format(currentDate);
		String fromIban = loggedInIban; //iban of loggedin userId needs to be added!
		String toIban = "-";
		System.out.print("Please enter the amount to be withdrawn: ");
		double amount = userInputDouble.nextDouble();
		
			while (amount > balance) {
				System.out.println("Withdrawal value exceeded your balance!");
				System.out.print("Please enter the amount to be withdrawn: ");
				amount = userInputDouble.nextDouble();
			}
		
			double newBalance = balance - amount;
		
		BankAccount.overwriteBalance(newBalance, loggedInUserID);

		transactions1[0] = new Transaction(date, fromIban, toIban, amount); // Does not work
		writeFile(transactions1);
	}

	
	// Sai still needs to fix this
	public static void transferFunds(int loggedInUserID, String loggedInIban) {
		Transaction[] transactions1 = new Transaction[1];
		double fromBalance = BankAccount.returnBalance(loggedInUserID);
		
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String date = dateFormat.format(currentDate);
		String fromIban = loggedInIban; //iban of loggedin userId needs to be added!
		System.out.print("Please enter the iban of recepient: ");
		String toIban = userInputString.nextLine();
		System.out.print("Please enter the amount to be transferred: ");
		double amount = userInputDouble.nextDouble();
			
			while (amount > fromBalance) {
				System.out.println("Insufficient Funds!");
				System.out.print("Please enter the amount to be transferred: ");
				amount = userInputDouble.nextDouble();
		}
		
		double toBalance = BankAccount.returnBalance(toIban);	//??Make method in BankAccount class to return balance based on Iban
			//local variables that represent new balances of both accounts 
			double updatedFromBalance = fromBalance - amount;
			double updatedToBalance = toBalance + amount;
			
		BankAccount.overwriteBalance(updatedFromBalance, loggedInUserID);
		
		BankAccount.overwriteBalance(updatedToBalance, toIban); //make another method in BankAccount so toIban bank balance is also changed
			
		transactions1[0] = new Transaction(date, fromIban, toIban, amount);
		writeFile(transactions1);
	}
	
	
	public static void searchTransactions(int loggedInUserID, String loggedInIban) {
		Transaction[] traArray = readFile();
		String localIban = loggedInIban;
		
		//getting user input for specific dates
		  System.out.print("Please enter the date of transactions you want to see (YYYY/MM/DD): ");
		  String localDate = userInputString.nextLine();
		  
		  System.out.println("Transaction History: ");
		  
		  for(int i= 0; i < numTra; i++) {
			  if (traArray[i].date.equals(localDate) && (traArray[i].fromIban.equals(localIban) || traArray[i].toIban.equals(localIban))) {
					System.out.println(traArray[i].date + "," + traArray[i].fromIban + "," + traArray[i].toIban + "," +
							traArray[i].amount);
				  	}
		  }
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
	

