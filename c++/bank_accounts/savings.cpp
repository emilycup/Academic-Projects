//-----------------------------------------------------------------------------
// Project Name: Bank Accounts
// Written by: Emily Le
// Last Modified: 06/11/2014
//-----------------------------------------------------------------------------

// This is the implementation of the savings class and serves is a child of the
// 'account' class

#include <iostream>
#include "savings.h"

using namespace std;

// A function that checks to see if the account is inactive before a withdrawal
// is made. A withdrawal is then made by calling the function of the base 
// class.
void Savings::withdraw(double amount) 
{
	Account::withdraw(amount);
}

// A function that checks to see if the account is inactive before a deposit is
// made. If the account is inactive and the deposit brings the balance above 
// $25, the account becomes active again. The deposit is then made by calling 
// the base class version of the function.
void Savings::deposit(double amount)
{
	Account::deposit(amount);
	isActive(amount);
}

// Before the base class function is called, this function checks the number of
// withdrawals. If the number of withdrawals for the month is more than 4, a 
// service fee of $1 is charged for each withdrawal and added to the base class
// service charge variable. Don't forget to check if the charges cause the
// account to go under $25.
void Savings::monthlyProc()
{
  if (Account::withdrawals > 4)
  {
  	Account::serviceCharges += Account::withdrawals;
  }

  Account::monthlyProc();
}

// Function will check whether or not the account is active. The status result 
// will be returned.
bool Savings::isActive(double balance)
{
	if (balance > 25)
	{
		this->status = true;
	}
	else
	{
		this->status = false;
	}
	
	return this->status;
}

// Function will reset all the vales in the account
void Savings::resetAccount()
{
	Account::resetAccount();
}

// Getter function that will return the balance.
double Savings::getBalance()
{
	return Account::getBalance();
}

// Getter function that will return the deposits
int Savings::getDeposits()
{
	return Account::getDeposits();
}

// Getter function that will return the withdrawals
int Savings::getWithdrawals()
{
	return Account::getWithdrawals();
}

// Getter function that will return the interst rate
double Savings::getInterestRate()
{
	return Account::getInterestRate();
}

// Getter function that will reuturn the service charges
double Savings::getServiceCharges()
{
	return Account::getServiceCharges();
}


