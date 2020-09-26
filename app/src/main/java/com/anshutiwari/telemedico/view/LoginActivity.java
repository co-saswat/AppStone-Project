package com.anshutiwari.telemedico.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anshutiwari.telemedico.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ui.DashBoardActivity;

public class LoginActivity extends AppCompatActivity {

    TextView mSignup, mForgotPassword;
    EditText mEmailLogin;
    EditText mPasswordLogin;
    private  String email, password;
    Button mlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mlogin = findViewById(R.id.btn_login);
        mSignup = findViewById(R.id.et_for_signUp);
        mEmailLogin = findViewById(R.id.et_mail);
        mPasswordLogin = findViewById(R.id.et_password);
        mForgotPassword = findViewById(R.id.et_forgottenPassword);
    }

    public void SignUp(View view){
        startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
    }



    public void btnLoginClick(View view){
        email = mEmailLogin.getText().toString().trim();
        password = mPasswordLogin.getText().toString().trim();

        if(email.equals(""))
        {
            mEmailLogin.setError("Enter Email");

        }else if(password.equals(""))
        {
            mPasswordLogin.setError("Enter Password");
        }else{
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public
                        void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));

                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this,"Login Failed :" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }

    public void Forgetpassword (View view){

        email = mEmailLogin.getText().toString().trim();
        if(email.equals("")){
            mEmailLogin.setError("Enter Email");
        }
        else{
            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public
                        void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Email Sent to:" + email, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(LoginActivity.this,"Failed to Sent Email:" +
                                        task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    protected
    void onStart() {
        super.onStart();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser =  mAuth.getCurrentUser();

        if(mUser!=null)
        {
        }
    }

   /** public void moveToSignUp(View view){


        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);

        Pair[] pairs = new Pair[1];

        pairs[0] = new Pair<View,String>(findViewById(R.id.btn_move_to_signup),"transition_sign_up");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
        startActivity(intent,options.toBundle());
    }**/
}

