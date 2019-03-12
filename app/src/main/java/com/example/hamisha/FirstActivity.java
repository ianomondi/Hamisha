package com.example.hamisha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity
{
    private EditText fname, lname, email;
    private Button next1;
    private SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        fname = (EditText) findViewById(R.id.input_firstname);
        lname = (EditText) findViewById(R.id.input_lastname);
        email = (EditText) findViewById(R.id.input_email);

        next1 = (Button) findViewById(R.id.btn_next1);

        next1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String firstname = fname.getText().toString();
                String lastname = lname.getText().toString();
                String emailAddress = email.getText().toString();

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
                }
                else
                {
                    Intent intent = new Intent(FirstActivity.this, SecondActivity.class);

                    preferences = getApplicationContext().getSharedPreferences(Config.PREF_NAME, Config.PRIVATE_MODE);
                    editor = preferences.edit();
                    editor.putString(Config.FNAMEKEY, firstname);
                    editor.putString(Config.LNAMEKEY, lastname);
                    editor.putString(Config.EMAILKEY, emailAddress);
                    editor.commit();

                    startActivity(intent);
                }
            }
        });
    }
}
