package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.ModelClass.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    EditText nameET,emailET,passET;
    Button signupBtn;
    TextView loginLink;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        passET = findViewById(R.id.passET);
        signupBtn = findViewById(R.id.signupBtn);
        loginLink = findViewById(R.id.loginLink);

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, Login.class));
                finish();
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String email = emailET.getText().toString();
                String pass = passET.getText().toString();

                if (name.isEmpty()){
                    Toast.makeText(SignUp.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (email.isEmpty()){
                    Toast.makeText(SignUp.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (pass.isEmpty()){
                    Toast.makeText(SignUp.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else{
                    mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                fUser = mAuth.getCurrentUser();
                                id = fUser.getUid();
                                //add UserModel to DB
                                UserModel user = new UserModel(name,email,pass);
                                databaseReference.child(id).setValue(user);
                                mAuth.signOut();

                                Toast.makeText(SignUp.this,"Sign Up Successful!",Toast.LENGTH_LONG).show();

                                startActivity(new Intent(getApplicationContext(),Login.class));
                            }
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignUp.this, Login.class));
        finish();
    }
}