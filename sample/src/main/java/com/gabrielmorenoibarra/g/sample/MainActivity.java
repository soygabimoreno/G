package com.gabrielmorenoibarra.g.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gabrielmorenoibarra.g.java.GLastModified;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (G.isConnectedToInternet(this)) {
//            Toast.makeText(this, "Connected!", Toast.LENGTH_SHORT).show();
//        }
//
//        String vector = "0123456789012345";
//        String secretKey = "1122334455667788";
//        try {
//            String encrypt = new GCrypt(vector, secretKey).encrypt("Message to send");
//            String decrypt = new GCrypt(vector, secretKey).decrypt(encrypt);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Date d = new Date(new GLastModified("http://gabrielmorenoibarra.com/images/pepe.txt").getMillis());
        String s = d.toString();

//        byte[] a = {1, 2, 3};
//        byte[] b = {4, 5};
//        GArray.concatenate(a, b);


    }
}