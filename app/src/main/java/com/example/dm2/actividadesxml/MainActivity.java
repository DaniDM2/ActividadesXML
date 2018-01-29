package com.example.dm2.actividadesxml;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public  void Ejercicio1(View v){
        Intent intento=new Intent(MainActivity.this, Ejercicio1.class);
        startActivity(intento);
    }

    public  void Ejercicio2(View v){
        Intent intento=new Intent(MainActivity.this, Ejercicio2.class);
        startActivity(intento);
    }
}
