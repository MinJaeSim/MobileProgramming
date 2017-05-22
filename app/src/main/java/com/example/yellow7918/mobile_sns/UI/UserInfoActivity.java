package com.example.yellow7918.mobile_sns.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.example.yellow7918.mobile_sns.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;


public class UserInfoActivity extends FragmentActivity {
    private static final String EXTRA_USER_ID = "com.example.yellow7918.mobile_sns.user_information";

    public static Intent newIntent(Context packageContext, String userID) {
        Intent intent = new Intent(packageContext, UserInfoActivity.class);
        intent.putExtra(EXTRA_USER_ID, userID);
        return intent;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            fragment = UserInfoFragment.newInstance(userId);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
