package BankingApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Usersmax {

	String userID;
	String username;
	String password;
	
	
	public static String checkLogin(String username, String password) {
		Usersmax[] uTemp = new Usersmax[100]; // 100 here is an upper bound 
		int uIndex = 0; // keeps track of the line number 
		try{ 
			BufferedReader myFile = new BufferedReader (new FileReader("users.txt")); 
			String sCurrentLine; 
			String[] uCurrent = new String[4]; 

			while ((sCurrentLine = myFile.readLine()) != null){ 
				uCurrent = sCurrentLine.split("\t"); 
				uTemp[uIndex] = new Usersmax(uCurrent[0], uCurrent[2], uCurrent[3]);
				
				uIndex++; 
			} 
			myFile.close(); 

		} catch(IOException e){ 
			System.out.print("Wrong! (Reading)"); 
		} 
		
		Usersmax[] uArray = new Usersmax[uIndex]; 
		System.arraycopy(uTemp, 0, uArray, 0, uIndex); 
		

		
		String currUserID = "";
		for(int i = 0; i < uArray.length; i++) {
			if (uArray[i].username.equals(username) && uArray[i].password.equals(password)) {
				currUserID = uArray[i].userID;
			}
		}
		
		return currUserID;
	}

	
	
	// Setter and Getter methods
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// Constructors
	public Usersmax(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	
	public Usersmax(String userID) {
		super();
		this.userID = userID;
	}



	public Usersmax(String userID, String username, String password) {
		super();
		this.userID = userID;
		this.username = username;
		this.password = password;
	}
	
}
