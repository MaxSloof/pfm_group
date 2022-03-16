package BankingApp;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Interface {

	static Scanner userInputInt = new Scanner(System.in); 
	static Scanner userInputString = new Scanner(System.in); 
	static Scanner userInputDouble = new Scanner(System.in);

	static Scanner my_scan = new Scanner(System.in); //declaring scanner object to scan input from the user
	static Scanner my_scanINT = new Scanner(System.in); //declaring scanner object to scan input from the user
	static AccountHolder[] AccountHolderArray = new AccountHolder[100]; //let the maximum number = 20 
	static BankEmployee[] BankEmployeeArray = new BankEmployee[100]; //let the maximum number = 20 
	static String[] current_line = new String[5];

	static String TempFirstName, TempLastName, TempNewUserName, TempNewUserPassword, TempNewUserPasswordConfirm, AccountType;
	static int TempNewUserID; 


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int userChoice=0;
		while(true){
			//second: login
			boolean BankEmployeeLoggedIn=false, AccountHolderLoggedIn=false; //turn true when either bank employee or account holder logs in
			System.out.println("--------------------------------------------------------"); 
			System.out.println("Welcome to our banking app, please create an account or if you have one login below."); 
			System.out.println("Please choose your option:"); 
			System.out.println("--------------------------------------------------------"); 
			System.out.println("(0) Sign Up");
			System.out.println("(1) Login");
			System.out.println("(2) Exit"); 
			System.out.println("--------------------------------------------------------"); 
			
			Boolean done = false;
			
			while(!done)	
			try {
				System.out.print("Please enter your choice (0, 1 or 2): ");
				userChoice = my_scanINT.nextInt();
				done = true;
				
			} catch (InputMismatchException e) {
				System.out.println("Invalid Input! Enter a number (0, 1 or 2) ");
				my_scanINT.nextLine();
			}
			
			// User chooses the option to sign up
			if(userChoice==0) SignUp();
			
			// User chooses the option to log in
			if(userChoice==1){
				User[] my_users = User.ReadUserData();
				AccountHolder[] my_account_holders = AccountHolder.accountHolderIDArray(my_users); //returns array of account holders together with balances
				BankEmployee[] my_bank_employees = BankEmployee.FilterBankEmployees(my_users);
		
				int LoggedInID=0, LoggedInIndex=1000; //ID is the parameter of the object, index is its location in the users array
				System.out.print("Username: ");
				String InputUserName = my_scan.nextLine(); 
				System.out.print("Password: ");
				String InputUserPassword = my_scan.nextLine();

				// First read the bank employee DB to see if user is an employee
				for(int i=0;i<BankEmployee.Num_bank_employees;i++){
					LoggedInID = my_bank_employees[i].LoginMethod(InputUserName, InputUserPassword); 
					if(LoggedInID>0){
						//Login Success
						BankEmployeeLoggedIn=true;
						LoggedInIndex = i;
						break;
					}
				}
				// If not found in the bank employee DB, look into the account holder DB 
				if(LoggedInID==0){
					for(int i=0;i<AccountHolder.Num_account_holders;i++){
						LoggedInID = my_account_holders[i].LoginMethod(InputUserName, InputUserPassword); 
						if(LoggedInID>0){
							//Login Success
							AccountHolderLoggedIn=true;
							LoggedInIndex = i;
							break;
						}
					}
				}

				if(LoggedInID==0) System.out.println("Login Failed!"); //Finished the for loop without finding the user either in bank employee or account holder DB:

				else if(BankEmployeeLoggedIn) BankEmployeeInterface(my_bank_employees[LoggedInIndex],my_account_holders); 
				else if(AccountHolderLoggedIn) AccountHolderInterface(my_account_holders[LoggedInIndex]);
			}
			
			// User chooses the option to exit and exit message appears
			else if (userChoice==2){
				System.out.println("BYE!");
				break; 
			}
			
			// User enters anything other than options 0, 1 or 2
			else System.out.println("Please enter a valid choice");
		} 
	}
	
	// User details were found in the account holder DB, user will be logged in
	public static void AccountHolderInterface(AccountHolder my_loggedIn_account_holder){ 

		System.out.println("You are now accessing the Account Holder's interface");

		while(true){
			System.out.println("--------------------------------------------------------"); 
			System.out.println("Please select from the menu option below");
			System.out.println("(1) View my Balance");
			System.out.println("(2) Deposit to my Account");		
			System.out.println("(3) Withdraw from my Account");		
			System.out.println("(4) Transfer from my Account");		
			System.out.println("(5) View Transaction History");	
			System.out.println("(6) View my Account");			
			System.out.println("(7) Modify my Account");		
			System.out.println("(8) Logout"); 
			System.out.println("--------------------------------------------------------"); 
			System.out.print("Please enter your choice (1, 2, 3, 4, 5, 6, 7 or 8): ");
			int userChoice = my_scanINT.nextInt();

			// Retrieve balance from BankAccount class
			if(userChoice==1){
				User.viewMyBalance(my_loggedIn_account_holder.UserID);
			}
			
			// Retrieve from Transaction class
			else if(userChoice==2) {
				Transaction.depositFunds(my_loggedIn_account_holder.UserID);
			}

			else if(userChoice==3) {
				Transaction.withdrawFunds(my_loggedIn_account_holder.UserID);
			}
			
			else if(userChoice==4) {
				Transaction.transferFunds(my_loggedIn_account_holder.UserID);
			}
			
			else if(userChoice==5){
				Transaction.searchTransactions(my_loggedIn_account_holder.UserID);
			} 

			else if(userChoice==6) {
				User.viewMyAccount(my_loggedIn_account_holder.UserID);
			}

			else if(userChoice==7) {
				System.out.println("--------------------------------------------------------"); 
				System.out.println("What Account Detail would you like to modify?");
				System.out.println("(1) Change my First Name");		
				System.out.println("(2) Change my Last Name");		
				System.out.println("(3) Change my Username");		
				System.out.println("(4) Change my Password"); 		
				System.out.println("--------------------------------------------------------"); 
				System.out.print("Please enter your choice (1, 2, 3 or 4): ");
				int userChoice1 = my_scanINT.nextInt();	
				
				if(userChoice1 == 1) {System.out.print("My new first name: ");}
				else if(userChoice1 == 2) {System.out.print("My new last name: ");}
				else if(userChoice1 == 3) {System.out.print("My new username: ");}
				else if(userChoice1 == 4) {System.out.print("My new password: ");}
				
				String input = userInputString.nextLine();
					User.changeAccountElement(input, my_loggedIn_account_holder.UserID, userChoice1);
			}

			else if(userChoice==8){
				System.out.println("You are logged out"); break;
			} 


			else System.out.println("Please enter valid choice");
		}
	}



	public static void BankEmployeeInterface(BankEmployee my_loggedIn_bank_employee, AccountHolder[] my_account_holders_local){ 

		System.out.println("You are now accessing the Bank Employee's interface");

		while(true){
			System.out.println("--------------------------------------------------------"); 
			System.out.println("Please select from the menu option below"); 
			System.out.println("(1) View Client Account Details"); 		
			System.out.println("(2) Modify Client Account Details");	
			System.out.println("(3) Delete Client Account");		
			System.out.println("(4) Logout"); 
			System.out.println("--------------------------------------------------------");
			System.out.print("Please enter your choice (1, 2, 3 or 4): ");
			int userChoice = my_scanINT.nextInt();

			if(userChoice==1){						
				int inputUserID = 0;
				boolean done = false;
			
				while(!done) {	
					try {
						System.out.print ("What is the UserID of the Client Account you want to view?: ");									
						inputUserID = userInputInt.nextInt();
						done = true;	
					} 
					catch (InputMismatchException e) {
						System.out.println("Invalid Input! Enter a number (Format: 000)");
						userInputInt.nextLine();
					}
				}		

				User.viewClientAccount(inputUserID);
			}	

			else if(userChoice==2) {
				int inputUserID = 0;
				boolean done = false;
			
				while(!done) {	
					try {
						System.out.print ("What is the UserID of the Client Account you want to modify?: ");									
						inputUserID = userInputInt.nextInt();
						done = true;	
					} 
					catch (InputMismatchException e) {
						System.out.println("Invalid Input! Enter a number (Format: 000)");
						userInputInt.nextLine();
					}
				}		

				System.out.println("--------------------------------------------------------"); 
				System.out.println("What Client Account Detail would you like to modify?");
				System.out.println("(1) Change Client First Name");		
				System.out.println("(2) Change Client Last Name");		
				System.out.println("(3) Change Client Username");		
				System.out.println("(4) Change Client Password"); 		
				System.out.println("--------------------------------------------------------"); 
				System.out.print("Please enter your choice (1, 2, 3 or 4): ");
				int userChoice1 = my_scanINT.nextInt();	
				
				if(userChoice1 == 1) {System.out.print("Account Holder's new first name: ");}
				else if(userChoice1 == 2) {System.out.print("Account Holder's new last name: ");}
				else if(userChoice1 == 3) {System.out.print("Account Holder's new username: ");}
				else if(userChoice1 == 4) {System.out.print("Account Holder's new password: ");}
				String input = userInputString.nextLine();
					User.changeAccountElement(input, inputUserID, userChoice1);

			}

			else if(userChoice==3) {
				int inputUserID = 0;
				boolean done = false;

				while(!done) {	
					try {
						System.out.print ("What is the UserID of the Client Account you want to delete?: ");									
						inputUserID = userInputInt.nextInt();
						done = true;	
					} 
					catch (InputMismatchException e) {
						System.out.println("Invalid Input! Enter a number (Format: 000)");
						userInputInt.nextLine();
					}
				}	

				User.deleteAccount(inputUserID);
			}

			else if(userChoice==4){
				System.out.println("You are logged out"); break;
			} 

			else System.out.println("Please enter valid choice");
		}
	}
	
	// User sign up method
	public static void SignUp() {
		System.out.print("Please enter your first name: ");
		TempFirstName = my_scan.nextLine(); 
		System.out.print("Please enter your last name: ");
		TempLastName = my_scan.nextLine(); 
		System.out.print("Please enter your username: ");
		TempNewUserName = my_scan.nextLine(); 
		
		// Check whether new username already exists
		Boolean existingUsername = User.checkUsername(TempNewUserName);
		while (existingUsername == true) {
			System.out.println("Username already exists. Please try a new username");
			System.out.print("Please enter your username: ");
			TempNewUserName = my_scan.nextLine(); 
			existingUsername = User.checkUsername(TempNewUserName);
		}

		System.out.print("Please enter your password: ");
		TempNewUserPassword = my_scan.nextLine(); 
		System.out.print("Please confirm your password: ");
		TempNewUserPasswordConfirm = my_scan.nextLine(); 

		// If the two passwords do not match, retry
		while (!TempNewUserPassword.equals(TempNewUserPasswordConfirm)) {
			System.out.println("Please re-enter password");
			System.out.print("Enter new password: ");
			TempNewUserPassword = userInputString.nextLine();
			System.out.print("Confirm password: ");
			TempNewUserPasswordConfirm = userInputString.nextLine();
		}

		// Create new UserID
		TempNewUserID = returnIndex()+100;
		AccountType = "AccountHolder";

		AccountHolderArray[AccountHolder.Num_account_holders] = new AccountHolder(TempNewUserID, TempFirstName, TempLastName, AccountType, TempNewUserName, TempNewUserPassword);
		appendFile(TempFirstName, TempLastName, TempNewUserName, TempNewUserPassword, TempNewUserID, AccountType);
		BankAccount.writeNewAccount(TempNewUserID);
		
	}


	public static void appendFile(String TempFirstName, String TempLastName, String TempNewUserName, String TempNewUserPassword, int TempNewUserID, String AccountType){

		try{	
			PrintWriter wr = new PrintWriter(
					new BufferedWriter (new FileWriter("userdata.txt", true)));	
			wr.println(TempFirstName + "," + TempLastName + "," + TempNewUserName + "," + TempNewUserPassword + "," + TempNewUserID + "," + AccountType);
			wr.close();
		}
		catch(IOException e){
			System.out.print("There is an I/O error when writing.");
		}
	}


	public static void overWriteFile(String[] TempFirstName, String[] TempLastName, String[] TempNewUserName, 
	String[] TempNewUserPassword, int[] TempNewUserID, String[] AccountType){
		// TODO
		try{
			PrintWriter wr = new PrintWriter(
					new BufferedWriter (new FileWriter ("userdata.txt", false)));			
			for (int i = 0; i < User.NumUser; i++){
				wr.println(TempFirstName[i] + "," + TempLastName[i] + "," + TempNewUserName[i] + "," + TempNewUserPassword[i] +
				 "," + TempNewUserID[i] + "," + AccountType[i]);
			}
			wr.close();																								
		} 
		catch (IOException e){																						
			System.out.print ("There is an I/O error when writing.");												
		}
		
	}	

	
	public static int returnIndex(){ 
		Interface[] stTemp = new Interface[100]; // 100 here is an upper bound 
		int stIndex = 0; // keeps track of the line number 
		try{ 
			BufferedReader myFile = new BufferedReader (new FileReader("userdata.txt")); 

			while ((myFile.readLine()) != null){ 
				stIndex++; 
			} 
			myFile.close(); 

		} catch(IOException e){ 
			System.out.print("Wrong! (Reading)"); 
		} 
		Interface[] baArray = new Interface[stIndex]; 
		System.arraycopy(stTemp, 0, baArray, 0, stIndex); 
		return stIndex; 

	}			
}
