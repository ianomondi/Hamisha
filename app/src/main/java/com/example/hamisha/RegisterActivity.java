package com.example.hamisha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity
{
    private EditText fname, lname, email,pass, mobile, id;
    private Button register;
    private SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fname = (EditText) findViewById(R.id.input_firstname);
        lname = (EditText) findViewById(R.id.input_lastname);
        email = (EditText) findViewById(R.id.input_email);
        pass = (EditText) findViewById(R.id.input_password);
        mobile = (EditText) findViewById(R.id.input_mobile);
        id = (EditText) findViewById(R.id.input_id);

        register = (Button) findViewById(R.id.btn_register);

        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String firstname = fname.getText().toString();
                String lastname = lname.getText().toString();
                String emailAddress = email.getText().toString();
                String password = pass.getText().toString();
                String ID = id.getText().toString();
                String mobileNumber = mobile.getText().toString();

                if (TextUtils.isEmpty(firstname))
                {
                    Toast.makeText(getApplicationContext(), "First name is mandatory", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(lastname))
                {
                    Toast.makeText(getApplicationContext(), "Last name is mandatory", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(emailAddress) || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches())
                {
                    Toast.makeText(getApplicationContext(), "Invalid Email Address ", Toast.LENGTH_SHORT).show();
                }else  if (TextUtils.isEmpty(password) || password.length() < 6)
                {
                    Toast.makeText(getApplicationContext(), "Password is mandatory", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(ID))
                {
                    Toast.makeText(getApplicationContext(), "National ID is mandatory", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(mobileNumber))
                {
                    Toast.makeText(getApplicationContext(), "Mobile number  is mandatory", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(RegisterActivity.this, DisplayActivity.class);

                    preferences = getApplicationContext().getSharedPreferences(Config.PREF_NAME, Config.PRIVATE_MODE);
                    editor = preferences.edit();
                    editor.putString(Config.FNAMEKEY, firstname);
                    editor.putString(Config.LNAMEKEY, lastname);
                    editor.putString(Config.EMAILKEY, emailAddress);
                    editor.putString(Config.MOBILEKEY, mobileNumber);
                    editor.putString(Config.IDKEY, ID);
                    editor.putString(Config.PASSWORDKEY, password);
                    editor.commit();

                    startActivity(intent);
                }
            }
        });
    }
}
