package com.cenet.mobsecure;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SOSActivity extends AppCompatActivity {
    private FloatingActionButton floatingActionButton;
    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pDialog;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);


        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.customdialogprimarycontact, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(SOSActivity.this);
                alert.setView(alertLayout);
                alert.setCancelable(true);
                final AlertDialog dialog = alert.create();
                dialog.requestWindowFeature(1);
                dialog.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
                dialog.show();
                final Button btnReg;
                final EditText edname,edPhone;

                btnReg = (Button)dialog.findViewById(R.id.btnReg);
                edname = (EditText)dialog.findViewById(R.id.et_name);
                edPhone = (EditText)dialog.findViewById(R.id.et_contact);
                btnReg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edPhone.getText().toString().length() == 10) {
                            String uid = Common.getPreferenceString(getApplicationContext(), "UId", "");
                            String arr[] = {uid.toString(), edPhone.getText().toString(), edname.getText().toString()};
                            new CreateNewUser().execute(arr);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(),"Invalid Input.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    class CreateNewUser extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SOSActivity.this);
            pDialog.setMessage("Creating Items..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            JSONObject json = null;
            String uid = args[0];
            String pcon = args[1];
            String pname = args[2];
            try {
                json = jsonParser.getDataFromWeb("InsertContact.php?uid="+ uid +"&pcon="+pcon+"&pname="+pname+"&isSOS="+true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
        }

    }
}
