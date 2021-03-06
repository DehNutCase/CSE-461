package com.example.lab3simplecalculator;

import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainActivity extends Activity implements View.OnClickListener{
    EditText t1;
    EditText t2;

    ImageButton plus;
    ImageButton minus;
    ImageButton multiply;
    ImageButton divide;

    TextView displayResult;

    String oper = "";

    private Socket client_socket;
    private static final int SERVERPORT = 5665; //makesure this matches the port in CalculatorServer.java
    private static final String SERVER_IP = "192.168.0.100"; //ipconfig gets this

    String num1 = "0";
    String num2 = "0";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the EditText elements (defined in res/layout/activity_main.xml
        t1 = (EditText) findViewById(R.id.t1);
        t2 = (EditText) findViewById(R.id.t2);

        plus = (ImageButton) findViewById(R.id.plus);
        minus = (ImageButton) findViewById(R.id.minus);
        multiply = (ImageButton) findViewById(R.id.multiply);
        divide = (ImageButton) findViewById(R.id.divide);

        displayResult = (TextView) findViewById(R.id.displayResult);

        // set  listeners
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        multiply.setOnClickListener(this);
        divide.setOnClickListener(this);


        new Thread(new ClientThread()).start();
    }

    PrintWriter out;
    BufferedReader input;
    class ClientThread implements Runnable {
        @Override
        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                client_socket = new Socket(serverAddr, SERVERPORT);
                out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(client_socket.getOutputStream())),
                        true);

                input = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    // @Override
    public void onClick( View view ) {



        // check if the fields are empty
        if (TextUtils.isEmpty(t1.getText().toString())
                || TextUtils.isEmpty(t2.getText().toString())) {
            return;
        }

        // read EditText and fill variables with numbers
        num1 = t1.getText().toString();
        num2 = t2.getText().toString();

        String str = "";

        // perform operations
        // save operator in oper for later use
        switch ( view.getId() ) {
            case R.id.plus:
                oper = "+";
                str = num1 + oper + num2;
                break;
            case R.id.minus:
                oper = "-";
                str = num1 + oper + num2;
                break;
            case R.id.multiply:
                oper = "*";
                str = num1 + oper + num2;
                break;
            case R.id.divide:
                oper = "/";
                str = num1 + oper + num2;
                break;
            default:
                break;
        }

        String[] str_list = {str};

        SendfeedbackJob job = new SendfeedbackJob();
        job.execute(str);
        str = "";


    }

    private class SendfeedbackJob extends AsyncTask<String, Void, String> {
        double result = 0;
        @Override
        protected String doInBackground(String[] params) {
            try {
                out.println(params[0]);
                String inputLine = null;
                int index = 0;
                while ( ( inputLine = input.readLine() )  != null ) {
                    result = Float.parseFloat(inputLine);
                    index = index + 1;
                    return "Done!";
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Message sent to server and result received.";
        }

        @Override
        protected void onPostExecute(String message) {
            //process message
            // form the output line
            displayResult.setText(num1 + " " + oper + " " + num2 + " = " + result);
        }
    }
}