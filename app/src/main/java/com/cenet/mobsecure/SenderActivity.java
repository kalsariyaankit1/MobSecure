package com.cenet.mobsecure;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SenderActivity extends AppCompatActivity {
    Button btnCh;
    EditText edPhone;
    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pDialog;
    RecyclerView recyclerView;
    JSONArray jsonArray = null;
    List<Code> codeList = null;
    private static final String TAG_SUCCESS = "success";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCode);

        codeList = new ArrayList<Code>();


        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.customdialogsender, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        final AlertDialog dialog = alert.create();
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
        dialog.show();

        btnCh = (Button) dialog.findViewById(R.id.btnCheck);
        edPhone = (EditText)dialog.findViewById(R.id.et_phone);
        btnCh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.setPreferenceString(getApplicationContext(),"destNo",edPhone.getText().toString());
                String[] arr = {edPhone.getText().toString()};
                new GetAllCode().execute(arr);
                dialog.dismiss();
            }
        });
    }
    class GetAllCode extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SenderActivity.this);
            pDialog.setMessage("Creating Items..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            JSONObject json = null;
            String uid = Common.getPreferenceString(getApplicationContext(), "UId", "");
            String phone = args[0];
            try {
                json = jsonParser.getDataFromWeb("GetAllCode.php?phone="+phone);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    jsonArray = json.getJSONArray("product");
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Code c = new Code();
                        c.setId(Integer.parseInt(jsonObject.getString("Id")));
                        c.setTitle(jsonObject.getString("Code_Name"));
                        c.setCode(jsonObject.getString("Code"));
                        c.setDestNumber(phone);
                        codeList.add(c);
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"No Records Found(s).",Toast.LENGTH_SHORT).show();
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
            CodeAdapter codeAdapter = new CodeAdapter(getApplicationContext(),codeList,recyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            //contactAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(codeAdapter);
        }

    }
}
