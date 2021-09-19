package com.example.chatapp;

import android.content.Context;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

    private List<Message> messages;
    private static final int TYPE_MY_MESSAGE = 1;
    private static final int TYPE_OTHER_MESSAGE = 2;
    private Context context;

    public MessagesAdapter(Context context) {
        messages = new ArrayList<>();
        this.context = context;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    public List<Message> getMessages() {
        return messages;
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == TYPE_MY_MESSAGE) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_my_message, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_other_message, viewGroup, false);
        }

        return new MessagesViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        String author = message.getAuthor();
        if (author != null && author.equals(PreferenceManager.getDefaultSharedPreferences(context).getString("author", "Anonym"))) {
            return TYPE_MY_MESSAGE;
        } else {
            return TYPE_OTHER_MESSAGE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder messagesViewHolder, int position) {
        Message message = messages.get(position);
        String author = message.getAuthor();
        String textOfMessage = message.getTextOfMessage();
        String urlToImage = message.getImageUrl();
        if (urlToImage == null || urlToImage.isEmpty()) {
            messagesViewHolder.imageViewImage.setVisibility(View.GONE);
        } else {
            messagesViewHolder.imageViewImage.setVisibility(View.VISIBLE);
        }
        messagesViewHolder.textViewAuthor.setText(author);
        if (textOfMessage != null && !textOfMessage.isEmpty()) {
            messagesViewHolder.textViewTextOfMessage.setVisibility(View.VISIBLE);
            messagesViewHolder.textViewTextOfMessage.setText(textOfMessage);
        } else {
            messagesViewHolder.textViewTextOfMessage.setVisibility(View.GONE);
        }
        if (urlToImage != null && !urlToImage.isEmpty()) {
            Picasso.get().load(urlToImage).into(messagesViewHolder.imageViewImage);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessagesViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewAuthor;
        private TextView textViewTextOfMessage;
        private ImageView imageViewImage;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewTextOfMessage = itemView.findViewById(R.id.textViewTextOfMessage);
            imageViewImage = itemView.findViewById(R.id.imageViewImage);
        }
    }
}
