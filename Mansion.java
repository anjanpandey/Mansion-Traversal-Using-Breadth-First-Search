/**********************************************************************************************
 *
 * @author Anjan Pandey 
 *
 * @title Haunting Mansion Class
 *
 * @since 04/29/2015
 *
 * @submitted to Dr. C
 *
 * @discription Main method to test the methods of the MansionTraversal class. The class uses
 *  try-catch, to detect the error if the user forgets to enter the file at run argument. The
 *  user can go at build in the jGRASP header and check run arguments for that. 
 **********************************************************************************************
 */

import java.io.*;

public class Mansion
{
   public static void main(String[] args)
   {
      try
      {
         MansionTraversal man1 = new MansionTraversal(args[0]);
      }
      catch (Exception e)
      {
         System.out.println("Please enter the file."); // displaying the error message
      }
   }
}
