package com.signme.signme.survey;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.signme.signme.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by NooHeat on 26/09/2017.
 */

public class SurveyQuestionAdapter extends BaseAdapter {
    ArrayList<SurveyQuestionItem> questionList = new ArrayList<>();

    @Override
    public int getCount() {
        return questionList.size();
    }

    @Override
    public Object getItem(int position) {
        return questionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.survey_item, parent, false);
        }


        TextView question = (TextView) convertView.findViewById(R.id.survey_question);
        SurveyQuestionItem questionItem = (SurveyQuestionItem) getItem(position);

        question.setText(questionItem.getQuestion());

        return convertView;
    }

    public void addItem(SurveyQuestionItem item){
        questionList.add(item);
    }


}
