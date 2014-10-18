#-------------------------------------------------------------------------------
# Project Name  : Bank Accounts
# Written by    : Gilberto Perez
# Last Modified : 05/13/2014
#-------------------------------------------------------------------------------

# Notice the '.PHONY' target. 
# This target tells Make that 'all' and 'clean' are not files. This is usually
# not required, but useful if for some reason
.PHONY: clean all tarball

all: bank_accounts

# The clean target should be used before you create your tarball to remove the
# binaries created during compilation
clean:
	rm -f bank_accounts
	rm -f *.o
	# rm -fr input
	# rm -fr output

# The following targets automate the process of creating a tarball. To activate
# it simply remove the leading '# ' and replace '<username>' with your own.
# tarball: ele-bank_accounts.tar.gz

# ele-bank_accounts.tar.gz: clean
# 	cd .. &&	tar -czvf ele-bank_accounts.tar.gz ./bank_accounts/*

# These targets take care of the compilation process.
bank_accounts: bank_accounts.o account.o checking.o savings.o
	g++ bank_accounts.o account.o checking.o savings.o -o bank_accounts

bank_accounts.o: bank_accounts.cpp
	g++ -c bank_accounts.cpp

account.o: account.cpp
	g++ -c account.cpp

checking.o: checking.cpp
	g++ -c checking.cpp

savings.o: savings.cpp
	g++ -c savings.cpp
