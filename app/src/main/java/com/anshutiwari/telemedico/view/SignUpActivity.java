package com.anshutiwari.telemedico.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.anshutiwari.telemedico.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout mTilFullName;
    private TextInputLayout mTilPhone;
    private TextInputLayout mTilEmail;
    private TextInputLayout mTilPassword;
    private TextInputLayout mTilConfirmPassword;
    private RadioGroup mRgGender;
    private RadioButton mSelectedGender;
    private DatePicker mDpAge;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //FindViewByIds of all the views
        mTilFullName = findViewById(R.id.til_signup_fullname);
        mTilPhone = findViewById(R.id.til_signup_phone);
        mTilEmail = findViewById(R.id.til_signup_email);
        mTilPassword = findViewById(R.id.til_signup_password);
        mTilConfirmPassword = findViewById(R.id.til_signup_confirm_password);
        mRgGender = findViewById(R.id.radio_group_gender);
        mDpAge = findViewById(R.id.dp_age);
    }

    public
    void submitSignUp(View view) {



     if (!validateFullName() | !validateEmail() | !validatePhoneNumber() | !validatePassword() | !validateGender() |!validateAge()){
            return;
        }
        mSelectedGender = findViewById(mRgGender.getCheckedRadioButtonId());

        //Getting the value of from user and store as String

        String userGender = mSelectedGender.getText().toString();
        String userFullname = mTilFullName.getEditText().getText().toString();
        /**String userPhoneString = mTilPhone.getEditText().getText().toString();**/
        String userEmail = mTilEmail.getEditText().getText().toString();
        String userPassword = mTilPassword.getEditText().getText().toString();
        String userConfirmPassword = mTilConfirmPassword.getEditText().getText().toString();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public
                    void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            Toast.makeText(SignUpActivity.this,"Registered Successfully ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(SignUpActivity.this,"Registration Failed :" + task.getException()
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }

                });

     /**   long userPhone = 45692 ;

        if(!userPhoneString.isEmpty()){
             userPhone = Long.parseLong(userPhoneString);
        }**/

        Intent signUpIntent = new Intent(getApplicationContext(), VerifyPhoneActivity.class);

        //Pass given data using Intent
        signUpIntent.putExtra("gender",userGender);
      /**  signUpIntent.putExtra("PHONE",userPhone);**/


        startActivity(signUpIntent);
    }
    // Validation of fields
    private boolean validateFullName() {
        String val = mTilFullName.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            mTilFullName.setError("Field can not be empty");
            return false;
        } else {
            mTilFullName.setError(null);
            mTilFullName.setErrorEnabled(false);
            return true;
        }
    }

   private boolean validatePhoneNumber() {
       String val = mTilPhone.getEditText().getText().toString().trim();
//        String checkspaces = "Aw{1,15}z";
        if (val.isEmpty()) {
           // mTilPhone.setError("Enter valid phone number");
            return false;
        }
//        else if (!val.matches(checkspaces)) {
//            mTilPhone.setError("No White spaces are allowed!");
//            return false;
//        }
        else {
            mTilPhone.setError(null);
            mTilPhone.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = mTilEmail.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            mTilEmail.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            mTilEmail.setError("Invalid Email!");
            return false;
        } else {
            mTilEmail.setError(null);
            mTilEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = mTilPassword.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                "(?=.*[0-9])" +           //at least 1 digit
                "(?=.*[a-z])" +           //at least 1 lower case letter
                "(?=.*[A-Z])" +           //at least 1 upper case letter
//                "(?=.*[a-zA-Z])" +        //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
//                "(?=S+$)" +               //no white spaces
                ".{4,}" +                 //at least 4 characters
                "$";

        if (val.isEmpty()) {
            mTilPassword.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            mTilPassword.setError("Password should contain 4 characters!");
            return false;
        } else {
            mTilPassword.setError(null);
            mTilPassword.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGender() {
        if (mRgGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = mDpAge.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 18) {
            Toast.makeText(this, "You are not eligible to apply", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

}