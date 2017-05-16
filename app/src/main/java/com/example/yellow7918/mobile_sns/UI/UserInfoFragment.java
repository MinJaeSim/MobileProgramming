package com.example.yellow7918.mobile_sns.UI;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yellow7918.mobile_sns.Model.UpLoader;
import com.example.yellow7918.mobile_sns.Model.User;
import com.example.yellow7918.mobile_sns.R;

import java.util.UUID;

public class UserInfoFragment extends Fragment {
    private static final String ARG_USER_ID = "user_id";

    private User mUser;
    private TextView mIdTV;
    private TextView mProfileTV;
    private TextView mFriendNumTV;
    private TextView mTextNumTV;

    public static UserInfoFragment newInstance(UUID uuid) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID, uuid);

        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID userId = (UUID) getArguments().getSerializable(ARG_USER_ID);
        mUser = UpLoader.getArticleUploadUser(userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_info, container, false);

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "AmaticSC-Bold.ttf");

        mIdTV = (TextView) v.findViewById(R.id.my_id);
        mIdTV.setText(mUser.getName());
        mIdTV.setTypeface(type);

        mProfileTV = (TextView) v.findViewById(R.id.profile);
        mProfileTV.setText(mUser.getProfile());

        mFriendNumTV = (TextView) v.findViewById(R.id.friend_num);
        mFriendNumTV.setText(mUser.getFriendNumber());

        mTextNumTV = (TextView) v.findViewById(R.id.text_num);
        mTextNumTV.setText(mUser.getTextNumber());

        return v;
    }
}
