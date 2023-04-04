package com.example.chatbot2o;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>{

    List<Message> messageList;
    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(chatView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message = messageList.get(position);

        if (message.getSendBy().equals(Message.SEND_BY_ME)){
            holder.left_chatView.setVisibility(View.GONE);
            holder.right_chatview.setVisibility(View.VISIBLE);
            holder.right_chat_textView.setText(message.getMessage());

        }else {
            holder.right_chatview.setVisibility(View.GONE);
            holder.left_chatView.setVisibility(View.VISIBLE);
            holder.left_chat_textView.setText(message.getMessage());

        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public  class  MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout left_chatView, right_chatview;
        TextView left_chat_textView, right_chat_textView;

        public MyViewHolder  (@NonNull View itemView) {
            super(itemView);
            left_chatView = itemView.findViewById(R.id.left_chatView);
            right_chatview = itemView.findViewById(R.id.right_chatView);
            left_chat_textView = itemView.findViewById(R.id.left_chat_textView);
            right_chat_textView = itemView.findViewById(R.id.right_chat_textView);
        }
    }
}
