package com.example.hamisha;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.hamisha.Prevelent.Prevelent;
import com.example.hamisha.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity
{
    private ProgressDialog loadingProgress;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        preferences = getApplication().getSharedPreferences(Config.PREF_NAME, Config.PRIVATE_MODE);

        Paper.init(this);
        loadingProgress = new ProgressDialog(this);



        final Thread thread = new Thread()
        {
            @Override
            public void run() {
                try
                {
                    sleep(5000);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                   /* Intent welcomeIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(welcomeIntent);*/

                }
            }
        };
        thread.start();

        setupsession();

    }

    private void setupsession() {
        String userPhoneKey = Paper.book().read(Prevelent.userPhoneKey);
        String userPasswordKey = Paper.book().read(Prevelent.userPasswordKey);

        if (userPhoneKey != "" && userPasswordKey != "")
        {
            if (!TextUtils.isEmpty(userPhoneKey) && !TextUtils.isEmpty(userPasswordKey))
            {
                GrantAccess(userPhoneKey, userPasswordKey);

                loadingProgress.setMessage("Please wait as we are setting you up");
                loadingProgress.setCanceledOnTouchOutside(false);
                loadingProgress.show();

            }
        }else {
            Intent welcomeIntent = new Intent(getApplicationContext(), LoginRegActivity.class);
            startActivity(welcomeIntent);
            finish();
        }
    }

    private void GrantAccess(final String mobile, final String password)
    {
        
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(mobile).exists())
                {
                    Users userData = dataSnapshot.child("Users").child(mobile).getValue(Users.class);
                    if (userData.getMobile().equals(mobile))
                    {
                        if (userData.getPassword().equals(password))
                        {
                            editor = preferences.edit();
                            editor.putString(Config.DRIVERPHONEKEY,mobile);
                            editor.commit();

                            Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();
                            loadingProgress.dismiss();

                            Intent intent = new Intent(getApplicationContext(), DriverChoiseActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            loadingProgress.dismiss();
                            Toast.makeText(getApplicationContext(), "Password is incorrect.", Toast.LENGTH_SHORT).show();
                            Intent welcomeIntent = new Intent(getApplicationContext(), LoginRegActivity.class);
                            startActivity(welcomeIntent);
                            finish();
                        }
                    }

                }
                else
                {
                   // Toast.makeText(getApplicationContext(), "Account with this" + mobile + "does not exist", Toast.LENGTH_SHORT).show();
                    loadingProgress.dismiss();
                    Intent welcomeIntent = new Intent(getApplicationContext(), LoginRegActivity.class);
                    startActivity(welcomeIntent);
                    finish();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    @Override
    protected void onPause() {
        super.onPause();
    }
}
