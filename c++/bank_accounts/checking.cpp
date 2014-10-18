//-----------------------------------------------------------------------------
// Project Name: Bank Accounts
// Written by: Emily Le
// Last Modified: 06/11/2014
//-----------------------------------------------------------------------------

// This is the implementation of the savings class and serves is a child of the
// 'account' class

#include <iostream>
#include "checking.h"

// Global Variables
const double FEE_PER_WITHDRAWAL = 0.10;
const double INSUFICIENT_SERVICE_CHARGE = 15.0;

// Before the base class function is called, this function will determine if a 
// withdrawal will cause the balance to go below $0. If the balance goes below 
// $0, a service charge of $15 will be taken from the account and the 
// withdrawal will not go through. If there isn't enough money in the account, 
// then the balance will go negative and the customer will owe the bank.
void Checking::withdraw(double amount)
{
	double tempBalance = Account::balance;
	tempBalance -= amount;

	if (tempBalance < 0)
	{
		balance -= INSUFICIENT_SERVICE_CHARGE;
	}
	else
	{
		Account::withdraw(amount);
	}
}

// Function will allow use to deposit a certain amount of money into the 
// account. This function will call the Base function from the 'account' class.
void Checking::deposit(double amount) 
{
	Account::deposit(amount);
}

// Before the base class function is called, this function adds the monthly fee
// of $5 plus $0.10 per withdrawal to the base class variable that holds the 
// monthly service charge.
void Checking::monthlyProc()
{
  Account::serviceCharges += (Account::withdrawals * FEE_PER_WITHDRAWAL);
	Account::monthlyProc();
}

// Function will reset all the values in the account.
void Checking::resetAccount()
{
	Account::withdrawals = 0;
	Account::deposits = 0;
	Account::serviceCharges = 5;
}

// Getter function that will return the balance.
double Checking::getBalance()
{
	return Account::getBalance();
}

// Getter function that will return the deposits
int Checking::getDeposits()
{
	return Account::getDeposits();
}

// Getter function that will return the withdrawals
int Checking::getWithdrawals()
{
	return Account::getWithdrawals();
}

// Getter function that will return the interst rate
double Checking::getInterestRate()
{
	return Account::getInterestRate();
}

// Getter function that will reuturn the service charges
double Checking::getServiceCharges()
{
	return Account::getServiceCharges();
}



