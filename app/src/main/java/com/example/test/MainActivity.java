package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner,spinner_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex1);
        spinner = findViewById(R.id.sp2);
        spinner_1 = findViewById(R.id.sp1);
        String[] list = {"PTIT","HUST","NEU","FTU"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.item,list);
        spinner.setAdapter(arrayAdapter);
        String[] listCountry = getResources().getStringArray(R.array.country);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this,R.layout.item,listCountry);
        spinner_1.setAdapter(arrayAdapter1);
    }

}