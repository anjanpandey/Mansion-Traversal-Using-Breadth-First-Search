/*************************************************************************************
 * @author Anjan Pandey 
 *
 * @title Haunting Mansion Class
 *
 * @since 04/29/2015
 *
 * @submitted to Dr. C
 *
 * @discription The class that performs the file reading, clearing method, displaying
 * method, and breadth first search method to detect the shortes path in the haunted mansion.
 **************************************************************************************
 */
import java.util.*;
import java.io.*;

public class MansionTraversal
{  
   private int[][] paths;    //Creating the adjacency matrix to store the possible paths
   private String passages = "ABCDEFGHIJKLMNOPQRSTUVWXY"; // Creating the string to relate the paths
   private int index = 1;                                // Creating index to record the number of problems
   private Queue <Character> queue;                     // Creating a queue for the breadth first search                       
   private char start = ' ';                           // Creating a char to store the start point
   private char end = ' ';                            // Creating a char to store the end point
   
   /*********************************************************************************************
    * Constructor that initializes the paths with the data stored in the file. It calls the method
    * Reader in order to relate the data in an array of 25*25.   
    * @param filename name of the input file
    **********************************************************************************************
    */
   
   public MansionTraversal(String filename)
   {
      paths = new int[25][25];
      Reader(filename);
   }
   
   /*****************************************************************
    * Reads the content of the file whose name is passed as argument.   
    * @param filename name of the file to be read
    ***************************************************************** 
    */
   
   public void Reader(String filename)
   { 
      try
      {
         
         Scanner file, numscan,cluescan;
         file = new Scanner (new File(filename));
         int mansion_num = 0;
         String clue="";
         String nextclue = "";
         String path = "";  
         while (file.hasNext())
         {
            String contents = file.nextLine();
            if (contents.length()==1) //The first line that gives the number of hunted mansion problem
            {
               numscan = new Scanner(contents);
               mansion_num = numscan.nextInt();
            } 
            if (contents.length()>2)// The possible paths given in the file
            {  
               cluescan = new Scanner(contents);
               while (cluescan.hasNext())
               {
                  clue = cluescan.next();
                  char firstclue = clue.charAt(0);
                  char secondclue = clue.charAt(1);
                  int indexfirst = passages.indexOf(firstclue);
                  int indexsecond = passages.indexOf(secondclue);
                  paths[indexfirst][indexsecond] = 1;
                  paths[indexsecond][indexfirst] = 1;
               
               }
            }
            if (contents.length()==2) // for the paths who starting and ending point is given
            {
               path = contents;
               start = contents.charAt(0);
               end = contents.charAt(1);
               bfs(start,end,paths);
               index++;
            }
         }
      }
      
      catch (IOException fileException)// if there is exception readingthe file, then the exception is thrown
      {
         System.out.println("File Not Found!!!");
      }
   }
     
   /**********************************************************************************************************************
    * The method that performs the breadth first search. Firstly, the file only consider to those boxes in the adjacency
    * matrix that has 1 in it. This means there is the path from the root and its fellow child. The program checks the 
    * roots and all its adjacent childs. If the child is discover then futher the program starts to look up for its next
    * child. The boolean terminator is created which shows us that the last element has been added to the array list. The 
    * queue is created to store the roots and child which we are exploring, and array list is created to store the visited
    * root. If any root is already been visited or was present in the queue, then the program interfere its entry second time
    * in both queue and array list.  
    * @param begin the starting point of the path
    * @param terminate the ending point of the path
    * @param solve the adjacency matrix that relates its path
    ************************************************************************************************************************
    */
  
   public void bfs(char begin, char terminate, int solve[][]  )
   {
      queue = new LinkedList<Character>();//queue that track our breadth-first search phenomenon
      ArrayList<Character> store_visited = new ArrayList<Character>();// list to store the visited roots and childs
      char put = ' ';
      char s = ' ';
      int childcount = 0;
      boolean terminator = false;
      queue.add(begin);
      while(!queue.isEmpty())
      {
         if (terminator)
         {
            char r = queue.peek();
            if (r != terminate)
            {
               queue.poll();
            }
            else
            {
               store_visited.add(queue.poll());
              
            }
            
         }
         else
         {
            childcount =0;
            for (int i = 0; i < 25; i++)
            {  
               s = queue.peek();
               int u = passages.indexOf(s);
               if (solve[u][i] == 1)
               {
                  put = passages.charAt(i);
                  if (put == terminate)
                  {
                     terminator = true;
                  }
                  if (!store_visited.contains(put)&& !queue.contains(put))
                  {
                     queue.add(put);
                     childcount++;
                  }
               }
            }
            if (childcount == 0)
            {
               queue.poll();
            }
            else
            {
               store_visited.add(s);
               queue.remove(s);
            }
         }
         
      }
      display(store_visited,terminate,begin);
      clear(solve); 
   }
      
   /****************************************************************************************************************
    * Displays the contents of the array list after the breadth first search is performed. The array list comprises
    * of the shortest paths or no paths. The following methods compare the starting point and ending point of the 
    * arraylist, and if both points are matched then it is sure that there is path, and hence the path is displayed, 
    * otherwise the unsuccessful message is displayed.  
    * @param list that contains or does not contains the shortest path of the haunted mansion
    * @param end is the end point of the graph
    * @param initial is the starting point of the graph
    *****************************************************************************************************************
    */
    
   public void display(ArrayList<Character> list,char end,char initial)
   {
      if((list.size() == 0) || (list.get(list.size()-1)!=end) ) //condition if the last element in the array list is not an end point
      
      {
         System.out.println("Problem "+index+": ");
         System.out.println("It is not possible to travel from "+initial+" to "+end+"."+"\n");
         
      }
      
      else if (list.get(list.size()-1)==end )// condition if the last element in the array list is an end point
      {
         char letters = ' ';
         String middle = "";
         String ans=" ";
         for (int q = 0; q < list.size(); q++)
         { 
            if(list.get(q) != initial && list.get(q) != end)
            {
               letters = list.get(q);
               middle = "-"+letters;
            } 
            ans += list.get(q)+middle;
            middle="";
         }
                  
         System.out.println("Problem "+index+": "); 
         System.out.println("It is possible to travel from "+ initial +" to "+ end+".");
         System.out.println("The shortest path involves "+ (list.size()-1)+" passages :"+ans+".\n"); 
         
      }
   }
   
   /********************************************************************************************************
    * Clears the contents of the two-dimensional array after the shortest path has been found by using the two 
    * nested for-loop which provides access to each row and column. 
    * @param route the two dimensional array whose contents need to be clear
    ********************************************************************************************************
    */

   public void clear(int[][] route)
   {
      for (int row=0; row <25; row++)
      {
         for (int col=0; col <25; col++)
         {
            route[row][col] = 0; //clearing the contents of the file
         }
      
      }
   
   }

}

      
        
    
        
     
