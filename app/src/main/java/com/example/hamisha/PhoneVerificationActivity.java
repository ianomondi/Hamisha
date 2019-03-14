package com.example.hamisha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bigbangbutton.editcodeview.EditCodeListener;
import com.bigbangbutton.editcodeview.EditCodeView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.Button;
import com.rey.material.widget.CheckBox;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PhoneVerificationActivity extends AppCompatActivity
{
    private EditText lastNameTv, firstNametv, idTv, mobileTv, emailTv, passwordTv;

    private DatabaseReference userRef;

    SharedPreferences.Editor editor;
    SharedPreferences pref;

    //These are the objects needed
    //It is the verification id that will be sent to the user
    private String mVerificationId;

    private ProgressBar progressBar;
    private Button resendBtn;

    //firebase auth object
    private FirebaseAuth mAuth;

    String $phone, mobile;

    private EditCodeView codeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);


        firstNametv = (EditText) findViewById(R.id.label_fname);
        lastNameTv = (EditText) findViewById(R.id.label_lname);
        idTv = (EditText) findViewById(R.id.label_id);
        mobileTv = (EditText) findViewById(R.id.label_phone);
        emailTv = (EditText) findViewById(R.id.label_email);
        passwordTv = (EditText) findViewById(R.id.label_password);

        pref = getApplicationContext().getSharedPreferences(Config.PREF_NAME, Config.PRIVATE_MODE);

        mAuth = FirebaseAuth.getInstance();

        $phone = pref.getString(Config.MOBILEKEY, null);

        mobile = $phone.trim();

        sendVerificationCode(mobile);

        codeView = (EditCodeView) findViewById(R.id.codeView);
        codeView.setEditCodeListener(new EditCodeListener() {
            @Override
            public void onCodeReady(String code) {
                if (mVerificationId != null)
                {
                    progressBar.setVisibility(View.VISIBLE);

                    //verifying the code
                    verifyVerificationCode(code);
                }else {
                    Toast.makeText(PhoneVerificationActivity.this, "Invalid Code", Toast.LENGTH_LONG).show();
                    codeView.setCode("");
                }
            }
        });

        progressBar = findViewById(R.id.progressBarVerification);

        resendBtn = findViewById(R.id.resend_button);

        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                sendVerificationCode(mobile);
            }
        });


    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                progressBar.setVisibility(View.GONE);
                codeView.setCode(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(PhoneVerificationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }

    };

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(PhoneVerificationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity

                            FetchAccountDetails();


                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Something is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();
                        }
                    }
                });
    }

    private void FetchAccountDetails()
    {
        HashMap<String, String> accountDetails = new HashMap<>();

        accountDetails.put(Config.FNAMEKEY, pref.getString(Config.FNAMEKEY, null));
        accountDetails.put(Config.LNAMEKEY, pref.getString(Config.LNAMEKEY, null));
        accountDetails.put(Config.IDKEY, pref.getString(Config.IDKEY, null));
        accountDetails.put(Config.PASSWORDKEY, pref.getString(Config.PASSWORDKEY, null));
        accountDetails.put(Config.EMAILKEY, pref.getString(Config.EMAILKEY, null));
        accountDetails.put(Config.MOBILEKEY, pref.getString(Config.MOBILEKEY, null));

        getRegDetails(accountDetails);

    }

    private void getRegDetails(HashMap<String, String> accountDetails)
    {
        String firstname, lastname, nationalid, mobilenumber, password, emailaddress;

        firstname = accountDetails.get(Config.FNAMEKEY);
        lastname = accountDetails.get(Config.LNAMEKEY);
        password = accountDetails.get(Config.PASSWORDKEY);
        emailaddress = accountDetails.get(Config.EMAILKEY);
        mobilenumber = accountDetails.get(Config.MOBILEKEY);
        nationalid = accountDetails.get(Config.IDKEY);

        CreateAccount(firstname, lastname, nationalid,password,emailaddress, mobilenumber);
    }

    private void CreateAccount(final String firstname, final String lastname, final String id, final String password, final String email, final String mobile)
    {
        final DatabaseReference userRef;
        userRef = FirebaseDatabase.getInstance().getReference();

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(mobile).exists()))
                {
                    HashMap<String, Object> UserDataMap = new HashMap<>();
                    UserDataMap.put("firstname", firstname);
                    UserDataMap.put("lastname", lastname);
                    UserDataMap.put("id", id);
                    UserDataMap.put("password", password);
                    UserDataMap.put("email", email);
                    UserDataMap.put("mobile", mobile);

                    userRef.child("Users").child(mobile).updateChildren(UserDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(getApplicationContext(), "Registered Successfully, proceed to login", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(PhoneVerificationActivity.this, LoginRegisterActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "This Phone number" + mobile + "has already been registered", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(PhoneVerificationActivity.this, SplashActivity.class);
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
