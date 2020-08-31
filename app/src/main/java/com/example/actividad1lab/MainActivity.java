package com.example.actividad1lab;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.LocalDate.now;

public class MainActivity extends AppCompatActivity {
    Switch switchCarga; //Defino el switch
    SeekBar cantidadCarga; //Defino el seekbar
    CheckBox terminos; //Defino el checkbox
    Button registrar;
    TextView textoCarga;
    EditText clave, claveRep, email, tarjeta, CCV, mes, anio, CBU, aliasCBU;
    boolean valClave, valClaveRep, valEmail, valTarjeta, valCCV, valMes, valAnio, valCBU, valAliasCBU, valCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchCarga = findViewById(R.id.cargaInicial);
        cantidadCarga = findViewById(R.id.barraCarga);
        terminos = findViewById(R.id.terminos);
        registrar = findViewById(R.id.registrar);
        textoCarga = findViewById(R.id.textoCarga);
        clave = findViewById(R.id.password);
        claveRep = findViewById(R.id.password2);
        email = findViewById(R.id.email);
        tarjeta = findViewById(R.id.numeroTarjeta);
        CCV = findViewById(R.id.numeroCCV);
        mes = findViewById(R.id.mes);
        anio = findViewById(R.id.anio);
        registrar = findViewById(R.id.registrar);
        registrar.setEnabled(false);

        clave.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (clave.getText().toString().length() <= 0) {
                    clave.setError("Este es un campo obligatorio");
                    valClave = false;
                } else {
                    clave.setError(null);
                    valClave = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (clave.getText().toString().length() <= 0) {
                    clave.setError("Este es un campo obligatorio");
                    valClave = false;
                } else {
                    clave.setError(null);
                    valClave = true;
                }

            }
        }); //Listener cambio de texto en password 1

        claveRep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!claveRep.getText().toString().equals(clave.getText().toString())) {
                    claveRep.setError("Las contraseñas no coinciden");
                    valClaveRep = false;
                } else {
                    claveRep.setError(null);
                    valClaveRep = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!claveRep.getText().toString().equals(clave.getText().toString())) {
                    claveRep.setError("Las contraseñas no coinciden");
                    valClaveRep = false;
                } else {
                    claveRep.setError(null);
                    valClaveRep = true;
                }
            }
        }); //Listener cambio de texto en password 2

        clave.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) { //Listener focus en password 1
                if (!hasFocus) {
                    if (clave.getText().toString().length() <= 0) {
                        clave.setError("Este es un campo obligatorio");
                        valClave = false;
                    } else {
                        clave.setError(null);
                        valClave = true;
                    }
                }
            }
        });

        claveRep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {  //Listener focus en password 2
                if (!hasFocus) {
                    if (claveRep.getText().toString().length() <= 0) {
                        claveRep.setError("Este es un campo obligatorio");
                        valClaveRep = false;
                    } else {
                        claveRep.setError(null);
                        valClaveRep = true;
                    }
                    if (!claveRep.getText().toString().equals(clave.getText().toString())) {
                        claveRep.setError("Las contraseñas no coinciden");
                        valClaveRep = false;
                    } else {
                        claveRep.setError(null);
                        valClaveRep = true;
                    }
                }
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (clave.getText().toString().length() <= 0) {
                    clave.setError("Este es un campo obligatorio");
                    valClave = false;
                } else {
                    clave.setError(null);
                    valClave = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (email.getText().toString().length() <= 0) {
                    email.setError("Este es un campo obligatorio");
                    valEmail = false;
                } else {
                    String cantLetras = email.getText().toString().substring(email.getText().toString().indexOf("@")+1);
                    if (email.getText().toString().trim().contains("@") && cantLetras.length() >= 3){
                        email.setError(null);
                        valEmail = true;
                    } else {
                        email.setError("Ingrese un correo electrónico válido");
                        valEmail = false;
                    }
                }
            }
        });

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() { //Listener focus en email
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (email.getText().toString().length() <= 0) {
                        email.setError("Este es un campo obligatorio");
                        valEmail = false;
                    } else {
                        String cantLetras = email.getText().toString().substring(email.getText().toString().indexOf("@") + 1);
                        if (email.getText().toString().trim().contains("@") && cantLetras.length() >= 3) {
                            email.setError(null);
                            valEmail = true;
                        } else {
                            email.setError("Ingrese un correo electrónico válido");
                            valEmail = false;
                        }
                    }
                }}});

        tarjeta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (tarjeta.getText().toString().length() <= 0) {
                    tarjeta.setError("Este es un campo obligatorio");
                    valTarjeta=false;
                } else {
                    tarjeta.setError(null);
                    valTarjeta=true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (tarjeta.getText().toString().length() <= 0) {
                    tarjeta.setError("Este es un campo obligatorio");
                    valTarjeta=false;
                } else {
                    tarjeta.setError(null);
                    valTarjeta=true;
                }
            }
        });

        tarjeta.setOnFocusChangeListener(new View.OnFocusChangeListener() { //Listener focus en email
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (tarjeta.getText().toString().length() <= 0) {
                        tarjeta.setError("Este es un campo obligatorio");
                        valTarjeta=false;
                    } else {
                        tarjeta.setError(null);
                        valTarjeta=true;
                    }
                }}});

        CCV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (CCV.getText().toString().length() <= 0) {
                    CCV.setError("Este es un campo obligatorio");
                    valCCV=false;
                } else {
                    CCV.setError(null);
                    valCCV=true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (CCV.getText().toString().length() <= 0) {
                    CCV.setError("Este es un campo obligatorio");
                    valCCV=false;
                } else {
                    CCV.setError(null);
                    valCCV=true;
                }
            }
        });

        CCV.setOnFocusChangeListener(new View.OnFocusChangeListener() { //Listener focus en email
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (CCV.getText().toString().length() <= 0) {
                        CCV.setError("Este es un campo obligatorio");
                        valCCV=false;
                    } else {
                        CCV.setError(null);
                        valCCV=true;
                    }
                }}});

        mes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mes.getText().toString().length() <= 0) {
                    mes.setError("Este es un campo obligatorio");
                    valMes=false;
                } else {
                    mes.setError(null);
                    valMes=true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mes.getText().toString().length() <= 0) {
                    mes.setError("Este es un campo obligatorio");
                    valMes=false;
                } else {
                    mes.setError(null);
                    valMes=true;
                }
            }
        });

        mes.setOnFocusChangeListener(new View.OnFocusChangeListener() { //Listener focus en email
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (mes.getText().toString().length() <= 0) {
                        mes.setError("Este es un campo obligatorio");
                        valMes=false;
                    } else {
                        mes.setError(null);
                        valMes=true;
                    }
                }}});

        anio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (anio.getText().toString().length() <= 0) {
                    anio.setError("Este es un campo obligatorio");
                    valAnio=false;
                } else {
                    anio.setError(null);
                    valAnio=true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (anio.getText().toString().length() <= 0) {
                    anio.setError("Este es un campo obligatorio");
                    valAnio=false;
                } else {
                    anio.setError(null);
                    valAnio=true;
                }
            }
        });

        anio.setOnFocusChangeListener(new View.OnFocusChangeListener() { //Listener focus en email
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (anio.getText().toString().length() <= 0) {
                        anio.setError("Este es un campo obligatorio");
                        valAnio=false;
                    } else {
                        anio.setError(null);
                        valAnio=true;
                       /* if(Integer.parseInt(mes.toString()) > 12){
                            Toast toast2 = Toast.makeText(getApplicationContext(), "Fecha invalida", Toast.LENGTH_SHORT);
                            toast2.show();
                        }
                        else {
                            SimpleDateFormat formatter = new SimpleDateFormat("MM-yyyy");
                            String dateInString = mes.toString() + anio.toString();
                            Date fechaIngresada = null;
                            {
                                try {
                                    fechaIngresada = formatter.parse(dateInString);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            Date date = new Date();
                            if (fechaIngresada.compareTo(date) > 0){
                                Toast toast1 = Toast.makeText(getApplicationContext(), "Fecha ingresada mayor a la actual", Toast.LENGTH_SHORT);
                                toast1.show();
                            }
                        } */
                    }
                }}});

        switchCarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cantidadCarga.setVisibility(View.VISIBLE);
                    textoCarga.setVisibility(View.VISIBLE);
                } else {
                    cantidadCarga.setVisibility(View.GONE);
                    textoCarga.setVisibility(View.GONE);
                }
            }
        });

        cantidadCarga.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int val = (i * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                textoCarga.setX(val);
                textoCarga.setText(String.valueOf(i));
                if (i > 0){
                    valCarga = true;
                }
                else{
                    valCarga = false;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        terminos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    registrar.setEnabled(true);
                }
                else{
                    registrar.setEnabled(false);
                }
            }
            });


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valClave && valCarga && valClaveRep && valEmail && valTarjeta && valCCV && valMes && valAnio) {
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Registro correcto", Toast.LENGTH_SHORT);
                    toast1.show();
                } else {
                    Toast toast2 = Toast.makeText(getApplicationContext(), "Registro incorrecto. Revise los datos ingresados", Toast.LENGTH_SHORT);
                    toast2.show();
                }
            }
        });
    }
}