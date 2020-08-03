package com.example.fypwebhost;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText editTextName, editTextEmail, editTextPassword, editTextVerificationCode;
    Button buttonAdd, buttonVerify;
    TextView textViewLogin;
    RadioGroup radioGroupType;
    RadioButton radioButtonTeacher, radioButtonStudent;
    String randomNumber, verificationCode,
            nameForSignUp, emailForSignUp, passwordForSignUp;
    int user_type;
    TextInputLayout verifyGroup;


    RelativeLayout relativeLayoutsignup, loginlayout, centersignup;
    LinearLayout linearLayoutmain, linearLayoutcontent;
    ImageButton imageButton;
    TextView textViewEmail, textViewUser, textViewPassword, signuptitle;
    ProgressBar progressBar;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyGroup = findViewById(R.id.verifyGroup);
        verifyGroup.setVisibility(View.INVISIBLE);

        getSupportActionBar().hide();
        /////Image button casting/////

        imageButton = (ImageButton) findViewById(R.id.user_profile_photo);

        /////Relative layout casting/////

        relativeLayoutsignup = (RelativeLayout) findViewById(R.id.relativeLayoutsignup);
        loginlayout = (RelativeLayout) findViewById(R.id.loginLayout);
        centersignup = (RelativeLayout) findViewById(R.id.centersignup);

/////Linear layout casting/////
        linearLayoutmain = (LinearLayout) findViewById(R.id.linearlayoutmain);
        linearLayoutcontent = (LinearLayout) findViewById(R.id.linearlayoutcontent);


/////View casting/////
        view = (View) findViewById(R.id.Viewmain);

        /////Radio group  casting/////
        radioGroupType = (RadioGroup) findViewById(R.id.radioGroupType);
        getSupportActionBar().hide();
        radioButtonStudent = (RadioButton) findViewById(R.id.radio_students);
        radioButtonTeacher = (RadioButton) findViewById(R.id.radio_teacher);

        /////Progress bar casting/////

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        /////text View casting/////
        textViewUser = (TextView) findViewById(R.id.textViewName);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewPassword = (TextView) findViewById(R.id.textViewPassword);
        textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        signuptitle = (TextView) findViewById(R.id.Signup_title);


        buttonVerify = findViewById(R.id.buttonVerifySignUp);
        buttonVerify.setVisibility(View.INVISIBLE);
        editTextVerificationCode = findViewById(R.id.editTextVerificationCode);


        radioGroupType = (RadioGroup) findViewById(R.id.radioGroupType);

        radioButtonStudent = (RadioButton) findViewById(R.id.radio_students);
        radioButtonTeacher = (RadioButton) findViewById(R.id.radio_teacher);

        textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
            }
        });
        editTextName = (EditText) findViewById(R.id.txtName);
        editTextEmail = (EditText) findViewById(R.id.txtEmail);
        editTextPassword = (EditText) findViewById(R.id.txtPassword);


        buttonAdd = (Button) findViewById(R.id.btnAdd);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameForSignUp = editTextName.getText().toString().trim();
                ;
                emailForSignUp = editTextEmail.getText().toString().trim();
                ;
                passwordForSignUp = editTextPassword.getText().toString().trim();
                ;

                verificationCode = verifyUser();
                String verifyCode = editTextVerificationCode.getText().toString();
                if (verifyCode.isEmpty()) {
                    Toast.makeText(MainActivity.this, "enter code", Toast.LENGTH_SHORT).show();
                } else {
                    editTextVerificationCode.setText(null);
                }

            }
        });
        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editTextVerificationCode.getText().toString().trim();

                if (code.contains(randomNumber)) {
                    signUpUser();
                } else {
                    Toast.makeText(MainActivity.this, "Code not correct", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signUpUser() {

        final String name = nameForSignUp;
        final String email = emailForSignUp;
        final String password = passwordForSignUp;

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "fill all text", Toast.LENGTH_SHORT).show();
        } else {


            StringRequest request = new StringRequest(Request.Method.POST, "https://temp321.000webhostapp.com/connect/Connect.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("Account Created")) {
                                Toast.makeText(MainActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, login.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("Name", name);
                    params.put("Email", email);
                    params.put("Password", password);
                    params.put("userType", String.valueOf(user_type));

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(request);
        }
    }

    private String verifyUser() {
        final String email = editTextEmail.getText().toString().trim();


        StringRequest request = new StringRequest(Request.Method.POST, "https://temp321.000webhostapp.com/connect/verifyUser.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Email verified cheema")) {
                            Toast.makeText(MainActivity.this, "Email Verified", Toast.LENGTH_SHORT).show();
                            String[] data = response.split(",");
                            randomNumber = data[1];
                        } else {
                            Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Email", email);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);

        radioGroupType.setVisibility(View.INVISIBLE);
        buttonVerify.setVisibility(View.VISIBLE);
        buttonAdd.setVisibility(View.INVISIBLE);
        verifyGroup.setVisibility(View.VISIBLE);
        textViewLogin.setVisibility(View.INVISIBLE);
        signuptitle.setVisibility(View.INVISIBLE);
        textViewEmail.setVisibility(View.INVISIBLE);
        textViewPassword.setVisibility(View.INVISIBLE);
        textViewUser.setVisibility(View.INVISIBLE);
        textViewLogin.setVisibility(View.INVISIBLE);
        editTextEmail.setVisibility(View.INVISIBLE);
        editTextName.setVisibility(View.INVISIBLE);
        editTextPassword.setVisibility(View.INVISIBLE);

        return randomNumber;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        user_type = -1;

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_teacher:
                if (checked)
                    user_type = 1;
                break;
            case R.id.radio_students:
                if (checked)
                    user_type = 0;
                break;
        }
//        Toast.makeText(MainActivity.this, user_type, Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.menu_logout:
                Toast.makeText(getApplicationContext(), "You are not logged in", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_compare:
                Intent intent2 = new Intent(this, CosineComparing.class);
                startActivity(intent2);
                break;
            case R.id.menu_compare2:
                Intent ini = new Intent(this,jaro_winkler_algorithm.class);
                startActivity(ini);
                break;

        }
        return true;
    }

}