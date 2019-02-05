// Sean O'Dell, CSE 142, Spring 2015, Section BG
// Programming Assignment #6, 05/19/15
//
// This program's behavior is to create and display mad libs using a pre-created mad libs story
// format

import java.util.*;
import java.io.*;
public class MadLibs {
   public static void main(String[] args) throws FileNotFoundException {
      Scanner console = new Scanner(System.in);
      intro();
      String directive = "";  // Always prompts the user at least once
      while (!directive.equals("q")) {                                     // Prompts the user for
         System.out.print("(C)reate mad-lib, (V)iew mad-lib, (Q)uit? ");   // whether they would
         directive = console.nextLine().toLowerCase();                     // like to create, or
         if (directive.equals("c") || directive.equals("v")) {             // view a mad lib, or if
            String inputFileName = determineInputFileName(console);        // they would like to
            Scanner fileScan = new Scanner(new File(inputFileName));       // quit the program. And
            if (directive.equals("c")) {                                   // then does the desired
               createMadLib(console, fileScan);                            // action. The user will
            } else {                                                       // continue to be
               viewMadLib(fileScan);                                       // prompted until they
            }                                                              // choose to quit.
         }
      }
   }
   
   // Prints mad libs introduction
   public static void intro() {
      System.out.println("Welcome to the game of Mad Libs.");
      System.out.println("I will ask you to provide various words");
      System.out.println("and phrases to fill in a story.");
      System.out.println("The result will be written to an output file.");
      System.out.println();
   }
   
   // Determines input file name based off of user input given a scanner, and displays a warning
   // when the file denoted by the user is not found
   public static String determineInputFileName(Scanner console) {
      System.out.print("Input file name: ");
      String inputFileName = console.nextLine();
      File inputFile = new File(inputFileName);
      while (!inputFile.exists()) {
         System.out.print("File not found. Try again: ");
         inputFileName = console.nextLine();
         inputFile = new File(inputFileName);
      }
      return inputFileName;
   }
   
   // Swaps placeholders for user input determined by given scanner after identifying placeholders
   // using a given file scanner, and writes output file utalizing a given print stream.
   public static void swapPlaceholders(Scanner console, Scanner fileScan, PrintStream output) {
      while (fileScan.hasNextLine()) {
         String line = fileScan.nextLine();
         Scanner tokens = new Scanner(line);
         while (tokens.hasNext()) {
            String token = tokens.next();
            if (token.startsWith("<") && token.endsWith(">")) {
               String wordQue = token.substring(1, token.length() - 1);
               wordQue = wordQue.replace("-", " ");
               determineUserWordCuePrompt(wordQue);
               String queResponse = console.nextLine();
               output.print(queResponse + " ");
            } else {
               output.print(token + " ");
            }
         }
         output.println();
      }
   }
   
   // Prints correct user prompt for the word que, given a word que.
   public static void determineUserWordCuePrompt(String wordQue) {
      char vowelTest = wordQue.toLowerCase().charAt(0);
      if (vowelTest == 'a' || vowelTest == 'e' || vowelTest == 'i' || vowelTest == 'o' ||
      vowelTest == 'u') {
         System.out.print("Please type an " + wordQue + ": ");
      } else {
         System.out.print("Please type a " + wordQue + ": ");
      }
   }
   
   // Creates Mad Lib.  Takes user input for the output file name given a scanner and reads the
   // file, given a file scanner
   public static void createMadLib(Scanner console, Scanner fileScan) throws FileNotFoundException
   {
      System.out.print("Output file name: ");
      String outputFileName = console.nextLine();
      System.out.println();
      PrintStream output = new PrintStream(new File(outputFileName));
      swapPlaceholders(console, fileScan, output);
      System.out.println("Your mad-lib has been created!");
      System.out.println();
   }
   
   // Prints mad libs file, after reading the file given a file scanner
   public static void viewMadLib(Scanner fileScan) {
      System.out.println();
      while (fileScan.hasNextLine()) {
         String line = fileScan.nextLine();
         System.out.println(line);
      }
      System.out.println();
   }
}