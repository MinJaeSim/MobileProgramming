package com.example.yellow7918.mobile_sns.UI;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yellow7918.mobile_sns.Model.Article;
import com.example.yellow7918.mobile_sns.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyPageFragment extends Fragment {

    private List<String> uriList = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mypage, container, false);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = auth.getCurrentUser();

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "NanumBarunGothic.otf");

        TextView myID = (TextView) v.findViewById(R.id.my_id);
        myID.setText(currentUser.getDisplayName() + " 의 프로필");
        myID.setTypeface(type);

        TextView myProfile = (TextView) v.findViewById(R.id.my_profile);
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
                    if (article.getName().equals(currentUser.getDisplayName())) {
                        textNumber++;
                        uriList.add(article.getImageURL());
                    }
                }
                myTextNum.setText(textNumber + "개");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase Error", databaseError.getMessage());
            }
        });

        GridView gridViewImages = (GridView) v.findViewById(R.id.gridViewImages);
        ImageAdapter imageGridAdapter = new ImageAdapter(this.getContext(), uriList);
        gridViewImages.setAdapter(imageGridAdapter);


        return v;
    }

    public class ImageAdapter extends BaseAdapter {
        Context context = null;

        List<String> imageIDs = null;

        public ImageAdapter(Context context, List imageIDs) {
            this.context = context;
            this.imageIDs = imageIDs;
        }

        public int getCount() {
            return (null != imageIDs) ? imageIDs.size() : 0;
        }

        public Object getItem(int position) {
            return (null != imageIDs) ? imageIDs.get(position) : 0;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = null;

            if (null != convertView)
                imageView = (ImageView) convertView;
            else {
                imageView = new ImageView(context);
                imageView.setAdjustViewBounds(true);

                Glide.with(MyPageFragment.this)
                        .load(imageIDs.get(position))
                        .into(imageView);
            }
            return imageView;
        }
    }
}
