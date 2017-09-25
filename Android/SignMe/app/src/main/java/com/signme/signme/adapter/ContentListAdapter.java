package com.signme.signme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.signme.signme.R;

import java.util.ArrayList;

/**
 * Created by dsm2016 on 2017-09-24.
 */

public class ContentListAdapter extends BaseAdapter {
    ArrayList<ContentlistItem> contentlistItemArrayList=new ArrayList<ContentlistItem>();
    public ContentListAdapter(){

    }
    @Override
    public int getCount() {
        return contentlistItemArrayList.size();
    }

    @Override
    public ContentlistItem getItem(int position) {
        return contentlistItemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        Context context=parent.getContext();
        if(converView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            converView=inflater.inflate(R.layout.content_list_item,parent,false);
        }
        TextView tv_title=(TextView)converView.findViewById(R.id.title_text);
        TextView tv_date=(TextView)converView.findViewById(R.id.date_text);
        ContentlistItem contentlistItem=getItem(position);
        tv_title.setText(contentlistItem.getTitle());
        tv_date.setText(contentlistItem.getOpenDate());
        return converView;
    }
    public void addItem(String title,String date){
        ContentlistItem contentlistItem=new ContentlistItem();
        contentlistItem.setTitle(title);
        contentlistItem.setOpenDate(date);
        contentlistItemArrayList.add(contentlistItem);
    }


}
