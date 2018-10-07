package com.cenet.mobsecure;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    Button btnReg,btnLogin;
    EditText edPin;
    TextView txtforget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);


        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.customdialoglogin, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        AlertDialog dialog = alert.create();
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
        dialog.show();
        btnReg = (Button)dialog.findViewById(R.id.btnReg);
        edPin = (EditText)dialog.findViewById(R.id.et_pin);
        txtforget = (TextView) dialog.findViewById(R.id.txt_forget);
        txtforget.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent i = new Intent(LoginActivity.this,ForgetPin.class);
                startActivity(i);
                finish();
                return false;
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin = edPin.getText().toString();
                String vpin = Common.getPreferenceString(getApplicationContext(),"pass",null);

                if(pin.equals(vpin) && pin.length() == 4) {
                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                    return;
                } else {
                    Toast.makeText(getApplicationContext(),"Invalid Input",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.customdialoglogin, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                alert.setView(alertLayout);
                alert.setCancelable(true);
                AlertDialog dialog = alert.create();
                dialog.requestWindowFeature(1);
                dialog.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
                dialog.show();

                final Button btnReg;
                final EditText edPin;

                btnReg = (Button)dialog.findViewById(R.id.btnReg);
                edPin = (EditText)dialog.findViewById(R.id.et_pin);

                btnReg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pin = edPin.getText().toString();
                        String vpin = Common.getPreferenceString(getApplicationContext(),"pass",null);

                        if(pin.equals(vpin) && pin.length() == 4) {
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();
                            return;
                        } else {
                            Toast.makeText(getApplicationContext(),"Invalid Input",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}
