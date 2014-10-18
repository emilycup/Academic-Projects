//------------------------------------------------------------------------------
// Project Name: Population
// Written by: Emily Le
// Last Modified: 05/02/2014
//------------------------------------------------------------------------------

// In a population, the birth rate is the percentage increase of the population 
// due to births and the death rate is the percentage decrease of the population
// due to deaths. This program displays the size of a population for any number
// of years.

#include <iostream>
#include <iomanip>

using namespace std;

// Prototypes
void prompt_user(double &, double &, double &, double &);
void calculate_year_population_size();

// Global Variable Definitions
double population_size, annual_birth_rate, annual_death_rate, year;
const int MAX_INITIAL_POPULATION_SIZE = 2;
const double MAX_YEARS = 100;

// Main function that calls on other functions to run the program
int main()
{
	prompt_user(population_size, annual_birth_rate, annual_death_rate, year);
	calculate_year_population_size();

 	return 0;
}

// Function that prompts and stores a user's input
void prompt_user(double &population_size, double &annual_birth_rate, 
	double &annual_death_rate, double &year)
{
	cout << "This program calculates population change." << endl;

	// -- Initial Population Size --
	cout << "Enter the starting population size: ";
	cin >> population_size;
	// Edge Case: The starting population should be greater than or equal to 2
	while (population_size < MAX_INITIAL_POPULATION_SIZE || cin.fail())
	{
		cin.clear();
    cin.ignore(100, '\n');
		cout << "Starting population must be 2 or more." << endl << 
			"Please re-enter: ";
		cin >> population_size;
	}

	// -- Annual Birth Rate --
	cout << "Enter the annual birth rate (as % of current population): " << endl;
	cin >> annual_birth_rate;
	// Edge Case: The birth rate and death rate should be positive
	while (annual_birth_rate < 0 || cin.fail())
	{
		cin.clear();
		cin.ignore(100, '\n');
		cout << "Birth rate percent cannot be negative." << endl << 
			"Please re-enter: " << endl;
		cin >> annual_birth_rate;
	}

	// --  Death Rate --
	cout << "Enter the annual death rate (as % of current population): " << endl;
	cin >> annual_death_rate;
	while (annual_death_rate < 0 || cin.fail())
	{
		cin.clear();
		cin.ignore(100, '\n');
		cout << "Death rate percent cannot be negative." << endl << 
			"Please re-enter: " << endl;
		cin >> annual_death_rate;
	}

	// -- Number of Years --
	cout << "For how many years do you wish to view population changes? " << endl;
	cin >> year;
	// Edge Case: The number of years should a positive integer <= 100
	while ( year < 0 || year > MAX_YEARS || cin.fail())
	{
		cin.clear();
		cin.ignore(100, '\n');
		cout << "Years must be one or more, but less than or equal to 100." << endl 
			<< "Please re-enter: " << endl;
		cin >> year;
	}
}

// Calculates the size of the population for a number of years. The population  
// is determined by the formula: N = P + BP - DP
void calculate_year_population_size()
{
	// Disclaimer: casting a 'double' to an 'int' will lose precision
	int number_of_years = static_cast<int>(year);	
	double year_population_size[number_of_years];

	cout << endl << endl;
	cout << "Starting population: " << population_size << endl << endl;

	for (int i = 0; i < number_of_years; i++)
	{
		year_population_size[i] = population_size + (annual_birth_rate * 
			population_size) - (annual_death_rate * population_size);
		population_size = year_population_size[i];

		// Will check if population is a negative number
		if(year_population_size[i] < 0)
		{
			year_population_size[i] = 0;
			population_size = 0;
		}

		// Disclaimer: casting a 'double' to an 'int' will lose precision
    cout << "Population at the end of year " << i + 1 << " is " 
      << static_cast<int>(year_population_size[i]) << "." << endl;
	}
}

