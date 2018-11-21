package com.cenet.mobsecure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class VerifyActivity extends AppCompatActivity {
    Button btnReg;
    EditText edPin;
    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String verificationCode;
    String phonenumber,otp;
    String pin,regno;
    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pDialog;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        Bundle b = new Bundle();
        b = getIntent().getExtras();
        pin = b.getString("pin");
        regno = b.getString("phone");

        StartFirebaseLogin();
        generateOtp();

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.customdialogverify, null);



        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        AlertDialog dialog = alert.create();
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
        dialog.show();
        btnReg = (Button)dialog.findViewById(R.id.btnSubmit);
        edPin = (EditText)dialog.findViewById(R.id.et_otp);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = edPin.getText().toString();
                if(otp.length() == 6) {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
                    SigninWithPhone(credential);
                }
            }
        });
    }

    private void SigninWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Common.setPreferenceString(getApplicationContext(), "pass", pin);
                            Common.setPreferenceString(getApplicationContext(), "regno", regno);
                            Common.setPreferenceString(getApplicationContext(), "is_Reg", "1");
                            String arr[] = {regno,regno,pin};
                            new CreateNewProduct().execute(arr);
                            startActivity(new Intent(VerifyActivity.this,MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),"Incorrect OTP",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    class CreateNewProduct extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(VerifyActivity.this);
            pDialog.setMessage("Creating User..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            JSONObject json = null;
            String ucon = args[0];
            String urcon = args[1];
            String pin = args[2];
            try {
                json = jsonParser.getDataFromWeb("InsertUser.php?ucon="+ ucon +"&urcon="+urcon+"&upin="+pin);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    String uid = json.getString("UId");
                    Common.setPreferenceString(getApplicationContext(),"UId",uid);
                    //Log.d("Success Response", successack );
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
            //pDialog.dismiss();
        }

    }
    private void generateOtp() {
        phonenumber = "+91" + regno;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,                     // Phone number to verify
                60,                           // Timeout duration
                TimeUnit.SECONDS,                // Unit of timeout
                VerifyActivity.this,        // Activity (for callback binding)
                mCallback);
    }

    private void StartFirebaseLogin() {
        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(getApplicationContext(),"verification completed",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getApplicationContext(),"verification failed",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                Toast.makeText(getApplicationContext(),"Code sent",Toast.LENGTH_SHORT).show();
            }
        };
    }
}
