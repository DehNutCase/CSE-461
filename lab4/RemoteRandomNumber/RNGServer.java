// A simple TCP server for Demo
// @Author: T.L. Yu

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;

import java.util.Random; 

public class RNGServer {
  public static void main(String[] args) throws IOException {
         
    if (args.length != 0) {
      System.err.println("Usage: java RNGServer");
    }
 
    int portNumber = 6556;
    System.out.println("Hello World!");

    try {
      ServerSocket serverSocket =  new ServerSocket(portNumber);
      Socket clientSocket = serverSocket.accept();
      BufferedReader input = new BufferedReader (
           new InputStreamReader(clientSocket.getInputStream()));
   
       String inputLine = null;
       while ( ( inputLine = input.readLine() )  != null ) {
         //print input line for debugging purposes
        System.out.println ( inputLine );
        
        //parse string and calculate result
        String string_array[] = inputLine.split("\\D");
        int num1 = Integer.parseInt(string_array[0]);
        int num2 = Integer.parseInt(string_array[1]);
        int num3 = Integer.parseInt(string_array[2]);
        
        int index = 0;
        String result = "";
        StringBuilder result_builder = new StringBuilder();
        
        if(num2 < num1){
          //make sure num1 is <= num2
          int temp = num1;
          num1 = num2;
          num2 = temp;
        }
        
        Random rand = new Random();
         
        while (index < num3){
          index = index + 1;
          
          //generates a random integer between num1 and num2
          int new_random = rand.nextInt(num2 - num1 + 1);
          new_random = new_random + num1;
          
          if (index != 0){
            result_builder.append(" "); //make sure numbers are separated by space
          }
          result_builder.append(new_random);  //add number to string
        }
        
        result = result_builder.toString();
        
        //send result to client
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
                        true);
        out.println(result);
        System.out.println(result); //printing result to be sure it worked
       }
    }  catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
    }
  }
}