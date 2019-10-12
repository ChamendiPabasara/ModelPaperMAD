package com.example.modelpaper;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Home extends AppCompatActivity {

    private EditText usernameText,passwordText;
    private Button LoginButtonn,registerButton;
    DBhelper dBhelper;
    long userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        dBhelper = new DBhelper(this);

        usernameText = findViewById(R.id.usernametxt);
        passwordText = findViewById(R.id.passwordText);
        LoginButtonn = findViewById(R.id.LoginBtn);
        registerButton = findViewById(R.id.registerBtn);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerUser();

            }
        });

        LoginButtonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });
    }

    private void login() {

        String userName = usernameText.getText().toString();
        String password = passwordText.getText().toString();


        userId = dBhelper.checkUser(userName,password);

        if (userId != -1) {
            Intent intent = new Intent(Home.this, ProfileManagement.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Err.. Try again!", Toast.LENGTH_SHORT).show();
        }
    }


    public void registerUser() {
        String userName = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        userId = dBhelper.addInfo(userName,password);
        if (userId == -1) {
            Toast.makeText(this, "Err.. Try again!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Successfully registed!", Toast.LENGTH_SHORT).show();
            login();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dBhelper.close();
    }

}
