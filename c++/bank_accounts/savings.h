#ifndef SAVINGS_H
#define SAVINGS_H

#include "account.h"

class Savings : public Account 
{
protected:
	bool status;
public:
	// Constructor
	Savings() : Account(0,0.05) 
	{
		status = false;
	}

	void withdraw(double);
	void deposit(double);
	void monthlyProc();
	bool isActive(double);
	void resetAccount();

	// Accessors
	double getBalance();
	int getDeposits();
	int getWithdrawals();
	double getInterestRate();
	double getServiceCharges();
};

#endif
