package com.example.builder.F100747;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button btnLogin;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username =  findViewById(R.id.usernameLogin);
        password =  findViewById(R.id.passwordLogin);
        btnLogin =  findViewById(R.id.btnsigninLogin);
        DB = new DBHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please provide valid input for all the fields.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkUserPass = DB.checkUsernamePassword(user, pass);
                    if (checkUserPass == true) {
                        Toast.makeText(LoginActivity.this, "Sign in successful.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        LoginOrRegistrationActivity.logRegAct.finish();
                        finish();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Invalid Username or Password. ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}