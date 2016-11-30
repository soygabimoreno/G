package com.gabrielmorenoibarra.g.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.gabrielmorenoibarra.g.G;
import com.gabrielmorenoibarra.g.java.GArray;
import com.gabrielmorenoibarra.g.java.GCrypt;
import com.gabrielmorenoibarra.g.java.GLastModified;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (G.isConnectedToInternet(this)) {
            Toast.makeText(this, "Connected!", Toast.LENGTH_SHORT).show();
        }

        String vector = "0123456789012345";
        String secretKey = "1122334455667788";
        try {
            String encrypt = new GCrypt(vector, secretKey).encrypt("Message to send");
            String decrypt = new GCrypt(vector, secretKey).decrypt(encrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Date d = new Date(new GLastModified("https://camo.githubusercontent.com/1d1bd13c6ad09c52166f29229567552bec34f811/687474703a2f2f6761627269656c6d6f72656e6f6962617272612e636f6d2f696d616765732f6c6f676f5f672e706e67").getSeconds() * 1000);
        String s = d.toString();

        byte[] a = {1, 2, 3};
        byte[] b = {4, 5};
        GArray.concatenate(a, b);


    }
}