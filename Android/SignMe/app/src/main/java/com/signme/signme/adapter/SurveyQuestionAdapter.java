package com.signme.signme.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.signme.signme.R;
import com.signme.signme.model.SurveyQuestionItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by NooHeat on 26/09/2017.
 */

public class SurveyQuestionAdapter extends BaseAdapter {
    ArrayList<SurveyQuestionItem> questionList = new ArrayList<>();

    private static Integer[] answers;

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        Log.d("getCount", answers.length + "");

        Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_survey, parent, false);
        }

        TextView question = (TextView) convertView.findViewById(R.id.survey_question);
        SurveyQuestionItem questionItem = (SurveyQuestionItem) getItem(position);
        question.setText(questionItem.getQuestion());

        TextView radio1=(TextView) convertView.findViewById(R.id.radio_tv1);
        TextView radio2=(TextView) convertView.findViewById(R.id.radio_tv2);
        TextView radio3=(TextView) convertView.findViewById(R.id.radio_tv3);
        TextView radio4=(TextView) convertView.findViewById(R.id.radio_tv4);
        TextView radio5=(TextView) convertView.findViewById(R.id.radio_tv5);
        radio1.setText(questionItem.getAnswer1());
        radio2.setText(questionItem.getAnswer2());
        radio3.setText(questionItem.getAnswer3());
        radio4.setText(questionItem.getAnswer4());
        radio5.setText(questionItem.getAnswer5());
        Log.d("라디오",radio1.toString());




        final RadioGroup answerGroup = (RadioGroup) convertView.findViewById(R.id.survey_answer_group);
        answerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Log.d("POSITION", position + "");
                switch (checkedId) {
                    case R.id.survey_answer5:
                        answers[position] = 5;
                        break;
                    case R.id.survey_answer4:
                        answers[position] = 4;
                        break;
                    case R.id.survey_answer3:
                        answers[position] = 3;
                        break;
                    case R.id.survey_answer2:
                        answers[position] = 2;
                        break;
                    case R.id.survey_answer1:
                        answers[position] = 1;
                        break;
                }

                for (int i = 0; i < answers.length; i++) {
                    Log.d("answers[" + i + "]", answers[i] + "");
                }
            }
        });
        return convertView;
    }

    public void addItem(SurveyQuestionItem item) {
        questionList.add(item);
        answers = new Integer[getCount()];
    }

    public ArrayList<SurveyQuestionItem> getItemList() {
        return questionList;
    }

    public List<Integer> getAnswers() {
        return Arrays.asList(answers);
    }

}
