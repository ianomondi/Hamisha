package com.example.hamisha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DriverProfileActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    private String $firstname, $lastname, $location, $email, $password, $phone, $id;

    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);

        preferences = getApplication().getSharedPreferences(Config.PREF_NAME, Config.PRIVATE_MODE);
        getData();
    }

    private void getData()
    {
        $firstname = preferences.getString(Config.FNAMEKEY, null);
        $lastname = preferences.getString(Config.LNAMEKEY, null);
        $location = "Nairobi";
        $email = preferences.getString(Config.EMAILKEY, null);
        $password = preferences.getString(Config.PASSWORDKEY, null);
        $phone = preferences.getString(Config.MOBILEKEY, null);
        $id = preferences.getString(Config.IDKEY, null);

        //saveData();
    }
/*
    private void saveData()
    {
        request = new StringRequest(Request.Method.POST, Config.REGISTRATIONURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DriverProfileActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DriverProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String>  params = new HashMap<String, String>();
                params.put("firstname", $firstname);
                params.put("lastname", $lastname);
                params.put("id", String.valueOf($id));
                params.put("phone", String.valueOf($phone));
                params.put("password", $password);
                params.put("email", $email);
                params.put("location", $location);
                params.put("registration", "Reg");

                return params;
            }
        };

        VolleySingleton.getInstance(DriverProfileActivity.this).addToRequestQueue(request);
    }*/
}
