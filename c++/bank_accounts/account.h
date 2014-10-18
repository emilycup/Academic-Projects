#ifndef ACCOUNT_H
#define ACCOUNT_H

//DEBUGGING PURUPOSES
#include <iostream>
using namespace std;

class Account
{
protected:
	double balance;
	int deposits;
	int withdrawals;
	double interestRate;
	double serviceCharges;
public:

	// Constructors
	Account(double, double);				
	Account() {}								

	 // Destructor
	~Account() {}

	virtual void deposit(double);
	virtual void withdraw(double);
	virtual void calcInt();
	virtual void monthlyProc();
	virtual void resetAccount();

	// Accessors
	virtual double getBalance();
	virtual int getDeposits();
	virtual int getWithdrawals();
	virtual double getInterestRate();
	virtual double getServiceCharges();
};

#endif
