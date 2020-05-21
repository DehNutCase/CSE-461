package com.example.lab4remoterandomnumber;

import androidx.appcompat.app.AppCompatActivity;

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
    EditText t3;

    Button send;

    TextView displayResult;

    String oper = "";

    private Socket client_socket;
    private static final int SERVERPORT = 6556; //makesure this matches the port in RNGServer.java
    private static final String SERVER_IP = "192.168.0.100"; //ipconfig gets this

    String num1 = "0";
    String num2 = "0";
    String num3 = "1";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the EditText elements (defined in res/layout/activity_main.xml
        t1 = (EditText) findViewById(R.id.t1);
        t2 = (EditText) findViewById(R.id.t2);
        t3 = (EditText) findViewById(R.id.t3);

        send = (Button) findViewById(R.id.send_button);

        displayResult = (TextView) findViewById(R.id.displayResult);

        // set  listeners
        send.setOnClickListener(this);

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
                || TextUtils.isEmpty(t2.getText().toString())
                || TextUtils.isEmpty(t3.getText().toString())) {
            return;
        }

        // read EditText and fill variables with numbers
        num1 = Integer.toString(Integer.parseInt(t1.getText().toString())); //forcibly checks that num 1 is an int rather than a float
        num2 = Integer.toString(Integer.parseInt(t2.getText().toString()));
        num3 = Integer.toString(Integer.parseInt(t3.getText().toString()));

        String str = num1 + "." + num2 + "." + num3;

        SendfeedbackJob job = new SendfeedbackJob();
        job.execute(str);
        str = "";
    }

    private class SendfeedbackJob extends AsyncTask<String, Void, String> {
        String result = "";
        @Override
        protected String doInBackground(String[] params) {
            try {
                out.println(params[0]);
                String inputLine = null;
                int index = 0;
                while ( ( inputLine = input.readLine() )  != null ) {
                    result = inputLine;
                    index = index + 1;
                    return "Done!"; //doesn't work without this line, I don't think I'm using Asynch Task properly
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
            displayResult.setText(result);
        }
    }
}