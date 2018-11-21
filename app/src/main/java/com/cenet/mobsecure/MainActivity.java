package com.cenet.mobsecure;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {
    DevicePolicyManager deviceManger;
    ActivityManager activityManager;
    ComponentName compName;
    JSONParser jsonParser = new JSONParser();
    static final int RESULT_ENABLE = 1;
    public int imagelist[] = {R.drawable.phone,R.drawable.primary,R.drawable.sos,R.drawable.normal,R.drawable.sil,R.drawable.location,R.drawable.won,R.drawable.woff,R.drawable.bon,R.drawable.boff,R.drawable.ringloud,R.drawable.lockscreen,R.drawable.changepin,R.drawable.changephone,R.drawable.don,R.drawable.doff,R.drawable.simserial,R.drawable.track};
    public static String[] modList = {"Enter Cell Number", "Primary Numbers", "SOS Contact", "Normal", "Silent", "Location", "Wifi ON", "Wifi OFF", "Blutooth On", "Blutooth Off", "Ring Out Loud", "Lock Screen", "Change Pin", "Change Pin Recovery Number", "Data On", "Data OFF", "Sim Serial Key", "Show Track Records"};

    GridView gridView;

    LayoutInflater inflater;
    View alertLayout;

    EditText edCode;
    Button btnApply;
    Button btnReg;
    EditText edPin, edCPin;

    AlertDialog dialog;

    private ProgressDialog pDialog;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.grid);
        gridView.setAdapter(new CustomGridAdapter(getApplicationContext(), modList, imagelist));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "regno", ""));
                    btnApply.setText("Close");
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
                if (position == 1) {
                    Intent intent = new Intent(MainActivity.this, PrimaryContactActivity.class);
                    startActivity(intent);
                }
                if (position == 2) {
                    Intent intent = new Intent(MainActivity.this, SOSActivity.class);
                    startActivity(intent);
                }
                if (position == 3) {
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "norm", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String uid = Common.getPreferenceString(getApplicationContext(), "UId", "");
                            String[] arr = {uid.toString(), "Normal Mode", edCode.getText().toString()};
                            new CreateNewProduct().execute(arr);
                            Common.setPreferenceString(getApplicationContext(), "norm", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                }
                if (position == 4) {
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "sil", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String uid = Common.getPreferenceString(getApplicationContext(), "UId", "");
                            String[] arr = {uid.toString(), "Silent Mode", edCode.getText().toString()};
                            new CreateNewProduct().execute(arr);

                            Common.setPreferenceString(getApplicationContext(), "sil", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                }
                if (position == 5) {
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "loc", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String uid = Common.getPreferenceString(getApplicationContext(), "UId", "");
                            String[] arr = {uid.toString(), "Location", edCode.getText().toString()};
                            new CreateNewProduct().execute(arr);
                            Common.setPreferenceString(getApplicationContext(), "loc", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                }
                if (position == 6) {
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "wifion", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String uid = Common.getPreferenceString(getApplicationContext(), "UId", "");
                            String[] arr = {uid.toString(), "WIFI On", edCode.getText().toString()};
                            new CreateNewProduct().execute(arr);
                            Common.setPreferenceString(getApplicationContext(), "wifion", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                }
                if (position == 7) {
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "wifioff", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String uid = Common.getPreferenceString(getApplicationContext(), "UId", "");
                            String[] arr = {uid.toString(), "WIFI Off", edCode.getText().toString()};
                            new CreateNewProduct().execute(arr);
                            Common.setPreferenceString(getApplicationContext(), "wifioff", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                }
                if (position == 8) {
                    //Blutooth On
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "bon", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String uid = Common.getPreferenceString(getApplicationContext(), "UId", "");
                            String[] arr = {uid.toString(), "Bluetooth On", edCode.getText().toString()};
                            new CreateNewProduct().execute(arr);
                            Common.setPreferenceString(getApplicationContext(), "bon", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                }
                if (position == 9) {
                    //Blutooth Off
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "boff", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String uid = Common.getPreferenceString(getApplicationContext(), "UId", "");
                            String[] arr = {uid.toString(), "Bluetooth Off", edCode.getText().toString()};
                            new CreateNewProduct().execute(arr);
                            Common.setPreferenceString(getApplicationContext(), "boff", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                }
                if (position == 10) {
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "rout", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String uid = Common.getPreferenceString(getApplicationContext(), "UId", "");
                            String[] arr = {uid.toString(), "Ring Out Loud", edCode.getText().toString()};
                            new CreateNewProduct().execute(arr);
                            Common.setPreferenceString(getApplicationContext(), "rout", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                }
                if (position == 11) {
                    deviceManger = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
                    activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                    compName = new ComponentName(getApplicationContext(), MyAdmin.class);

                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Some Description About Your Admin");
                    startActivityForResult(intent, RESULT_ENABLE);

                    dialog_Bind();

                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "lock", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String uid = Common.getPreferenceString(getApplicationContext(), "UId", "");
                            String[] arr = {uid.toString(), "Lock Screen", edCode.getText().toString()};
                            new CreateNewProduct().execute(arr);
                            Common.setPreferenceString(getApplicationContext(), "lock", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                }
                if (position == 12) {
                    //Change Pin
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.customdialogchangepin, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setView(alertLayout);
                    alert.setCancelable(true);
                    dialog = alert.create();
                    dialog.requestWindowFeature(1);
                    dialog.getWindow().setBackgroundDrawable((Drawable) new ColorDrawable(0));
                    dialog.show();
                    btnReg = (Button) dialog.findViewById(R.id.btnSubmit);
                    edPin = (EditText) dialog.findViewById(R.id.et_pin);
                    edCPin = (EditText) dialog.findViewById(R.id.et_cpin);

                    btnReg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String pin = edPin.getText().toString();
                            String cpin = edCPin.getText().toString();
                            if (!cpin.equals("") && !pin.equals("") && pin.length() == 4 && cpin.length() == 4 && pin.equals(cpin)) {
                                Common.setPreferenceString(getApplicationContext(), "pass", edPin.getText().toString());
                                Toast.makeText(getApplicationContext(), "Pin Changed", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                if (position == 13) {
                    //Change Pin Number
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.customdialogrecoverynumber, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setView(alertLayout);
                    alert.setCancelable(true);
                    AlertDialog dialog = alert.create();
                    dialog.requestWindowFeature(1);
                    dialog.getWindow().setBackgroundDrawable((Drawable) new ColorDrawable(0));
                    dialog.show();
                    final Button btnSave;
                    final EditText edPin, edPhone;

                    btnSave = (Button) dialog.findViewById(R.id.btnSave);
                    edPin = (EditText) dialog.findViewById(R.id.et_pin);
                    edPhone = (EditText) dialog.findViewById(R.id.et_phone);
                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String phone = edPhone.getText().toString();
                            String pin = edPin.getText().toString();
                            String cpin = Common.getPreferenceString(getApplicationContext(), "pass", "");
                            String cphone = Common.getPreferenceString(getApplicationContext(), "regno", "");
                            if (cphone.equals(phone)) {
                                Toast.makeText(getApplicationContext(), "Recovery Number has been already registered.", Toast.LENGTH_SHORT).show();
                            } else {
                                if (cpin.equals(pin) && !phone.equals("") && !pin.equals("") && pin.length() == 4 && phone.length() == 10) {
                                    Common.setPreferenceString(getApplicationContext(), "regno", edPhone.getText().toString());
                                    Toast.makeText(getApplicationContext(), "Recovery Number has been changed.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Invalid Input.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
                if (position == 14) {
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "don", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String uid = Common.getPreferenceString(getApplicationContext(), "UId", "");
                            String[] arr = {uid.toString(), "Data On", edCode.getText().toString()};
                            new CreateNewProduct().execute(arr);

                            Common.setPreferenceString(getApplicationContext(), "don", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                }
                if (position == 15) {
                    dialog_Bind();
                    edCode.setText(Common.getPreferenceString(getApplicationContext(), "doff", ""));
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String uid = Common.getPreferenceString(getApplicationContext(), "UId", "");
                            String[] arr = {uid.toString(), "Data Off", edCode.getText().toString()};
                            new CreateNewProduct().execute(arr);
                            Common.setPreferenceString(getApplicationContext(), "doff", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                }
                if (position == 16) {
                    dialog_Bind();
                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

                    edCode.setText(telephonyManager.getSimSerialNumber());
                    btnApply.setText("Close");
                    btnApply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Common.setPreferenceString(getApplicationContext(), "sim", edCode.getText().toString());
                            dialog.dismiss();
                        }
                    });
                }
                if(position == 17) {
                    Intent intent = new Intent(MainActivity.this,TrackRecordActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    public void dialog_Bind() {
        inflater = getLayoutInflater();
        alertLayout = inflater.inflate(R.layout.customdialogmodule, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        dialog = alert.create();
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
        dialog.show();
        btnApply = (Button)dialog.findViewById(R.id.btnApply);
        edCode = (EditText) dialog.findViewById(R.id.et_Code);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_ENABLE:
                if (resultCode == Activity.RESULT_OK) {
                    Log.i("DeviceAdminSample", "Admin enabled!");
                    Toast.makeText(getApplicationContext(),"Admin Enabled",Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("DeviceAdminSample", "Admin enable FAILED!");
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Intent i;
        switch (menuItem.getItemId()) {
            case R.id.btnCls:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Are you sure, You wanted Close Account");
                        alertDialogBuilder.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        String uid = Common.getPreferenceString(getApplicationContext(), "UId", "");
                                        String[] arr = {uid.toString()};
                                        new DeleteUser().execute(arr);
                                        Common.clearPre(getApplicationContext());
                                        Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                break;
            case R.id.btnLogout:
                FirebaseAuth.getInstance().signOut();
                i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.btnSender:
                i = new Intent(MainActivity.this, SenderActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }

    class CreateNewProduct extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Creating Items..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            JSONObject json = null;
            String uid = args[0];
            String c_name = args[1];
            String code = args[2];
            try {
                json = jsonParser.getDataFromWeb("InsertCode.php?uid="+ uid +"&c_name="+c_name+"&code="+code);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    String successack = "Product inserted successfully";
                    Log.d("Success Response", successack );
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
    class DeleteUser extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Creating Items..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            JSONObject json = null;
            String uid = args[0];
            try {
                json = jsonParser.getDataFromWeb("DeleteUser.php?uid="+ uid);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    String successack = "Product inserted successfully";
                    Log.d("Success Response", successack );
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
        }

    }
}
