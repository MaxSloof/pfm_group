package BankingApp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class AccountHolder extends User {
	static int Num_account_holders=0;
	double Balance=0;

	public AccountHolder(int UserID, String FirstName, String LastName, String Role, String UserName, String Password) {
		super(UserID, FirstName, LastName, Role, UserName, Password); 

		// TODO Auto-generated constructor stub
	}
	
	public static AccountHolder[] ReadAccountHolderBalance(User[] my_users_local){ 
		int LocalID;
		double LocalBalance;
		AccountHolder[] my_account_holders_local = new AccountHolder[20]; //let the maximum number =20 
		String[] current_line = new String[2];
		int count = 0;
		try{
			BufferedReader my_reader = new BufferedReader(new FileReader("AccountHolderBalance.txt")); //declaring the reader object
			String input_line; //variable to read line by line
			while( (input_line=my_reader.readLine()) != null){
				current_line = input_line.split(","); //split the line at the tab 
				//current_line is an array:
				//order in the database: FirstName, LastName, UserName, Password, ID, Role 
				LocalID = Integer.parseInt(current_line[0]);
				LocalBalance = Double.parseDouble(current_line[1]);

				for(int i=0; i<User.NumUser;i++){ 
					if(my_users_local[i].UserID == LocalID){
						String username_local = my_users_local[i].GetUserName(true); 
						String password_local = my_users_local[i].GetPassword(true);
						my_account_holders_local[count] = new 
								AccountHolder(my_users_local[i].UserID,my_users_local[i].FirstName, my_users_local[i].LastName, my_users_local[i].Role, username_local, password_local); 
						my_account_holders_local[count].Balance = LocalBalance;
						count++;
						break;
					}
				}

			}
			my_reader.close();
		}
		catch(IOException e){ 
			System.out.println("can't read file"); 
		}
		return(my_account_holders_local);
	}
}