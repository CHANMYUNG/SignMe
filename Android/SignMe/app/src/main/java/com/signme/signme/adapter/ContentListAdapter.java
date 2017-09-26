package com.signme.signme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.signme.signme.LetterTypes;
import com.signme.signme.R;

import java.util.ArrayList;

/**
 * Created by dsm2016 on 2017-09-24.
 */

public class ContentListAdapter extends BaseAdapter {
    ArrayList<ContentListItem> contentListItemArrayList = new ArrayList<ContentListItem>();

    public ContentListAdapter() {

    }

    @Override
    public int getCount() {
        return contentListItemArrayList.size();
    }

    @Override
    public ContentListItem getItem(int position) {
        return contentListItemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        Context context = parent.getContext();
        if (converView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            converView = inflater.inflate(R.layout.content_list_item, parent, false);
        }
        TextView title = (TextView) converView.findViewById(R.id.title_text);
        TextView openDate = (TextView) converView.findViewById(R.id.openDate);
        TextView closeDate = (TextView) converView.findViewById(R.id.closeDate);
        ContentListItem contentListItem = getItem(position);
        title.setText(contentListItem.getTitle());

        openDate.setText(contentListItem.getOpenDate());
        
        if (contentListItem.getType() != LetterTypes.RESPONSELESSLETTER) {
            closeDate.setText(contentListItem.getCloseDate());
        }

        return converView;
    }

    public void addItem(String title, String date) {
        ContentListItem contentListItem = new ContentListItem();
        contentListItem.setTitle(title);
        contentListItem.setOpenDate(date);
        contentListItemArrayList.add(contentListItem);
    }

    public void addItem(ContentListItem item) {
        contentListItemArrayList.add(item);
    }
}
