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

public class CalculatorServer {
  public static void main(String[] args) throws IOException {
         
    if (args.length != 0) {
      System.err.println("Usage: java CalculatorServer");
    }
 
    int portNumber = 5665;
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
        System.out.println (string_array[0]);
        System.out.println (string_array[1]);
        double num1 = Float.parseFloat(string_array[0]);
        double num2 = Float.parseFloat(string_array[1]);
        String oper = "+";
        int index = 0;
        double result = 0;
        while(true){
          boolean found = false;
          switch(inputLine.charAt(index)){
            case '+':
              result = num1 + num2;
              found = true;
              break;
            case '-':
              result = num1 - num2;
              found = true;
              break;
            case '*':
              result = num1 * num2;
              found = true;
              break;
            case '/':
              result = num1/num2;
              found = true;
              break;
          }
          if (found) break;
          index = index + 1;
        }
        System.out.println(result);
        //send result to client
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
                        true);
        System.out.println(result);
        out.println(result);
        System.out.println(result);
       }
    }  catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
    }
  }
}