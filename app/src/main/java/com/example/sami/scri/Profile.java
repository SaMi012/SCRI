package com.example.sami.scri;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    private TextView fullName;
    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private TextView phoneNo;
    private TextView gender;
    private TextView address;
    private TextView postCode;
    private Button buttonEdit;
    private String full_Name;
    private ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent i = getIntent();
        final UserInformation info = (UserInformation)i.getSerializableExtra("myUser");

        fullName = (TextView) findViewById(R.id.textView_fullName);
        firstName = (TextView) findViewById(R.id.textView_firstName);
        lastName = (TextView) findViewById(R.id.textView_lastName);
        email = (TextView) findViewById(R.id.textView_email);
        phoneNo = (TextView) findViewById(R.id.textView_phone);
        gender = (TextView) findViewById(R.id.textView_gender);
        address = (TextView) findViewById(R.id.textView_address);
        postCode = (TextView) findViewById(R.id.textView_postCode);
        buttonEdit = (Button) findViewById(R.id.buttonEditProfile);
        profilePic = (ImageView) findViewById(R.id.imageView_profile);


        full_Name = info.getFirstName() + " " + info.getLastName();
        fullName.setText(full_Name);
        firstName.setText(info.getFirstName().toString());
        lastName.setText(info.getLastName().toString());
        email.setText(info.getEmail().toString());
        phoneNo.setText(info.getPhoneNo().toString());
        gender.setText(info.getGender().toString());
        address.setText(info.getAddress().toString());
        postCode.setText(info.getPostCode().toString());
        //profilePic.setImageResource();





        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent editProfile = new Intent(Profile.this, EditProfile.class);
                editProfile.putExtra("myUser", info);
                startActivity(editProfile);

            }
        });



    }
}
