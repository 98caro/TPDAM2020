package com.example.actividad1lab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    Switch switchCarga; //Defino el switch
    SeekBar cantidadCarga; //Defino el seekbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchCarga = findViewById(R.id.cargaInicial);
        cantidadCarga = findViewById(R.id.barraCarga);

        switchCarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cantidadCarga.setVisibility(View.VISIBLE);
                } else {
                    cantidadCarga.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}