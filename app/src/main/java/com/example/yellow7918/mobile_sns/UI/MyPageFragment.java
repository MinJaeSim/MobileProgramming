package com.example.yellow7918.mobile_sns.UI;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yellow7918.mobile_sns.Model.Article;
import com.example.yellow7918.mobile_sns.Model.UpLoader;
import com.example.yellow7918.mobile_sns.Model.User;
import com.example.yellow7918.mobile_sns.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyPageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mypage, container, false);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        final User user = UpLoader.getArticleUploadUser(currentUser.getDisplayName());

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "AmaticSC-Bold.ttf");

        TextView myID = (TextView) v.findViewById(R.id.my_id);
        myID.setText(currentUser.getDisplayName() + " 의 프로필");
        myID.setTypeface(type);

        TextView myProfile = (TextView) v.findViewById(R.id.my_profile);
        myProfile.setText(user.getProfile());
        myProfile.setTypeface(type);


        final TextView myTextNum = (TextView) v.findViewById(R.id.my_text_number_view);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                int textNumber = 0;
                for (DataSnapshot e : children) {
                    Article article = e.getValue(Article.class);
                    if (article.getName().equals(user.getName())) {
                        textNumber++;
                    }
                }
                myTextNum.setText(textNumber + "개");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }
}
