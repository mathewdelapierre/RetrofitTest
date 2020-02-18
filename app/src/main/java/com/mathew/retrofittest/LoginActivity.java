package com.mathew.retrofittest;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mathew.retrofittest.Network.ApiServices;
import com.mathew.retrofittest.Network.InitLibrary;
import com.mathew.retrofittest.pojo.CountResponse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText ed_email;
    EditText ed_password;
    Button btn_login;
    TextView txt_signup;
    TextView txt_forgetpass;
    String str_email, str_password;
    SharedPreferences pref;
    CheckBox chk_rememberme;
    private Boolean saveLogin;
    String refreshedToken;
    ProgressDialog pd;

    String str_customer_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getApplicationContext().getSharedPreferences("test", 0); // 0 - for private mode

        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_password = (EditText) findViewById(R.id.ed_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        txt_signup = (TextView) findViewById(R.id.txt_signup);
        txt_forgetpass = (TextView) findViewById(R.id.txt_forgetpass);
        chk_rememberme = (CheckBox) findViewById(R.id.chk_rememberme);
        saveLogin = pref.getBoolean("saveLogin", false);
        Log.i("llll", "llll" + saveLogin);


        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                login();

            }
        });


        saveLogin = pref.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            ed_email.setText(pref.getString("email", ""));
            ed_password.setText(pref.getString("password", ""));
            chk_rememberme.setChecked(true);
        }


    }

    public void login() {
        Log.d(TAG, "Login");
        if (!validate()) {
            onLoginFailed();
            return;
        }
        btn_login.setEnabled(false);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                       /* Intent i = new Intent(LoginActivity.this, ScrollableTabsActivity.class);
                        startActivity(i);
                        finish();*/
                        // onLoginFailed();
                    }
                }, 10);
    }


    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        // moveTaskToBack(true);
        finish();
    }

    public void onLoginSuccess() {
        btn_login.setEnabled(true);
        Login();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        btn_login.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        str_email = ed_email.getText().toString();
        str_password = ed_password.getText().toString();

        if (str_email.isEmpty()) {
            ed_email.setError("enter a valid User Name");
            valid = false;
        } else {
            ed_email.setError(null);
        }
        if (str_password.isEmpty() || str_password.length() < 4 || str_password.length() > 20) {
            ed_password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            ed_password.setError(null);
        }

        return valid;
    }


    public void Login() {



        ApiServices api = InitLibrary.getInstance();
        Log.i("refreshedToken", "refreshedToken" + refreshedToken);
        Call<CountResponse> call = api.login(str_password);
        call.enqueue(new Callback<CountResponse>() {
            @Override
            public void onResponse(Call<CountResponse> call, Response<CountResponse> response) {
                if (response.isSuccessful()) {
                    // pDialog.dismiss();
                    CountResponse data = response.body();
                    Log.i("rrrr", "rrrr" + new Gson().toJson(data));



                } else {

                }
            }

            @Override
            public void onFailure(Call<CountResponse> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }


    }


