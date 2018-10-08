package com.cenet.mobsecure;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SenderActivity extends AppCompatActivity {
    Button btnCh;
    EditText edPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.customdialogsender, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        AlertDialog dialog = alert.create();
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
        dialog.show();

        btnCh = (Button) dialog.findViewById(R.id.btnCheck);
        edPhone = (EditText)dialog.findViewById(R.id.et_phone);
        btnCh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
