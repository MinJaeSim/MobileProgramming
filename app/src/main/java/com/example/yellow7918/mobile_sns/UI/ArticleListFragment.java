package com.example.yellow7918.mobile_sns.UI;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yellow7918.mobile_sns.Model.ArticleModel;
import com.example.yellow7918.mobile_sns.Model.OnDataChangedListener;
import com.example.yellow7918.mobile_sns.Model.UpLoader;
import com.example.yellow7918.mobile_sns.R;
import com.example.yellow7918.mobile_sns.Model.User;

import java.util.List;

public class ArticleListFragment extends Fragment {
    private ArticleModel model = new ArticleModel();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        final List<User> users = UpLoader.getInstance();
        final RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.article_recycle_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(new RecyclerView.Adapter<ArticleHolder>() {
            @Override
            public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                View view = layoutInflater.inflate(R.layout.list_item_article, parent, false);
                return new ArticleHolder(view);
            }

            @Override
            public void onBindViewHolder(ArticleHolder holder, int position) {
                String name = model.getName(position);
                String imageURL = model.getImageURL(position);
                String tag = model.getTag(position);
                String timeStamp = model.getTimeStamp(position);

                holder.setName(name);
                holder.setImage(imageURL);
                holder.setTagAndTimeStamp(timeStamp, tag);
            }

            @Override
            public int getItemCount() {
                return model.getMessageCount();
            }
        });

        model.setOnDataChangedListener(new OnDataChangedListener() {
            @Override
            public void onDataChanged() {
                mRecyclerView.getAdapter().notifyDataSetChanged();
                int count = mRecyclerView.getAdapter().getItemCount();
                mRecyclerView.scrollToPosition(count - 1);
            }
        });
        return view;
    }


    private class ArticleHolder extends RecyclerView.ViewHolder {

        private TextView mNameTextView;
        private TextView mTagTextView;
        private ImageView mImageView;

        public ArticleHolder(View itemView) {
            super(itemView);

            mNameTextView = (TextView) itemView.findViewById(R.id.list_item_uploader_id);
            Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "NanumBarunGothic.otf");
            mNameTextView.setTypeface(type);

            mTagTextView = (TextView) itemView.findViewById(R.id.list_item_uploader_comment);
            mImageView = (ImageView) itemView.findViewById(R.id.list_item_uploader_image);
        }

        public void setName(final String text) {
            mNameTextView.setText(text);
            mNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = UserInfoActivity.newIntent(getActivity(), text);
                    startActivity(intent);
                }
            });
        }

        public void setImage(String imageUrl) {
            Glide.with(ArticleListFragment.this)
                    .load(imageUrl)
                    .into(mImageView);
        }

        public void setTagAndTimeStamp(String timeStamp, String tag) {
            mTagTextView.setText(timeStamp + " \n\n" + tag);
        }
    }
}