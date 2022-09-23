package com.example.project_who_yak;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.project_who_yak.databinding.ActivityCamaraScanBinding;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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

    //로컬이미지 변수
    private String imageUrl="";
    ActivityCamaraScanBinding mBinding;
    private Handler sliderHandler = new Handler();

    List<Bitmap> SliderItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //화면 이동
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara_scan);

        //스크린 위에 accessibility가 있는지 확인
        AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(ACCESSIBILITY_SERVICE);
        boolean isScreenReaderEnabled = accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled();

        Button btnvoice;
        Button btnhome;
        ImageButton btnImg;
        Button btnPic;
        Button btnOcr;
        Button btnResult;

        btnvoice = (Button) findViewById(R.id.btnvoice);
        btnhome = (Button) findViewById(R.id.btnhome);
        //btnImg = (ImageButton) findViewById(R.id.Ib_preview);
        btnResult = (Button) findViewById(R.id.btnResult);
        btnOcr = (Button) findViewById(R.id.btnOCR);
        btnPic = (Button) findViewById(R.id.btnPic);

        //OCR Textview
        OCRTextview = (TextView) findViewById(R.id.ocrResult);

        //OCR 기능
        datapath = getFilesDir() + "/tesseract/"; // 언어파일 경로
        //checkFile(new File(datapath + "/tessdata/kor.traineddata")); // 트레이닝 데이터 확인
        checkFile(new File(datapath + "tessdata/"),"kor");
        checkFile(new File(datapath + "tessdata/"),"eng");
        String lang = "kor"; // 언어 세팅

        //OCR 세팅
        mTess = new TessBaseAPI();
        mTess.init(datapath, lang);

        //Slider 변수
        mBinding = ActivityCamaraScanBinding.inflate(getLayoutInflater());

        // 카메라 권한 확인
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(activity_Camara_scan.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1); }
        }

        //---------리스너 파트-------------//
        //리스너 버튼 리스터
        btnvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isScreenReaderEnabled) {
                    Toast.makeText(getApplicationContext(),"이미 음성옵션이 설정되어있습니다.",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivity(intent);
                    //startActivityForResult(intent, 0);
                }
            }
        });

//        btnImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//
//                startActivityForResult(intent,10);
//
//            }
//        });

        //홈 버튼 리스너
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

        //OCR 버튼 리스너
        btnOcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable d = (BitmapDrawable)((ImageView) findViewById(R.id.ivPic)).getDrawable();
                image = d.getBitmap();

                //image = BitmapFactory.decodeResource(getResources(), R.drawable.ocr_kr);
                String OCResult = null;
                mTess.setImage(image);
                OCResult = mTess.getUTF8Text();
                OCRTextview.setText(OCResult);
            }
        });

        //뒤로가기 버튼 리스너
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //---------권한, 함수 파트-------------//
    // 카메라 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        } }

    //이미지 절대경로를 구한다.
    private String getRealPathFromUri(Uri uri)
    {
        String[] proj=  {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);
        Cursor cursor = cursorLoader.loadInBackground();

        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String url = cursor.getString(columnIndex);
        cursor.close();
        return  url;
    }


    // 카메라로 촬영한 사진의 썸네일을 가져와 이미지뷰에 띄워줌
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        ImageView ivPic;
        ImageView iv_preview1;
        ivPic = (ImageView)findViewById(R.id.ivPic);
        //iv_preview1 = (ImageView)findViewById(R.id.Iv_preview1);

        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) { case TAKE_PICTURE: if (resultCode == RESULT_OK && intent.hasExtra("data")) {
            Bitmap bitmap = (Bitmap) intent.getExtras().get("data");

            if (bitmap != null) {
                ivPic.setImageBitmap(bitmap);

                //아래 프리뷰 변경하는 함수
                //iv_preview1.setImageBitmap(bitmap);

                //카메라에서 찍은 사진을 슬라이더 리스트에 추가
                SliderItems.add(bitmap);
                //SliderItems.add(R.drawable.ic_baseline_textsms_24)
                mBinding.vpImageSlider.setAdapter(new SliderAdapter_Camara_scan(this, mBinding.vpImageSlider, SliderItems));

            }
        }break;
        }
        if(requestCode == 10)
        {
            imageUrl = getRealPathFromUri(intent.getData());
            RequestOptions cropOptions = new RequestOptions();
            Glide.with(getApplicationContext())
                    .load(imageUrl)
                    .into(ivPic);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }


    //장치에 파일 복사
    private void copyFiles(String lang) {
        try{
            //파일이 있을 위치
            String filepath = datapath + "/tessdata/"+lang+".traineddata";

            //AssetManager에 액세스
            AssetManager assetManager = getAssets();

            //읽기/쓰기를 위한 열린 바이트 스트림
            InputStream instream = assetManager.open("tessdata/"+lang+".traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            //filepath에 의해 지정된 위치에 파일 복사
            byte[] buffer = new byte[1024];
            int read;

            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
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

    //check file on the device
    private void checkFile(File dir, String lang) {
        //디렉토리가 없으면 디렉토리를 만들고 그후에 파일을 카피
        if (!dir.exists() && dir.mkdirs()) {
            copyFiles(lang);
        }
        //디렉토리가 있지만 파일이 없으면 파일카피 진행
        if (dir.exists()) {
            String datafilepath = datapath + "/tessdata/" + lang + ".traineddata";
            File datafile = new File(datafilepath);
            if (!datafile.exists()) {
                copyFiles(lang);
            }
        }
    }

    //    //OCR 데이터 파일이 있는지 확인
//    private void checkFile(File dir) {
//        if (!dir.exists() && dir.mkdirs()){
//            copyFiles();
//        }
//        if (dir.exists()){
//            String datafilepath = "/Users/kimandrew/AndroidStudioProjects/Project_Who_yak/app/src/main/assets/tessdata/kor.traineddata";
//            File datafile = new File(datafilepath);
//            if(!datafile.exists()){
//                copyFiles();
//            }
//        }
//    }
//
//    private void copyFiles() {
//        try {
//            String filePath = "/tessdata/kor.traineddata";
//            //String filePath = "tessdata/";
//            AssetManager assetManager = getAssets();
//            //InputStream instream = assetManager.open("tessdata/kor.traineddata");
//            InputStream instream = assetManager.open("/Users/kimandrew/AndroidStudioProjects/Project_Who_yak/app/src/main/assets/tessdata/kor.traineddata");
//            OutputStream outstream = new FileOutputStream(filePath);
//
//            byte[] buffer = new byte[1024];
//            int read;
//            while ((read = instream.read(buffer)) != -1){
//                outstream.write(buffer,0,read);
//            }
//            outstream.flush();
//            outstream.close();
//            instream.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}