package com.example.yellow7918.mobile_sns;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends FragmentActivity {
    private static final String ARG_USER_ID = "user_id";
    private List<String> urlList = new ArrayList<String>();
    private Bundle args = new Bundle();
    private Fragment fragment;
    private FragmentManager fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final EditText searchEditText = (EditText) findViewById(R.id.search_edit);
        Button searchButton = (Button) findViewById(R.id.search_button);


        fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.search_view_container);

        args.putSerializable(ARG_USER_ID, (Serializable) urlList);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String searchText = searchEditText.getText().toString();
                if (searchText.length() > 0) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference();

                    ref.orderByChild("name").equalTo(searchText).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                            urlList.clear();
                            args.clear();
                            for (DataSnapshot e : children) {
                                Article article = e.getValue(Article.class);
                                urlList.add(article.getImageURL());
                            }
                            args.putSerializable(ARG_USER_ID, (Serializable) urlList);

                            fragment = new SearchFragment();
                            fragment.setArguments(args);
                            fm.beginTransaction().add(R.id.search_view_container, fragment).commit();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("Firebase Error", databaseError.getMessage());
                        }
                    });
                }
            }
        });

        if (fragment == null) {
            fragment = new SearchFragment();
            fragment.setArguments(args);
            fm.beginTransaction().add(R.id.search_view_container, fragment).commit();
        }
    }
}
