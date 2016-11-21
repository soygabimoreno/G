package com.gabrielmorenoibarra.g.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.gabrielmorenoibarra.g.G;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: I have to set g as a module for sample 

        if (G.isConnectedToInternet(this)) {
            Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        }
    }
}