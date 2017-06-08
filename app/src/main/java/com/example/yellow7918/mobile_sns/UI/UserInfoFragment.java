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
import com.example.yellow7918.mobile_sns.Model.UpLoader;
import com.example.yellow7918.mobile_sns.Model.User;
import com.example.yellow7918.mobile_sns.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserInfoFragment extends Fragment {
    private static final String ARG_USER_ID = "user_id";

    private int textNumber;
    private User mUser;
    private List<String> uriList = new ArrayList<>();

    public static UserInfoFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID, name);

        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userId = null;

        if (getArguments() != null) {
            userId = getArguments().getString("user_id");
        }

        mUser = UpLoader.getArticleUploadUser(userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_user_info, container, false);

        textNumber = 0;

        final Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "NanumBarunGothic.otf");

        TextView mIdTV = (TextView) v.findViewById(R.id.uploader_id);
        mIdTV.setText(mUser.getName());
        mIdTV.setTypeface(type);

        final TextView mNumTextTV = (TextView) v.findViewById(R.id.user_text_number_view);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot e : children) {
                    Article article = e.getValue(Article.class);
                    if (article.getName().equals(mUser.getName())) {
                        textNumber++;
                        uriList.add(article.getImageURL());
                    }
                }
                mNumTextTV.setText(textNumber + "개");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        GridView gridViewImages = (GridView) v.findViewById(R.id.user_gridViewImages);
        UserInfoFragment.ImageAdapter imageGridAdapter = new UserInfoFragment.ImageAdapter(this.getContext(), uriList);
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

                Glide.with(UserInfoFragment.this)
                        .load(imageIDs.get(position))
                        .into(imageView);

            }
            return imageView;
        }
    }
}
