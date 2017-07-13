package com.signme.signme;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends Activity implements DialogInterface.OnClickListener, View.OnClickListener {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;

    private Uri mImageCoptureUri;
    private ImageView mPhotoImgeView;
    private ImageButton mButton;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout);
        ImageButton drawerIB = (ImageButton) findViewById(R.id.ib_drawer);
        drawerIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        NavigationView nv = (NavigationView) findViewById(R.id.navigation);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(MainActivity.this, "" + item.getItemId(), Toast.LENGTH_SHORT).show();
                switch (item.getItemId()) {
                    // 마이페이지 선택시
                    case R.id.nav_mypage:
                        Intent mypage_btn = new Intent(getApplicationContext(), MypageActivity.class);
                        startActivity(mypage_btn);
                        break;
                    // 신청서 일정 선택시
                    case R.id.nav_schedule:

                        break;

                    // 가정통신문 선택시
                    case R.id.nav_newsLetter:

                        break;

                    // 채팅 선택시
                    case R.id.nav_chat:

                        break;

                    // 설정 선택시
                    case R.id.nav_option:

                        break;
                }

                return true;
            }
        });
        mButton = (ImageButton) findViewById(R.id.profilebtn);
        mPhotoImgeView = (ImageView) findViewById(R.id.profile);
        mButton.setOnClickListener(this);
    }

    //카메라에서 이미지 가져오기
    private void doTakePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //임시로 사용할 팔일의 경로 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCoptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCoptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    private void doTakeAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CROP_FROM_CAMERA: {
                final Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    mPhotoImgeView.setImageBitmap(photo);
                }
                File f = new File(mImageCoptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }
                break;
            }
            case PICK_FROM_ALBUM: {
                mImageCoptureUri = data.getData();
            }
            case PICK_FROM_CAMERA: {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCoptureUri, "image/*");
                intent.putExtra("outputX", 90);
                intent.putExtra("outputY", 90);
                intent.putExtra("aspectY", 1);
                intent.putExtra("aspectY", true);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_CAMERA);
                break;

            }
        }


    }


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
                doTakeAlbumAction();
            }
        };
        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        new AlertDialog.Builder(this)
                .setTitle("프로필 선택")
                .setPositiveButton("사진촬영", cameraListener)
                .setNeutralButton("앨범선택", albumListener)
                .setNegativeButton("취소", cancelListener)
                .show();
    }

    //두번 클릭 시 종료
    @Override
    public void onBackPressed() {
        if (pressedTime == 0) {
            Toast.makeText(MainActivity.this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        } else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);
            if (seconds > 2000) {
                pressedTime = 0;
            } else {
                finish();
            }
        }
    }

    public void sideButtonClicked(View view) {

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    public void mypageonClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
        startActivity(intent);
    }

}
