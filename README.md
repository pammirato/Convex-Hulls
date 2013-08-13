This Is the code for two parts of a project completed for a graduate course at Stony
 Brook University in Spring 2013, Computational Geometry, with professor Joe Mitchell.

All code by Phil Ammirato. See https://sites.google.com/site/philammirato/home/course-projects
 for more details on the project.





**ChanFinal has its own README.txt in its directory



****************** HASHING.C  *******************

An algorithm for computing the convex of a point set in 2D.





to run   gcc hashing.c -o hashing
         ./hashing data.txt 10000
		 
		 *the first argument is the path of the text file that has the points
		 *the second argument is the x-range of the data values
		 
    The format of the file(same as for QHull) is:
	
	dimension of points
	number of points
	x y
	x y
	
	
	
	ex) for 2D with 3 Points
	2
	3
	1 10
	3 4
	5 2
	
	
	*MY CODE ONLY RUNS ON 2D, INTEGER POINTS
	
	

 ** OUTPUT  - the running time from the the first line of main to the end of main
            - the number of vertices on the convex hull

