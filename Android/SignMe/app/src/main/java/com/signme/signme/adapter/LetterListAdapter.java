package com.signme.signme.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.signme.signme.LetterTypes;
import com.signme.signme.R;
import com.signme.signme.model.LetterListItem;
import com.signme.signme.activity.ResponseLetterActivity;
import com.signme.signme.activity.ResponselessLetterActivity;
import com.signme.signme.activity.SurveyActivity;
import com.signme.signme.support.DateTime;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by dsm2016 on 2017-09-24.
 */

public class LetterListAdapter extends RecyclerView.Adapter<LetterListAdapter.MyViewHolder> {

    ArrayList<LetterListItem> mLetterSet = new ArrayList<LetterListItem>();

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titleView;
        public TextView writerNameView;
        public TextView openDateView;
        public TextView closeDateView;
        public TextView closeDateMentView;

        public MyViewHolder(final View view, final int position) {
            super(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final LetterListItem item = mLetterSet.get(position);
                    int letterNumber = item.getLetterNumber();
                    Intent letterActivity = null;

                    if (item.getType() == LetterTypes.RESPONSELESSLETTER) {
                        letterActivity = new Intent(v.getContext(), ResponselessLetterActivity.class);
                    }
                    if (item.getType() == LetterTypes.SURVEY) {
                        letterActivity = new Intent(v.getContext(), SurveyActivity.class);
                    }
                    if (item.getType() == LetterTypes.RESPONSELETTER) {
                        letterActivity = new Intent(v.getContext(), ResponseLetterActivity.class);
                    }

                    letterActivity.putExtra("letterNumber", letterNumber);

                    if (item.isAnswered()) {
                        final Intent finalLetterActivity = letterActivity;
                        new SweetAlertDialog(v.getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("이미 답변하셨습니다!")
                                .setContentText("응답을 수정하실건가요?")
                                .setConfirmText("네! 수정할래요.")
                                .setCancelText("아뇨 됐어요.")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        // reuse previous dialog instance
                                        if(item.getCloseDate().compareTo(DateTime.getDateNow()) == -1){
                                            Toast.makeText(sDialog.getContext(), "응답 기한이 지났습니다.", Toast.LENGTH_SHORT).show();
                                            sDialog.dismiss();
                                        }
                                        else {
                                            finalLetterActivity.putExtra("modify", true);
                                            v.getContext().startActivity(finalLetterActivity);
                                        }
                                        sDialog.dismiss();
                                    }
                                })
                                .show();
                        return;
                    }
                    else{
                        v.getContext().startActivity(letterActivity);
                    }

                }

            });

            titleView = (TextView) view.findViewById(R.id.letter_title);
            writerNameView = (TextView) view.findViewById(R.id.letter_writerName);
            openDateView = (TextView) view.findViewById(R.id.letter_openDate);
            closeDateView = (TextView) view.findViewById(R.id.letter_closeDate);
            closeDateMentView = (TextView) view.findViewById(R.id.letter_closeDateMent);
        }

    }

    public LetterListAdapter(ArrayList<LetterListItem> mLetterSet) {
        this.mLetterSet = mLetterSet;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_letter_list, parent, false);
        MyViewHolder vh = new MyViewHolder(v, viewType);
        Log.d("xxx", "onCreateViewHolder: " + viewType);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("BINDBIND", "ASDASDDAS");
        holder.titleView.setText(mLetterSet.get(position).getTitle());
        holder.openDateView.setText(mLetterSet.get(position).getOpenDate());
        holder.writerNameView.setText(mLetterSet.get(position).getWriterName());
        if (mLetterSet.get(position).getType() != LetterTypes.RESPONSELESSLETTER) {
            holder.closeDateView.setText(mLetterSet.get(position).getCloseDate());
        } else {
            holder.closeDateMentView.setVisibility(View.GONE);
            holder.closeDateView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mLetterSet.size();
    }



}
