package ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.anshutiwari.telemedico.R;
import com.google.android.material.textfield.TextInputLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    //Global Variables
    static final int IMAGE_PICKER = 100;
    static final int IMAGE_CAPTURE = 101;
    private ImageView mIvEditProfilePic;
    private TextInputLayout mTilEditName;
    private TextInputLayout mTilEditEmail;
    private TextInputLayout mTilEditAge;
    private Spinner mSpinBloodGroup;
    private RelativeLayout mRlModeOfUpload;
    private Dialog profilePicDialog;
    private CircleImageView mCivGenderMale,mCivGenderFemale;
    private ImageView mIvGenderOther;
    private ImageView mIvExitProfile;
    private ImageView mIvSaveProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        //findViewByIds of the fields
        mIvEditProfilePic = findViewById(R.id.iv_edit_profile_picture);
        mTilEditName = findViewById(R.id.til_edit_fullname);
        mTilEditAge = findViewById(R.id.til_edit_age);
        mTilEditEmail = findViewById(R.id.til_edit_email);
        mSpinBloodGroup = findViewById(R.id.spin_edit_bloodGroup);
        mRlModeOfUpload = findViewById(R.id.rl_upload_profile_pic);
        mCivGenderMale = findViewById(R.id.civ_edit_gender_male);
        mCivGenderFemale = findViewById(R.id.civ_edit_gender_female);
        mIvGenderOther = findViewById(R.id.iv_edit_gender_other);
        mIvExitProfile = findViewById(R.id.iv_exit_edit_profile);
        mIvSaveProfile = findViewById(R.id.iv_edit_profile_done);

        //Setting the gender of user
        mCivGenderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCivGenderMale.setCircleBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mCivGenderFemale.setCircleBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                mIvGenderOther.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
            }
        });

        mCivGenderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCivGenderMale.setCircleBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                mCivGenderFemale.setCircleBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mIvGenderOther.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
            }
        });

        mIvGenderOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCivGenderMale.setCircleBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                mCivGenderFemale.setCircleBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                mIvGenderOther.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        //Spinner for Blood-Group
        ArrayAdapter<CharSequence> bloodAdapter = ArrayAdapter.createFromResource(this, R.array.blood_group, android.R.layout.simple_spinner_item);
        bloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinBloodGroup.setAdapter(bloodAdapter);

        //Change profile picture
        mRlModeOfUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
                    } else {
                        setProfilePicture();
                    }
                } else {
                    setProfilePicture();
                }

            }
        });

        //Exit Edit Profile
        mIvExitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this,DashBoardActivity.class));
                finish();
            }
        });

    }

    private void setProfilePicture() {
        profilePicDialog = new Dialog(EditProfileActivity.this);
        profilePicDialog.setContentView(R.layout.dailog_image_picker);
        profilePicDialog.show();

        //findViewByIds of the views in Dialog
        TextView mTvTakePhoto = profilePicDialog.findViewById(R.id.tv_take_photo);
        TextView mTvChoosePhoto = profilePicDialog.findViewById(R.id.tv_choose_photo);

        mTvChoosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK);
                pickIntent.setType("image/*");
                startActivityForResult(pickIntent, IMAGE_PICKER);
                profilePicDialog.cancel();
            }
        });

        mTvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), IMAGE_CAPTURE);
                profilePicDialog.cancel();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                setProfilePicture();
            } else {
                Toast.makeText(EditProfileActivity.this, "User Denied Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //onActivityResult for picking image from the gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICKER) {
            mIvEditProfilePic.setImageURI(data.getData());
        }else if (resultCode == RESULT_OK && requestCode == IMAGE_CAPTURE){
            Bitmap image = (Bitmap) data.getExtras().get("data");
            mIvEditProfilePic.setImageBitmap(image);
        }
    }


}