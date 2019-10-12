package com.example.modelpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ProfileManagement extends AppCompatActivity  {

    private RadioGroup radioGroup;
    private RadioButton radioMale,radioFemale,radioBtn;
    private EditText userText,dobText,passwordText;
    private Button updateBtn;
    private SQLiteDatabase db;
    long userId;
    DBhelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);
       dBhelper = new DBhelper(this);

        radioGroup = findViewById(R.id.radioGroup);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        userText = findViewById(R.id.userText);
        dobText = findViewById(R.id.dobText);
        passwordText = findViewById(R.id.passwordText);
        updateBtn = findViewById(R.id.updateBtn);

        //get userId that is coming from the home activity
        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getLongExtra("userId", -1);
        }
        //get logged in or registered user data from table and bind to editTexts
        Cursor cursor = dBhelper.readAllInfor(userId);
        if (cursor.moveToFirst()) {
            userText.setText(cursor.getString(1));
            passwordText.setText(cursor.getString(2));
            dobText.setText(cursor.getString(3));
            if (cursor.getString(4) != null) {
                if (cursor.getString(4).equals("Male")) {
                    radioGroup.check(R.id.radioMale);
                } else {
                    radioGroup.check(R.id.radioFemale);
                }
            }
        }
        //if user clicked edit button
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open update profile
                Intent intent = new Intent(ProfileManagement.this, EditProfile.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dBhelper.close();
    }
}





       /* radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);

            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = userText.getText().toString();
                String password = passwordText.getText().toString();
                String dob = dobText.getText().toString();


                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(ProfileManagement.this, "No answer has been selected", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton radioButton = (RadioButton) radioGroup.findViewById(selectedId);
                    dBhelper.addInfo(username,password);
                    Toast.makeText(ProfileManagement.this,"Record Added..",Toast.LENGTH_SHORT).show();
                    // Now display the value of selected item
                    // by the Toast message
                    Toast.makeText(ProfileManagement.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(ProfileManagement.this,EditProfile.class);
                startActivity(intent);
                finish();

            }
        });*/









