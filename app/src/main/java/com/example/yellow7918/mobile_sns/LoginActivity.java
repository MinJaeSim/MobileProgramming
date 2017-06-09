package com.example.yellow7918.mobile_sns;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_layout)
    RelativeLayout layout;

    @BindView(R.id.email_edit)
    EditText emailEdit;

    @BindView(R.id.password_edit)
    EditText passwordEdit;

    @BindView(R.id.login_button)
    Button loginButton;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.goto_enroll_button)
    Button gotoEnrollButton;

    private String email;
    private String password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();

                email = emailEdit.getText().toString();
                password = passwordEdit.getText().toString();

                auth = FirebaseAuth.getInstance();

                if (email.length() <= 0) {
                    emailEdit.setError("아이디를 입력해주세요.");
                    hideProgressBar();
                } else if (password.length() <= 0) {
                    passwordEdit.setError("비밀번호를 입력해주세요.");
                    hideProgressBar();
                } else {
                    Task<AuthResult> authResultTask = auth.signInWithEmailAndPassword(email, password);
                    authResultTask.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            hideProgressBar();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    authResultTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            hideProgressBar();
                            Snackbar.make(layout, e.getLocalizedMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        gotoEnrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EnrollActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        loginButton.setEnabled(false);
        gotoEnrollButton.setEnabled(false);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        loginButton.setEnabled(true);
        gotoEnrollButton.setEnabled(true);
    }
}
