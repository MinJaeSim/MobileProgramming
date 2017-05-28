package com.example.yellow7918.mobile_sns.Model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ArticleModel {
    private DatabaseReference ref;
    private List<Article> articles = new ArrayList<>();

    private OnDataChangedListener onDataChangedListener;

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.onDataChangedListener = listener;
    }

    public ArticleModel() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        this.ref = database.getReference();
        this.ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Article> newArticles = new ArrayList<Article>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot e : children) {
                    Article article = e.getValue(Article.class);
                    newArticles.add(article);
                    UpLoader.getInstance().add(new User(article.getName()));
                }

//                for(int i =0; i< UpLoader.getInstance().size(); i++)
//                    Log.i("AAA",UpLoader.getInstance().get(UpLoader.getInstance().size()-1).getId());

                articles = newArticles;
                if (onDataChangedListener != null) {
                    onDataChangedListener.onDataChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }

    public void upLoadArticle(String name, String url, String tag) {
        DatabaseReference childRef = ref.push();
        childRef.setValue(Article.newArticle(name, url, tag));
    }

    public String getName(int position) {
        return articles.get(position).getName();
    }

    public String getImageURL(int position) {
        return articles.get(position).getImageURL();
    }

    public int getMessageCount() {
        return articles.size();
    }

    public String getTag(int position) {
        return articles.get(position).getTag();
    }

    public String getTimeStamp(int position) {
        return articles.get(position).getTimeStamp();
    }
}
