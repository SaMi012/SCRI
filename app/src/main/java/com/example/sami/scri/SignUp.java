package com.example.sami.scri;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextCodeName;
    private RadioGroup radioGroupGender;
    private EditText editTextEmail;
    private EditText editTextPostalAddress;
    private EditText editTextPhoneNo;
    private EditText editTextAddress;
    private Button buttonSignUp;
    private RadioButton radioButtonGender;

    private FirebaseAuth firebaseAuthSignUp;
    private ProgressDialog progressDialogSignUp;
    private DatabaseReference databaseReferenceSignUp;
    private DatabaseReference mdatabase;
    private static String cnt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuthSignUp = FirebaseAuth.getInstance();
        progressDialogSignUp = new ProgressDialog(this);

        databaseReferenceSignUp = FirebaseDatabase.getInstance().getReference().child("Authorized Users");
        mdatabase = FirebaseDatabase.getInstance().getReference().child("count");

        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextCodeName = (EditText) findViewById(R.id.editTextCodeName);
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPostalAddress = (EditText) findViewById(R.id.editTextPost);
        editTextPhoneNo = (EditText) findViewById(R.id.editTextPhoneNo);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignUp();
            }
        });


    }

    private void startSignUp() {


        int radioButtonId = radioGroupGender.getCheckedRadioButtonId();
        radioButtonGender = (RadioButton) findViewById(radioButtonId);
        final String firstName = editTextFirstName.getText().toString().trim();
        final String lastName = editTextLastName.getText().toString().trim();
        final String codeName = editTextCodeName.getText().toString().trim();
        final String gender = radioButtonGender.getText().toString().trim();
        final String phoneNo = editTextPhoneNo.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String postAddress = editTextPostalAddress.getText().toString().trim();
        final String address = editTextAddress.getText().toString().trim();

        if( TextUtils.isEmpty(codeName) ){
            Toast.makeText(this, "Password is Required", Toast.LENGTH_SHORT).show();
            return;
        }
        if( TextUtils.isEmpty(email) ){
            Toast.makeText(this, "Email is Required", Toast.LENGTH_SHORT).show();
            return;
        }

        if( !TextUtils.isEmpty(codeName) && !TextUtils.isEmpty(email) ){

            progressDialogSignUp.setMessage("Signing Up...");
            progressDialogSignUp.show();

            HashMap<String, String>  dataMap = new HashMap<String, String>();
            dataMap.put("firstName", firstName);
            dataMap.put("lastName", lastName);
            dataMap.put("password", codeName);
            dataMap.put("gender", gender);
            dataMap.put("email", email);
            dataMap.put("phoneNo", phoneNo);
            dataMap.put("postCode", postAddress);
            dataMap.put("address", address);

            /*
            mdatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    cnt = dataSnapshot.getValue().toString();
                    //Toast.makeText(SignUp.this, cnt, Toast.LENGTH_SHORT).show();
                    int count = Integer.parseInt(cnt);
                    count++;
                    cnt = Integer.toString(count);
                    //Toast.makeText(SignUp.this, cnt, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            */

            //mdatabase.child("count").setValue(cnt);
            //Toast.makeText(SignUp.this, cnt, Toast.LENGTH_SHORT).show();

            databaseReferenceSignUp.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        Toast.makeText(SignUp.this, "successfully signed up...", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(SignUp.this, "error", Toast.LENGTH_SHORT).show();
                    }

                    progressDialogSignUp.dismiss();
                    Intent signIn = new Intent(SignUp.this, SignIn.class);
                    startActivity(signIn);
                }
            });




        }



    }
}
