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

public class LetterListAdapter extends BaseAdapter {

    ArrayList<LetterListItem> letterListItemArrayList = new ArrayList<LetterListItem>();

    public LetterListAdapter() {

    }

    @Override
    public int getCount() {
        return letterListItemArrayList.size();
    }

    @Override
    public LetterListItem getItem(int position) {
        return letterListItemArrayList.get(position);
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
            converView = inflater.inflate(R.layout.letter_list_item, parent, false);
        }
        TextView title = (TextView) converView.findViewById(R.id.title_text);
        TextView openDate = (TextView) converView.findViewById(R.id.openDate);
        TextView closeDate = (TextView) converView.findViewById(R.id.closeDate);
        TextView closeDateMent = (TextView) converView.findViewById(R.id.closeDateMent);
        LetterListItem letterListItem = getItem(position);
        title.setText(letterListItem.getTitle());

        openDate.setText(letterListItem.getOpenDate());

        if (letterListItem.getType() != LetterTypes.RESPONSELESSLETTER) {
            closeDate.setText(letterListItem.getCloseDate());
        } else {
            closeDateMent.setVisibility(View.INVISIBLE);
            closeDate.setVisibility(View.INVISIBLE);
        }

        return converView;
    }

    public void addItem(LetterListItem item) {
        letterListItemArrayList.add(item);
    }

}
