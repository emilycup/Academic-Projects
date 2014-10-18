//-----------------------------------------------------------------------------
// Project Name: Movie Stats
// Written by: Emily Le
// Last Modified: 05/17/2014
//-----------------------------------------------------------------------------

// This is a program that can be used to gather statistical data about the
// number of movies college students see in a year. The results will then be 
// printed to a file chosen by the user.

#include <iostream>
#include <fstream>
#include <stdlib.h>

using namespace std;

// Prototypes
void prompt_user(char *);
void sort_movies();
double calculate_average();
double calculate_median();
double calculate_mode();

// Global Variables
int number_of_students;
int *movies;


// Main method that calls on other methods to run program.
int main(int argc, char* argv[])
{
	// Edge Case: Incorrect number of parameters are provided at runtime. Will
	// exit and return 1 at execution.
	if (argc > 2 || argv[1] == NULL)
	{
		cerr << "1" << endl;
		exit(1);
	}

	prompt_user(argv[1]);
	delete []  movies;

	return 0;
}

// Function will display and prompt user for input of students and the number 
// movies that they watch. This function will also write the output to a user-
// defined file.
void prompt_user(char* file_name)
{

	// Opens a user-defined file.
	fstream output_file;
	output_file.open(file_name);

	// Prompts for number of surveyed students
	cout << "How many students were surveyed? ";
	output_file << "How many students were surveyed? ";
	cin >> number_of_students;

	// Edge Case: student of numbers cannot be 0 or a negative number or a letter
	if(number_of_students < 1 || cin.fail() || cin.peek() != 10)
	{
		cin.clear();
    cin.ignore(100, '\n');

		cout << "Number of students surveyed must be greater than 1." << endl;
		output_file << "Number of students surveyed must be greater than 1." 
								<< endl;
		exit(EXIT_FAILURE);
	}

	// Prompts for number of movies each student saw
	cout << "Enter the number of movies each student saw." << endl;
	output_file << "Enter the number of movies each student saw." << endl;
	movies = new int[number_of_students];

	for (int i = 0; i < number_of_students; i++)
	{
		cout << "Student " << i + 1 << ": ";
		output_file << "Student " << i + 1 << ": ";
		cin >> movies[i];

		// Edge Case: Number of movies can not be a negative number or a letter
		if (movies[i] < 0 || cin.fail() || cin.peek() != 10)
		{
			cin.clear();
   	  cin.ignore(100, '\n');

			cout << "Number of movies must be 0 or greater." << endl;
			output_file << "Number of movies must be 0 or greater." << endl;
			exit(EXIT_FAILURE);
		}
	}

	sort_movies();

	// Displays the movie statistics results: mean, median, mode. Will also
	// write the messages to the file.
	cout << "The average number of movies seen is: " << calculate_average() 
			 << endl;
	output_file << "The average number of movies seen is: " << 
		calculate_average() << endl;
	cout << "The median of the number of movies seen is: " << calculate_median() 
		   << endl;
	output_file << "The median of the number of movies seen is: " << 
		calculate_median() << endl;
	cout << "The mode of the number of movies seen is: " << calculate_mode()  
		 	 << endl;
	output_file << "The mode of the number of movies seen is: " << 
		calculate_mode() << endl;

	// closes file for writing
	output_file.close();
}

// Selection sort algorithm that sorts the dynamically allocated array. 
void sort_movies()
{
	int minimum, temp;
	for (int i = 0; i < number_of_students - 1; i++)
	{
		 minimum = i;
		for (int j = i + 1; j < number_of_students; j++)
		{
			if (movies[j] < movies[minimum])
			{
				minimum = j;
			}

			// Swap algorithm that will swap 2 elements
			temp = movies[i];
			movies[i] = movies[minimum];
			movies[minimum] = temp;

			// Resets the minimum value
			minimum = i;
		}
	}
}

// Function that calculates the average number of movies that the students have
// seen.
double calculate_average()
{
	double average;
	double sum = 0;

	for (int i = 0; i < number_of_students; i++)
	{
		sum += movies[i];
	}

	average = sum / number_of_students;

	return average;
}

// Fuction that calculates the median of movies that the studets have seen.
double calculate_median()
{
	int middle = number_of_students / 2;

	if(number_of_students % 2 == 0)
	{
		return (movies[middle] + movies[middle - 1]) / 2;
	}
	else
	{
		return movies[middle];
	}
}

// Function that calculates the mode of movies that the students have seen
double calculate_mode()
{
	int bigger_counter = 0;
	int counter = 1;
	int mode;

	for (int i = 0; i < number_of_students; i++)
	{
		if (movies[i] == movies[i + 1])
		{
			counter++;
			if (counter > bigger_counter)
			{
				bigger_counter = counter;
				mode = movies[i];
			}
		}
		else
		{
			counter = 1;
		}
	}

	return mode;
}

