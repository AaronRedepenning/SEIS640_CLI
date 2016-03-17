import java.io.*;
import java.util.*;
import java.text.*;

public class SeisCli
{
   private final static String version = "1.0";
   public static int cmdNumber;
   private static boolean _continue = true;
   private static String[] hisory = new String[10];
   private final static String helpMsg = "version   prints out the CLI version number\n" +
                                         "history   displays the last 10 commands entered\n" +
                                         "help      displays a list of valid commands\n" +
                                         "exit      exits the CLI\n" +
                                         "date      prints out the current date\n" +
                                         "time      prints out the current time\n" +
                                         "!<number> (re)executes a command <number> from the history\n" +
                                         "#<cmd>    execures program <cmd>\n";

   public static void main(String[] args) throws IOException {
      cmdNumber = 0;
      BufferedReader rdr = new BufferedReader(new InputStreamReader(System.in));
  
      while(_continue) {
         PrintPrompt();
         String command = rdr.readLine();
         cmdNumber += ExecuteCommand(command) ? 1 : 0;
      }
   }

   public static void PrintPrompt() {
      System.out.print("abr-" + cmdNumber + ">");
   }

   public static boolean ExecuteCommand(String cmd) {
      switch(cmd) {
         case "version": {
            System.out.println("Aaron Redepenning CLI, Version " + version + "\n");
            return true;
         }
         case "history": {
            return true;
         }
         case "help": {
            System.out.println(helpMsg);
            return true;
         }
         case "time": {
            Calendar now = Calendar.getInstance();
            now.
            System.out.println("The time is 8:20\n");
            return true;
         }
         case "date": {
            Calendar now = Calendar.getInstance();
            
            System.out.println("The date is 10/15/2015\n");
            return true;
         }
         case "exit": {
            _continue = false;
            return true;
         }
      }
      return false;
   }
}
