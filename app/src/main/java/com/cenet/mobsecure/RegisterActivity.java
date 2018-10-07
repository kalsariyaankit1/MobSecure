package com.cenet.mobsecure;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    Button btnReg,btnOpenDialog;
    EditText edPin,edPhone;
    AlertDialog.Builder alert;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnOpenDialog = (Button) findViewById(R.id.btnRegister);

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.customdialogreg, null);

        alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        dialog = alert.create();
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
        dialog.show();
        btnReg = (Button)dialog.findViewById(R.id.btnReg);
        edPin = (EditText)dialog.findViewById(R.id.et_pin);
        edPhone = (EditText)dialog.findViewById(R.id.et_phone);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edPhone.getText().toString();
                String pin = edPin.getText().toString();
                if(!phone.equals("") && !pin.equals("") && pin.length() == 4 && phone.length() == 10) {
                    //Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,VerifyActivity.class);
                    intent.putExtra("phone",phone);
                    intent.putExtra("pin",pin);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Invalid Input.",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.customdialogreg, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                alert.setView(alertLayout);
                alert.setCancelable(true);
                AlertDialog dialog = alert.create();
                dialog.requestWindowFeature(1);
                dialog.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
                dialog.show();
                final Button btnReg;
                final EditText edPin,edPhone;

                btnReg = (Button)dialog.findViewById(R.id.btnReg);
                edPin = (EditText)dialog.findViewById(R.id.et_pin);
                edPhone = (EditText)dialog.findViewById(R.id.et_phone);
                btnReg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phone = edPhone.getText().toString();
                        String pin = edPin.getText().toString();
                        if(!phone.equals("") && !pin.equals("") && pin.length() == 4 && phone.length() == 10) {
                            Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                            Common.setPreferenceString(getApplicationContext(), "pass", edPin.getText().toString());
                            Common.setPreferenceString(getApplicationContext(), "regno", edPhone.getText().toString());
                            Common.setPreferenceString(getApplicationContext(), "is_Reg", "1");
                            Intent intent = new Intent(RegisterActivity.this,VerifyActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Invalid Input.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}
