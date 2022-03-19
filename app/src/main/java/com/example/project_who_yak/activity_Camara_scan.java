package com.example.project_who_yak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;

public class activity_Camara_scan extends AppCompatActivity {

    final String TAG = "HIBAR";
    final int TAKE_PICTURE = 1;
    String mCurrentPhotoPath;
    final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //화면 이동
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara_scan);

        Button btnPic;
        Button btnBack;

        btnBack = (Button)findViewById(R.id.btnBack);
        btnPic = (Button) findViewById(R.id.btnPic);

        //권한 확인
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(activity_Camara_scan.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1); }
        }


        //촬영 버튼 리스너
        btnPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnPic:
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });

        //뒤로가기 버튼 리스너
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Main으로 이동
                finish();
            }
        });
    }

    // 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        } }


    // 카메라로 촬영한 사진의 썸네일을 가져와 이미지뷰에 띄워줌
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        ImageView ivPic;
        ImageView iv_preview1;
        ivPic = (ImageView)findViewById(R.id.ivPic);
        iv_preview1 = (ImageView)findViewById(R.id.Iv_preview1);
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) { case TAKE_PICTURE: if (resultCode == RESULT_OK && intent.hasExtra("data")) {
            Bitmap bitmap = (Bitmap) intent.getExtras().get("data");

            if (bitmap != null) {
                ivPic.setImageBitmap(bitmap);
                iv_preview1.setImageBitmap(bitmap);
            }
        }break;
        }
    }

}