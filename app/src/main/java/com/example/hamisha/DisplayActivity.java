package com.example.hamisha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rey.material.widget.CheckBox;

import java.util.HashMap;

public class DisplayActivity extends AppCompatActivity
{
    private EditText lastNameTv, firstNametv, idTv, mobileTv, emailTv, passwordTv;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private CheckBox checkBox;

    private Button VerifyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        preferences = getApplication().getSharedPreferences(Config.PREF_NAME, Config.PRIVATE_MODE);

        firstNametv = (EditText) findViewById(R.id.label_fname);
        lastNameTv = (EditText) findViewById(R.id.label_lname);
        idTv = (EditText) findViewById(R.id.label_id);
        mobileTv = (EditText) findViewById(R.id.label_phone);
        emailTv = (EditText) findViewById(R.id.label_email);
        checkBox = (CheckBox) findViewById(R.id.check_box);
        passwordTv = (EditText) findViewById(R.id.label_password);

        VerifyBtn = (com.rey.material.widget.Button) findViewById(R.id.verify_button);

        FetchRegDetails();

       VerifyBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               GrantAccessToCreateAccount();
           }
       });
    }

    private void GrantAccessToCreateAccount()
    {
        if (checkBox.isChecked())
        {
            Intent intent = new Intent(DisplayActivity.this,PhoneVerificationActivity.class);
            setUpdatedDetails();
            startActivity(intent);
        }
        else
        {
            AlertDialog alertDialog = new AlertDialog.Builder(DisplayActivity.this).create();
            alertDialog.setTitle("Warning!");
            alertDialog.setIcon(R.drawable.ic_warning_black_24dp);
            alertDialog.setMessage("Agree with our terms before proceeding");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

    }

    private void setUpdatedDetails()
    {
        editor = preferences.edit();
        editor.putString(Config.FNAMEKEY, firstNametv.getText().toString().trim());
        editor.putString(Config.LNAMEKEY, lastNameTv.getText().toString().trim());
        editor.putInt(Config.IDKEY, Integer.parseInt(idTv.getText().toString().trim()));
        editor.putString(Config.MOBILEKEY, mobileTv.getText().toString().trim());
        editor.putString(Config.EMAILKEY, emailTv.getText().toString().trim());
        editor.putString(Config.PASSWORDKEY, passwordTv.getText().toString().trim());
        editor.commit();
    }

    private void FetchRegDetails()
    {
        HashMap<String, String> userDetails = new HashMap<>();

        userDetails.put(Config.FNAMEKEY , preferences.getString(Config.FNAMEKEY, null));
        userDetails.put(Config.LNAMEKEY , preferences.getString(Config.LNAMEKEY, null));
        userDetails.put(Config.IDKEY , preferences.getString(Config.IDKEY, null));
        userDetails.put(Config.EMAILKEY , preferences.getString(Config.EMAILKEY, null));
        userDetails.put(Config.MOBILEKEY , preferences.getString(Config.MOBILEKEY, null));
        userDetails.put(Config.PASSWORDKEY , preferences.getString(Config.PASSWORDKEY, null));

        DisplayRegDetails(userDetails);


    }

    private void DisplayRegDetails(HashMap<String, String> userDetails)
    {
        String first_name, last_name, national_id, mobile_number, pass_word, email_address;

        first_name = userDetails.get(Config.FNAMEKEY);
        last_name = userDetails.get(Config.LNAMEKEY);
        pass_word = userDetails.get(Config.PASSWORDKEY);
        email_address = userDetails.get(Config.EMAILKEY);
        mobile_number = userDetails.get(Config.MOBILEKEY);
        national_id = userDetails.get(Config.IDKEY);

        String mobile = "+254" + mobile_number.substring(1).trim();

        firstNametv.setText(first_name);
        lastNameTv.setText(last_name);
        passwordTv.setText(pass_word);
        idTv.setText(national_id);
        emailTv.setText(email_address);
        mobileTv.setText(mobile);

    }
}
