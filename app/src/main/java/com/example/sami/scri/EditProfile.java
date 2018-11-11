package com.example.sami.scri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EditProfile extends AppCompatActivity {

    private TextView demo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        UserInformation info = (UserInformation) getIntent().getSerializableExtra("myUser");





    }
}
