package BankingApp;

import java.io.*;


public class User {
	private String UserName, Password;
	public String FirstName, LastName, Role;
	public int UserID;
	static int NumUser = 0; //To store the number of users
	static User[] myUsers = new User[100];


	// Constructors
	public User(int UserID, String FirstName, String LastName, String Role, String UserName, String Password){
		this.UserName = UserName;
		this.Password = Password;
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.UserID = UserID;
		this.Role = Role;
	}
	
	public User(String FirstName, String LastName, String UserName, String Password, int UserID, String Role){
		this.UserName = UserName;
		this.Password = Password;
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.UserID = UserID;
		this.Role = Role;
	}

	// Methods
	public static User[] ReadUserData() {
		String LocalFirstName, LocalLastName, LocalUserName, LocalPassword, LocalRole; 
		int LocalID;
		
		User[] my_users_Local = new User[100];
		String[] current_line = new String[5];

		// Set values to zero, because it would continue counting after previous login failed
		NumUser = 0;
		BankEmployee.Num_bank_employees = 0;
		AccountHolder.Num_account_holders = 0;

		try{
			BufferedReader my_reader = new BufferedReader(new FileReader("userdata.txt")); //declaring the reader object
			String input_line; //Variable to read line by line 
			while( (input_line=my_reader.readLine()) != null){
				
				current_line = input_line.split(","); //split the line at the tab
				//Current_line is an array:
				//Order in the database: FirstName, LastName, UserName, Password, ID, Role 
				LocalFirstName = current_line[0];
				LocalLastName = current_line[1];
				LocalUserName = current_line[2];
				LocalPassword = current_line[3];
				LocalID = Integer.parseInt(current_line[4]);
				LocalRole = current_line[5];


				if(LocalRole.equals("BankEmployee")){
					BankEmployee.Num_bank_employees++; 
				}
				if(LocalRole.equals("AccountHolder")){
					AccountHolder.Num_account_holders++;
				}
				my_users_Local[User.NumUser] = new User(LocalFirstName, LocalLastName, LocalUserName, LocalPassword, LocalID, LocalRole);
				User.NumUser++;
			}
			my_reader.close();
		}
		catch(IOException e){
			System.out.println("can't read file"); 
		}
		myUsers = my_users_Local;
		return(my_users_Local);
	}

	public int LoginMethod(String InUserName, String InPassword){ //returns the ID if login correct 
		if(UserName.equals(InUserName) && Password.equals(InPassword)){ //
			//Login Success: 
			System.out.println("--------------------------------------------------------"); 
			System.out.println("Welcome "+ FirstName + " " + LastName);
			return(UserID);
		}
		else return(0);
	}
	
	public String GetPassword(boolean LoggedIn){ 
		if(LoggedIn) return(Password); 
		else return("Not Logged In");
	}

	public String GetUserName(boolean LoggedIn){ 
		if(LoggedIn) return(UserName);
		else return("Not Logged In");
	}
	
	
	public static void viewMyBalance(int myUserID) { 
		User[] my_users = myUsers; 
		
		for(int i = 0; i < NumUser; i++) {
			if(my_users[i].UserID == myUserID) {
				System.out.println("\n***************************************");
				System.out.println("*************** Overview **************");
				System.out.println("***************************************");
				System.out.printf("IBAN: %s\n", BankAccount.returnIban(my_users[i].UserID));
				System.out.printf("Bank balance: %.2f\n\n", BankAccount.returnBalance(my_users[i].UserID));
			}
		}
	}
	
	
	public static void viewMyAccount(int myUserID) { 
		User[] my_users = myUsers; 
		
		for(int i = 0; i < NumUser; i++) {
			if(my_users[i].UserID == myUserID) {
				System.out.println("Your first name is: " + my_users[i].FirstName);
				System.out.println("Your last name is:  " + my_users[i].LastName);
				System.out.println("Your username is:   " + my_users[i].UserName);
				System.out.println("Your password is:   " + my_users[i].Password);
				System.out.println("Your user ID is:    " + my_users[i].UserID);
				System.out.println("Your IBAN is:       "+ BankAccount.returnIban(myUserID));
			}
		}
	}

	
	public static void viewClientAccount(int clientUserID) { 
		User[] my_users = myUsers; 
		
		for(int i = 0; i < NumUser; i++) {
			if(my_users[i].UserID == clientUserID) {
				System.out.println("The client's first name is: " + my_users[i].FirstName);
				System.out.println("The client's last name is:  " + my_users[i].LastName);
				System.out.println("The client's username is:   " + my_users[i].UserName);
				System.out.println("The client's password is:   *******");
				System.out.println("The client's user ID is:    " + my_users[i].UserID);
				System.out.println("The client's IBAN is:       "+ BankAccount.returnIban(clientUserID));
				System.out.println("The client's balance is:    " + BankAccount.returnBalance(clientUserID));
			}
		}
	}

	
	public static Boolean checkUsername(String newUsername) {
		User[] myUsersLocal = ReadUserData();
		Boolean existingUsername = false;

		for (int i = 0; i < NumUser; i++){
			if (newUsername.equals(myUsersLocal[i].UserName)) {
				existingUsername = true;
				break;
			}
		}
		return existingUsername;
	}
	
	
	public static void changeAccountElement(String newChangeElement, int loggedInUserID, int changeElement) {
		User[] my_users_local = myUsers; 
		
		String[] firstNameArray = new String[100];
		String[] lastNameArray 	= new String[100];
		String[] usernameArray 	= new String[100];
		String[] passwordArray 	= new String[100];
		int[] 	 userIDArray 	= new int[100];
		String[] roleArray 		= new String[100];
		
		for(int i = 0; i < NumUser; i++) {
			if(my_users_local[i].UserID == loggedInUserID) {
				if(changeElement == 1) {my_users_local[i].FirstName = newChangeElement;}
				else if(changeElement == 2) {my_users_local[i].LastName = newChangeElement;}
				else if(changeElement == 3) {my_users_local[i].Password = newChangeElement;}
				
			}
			firstNameArray[i] = my_users_local[i].FirstName;
			lastNameArray[i]  = my_users_local[i].LastName;
			usernameArray[i]  = my_users_local[i].UserName;
			passwordArray[i]  = my_users_local[i].Password;
			userIDArray[i] 	  = my_users_local[i].UserID;
			roleArray[i] 	  = my_users_local[i].Role;	
			

			Interface.overWriteFile(firstNameArray, lastNameArray, usernameArray, passwordArray, userIDArray, roleArray);
		}
	}
	
	
		public static void changeUserAccountElement(String newChangeElement, int loggedInUserID, int changeElement) {
		User[] my_users_local = myUsers; 
		
		String[] firstNameArray = new String[100];
		String[] lastNameArray 	= new String[100];
		String[] usernameArray 	= new String[100];
		String[] passwordArray 	= new String[100];
		int[] 	 userIDArray 	= new int[100];
		String[] roleArray 		= new String[100];
		
		for(int i = 0; i < NumUser; i++) {
			if(my_users_local[i].UserID == loggedInUserID) {
				if(changeElement == 1) {my_users_local[i].FirstName = newChangeElement;}
				else if(changeElement == 2) {my_users_local[i].LastName = newChangeElement;}
				else if(changeElement == 3) {my_users_local[i].UserName = newChangeElement;}
				else if(changeElement == 4) {my_users_local[i].Password = newChangeElement;}
				
			}
			firstNameArray[i] = my_users_local[i].FirstName;
			lastNameArray[i]  = my_users_local[i].LastName;
			usernameArray[i]  = my_users_local[i].UserName;
			passwordArray[i]  = my_users_local[i].Password;
			userIDArray[i] 	  = my_users_local[i].UserID;
			roleArray[i] 	  = my_users_local[i].Role;	
			

			Interface.overWriteFile(firstNameArray, lastNameArray, usernameArray, passwordArray, userIDArray, roleArray);
		}
	}
	
	
	public static void deleteAccount(int clientUserID) { //Needs some work. Not completely running okay yet.
		User [] my_users_old = myUsers;
		User [] my_users_new = new User [NumUser - 1];

		String[] firstNameArray = new String[100];
		String[] lastNameArray 	= new String[100];
		String[] usernameArray 	= new String[100];
		String[] passwordArray 	= new String[100];
		int[] 	 userIDArray 	= new int[100];
		String[] roleArray 		= new String[100];
		
		for(int i = 0, j = 0; i < NumUser; i++) {
			if(clientUserID != my_users_old[i].UserID) {
				my_users_new [j] = my_users_old [i];
			}

		firstNameArray[i] = my_users_new[j].FirstName;
		lastNameArray [i] = my_users_new[j].LastName;
		usernameArray [i] = my_users_new[j].UserName;
		passwordArray [i] = my_users_new[j].Password;
		userIDArray [i] = my_users_new[j].UserID;
		roleArray [i] = my_users_new[j].Role;	
		}
		
		try{
			PrintWriter wr = new PrintWriter(
					new BufferedWriter (new FileWriter ("userdata.txt", false)));			

		// Replace existing file with one fewer line
		for (int k = 0; k < User.NumUser-1; k++){
				wr.println(firstNameArray[k] + "," + lastNameArray[k] + "," + usernameArray[k] + "," + passwordArray[k] +
				 "," + userIDArray[k] + "," + roleArray[k]);
		}
		wr.close();																								
		}
		catch (IOException e){																						
			System.out.print ("There is an I/O error when writing.");												
		}
		// Print completion message
		System.out.println("User with UserID " + clientUserID + " has succesfully been deleted");
	}	

}
