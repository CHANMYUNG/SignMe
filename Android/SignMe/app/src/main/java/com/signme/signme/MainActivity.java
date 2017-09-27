package com.signme.signme;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.signme.signme.response.ResponseActivity;
import com.signme.signme.adapter.ListViewAdapter;
import com.signme.signme.mypage.MypageActivity;

import java.io.File;

public class MainActivity extends Activity implements DialogInterface.OnClickListener, View.OnClickListener {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;

    private Uri mImageCoptureUri;
    private ImageView mPhotoImgeView;
    private ImageButton mypagebtn;
    private ImageButton mButton;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //리스트 뷰
//        Log.d("xxx", FirebaseInstanceId.getInstance().getToken());

        ListView listView;
        ListViewAdapter adapter;

        adapter=new ListViewAdapter();

        listView=(ListView)findViewById(R.id.alarm_listview);
        listView.setAdapter(adapter);
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.text),"\n새로운 가정통신문",ContextCompat.getDrawable(this,R.drawable.right));
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.chat),"\n새로운 메세지",ContextCompat.getDrawable(this,R.drawable.right));
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.calander),"\n새로운 일정",ContextCompat.getDrawable(this,R.drawable.right));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Intent listbtn=new Intent(getApplicationContext(),LetterListActivity.class);
                startActivity(listbtn);
            }
        });
        //네비게이션 바
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
                        Intent schedule=new Intent(getApplicationContext(),LetterListActivity.class);
                        startActivity(schedule);
                        break;

                    // 가정통신문 선택시
                    case R.id.nav_newsLetter:
                        Intent intent=new Intent(getApplicationContext(),ResponseActivity.class);
                        startActivity(intent);

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
        mypagebtn=(ImageButton) findViewById(R.id.mypagebtn);
        mButton.setOnClickListener(this);
    }
    ///마이페이지
    //카메라에서 이미지 가져오기
    private void doTakePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //임시로 사용할 파일의 경로 생성
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
                    mypagebtn.setImageBitmap(photo);
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
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
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

    //두번 클릭 시 앱 종료
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
    //마이페이지 버튼
    public void mypageonClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
        startActivity(intent);
    }


}
