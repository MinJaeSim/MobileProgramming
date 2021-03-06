package com.example.yellow7918.mobile_sns;

import android.content.Context;
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

import com.bumptech.glide.Glide;

import java.util.List;

public class SearchFragment extends Fragment {

    private List<String> uriList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        uriList = getArguments().getStringArrayList("user_id");

        for (String s : uriList) {
            Log.i("AAAA", "test : " + s);
        }

        GridView gridViewImages = (GridView) v.findViewById(R.id.search_grid_view_container);
        ImageAdapter imageGridAdapter = new ImageAdapter(this.getContext(), uriList);
        gridViewImages.setAdapter(imageGridAdapter);

        return v;
    }


    public class ImageAdapter extends BaseAdapter {
        private Context context;

        private List<String> imageIDs;

        public ImageAdapter(Context context, List imageIDs) {
            this.context = context;
            this.imageIDs = imageIDs;
        }

        public int getCount() {
            return imageIDs.size();
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

                Glide.with(SearchFragment.this)
                        .load(imageIDs.get(position))
                        .into(imageView);
            }
            return imageView;
        }
    }
}
