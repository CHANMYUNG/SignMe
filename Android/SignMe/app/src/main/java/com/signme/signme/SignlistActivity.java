package com.signme.signme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.signme.signme.server.APIinterface;
import com.signme.signme.server.contentListItem;

import java.util.ArrayList;
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
    ArrayList<Integer> listItems=new ArrayList<>();
    ArrayAdapter<contentListItem> arrayAdapter;
    private String repo_title;
    private String repo_date;
    //ArrayAdapter<ListViewAdapter> adapter;
    int clickCounter=0;
//    private class ViewHolder {
//        public TextView title_textview;
//        public TextView date_textview;
//
//    }
//
//    private class ListViewAdapter extends BaseAdapter {
//        private Context mContext = null;
//        private ArrayList<ContentlistItem> mListData = new ArrayList<>();
//
//        public ListViewAdapter(Context mContext) {
//            super();
//            this.mContext = mContext;
//        }
//
//        @Override
//        public int getCount() {
//            return mListData.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return mListData.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            SignlistActivity.ViewHolder holder;
//            if (convertView == null) {
//                 holder = new ViewHolder();
//
//                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = inflater.inflate(R.layout.content_list_item, null);
//                holder.title_textview = (TextView) convertView.findViewById(R.id.title_text);
//                holder.date_textview = (TextView) convertView.findViewById(R.id.date_text);
//
//                convertView.setTag(holder);
//            }else{
//                holder = (ViewHolder) convertView.getTag();
//            }
//
//            ContentlistItem mData = mListData.get(position);
//
//
//            holder.title_textview.setText(mData.title);
//            holder.date_textview.setText(mData.openDate);
//
//
//            return convertView;
//        }
//
//        public void addItem(View v){
////            ContentlistItem addInfo = null;
////            addInfo = new ContentlistItem();
////            addInfo.title = title_text;
////            addInfo.openDate = date_text;
//
//
//            mListData.add("Clicked:"+clickCounter++);
//        }
//
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signlist);
        mListView = (ListView) findViewById(R.id.contentlist);
        arrayAdapter=new ArrayAdapter<contentListItem>(this,R.layout.content_list_item);
        mListView.setAdapter(arrayAdapter);
//

//        adapter=new ArrayAdapter<ListViewAdapter>(this,R.layout.content_list_item);
//      mListView.setAdapter(adapter);
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
//        mAdapter.addItem(repo_title,repo_date);
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
        listItems.add(clickCounter++);
        arrayAdapter.notifyDataSetChanged();
    }
    public void get(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Map map= new HashMap();
        map.get(getTitle());
        APIinterface retrofitService=retrofit.create(APIinterface.class);
        Call<contentListItem> call=retrofitService.letter_list(map);
        call.enqueue(new Callback<contentListItem>() {
            @Override
            public void onResponse(Call<contentListItem> call, Response<contentListItem> response) {
               
                contentListItem repo=response.body();

                for(int i=0;i<repo.getGetContentList().size();i++){

                    repo_title=repo.getGetContentList().get(i).getTitle();
                   repo_date=repo.getGetContentList().get(i).getOpenDate();

                }

            }

            @Override
            public void onFailure(Call<contentListItem> call, Throwable t) {

            }

        });
    }
}
