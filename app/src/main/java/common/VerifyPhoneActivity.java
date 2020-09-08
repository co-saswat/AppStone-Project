package common;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.anshutiwari.telemedico.R;

public class VerifyPhoneActivity extends AppCompatActivity {

    private TextView mTvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        mTvPhone = findViewById(R.id.tv_verify_phone);

        Bundle phone = getIntent().getExtras();
        Long phoneNumber = phone.getLong("phone");
        mTvPhone.setText(String.valueOf(phoneNumber));
    }

    public void moveToSignUp(View view){
        startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
    }

}