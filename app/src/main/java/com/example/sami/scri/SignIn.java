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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;

    private FirebaseAuth firebaseAuthSignIn;
    private ProgressDialog progressDialogSignIn;
    private DatabaseReference databaseReferenceSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        firebaseAuthSignIn = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuthSignIn.getCurrentUser();

        progressDialogSignIn = new ProgressDialog(this);
        databaseReferenceSignIn = FirebaseDatabase.getInstance().getReference().child("Authorized Users");
        Button btnSignIn = findViewById(R.id.buttonSignIn);
        Button btnSignUp = findViewById(R.id.buttonSignUp);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(SignIn.this, SignUp.class);
                startActivity(signUp);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(SignIn.this, "hello "+userEmail , Toast.LENGTH_SHORT).show();
                startSignIn();
            }
        });

    }

    private void startSignIn() {
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();


        if( TextUtils.isEmpty(email) ){
            Toast.makeText(SignIn.this, "Enter Email...", Toast.LENGTH_SHORT).show();
            return;
        }
        if( TextUtils.isEmpty(password) ){
            Toast.makeText(SignIn.this, "Enter your Password...", Toast.LENGTH_SHORT).show();
            return;
        }

        if( !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) ){

            progressDialogSignIn.setMessage("Signing In...");
            progressDialogSignIn.show();

            databaseReferenceSignIn.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    int flag = 0;

                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        //String str = ds.child("email").getValue().toString();

                        //Toast.makeText(SignIn.this, "hello", Toast.LENGTH_SHORT);

                        UserInformation info = ds.getValue(UserInformation.class);

                        //Toast.makeText(SignIn.this, info.getEmail(), Toast.LENGTH_SHORT).show();

                        if( info.getEmail().equals(email) && info.getPassword().equals(password) ){
                            flag = 1;
                            Toast.makeText(SignIn.this, "signed in", Toast.LENGTH_SHORT).show();
                            progressDialogSignIn.dismiss();
                            Intent profile = new Intent(SignIn.this, Profile.class);
                            profile.putExtra("myUser", info);
                            startActivity(profile);
                            break;

                        }

                    }
                    if(flag == 0){
                        progressDialogSignIn.dismiss();
                        Toast.makeText(SignIn.this, "User Not Found", Toast.LENGTH_SHORT).show();
                    }
                    
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

}
