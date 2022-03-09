package BankingApp;

public class BankEmployee extends User {
	static int Num_bank_employees=0;

	public BankEmployee(int UserID, String FirstName, String LastName, String Role, String UserName, String Password) {
		super(UserID, FirstName, LastName, Role, UserName, Password);
		// TODO Auto-generated constructor stub
	}

	public static BankEmployee[] FilterBankEmployees(User[] my_users_local){
		BankEmployee[] my_bank_employees_local = new BankEmployee[BankEmployee.Num_bank_employees]; //let the maximum number = 20 
		int counter =0;
		for(int i=0;i<User.NumUser;i++) {

			if(my_users_local[i].Role.equals("BankEmployee")){
				String username_local = my_users_local[i].GetUserName(true); 
				String password_local = my_users_local[i].GetPassword(true);

				my_bank_employees_local[counter]= new BankEmployee(my_users_local[i].UserID,my_users_local[i].FirstName, my_users_local[i].LastName, my_users_local[i].Role, username_local, password_local); 
				counter++;
			}
		}

		return(my_bank_employees_local);
	}
}