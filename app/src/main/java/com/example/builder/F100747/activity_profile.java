package com.example.builder.F100747;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class activity_profile extends AppCompatActivity {

    Button btnPlay;
    Boolean isPlaying = false;
    TextView profileName, profileAge, profileDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fetchData();

        profileName = findViewById(R.id.profileName);
        profileAge = findViewById(R.id.profileAge);
        profileDescription = findViewById(R.id.profileDescription);

        btnPlay = findViewById(R.id.profileMusicBtn);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    stopService(new Intent(activity_profile.this, BackgroundMusicService.class));
                    isPlaying = false;
                }
                else {
                    startService(new Intent(activity_profile.this, BackgroundMusicService.class));
                    isPlaying = true;
                }
            }
        });
    }

    public void fetchData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://raw.githubusercontent.com/nikiTomov01/builderJSON/main/jsonFile.json";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                profileDescription.setText(error.toString());
            }
        });

        queue.add(stringRequest);
    }

    public void parseJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jsonProfile = jsonObject.getJSONObject("Profile");

            String varName, varAge, varDescription;

            varName = jsonProfile.getString("name");
            varAge = jsonProfile.getString("age");
            varDescription = jsonProfile.getString("description");
            profileName.setText(varName + ",");
            profileAge.setText(varAge);
            profileDescription.setText(varDescription);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isPlaying) {
            stopService(new Intent(activity_profile.this, BackgroundMusicService.class));
            isPlaying = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isPlaying) {
            stopService(new Intent(activity_profile.this, BackgroundMusicService.class));
            isPlaying = false;
        }
    }
}