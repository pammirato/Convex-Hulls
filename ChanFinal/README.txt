
*************  CHAN APPLET  ****************


***TO RUN THE CODE
    The easiest way to run the program is to navigate to the .jar file
     The file is located in ChanFinal/dist/ChanFinal.jar
     Just doucle click the file and the GUI should pop up.

    Alternatively
     This project was developed in NetBeans, and so can be imported
     and run in netbeans.

    From the command line, again navigate to the .jar file and run 
     java -jar ChanFinal.jar
   
   
***Big Picture Description

  The ChanAnimated class runs most of the chan convex hull code, including 
   finding the tangent points of convex hulls.
   
  The GrahamScan class has a stand alone implementation of graham scan, which
    is used as a black box by ChanAnimated
	
  Primitives has some primitives as described in O'Rourke's text
  
  Point and Point stack define data simple data structures for the algorithms
  
  Bookeeping does some simple things like splitting the point set into groups
  
  Everything else is concerned with the GUI
  
   
   








