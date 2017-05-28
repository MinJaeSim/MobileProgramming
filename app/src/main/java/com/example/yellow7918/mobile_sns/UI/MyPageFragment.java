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

import com.example.yellow7918.mobile_sns.Model.UpLoader;
import com.example.yellow7918.mobile_sns.Model.User;
import com.example.yellow7918.mobile_sns.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyPageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mypage, container, false);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        User user = UpLoader.getArticleUploadUser(currentUser.getDisplayName());
        Log.i("AAA",user.getName());

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "AmaticSC-Bold.ttf");

        TextView myID = (TextView) v.findViewById(R.id.my_id);
        myID.setText(currentUser.getDisplayName() + " 의 프로필");
        myID.setTypeface(type);


        TextView myProfile = (TextView) v.findViewById(R.id.user_profile);
        //myProfile.setText(user.getProfile());
        //myProfile.setTypeface(type);

        TextView myTextNum = (TextView) v.findViewById(R.id.my_text_number_view);
        //myTextNum.setText(user.getTextNumber());

        return v;
    }
}
