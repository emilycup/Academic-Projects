#ifndef CHECKING_H
#define CHECKING_H

#include "account.h"

class Checking : public Account
{
public:
	Checking() : Account(0,0.05) 
	{
		Account::serviceCharges = 5;
	}
	void withdraw(double);
	void deposit(double);
	void monthlyProc();
	void resetAccount();

	// Accessors
	double getBalance();
	int getDeposits();
	int getWithdrawals();
	double getInterestRate();
	double getServiceCharges();
};

#endif
