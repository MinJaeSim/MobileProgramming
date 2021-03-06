package com.example.yellow7918.mobile_sns;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ArticleAddActivity extends AppCompatActivity {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 10;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    private ArticleModel model = new ArticleModel();

    private Uri imageUri;
    private StorageReference ref;
    private String stringImageUrl;
    private ImageView mChoiceImageView;
    private String absPath;

    private EditText mHashEdit;

    private static String timestamp() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("y년 M월 d일 hh:mm" + " 작성", Locale.KOREA);
        return dateFormat.format(date);
    }

    private String generateTempFilename() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case PICK_FROM_ALBUM:
                imageUri = data.getData();
                Glide.with(ArticleAddActivity.this)
                        .load(imageUri)
                        .into(mChoiceImageView);
                break;

            case PICK_FROM_CAMERA:
                imageUri = galleryAdd();

                Glide.with(ArticleAddActivity.this)
                        .load(imageUri)
                        .into(mChoiceImageView);
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        ref = storage.getReferenceFromUrl("gs://mobileprogram-c7ab5.appspot.com").child("images");

        Button mChoiceButton = (Button) findViewById(R.id.choice_pic);
        mChoiceImageView = (ImageView) findViewById(R.id.choice_imageView);
        mHashEdit = (EditText) findViewById(R.id.edit_hash);
        //mHashEdit.setVisibility(View.GONE);
        final Button mWriteButton = (Button) findViewById(R.id.write_article);

        mChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ContextCompat.checkSelfPermission(ArticleAddActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(ArticleAddActivity.this, Manifest.permission.CAMERA)) {
                                ActivityCompat.requestPermissions(ArticleAddActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                            } else {
                                ActivityCompat.requestPermissions(ArticleAddActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                            }
                        } else {
                            doTakePhotoAction();
                        }
                    }
                };

                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ContextCompat.checkSelfPermission(ArticleAddActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(ArticleAddActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                ActivityCompat.requestPermissions(ArticleAddActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                            } else {
                                ActivityCompat.requestPermissions(ArticleAddActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                            }
                        } else {
                            doChoicePhotoAction();
                        }
                    }
                };
                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };

                new AlertDialog.Builder(ArticleAddActivity.this)
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
                final String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                final String tag = mHashEdit.getText().toString();

                List<User> users = UpLoader.getInstance();
                users.add(new User(name));

                if (imageUri != null) {
                    try {
                        ContentResolver resolver = getContentResolver();
                        InputStream is = resolver.openInputStream(imageUri);

                        assert is != null;
                        UploadTask task = ref.child(generateTempFilename()).putStream(is);
                        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                stringImageUrl = taskSnapshot.getDownloadUrl().toString();
                                model.upLoadArticle(name, timestamp(),stringImageUrl, tag);
                            }
                        });
                    } catch (FileNotFoundException e) {

                    }
                }
                finish();
            }
        });
    }

    private void doChoicePhotoAction() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    private void doTakePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
            }
            if (photoFile != null) {
                Uri contentUri = FileProvider.getUriForFile(this.getBaseContext(), "com.example.yellow7918.mobile_sns.fileprovider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

                startActivityForResult(intent, PICK_FROM_CAMERA);
            }
        }
    }

    private File createImageFile() throws IOException {
        String fileName = generateTempFilename();
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(fileName, ".jpg", storageDir);

        absPath = image.getAbsolutePath();
        return image;
    }

    private Uri galleryAdd() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(absPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);

        return contentUri;
    }
}
