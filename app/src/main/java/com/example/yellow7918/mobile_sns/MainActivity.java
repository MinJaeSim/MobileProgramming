package com.example.yellow7918.mobile_sns;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbarTitle)
    TextView mTitle;

    private FragmentManager fm;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            Toast.makeText(getBaseContext(), name + " 님 로그인이 성공적으로 되었습니다.", Toast.LENGTH_SHORT).show();
        }
        fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.main_container);

        if (fragment == null) {
            fragment = new ArticleListFragment();
            fm.beginTransaction().add(R.id.main_container, fragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mToolbar.setTitle("");
        mTitle = (TextView) mToolbar.findViewById(R.id.toolbarTitle);

        Typeface type = Typeface.createFromAsset(this.getAssets(), "NanumBarunGothicBold.otf");
        mTitle.setTypeface(type);

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_home:
                Fragment homeFragment = new ArticleListFragment();
                fm.beginTransaction().replace(R.id.main_container, homeFragment).commit();
                return true;

            case R.id.menu_item_add_photo:
                Intent addIntent = new Intent(getBaseContext(), ArticleAddActivity.class);
                startActivity(addIntent);
                return true;

            case R.id.menu_item_my_page:
                Intent menuIntent = new Intent(getBaseContext(), MyPageActivity.class);
                startActivity(menuIntent);
                return true;

            case R.id.menu_item_search:
                Intent searchIntent = new Intent(getBaseContext(), SearchActivity.class);
                startActivity(searchIntent);
                return true;

            case R.id.menu_item_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent loginIntent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(loginIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setMessage("앱을 종료하시겠습니까?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        dialog.show();
    }
}
