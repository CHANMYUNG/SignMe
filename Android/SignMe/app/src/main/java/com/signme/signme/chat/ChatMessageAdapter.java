package com.signme.signme.chat;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.signme.signme.R;
import com.signme.signme.chat.ChatMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsm2016 on 2017-07-20.
 */

public class ChatMessageAdapter extends ArrayAdapter {
    List msgs=new ArrayList();
    public ChatMessageAdapter(Context context,int textViewResouroeId){
        super(context,textViewResouroeId);
    }




    //@Override
    public void add(ChatMessage object) {
       msgs.add(object);
        super.add(object);
    }
    @Override
    public int getCount(){
        return msgs.size();
    }
    @Override
    public  ChatMessage getItem(int index){
        return (ChatMessage) msgs.get(index);
    }
    @Override
    public View getView(int position, View converView, ViewGroup parent){
        View row=converView;
        if(row==null){
            LayoutInflater inflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.chatting_message,parent,false);
        }
        ChatMessage msg=(ChatMessage)msgs.get(position);
        TextView msgText=(TextView)row.findViewById(R.id.chatmassage);
        msgText.setText(msg.message);
        msgText.setTextColor(Color.parseColor("#000000"));
        return  row;
    }


}
