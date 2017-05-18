package com.example.yellow7918.mobile_sns.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yellow7918.mobile_sns.Model.ArticleModel;
import com.example.yellow7918.mobile_sns.Model.UpLoader;
import com.example.yellow7918.mobile_sns.Model.User;
import com.example.yellow7918.mobile_sns.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ArticleAddFragment extends Fragment {
    private ArticleModel model = new ArticleModel();
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;
    private Uri imageUri;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case PICK_FROM_ALBUM:
                imageUri = data.getData();

            case PICK_FROM_CAMERA:
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(imageUri, "image/*");

                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 200);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);

                startActivityForResult(intent, CROP_FROM_IMAGE);
                break;

            case CROP_FROM_IMAGE :

                Toast.makeText(getContext(),"test",Toast.LENGTH_SHORT).show();

                if (resultCode != RESULT_OK) {
                    return;
                }

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add, container, false);

        Button mChoiceButton = (Button) v.findViewById(R.id.choice_pic);
        final ImageView mChoiceImageView = (ImageView) v.findViewById(R.id.choice_imageView);
        final EditText mHashEdit = (EditText) v.findViewById(R.id.edit_hash);
        final Button mWriteButton = (Button) v.findViewById(R.id.write_article);

        mChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakePhotoAction();
                    }
                };

                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doChoicePhotoAction();
                    }
                };
                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doCancelAction();
                    }
                };

                new AlertDialog.Builder(getContext())
                        .setTitle("업로드할 이미지 선택")
                        .setPositiveButton("취소", cancelListener)
                        .setNeutralButton("앨범선택", albumListener)
                        .setNegativeButton("사진촬영", cameraListener)
                        .show();
            }
        });

        mWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                String tag = mHashEdit.getText().toString();

                List<User> users = UpLoader.getInstance();
                users.add(new User(name));

                model.upLoadArticle(name, tag);
                getActivity().finish();
            }
        });

        return v;
    }

    private void doTakePhotoAction() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        String uri = "temp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
//        imageUri = FileProvider.getUriForFile(getContext(), getContext().getPackageName()+".provider",new File(Environment.getExternalStorageDirectory(),uri));
//
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    private void doChoicePhotoAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    private void doCancelAction() {

    }


}
