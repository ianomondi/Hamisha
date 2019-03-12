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

public class SecondActivity extends AppCompatActivity
{
    private EditText pass, mobile, id;
    private Button btnRegister;

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        pass = (EditText) findViewById(R.id.input_password);
        mobile = (EditText) findViewById(R.id.input_mobile);
        id = (EditText) findViewById(R.id.input_id);

        btnRegister = (Button) findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String password = pass.getText().toString();
                String ID = id.getText().toString();
                String mobileNumber = mobile.getText().toString();

                if (TextUtils.isEmpty(password) || password.length() < 6)
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
                    Intent intent = new Intent(SecondActivity.this, DisplayActivity.class);

                    preferences = getApplicationContext().getSharedPreferences(Config.PREF_NAME, Config.PRIVATE_MODE);
                    editor = preferences.edit();
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
