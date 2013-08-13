//Phil Ammriato
//107680750


#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <time.h>
#include <sys/mman.h>
#include <math.h>
#include <assert.h>

#define PI 3.14159265359
#define DEBUG 1



struct Point {
 struct Point *next;
 struct Point *prev;
 double x;
 double y;
 char init;
 //int onHull;
};







/* *********************** HELPER  ***************************** */

//FROM JOSEPH O'ROURKE's text, Computational Geometry in C
inline double Area2(struct Point a, struct Point b, struct Point c){
  return (b.x - a.x) * (c.y - a.y)  - (c.x - a.x) * (b.y - a.y);
}//AREA2


/* ***************************** main   ********************************/

int main(int argc, char *argv[]){
  clock_t start = clock();

   //clock_t readS = clock();

  int range = atoi(argv[2]);


  int debug = 0;
  if(argc >3){ debug = 1;}


  //everything needed for opening the input file
  int inFD;
  struct stat s;
  int status;
  size_t size;
  char *infile;


  inFD = open(argv[1],O_RDONLY); //open the file
  status = fstat(inFD,&s); //get size of file
  size = s.st_size;
  infile =(char *) mmap(NULL,size,PROT_READ,MAP_PRIVATE,inFD,0);//get data


  


  int off = 0;
  int dimension = 0;
  int numPoints = 0;
  char buf[1] = {'\0'};

  //now read in the dimesion and number of points  
  buf[0] = infile[off++];
  int x = 0;
  while(buf[0] != '\n' && buf[0]!=' '){
    dimension = dimension*10;  //move the decimal place
    dimension = dimension + (buf[0] -'0');//atoi(buf); //add the next digit
    buf[0] = infile[off++];//get the next character
  }
  buf[0] = infile[off++];
  int y = 0;
  while(buf[0] != '\n' && buf[0]!=' '){
    numPoints = numPoints*10;  //move the decimal place
    numPoints = numPoints + (buf[0] -'0'); //add the next digit
    buf[0] = infile[off++];//get the next character
  }

  //calloc(1,1);
  //printf("D: %d\nNUM: %d\n",dimension,numPoints);


 //clock_t cS = clock();
  struct Point * hashTable = calloc((range+1),sizeof(struct Point));
  /*struct Point * hashTable = (struct Point *)
        mmap(NULL,(range+1)*sizeof(struct Point),
             PROT_READ | PROT_WRITE,MAP_PRIVATE|MAP_ANONYMOUS,-1,0); */
   /*struct Point *hashTable = (struct Point *) 
                            malloc((range+1)*sizeof(struct Point));*/

 // memset(hashTable,0,(range+1)*sizeof(struct Point));
  //printf("Time elapsedC: %f\n",((double)clock() - cS)/CLOCKS_PER_SEC);

  int count = numPoints;
  int i = 0;


  struct Point *extra=calloc((numPoints/2+1),sizeof(struct Point));
  int eIndex = 0;  

clock_t readS = clock();
 //////////////// clock_t readS = clock();
  //read in every point, x y  
  while(count--){
    buf[0] = infile[off++];
    x = 0;
    while(buf[0] != '\n' && buf[0]!=' '){
      x = x*10;  //move the decimal place
      x = x + (buf[0] -'0');//atoi(buf); //add the next digit
      buf[0] = infile[off++];//get the next character
    }
    buf[0] = infile[off++];
    y = 0;
    while(buf[0] != '\n' && buf[0]!=' '){
      y = y*10;  //move the decimal place
      y = y + (buf[0] -'0'); //add the next digit
      buf[0] = infile[off++];//get the next character
    }



    if(hashTable[x].init == '\0'){
      hashTable[x].x = x;
      hashTable[x].y = y;
      hashTable[x].init = 'y';
    }
    //Only need top and bottom point with same x
    else{           //   OPTIMIZEEEEEEEEEEEEEEEEEEEEEEEEEE   ************
      if(hashTable[x].next == NULL){//need to construct new point
        struct Point *q = &(extra[eIndex++]);
        q->x = x;
        q->y = y;
        q->init = 'y';

        int oldY = hashTable[x].y;
        if(y < hashTable[x].y){ //put new point first
          hashTable[x].y =y;  //q will now be the old point
          q->y = oldY;      
          q->prev = &(hashTable[x]);
          hashTable[x].next = q;
        }
        else{  //y>=
          hashTable[x].next = q;
          q->prev = &(hashTable[x]);   
        }
      }//if next == NULL

      //now next !=NULL
      else if(y < hashTable[x].y){ //replace lowest point
        hashTable[x].y = y;
      }
      else if(y > hashTable[x].next->y){ //replace highest point
        hashTable[x].next->y = y;
      }
    }//else   
  }//while count--

  //turn the possible sparse hashtable to a linkedlist

  printf("Time elapsed1: %f\n",((double)clock() - readS)/CLOCKS_PER_SEC);

  //start = clock();
  munmap(infile,size);  


  
  //clock_t switchS = clock();
  int index = 0;   
  struct Point * firstPoint = NULL;     //get the first point
  do{               
    firstPoint= &(hashTable[index++]);
  }while(firstPoint->init == '\0');
  
  struct Point * cur = firstPoint;
  if(firstPoint->next != NULL){ cur = firstPoint->next;}
  

  struct Point *temp = NULL;
  while(index < (range+1)){   //every position in the hashTable
    if (hashTable[index].init != '\0'){
      cur->next = &(hashTable[index]);
      hashTable[index].prev = cur;
      cur = &(hashTable[index]);
      if(cur->next != NULL){
        cur = cur->next;
      }
    }
    index++;
  }

  

  struct Point ** hull = calloc(numPoints,sizeof(struct Point *));
  
  hull[0] = firstPoint;        //definitely on the hull
  hull[1] = firstPoint->next;   //may be popped
  cur = hull[1]->next;
  
  int hIndex = 1;  //keep track of the last point added to hull
 
  //left to right
  while(cur->next != NULL){ //for every point

    //while current is right of last two hull points
    while(hIndex>0 && Area2(*hull[hIndex-1],*hull[hIndex],*cur)<=0){
      hull[hIndex--] = NULL;//pop
    }//while pop

   if(hIndex>0 && cur->y == hull[hIndex]->y && cur->y == hull[hIndex-1]->y){
      hull[hIndex--] = NULL;//pop
    }

    hull[++hIndex] = cur;//push

    cur = cur->next;

  }//while forward

  //the while does not process rightmost, but that is on the hull
  //while current is right of last two hull points
    while(hIndex>0 && Area2(*hull[hIndex-1],*hull[hIndex],*cur)<=0){
      hull[hIndex--] = NULL;//pop
    }//while pop

  hull[++hIndex] = cur;//push

  cur = cur->prev;
  cur = cur->prev;


//  ********  NOW GO BACK  RIGHT TO LEFT   *********

  while(cur->prev != NULL){ //for every point

    //while current is right of last two hull points
    while(hIndex>0 && Area2(*hull[hIndex-1],*hull[hIndex],*cur)<=0){
      hull[hIndex--] = NULL;//pop
    }//while pop

   if(hIndex>0 && cur->y == hull[hIndex]->y && cur->y == hull[hIndex-1]->y){
      hull[hIndex--] = NULL;//pop
    }

    hull[++hIndex] = cur;//push

    cur = cur->prev;

  }//while back


  //the while does not process rightmost, but that is on the hull
  //while current is right of last two hull points
    while(hIndex>0 && Area2(*hull[hIndex-1],*hull[hIndex],*cur)<=0){
      hull[hIndex--] = NULL;//pop
    }//while pop

  hull[++hIndex] = cur;//push




  free(hull);
  free(extra);
  free(hashTable);

 // printf("Time elapsed4: %f\n",((double)clock() - hull2S)/CLOCKS_PER_SEC);

  printf("Time elapsed: %f\n",((double)clock() - start)/CLOCKS_PER_SEC);
  

printf("Number of Hull Vertices: %d\n",hIndex);
}//main

