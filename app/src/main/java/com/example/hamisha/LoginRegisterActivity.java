package com.example.hamisha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hamisha.Prevelent.Prevelent;
import com.example.hamisha.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.drawable.CheckBoxDrawable;
import com.rey.material.widget.CheckBox;

public class LoginRegisterActivity extends AppCompatActivity
{
    private EditText mobileEditText, passwordEditText;
    private CheckBox checkBox;
    private Button loginBtn;
    private TextView signUpLink;
    private ProgressDialog loadingProgress;

    private String parentDbName = "Users";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        mobileEditText = (EditText) findViewById(R.id.login_input_mobile);
        passwordEditText = (EditText) findViewById(R.id.login_input_password);

        checkBox = (CheckBox) findViewById(R.id.remember_me_chkb);
        loginBtn = (Button) findViewById(R.id.btn_login);
        signUpLink = (TextView) findViewById(R.id.link_signup);
        
        loadingProgress = new ProgressDialog(this);

        Paper.init(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) 
            {
                LoginUser();    
            }
        });

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
                startActivity(intent);
            }
        });


    }

    private void LoginUser()
    {
        String mobile = mobileEditText.getText().toString();
        //String mobilephone = "+254" + mobile.substring(1).trim();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(mobile)){
            Toast.makeText(getApplicationContext(), "Please fill the phone field", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Please fill the password field", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingProgress.setTitle("Logging Account");
            loadingProgress.setMessage("Please wait as we are setting you up");
            loadingProgress.setCanceledOnTouchOutside(false);
            loadingProgress.show();


            GrantAccess(mobile, password);
        }
    }

    private void GrantAccess(String mobile, String password)
    {
        if (checkBox.isChecked())
        {
            Paper.book().write(Prevelent.userPasswordKey, password);
            Paper.book().write(Prevelent.userPhoneKey, mobile);
        }

            final DatabaseReference RootRef;
            RootRef = FirebaseDatabase.getInstance().getReference();

            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   if (dataSnapshot.child(parentDbName).child(mobile).exists())
                   {
                        Users userData = dataSnapshot.child(parentDbName).child(mobile).getValue(Users.class);
                        if (userData.getMobile().equals(mobile))
                        {
                            if (userData.getPassword().equals(password))
                            {
                                Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();
                                loadingProgress.dismiss();

                                Intent intent = new Intent(getApplicationContext(), DriverProfileActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                loadingProgress.dismiss();
                                Toast.makeText(getApplicationContext(), "Password is incorrect.", Toast.LENGTH_SHORT).show();
                            }
                        }
                   }
                   else
                   {
                       Toast.makeText(getApplicationContext(), "Account with this" + mobile + "does not exist", Toast.LENGTH_SHORT).show();
                       loadingProgress.dismiss();
                   }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }
}
