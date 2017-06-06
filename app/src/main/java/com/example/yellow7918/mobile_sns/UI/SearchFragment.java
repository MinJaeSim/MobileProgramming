package com.example.yellow7918.mobile_sns.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.yellow7918.mobile_sns.Model.Article;
import com.example.yellow7918.mobile_sns.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        EditText searchEditText = (EditText) v.findViewById(R.id.search_edit) ;
        final String searchText = searchEditText.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                int textNumber = 0;
                for (DataSnapshot e : children) {
                    Article article = e.getValue(Article.class);
                    if (article.getName().equals(searchText)) {
                        textNumber++;
                        //uriList.add(article.getImageURL());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase Error", databaseError.getMessage());
            }
        });

        return v;
    }
}
