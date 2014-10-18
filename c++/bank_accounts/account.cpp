//-----------------------------------------------------------------------------
// Project Name: Bank Accounts
// Written by: Emily Le
// Last Modified: 06/11/2014
//-----------------------------------------------------------------------------

// This is the implementation of the account class and serves as the base class
// for the 'savings' and 'checking' classes.

#include "account.h"

// Constructor that will accept arguments for the balance and annual interest 
// rates.
Account::Account(double balance, double rate)
{
	this->balance = balance;
	this->interestRate = rate;
	this->serviceCharges = 0;
	this->withdrawals = 0;
	this->deposits = 0;
}

// Function will allow use to deposit a certain amount of money into the 
// account. 
void Account::deposit(double amount)
{
	this->balance += amount;
	this->deposits++;
}

// Function that accepts an argument for the amount of the withdrawal. The 
// function should subtract the argument from the balance. It should also 
// increment the variable holding the number of withdrawals.
void Account::withdraw(double amount)
{
	this->balance -= amount;
	this->withdrawals++;
}

// Function that updates the balance by calculating the monthly interest earned
// by the account, and adding this interest to the balance. Assume the annual 
// interest rate is 5 percent.
void Account::calcInt()
{
  double monthlyInterestRate = this->interestRate / 12;
  double monthlyInterest = this->balance * monthlyInterestRate;
  this->balance += monthlyInterest;
}

// Function that subtracts the monthly service charges from the balance, calls 
// the calcInt function, and sets the variables that hold the number of 
// withdrawals, number of deposits, and monthly service charges to zero.
void Account::monthlyProc()
{
	Account::calcInt();
	this->balance -= this->serviceCharges;
}

// Function will reset all of the values in the account
void Account::resetAccount()
{
	this->withdrawals = 0;
	this->deposits = 0;
	this->serviceCharges = 0;
}

// Getter function that will return the balance.
double Account::getBalance()
{
	return this->balance;
}

// Getter function that will return the deposits
int Account::getDeposits()
{
	return this->deposits;
}

// Getter function that will return the withdrawals
int Account::getWithdrawals()
{
	return this->withdrawals;
}

// Getter function that will return the interst rate
double Account::getInterestRate()
{
	return this->interestRate;
}

// Getter function that will reuturn the service charges
double Account::getServiceCharges()
{
	return this->serviceCharges;
}
