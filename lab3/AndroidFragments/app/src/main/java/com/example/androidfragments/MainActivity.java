package com.example.androidfragments;

import android.app.Fragment;
import android.os.Bundle;
import com.example.androidfragments.ui.AFragment;
import com.example.androidfragments.ui.BFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;


public class MainActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_location, new AFragment());
        ft.commit();

        Button button = (Button) findViewById(R.id.button_b);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_location, new BFragment());
                ft.commit();
            }
        });
    }


    public void fragA (View view) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_location, new AFragment());
        ft.commit();
    }


}