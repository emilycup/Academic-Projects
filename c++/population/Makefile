#-------------------------------------------------------------------------------
# Project Name  : Population
# Written by    : Gilberto Perez
# Last Modified : 04/21/2014
#-------------------------------------------------------------------------------


# Notice the '.PHONY' target. 
# This target tells Make that 'all' and 'clean' are not files. This is usually
# not required, but useful if for some reason
.PHONY: clean all tarball

all: population

# The clean target should be used before you create your tarball to remove the
# binaries created during compilation
clean:
	rm -f population
	rm -f *.o
	rm -fr input
	rm -fr output

# The following targets automate the process of creating a tarball. To activate
# it simply remove the leading '# ' and replace '<username>' with your own.
tarball: ele-population.tar.gz
ele-population.tar.gz: clean
	cd .. &&	tar -czvf ele-population.tar.gz ./population/*

# These targets take care of the compilation process.
population: population.o
	g++ population.o -o population

population.o: population.cpp
	g++ -c population.cpp
