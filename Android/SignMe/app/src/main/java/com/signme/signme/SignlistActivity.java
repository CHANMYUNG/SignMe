package com.signme.signme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.signme.signme.adapter.ContentlistItem;
import com.signme.signme.server.APIinterface;
import com.signme.signme.server.contentListItem;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dsm2016 on 2017-09-04.
 */

public class SignlistActivity extends AppCompatActivity {
    static final String URL="http://13.124.15.202:80/";
    private ListView mListView = null;
    //private SignlistActivity.ListViewAdapter mAdapter = null;
    ArrayAdapter<String> arrayAdapter;
    private String repo_title;
    private String repo_date;
    //ArrayAdapter<ListViewAdapter> adapter;
    int clickCounter=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signlist);
        mListView = (ListView) findViewById(R.id.contentlist);
        arrayAdapter=new ArrayAdapter<>(this,R.layout.content_list_item);
        mListView.setAdapter(arrayAdapter);
        get();
//
//        mAdapter = new ListViewAdapter(this);
//        mListView.setAdapter(mAdapter);
//        get();
//
//        mAdapter.addItem(
//                "보건소식",
//                "2017-09-18"
//        );
//        mAdapter.addItem(
//                "보건소식2",
//                "2017-09-20"
//        );
//        mAdapter.notifyDataSetChanged();
//
//
//
//       mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
//                ContentlistItem mData = mAdapter.mListData.get(position);
//                Toast.makeText(SignlistActivity.this, mData.title, Toast.LENGTH_SHORT).show();
//            }
//        });

    }
    public void addItems(View v){
        arrayAdapter.add("Click:"+clickCounter++);

        arrayAdapter.notifyDataSetChanged();
    }
    ///레트로핏 어뎁터
    public void get(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Map map= new HashMap();
        //Map map2=new HashMap();
        map.get(getTitle());
       // map2.get(getOpenDate());
        APIinterface retrofitService=retrofit.create(APIinterface.class);
        //Call<contentListItem> call=retrofitService.letter_list(map);
//        call.enqueue(new Callback<contentListItem>() {
//            @Override
//            public void onResponse(Call<contentListItem> call, Response<contentListItem> response) {
//                int statusCode=response.code();
//                contentListItem repo=response.body();
//                if(statusCode==200){
//                    for(int i=0;i<repo.getGetContentList().size();i++){
//                        repo_title=repo.getGetContentList().get(i).getTitle();
//                        repo_date=repo.getGetContentList().get(i).getOpenDate();
//
//                    }
//                  //  arrayAdapter.add(repo_title,repo_dat);
//                }
//                else if(statusCode==400){
//                    Toast.makeText(SignlistActivity.this,"잘못된 연결",Toast.LENGTH_SHORT).show();
//                }
//
//            }

//            @Override
//            public void onFailure(Call<contentListItem> call, Throwable t) {
//                Toast.makeText(SignlistActivity.this,"인터넷 연결 해주세요",Toast.LENGTH_LONG).show();
//            }
//
//        });
//    }
//    //뒤로가기
//    public void signlist_back(View view){
//        finish();
   }
}
