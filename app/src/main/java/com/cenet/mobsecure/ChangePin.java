package com.cenet.mobsecure;

import android.content.Intent;
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

public class ChangePin extends AppCompatActivity {
    Button btnReg;
    EditText edPin,edCPin;
    AlertDialog.Builder alert;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.customdialogchangepin, null);

        alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        dialog = alert.create();
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
        dialog.show();
        btnReg = (Button)dialog.findViewById(R.id.btnSubmit);
        edPin = (EditText)dialog.findViewById(R.id.et_pin);
        edCPin = (EditText)dialog.findViewById(R.id.et_cpin);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin = edPin.getText().toString();
                String cpin = edCPin.getText().toString();
                if(!cpin.equals("") && !pin.equals("") && pin.length() == 4 && cpin.length() == 4 && pin.equals(cpin)) {
                    Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                    Common.setPreferenceString(getApplicationContext(), "pass", edPin.getText().toString());
                    Intent intent = new Intent(ChangePin.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Invalid Input.",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
