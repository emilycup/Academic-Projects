#include <iostream>

using namespace std;

// main function
int main()
{
	// base isp rates for each package
	const double package_A = 9.95;
	const double package_B = 14.95;
	const double package_C = 19.95;

	double hours = 0;
	double total_amount_due = 0;
	int userInput;

	// creates the main menu that will appear at the start of the program. 
	// The user will choose between 4 choices: Package A, Package B, Package
	// C, or Quit.
	cout << "Select a subscription package: " << endl;
	cout << "1. Package A\n2. Package B\n3. Package C\n4. Quit\n" << endl;
	cin >> userInput;

	// executes certain commands given a user's input
	switch(userInput)
	{
		// Package A: For $9.95 per month 10 hours of access are provided. 
		// Additional hours are $2.00 per hour.
		case 1:
			cout << "How many hours were used? ";
			cin >> hours;
			// edge case in which a user enters a value not in range of 0.00-744.00
			// edge case in which the user enters a value that is a string/char
			if (hours < 0.00 || hours > 744.00 || cin.fail())
			{
				cout << "The hours used must be between 0.00 and 744.00." << endl;
			}
			else
			{
				if (hours <= 10.00)
				{
					cout << "The total amount due is $" << package_A << endl;
				}
				else
				{
					hours -= 10.00;
					total_amount_due = (hours * 2.00) + package_A;
					cout << "The total amount due is $" << total_amount_due << endl;
				}
			}
			break;

		// Package B: For $14.95 per month 20 hours of access are provided. 
		// Additional hours are $1.00 per hour.
		case 2: 
			cout << "How many hours were used? ";
			cin >> hours;
			// edge case in which a user enters a value not in range of 0.00-744.00
			// edge case in which the user enters a value that is a string/char
			if (hours < 0.00 || hours > 744.00 || cin.fail())
			{
				cout << "The hours used must be between 0.00 and 744.00." << endl;
			}
			else
			{
				if (hours <= 20.00)
				{
					cout << "The total amount due is $" << package_B << endl;
				}
				else
				{
					hours -= 20.00;
					total_amount_due = (hours * 1.00) + package_B;
					cout << "The total amount due is $" << total_amount_due << endl;
				}
			}
			break;

		// package C: For $19.95 per month unlimited access is provided.
		case 3:
			cout << "How many hours were used? ";
			cin >> hours;
			// edge case in which a user enters a value not in range of 0.00-744.00
			// edge case in which the user enters a value that is a string/char
			if (hours < 0.00 || hours > 744.00 || cin.fail())
			{
				cout << "The hours used must be between 0.00 and 744.00." << endl;
			}
			else
			{
				cout << "The total amount due is $" << package_C << endl;
			}	
			break;

		// exit
		case 4:
			// edge case in which the user exits the program
			cout << "Program ending." << endl;
			return(0);
			break;

		// edge case in which the user enters an invalid value. ie. 1, 2, 3, or 4
		default:
			cout << "The valid choices are 1 through 4. Run the" << endl 
			<< "program again and select one of those." << endl;
			break;
	}

	return 0;
}