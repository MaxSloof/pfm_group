package BankingApp;

import java.io.*;
import java.util.Scanner;


public class User {
	private String UserName, Password;
	public String FirstName, LastName, Role;
	public int UserID;
	static int NumUser=0; //To store the number of users

	public User(int UserID, String FirstName, String LastName, String Role, String UserName, String Password){
		this.UserName = UserName;
		this.Password = Password;
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.UserID = UserID;
		this.Role = Role;
	}

	public static User[] ReadUserData(){
		String LocalFirstName, LocalLastName, LocalUserName, LocalPassword, LocalRole; 
		int LocalID;
		boolean FirstLine= true;
		User[] my_users_Local = new User[20];
		String[] current_line = new String[5];

		try{
			BufferedReader my_reader = new BufferedReader(new FileReader("UserData.txt")); //declaring the reader object
			String input_line; //Variable to read line by line 
			while( (input_line=my_reader.readLine()) != null){
				if (FirstLine){
					FirstLine= false;
					continue; 
				}
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
				my_users_Local[User.NumUser] = new User(LocalID, LocalFirstName, LocalLastName, LocalRole, LocalUserName, LocalPassword);
				User.NumUser++;
			}
			my_reader.close();
		}
		catch(IOException e){
			System.out.println("can't read file"); 
		}
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
}