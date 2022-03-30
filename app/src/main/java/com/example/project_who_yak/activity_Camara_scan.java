package com.example.project_who_yak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class activity_Camara_scan extends AppCompatActivity {

    final String TAG = "HIBAR";
    final int TAKE_PICTURE = 1;
    String mCurrentPhotoPath;
    final int REQUEST_TAKE_PHOTO = 1;

    //OCR 변수
    Bitmap image;
    private TessBaseAPI mTess;
    String datapath = "";
    TextView OCRTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //화면 이동
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara_scan);

        Button btnPic;
        Button btnBack;

        btnBack = (Button)findViewById(R.id.btnBack);
        btnPic = (Button) findViewById(R.id.btnPic);

        //OCR Textview
        OCRTextview = findViewById(R.id.ocrResult);

        //OCR 기능
        image = BitmapFactory.decodeResource(getResources(), R.drawable.ocr_kr);
        datapath = getFilesDir() + ""; // 언어파일 경로
        checkFile(new File(datapath + "/tessdata/kor.traineddata")); // 트레이닝 데이터 확인
        String lang = "kor"; // 언어 세팅
        //OCR 세팅
        mTess = new TessBaseAPI();
        mTess.init(datapath, lang);

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
                //finish();
                String OCResult = null;
                mTess.setImage(image);
                OCResult = mTess.getUTF8Text();
                OCRTextview.setText(OCResult);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
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

    //OCR 데이터 파일이 있는지 확인
    private void checkFile(File dir) {
        if (!dir.exists() && dir.mkdirs()){
            copyFiles();
        }
        if (dir.exists()){
            String datafilepath = "/tesseract/";
            File datafile = new File(datafilepath);
            if(!datafile.exists()){
                copyFiles();
            }
        }
    }

    private void copyFiles() {
        try {
           // String filePath = "/data/user/0/com.example.project_who_yak/files/tesseract/tessdata";
            String filePath = "tessdata/";
            AssetManager assetManager = getAssets();
            //InputStream instream = assetManager.open("tessdata/kor.traineddata");
            InputStream instream = assetManager.open("/Users/kimandrew/AndroidStudioProjects/Project_Who_yak/app/src/main/assets/tessdata/kor.traineddata");
            OutputStream outstream = new FileOutputStream(filePath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1){
                outstream.write(buffer,0,read);
            }
            outstream.flush();
            outstream.close();
            instream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    //이미지에서 텍스트 읽기
    public void  processImage(View view){
        String OCResult = null;
        mTess.setImage(image);
        OCResult = mTess.getUTF8Text();
        OCRTextview.setText(OCResult);
    }

}