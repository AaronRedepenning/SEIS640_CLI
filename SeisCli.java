import java.io.*;
import java.util.*;
import java.text.*;

public class SeisCli
{
   private final static String version = "1.0";
   public static int cmdNumber;
   private static boolean _continue = true;
   private static int head = 0;
   private static String[] history = new String[10];
   private final static String helpMsg = "version   prints out the CLI version number\n" +
                                         "history   displays the last 10 commands entered\n" +
                                         "help      displays a list of valid commands\n" +
                                         "exit      exits the CLI\n" +
                                         "date      prints out the current date\n" +
                                         "time      prints out the current time\n" +
                                         "!<number> (re)executes a command <number> from the history\n" +
                                         "#<cmd>    execures program <cmd>";

   public static void main(String[] args) throws IOException {
      cmdNumber = 0;
      BufferedReader rdr = new BufferedReader(new InputStreamReader(System.in));
  
      while(_continue) {
         PrintPrompt();
         String command = rdr.readLine();
         ExecuteCommand(command);
      }
   }

   public static void PrintPrompt() {
      System.out.print("arms-" + cmdNumber + ">");
   }

   public static void PrintHistory() {
      int index;
      String cmd;
      System.out.println("   " + cmdNumber + ": " + "history");
      for(int i = 0; i < 9; i++) {
         index = (head - i - 1) % 10;
         cmd = index < 0 ? null : history[index];
         if(cmd != null) {
            System.out.print("   " + (cmdNumber -1 - i));
            System.out.println(": " + cmd);
         }
      }
   }

   public static boolean ExecuteCommand(String cmd) {
      boolean result = false;
      switch(cmd) {
         case "version": {
            System.out.println("Aaron Redepenning and Micheal Xiong CLI, Version " + version);
            result = true;
            break;
         }
         case "history": {
            PrintHistory();
            result = true;
            break;
         }
         case "help": {
            System.out.println(helpMsg);
            result = true;
            break;
         }
         case "time": {
            Calendar now = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	    String t1 = sdf.format(now.getTime());
            System.out.println("The time is " + t1);
            result = true;
            break;
         }
         case "date": {
            Calendar now = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String t1 = sdf.format(now.getTime());  
            System.out.println("The date is " + t1);
            result = true;
            break;
         }
         case "exit": {
            _continue = false;
           System.out.println("arms CLI Exiting...");
           result = true;
           break;
         }
         default: {
            if(cmd.matches("!\\d+")) {
               int num = Integer.parseInt(cmd.substring(1));
               if(num < cmdNumber && num >= cmdNumber - 10) {
                  return ExecuteCommand(history[num % 10]);
               }
               else {
                  System.out.print("Command History is only availible back to command ");
                  System.out.println((cmdNumber - 10) < 0 ? 0 : cmdNumber - 10);
                  return false;
               }
            }
            else if(cmd.matches("#\\w+")) {
               Runtime r = Runtime.getRuntime(); // fork()
               try {
                  Process p = r.exec(cmd.substring(1)); // exec()
                  p.waitFor();
                  BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                  String line = "";
                  while((line = br.readLine()) != null) {
                     System.out.println(line);
                  }
                  br.close();
                  result = true;
               }
               catch(Exception e) {
                  result = false;
               }
            }
            break;
         }
      }
      
      if(result) {
         // Add To history and increment command count
         history[head % 10] = cmd;
         head++;
         cmdNumber++;
      }
      else {
         // Invalid Command
         System.out.println("\"" + cmd + "\" is an invalid command");
      }

      return result;
   }
}
