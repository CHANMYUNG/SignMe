package com.signme.signme;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;




/**
 * Created by dsm2016 on 2017-07-16.
 */

public class ListViewAdapter extends BaseAdapter {
   private ArrayList<ListViewItem> listViewItemList=new ArrayList<ListViewItem>();
    public ListViewAdapter(){

    }
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos=position;
        final Context context =parent.getContext();
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.listview_item,parent,false);
        }
        ImageView icon=(ImageView) convertView.findViewById(R.id.icon_image);
        TextView title=(TextView) convertView.findViewById(R.id.titleView);
        ImageView image=(ImageView) convertView.findViewById(R.id.image);
        ListViewItem lisViewItem=listViewItemList.get(position);

        icon.setImageDrawable(lisViewItem.getIcon());
        title.setText(lisViewItem.getTitle());
        image.setImageDrawable(lisViewItem.getImage());
        return convertView;
    }
    public void addItem(Drawable icon, String title, Drawable image){
        ListViewItem item=new ListViewItem();
        item.setIcon(icon);
        item.setTitle(title);
        item.setImage(image);

        listViewItemList.add(item);

    }
}
