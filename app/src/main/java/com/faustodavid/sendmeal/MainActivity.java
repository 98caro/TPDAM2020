package com.faustodavid.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.time.LocalDate.now;

public class MainActivity extends AppCompatActivity {
    Switch switchCarga;
    SeekBar cantidadCarga;
    CheckBox terminos;
    Button registrar; //probando commit
    TextView textoCarga, labelFecha, labelTarjeta;
    RadioButton debito, credito;
    RadioGroup tipoTarjeta;
    EditText clave, claveRep, email, tarjeta, CCV, mes, anio, CBU, aliasCBU;
    boolean valClave = false, valClaveRep = false, valEmail = false, valTipo=false, valTarjeta = false, valCCV = false, valMes = false, valAnio = false, valFecha = false, valCBU, valAliasCBU, valCarga = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchCarga = findViewById(R.id.cargaInicial); //Switch
        cantidadCarga = findViewById(R.id.barraCarga);  //Barra de carga -seekbar-
        terminos = findViewById(R.id.terminos); //Checkbox de terminos y condiciones
        registrar = findViewById(R.id.registrar);   //Botón registrar
        textoCarga = findViewById(R.id.textoCarga); // Label
        clave = findViewById(R.id.password);    //Campo clave
        claveRep = findViewById(R.id.password2); //Campo repetir clave
        email = findViewById(R.id.email); //Campo email
        tipoTarjeta = findViewById(R.id.tipoTarjeta);  // Radio group tipo tarjeta
        debito = findViewById(R.id.debito); // Radio button debito
        credito = findViewById(R.id.credito); // Radio button credito
        tarjeta = findViewById(R.id.numeroTarjeta);  // Campo numero tarjeta
        CCV = findViewById(R.id.numeroCCV); // Campo numero CCV
        labelFecha = findViewById(R.id.labelFecha); // Label fecha
        mes = findViewById(R.id.mes); // Campo numero mes
        anio = findViewById(R.id.anio); // Campo numero año
        labelTarjeta = findViewById(R.id.labelTarjeta); // Label tipo de tarjeta (radio group)

        registrar.setEnabled(false);
        CCV.setEnabled(false);
        mes.setEnabled(false);
        anio.setEnabled(false);

        //Listener cambio de texto en password 1
        clave.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                valClave = validacionNulo(clave);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                valClave = validacionNulo(clave);
            }
        });

        //Listener focus en password 1
        clave.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    valClave = validacionNulo(clave);
                }
            }
        });

        //Listener cambio de texto en password 2
        claveRep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                valClaveRep = validacionNulo(claveRep);
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
        });
        //Listener focus en password 2
        claveRep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    valClaveRep = validacionNulo(claveRep);
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

        //Listener cambio de texto en email
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                valClave = validacionNulo(clave);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                valEmail = validacionNulo(email) && validacionEmail(email);
            }
        });
        //Listener focus en email
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() { //Listener focus en email
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    valEmail = validacionNulo(email) && validacionEmail(email);
                }}});

        //Listener focus en tipo debito
        debito.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    valTipo = validacionTipo(debito,credito);
                    if (mes.getText().toString().length() > 0 && anio.getText().toString().length() > 0) {
                        valFecha = validacionFecha(mes, anio);
                    }
                }
            }
        });

        //Listener checked en tipo debito
        debito.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) //Line A
            {
                valTipo = validacionTipo(debito,credito);
                if (mes.getText().toString().length() > 0 && anio.getText().toString().length() > 0) {
                    valFecha = validacionFecha(mes, anio);
                }
            }
        });

        //Listener focus en tipo credito
        credito.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    valTipo = validacionTipo(debito,credito);
                    if (mes.getText().toString().length() > 0 && anio.getText().toString().length() > 0) {
                        valFecha = validacionFecha(mes, anio);
                    }
                }
            }
        });

        //Listener checked en tipo credito
        credito.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) //Line A
            {
                valTipo = validacionTipo(debito,credito);
                if (mes.getText().toString().length() > 0 && anio.getText().toString().length() > 0) {
                    valFecha = validacionFecha(mes, anio);
                }
            }
        });

        //Listener cambio de texto en tarjeta
        tarjeta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                valTarjeta = validacionNulo(tarjeta);
                if(valTarjeta){
                    CCV.setEnabled(true);
                    mes.setEnabled(true);
                    anio.setEnabled(true);
                } else{
                    CCV.setEnabled(false);
                    mes.setEnabled(false);
                    anio.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                valTarjeta = validacionNulo(tarjeta);
                if(valTarjeta){
                    CCV.setEnabled(true);
                    mes.setEnabled(true);
                    anio.setEnabled(true);
                } else{
                    CCV.setEnabled(false);
                    mes.setEnabled(false);
                    anio.setEnabled(false);
                }
            }
        });
        //Listener focus en tarjeta
        tarjeta.setOnFocusChangeListener(new View.OnFocusChangeListener() { //Listener focus en email
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    valTarjeta = validacionNulo(tarjeta);
                    if(valTarjeta){
                        CCV.setEnabled(true);
                        mes.setEnabled(true);
                        anio.setEnabled(true);
                    } else{
                        CCV.setEnabled(false);
                        mes.setEnabled(false);
                        anio.setEnabled(false);
                    }
                }}});

        //Listener cambio de texto en CCV
        CCV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                valCCV = validacionNulo(CCV);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                valCCV = validacionNulo(CCV);
            }
        });
        //Listener focus en CCV
        CCV.setOnFocusChangeListener(new View.OnFocusChangeListener() { //Listener focus en email
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    valCCV = validacionNulo(CCV);
                }}});

        //Listener cambio de texto en mes
        mes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                valMes = validacionMes(mes);
                if (mes.getText().toString().length() > 0 && anio.getText().toString().length() > 0) {
                    valFecha = validacionFecha(mes, anio);
                }
                }

            @Override
            public void afterTextChanged(Editable editable) {
                valMes = validacionMes(mes);
                if (mes.getText().toString().length() > 0 && anio.getText().toString().length() > 0) {
                    valFecha = validacionFecha(mes, anio);
                }
            }
        });

        //Listener focus en mes
        mes.setOnFocusChangeListener(new View.OnFocusChangeListener() { //Listener focus en email
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    valMes = validacionMes(mes);
                    if (mes.getText().toString().length() > 0 && anio.getText().toString().length() > 0) {
                        valFecha = validacionFecha(mes, anio);
                    }
                }}});

        //Listener cambio de texto en año
        anio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                valAnio = validacionNulo(anio);
                if (mes.getText().toString().length() > 0 && anio.getText().toString().length() > 0) {
                    valFecha = validacionFecha(mes, anio);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                valAnio = validacionNulo(anio);
                if (mes.getText().toString().length() > 0 && anio.getText().toString().length() > 0) {
                    valFecha = validacionFecha(mes, anio);
                }
            }
        });

        //Listener focus en año
        anio.setOnFocusChangeListener(new View.OnFocusChangeListener() { //Listener focus en email
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    valAnio = validacionNulo(anio);
                    if (mes.getText().toString().length() > 0 && anio.getText().toString().length() > 0) {
                        valFecha = validacionFecha(mes, anio);
                    }
                }
            }
        });

        //Listener del botón switch de carga inicial
        switchCarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cantidadCarga.setVisibility(View.VISIBLE);
                    textoCarga.setVisibility(View.VISIBLE);
                } else {
                    cantidadCarga.setVisibility(View.GONE);
                    textoCarga.setVisibility(View.GONE);
                    switchCarga.setText(R.string.string_cargaInicial);
                    switchCarga.setTextColor(Color.BLACK);
                    switchCarga.setTypeface(null, Typeface.NORMAL);
                }
            }
        });

        //Listener del cambio en seekbar
        cantidadCarga.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int val = (i * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                textoCarga.setX(val);
                textoCarga.setText(String.valueOf(i));
                valCarga = validacionCarga();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //Listener del botón de los términos y condiciones
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

        //Listener del botón de registro
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mes.getText().toString().length() > 0 && anio.getText().toString().length() > 0) {
                    valFecha = validacionFecha(mes, anio);
                }
                valCarga = validacionCarga();
                valTipo = validacionTipo(debito,credito);
                if (valClave && valClaveRep && valEmail && valTipo && valTarjeta && valCCV && valMes && valAnio && valFecha && valCarga) {
                    Toast.makeText(getApplicationContext(), "Registro correcto", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Registro incorrecto", Toast.LENGTH_LONG).show();
                    validacionesDefault();
                }
            }
        });
    }

    private boolean validacionNulo(EditText campoIngresado){
        boolean valNulo;
        if (campoIngresado.getText().toString().length() <= 0) {
            campoIngresado.setError("Este campo es obligatorio");
            valNulo = false;
        } else {
            campoIngresado.setError(null);
            valNulo = true;
        }
        return valNulo;
    }

    private boolean validacionEmail(EditText textoEmail){
        boolean valEmail;
        String cantLetras = textoEmail.getText().toString().substring(textoEmail.getText().toString().indexOf("@")+1); //Obtengo lo que hay después de la arroba

        if (textoEmail.getText().toString().trim().contains("@") && cantLetras.length() >= 3){
            textoEmail.setError(null);
            valEmail = true;
        } else {
            textoEmail.setError("Ingrese un correo electrónico válido");
            valEmail = false;
        }

        return valEmail;
    }

    private boolean validacionMes(EditText textoMes) {
        boolean valMes;
        if (textoMes.getText().toString().length() <= 0) {
            textoMes.setError("Este campo es obligatorio");
            valMes = false;
        } else {
            int minimo = 1;
            int maximo = 12;
            int enteroMes = Integer.parseInt(textoMes.getText().toString());
            if (enteroMes >= minimo && enteroMes <= maximo) {
                valMes=true;
            } else {
                textoMes.setError("El número debe encontrarse entre 1 y 12");
                textoMes.setText(textoMes.getText().toString().substring(0, textoMes.getText().toString().length()-1));
                valMes = false;
            }
        }
        return valMes;
    }

    private boolean validacionFecha(EditText textoMes, EditText textoAño){
        boolean valFecha;
        DateFormat formato = new SimpleDateFormat("MM-yy");
        String fechaIngresadaStr = textoMes.getText().toString()+"-"+textoAño.getText().toString();
        Date fechaIngresada = null;

        try {
            fechaIngresada = formato.parse(fechaIngresadaStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calActual = Calendar.getInstance();
        Date fechaActual = new Date();
        calActual.setTime(fechaActual);
        calActual.add(Calendar.MONTH, 2);

        Calendar calIngresada = Calendar.getInstance();
        calIngresada.setTime(fechaIngresada);

        if (calIngresada.before(calActual)){
            labelFecha.setText("La fecha de vencimiento ingresada debe ser superior a los próximos 3 meses");
            labelFecha.setTextColor(Color.RED);
            labelFecha.setTypeface(null, Typeface.BOLD);
            valFecha=false;
        } else {
            labelFecha.setText(R.string.string_fecha);
            labelFecha.setTextColor(Color.parseColor("#808080"));
            labelFecha.setTypeface(null, Typeface.NORMAL);
            valFecha=true;
        }

        return valFecha;
    }

    private boolean validacionTipo(RadioButton debitoRB, RadioButton creditoRB){
        boolean valTipo;
        if(debitoRB.isChecked()) {
            labelTarjeta.setText(R.string.string_tipodetarjeta);
            labelTarjeta.setTextColor(Color.parseColor("#808080"));
            labelTarjeta.setTypeface(null, Typeface.NORMAL);
            valTipo=true;
        } else if (creditoRB.isChecked()) {
            labelTarjeta.setText(R.string.string_tipodetarjeta);
            labelTarjeta.setTextColor(Color.parseColor("#808080"));
            labelTarjeta.setTypeface(null, Typeface.NORMAL);
            valTipo = true;
        }
        else{
            labelTarjeta.setText("Debe seleccionar un tipo de tarjeta.");
            labelTarjeta.setTextColor(Color.RED);
            labelTarjeta.setTypeface(null, Typeface.BOLD);
            valTipo=false;
        }
        return valTipo;
    }

    private boolean validacionCarga(){
        if(switchCarga.isChecked()) {
            if (cantidadCarga.getProgress() > 0) {
                switchCarga.setText(R.string.string_cargaInicial);
                switchCarga.setTextColor(Color.BLACK);
                switchCarga.setTypeface(null, Typeface.NORMAL);
                return true;
            } else {
                switchCarga.setText("El monto seleccionado debe ser mayor a 0 pesos");
                switchCarga.setTextColor(Color.RED);
                switchCarga.setTypeface(null, Typeface.BOLD);
                return false;
            }
        }
        else {
            switchCarga.setError(null);
            return true;
        }
    }

    private void validacionesDefault(){
        validacionNulo(clave);
        validacionNulo(claveRep);
        validacionNulo(email);
        validacionTipo(debito,credito);
        validacionNulo(tarjeta);
        validacionNulo(CCV);
        validacionNulo(mes);
        validacionNulo(anio);
        validacionCarga();
    }
}