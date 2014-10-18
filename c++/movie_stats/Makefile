#-------------------------------------------------------------------------------
# Project Name  : Movie Stats
# Written by    : Gilberto Perez
# Last Modified : 04/18/2014
#-------------------------------------------------------------------------------

# Notice the '.PHONY' target. 
# This target tells Make that 'all' and 'clean' are not files. This is usually
# not required, but useful if for some reason
.PHONY: clean all tarball

all: movie_stats

# The clean target should be used before you create your tarball to remove the
# binaries created during compilation
clean:
	rm -f movie_stats
	rm -f *.o
	rm -fr input
	rm -fr output

# The following targets automate the process of creating a tarball. To activate
# it simply remove the leading '# ' and replace '<username>' with your own.
tarball: ele-movie_stats.tar.gz

ele-movie_stats.tar.gz: clean
	cd .. &&	tar -czvf ele-movie_stats.tar.gz ./movie_stats/*

# These targets take care of the compilation process.
movie_stats: movie_stats.o
	g++ movie_stats.o -o movie_stats

movie_stats.o: movie_stats.cpp
	g++ -c movie_stats.cpp
