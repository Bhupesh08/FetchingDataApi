package com.example.fetchingdataapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginPage extends AppCompatActivity {

    Button btn;

    EditText name, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        btn = findViewById(R.id.btn);
        name = findViewById(R.id.etv_name);
        password = findViewById(R.id.etv_pw);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String enteredText = name.getText().toString();
                String enteredPw = password.getText().toString();

                if(enteredText.equals("admin") && enteredPw.equals("admin")) {

                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    runIntent();
                }

            }
        });
    }


    private void runIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}