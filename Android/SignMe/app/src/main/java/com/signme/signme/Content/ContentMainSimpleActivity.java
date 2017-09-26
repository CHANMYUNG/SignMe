package com.signme.signme.Content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.signme.signme.R;
import com.signme.signme.server.APIinterface;
import com.signme.signme.server.ApplicationController;
import com.signme.signme.server.contentlistRepo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dsm2016 on 2017-09-25.
 */

public class ContentMainSimpleActivity extends AppCompatActivity {
    private TextView title_text,date_text,content_text;
    private APIinterface apIinterface;
    static final String URL="http://13.124.15.202:80/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title_text=(TextView)findViewById(R.id.title_text);
        date_text=(TextView)findViewById(R.id.openDate);
        content_text=(TextView)findViewById(R.id.content_text);
        setContentView(R.layout.activity_content_main_simple);
        ApplicationController applicationController=ApplicationController.getInstance();
        applicationController.buildNetworkService(URL); //포트번호 임의로 지정
        apIinterface=ApplicationController.getInstance().getApIinterface();
        //파라미터에 실을 값 임의로 지정했음 바꿀예정
        int letterNumber=1 ;

        contentlistRepo Repo=new contentlistRepo();

        Call<contentlistRepo> repoCall=apIinterface.getletterNumber(letterNumber);
        repoCall.enqueue(new Callback<contentlistRepo>() {
            @Override
            public void onResponse(Call<contentlistRepo> call, Response<contentlistRepo> response) {
                int statusCode=response.code();
                if(statusCode==200){
                    contentlistRepo contentlistRepo_temp=response.body();
                    title_text.setText(contentlistRepo_temp.getTitle());
                    date_text.setText(contentlistRepo_temp.getOpenDate());
                    content_text.setText(contentlistRepo_temp.getContents());
                }
                else if(statusCode==404){
                    Toast.makeText(ContentMainSimpleActivity.this,"잘못된 연결입니다.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<contentlistRepo> call, Throwable t) {
                Toast.makeText(ContentMainSimpleActivity.this,"인터넷을 연결해 주세요.",Toast.LENGTH_LONG).show();
            }
        });
    }
}
