package com.example.yellow7918.mobile_sns;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnrollActivity extends AppCompatActivity {

    @BindView(R.id.enroll_text)
    TextView mEnrollText;
    @BindView(R.id.enroll_button)
    Button mEnrollButton;
    @BindView(R.id.enroll_progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.enroll_email_edit)
    EditText mEmailEditText;
    @BindView(R.id.enroll_password_edit)
    EditText mPasswordEditText;
    @BindView(R.id.enroll_nick_name_edit)
    EditText mNickNameEditText;


    private FirebaseAuth auth;
    private FirebaseUser user;
    private String email;
    private String password;
    private String nickName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        ButterKnife.bind(this);


        Typeface type = Typeface.createFromAsset(this.getAssets(), "NanumBarunGothicBold.otf");
        mEnrollText.setTypeface(type);


        mEnrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                email = mEmailEditText.getText().toString();
                password = mPasswordEditText.getText().toString();
                nickName = mNickNameEditText.getText().toString();

                if (email.length() <= 0) mEmailEditText.setError("아이디를 입력해주세요.");
                else if (password.length() <= 0) mPasswordEditText.setError("비밀번호를 입력해주세요.");
                else if (nickName.length() <= 0) mNickNameEditText.setError("닉네임를 입력해주세요.");
                else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(EnrollActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user = FirebaseAuth.getInstance().getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(nickName).build();

                                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);

                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            } else {
                                mProgressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getBaseContext(), "입력 정보를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
