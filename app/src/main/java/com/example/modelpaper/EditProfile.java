package com.example.modelpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class EditProfile extends AppCompatActivity {

    private EditText usernameEt, passwordEt, dobEt;
    private RadioGroup genderRadio;
    private Button editBt, searchBt, deleteBt;
    DBhelper dbhelper;
    long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

          usernameEt =findViewById(R.id.namtTxt);
        passwordEt =findViewById(R.id.passwordTxt);
        dobEt =findViewById(R.id.dobTxt);
        genderRadio =findViewById(R.id.radioGroup);
        editBt =findViewById(R.id.updateBtn);
        searchBt =findViewById(R.id.searchbtn);
        deleteBt =findViewById(R.id.deletebtn);

        dbhelper = new DBhelper(this);

        //TODO: get userId that is coming from the home activity to search using user Id(not sure this way or using search username)
        //If press update or delete without searching user id coming from the home activity will be deleted
        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getLongExtra("userId", -1);
        }

        //if user clicked edit button
        editBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uname = usernameEt.getText().toString();
                String pass = passwordEt.getText().toString();
                String dob = dobEt.getText().toString();
                String gender = "";
                if (genderRadio.getCheckedRadioButtonId() == R.id.radioMale) {
                    gender = "Male";
                } else if (genderRadio.getCheckedRadioButtonId() == R.id.radioFemale) {
                    gender = "Female";
                }

                //edit logged in user
                if (dbhelper.updateInfor(userId, uname, pass, dob, gender)) {
                    Toast.makeText(EditProfile.this, "Updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProfile.this, "Cannot update!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //if user clicked search button
        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get typed username to search
                String username = usernameEt.getText().toString();

                //get current user values to textFields from readInfo
                Cursor cursor = dbhelper.readAllInfor();

                //TODO: I think this way is not the perfect since, we can directly search using the query
                while (cursor.moveToNext()) {
                    //if typed username equals with table value
                    if (username.equals(cursor.getString(1))) {
                        //get the user id to update and delete
                        userId = cursor.getLong(0);
                        //if there is any matching username with db user table get those values and place into textfields
                        passwordEt.setText(cursor.getString(4));
                        dobEt.setText(cursor.getString(2));

                        if (cursor.getString(3) != null) {
                            if (cursor.getString(3).equals("Male")) {
                                genderRadio.check(R.id.radioMale);
                            } else if (cursor.getString(3).equals("Female"))
                                genderRadio.check(R.id.radioFemale);
                        }
                    }
                }
                cursor.close();

                //dumb trick to display if user not exists
                if (passwordEt.getText().toString().equals("")) {
                    //if searched user not exists
                    Toast.makeText(EditProfile.this, "No user found!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //if user clicked delete button
        deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete user from table and if deleted count is greater than 0 display delete message
                if (dbhelper.deleteInfo(userId)) {
                    Toast.makeText(EditProfile.this, "Deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProfile.this, "User not in the table!", Toast.LENGTH_SHORT).show();
                }

                //clear out editText after deleted
                usernameEt.setText("");
                passwordEt.setText("");
                dobEt.setText("");
                genderRadio.clearCheck();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbhelper.close();
    }
}
