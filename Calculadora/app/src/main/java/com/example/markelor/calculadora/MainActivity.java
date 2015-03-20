package com.example.markelor.calculadora;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.markelor.calculadora.logic.CalcLogic;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private CalcLogic calcLogic = new CalcLogic();
    private TextView pantalla;
    private Button btnClear;
    private Button btnDiv;
    private Button btnMul;
    private Button btnSum;
    private Button btnRes;
    private Button btnIgual;
    private Button btnComa;
    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private String operacion = "";
    private String numero1 ="";
    private String numero2 = "";
    private double resultado=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pantalla = (TextView) findViewById(R.id.textView);
        pantalla.setOnClickListener(this);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnDiv.setOnClickListener(this);
        btnMul = (Button) findViewById(R.id.btnMul);
        btnMul.setOnClickListener(this);
        btnSum = (Button) findViewById(R.id.btnSum);
        btnSum.setOnClickListener(this);
        btnRes = (Button) findViewById(R.id.btnRes);
        btnRes.setOnClickListener(this);
        btnIgual = (Button) findViewById(R.id.btnIgual);
        btnIgual.setOnClickListener(this);
        btnComa = (Button) findViewById(R.id.btnComa);
        btnComa.setOnClickListener(this);
        btn0 = (Button) findViewById(R.id.btn0);
        btn0.setOnClickListener(this);
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
        btn7 = (Button) findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
        btn8 = (Button) findViewById(R.id.btn8);
        btn8.setOnClickListener(this);
        btn9 = (Button) findViewById(R.id.btn9);
        btn9.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int selection = v.getId();

        try {
            switch (selection) {
                case R.id.btnClear:
                    pantalla.setText(" ");
                    numero1="";
                    numero2="";
                    calcLogic.setTotal(0.0);
                    break;
                case R.id.btnDiv:
                    operacion = "/";
                    break;
                case R.id.btnMul:
                    operacion = "*";
                    break;
                case R.id.btnSum:
                    operacion = "+";
                    break;
                case R.id.btnRes:
                    operacion = "-";
                    break;
                case R.id.btnIgual:
                    Log.d("igual", calcLogic.getTotalString());
                    hacerOperacion();
                    pantalla.setText(calcLogic.getTotalString());
                    operacion="";
                    numero1=calcLogic.getTotalString();
                    numero2="";
                    break;
                case R.id.btnComa:
                    break;
                case R.id.btn0:
                    if (operacionPulsada()) {
                        Log.d("prueba","hemen");
                        numero2=numero2.concat("0");
                        pantalla.setText(numero2);

                    } else {
                        Log.d("prueba","hemen2");
                        numero1=numero1.concat("0");
                        pantalla.setText(numero1);
                    }

                    Log.d("aa", numero1);
                    Log.d("aa", numero2);

                    break;
                case R.id.btn1:
                    if (operacionPulsada()) {
                        numero2=numero2.concat("1");
                        pantalla.setText(numero2);

                    } else {
                        numero1=numero1.concat("1");
                        pantalla.setText(numero1);
                    }
                    break;
                case R.id.btn2:
                    if (operacionPulsada()) {
                        numero2=numero2.concat("2");
                        pantalla.setText(numero2);

                    } else {
                        numero1=numero1.concat("2");
                        pantalla.setText(numero1);
                    }
                    break;
                case R.id.btn3:
                    if (operacionPulsada()) {
                        numero2=numero2.concat("3");
                        pantalla.setText(numero2);

                    } else {
                        numero1=numero1.concat("3");
                        pantalla.setText(numero1);
                    }
                    break;
                case R.id.btn4:
                    if (operacionPulsada()) {
                        numero2=numero2.concat("4");
                        pantalla.setText(numero2);

                    } else {
                        numero1=numero1.concat("4");
                        pantalla.setText(numero1);
                    }
                    break;
                case R.id.btn5:
                    if (operacionPulsada()) {
                        numero2=numero2.concat("5");
                        pantalla.setText(numero2);

                    } else {
                        numero1=numero1.concat("5");
                        pantalla.setText(numero1);
                    }
                    break;
                case R.id.btn6:
                    if (operacionPulsada()) {
                        numero2=numero2.concat("6");
                        pantalla.setText(numero2);

                    } else {
                        numero1=numero1.concat("6");
                        pantalla.setText(numero1);
                    }
                    break;
                case R.id.btn7:
                    if (operacionPulsada()) {
                        numero2=numero2.concat("7");
                        pantalla.setText(numero2);

                    } else {
                        numero1=numero1.concat("7");
                        pantalla.setText(numero1);
                    }
                    break;
                case R.id.btn8:
                    if (operacionPulsada()) {
                        numero2=numero2.concat("8");
                        pantalla.setText(numero2);

                    } else {
                        numero1=numero1.concat("8");
                        pantalla.setText(numero1);
                    }
                    break;
                case R.id.btn9:
                    if (operacionPulsada()) {
                        numero2=numero2.concat("9");
                        pantalla.setText(numero2);

                    } else {
                        numero1=numero1.concat("9");
                        pantalla.setText(numero1);
                    }
                    break;
            }
        } catch (Exception e) {
            pantalla.setText("Error");

        }
    }

    private boolean operacionPulsada() {
        pantalla.clearComposingText();
        boolean pulsada = false;
        if (!operacion.equals("")) {
            pulsada = true;
        }
        return pulsada;
    }

    private void hacerOperacion() {
        if (operacion.equals("/")) {
            calcLogic.setTotal(Double.parseDouble(numero1));
            resultado=calcLogic.divide(numero2);
            calcLogic.setTotal(resultado);

        } else if (operacion.equals("*")) {
            calcLogic.setTotal(Double.parseDouble(numero1));
            resultado = calcLogic.multiply(numero2);
            calcLogic.setTotal(resultado);

        } else if (operacion.equals("+")) {
            calcLogic.setTotal(Double.parseDouble(numero1));
            resultado = calcLogic.add(numero2);
            calcLogic.setTotal(resultado);

        } else if (operacion.equals("-")) {
            calcLogic.setTotal(Double.parseDouble(numero1));
            resultado = calcLogic.subtract(numero2);
            calcLogic.setTotal(resultado);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
