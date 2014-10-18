//-----------------------------------------------------------------------------
// Project Name: Bank Accounts
// Written by: Emily Le
// Last Modified: 06/11/2014
//-----------------------------------------------------------------------------

#include <iostream>
#include <stdlib.h> 
#include <iomanip>
#include <fstream>

// User-defined headers
#include "account.h"
#include "savings.h"
#include "checking.h"

using namespace std;

// Prototypes
void prompt_user();
void depositChecking();
void depositSavings();
void withdrawChecking();
void withdrawSavings();
void printStatistics();

// Initializes the following objects: Accounts, Savings, and Checking.
Savings savings;
Checking checking;

// Global Variables
fstream outputFile;
fstream inputFile;
double userAmount;


int main(int argc, char* argv[])
{
	if (argc != 3)
	{
		cerr << "1" << endl;
		exit(1);
	}

	inputFile.open(argv[1]);
	outputFile.open(argv[2]);

	while (true)
		prompt_user();

	return 0;
}

// Function will prompt the user for necessary input
void prompt_user()
{ 
	int choice;
	bool validInput = true;

	while (true)
	{
		outputFile << endl << endl;
		outputFile << "******** BANK ACCOUNT MENU ********" << endl;
		outputFile << endl;
		outputFile << "1.  Savings Account Deposit" << endl;
		outputFile << "2.  Checking Account Deposit" << endl;
		outputFile << "3.  Savings Account Withdrawal" << endl;
		outputFile << "4.  Checking Account Withdrawal" << endl; 
		outputFile << "5.  Update and Display Account Statistics" << endl;
		outputFile << "6.  Exit"  << endl << endl;
		outputFile << "Your choice, please: (1-6)  ";
		inputFile >> choice;

		// checks to see if user input is valid
		do
		{
			if (choice != 1 && choice != 2 && choice != 3 && choice != 4 && 
				 choice != 5 && choice != 6)
			{
				validInput = false;
				outputFile << "Enter a number from 1 through 6 please: ";
				inputFile >> choice;
			}
			else
				validInput = true;
		} while (!validInput);

		// series of choices a user can choose from
		switch (choice)
		{
			case 1:
				depositSavings();
				break;
			case 2:
				depositChecking();
				break;
			case 3:
				withdrawSavings();
				break;
			case 4:
				withdrawChecking();
				break;
			case 5:
				printStatistics();
				break;
			case 6:
				inputFile.close();
				outputFile.close();
				exit(EXIT_SUCCESS);
				break;
		}
	}
}

// Choice 1: deposits a user-defined amount in the savings account.
void depositSavings()
{
	do
	{
		outputFile << "Enter amount to deposit: ";
		inputFile >> userAmount;
	} while (userAmount < 0);

	savings.deposit(userAmount);
}

// Choice 2: deposits a user-defined amount in the checking account.
void depositChecking()
{
	do
	{
		outputFile << "Enter amount to deposit: ";
		inputFile >> userAmount;
	} while (userAmount < 0);

	checking.deposit(userAmount);
}

// Choice 3: allows user to withdraw a user-defined amount from the savings
// account.
void withdrawSavings()
{ 
	do 
	{
		outputFile << "Enter amount to withdraw: ";
		inputFile >> userAmount;
	} while (userAmount < 0);
		
	if (!savings.isActive(savings.getBalance()))
	{
		outputFile << "Account is inactive." << endl << endl;
	}
	else 
	{
		double tempBalance = savings.getBalance();
		tempBalance -= userAmount;	

		if (savings.isActive(tempBalance))
		{
			savings.withdraw(userAmount);
		}
		else
		{
			outputFile << endl;
			outputFile << "Your account has fallen below $25.00." << endl;
			outputFile << "It will be deactivated." << endl;
		}
	}
}

// Choice 4: allows user to withdraw a user-defined amount from the checking
// account.
void withdrawChecking()
{
	do
	{
		outputFile << "Enter amount to withdraw: ";
		inputFile >> userAmount;
	} while (userAmount < 0);

	if (userAmount > checking.getBalance())
	{
		outputFile << "You are attempting to withdraw more than the account " 
							 << "balance." << endl;
	}
	else
	{
		checking.withdraw(userAmount);
	}
}

// Choice 5: prints the statistics of a user's account.
void printStatistics()
{
	// Savings account statistics
	savings.monthlyProc();
	outputFile << endl << endl;
	outputFile << "SAVINGS ACCOUNT MONTHLY STATISTICS:" << endl;
	outputFile << "Withdrawals:\t\t" << savings.getWithdrawals() << endl;
	outputFile << "Deposits:\t\t" << savings.getDeposits() << endl;
	outputFile << "Service Charges:\t" << fixed << setprecision(2) << 
			 savings.getServiceCharges() << endl;
	outputFile << endl;
	outputFile << "Ending Balance:\t\t" << fixed << setprecision(2) << 
	 		 savings.getBalance() << endl;
	savings.resetAccount();

	outputFile << endl << endl << endl;

	// Checking account statistic
	checking.monthlyProc();
	outputFile << "CHECKING ACCOUNT MONTHLY STATISTICS:" << endl;
	outputFile << "Withdrawals:\t\t" << checking.getWithdrawals() << endl;
	outputFile << "Deposits:\t\t" << checking.getDeposits() << endl;
	outputFile << "Service Charges:\t" << fixed << setprecision(2) << 
			 checking.getServiceCharges() << endl;
	outputFile << endl;
	outputFile << "Ending Balance:\t\t" << fixed << setprecision(2) << 
		 	 checking.getBalance() << endl << endl;
	checking.resetAccount();
}




