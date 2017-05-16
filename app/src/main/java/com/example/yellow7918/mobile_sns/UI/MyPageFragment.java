package com.example.yellow7918.mobile_sns.UI;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yellow7918.mobile_sns.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;

public class MyPageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mypage, container, false);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "AmaticSC-Bold.ttf");

        TextView myID = (TextView) v.findViewById(R.id.my_id);
        if (user != null) {
            myID.setText(user.getDisplayName() + " 의 프로필");
            myID.setTypeface(type);
        }

        return v;
    }
}
