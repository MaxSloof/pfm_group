package groupPackagae;

import java.util.Scanner;

public class UserInput {
	static Scanner userInputInt = new Scanner(System.in);
	static Scanner userInputString = new Scanner(System.in);
	static Scanner userInputDouble = new Scanner(System.in);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int userChoice = getUserChoice();
		switch (userChoice) {
		case 1:
			openBankAccount();
			break;
		case 2:
			viewBalance();
			break;
		case 3:
			newEntry();
			break;
		case 4:
			removeCrypto();
			break;
		case 5:
			updateWallet();
			break;
		}
	}

	private static void viewBalance() {
		// TODO Auto-generated method stub
		acc1.balance
	}

	